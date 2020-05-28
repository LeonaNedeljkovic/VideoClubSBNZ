package com.videoClub.model.drl;

import com.videoClub.model.Artist;

public class ArtistReviewBadge extends Badge {

	private Artist artist;
	private Integer watchedNumber;
	private Integer unwatchedNumber;

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

	public Integer getWatchedNumber() {
		return watchedNumber;
	}

	public void setWatchedNumber(int watchedNumber) {
		this.watchedNumber = watchedNumber;
	}

	public Integer getUnwatchedNumber() {
		return unwatchedNumber;
	}

	public void setUnwatchedNumber(int unwatchedNumber) {
		this.unwatchedNumber = unwatchedNumber;
	}
}
