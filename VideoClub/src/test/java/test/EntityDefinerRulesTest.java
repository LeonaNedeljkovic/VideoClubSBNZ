package test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.videoClub.bean.BronzeImmunity;
import com.videoClub.bean.BronzeTitle;
import com.videoClub.bean.GoldImmunity;
import com.videoClub.bean.GoldTitle;
import com.videoClub.bean.SilverImmunity;
import com.videoClub.bean.SilverTitle;
import com.videoClub.exception.InvalidImmunity;
import com.videoClub.exception.InvalidTitle;

public class EntityDefinerRulesTest {

	/* Test setting valid amount of acquire points for bronze immunity */
	@Test
	public void test1(){
		KieSession kieSession = initializeKieSession();
		kieSession.insert(new BronzeImmunity(15));
		kieSession.fireAllRules();
		kieSession.dispose();
	}
	
	/* Test setting high amount of acquire points for bronze immunity */
	@Test
	public void test2(){
		KieSession kieSession = initializeKieSession();
		kieSession.insert(new BronzeImmunity(30));
		boolean exceptionOccured = false;
		try {
			kieSession.fireAllRules();
			kieSession.dispose();
		} catch (Exception e) {
			exceptionOccured = true;
			assertTrue(e.getMessage().contains(new InvalidImmunity().getMessage()));
		}
		assertTrue(exceptionOccured);
	}
	
	/* Test setting low amount of acquire points for bronze immunity */
	@Test
	public void test3(){
		KieSession kieSession = initializeKieSession();
		kieSession.insert(new BronzeImmunity(1));
		boolean exceptionOccured = false;
		try {
			kieSession.fireAllRules();
			kieSession.dispose();
		} catch (Exception e) {
			exceptionOccured = true;
			assertTrue(e.getMessage().contains(new InvalidImmunity().getMessage()));
		}
		assertTrue(exceptionOccured);
	}
	
	/* Test setting valid amount of acquire points for silver immunity */
	@Test
	public void test4(){
		KieSession kieSession = initializeKieSession();
		kieSession.insert(new SilverImmunity(25));
		kieSession.fireAllRules();
		kieSession.dispose();
	}
	
	/* Test setting high amount of acquire points for silver immunity */
	@Test
	public void test5(){
		KieSession kieSession = initializeKieSession();
		kieSession.insert(new SilverImmunity(50));
		boolean exceptionOccured = false;
		try {
			kieSession.fireAllRules();
			kieSession.dispose();
		} catch (Exception e) {
			exceptionOccured = true;
			assertTrue(e.getMessage().contains(new InvalidImmunity().getMessage()));
		}
		assertTrue(exceptionOccured);
	}
	
	/* Test setting low amount of acquire points for silver immunity */
	@Test
	public void test6(){
		KieSession kieSession = initializeKieSession();
		kieSession.insert(new SilverImmunity(10));
		boolean exceptionOccured = false;
		try {
			kieSession.fireAllRules();
			kieSession.dispose();
		} catch (Exception e) {
			exceptionOccured = true;
			assertTrue(e.getMessage().contains(new InvalidImmunity().getMessage()));
		}
		assertTrue(exceptionOccured);
	}
	
	/* Test setting valid amount of acquire points for gold immunity */
	@Test
	public void test7(){
		KieSession kieSession = initializeKieSession();
		kieSession.insert(new GoldImmunity(50));
		kieSession.fireAllRules();
		kieSession.dispose();
	}
	
	/* Test setting low amount of acquire points for gold immunity */
	@Test
	public void test8(){
		KieSession kieSession = initializeKieSession();
		kieSession.insert(new GoldImmunity(20));
		boolean exceptionOccured = false;
		try {
			kieSession.fireAllRules();
			kieSession.dispose();
		} catch (Exception e) {
			exceptionOccured = true;
			assertTrue(e.getMessage().contains(new InvalidImmunity().getMessage()));
		}
		assertTrue(exceptionOccured);
	}
	
