package test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.videoClub.exception.FilmNotReviewed;
import com.videoClub.exception.InvalidParameters;
import com.videoClub.exception.InvalidRate;
import com.videoClub.exception.NotEnoughMinutes;
import com.videoClub.exception.ReviewNotRated;
import com.videoClub.model.Action;
import com.videoClub.model.ActionEvent;
import com.videoClub.model.Film;
import com.videoClub.model.FreeContent;
import com.videoClub.model.Notification;
import com.videoClub.model.Purchase;
import com.videoClub.model.RegisteredUser;
import com.videoClub.model.Review;
import com.videoClub.model.TimeInterval;
import com.videoClub.model.enumeration.Genre;
import com.videoClub.model.enumeration.Rank;

public class ReviewRulesTest {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static DateTimeFormatter df2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	/*Invalid TimeInterval test*/
	@Test
	public void test1(){
		RegisteredUser user = createRegisteredUser(1L, Rank.BRONZE);
		user.setAvailableMinutes(100);
		Film film = createFilm();
		Review review = new Review();
		review.setWatched(false);
		review.setFilm(film);
		review.setUser(user);
		review.getTimeIntervals().add(new TimeInterval(1L, -1, 5, review));
		user.getReviews().add(review);
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("reviewRulesSession");
		kieSession.insert(review.getTimeIntervals().get(0));
		kieSession.insert(user);
		boolean exceptionOccured = false;
		try {
			kieSession.fireAllRules();
		} catch (Exception e) {
			exceptionOccured = true;
			assertTrue(e.getMessage().contains(new InvalidParameters().getMessage()));
		}
		kieSession.dispose();
		assertTrue(exceptionOccured);
	}
	
	/*Review with free content action*/
	@Test
	public void test2(){
		ActionEvent actionEvent =  generateActionEvent();
		actionEvent.getActions().add(generateFreeContent(
				Rank.BRONZE, actionEvent));
		RegisteredUser user = createRegisteredUser(1L, Rank.BRONZE);
		user.setAvailableMinutes(100);
		user.getAction().add(actionEvent.getActions().get(0));
		Film film = createFilm();
		Review review = new Review();
		review.setWatched(false);
		review.setFilm(film);
		review.setUser(user);
		review.getTimeIntervals().add(new TimeInterval(1L, 0, 50, review));
		user.getReviews().add(review);
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("reviewRulesSession");
		kieSession.insert(review.getTimeIntervals().get(0));
		kieSession.insert(user);
		kieSession.fireAllRules();
		kieSession.dispose();
		
		assertEquals(100,user.getAvailableMinutes());
	}
	
	/*Review without free content action*/
	@Test
	public void test3(){
		ActionEvent actionEvent =  generateActionEvent();
		actionEvent.getActions().add(generateFreeContent2(
				Rank.BRONZE, actionEvent));
		RegisteredUser user = createRegisteredUser(1L, Rank.BRONZE);
		user.setAvailableMinutes(100);
		user.getAction().add(actionEvent.getActions().get(0));
		Film film = createFilm();
		Review review = new Review();
		review.setWatched(false);
		review.setFilm(film);
		review.setUser(user);
		review.getTimeIntervals().add(new TimeInterval(1L, 0, 50, review));
		user.getReviews().add(review);
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("reviewRulesSession");
		kieSession.insert(review.getTimeIntervals().get(0));
		kieSession.insert(user);
		kieSession.fireAllRules();
		kieSession.dispose();
		
		assertEquals(50,user.getAvailableMinutes());
	}
	
	/*Review without free content action when user do not have enough minutes*/
	@Test
	public void test4(){
		ActionEvent actionEvent =  generateActionEvent();
		actionEvent.getActions().add(generateFreeContent2(
				Rank.BRONZE, actionEvent));
		RegisteredUser user = createRegisteredUser(1L, Rank.BRONZE);
		user.setAvailableMinutes(10);
		user.getAction().add(actionEvent.getActions().get(0));
		Film film = createFilm();
		Review review = new Review();
		review.setWatched(false);
		review.setFilm(film);
		review.setUser(user);
		review.getTimeIntervals().add(new TimeInterval(1L, 0, 50, review));
		user.getReviews().add(review);
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("reviewRulesSession");
		kieSession.insert(review.getTimeIntervals().get(0));
		kieSession.insert(user);
		boolean exceptionOccured = false;
		try {
			kieSession.fireAllRules();
		} catch (Exception e) {
			exceptionOccured = true;
			assertTrue(e.getMessage().contains(new NotEnoughMinutes().getMessage()));
		}
		kieSession.dispose();
		assertTrue(exceptionOccured);
	}
	
	/*Review watched, 1 time interval test*/
	@Test
	public void test5(){
		RegisteredUser user = createRegisteredUser(1L, Rank.BRONZE);
		user.setAvailableMinutes(100);
		Film film = createFilm();
		Review review = new Review();
		review.setWatched(false);
		review.setFilm(film);
		review.setUser(user);
		review.getTimeIntervals().add(new TimeInterval(1L, 0, 90, review));
		user.getReviews().add(review);
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("reviewRulesSession");
		kieSession.insert(review.getTimeIntervals().get(0));
		kieSession.insert(user);
		kieSession.fireAllRules();
		kieSession.dispose();
		assertTrue(review.isWatched());
	}
	
