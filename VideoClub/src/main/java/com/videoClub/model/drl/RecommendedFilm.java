package com.videoClub.model.drl;

import java.util.List;

import com.videoClub.model.Artist;
import com.videoClub.model.Film;

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
	
	public double actorBadgeRating(List<Badge> badges){
		double num = 0.0;
		int i = 0;
		for(Badge badge : badges){
			if(badge instanceof ArtistRateBadge){
				for(Artist a : film.getActors()){
					if(((ArtistRateBadge)badge).getArtist().getId() == a.getId()){
						num += ((ArtistRateBadge)badge).getAverageRate();
						i++;
					}
				}
			}
		}
		return num/i;
	}
	
	public int actorFavourites(List<Badge> badges){
		int i = 0;
		for(Badge badge : badges){
			if(badge instanceof ArtistRateBadge){
				for(Artist a : film.getActors()){
					if(((ArtistRateBadge)badge).getArtist().getId() == a.getId()){
						i += ((ArtistRateBadge)badge).getFavourites();
					}
				}
			}
		}
		return i;
	}
	
	public int directorFavourites(List<Badge> badges){
		int num = 0;
		for(Badge badge : badges){
			if(badge instanceof ArtistRateBadge){
				if(film.getDirector().getId() == ((ArtistRateBadge) badge).getArtist().getId()){
					num += ((ArtistRateBadge)badge).getFavourites();
				}
			}
		}
		return num;
	}
	
	public int scenaristFavourites(List<Badge> badges){
		int num = 0;
		for(Badge badge : badges){
			if(badge instanceof ArtistRateBadge){
				if(film.getWrittenBy().getId() == ((ArtistRateBadge) badge).getArtist().getId()){
					num += ((ArtistRateBadge)badge).getFavourites();
				}
			}
		}
		return num;
	}
	
	public int actorBadgeWatchedReviews(List<Badge> badges){
		int num = 0;
		for(Badge badge : badges){
			if(badge instanceof ArtistReviewBadge){
				for(Artist a : film.getActors()){
					if(((ArtistReviewBadge)badge).getArtist().getId() == a.getId()){
						num += ((ArtistReviewBadge)badge).getWatchedNumber();
					}
				}
			}
		}
		return num;
	}
	
	public int actorBadgeUnwatchedReviews(List<Badge> badges){
		int num = 0;
		for(Badge badge : badges){
			if(badge instanceof ArtistReviewBadge){
				for(Artist a : film.getActors()){
					if(((ArtistReviewBadge)badge).getArtist().getId() == a.getId()){
						num += ((ArtistReviewBadge)badge).getUnwatchedNumber();
					}
				}
			}
		}
		return num;
	}
	
	public double directorBadgeRating(List<Badge> badges){
		double num = 0.0;
		int i = 0;
		for(Badge badge : badges){
			if(badge instanceof ArtistRateBadge){
				if(film.getDirector().getId() == ((ArtistRateBadge) badge).getArtist().getId()){
					num += ((ArtistRateBadge)badge).getAverageRate();
					i++;
				}
			}
		}
		return num/i;
	}
	
	public double scenaristBadgeRating(List<Badge> badges){
		double num = 0.0;
		int i = 0;
		for(Badge badge : badges){
			if(badge instanceof ArtistRateBadge){
				if(film.getWrittenBy().getId() == ((ArtistRateBadge) badge).getArtist().getId()){
					num += ((ArtistRateBadge)badge).getAverageRate();
					i++;
				}
			}
		}
		return num/i;
	}
	
	public int directorBadgeWatchedReviews(List<Badge> badges){
		int num = 0;
		for(Badge badge : badges){
			if(badge instanceof ArtistReviewBadge){
				if(film.getDirector().getId() == ((ArtistReviewBadge) badge).getArtist().getId()){
					num += ((ArtistReviewBadge)badge).getWatchedNumber();
				}
			}
		}
		return num;
	}
	
	public int scenaristBadgeWatchedReviews(List<Badge> badges){
		int num = 0;
		for(Badge badge : badges){
			if(badge instanceof ArtistReviewBadge){
				if(film.getWrittenBy().getId() == ((ArtistReviewBadge) badge).getArtist().getId()){
					num += ((ArtistReviewBadge)badge).getWatchedNumber();
				}
			}
		}
		return num;
	}
	
	public int directorBadgeUnwatchedReviews(List<Badge> badges){
		int num = 0;
		for(Badge badge : badges){
			if(badge instanceof ArtistReviewBadge){
				if(film.getDirector().getId() == ((ArtistReviewBadge) badge).getArtist().getId()){
					num += ((ArtistReviewBadge)badge).getUnwatchedNumber();
				}
			}
		}
		return num;
	}
	
	public int scenaristBadgeUnwatchedReviews(List<Badge> badges){
		int num = 0;
		for(Badge badge : badges){
			if(badge instanceof ArtistReviewBadge){
				if(film.getWrittenBy().getId() == ((ArtistReviewBadge) badge).getArtist().getId()){
					num += ((ArtistReviewBadge)badge).getUnwatchedNumber();
				}
			}
		}
		return num;
	}
}
