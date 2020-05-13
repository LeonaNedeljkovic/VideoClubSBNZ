package com.videoClub.exception;

@SuppressWarnings("serial")
public class InvalidDate extends RuntimeException {

	private String message;
	
	public InvalidDate(){
		super();
		this.message = "Date you have entered is invalid.";
	}

	public String getMessage() {
		return message;
	}
}
