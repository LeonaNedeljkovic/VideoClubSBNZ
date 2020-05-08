package com.videoClub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.videoClub.model.enumeration.UserRole;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "authority")
public class Authority implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3742453340543117722L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "name")
	UserRole name;

	@Override
	public String getAuthority() {
		return name.name();
	}

	public void setName(UserRole name) {
		this.name = name;
	}

	@JsonIgnore
	public UserRole getName() {
		return name;
	}

	@JsonIgnore
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}

