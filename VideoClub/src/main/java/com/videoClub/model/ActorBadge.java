package com.videoClub.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.videoClub.model.enumeration.BadgeType;

@Entity
public class ActorBadge extends Badge {

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "actor_id")
	private Artist actor;

	public ActorBadge(Artist actor) {
		super();
		this.actor = actor;
	}

	public ActorBadge(Long id, RegisteredUser user, Artist actor) {
		super(id, user, BadgeType.ACTOR_BADGE);
		this.actor = actor;
	}

	public ActorBadge() {
		super();
	}

	public Artist getActor() {
		return actor;
	}

	public void setActor(Artist actor) {
		this.actor = actor;
	}
}
