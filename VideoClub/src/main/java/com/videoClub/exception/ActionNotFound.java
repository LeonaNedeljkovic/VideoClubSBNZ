package com.videoClub.exception;

@SuppressWarnings("serial")
public class ActionNotFound extends RuntimeException  {

private String message;
	
	public ActionNotFound(Long id){
		super();
		this.message = "Action with ID " + id +
				" does not exist in system.";
	}

	public String getMessage() {
		return message;
	}
}
