package com.videoClub.exception;

@SuppressWarnings("serial")
public class InvalidParameters extends RuntimeException{

private String message;
	
	public InvalidParameters(){
		super();
		this.message = "Invalid parameters in request found.";
	}

	public String getMessage() {
		return message;
	}
}
