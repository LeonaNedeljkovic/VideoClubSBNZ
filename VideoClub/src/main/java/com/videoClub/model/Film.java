package com.videoClub.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.videoClub.model.enumeration.AgeCategory;
import com.videoClub.model.enumeration.Genre;

@Entity
@Table(name = "film")
public class Film {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "genre")
	private Genre genre;
	
	@Column(name = "duration")
	private int duration;
	
	@Column(name = "year")
	private int year;
	
	@Column(name = "rating")
	private double rating;
	
	@Column(name = "poster")
	private String poster;
	
	@ManyToMany
	@JoinTable(name = "film_actors", 
			  joinColumns = @JoinColumn(name = "film_id"), 
			  inverseJoinColumns = @JoinColumn(name = "artist_id"))
	private List<Artist> actors = new ArrayList<Artist>();
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "director_id")
	private Artist director;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "written_by")
	private Artist writtenBy;
	
	@JsonIgnore
	@OneToMany(mappedBy = "film", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Review> reviews = new ArrayList<Review>();
	
	@JsonIgnore
	@ElementCollection(targetClass = Genre.class)
	@JoinTable(name = "restricted_age_categories", joinColumns = @JoinColumn(name = "id"))
	@Column(name = "age_category", nullable = false)
	@Enumerated(EnumType.STRING)
	private List<AgeCategory> restrictedAgeCategories = new ArrayList<AgeCategory>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "film_report", fetch = FetchType.LAZY)
	private List<Report> reports = new ArrayList<Report>();


	public Film() {
		super();
	}
	
	public Film(Long id, String name, String description, Genre genre, int duration, int year, double rating,
			String poster, List<Artist> actors, Artist director, Artist writtenBy, List<Review> reviews,
			List<AgeCategory> restrictedAgeCategories) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.genre = genre;
		this.duration = duration;
		this.year = year;
		this.rating = rating;
		this.poster = poster;
		this.actors = actors;
		this.director = director;
		this.writtenBy = writtenBy;
		this.reviews = reviews;
		this.restrictedAgeCategories = restrictedAgeCategories;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public List<Artist> getActors() {
		return actors;
	}

	public void setActors(List<Artist> actors) {
		this.actors = actors;
	}

	public Artist getDirector() {
		return director;
	}

	public void setDirector(Artist director) {
		this.director = director;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public Artist getWrittenBy() {
		return writtenBy;
	}

	public void setWrittenBy(Artist writtenBy) {
		this.writtenBy = writtenBy;
	}
	
	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public List<AgeCategory> getRestrictedAgeCategories() {
		return restrictedAgeCategories;
	}

	public void setRestrictedAgeCategories(List<AgeCategory> restrictedAgeCategories) {
		this.restrictedAgeCategories = restrictedAgeCategories;
	}
	
	
	
	public List<Report> getReports() {
		return reports;
	}

	public void setReports(List<Report> reports) {
		this.reports = reports;
	}

	public void addNewRestrictedAgeCategory(AgeCategory category){
		restrictedAgeCategories.add(category);
	}

	public void addNewRate(int rate, Long reviewId){
		double rates = 0;
		double sum = 0;
		for(Review r : reviews){
			if(r.getRate() != 0 && r.getId() != reviewId){
				rates++;
				sum += r.getRate();
			}
		}
		rates++;
		sum += rate;
		setRating(sum/rates);
	}
}
