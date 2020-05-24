package com.videoClub.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "review")
public class Review {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "film_id")
	private Film film;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private RegisteredUser user;
	
	@JsonIgnore
	@OneToMany(mappedBy = "review", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<TimeInterval> timeIntervals = new ArrayList<TimeInterval>();
	
	@Column(name = "watched")
	private boolean watched;
	
	@Column(name = "watched_time")
	private LocalDateTime watchedTime;
	
	@Column(name = "rate")
	private Integer rate;

	public Review() {
		super();
	}

	public Review(Long id, Film film, RegisteredUser user, List<TimeInterval> timeIntervals, boolean watched,
			LocalDateTime watchedTime, Integer rate) {
		super();
		this.id = id;
		this.film = film;
		this.user = user;
		this.timeIntervals = timeIntervals;
		this.watched = watched;
		this.watchedTime = watchedTime;
		this.rate = rate;
	}

	public LocalDateTime getWatchedTime() {
		return watchedTime;
	}

	public void setWatchedTime(LocalDateTime watchedTime) {
		this.watchedTime = watchedTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public RegisteredUser getUser() {
		return user;
	}

	public void setUser(RegisteredUser user) {
		this.user = user;
	}

	public List<TimeInterval> getTimeIntervals() {
		return timeIntervals;
	}

	public void setTimeIntervals(List<TimeInterval> timeIntervals) {
		this.timeIntervals = timeIntervals;
	}

	public boolean isWatched() {
		return watched;
	}

	public void setWatched(boolean watched) {
		this.watched = watched;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public double getWatchedDuration(){
		Set<Integer> watchedDuration = new HashSet<Integer>();
		for(TimeInterval ti : this.timeIntervals){
			for(int i = ti.getStartMinute(); i <= ti.getEndMinute(); i++){
				watchedDuration.add(i);
			}
		}
		int watched = watchedDuration.size();
		double percentage = 100.0*watched/film.getDuration();
		return percentage;
	}
}
