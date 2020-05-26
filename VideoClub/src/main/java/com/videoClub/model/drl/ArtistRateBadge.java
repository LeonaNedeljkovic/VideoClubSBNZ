package com.videoClub.model.drl;

import com.videoClub.model.Artist;
import com.videoClub.model.enumeration.ArtistRateRank;

public class ArtistRateBadge extends Badge{

	private ArtistRateRank artistRateRank;
	private double averageRate;
	private Artist artist;

	public ArtistRateBadge( ArtistRateRank artistRateRank, Artist artist, double averageRate) {
		super();
		this.artistRateRank = artistRateRank;
		this.averageRate = averageRate;
		this.artist = artist;
	}

	public ArtistRateBadge() {
		super();
	}

	public ArtistRateRank getArtistRateRank() {
		return artistRateRank;
	}

	public void setArtistRateRank(ArtistRateRank artistRateRank) {
		this.artistRateRank = artistRateRank;
	}

	public double getAverageRate() {
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
}
