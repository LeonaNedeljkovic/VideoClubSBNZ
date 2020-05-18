package com.videoClub.exception;

@SuppressWarnings("serial")
public class InvalidReview extends RuntimeException {

	private String message;
	
	public InvalidReview(){
		super();
		this.message = "Invalid review parameters.";
	}

	public String getMessage() {
		return message;
	}
}