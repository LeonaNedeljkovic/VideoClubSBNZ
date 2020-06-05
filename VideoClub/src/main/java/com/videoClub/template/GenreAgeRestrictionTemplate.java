package com.videoClub.template;

import com.videoClub.model.enumeration.AgeCategory;
import com.videoClub.model.enumeration.Genre;

public class GenreAgeRestrictionTemplate {

	private AgeCategory ageCategory;
	private Genre genre;
	
	public GenreAgeRestrictionTemplate(AgeCategory ageCategory, Genre genre) {
		super();
		this.ageCategory = ageCategory;
		this.genre = genre;
	}

	public GenreAgeRestrictionTemplate() {
		super();
	}

	public AgeCategory getAgeCategory() {
		return ageCategory;
	}

	public void setAgeCategory(AgeCategory ageCategory) {
		this.ageCategory = ageCategory;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}
}
