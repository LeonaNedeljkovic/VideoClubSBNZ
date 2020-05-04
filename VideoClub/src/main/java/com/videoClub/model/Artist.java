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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "artist")
public class Artist {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "surname")
	private String surname;
	
	@ManyToMany
	private List<VideoContent> roles = new ArrayList<VideoContent>();
	
	@OneToMany(mappedBy = "director", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<VideoContent> directed = new ArrayList<VideoContent>();

	public Artist() {
		super();
	}

	public Artist(Long id, String name, String surname, List<VideoContent> roles, List<VideoContent> directed) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.roles = roles;
		this.directed = directed;
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<VideoContent> getRoles() {
		return roles;
	}

	public void setRoles(List<VideoContent> roles) {
		this.roles = roles;
	}

	public List<VideoContent> getDirected() {
		return directed;
	}

	public void setDirected(List<VideoContent> directed) {
		this.directed = directed;
	}

}
