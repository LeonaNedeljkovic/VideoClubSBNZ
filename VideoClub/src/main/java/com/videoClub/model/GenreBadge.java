package com.videoClub.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

import com.videoClub.model.enumeration.BadgeType;
import com.videoClub.model.enumeration.Genre;

@Entity
public class GenreBadge extends Badge {
	@ElementCollection(targetClass = Genre.class)
	@JoinTable(name = "badge_genre", joinColumns = @JoinColumn(name = "id"))
	@Column(name = "genre", nullable = false)
	@Enumerated(EnumType.STRING)
	private List<Genre> rewardGenres = new ArrayList<Genre>();

	public GenreBadge(Long id, RegisteredUser user, List<Genre> rewardGenres) {
		super(id, user, BadgeType.GENRE_BADGE);
		this.rewardGenres = rewardGenres;
	}

	public GenreBadge(List<Genre> rewardGenres) {
		super();
		this.rewardGenres = rewardGenres;
	}

	public GenreBadge() {
		super();
	}

	public List<Genre> getRewardGenres() {
		return rewardGenres;
	}

	public void setRewardGenres(List<Genre> rewardGenres) {
		this.rewardGenres = rewardGenres;
	}
}
