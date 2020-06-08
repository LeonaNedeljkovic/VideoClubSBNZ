package com.videoClub.model.drl;

import com.videoClub.model.Film;

public class RecommendedFilm {

	private Double recommendPoints;
	private Film film;
	private boolean analyzed;
	
	public RecommendedFilm(double recommendPoints, Film film) {
		super();
		this.recommendPoints = recommendPoints;
		this.film = film;
		analyzed = false;
	}

	public RecommendedFilm() {
		super();
	}

	public double getRecommendPoints() {
		return recommendPoints;
	}
	
	public Double getRecommendPointsDouble() {
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

	public boolean isAnalyzed() {
		return analyzed;
	}

	public void setAnalyzed(boolean analyzed) {
		this.analyzed = analyzed;
	}

	public void setRecommendPoints(Double recommendPoints) {
		this.recommendPoints = recommendPoints;
	}
}
