package com.videoClub.comparator;

import java.util.Comparator;

import com.videoClub.model.drl.RecommendedFilm;

public class RecommendedFilmComparator implements Comparator<RecommendedFilm>{

	@Override
	public int compare(RecommendedFilm o1, RecommendedFilm o2) {
		if (o1.getRecommendPoints() < o2.getRecommendPoints()) return 1;
        if (o1.getRecommendPoints() > o2.getRecommendPoints()) return -1;
        return 0;
	}

}
