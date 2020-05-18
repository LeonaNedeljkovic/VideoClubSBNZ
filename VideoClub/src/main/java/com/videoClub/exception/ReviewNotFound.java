package com.videoClub.exception;

@SuppressWarnings("serial")
public class ReviewNotFound extends RuntimeException {

	private String message;
	
	public ReviewNotFound(Long id){
		super();
		this.message = "Review with ID " + id +
				" does not exist in system.";
	}

	public String getMessage() {
		return message;
	}
}