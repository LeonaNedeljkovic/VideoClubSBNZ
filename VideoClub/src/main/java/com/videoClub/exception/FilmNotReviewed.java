package com.videoClub.exception;

@SuppressWarnings("serial")
public class FilmNotReviewed extends RuntimeException {

	private String message;
	
	public FilmNotReviewed(){
		super();
		this.message = "You have to watch film to the end before giving it a rate.";
	}

	public String getMessage() {
		return message;
	}
}