	/* Test setting high amount of acquire points for gold immunity */
	@Test
	public void test9(){
		KieSession kieSession = initializeKieSession();
		kieSession.insert(new GoldImmunity(1001));
		boolean exceptionOccured = false;
		try {
			kieSession.fireAllRules();
			kieSession.dispose();
		} catch (Exception e) {
			exceptionOccured = true;
			assertTrue(e.getMessage().contains(new InvalidImmunity().getMessage()));
		}
		assertTrue(exceptionOccured);
	}
	
	/* Test invalid acquire points of bronze title */
	@Test
	public void test10(){
		KieSession kieSession = initializeKieSession();
		kieSession.insert(new BronzeTitle(200, 50, 100));
		boolean exceptionOccured = false;
		try {
			kieSession.fireAllRules();
			kieSession.dispose();
		} catch (Exception e) {
			exceptionOccured = true;
			assertTrue(e.getMessage().contains(new InvalidTitle().getMessage()));
		}
		assertTrue(exceptionOccured);
	}
	
	/* Test invalid acquire points of bronze title */
	@Test
	public void test11(){
		KieSession kieSession = initializeKieSession();
		kieSession.insert(new BronzeTitle(1, 50, 100));
		boolean exceptionOccured = false;
		try {
			kieSession.fireAllRules();
			kieSession.dispose();
		} catch (Exception e) {
			exceptionOccured = true;
			assertTrue(e.getMessage().contains(new InvalidTitle().getMessage()));
		}
		assertTrue(exceptionOccured);
	}
	
	/* Test invalid save points of bronze title */
	@Test
	public void test12(){
		KieSession kieSession = initializeKieSession();
		kieSession.insert(new BronzeTitle(100, 100, 100));
		boolean exceptionOccured = false;
		try {
			kieSession.fireAllRules();
			kieSession.dispose();
		} catch (Exception e) {
			exceptionOccured = true;
			assertTrue(e.getMessage().contains(new InvalidTitle().getMessage()));
		}
		assertTrue(exceptionOccured);
	}
	
	/* Test invalid reward points of bronze title */
	@Test
	public void test13(){
		KieSession kieSession = initializeKieSession();
		kieSession.insert(new BronzeTitle(100, 50, 200));
		boolean exceptionOccured = false;
		try {
			kieSession.fireAllRules();
			kieSession.dispose();
		} catch (Exception e) {
			exceptionOccured = true;
			assertTrue(e.getMessage().contains(new InvalidTitle().getMessage()));
		}
		assertTrue(exceptionOccured);
	}
	
	/* Test invalid acquire points of silver title */
	@Test
	public void test14(){
		KieSession kieSession = initializeKieSession();
		kieSession.insert(new SilverTitle(100, 100, 200));
		boolean exceptionOccured = false;
		try {
			kieSession.fireAllRules();
			kieSession.dispose();
		} catch (Exception e) {
			exceptionOccured = true;
			assertTrue(e.getMessage().contains(new InvalidTitle().getMessage()));
		}
		assertTrue(exceptionOccured);
	}
	
	/* Test invalid acquire points of silver title */
	@Test
	public void test15(){
		KieSession kieSession = initializeKieSession();
		kieSession.insert(new SilverTitle(300, 100, 200));
		boolean exceptionOccured = false;
		try {
			kieSession.fireAllRules();
			kieSession.dispose();
		} catch (Exception e) {
			exceptionOccured = true;
			assertTrue(e.getMessage().contains(new InvalidTitle().getMessage()));
		}
		assertTrue(exceptionOccured);
	}
	
	/* Test invalid save points of silver title */
	@Test
	public void test16(){
		KieSession kieSession = initializeKieSession();
		kieSession.insert(new SilverTitle(200, 200, 200));
		boolean exceptionOccured = false;
		try {
			kieSession.fireAllRules();
			kieSession.dispose();
		} catch (Exception e) {
			exceptionOccured = true;
			assertTrue(e.getMessage().contains(new InvalidTitle().getMessage()));
		}
		assertTrue(exceptionOccured);
	}
	
	/* Test invalid reward points of silver title */
	@Test
	public void test17(){
		KieSession kieSession = initializeKieSession();
		kieSession.insert(new SilverTitle(200, 100, 300));
		boolean exceptionOccured = false;
		try {
			kieSession.fireAllRules();
			kieSession.dispose();
		} catch (Exception e) {
			exceptionOccured = true;
			assertTrue(e.getMessage().contains(new InvalidTitle().getMessage()));
		}
		assertTrue(exceptionOccured);
	}
	
