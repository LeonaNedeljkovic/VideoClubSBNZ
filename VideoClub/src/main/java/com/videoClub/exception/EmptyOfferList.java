package com.videoClub.exception;

@SuppressWarnings("serial")
public class EmptyOfferList extends RuntimeException {

	private String message;
	
	public EmptyOfferList(){
		super();
		this.message = "List of discount offers can not be empty.";
	}

	public String getMessage() {
		return message;
	}
}
