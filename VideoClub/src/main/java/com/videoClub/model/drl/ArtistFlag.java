package com.videoClub.model.drl;

import com.videoClub.model.Artist;

public class ArtistFlag {

	private Artist artist;
	private double averageRate;
	private Integer favourites;
	private int watchedNumber;
	private int unwatchedNumber;
	private int ratesNumber;
	
	public ArtistFlag() {
		super();
	}

	public ArtistFlag(Artist artist, double averageRate, int favourites,
			int watchedNumber, int unwatchedNumber, int ratesNumber) {
		super();
		this.artist = artist;
		this.averageRate = averageRate;
		this.favourites = favourites;
		this.watchedNumber = watchedNumber;
		this.unwatchedNumber = unwatchedNumber;
		this.ratesNumber = ratesNumber;
	}
	
	public ArtistFlag(Artist artist, int watchedNumber, int unwatchedNumber) {
		super();
		this.artist = artist;
		this.watchedNumber = watchedNumber;
		this.unwatchedNumber = unwatchedNumber;
	}
	
	public ArtistFlag(Artist artist, double averageRate, int favourites) {
		super();
		this.artist = artist;
		this.averageRate = averageRate;
		this.favourites = favourites;
	}

	public double getAverageRate() {
		return averageRate;
	}
	
	public Double getAverageDoubleRate() {
		return averageRate;
	}

	public void setAverageRate(double averageRate) {
		this.averageRate = averageRate;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public Integer getFavourites() {
		return favourites;
	}

	public void setFavourites(Integer favourites) {
		this.favourites = favourites;
	}
	
	public int getWatchedNumber() {
		return watchedNumber;
	}
	
	public Integer getWatchedNumberInteger() {
		return watchedNumber;
	}
	
	public Integer getUnwatchedNumberInteger() {
		return unwatchedNumber;
	}

	public void setWatchedNumber(int watchedNumber) {
		this.watchedNumber = watchedNumber;
	}

	public int getUnwatchedNumber() {
		return unwatchedNumber;
	}

	public void setUnwatchedNumber(int unwatchedNumber) {
		this.unwatchedNumber = unwatchedNumber;
	}

	public int getRatesNumber() {
		return ratesNumber;
	}
	
	public Double getRatesNumberXRating() {
		return ratesNumber*averageRate;
	}

	public void setRatesNumber(int ratesNumber) {
		this.ratesNumber = ratesNumber;
	}
}
