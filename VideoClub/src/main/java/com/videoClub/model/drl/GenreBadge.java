package com.videoClub.model.drl;

import com.videoClub.model.enumeration.Genre;

public class GenreBadge extends Badge {

	private Genre genre;
	private int watchedTime;
	private int unwatchedTime;
	private double averageRate;

	public GenreBadge(Genre genre, int watchedTime, int unwatchedTime, double averageRate) {
		super();
		this.genre = genre;
		this.watchedTime = watchedTime;
		this.unwatchedTime = unwatchedTime;
		this.averageRate = averageRate;
	}

	public GenreBadge() {
		super();
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public int getWatchedTime() {
		return watchedTime;
	}

	public void setWatchedTime(int watchedTime) {
		this.watchedTime = watchedTime;
	}

	public int getUnwatchedTime() {
		return unwatchedTime;
	}

	public void setUnwatchedTime(int unwatchedTime) {
		this.unwatchedTime = unwatchedTime;
	}

	public double getAverageRate() {
		return averageRate;
	}
	
	public Double getAverageRateDouble() {
		return averageRate;
	}

	public void setAverageRate(double averageRate) {
		this.averageRate = averageRate;
	}
}
