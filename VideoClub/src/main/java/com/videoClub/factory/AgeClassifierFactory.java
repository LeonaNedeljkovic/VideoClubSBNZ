package com.videoClub.factory;

import com.videoClub.model.AgeClassifier;
import com.videoClub.model.enumeration.AgeCategory;

public class AgeClassifierFactory {

	private AgeClassifier childClassifier;
	private AgeClassifier teenClassifier;
	private AgeClassifier youngAdultClassifier;
	private AgeClassifier adultClassifier;
	private AgeClassifier elderClassifier;
	
	public AgeClassifierFactory(AgeClassifier childClassifier, AgeClassifier teenClassifier,
			AgeClassifier youngAdultClassifier, AgeClassifier adultClassifier, AgeClassifier elderClassifier) {
		super();
		this.childClassifier = childClassifier;
		this.teenClassifier = teenClassifier;
		this.youngAdultClassifier = youngAdultClassifier;
		this.adultClassifier = adultClassifier;
		this.elderClassifier = elderClassifier;
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
