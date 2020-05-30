package com.videoClub.model.drl;

import com.videoClub.model.Artist;

public class ArtistRateBadge extends Badge{

	private double averageRate;
	private Artist artist;
	private Integer favourites;

	public ArtistRateBadge( Artist artist, double averageRate, int favourites) {
		super();
		this.averageRate = averageRate;
		this.artist = artist;
		this.favourites = favourites;
	}

	public ArtistRateBadge() {
		super();
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
}
