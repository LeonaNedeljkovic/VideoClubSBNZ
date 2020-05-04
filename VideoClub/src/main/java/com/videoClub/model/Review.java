package com.videoClub.model;

import java.util.ArrayList;
import java.util.List;

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
	@JoinColumn(name = "video_content_id")
	private VideoContent videoContent;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private RegisteredUser user;
	
	@JsonIgnore
	@OneToMany(mappedBy = "review", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<TimeInterval> timeIntervals = new ArrayList<TimeInterval>();
	
	@Column(name = "watched")
	private boolean watched;

	public Review() {
		super();
	}

	public Review(Long id, VideoContent videoContent, RegisteredUser user, List<TimeInterval> timeIntervals,
			boolean watched) {
		super();
		this.id = id;
		this.videoContent = videoContent;
		this.user = user;
		this.timeIntervals = timeIntervals;
		this.watched = watched;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public VideoContent getVideoContent() {
		return videoContent;
	}

	public void setVideoContent(VideoContent videoContent) {
		this.videoContent = videoContent;
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

}
