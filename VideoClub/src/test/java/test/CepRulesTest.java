package test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import com.videoClub.event.LoggingEvent;
import com.videoClub.event.PurchaseEvent;
import com.videoClub.model.Action;
import com.videoClub.model.Film;
import com.videoClub.model.Notification;
import com.videoClub.model.Purchase;
import com.videoClub.model.RegisteredUser;
import com.videoClub.model.Review;
import com.videoClub.model.User;
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
	 * Testiramo sta ce se desiti ukoliko se korisnik pokusa ulogovati vise od 3
	 * puta neuspesno sa istim korisnickim imenom, a zatim bar jedan od ta 3
	 * dogadjaja istekne -> korisniku bi trebalo opet biti omoguceno logovanje
	 */

	@Test
	public void testSuccessfulLoginAfterDeletingFactFromWorkingMemory() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kc = ks.newKieClasspathContainer();
		KieSession ksession = kc.newKieSession("cepRulesSession");
		User u = new User(1L, "John", "Smith", "john.smith@email.com");
		u.setAllowedToLogIn(true);
		FactHandle factHandle = ksession.insert(new LoggingEvent(u, false));
		FactHandle factHandle2 = ksession.insert(new LoggingEvent(u, false));
		ksession.insert(new LoggingEvent(u, false));
		ksession.insert(new LoggingEvent(u, false));
		long ruleFireCount = ksession.fireAllRules();
		assertEquals(ruleFireCount, 1);
		assertEquals(false, u.getAllowedToLogIn());
		ksession.delete(factHandle);
		ksession.delete(factHandle2);
		ksession.insert(new LoggingEvent(u, true));
		ruleFireCount = ksession.fireAllRules();
		assertEquals(true, u.getAllowedToLogIn());

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
		RegisteredUser user = new RegisteredUser(1L, "user1", "123", "e@gmail.com",
				LocalDateTime.parse(sdf.format(new Date()), df), 0, 0, Rank.SILVER, Rank.SILVER,
				new ArrayList<Action>(), new ArrayList<Review>(), new ArrayList<Notification>(), new ArrayList<Film>(),
				new ArrayList<Purchase>(), 30, null, true,
				null);
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
		long ruleFireCount = ksession.fireAllRules();
		assertEquals(ruleFireCount, 1);
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
		RegisteredUser user = new RegisteredUser(1L, "user1", "123", "e@gmail.com",
				LocalDateTime.parse(sdf.format(new Date()), df), 0, 0, Rank.SILVER, Rank.SILVER,
				new ArrayList<Action>(), new ArrayList<Review>(), new ArrayList<Notification>(), new ArrayList<Film>(),
				new ArrayList<Purchase>(), 30, null, true,
				null);
		user.setAllowedToPurchase(false);
		Purchase purchase = new Purchase();
		ksession.insert(new PurchaseEvent(purchase, user));
		long ruleFireCount = ksession.fireAllRules();
		assertEquals(ruleFireCount, 1);
		assertEquals(true, user.getAllowedToPurchase());
	}

	/*
	 * Testiramo sta ce se desiti ukoliko je korisnik pokusao da obavi kupovinu
	 * nakon sto je iz radne memorije uklonjena bar jedna kupovina, nakon sto mu je
	 * bilo zabranjeno da kupuje
	 */
	@Test
	public void testSuccessfulPurchaseAfterDeletingFactsFromWorkingMemory() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kc = ks.newKieClasspathContainer();
		KieSession ksession = kc.newKieSession("cepPurchaseSession");
		RegisteredUser user = new RegisteredUser(1L, "user1", "123", "e@gmail.com",
				LocalDateTime.parse(sdf.format(new Date()), df), 0, 0, Rank.SILVER, Rank.SILVER,
				new ArrayList<Action>(), new ArrayList<Review>(), new ArrayList<Notification>(), new ArrayList<Film>(),
				new ArrayList<Purchase>(), 30, null, true,
				null);
		user.setAllowedToPurchase(true);
		Purchase purchase = new Purchase();
		FactHandle factHandle = ksession.insert(new PurchaseEvent(purchase, user));
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
		long ruleFireCount = ksession.fireAllRules();
		assertEquals(ruleFireCount, 1);
		assertEquals(false, user.getAllowedToPurchase());
		ksession.delete(factHandle);
		ruleFireCount = ksession.fireAllRules();
		assertEquals(true, user.getAllowedToPurchase());

	}

}
