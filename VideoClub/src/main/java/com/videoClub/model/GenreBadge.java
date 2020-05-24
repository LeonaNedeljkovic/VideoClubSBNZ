package com.videoClub.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.videoClub.model.enumeration.Genre;
import com.videoClub.model.enumeration.Rank;

@Entity
public class GenreBadge extends Badge {

	@Column(name = "genre", nullable = false)
	@Enumerated(EnumType.STRING)
	private Genre genre;

	@Column(name = "genre_rank", nullable = false)
	@Enumerated(EnumType.STRING)
	private Rank genreRank;

	public GenreBadge(Long id, RegisteredUser user, Genre genre, Rank genreRank) {
		super(id, user);
		this.genre = genre;
		this.genreRank = genreRank;
	}

	public GenreBadge() {
		super();
	}

	public GenreBadge(Genre genre, Rank genreRank) {
		super();
		this.genre = genre;
		this.genreRank = genreRank;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public Rank getGenreRank() {
		return genreRank;
	}

	public void setGenreRank(Rank genreRank) {
		this.genreRank = genreRank;
	}
}
