package com.videoClub.exception;

@SuppressWarnings("serial")
public class InvalidTitle extends RuntimeException{

private String message;
	
	public InvalidTitle(){
		super();
		this.message = "Invalid value.";
	}

	public String getMessage() {
		return message;
	}
}