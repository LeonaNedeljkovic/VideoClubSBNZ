package com.videoClub.model.drl;

import com.videoClub.model.enumeration.AgeCategory;

public class AgeCategoryReport {

	private AgeCategory ageCategory;
	private int userRecommendationNumber;
	private double percentage;
	
	public AgeCategoryReport(AgeCategory ageCategory, int userRecommendationNumber, double percentage) {
		super();
		this.ageCategory = ageCategory;
		this.userRecommendationNumber = userRecommendationNumber;
		this.percentage = percentage;
	}

	public AgeCategoryReport() {
		super();
	}

	public AgeCategory getAgeCategory() {
		return ageCategory;
	}

	public void setAgeCategory(AgeCategory ageCategory) {
		this.ageCategory = ageCategory;
	}

	public int getUserRecommendationNumber() {
		return userRecommendationNumber;
	}

	public void setUserRecommendationNumber(int userRecommendationNumber) {
		this.userRecommendationNumber = userRecommendationNumber;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
}
