package com.videoClub.model;

import java.util.ArrayList; 
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;

import com.videoClub.model.enumeration.ActionType;
import com.videoClub.model.enumeration.Genre;
import com.videoClub.model.enumeration.Rank;

@Entity
public class FreeContent extends Action {
	@ElementCollection(targetClass = Genre.class)
	@JoinTable(name = "free_content_genre", joinColumns = @JoinColumn(name = "id"))
	@Column(name = "genre", nullable = false)
	@Enumerated(EnumType.STRING)
	private List<Genre> freeGenres = new ArrayList<Genre>();

	public FreeContent() {
		super();
	}

	public FreeContent(Long id, String description, ActionEvent actionEvent, Rank titleRank, List<Genre> freeGenres) {
		super(id, description, actionEvent, titleRank, ActionType.FREE_CONTENT);
		this.freeGenres = freeGenres;
	}

	public FreeContent(List<Genre> freeGenres) {
		super();
		this.freeGenres = freeGenres;
	}

	public List<Genre> getFreeGenres() {
		return freeGenres;
	}

	public void setFreeGenres(List<Genre> freeGenres) {
		this.freeGenres = freeGenres;
	}
}
