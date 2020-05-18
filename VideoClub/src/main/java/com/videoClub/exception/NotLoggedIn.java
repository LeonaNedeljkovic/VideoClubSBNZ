package com.videoClub.exception;

@SuppressWarnings("serial")
public class NotLoggedIn extends RuntimeException {

	private String message;
	
	public NotLoggedIn(){
		super();
		this.message = "Only registered users can perform this action.";
	}

	public String getMessage() {
		return message;
	}
}