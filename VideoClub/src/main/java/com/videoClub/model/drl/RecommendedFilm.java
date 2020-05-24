package com.videoClub.model.drl;

import com.videoClub.model.Film;

public class RecommendedFilm {

	private double recommendPoints;
	private Film film;
	
	public RecommendedFilm(double recommendPoints, Film film) {
		super();
		this.recommendPoints = recommendPoints;
		this.film = film;
	}

	public RecommendedFilm() {
		super();
	}

	public double getRecommendPoints() {
		return recommendPoints;
	}

	public void setRecommendPoints(double recommendPoints) {
		this.recommendPoints = recommendPoints;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}
}
