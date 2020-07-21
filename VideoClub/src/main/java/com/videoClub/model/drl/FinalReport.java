package com.videoClub.model.drl;

import java.util.ArrayList;
import java.util.List;

public class FinalReport {

	private int userRecommendationsNumber;
	private double percentage;
	private List<AgeCategoryReport> ageCategoryReports = new ArrayList<AgeCategoryReport>();
	
	public FinalReport(int userRecommendationsNumber, double percentage, List<AgeCategoryReport> ageCategoryReports) {
		super();
		this.userRecommendationsNumber = userRecommendationsNumber;
		this.percentage = percentage;
		this.ageCategoryReports = ageCategoryReports;
	}

	public FinalReport() {
		super();
	}

	public int getUserRecommendationsNumber() {
		return userRecommendationsNumber;
	}

	public void setUserRecommendationsNumber(int userRecommendationsNumber) {
		this.userRecommendationsNumber = userRecommendationsNumber;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public List<AgeCategoryReport> getAgeCategoryReports() {
		return ageCategoryReports;
	}

	public void setAgeCategoryReports(List<AgeCategoryReport> ageCategoryReports) {
		this.ageCategoryReports = ageCategoryReports;
	}
}
