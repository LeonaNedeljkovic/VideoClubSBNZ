package com.videoClub.model.drl;

import java.util.List;

import com.videoClub.model.Artist;
import com.videoClub.model.Film;
import com.videoClub.model.enumeration.ArtistRateRank;
import com.videoClub.model.enumeration.ArtistReviewRank;

public class RecommendedFilm {

	private Double recommendPoints;
	private Film film;
	
	public RecommendedFilm(double recommendPoints, Film film) {
		super();
		this.recommendPoints = recommendPoints;
		this.film = film;
	}

	public RecommendedFilm() {
		super();
	}

	public double getRecommendPoints() {
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
	
	public double actorBadgeRating(List<Badge> badges, ArtistRateRank rank){
		double num = 0.0;
		for(Badge badge : badges){
			if(badge instanceof ArtistRateBadge){
				for(Artist a : film.getActors()){
					if(((ArtistRateBadge)badge).getArtist().getId() == a.getId()
							&& ((ArtistRateBadge)badge).getArtistRateRank().equals(rank)){
						num += ((ArtistRateBadge)badge).getAverageRate();
					}
				}
			}
		}
		return num;
	}
	
	public int actorBadgeWatchedReviews(List<Badge> badges, ArtistReviewRank rank){
		int num = 0;
		for(Badge badge : badges){
			if(badge instanceof ArtistReviewBadge){
				for(Artist a : film.getActors()){
					if(((ArtistReviewBadge)badge).getArtist().getId() == a.getId()
							&& ((ArtistReviewBadge)badge).getArtistReviewRank().equals(rank)){
						num += ((ArtistReviewBadge)badge).getWatchedNumber();
					}
				}
			}
		}
		return num;
	}
	
	public int actorBadgeUnwatchedReviews(List<Badge> badges, ArtistReviewRank rank){
		int num = 0;
		for(Badge badge : badges){
			if(badge instanceof ArtistReviewBadge){
				for(Artist a : film.getActors()){
					if(((ArtistReviewBadge)badge).getArtist().getId() == a.getId()
							&& ((ArtistReviewBadge)badge).getArtistReviewRank().equals(rank)){
						num += ((ArtistReviewBadge)badge).getUnwatchedNumber() - ((ArtistReviewBadge)badge).getWatchedNumber();
					}
				}
			}
		}
		return num;
	}
	
	public double directorScenaristBadgeRating(List<Badge> badges, ArtistRateRank rank){
		double num = 0.0;
		for(Badge badge : badges){
			if(badge instanceof ArtistRateBadge){
				if(((ArtistRateBadge)badge).getArtistRateRank().equals(rank) && (film.getDirector().getId() == ((ArtistRateBadge) badge).getArtist().getId()
						|| film.getWrittenBy().getId() == ((ArtistRateBadge) badge).getArtist().getId())){
					num += ((ArtistRateBadge)badge).getAverageRate();
				}
			}
		}
		return num;
	}
	
	public int directorScenaristBadgeWatchedReviews(List<Badge> badges, ArtistReviewRank rank){
		int num = 0;
		for(Badge badge : badges){
			if(badge instanceof ArtistReviewBadge){
				if(((ArtistReviewBadge)badge).getArtistReviewRank().equals(rank) && (film.getDirector().getId() == ((ArtistReviewBadge) badge).getArtist().getId()
						|| film.getWrittenBy().getId() == ((ArtistReviewBadge) badge).getArtist().getId())){
					num += ((ArtistReviewBadge)badge).getWatchedNumber();
				}
			}
		}
		return num;
	}
	
	public int directorScenaristBadgeUnwatchedReviews(List<Badge> badges, ArtistReviewRank rank){
		int num = 0;
		for(Badge badge : badges){
			if(badge instanceof ArtistReviewBadge){
				if(((ArtistReviewBadge)badge).getArtistReviewRank().equals(rank) && (film.getDirector().getId() == ((ArtistReviewBadge) badge).getArtist().getId()
						|| film.getWrittenBy().getId() == ((ArtistReviewBadge) badge).getArtist().getId())){
					num += ((ArtistReviewBadge)badge).getUnwatchedNumber() - ((ArtistReviewBadge)badge).getWatchedNumber();
				}
			}
		}
		return num;
	}
}
