package com.videoClub.exception;

@SuppressWarnings("serial")
public class ActionNotDeletable extends RuntimeException  {

private String message;
	
	public ActionNotDeletable(){
		super();
		this.message = "Action event must contain at least one action.";
	}

	public String getMessage() {
		return message;
	}
}
