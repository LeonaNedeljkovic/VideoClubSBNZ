package com.videoClub.model;

import com.videoClub.model.enumeration.UserRole;

public class UserTokenState {

	private String accessToken;
	private Long expiresIn;
	private UserRole role;

	public UserTokenState() {
		this.accessToken = null;
		this.expiresIn = null;
		this.role = null;
	}

	public UserTokenState(String accessToken, long expiresIn, UserRole role) {
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
		this.role=role;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	

}

