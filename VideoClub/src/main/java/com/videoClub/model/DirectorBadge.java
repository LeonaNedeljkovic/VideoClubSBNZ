package com.videoClub.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.videoClub.model.enumeration.BadgeType;

@Entity
public class DirectorBadge extends Badge{

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "director_id")
	private Artist director;

	public DirectorBadge(Artist director) {
		super();
		this.director = director;
	}

	public DirectorBadge(Long id, RegisteredUser user, Artist director) {
		super(id, user, BadgeType.DIRECTOR_BADGE);
		this.director = director;
	}

	public DirectorBadge() {
		super();
	}

	public Artist getDirector() {
		return director;
	}

	public void setDirector(Artist director) {
		this.director = director;
	}
}
