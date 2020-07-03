package com.videoClub.model;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "report")
public class Report {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="earned")
	private double earned;
	
	@Column(name="date")
	private String date;
	
	@Column(name="numberOfViews")
	private Long numberOfViews;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "film_id")
	@JsonIgnore
	private Film film_report;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getEarned() {
		return earned;
	}

	public void setEarned(double earned) {
		this.earned = earned;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Long getNumberOfViews() {
		return numberOfViews;
	}

	public void setNumberOfViews(Long numberOfViews) {
		this.numberOfViews = numberOfViews;
	}

	public Film getFilm() {
		return film_report;
	}

	public void setFilm(Film film) {
		this.film_report = film;
	}

	public Report(Long id, double earned, String date, Long numberOfViews, Film film) {
		super();
		this.id = id;
		this.earned = earned;
		this.date = date;
		this.numberOfViews = numberOfViews;
		this.film_report = film;
	}
	
	

	public Report(double earned, String date, Long numberOfViews, Film film) {
		super();
		this.earned = earned;
		this.date = date;
		this.numberOfViews = numberOfViews;
		this.film_report = film;
	}

	public Report() {
		super();
	}
	
	

}
