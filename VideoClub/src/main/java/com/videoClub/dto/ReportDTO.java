package com.videoClub.dto;

import java.time.LocalDate;

import com.videoClub.model.Film;

public class ReportDTO {
	private double earned;
	private LocalDate date;
	private Film film;
	private Long numberOfViews;

	public ReportDTO() {
		super();
	}

	public ReportDTO(double earned, LocalDate date, Film film, Long numberOfViews) {
		super();
		this.earned = earned;
		this.date = date;
		this.film = film;
		this.numberOfViews = numberOfViews;
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

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public Long getNumberOfViews() {
		return numberOfViews;
	}

	public void setNumberOfViews(Long numberOfViews) {
		this.numberOfViews = numberOfViews;
	}

}
