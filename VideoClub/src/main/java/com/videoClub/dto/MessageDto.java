package com.videoClub.dto;

public class MessageDto {
	private String message;
	private String result;

	public MessageDto(String message, String result) {
		super();
		this.message = message;
		this.result = result;
	}

	public MessageDto() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
