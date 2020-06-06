package test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.videoClub.exception.EmptyGenreList;
import com.videoClub.exception.InvalidDiscount;
import com.videoClub.model.Action;
import com.videoClub.model.ActionEvent;
import com.videoClub.model.Administrator;
import com.videoClub.model.Discount;
import com.videoClub.model.Film;
import com.videoClub.model.FreeContent;
import com.videoClub.model.Notification;
import com.videoClub.model.Offer;
import com.videoClub.model.Purchase;
import com.videoClub.model.RegisteredUser;
import com.videoClub.model.Review;
import com.videoClub.model.User;
import com.videoClub.model.enumeration.Genre;
import com.videoClub.model.enumeration.Rank;

public class ActionRulesTest {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static DateTimeFormatter df2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	private static List<Offer> offers = generateOffers();
	
	private static int DISCOUNT_VALID = 50;
	private static int DISCOUNT_INVALID_LOW = 80;
	private static int DISCOUNT_INVALID_HIGH = 5;
	
	/*Test adding valid action event*/
	@Test
	public void test1(){
		ActionEvent actionEvent =  generateActionEvent();
		actionEvent.getActions().add(generateDiscount(
				Rank.BRONZE, actionEvent, DISCOUNT_VALID));
		actionEvent.getActions().add(generateFreeContent(
				Rank.GOLD, actionEvent));
		
		List<User> users = generateUsers();
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("actionRulesSession");
		for(User u : users){
			kieSession.insert(u);
		}
		for(Action a : actionEvent.getActions()){
			kieSession.insert(a);
		}
		kieSession.fireAllRules();
		kieSession.dispose();
		
		RegisteredUser userNone = (RegisteredUser) users.get(0);
		RegisteredUser userBronze = (RegisteredUser) users.get(1);
		RegisteredUser userGold = (RegisteredUser) users.get(2);
		
		assertEquals(0, userNone.getAction().size());
		assertEquals(1, userBronze.getAction().size());
		assertEquals(1, userGold.getAction().size());
		
		assertTrue(userBronze.getAction().get(0) instanceof Discount);
		assertTrue(userGold.getAction().get(0) instanceof FreeContent);
		
		assertTrue(((Discount)userBronze.getAction().get(0)).getAmount() == DISCOUNT_VALID);
		assertTrue(((FreeContent)userGold.getAction().get(0)).getFreeGenres().size() == 3);
		
		assertTrue(((FreeContent)userGold.getAction().get(0)).getFreeGenres().contains(Genre.ACTION));
		assertTrue(((FreeContent)userGold.getAction().get(0)).getFreeGenres().contains(Genre.THRILLER));
		assertTrue(((FreeContent)userGold.getAction().get(0)).getFreeGenres().contains(Genre.HORROR));
	}
	
	/*Test adding 2 action events*/
	@Test
	public void test2(){
		ActionEvent actionEvent1 =  generateActionEvent();
		actionEvent1.getActions().add(generateDiscount(
				Rank.BRONZE, actionEvent1, DISCOUNT_VALID));
		actionEvent1.getActions().add(generateFreeContent(
				Rank.GOLD, actionEvent1));
		
		ActionEvent actionEvent2 =  generateActionEvent();
		actionEvent2.getActions().add(generateDiscount(
				Rank.BRONZE, actionEvent2, DISCOUNT_VALID));
		actionEvent2.getActions().add(generateFreeContent(
				Rank.GOLD, actionEvent2));
		
		List<User> users = generateUsers();
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("actionRulesSession");
		for(User u : users){
			kieSession.insert(u);
		}
		for(Action a : actionEvent1.getActions()){
			kieSession.insert(a);
		}
		kieSession.fireAllRules();
		kieSession.dispose();
		
		KieSession kieSession2 = kieContainer.newKieSession("actionRulesSession");
		for(User u : users){
			kieSession2.insert(u);
		}
		for(Action a : actionEvent2.getActions()){
			kieSession2.insert(a);
		}
		kieSession2.fireAllRules();
		kieSession2.dispose();
		
		RegisteredUser userNone = (RegisteredUser) users.get(0);
		RegisteredUser userBronze = (RegisteredUser) users.get(1);
		RegisteredUser userGold = (RegisteredUser) users.get(2);
		
		assertEquals(0, userNone.getAction().size());
		assertEquals(1, userBronze.getAction().size());
		assertEquals(1, userGold.getAction().size());
		
		assertTrue(userBronze.getAction().get(0) instanceof Discount);
		assertTrue(userGold.getAction().get(0) instanceof FreeContent);
		
		assertTrue(((Discount)userBronze.getAction().get(0)).getAmount() == DISCOUNT_VALID);
		assertTrue(((FreeContent)userGold.getAction().get(0)).getFreeGenres().size() == 3);
		
		assertTrue(((FreeContent)userGold.getAction().get(0)).getFreeGenres().contains(Genre.ACTION));
		assertTrue(((FreeContent)userGold.getAction().get(0)).getFreeGenres().contains(Genre.THRILLER));
		assertTrue(((FreeContent)userGold.getAction().get(0)).getFreeGenres().contains(Genre.HORROR));
	}
	
