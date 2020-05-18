package com.videoClub.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "purchase")
public class Purchase {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "date")
	private LocalDate date;
	
	@Column(name = "discount")
	private int discount;
	
	@Column(name = "price")
	private double price;
	
	@Column(name = "purchasedMinutes")
	private int purchasedMinutes;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "offer_id")
	@JsonIgnore
	private Offer offer;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private RegisteredUser user;

	public Purchase() {
		super();
	}

	public Purchase(Long id, LocalDate date, int discount, double price, int purchasedMinutes, Offer offer,
			RegisteredUser user) {
		super();
		this.id = id;
		this.date = date;
		this.discount = discount;
		this.price = price;
		this.purchasedMinutes = purchasedMinutes;
		this.offer = offer;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getPurchasedMinutes() {
		return purchasedMinutes;
	}

	public void setPurchasedMinutes(int purchasedMinutes) {
		this.purchasedMinutes = purchasedMinutes;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public RegisteredUser getUser() {
		return user;
	}

	public void setUser(RegisteredUser user) {
		this.user = user;
	}
}
