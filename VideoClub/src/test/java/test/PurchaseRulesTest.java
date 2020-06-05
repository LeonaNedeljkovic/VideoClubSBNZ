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

import com.videoClub.model.Action;
import com.videoClub.model.ActionEvent;
import com.videoClub.model.Discount;
import com.videoClub.model.Film;
import com.videoClub.model.Notification;
import com.videoClub.model.Offer;
import com.videoClub.model.Purchase;
import com.videoClub.model.RegisteredUser;
import com.videoClub.model.Review;
import com.videoClub.model.enumeration.Rank;

public class PurchaseRulesTest {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static DateTimeFormatter df2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	private static List<Offer> offers = generateOffers();
	
	@Test
	public void test1(){
		System.out.println("\nPurchase with discount:");
		ActionEvent actionEvent =  generateActionEvent();
		actionEvent.getActions().add(generateDiscount(
				Rank.SILVER, actionEvent, 50));
		
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("purchaseRulesSession");
		RegisteredUser user = createRegisteredUser(1L, Rank.SILVER);
		user.getAction().add(actionEvent.getActions().get(0));
		kieSession.insert(user);
		user.getPurchases().add(createPurchase(offers.get(2), user));
		kieSession.insert(user.getPurchases().get(0));
		kieSession.fireAllRules();
		kieSession.dispose();
		
		assertEquals(1,user.getPurchases().size());
		assertEquals(50,user.getPurchases().get(0).getDiscount());
		assertEquals(1.5,user.getPurchases().get(0).getPrice(), 0.001);
		assertEquals(offers.get(2).getMinutes(),user.getAvailableMinutes());
		assertEquals(offers.get(2).getPrice(),user.getImmunityPoints(), 0.001);
	}
	
	@Test
	public void test2(){
		System.out.println("\nPurchase with discount that is not for choosen offer:");
		ActionEvent actionEvent =  generateActionEvent();
		actionEvent.getActions().add(generateDiscount(
				Rank.SILVER, actionEvent, 50));
		
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("purchaseRulesSession");
		RegisteredUser user = createRegisteredUser(1L, Rank.SILVER);
		user.getAction().add(actionEvent.getActions().get(0));
		kieSession.insert(user);
		user.getPurchases().add(createPurchase(offers.get(0), user));
		kieSession.insert(user);
		kieSession.insert(user.getPurchases().get(0));
		kieSession.fireAllRules();
		kieSession.dispose();
		
		assertEquals(1,user.getPurchases().size());
		assertEquals(0,user.getPurchases().get(0).getDiscount());
		assertEquals(1.0,user.getPurchases().get(0).getPrice(), 0.001);
		assertEquals(offers.get(0).getMinutes(),user.getAvailableMinutes());
		assertEquals(offers.get(0).getPrice(),user.getImmunityPoints(), 0.001);
	}
	
	@Test
	public void test3(){
		System.out.println("\nPurchase without discount:");
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("purchaseRulesSession");
		RegisteredUser user = createRegisteredUser(1L, Rank.SILVER);
		user.getPurchases().add(createPurchase(offers.get(2), user));
		kieSession.insert(user.getPurchases().get(0));
		kieSession.insert(user);
		kieSession.fireAllRules();
		kieSession.dispose();
		
		assertEquals(1,user.getPurchases().size());
		assertEquals(0,user.getPurchases().get(0).getDiscount());
		assertEquals(3.0,user.getPurchases().get(0).getPrice(), 0.001);
		assertEquals(offers.get(2).getMinutes(),user.getAvailableMinutes());
		assertEquals(offers.get(2).getPrice(),user.getImmunityPoints(), 0.001);
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
				Rank.SILVER,
				new ArrayList<Action>(),
				new ArrayList<Review>(),
				new ArrayList<Notification>(),
				new ArrayList<Film>(),
				new ArrayList<Purchase>(),
				30,
				null);
		return user;
	}
	
	public Purchase createPurchase(Offer offer, RegisteredUser user){
		Purchase purchase = new Purchase(
				1L,
				LocalDateTime.parse(sdf2.format(new Date()),df2),
				0,
				offer.getPrice(),
				offer.getMinutes(),
				offer,
				user);
		return purchase;
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
}
