package com.videoClub.exception;

@SuppressWarnings("serial")
public class InvalidImmunity extends RuntimeException{

private String message;
	
	public InvalidImmunity(){
		super();
		this.message = "Invalid value.";
	}

	public String getMessage() {
		return message;
	}
}
