package com.videoClub.dto;

import java.util.ArrayList;
import java.util.List;

public class ActionDTO {

	private Long id;
	private String description;
	private String actionType;
	private String rank;
	private int amount;
	private List<String> genres = new ArrayList<String>();
	private List<Long> discountOffersIds = new ArrayList<Long>();

	public ActionDTO(Long id, String description, String actionType, String rank, int amount, List<String> genres,
			List<Long> discountOffersIds) {
		super();
		this.id = id;
		this.description = description;
		this.actionType = actionType;
		this.rank = rank;
		this.amount = amount;
		this.genres = genres;
		this.discountOffersIds = discountOffersIds;
	}

	public ActionDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public List<String> getGenres() {
		return genres;
	}

	public void setGenres(List<String> genres) {
		this.genres = genres;
	}

	public List<Long> getDiscountOffersIds() {
		return discountOffersIds;
	}

	public void setDiscountOffersIds(List<Long> discountOffersIds) {
		this.discountOffersIds = discountOffersIds;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}
}
