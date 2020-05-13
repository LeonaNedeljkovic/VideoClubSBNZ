package com.videoClub.exception;

@SuppressWarnings("serial")
public class ActionEventNotFound extends RuntimeException  {

private String message;
	
	public ActionEventNotFound(Long id){
		super();
		this.message = "Action event with ID " + id +
				" does not exist in system.";
	}

	public String getMessage() {
		return message;
	}
}
