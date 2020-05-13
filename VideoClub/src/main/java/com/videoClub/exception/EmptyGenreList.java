package com.videoClub.exception;

@SuppressWarnings("serial")
public class EmptyGenreList extends RuntimeException {

	private String message;
	
	public EmptyGenreList(){
		super();
		this.message = "List of genres can not be empty.";
	}

	public String getMessage() {
		return message;
	}
}
