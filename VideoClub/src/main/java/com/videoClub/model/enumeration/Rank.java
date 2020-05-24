package com.videoClub.model.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Rank {
	NONE, BRONZE, SILVER, GOLD;
	
	@JsonValue
	public String getRank(){
		return this.toString();
	}
}
