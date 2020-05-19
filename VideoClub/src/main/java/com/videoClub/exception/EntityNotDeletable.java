package com.videoClub.exception;

@SuppressWarnings("serial")
public class EntityNotDeletable extends RuntimeException {

private String message;
	
	public EntityNotDeletable(){
		super();
		this.message = "Entity could not be deleted. Check it's relationships with other entities and try again.";
	}

	public String getMessage() {
		return message;
	}
}