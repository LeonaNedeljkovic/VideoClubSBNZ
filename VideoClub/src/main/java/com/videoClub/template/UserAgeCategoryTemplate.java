package com.videoClub.template;

import com.videoClub.model.enumeration.AgeCategory;

public class UserAgeCategoryTemplate {

	private int minAge;
	private int maxAge;
	private AgeCategory ageCategory;
	
	public UserAgeCategoryTemplate(int minAge, int maxAge, AgeCategory ageCategory) {
		super();
		this.minAge = minAge;
		this.maxAge = maxAge;
		this.ageCategory = ageCategory;
	}

	public UserAgeCategoryTemplate() {
		super();
	}

	public int getMinAge() {
		return minAge;
	}

	public void setMinAge(int minAge) {
		this.minAge = minAge;
	}

	public int getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}

	public AgeCategory getAgeCategory() {
		return ageCategory;
	}

	public void setAgeCategory(AgeCategory ageCategory) {
		this.ageCategory = ageCategory;
	}
	
}
