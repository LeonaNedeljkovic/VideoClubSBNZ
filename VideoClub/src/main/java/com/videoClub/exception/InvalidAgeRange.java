package com.videoClub.exception;

@SuppressWarnings("serial")
public class InvalidAgeRange extends RuntimeException{

private String message;
	
	public InvalidAgeRange(){
		super();
		this.message = "Invalid value. Make sure that:"
				+ "\tEvery age range is only 1 year apart from next one (if exists) and previous one (if exists)";
	}

	public String getMessage() {
		return message;
	}

}
