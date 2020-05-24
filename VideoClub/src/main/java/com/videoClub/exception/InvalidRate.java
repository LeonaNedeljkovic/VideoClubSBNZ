package com.videoClub.exception;

@SuppressWarnings("serial")
public class InvalidRate extends RuntimeException{

private String message;
	
	public InvalidRate(){
		super();
		this.message = "Rate must be a number between 1 and 5.";
	}

	public String getMessage() {
		return message;
	}
}