package com.videoClub.dto;

public class OfferDTO {

	private Long id;
	private int minutes;
	private double price;
	private int discount;
	
	public OfferDTO() {
		super();
	}

	public OfferDTO(Long id, int minutes, double price, int discount) {
		super();
		this.id = id;
		this.minutes = minutes;
		this.price = price;
		this.discount = discount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}
}