	/*Test adding action event with invalid discount*/
	@Test
	public void test3(){
		ActionEvent actionEvent =  generateActionEvent();
		actionEvent.getActions().add(generateInvalidDiscount(
				Rank.BRONZE, actionEvent));
		actionEvent.getActions().add(generateFreeContent(
				Rank.GOLD, actionEvent));
		
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("actionRulesSession");
		List<User> users = generateUsers();
		for(User u : users){
			kieSession.insert(u);
		}
		for(Action a : actionEvent.getActions()){
			kieSession.insert(a);
		}
		boolean exceptionOccured = false;
		try {
			kieSession.fireAllRules();
			kieSession.dispose();
		} catch (Exception e) {
			exceptionOccured = true;
			assertTrue(e.getMessage().contains(new InvalidDiscount().getMessage()));
		}
		assertTrue(exceptionOccured);
	}

	/*Test adding action event with invalid discount*/
	@Test
	public void test4(){
		ActionEvent actionEvent =  generateActionEvent();
		actionEvent.getActions().add(generateDiscount(
				Rank.BRONZE, actionEvent, DISCOUNT_INVALID_LOW));
		actionEvent.getActions().add(generateFreeContent(
				Rank.GOLD, actionEvent));
		
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("actionRulesSession");
		List<User> users = generateUsers();
		for(User u : users){
			kieSession.insert(u);
		}
		for(Action a : actionEvent.getActions()){
			kieSession.insert(a);
		}
		boolean exceptionOccured = false;
		try {
			kieSession.fireAllRules();
			kieSession.dispose();
		} catch (Exception e) {
			exceptionOccured = true;
			assertTrue(e.getMessage().contains(new InvalidDiscount().getMessage()));
		}
		assertTrue(exceptionOccured);
	}
	
	/*Test adding action event with invalid discount*/
	@Test
	public void test5(){
		ActionEvent actionEvent =  generateActionEvent();
		actionEvent.getActions().add(generateDiscount(
				Rank.BRONZE, actionEvent, DISCOUNT_INVALID_HIGH));
		actionEvent.getActions().add(generateFreeContent(
				Rank.GOLD, actionEvent));
		
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("actionRulesSession");
		List<User> users = generateUsers();
		for(User u : users){
			kieSession.insert(u);
		}
		for(Action a : actionEvent.getActions()){
			kieSession.insert(a);
		}
		boolean exceptionOccured = false;
		try {
			kieSession.fireAllRules();
			kieSession.dispose();
		} catch (Exception e) {
			exceptionOccured = true;
			assertTrue(e.getMessage().contains(new InvalidDiscount().getMessage()));
		}
		assertTrue(exceptionOccured);
	}
	
	/*Test adding action event with invalid free content*/
	@Test
	public void test8(){
		ActionEvent actionEvent =  generateActionEvent();
		actionEvent.getActions().add(generateDiscount(
				Rank.BRONZE, actionEvent, DISCOUNT_VALID));
		actionEvent.getActions().add(generateInvalidFreeContent(
				Rank.GOLD, actionEvent));
		
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("actionRulesSession");
		List<User> users = generateUsers();
		for(User u : users){
			kieSession.insert(u);
		}
		for(Action a : actionEvent.getActions()){
			kieSession.insert(a);
		}
		boolean exceptionOccured = false;
		try {
			kieSession.fireAllRules();
			kieSession.dispose();
		} catch (Exception e) {
			exceptionOccured = true;
			assertTrue(e.getMessage().contains(new EmptyGenreList().getMessage()));
		}
		assertTrue(exceptionOccured);
	}
	
