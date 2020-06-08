package test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.drools.core.time.SessionPseudoClock;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import com.videoClub.dto.ReportDTO;
import com.videoClub.event.FilmWatchEvent;
import com.videoClub.event.LoggingEvent;
import com.videoClub.event.PurchaseEvent;
import com.videoClub.model.Action;
import com.videoClub.model.Artist;
import com.videoClub.model.Film;
import com.videoClub.model.Notification;
import com.videoClub.model.Purchase;
import com.videoClub.model.RegisteredUser;
import com.videoClub.model.Review;
import com.videoClub.model.User;
import com.videoClub.model.enumeration.Genre;
import com.videoClub.model.enumeration.Rank;

public class CepRulesTest {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	/*
	 * Testiramo sta ce se desiti ukoliko se korisnik pokusa ulogovati vise od 3
	 * puta neuspesno sa istim korisnickim imenom
	 */

	@Test
	public void testUnsuccessfulLoginAfterThreeAtempts() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kc = ks.newKieClasspathContainer();
		KieSession ksession = kc.newKieSession("cepRulesSession");
		User u = new User(1L, "John", "Smith", "john.smith@email.com");
		u.setAllowedToLogIn(true);
		ksession.insert(new LoggingEvent(u, false));
		ksession.insert(new LoggingEvent(u, false));
		ksession.insert(new LoggingEvent(u, false));
		ksession.insert(new LoggingEvent(u, false));
		long ruleFireCount = ksession.fireAllRules();
		assertEquals(ruleFireCount, 1);
		assertEquals(false, u.getAllowedToLogIn());
	}


	/*
	 * Testiramo sta ce se desiti ukoliko je korisniku ostalo da mu je nedozvoljeno
	 * logovanje a u radnoj memoriji nema neuspesnih pokusaja logovanja za njega
	 * Rezultat bi trebao da bude da je logovanje ponovo omoguceno za korisnika
	 */

	@Test
	public void testSuccessfulLoginWhenNoUnsuccessfulLoggingEventsInWorkingMemory() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kc = ks.newKieClasspathContainer();
		KieSession ksession = kc.newKieSession("cepRulesSession");
		User u = new User(1L, "John", "Smith", "john.smith@email.com");
		u.setAllowedToLogIn(false);
		ksession.insert(new LoggingEvent(u, true));
		long ruleFireCount = ksession.fireAllRules();
		assertEquals(ruleFireCount, 1);
		assertEquals(true, u.getAllowedToLogIn());
	}


	/*
	 * Testiramo sta ce se desiti ukoliko korisnik bude imao vise od 10 kupovina u
	 * jednom satu
	 */

	@Test
	public void testTooManyPurchasesFromSameUserIn1Hour() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kc = ks.newKieClasspathContainer();
		KieSession ksession = kc.newKieSession("cepPurchaseSession");
		RegisteredUser user = new RegisteredUser();
		user.setId(1L);
		user.setAllowedToPurchase(true);
		Purchase purchase = new Purchase();
		ksession.insert(new PurchaseEvent(purchase, user));
		ksession.insert(new PurchaseEvent(purchase, user));
		ksession.insert(new PurchaseEvent(purchase, user));
		ksession.insert(new PurchaseEvent(purchase, user));
		ksession.insert(new PurchaseEvent(purchase, user));
		ksession.insert(new PurchaseEvent(purchase, user));
		ksession.insert(new PurchaseEvent(purchase, user));
		ksession.insert(new PurchaseEvent(purchase, user));
		ksession.insert(new PurchaseEvent(purchase, user));
		ksession.insert(new PurchaseEvent(purchase, user));
		ksession.insert(new PurchaseEvent(purchase, user));
		ksession.fireAllRules();
		assertEquals(false, user.getAllowedToPurchase());
	}

	/*
	 * Testiramo sta ce se desiti ukoliko je korisniku ostalo da su mu nedozvoljene
	 * kupovine a u radnoj memoriji nema pokusaja kupovina za njega Rezultat bi
	 * trebao da bude da su kupovine ponovo omogucene za korisnika
	 */

	@Test
	public void testSuccessfulPurchaseAfterBeingUnableTo() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kc = ks.newKieClasspathContainer();
		KieSession ksession = kc.newKieSession("cepPurchaseSession");
		RegisteredUser user = new RegisteredUser();
		user.setId(1L);
		user.setAllowedToPurchase(false);
		Purchase purchase = new Purchase();
		ksession.insert(new PurchaseEvent(purchase, user));
		long ruleFireCount = ksession.fireAllRules();
		assertEquals(ruleFireCount, 1);
		assertEquals(true, user.getAllowedToPurchase());
	}
	
	
	//Testiranje purchase eventa sa pseudo clockom. Prvo kupovina vise od 10 u jednom satu, korisniku se zabranjuje da obavlja kupovine
	//zatim pseudo clockom uvecamo vreme tako da ako korisnik pokusa ponovo nesto da kupi uspeva
	@Test
	public void test_cepPurchaseWithPseudoClock() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kc = ks.newKieClasspathContainer();
		KieSession ksession = kc.newKieSession("cepPurchaseKsessionPseudoClock");
		SessionPseudoClock clock = ksession.getSessionClock();
		RegisteredUser user = new RegisteredUser();
		user.setId(1L);
		user.setAllowedToPurchase(true);
		Purchase purchase = new Purchase();
		ksession.insert(new PurchaseEvent(purchase, user));
		ksession.insert(new PurchaseEvent(purchase, user));
		ksession.insert(new PurchaseEvent(purchase, user));
		ksession.insert(new PurchaseEvent(purchase, user));
		ksession.insert(new PurchaseEvent(purchase, user));
		ksession.insert(new PurchaseEvent(purchase, user));
		ksession.insert(new PurchaseEvent(purchase, user));
		ksession.insert(new PurchaseEvent(purchase, user));
		ksession.insert(new PurchaseEvent(purchase, user));
		ksession.insert(new PurchaseEvent(purchase, user));
		ksession.insert(new PurchaseEvent(purchase, user));
		ksession.fireAllRules();
		assertEquals(false, user.getAllowedToPurchase());
		clock.advanceTime(2, TimeUnit.HOURS);
		ksession.insert(new PurchaseEvent(purchase, user));
		ksession.fireAllRules();
		assertEquals(true, user.getAllowedToPurchase());
		
	}
	
	
	//Preko pseudo clocka testiramo izvestaje da vidimo rezultat u prethodnih 24h
	//nakon sto prodje jos2 24h prethodni podaci se brisu
	@Test
	public void test_cepReportRules() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kc = ks.newKieClasspathContainer();
		KieSession ksession = kc.newKieSession("cepConfigKsessionPseudoClock");
		SessionPseudoClock clock = ksession.getSessionClock();
		Purchase purchase = new Purchase();
		purchase.setPrice(100);
		PurchaseEvent purchaseEvent1 = new PurchaseEvent(purchase,new RegisteredUser());
		PurchaseEvent purchaseEvent2 = new PurchaseEvent(purchase,new RegisteredUser());
		PurchaseEvent purchaseEvent3 = new PurchaseEvent(purchase,new RegisteredUser());
		ksession.insert(purchaseEvent1);
		ksession.insert(purchaseEvent2);
		ksession.insert(purchaseEvent3);
		ReportDTO reportDto = new ReportDTO(0.0,LocalDate.now(),null,0L);
		ksession.insert(reportDto);
		ksession.fireAllRules();
		assertEquals(300.0,reportDto.getEarned(),0);
		clock.advanceTime(23, TimeUnit.HOURS);
		ksession.fireAllRules();
		assertEquals(300.0,reportDto.getEarned(),0);
		clock.advanceTime(1, TimeUnit.HOURS);
		ksession.fireAllRules();
		assertEquals(0.0,reportDto.getEarned(),0);
	}
	
	//Testiramo uspesnost pravila koje testira filmove po gledansoti
	//trebalo bi da u reportDto.film smesti najgledaniji film u prethodna 24h
	//u reportDto.numberOfViews da smesti ukupan broj pregleda u 24h
	//a u reportDto.earned ukupnu zaradu u prethodnih 24h
	@Test
	public void test_cepReportRulesTestMovieSorting() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kc = ks.newKieClasspathContainer();
		KieSession ksession = kc.newKieSession("cepConfigKsessionPseudoClock");
		SessionPseudoClock clock = ksession.getSessionClock();
		Purchase purchase = new Purchase();
		purchase.setPrice(100);
		PurchaseEvent purchaseEvent1 = new PurchaseEvent(purchase,new RegisteredUser());
		PurchaseEvent purchaseEvent2 = new PurchaseEvent(purchase,new RegisteredUser());
		PurchaseEvent purchaseEvent3 = new PurchaseEvent(purchase,new RegisteredUser());
		Film film = new Film(new Long(1),"Film1","Description",Genre.ACTION,200,2019,0.0,"poster",null,new Artist(),new Artist(),null,null);
		Film film2 = new Film(new Long(2),"Film2","Description",Genre.ACTION,200,2019,0.0,"poster",null,new Artist(),new Artist(),null,null);
		ksession.insert(purchaseEvent1);
		ksession.insert(purchaseEvent2);
		ksession.insert(purchaseEvent3);
		ksession.insert(new FilmWatchEvent(film));
		ksession.insert(new FilmWatchEvent(film));
		ksession.insert(new FilmWatchEvent(film));
		ksession.insert(new FilmWatchEvent(film2));
		ksession.insert(new FilmWatchEvent(film2));
		ReportDTO reportDto = new ReportDTO();
		reportDto.setDate(LocalDate.now());
		ksession.insert(reportDto);
		ksession.fireAllRules();
		assertEquals(300.0,reportDto.getEarned(),0);
		assertEquals("Film1",reportDto.getFilm().getName());
		assertEquals(new Long(5) ,reportDto.getNumberOfViews());
		clock.advanceTime(23, TimeUnit.HOURS);
		ksession.fireAllRules();
		assertEquals(300.0,reportDto.getEarned(),0);
		assertEquals("Film1",reportDto.getFilm().getName());
		assertEquals(new Long(5) ,reportDto.getNumberOfViews());
		clock.advanceTime(1, TimeUnit.HOURS);
		ksession.fireAllRules();
		assertEquals(0.0,reportDto.getEarned(),0);
		assertEquals(new Long(0) ,reportDto.getNumberOfViews());
	}

}
