package com.videoClub.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.videoClub.model.enumeration.Rank;

@Entity
public class FreeMinutes extends Action {

	@Column(name = "amount")
	private int amount;

	public FreeMinutes() {
		super();
	}
	
	public FreeMinutes(Long id, String description, ActionEvent actionEvent, Rank titleRank,
			int amount) {
		super(id, description, actionEvent, titleRank);
		this.amount = amount;
	}


	public FreeMinutes(int amount) {
		super();
		this.amount = amount;
	}
	
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
