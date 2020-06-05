package com.videoClub.template;

import com.videoClub.model.enumeration.AgeCategory;
import com.videoClub.model.enumeration.Rank;

public class FreeMinutes {

	private AgeCategory ageCategory;
	private Rank title;
	private int amount;
	private String header;
	private String body;
	
	public FreeMinutes(AgeCategory ageCategory, Rank title, int amount, String header,
			String body) {
		super();
		this.ageCategory = ageCategory;
		this.title = title;
		this.amount = amount;
		this.header = header;
		this.body = body;
	}
	
	public FreeMinutes(AgeCategory ageCategory, int amount, String header,
			String body) {
		super();
		this.ageCategory = ageCategory;
		this.amount = amount;
		this.header = header;
		this.body = body;
	}
	
	public FreeMinutes(Rank title, int amount, String header,
			String body) {
		super();
		this.title = title;
		this.amount = amount;
		this.header = header;
		this.body = body;
	}

	public FreeMinutes() {
		super();
	}

	public AgeCategory getAgeCategory() {
		return ageCategory;
	}

	public void setAgeCategory(AgeCategory ageCategory) {
		this.ageCategory = ageCategory;
	}

	public Rank getTitle() {
		return title;
	}

	public void setTitle(Rank title) {
		this.title = title;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
