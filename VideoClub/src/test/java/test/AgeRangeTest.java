package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.videoClub.exception.EmptyGenreList;
import com.videoClub.exception.InvalidAgeRange;
import com.videoClub.model.AgeClassifier;
import com.videoClub.model.enumeration.AgeCategory;

public class AgeRangeTest {
	
	@Test
	public void test1(){
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("ageRangeRulesSession");
		AgeClassifier child = new AgeClassifier(1L, 0, 6, AgeCategory.CHILD);
		AgeClassifier teen = new AgeClassifier(2L, 7, 15, AgeCategory.TEEN);
		AgeClassifier youngAdult = new AgeClassifier(3L, 16, 25, AgeCategory.YOUNG_ADULT);
		AgeClassifier adult = new AgeClassifier(4L, 26, 65, AgeCategory.ADULT);
		AgeClassifier elder = new AgeClassifier(5L, 20, 80, AgeCategory.ELDER);
		kieSession.insert(child);
		kieSession.insert(teen);
		kieSession.insert(youngAdult);
		kieSession.insert(adult);
		kieSession.insert(elder);
		boolean exceptionOccured = false;
		try {
			kieSession.fireAllRules();
			kieSession.dispose();
		} catch (Exception e) {
			exceptionOccured = true;
			assertTrue(e.getMessage().contains(new InvalidAgeRange().getMessage()));
		}
		assertTrue(exceptionOccured);
		
	}
	
	@Test
	public void test2(){
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("ageRangeRulesSession");
		AgeClassifier child = new AgeClassifier(1L, 0, 6, AgeCategory.CHILD);
		AgeClassifier teen = new AgeClassifier(2L, 7, 15, AgeCategory.TEEN);
		AgeClassifier youngAdult = new AgeClassifier(3L, 16, 25, AgeCategory.YOUNG_ADULT);
		AgeClassifier adult = new AgeClassifier(4L, 26, 65, AgeCategory.ADULT);
		AgeClassifier elder = new AgeClassifier(5L, 66, 80, AgeCategory.ELDER);
		kieSession.insert(child);
		kieSession.insert(teen);
		kieSession.insert(youngAdult);
		kieSession.insert(adult);
		kieSession.insert(elder);
		int num = kieSession.fireAllRules();
		assertEquals(0, num);
		kieSession.dispose();
		
	}
	
	@Test
	public void test3(){
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("ageRangeRulesSession");
		AgeClassifier child = new AgeClassifier(1L, 0, 20, AgeCategory.CHILD);
		AgeClassifier teen = new AgeClassifier(2L, 7, 15, AgeCategory.TEEN);
		AgeClassifier youngAdult = new AgeClassifier(3L, 16, 25, AgeCategory.YOUNG_ADULT);
		AgeClassifier adult = new AgeClassifier(4L, 26, 65, AgeCategory.ADULT);
		AgeClassifier elder = new AgeClassifier(5L, 20, 80, AgeCategory.ELDER);
		kieSession.insert(child);
		kieSession.insert(teen);
		kieSession.insert(youngAdult);
		kieSession.insert(adult);
		kieSession.insert(elder);
		boolean exceptionOccured = false;
		try {
			kieSession.fireAllRules();
			kieSession.dispose();
		} catch (Exception e) {
			exceptionOccured = true;
			assertTrue(e.getMessage().contains(new InvalidAgeRange().getMessage()));
		}
		assertTrue(exceptionOccured);
		
	}
	

}
