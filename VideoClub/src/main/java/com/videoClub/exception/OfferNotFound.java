package com.videoClub.exception;

@SuppressWarnings("serial")
public class OfferNotFound extends RuntimeException {

	private String message;
	
	public OfferNotFound(Long id){
		super();
		this.message = "Offer with ID " + id +
				" does not exist in system.";
	}

	public String getMessage() {
		return message;
	}
}
