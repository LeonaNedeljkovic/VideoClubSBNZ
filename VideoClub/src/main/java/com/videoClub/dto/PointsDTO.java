package com.videoClub.dto;

public class PointsDTO {

	private int value;

	public PointsDTO(int value) {
		super();
		this.value = value;
	}

	public PointsDTO() {
		super();
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}