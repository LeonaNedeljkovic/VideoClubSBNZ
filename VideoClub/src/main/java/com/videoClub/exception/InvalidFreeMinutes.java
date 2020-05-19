package com.videoClub.exception;

@SuppressWarnings("serial")
public class InvalidFreeMinutes extends RuntimeException{

private String message;
	
	public InvalidFreeMinutes(){
		super();
		this.message = "Amount of free minutes must be between 30 and 600.";
	}

	public String getMessage() {
		return message;
	}
}