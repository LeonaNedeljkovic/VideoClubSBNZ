package com.videoClub.template;

import com.videoClub.model.enumeration.AgeCategory;

public class FilmAgeRestrictionTemplate {

	private AgeCategory ageCategory;
	Long id;
	
	public FilmAgeRestrictionTemplate() {
		super();
	}

	public FilmAgeRestrictionTemplate(AgeCategory ageCategory, Long id) {
		super();
		this.ageCategory = ageCategory;
		this.id = id;
	}

	public AgeCategory getAgeCategory() {
		return ageCategory;
	}

	public void setAgeCategory(AgeCategory ageCategory) {
		this.ageCategory = ageCategory;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
