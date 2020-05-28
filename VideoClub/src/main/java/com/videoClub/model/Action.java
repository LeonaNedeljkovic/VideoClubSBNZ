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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.videoClub.model.enumeration.Rank;

@Entity
@Table(name = "action")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Action {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "description")
	private String description;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "action_event_id")
	private ActionEvent actionEvent;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "title_rank")
	private Rank titleRank;

	public Action() {
		super();
	}

	public Action(Long id, String description, ActionEvent actionEvent, Rank titleRank) {
		super();
		this.id = id;
		this.description = description;
		this.actionEvent = actionEvent;
		this.titleRank = titleRank;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ActionEvent getActionEvent() {
		return actionEvent;
	}

	public void setActionEvent(ActionEvent actionEvent) {
		this.actionEvent = actionEvent;
	}

	public Rank getTitleRank() {
		return titleRank;
	}

	public void setTitleRank(Rank titleRank) {
		this.titleRank = titleRank;
	}
}
