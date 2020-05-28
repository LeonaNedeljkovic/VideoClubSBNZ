package com.videoClub.model.drl;

import com.videoClub.model.enumeration.Genre;
import com.videoClub.model.enumeration.GenreRank;

public class GenreBadge extends Badge {

	private Genre genre;
	private GenreRank rank;
	
	public GenreBadge(Genre genre, GenreRank rank) {
		super();
		this.genre = genre;
		this.rank = rank;
	}

	public GenreBadge() {
		super();
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public GenreRank getRank() {
		return rank;
	}

	public void setRank(GenreRank rank) {
		this.rank = rank;
	}
}
