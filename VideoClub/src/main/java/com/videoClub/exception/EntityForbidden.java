package com.videoClub.exception;

@SuppressWarnings("serial")
public class EntityForbidden extends RuntimeException {

	private String message;
	
	public EntityForbidden(){
		super();
		this.message = "You have no access to this content.";
	}

	public String getMessage() {
		return message;
	}
}
