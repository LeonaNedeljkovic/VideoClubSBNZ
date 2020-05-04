package com.videoClub.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.videoClub.model.enumeration.BadgeType;

@Entity
@Table(name = "badge")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Badge {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private RegisteredUser user;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private BadgeType type;

	public Badge() {
		super();
	}

	public Badge(Long id, RegisteredUser user, BadgeType type) {
		super();
		this.id = id;
		this.user = user;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RegisteredUser getUser() {
		return user;
	}

	public void setUser(RegisteredUser user) {
		this.user = user;
	}

	public BadgeType getType() {
		return type;
	}

	public void setType(BadgeType type) {
		this.type = type;
	}
	
}
