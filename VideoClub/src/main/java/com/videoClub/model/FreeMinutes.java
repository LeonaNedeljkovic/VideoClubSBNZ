package com.videoClub.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.videoClub.model.enumeration.ActionType;
import com.videoClub.model.enumeration.Rank;

@Entity
public class FreeMinutes extends Action {

	@Column(name = "amount")
	private int amount;

	public FreeMinutes() {
		super();
	}
	
	public FreeMinutes(Long id, String description, ActionEvent actionEvent, Rank titleRank, ActionType actionType,
			List<RegisteredUser> users, int amount) {
		super(id, description, actionEvent, titleRank, actionType, users);
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
