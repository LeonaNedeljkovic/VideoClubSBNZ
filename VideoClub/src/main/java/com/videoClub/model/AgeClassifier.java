package com.videoClub.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.videoClub.model.enumeration.AgeCategory;

@Entity
@Table(name = "age_classifier")
public class AgeClassifier {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "age_category")
	private AgeCategory ageCategory;
	
	@Column(name = "start_age")
	private int startAge;
	
	@Column(name = "end_age")
	private int endAge;

	public AgeClassifier(Long id, int startAge, int endAge, AgeCategory ageCategory) {
		super();
		this.id = id;
		this.ageCategory = ageCategory;
		this.startAge = startAge;
		this.endAge = endAge;
	}

	public AgeClassifier() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AgeCategory getAgeCategory() {
		return ageCategory;
	}

	public void setAgeCategory(AgeCategory ageCategory) {
		this.ageCategory = ageCategory;
	}

	public int getStartAge() {
		return startAge;
	}

	public void setStartAge(int startAge) {
		this.startAge = startAge;
	}

	public int getEndAge() {
		return endAge;
	}

	public void setEndAge(int endAge) {
		this.endAge = endAge;
	}
}
