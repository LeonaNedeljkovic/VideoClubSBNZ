package com.videoClub.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.videoClub.model.enumeration.Rank;

@Entity
public class Discount extends Action {

	@Column(name = "amount")
	private int amount;
	
	@ManyToMany
	@JoinTable(name = "discount_offers", 
			  joinColumns = @JoinColumn(name = "discount_id"), 
			  inverseJoinColumns = @JoinColumn(name = "offer_id"))
	private List<Offer> discountOffers = new ArrayList<Offer>();

	public Discount() {
		super();
	}
	
	public Discount(Long id, String description, ActionEvent actionEvent, Rank titleRank,
			int amount, List<Offer> discountOffers) {
		super(id, description, actionEvent, titleRank);
		this.amount = amount;
		this.discountOffers = discountOffers;
	}

	public Discount(int amount, List<Offer> discountOffers) {
		super();
		this.amount = amount;
		this.discountOffers = discountOffers;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public List<Offer> getDiscountOffers() {
		return discountOffers;
	}

	public void setDiscountOffers(List<Offer> discountOffers) {
		this.discountOffers = discountOffers;
	}
}
