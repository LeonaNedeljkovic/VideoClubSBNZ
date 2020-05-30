package com.videoClub.model.drl;

import com.videoClub.model.Artist;

public class ArtistReviewBadge extends Badge {

	private Artist artist;
	private int watchedNumber;
	private int unwatchedNumber;

	public ArtistReviewBadge(Artist artist, int watchedNumber, int unwatchedNumber) {
		super();
		this.artist = artist;
		this.watchedNumber = watchedNumber;
		this.unwatchedNumber = unwatchedNumber;
	}

	public ArtistReviewBadge() {
		super();
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
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
}