	/*Review watched, 2 time intervals test*/
	@Test
	public void test6(){
		RegisteredUser user = createRegisteredUser(1L, Rank.BRONZE);
		user.setAvailableMinutes(100);
		Film film = createFilm();
		Review review = new Review();
		review.setWatched(false);
		review.setFilm(film);
		review.setUser(user);
		review.getTimeIntervals().add(new TimeInterval(1L, 0, 45, review));
		review.getTimeIntervals().add(new TimeInterval(2L, 45, 90, review));
		user.getReviews().add(review);
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("reviewRulesSession");
		kieSession.insert(review.getTimeIntervals().get(1));
		kieSession.insert(user);
		kieSession.fireAllRules();
		kieSession.dispose();
		assertTrue(review.isWatched());
	}
	
	/*Invalid Rate test 1*/
	@Test
	public void test7(){
		RegisteredUser user = createRegisteredUser(1L, Rank.BRONZE);
		user.setAvailableMinutes(100);
		Film film = createFilm();
		Review review = new Review();
		review.setWatched(false);
		review.setFilm(film);
		review.setUser(user);
		review.setRate(6);
		user.getReviews().add(review);
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("reviewRulesSession");
		kieSession.insert(review);
		boolean exceptionOccured = false;
		try {
			kieSession.fireAllRules();
		} catch (Exception e) {
			exceptionOccured = true;
			assertTrue(e.getMessage().contains(new InvalidRate().getMessage()));
		}
		assertTrue(exceptionOccured);
	}
	
	/*Invalid Rate test 2*/
	@Test
	public void test8(){
		RegisteredUser user = createRegisteredUser(1L, Rank.BRONZE);
		user.setAvailableMinutes(100);
		Film film = createFilm();
		Review review = new Review();
		review.setWatched(false);
		review.setFilm(film);
		review.setUser(user);
		review.setRate(-1);
		user.getReviews().add(review);
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("reviewRulesSession");
		kieSession.insert(review);
		boolean exceptionOccured = false;
		try {
			kieSession.fireAllRules();
		} catch (Exception e) {
			exceptionOccured = true;
			assertTrue(e.getMessage().contains(new InvalidRate().getMessage()));
		}
		assertTrue(exceptionOccured);
	}
	
	/*Review rated but not watched test*/
	@Test
	public void test9(){
		RegisteredUser user = createRegisteredUser(1L, Rank.BRONZE);
		user.setAvailableMinutes(100);
		Film film = createFilm();
		Review review = new Review();
		review.setWatched(false);
		review.setFilm(film);
		review.setUser(user);
		review.setRate(4);
		user.getReviews().add(review);
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("reviewRulesSession");
		kieSession.insert(review);
		boolean exceptionOccured = false;
		try {
			kieSession.fireAllRules();
		} catch (Exception e) {
			exceptionOccured = true;
			assertTrue(e.getMessage().contains(new FilmNotReviewed().getMessage()));
		}
		assertTrue(exceptionOccured);
	}
	
	/*Film added to favourites but not reviewed*/
	@Test
	public void test10(){
		RegisteredUser user = createRegisteredUser(1L, Rank.BRONZE);
		user.setAvailableMinutes(100);
		Film film = createFilm();
		Review review = new Review();
		user.getFavouriteFilms().add(film);
		review.setWatched(false);
		review.setFilm(film);
		review.setUser(user);
		review.setRate(0);
		user.getReviews().add(review);
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("reviewRulesSession");
		kieSession.insert(review);
		boolean exceptionOccured = false;
		try {
			kieSession.fireAllRules();
		} catch (Exception e) {
			exceptionOccured = true;
			assertTrue(e.getMessage().contains(new FilmNotReviewed().getMessage()));
		}
		assertTrue(exceptionOccured);
	}
	
	/*Film added to favourites but not rated 5*/
	@Test
	public void test11(){
		RegisteredUser user = createRegisteredUser(1L, Rank.BRONZE);
		user.setAvailableMinutes(100);
		Film film = createFilm();
		Review review = new Review();
		user.getFavouriteFilms().add(film);
		review.setWatched(true);
		review.setFilm(film);
		review.setUser(user);
		review.setRate(4);
		user.getReviews().add(review);
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("reviewRulesSession");
		kieSession.insert(review);
		boolean exceptionOccured = false;
		try {
			kieSession.fireAllRules();
		} catch (Exception e) {
			exceptionOccured = true;
			assertTrue(e.getMessage().contains(new ReviewNotRated().getMessage()));
		}
		assertTrue(exceptionOccured);
	}
	
	public Film createFilm(){
		Film film = new Film();
		film.setGenre(Genre.ACTION);
		film.setDuration(100);
		return film;
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
				true,
				null);
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
	
	public FreeContent generateFreeContent2(Rank rank, ActionEvent event){
		FreeContent freeContent = new FreeContent();
		freeContent.setDescription("FreeContent action");
		freeContent.setTitleRank(rank);
		freeContent.setActionEvent(event);
		freeContent.getFreeGenres().add(Genre.THRILLER);
		freeContent.getFreeGenres().add(Genre.HORROR);
		return freeContent;
	}
}
