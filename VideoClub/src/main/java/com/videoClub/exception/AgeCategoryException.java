package com.videoClub.exception;

@SuppressWarnings("serial")
public class AgeCategoryException extends RuntimeException {

	private String message;
	
	public AgeCategoryException(){
		super();
		this.message = "You have entered invalid age category.";
	}

	public String getMessage() {
		return message;
	}
}