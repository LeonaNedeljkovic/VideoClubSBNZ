package com.videoClub.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "offer")
public class Offer {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "minutes")
	private int minutes;
	
	@Column(name = "price")
	private double price;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "discountOffers")
	private List<Discount> discounts = new ArrayList<Discount>();

	public Offer(int minutes, double price) {
		super();
		this.minutes = minutes;
		this.price = price;
	}

	public Offer(Long id, int minutes, double price, List<Discount> discounts) {
		super();
		this.id = id;
		this.minutes = minutes;
		this.price = price;
		this.discounts = discounts;
	}

	public Offer() {
		super();
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
	
	public Double getPriceDouble() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<Discount> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(List<Discount> discounts) {
		this.discounts = discounts;
	}
}