	/* Test invalid acquire points of gold title */
	@Test
	public void test18(){
		KieSession kieSession = initializeKieSession();
		kieSession.insert(new GoldTitle(10001, 150, 300));
		boolean exceptionOccured = false;
		try {
			kieSession.fireAllRules();
			kieSession.dispose();
		} catch (Exception e) {
			exceptionOccured = true;
			assertTrue(e.getMessage().contains(new InvalidTitle().getMessage()));
		}
		assertTrue(exceptionOccured);
	}
	
	/* Test invalid acquire points of gold title */
	@Test
	public void test19(){
		KieSession kieSession = initializeKieSession();
		kieSession.insert(new GoldTitle(200, 150, 300));
		boolean exceptionOccured = false;
		try {
			kieSession.fireAllRules();
			kieSession.dispose();
		} catch (Exception e) {
			exceptionOccured = true;
			assertTrue(e.getMessage().contains(new InvalidTitle().getMessage()));
		}
		assertTrue(exceptionOccured);
	}
	
	/* Test invalid save points of gold title */
	@Test
	public void test20(){
		KieSession kieSession = initializeKieSession();
		kieSession.insert(new GoldTitle(300, 100, 300));
		boolean exceptionOccured = false;
		try {
			kieSession.fireAllRules();
			kieSession.dispose();
		} catch (Exception e) {
			exceptionOccured = true;
			assertTrue(e.getMessage().contains(new InvalidTitle().getMessage()));
		}
		assertTrue(exceptionOccured);
	}
	
	/* Test invalid reward points of gold title */
	@Test
	public void test21(){
		KieSession kieSession = initializeKieSession();
		kieSession.insert(new GoldTitle(300, 150, 601));
		boolean exceptionOccured = false;
		try {
			kieSession.fireAllRules();
			kieSession.dispose();
		} catch (Exception e) {
			exceptionOccured = true;
			assertTrue(e.getMessage().contains(new InvalidTitle().getMessage()));
		}
		assertTrue(exceptionOccured);
	}
	
	/* Test valid bronze title */
	@Test
	public void test22(){
		KieSession kieSession = initializeKieSession();
		kieSession.insert(new BronzeTitle(120, 70, 120));
		kieSession.fireAllRules();
		kieSession.dispose();
	}
	
	/* Test valid silver title */
	@Test
	public void test23(){
		KieSession kieSession = initializeKieSession();
		kieSession.insert(new SilverTitle(220, 110, 220));
		kieSession.fireAllRules();
		kieSession.dispose();
	}
	
	/* Test valid gold title */
	@Test
	public void test24(){
		KieSession kieSession = initializeKieSession();
		kieSession.insert(new GoldTitle(320, 210, 320));
		kieSession.fireAllRules();
		kieSession.dispose();
	}
	
	public KieSession initializeKieSession(){
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("entityDefinerRulesSession");
		kieSession.setGlobal("bronzeImmunity", generateBronzeImmunity());
		kieSession.setGlobal("silverImmunity", generateSilverImmunity());
		kieSession.setGlobal("goldImmunity", generateGoldImmunity());
		kieSession.setGlobal("bronzeTitle", generateBronzeTitle());
		kieSession.setGlobal("silverTitle", generateSilverTitle());
		kieSession.setGlobal("goldTitle", generateGoldTitle());
		return kieSession;
	}
	
	public BronzeImmunity generateBronzeImmunity(){
		return new BronzeImmunity(10);
	}
	
	public SilverImmunity generateSilverImmunity(){
		return new SilverImmunity(20);
	}

	public GoldImmunity generateGoldImmunity(){
		return new GoldImmunity(30);
	}
	
	
	public BronzeTitle generateBronzeTitle(){
		return new BronzeTitle(100, 50, 100);
	}
	
	public SilverTitle generateSilverTitle(){
		return new SilverTitle(200, 100, 200);
	}

	public GoldTitle generateGoldTitle(){
		return new GoldTitle(300, 150, 300);
	}
}
