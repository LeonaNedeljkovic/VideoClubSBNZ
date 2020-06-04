package com.videoClub.exception;

@SuppressWarnings("serial")
public class TooManyPurchasesFromUser extends RuntimeException {
	
	private String message;
	
	public TooManyPurchasesFromUser(){
		super();
		this.message = "Too many purchases from one user in 1 hour";
	}

	public String getMessage() {
		return message;
	}

}
