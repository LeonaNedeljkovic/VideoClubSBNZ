package com.videoClub.comparator;

import java.util.Comparator;

import com.videoClub.model.drl.AgeCategoryReport;

public class AgeCategoryReportComparator implements Comparator< AgeCategoryReport> {

	@Override
	public int compare(AgeCategoryReport o1, AgeCategoryReport o2) {
		if (o1.getUserRecommendationNumber() < o2.getUserRecommendationNumber()) return 1;
        if (o1.getUserRecommendationNumber() > o2.getUserRecommendationNumber()) return -1;
        return 0;
	}
	
	public int compareByPercentage(AgeCategoryReport o1, AgeCategoryReport o2) {
		if (o1.getPercentage() < o2.getPercentage()) return 1;
        if (o1.getPercentage() > o2.getPercentage()) return -1;
        return 0;
	}

}
