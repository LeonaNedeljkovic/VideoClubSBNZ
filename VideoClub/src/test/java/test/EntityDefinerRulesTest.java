package test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.videoClub.bean.Immunity;
import com.videoClub.bean.Title;
import com.videoClub.exception.InvalidImmunity;
import com.videoClub.exception.InvalidTitle;
import com.videoClub.model.enumeration.Rank;

public class EntityDefinerRulesTest {

	/* Test setting valid amount of acquire points for bronze immunity */
	@Test
	public void test1(){
		KieSession kieSession = initializeKieSession();
		kieSession.insert(new Immunity(15, Rank.BRONZE));
		kieSession.fireAllRules();
		kieSession.dispose();
	}
	
	/* Test setting high amount of acquire points for bronze immunity */
	@Test
	public void test2(){
		KieSession kieSession = initializeKieSession();
		kieSession.insert(new Immunity(30, Rank.BRONZE));
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
		kieSession.insert(new Immunity(1, Rank.BRONZE));
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
		kieSession.insert(new Immunity(25, Rank.SILVER));
		kieSession.fireAllRules();
		kieSession.dispose();
	}
	
	/* Test setting high amount of acquire points for silver immunity */
	@Test
	public void test5(){
		KieSession kieSession = initializeKieSession();
		kieSession.insert(new Immunity(50, Rank.SILVER));
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
		kieSession.insert(new Immunity(10, Rank.SILVER));
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
		kieSession.insert(new Immunity(50, Rank.GOLD));
		kieSession.fireAllRules();
		kieSession.dispose();
	}
	
	/* Test setting low amount of acquire points for gold immunity */
	@Test
	public void test8(){
		KieSession kieSession = initializeKieSession();
		kieSession.insert(new Immunity(20, Rank.GOLD));
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
		kieSession.insert(new Immunity(1001, Rank.GOLD));
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
		kieSession.insert(new Title(200, 50, 100, Rank.BRONZE));
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
		kieSession.insert(new Title(1, 50, 100, Rank.BRONZE));
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
		kieSession.insert(new Title(100, 100, 100, Rank.BRONZE));
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
		kieSession.insert(new Title(100, 50, 200, Rank.BRONZE));
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
		kieSession.insert(new Title(100, 100, 200, Rank.SILVER));
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
		kieSession.insert(new Title(300, 100, 200, Rank.SILVER));
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
		kieSession.insert(new Title(200, 200, 200, Rank.SILVER));
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
		kieSession.insert(new Title(200, 100, 300, Rank.SILVER));
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
		kieSession.insert(new Title(10001, 150, 300, Rank.GOLD));
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
		kieSession.insert(new Title(200, 150, 300, Rank.GOLD));
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
		kieSession.insert(new Title(300, 100, 300, Rank.GOLD));
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
		kieSession.insert(new Title(300, 150, 601, Rank.GOLD));
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
		kieSession.insert(new Title(120, 70, 120, Rank.BRONZE));
		kieSession.fireAllRules();
		kieSession.dispose();
	}
	
	/* Test valid silver title */
	@Test
	public void test23(){
		KieSession kieSession = initializeKieSession();
		kieSession.insert(new Title(220, 110, 220, Rank.SILVER));
		kieSession.fireAllRules();
		kieSession.dispose();
	}
	
	/* Test valid gold title */
	@Test
	public void test24(){
		KieSession kieSession = initializeKieSession();
		kieSession.insert(new Title(320, 210, 320, Rank.GOLD));
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
	
	public Immunity generateBronzeImmunity(){
		return new Immunity(10, Rank.BRONZE);
	}
	
	public Immunity generateSilverImmunity(){
		return new Immunity(20, Rank.SILVER);
	}

	public Immunity generateGoldImmunity(){
		return new Immunity(30, Rank.GOLD);
	}
	
	
	public Title generateBronzeTitle(){
		return new Title(100, 50, 100, Rank.BRONZE);
	}
	
	public Title generateSilverTitle(){
		return new Title(200, 100, 200, Rank.SILVER);
	}

	public Title generateGoldTitle(){
		return new Title(300, 150, 300, Rank.GOLD);
	}
}