	public List<User> generateUsers(){
		List<User> users = new ArrayList<User>();
		users.add(createRegisteredUser(1L, Rank.NONE));
		users.add(createRegisteredUser(2L, Rank.BRONZE));
		users.add(createRegisteredUser(3L, Rank.SILVER));
		users.add(createRegisteredUser(4L, Rank.GOLD));
		users.add(createAdministrator(5L));
		return users;
	}
	
	public static List<Offer> generateOffers(){
		List<Offer> offers = new ArrayList<Offer>();
		offers.add(new Offer(150, 1.0));
		offers.add(new Offer(400, 2.0));
		offers.add(new Offer(700, 3.0));
		return offers;
	}
	
	public RegisteredUser createRegisteredUser(Long id, Rank rank){
		RegisteredUser user = new RegisteredUser(
				id,
				"user" + id,
				"123",
				"e@gmail.com",
				LocalDateTime.parse(sdf2.format(new Date()),df2),
				0,
				0,
				rank,
				Rank.NONE,
				new ArrayList<Action>(),
				new ArrayList<Review>(),
				new ArrayList<Notification>(),
				new ArrayList<Film>(),
				new ArrayList<Purchase>(),
				30,
				null,
				true);
		return user;
	}
	
	public Administrator createAdministrator(Long id){
		Administrator user = new Administrator(
				id,
				"user" + id,
				"123",
				"e@gmail.com");
		return user;
	}
	
	public ActionEvent generateActionEvent(){
		ActionEvent actionEvent = new ActionEvent();
		try {
			actionEvent.setStartDate(LocalDate.parse(sdf.format(sdf.parse("2020-12-12")),df));
			actionEvent.setEndDate(LocalDate.parse(sdf.format(sdf.parse("2020-13-12")),df));
		} catch (ParseException e) {
			
		}
		actionEvent.setName("Action Event");
		return actionEvent;
	}
	
	public ActionEvent generateExpieredActionEvent(){
		ActionEvent actionEvent = new ActionEvent();
		actionEvent.setStartDate(LocalDate.parse(sdf.format(new Date(System.currentTimeMillis()-10000)),df));
		actionEvent.setEndDate(LocalDate.parse(sdf.format(new Date(System.currentTimeMillis()-1000)),df));
		actionEvent.setName("Action Event");
		return actionEvent;
	}
	
	public Discount generateDiscount(Rank rank, ActionEvent event, int amount){
		Discount discount = new Discount();
		discount.setAmount(amount);
		discount.setDescription("Discount action");
		discount.setTitleRank(rank);
		discount.setActionEvent(event);
		discount.getDiscountOffers().add(offers.get(2));
		discount.getDiscountOffers().add(offers.get(1));
		return discount;
	}
	
	public Discount generateInvalidDiscount(Rank rank, ActionEvent event){
		Discount discount = new Discount();
		discount.setAmount(DISCOUNT_VALID);
		discount.setDescription("Discount action");
		discount.setTitleRank(rank);
		discount.setActionEvent(event);
		return discount;
	}
	
	public FreeContent generateFreeContent(Rank rank, ActionEvent event){
		FreeContent freeContent = new FreeContent();
		freeContent.setDescription("FreeContent action");
		freeContent.setTitleRank(rank);
		freeContent.setActionEvent(event);
		freeContent.getFreeGenres().add(Genre.ACTION);
		freeContent.getFreeGenres().add(Genre.THRILLER);
		freeContent.getFreeGenres().add(Genre.HORROR);
		return freeContent;
	}
	
	public FreeContent generateInvalidFreeContent(Rank rank, ActionEvent event){
		FreeContent freeContent = new FreeContent();
		freeContent.setDescription("FreeContent action");
		freeContent.setTitleRank(rank);
		freeContent.setActionEvent(event);
		return freeContent;
	}
}
