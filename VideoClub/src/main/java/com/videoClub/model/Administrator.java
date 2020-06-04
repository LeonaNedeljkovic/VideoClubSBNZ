package com.videoClub.model;

import javax.persistence.Entity;

@Entity
public class Administrator extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Administrator() {
		
	}
	
	public Administrator(Long id, String username, String password, String email) {
		super(id, username, password, email);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
