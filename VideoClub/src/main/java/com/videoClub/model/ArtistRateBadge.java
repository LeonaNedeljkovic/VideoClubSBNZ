package com.videoClub.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.videoClub.model.enumeration.ArtistRateRank;

@Entity
public class ArtistRateBadge extends Badge {

	@Column(name = "artist_rate_rank", nullable = false)
	@Enumerated(EnumType.STRING)
	private ArtistRateRank artistRateRank;
	
	@Column(name = "average_rate", nullable = false)
	private double averageRate;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "artist_id")
	private Artist artist;

	public ArtistRateBadge(ArtistRateRank artistRateRank, double averageRate, Artist artist) {
		super();
		this.artistRateRank = artistRateRank;
		this.averageRate = averageRate;
		this.artist = artist;
	}

	public ArtistRateBadge(Long id, RegisteredUser user, ArtistRateRank artistRateRank, double averageRate,
			Artist artist) {
		super(id, user);
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
