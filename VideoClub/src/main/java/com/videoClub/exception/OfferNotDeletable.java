package com.videoClub.exception;

@SuppressWarnings("serial")
public class OfferNotDeletable extends RuntimeException {

private String message;
	
	public OfferNotDeletable(){
		super();
		this.message = "Offer could not be deleted. Check if you have defined discounts for this offer that need to pass or if this is only offer in a system.";
	}

	public String getMessage() {
		return message;
	}
}
