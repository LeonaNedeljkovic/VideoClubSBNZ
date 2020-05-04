package com.videoClub.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.videoClub.model.enumeration.DefinerType;
import com.videoClub.model.enumeration.Rank;

@Entity
@Table(name = "entity_definer")
public class EntityDefiner {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private DefinerType type;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "rank")
	private Rank rank;
	
	@Column(name = "value")
	private int value;

	public EntityDefiner() {
		super();
	}

	public EntityDefiner(Long id, DefinerType type, Rank rank, int value) {
		super();
		this.id = id;
		this.type = type;
		this.rank = rank;
		this.value = value;
	}
	
	public DefinerType getType() {
		return type;
	}

	public void setType(DefinerType type) {
		this.type = type;
	}

	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
