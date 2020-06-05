package com.videoClub.exception;

@SuppressWarnings("serial")
public class RestrictedAgeCategory extends RuntimeException {

	private String message;
	
	public RestrictedAgeCategory(){
		super();
		this.message = "This content is restricted for your age.";
	}

	public String getMessage() {
		return message;
	}
}