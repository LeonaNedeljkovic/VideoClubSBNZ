package com.videoClub.model.drl;

import com.videoClub.model.Artist;
import com.videoClub.model.enumeration.ArtistReviewRank;

public class ArtistReviewBadge extends Badge {

	private ArtistReviewRank artistReviewRank;
	private Artist artist;
	
	public ArtistReviewBadge(ArtistReviewRank artistReviewRank, Artist artist) {
		super();
		this.artistReviewRank = artistReviewRank;
		this.artist = artist;
	}

	public ArtistReviewBadge() {
		super();
	}

	public ArtistReviewRank getArtistReviewRank() {
		return artistReviewRank;
	}

	public void setArtistReviewRank(ArtistReviewRank artistReviewRank) {
		this.artistReviewRank = artistReviewRank;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}
}
