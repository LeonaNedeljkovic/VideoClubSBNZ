package com.videoClub.exception;

@SuppressWarnings("serial")
public class InvalidDiscount extends RuntimeException{

private String message;
	
	public InvalidDiscount(){
		super();
		this.message = "Discount must represent a value between 10 and 75 and at least one offer must be selected.";
	}

	public String getMessage() {
		return message;
	}
}
