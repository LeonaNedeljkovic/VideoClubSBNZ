package com.videoClub.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "notification")
public class Notification {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "header")
	private String header;
	
	@Column(name = "body")
	private String body;
	
	@Column(name = "opened")
	private boolean opened;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "user_id")
	private RegisteredUser user;

	public Notification(Long id, String header, String body, boolean opened, RegisteredUser user) {
		super();
		this.id = id;
		this.header = header;
		this.body = body;
		this.opened = opened;
		this.user = user;
	}
	
	public Notification(String header, String body, boolean opened, RegisteredUser user) {
		super();
		this.header = header;
		this.body = body;
		this.opened = opened;
		this.user = user;
	}

	public Notification() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public boolean isOpened() {
		return opened;
	}

	public void setOpened(boolean opened) {
		this.opened = opened;
	}

	public RegisteredUser getUser() {
		return user;
	}

	public void setUser(RegisteredUser user) {
		this.user = user;
	}
}
