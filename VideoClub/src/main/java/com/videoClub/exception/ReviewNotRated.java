package com.videoClub.exception;

@SuppressWarnings("serial")
public class ReviewNotRated extends RuntimeException {

	private String message;
	
	public ReviewNotRated(){
		super();
		this.message = "Film must be rated 5 before adding it to favourites.";
	}

	public String getMessage() {
		return message;
	}
}