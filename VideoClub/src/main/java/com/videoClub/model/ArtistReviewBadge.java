package com.videoClub.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.videoClub.model.enumeration.ArtistReviewRank;

@Entity
public class ArtistReviewBadge extends Badge {

	@Column(name = "artist_review_rank", nullable = false)
	@Enumerated(EnumType.STRING)
	private ArtistReviewRank artistReviewRank;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "artist_id")
	private Artist artist;

	public ArtistReviewBadge(Long id, RegisteredUser user, ArtistReviewRank artistReviewRank, Artist artist) {
		super(id, user);
		this.artistReviewRank = artistReviewRank;
		this.artist = artist;
	}

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
