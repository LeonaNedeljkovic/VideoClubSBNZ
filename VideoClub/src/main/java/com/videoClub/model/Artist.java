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
	@ManyToMany(mappedBy = "actors")
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((directed == null) ? 0 : directed.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		result = prime * result + ((written == null) ? 0 : written.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Artist other = (Artist) obj;
		if (directed == null) {
			if (other.directed != null)
				return false;
		} else if (!directed.equals(other.directed))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		if (written == null) {
			if (other.written != null)
				return false;
		} else if (!written.equals(other.written))
			return false;
		return true;
	}
	
	
}
