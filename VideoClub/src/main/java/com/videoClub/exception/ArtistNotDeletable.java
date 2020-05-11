package com.videoClub.exception;

import com.videoClub.model.Artist;

@SuppressWarnings("serial")
public class ArtistNotDeletable extends RuntimeException {

	private String message;
	
	public ArtistNotDeletable(Artist artist){
		super();
		this.message = "It is not possible to delete artist " + artist.getName() + " " + artist.getSurname() +
				" because he/she appears in some video content in system.";
	}

	public String getMessage() {
		return message;
	}
}
