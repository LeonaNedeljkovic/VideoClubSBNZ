package com.videoClub.exception;

@SuppressWarnings("serial")
public class EntityNotFound extends RuntimeException  {

private String message;
	
	public EntityNotFound(Long id){
		super();
		this.message = "Entity with ID " + id +
				" could not be found.";
	}

	public String getMessage() {
		return message;
	}
}