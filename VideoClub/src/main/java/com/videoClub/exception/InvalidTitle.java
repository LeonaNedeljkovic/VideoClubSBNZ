package com.videoClub.exception;

@SuppressWarnings("serial")
public class InvalidTitle extends RuntimeException{

private String message;
	
	public InvalidTitle(){
		super();
		this.message = "Invalid value. Make sure that:"
				+ "\tTitle with higher value must have more acquire, save and reward points than title with lower value."
				+ "\tValue of acquire points must be greater than value of save points."
				+ "\tValue of save points must be between 30 and 600.";
	}

	public String getMessage() {
		return message;
	}
}