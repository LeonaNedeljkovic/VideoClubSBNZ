package com.videoClub.model;

import java.time.LocalDate;

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
	private LocalDate date;
	
	@Column(name="numberOfViews")
	private Long numberOfViews;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "film_id")
	private Film film;

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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Long getNumberOfViews() {
		return numberOfViews;
	}

	public void setNumberOfViews(Long numberOfViews) {
		this.numberOfViews = numberOfViews;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public Report(Long id, double earned, LocalDate date, Long numberOfViews, Film film) {
		super();
		this.id = id;
		this.earned = earned;
		this.date = date;
		this.numberOfViews = numberOfViews;
		this.film = film;
	}
	
	

	public Report(double earned, LocalDate date, Long numberOfViews, Film film) {
		super();
		this.earned = earned;
		this.date = date;
		this.numberOfViews = numberOfViews;
		this.film = film;
	}

	public Report() {
		super();
	}
	
	

}
