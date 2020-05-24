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

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@JsonIgnore
	@ManyToMany
	private List<Film> roles = new ArrayList<Film>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "director", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Film> directed = new ArrayList<Film>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "writtenBy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Film> written = new ArrayList<Film>();

	public Artist() {
		super();
	}
	
	public Artist(Long id, String name, String surname, List<Film> roles, List<Film> directed, List<Film> written) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.roles = roles;
		this.directed = directed;
		this.written = written;
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

	public List<Film> getRoles() {
		return roles;
	}

	public void setRoles(List<Film> roles) {
		this.roles = roles;
	}

	public List<Film> getDirected() {
		return directed;
	}

	public void setDirected(List<Film> directed) {
		this.directed = directed;
	}

	public List<Film> getWritten() {
		return written;
	}

	public void setWritten(List<Film> written) {
		this.written = written;
	}
}
