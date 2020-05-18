package com.videoClub.exception;

@SuppressWarnings("serial")
public class NotEnoughMinutes extends RuntimeException {

	private String message;
	
	public NotEnoughMinutes(){
		super();
		this.message = "You do not have enough available minutes to perform this action.";
	}

	public String getMessage() {
		return message;
	}
}
