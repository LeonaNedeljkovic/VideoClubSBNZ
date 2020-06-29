package com.videoClub.factory;

import java.util.List;

import com.videoClub.model.AgeClassifier;
import com.videoClub.model.enumeration.AgeCategory;

public class AgeClassifierFactory {

	private AgeClassifier childClassifier;
	private AgeClassifier teenClassifier;
	private AgeClassifier youngAdultClassifier;
	private AgeClassifier adultClassifier;
	private AgeClassifier elderClassifier;
	
	public AgeClassifierFactory(List<AgeClassifier> classifiers) {
		super();
		for(AgeClassifier ac : classifiers){
			if(ac.getAgeCategory().equals(AgeCategory.CHILD)){
				this.childClassifier = ac;
			}
			else if(ac.getAgeCategory().equals(AgeCategory.TEEN)){
				this.teenClassifier = ac;
			}
			else if(ac.getAgeCategory().equals(AgeCategory.YOUNG_ADULT)){
				this.youngAdultClassifier = ac;
			}
			else if(ac.getAgeCategory().equals(AgeCategory.ADULT)){
				this.adultClassifier = ac;
			}
			else{
				this.elderClassifier = ac;
			}
		}
	}

	public AgeClassifierFactory() {
		super();
	}
	
	public AgeCategory getAgeCategory(int age){
		if(age >= childClassifier.getStartAge() && age <= childClassifier.getEndAge()){
			return childClassifier.getAgeCategory();
		}
		else if(age >= teenClassifier.getStartAge() && age <= teenClassifier.getEndAge()){
			return teenClassifier.getAgeCategory();
		}
		else if(age >= youngAdultClassifier.getStartAge() && age <= youngAdultClassifier.getEndAge()){
			return youngAdultClassifier.getAgeCategory();
		}
		else if(age >= adultClassifier.getStartAge() && age <= adultClassifier.getEndAge()){
			return adultClassifier.getAgeCategory();
		}
		else{
			return elderClassifier.getAgeCategory();
		}
	}
}
