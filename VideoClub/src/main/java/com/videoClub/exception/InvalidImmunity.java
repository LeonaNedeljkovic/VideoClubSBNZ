package com.videoClub.exception;

@SuppressWarnings("serial")
public class InvalidImmunity extends RuntimeException{

private String message;
	
	public InvalidImmunity(){
		super();
		this.message = "Invalid value. Make sure that:"
				+ "\tImmunity with higher value must have more acquire points than immunity with lower value."
				+ "\tValue of points must be between 5 and 1000.";
	}

	public String getMessage() {
		return message;
	}
}
