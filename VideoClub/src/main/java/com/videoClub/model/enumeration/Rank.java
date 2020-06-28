package com.videoClub.model.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Rank {
	NONE, BRONZE, SILVER, GOLD;
	
	@JsonValue
	public String getRank(){
		return this.toString();
	}
	
	public Rank getLowerRank(){
		if(this.equals(BRONZE)){
			return NONE;
		}
		else if(this.equals(SILVER)){
			return BRONZE;
		}
		else if(this.equals(GOLD)){
			return SILVER;
		}
		else{
			return NONE;
		}
	}
	
	public Rank getHigherRank(){
		if(this.equals(BRONZE)){
			return SILVER;
		}
		else if(this.equals(SILVER)){
			return GOLD;
		}
		else if(this.equals(GOLD)){
			return GOLD;
		}
		else{
			return BRONZE;
		}
	}
	
	public boolean equalsOrHigher(Rank rank){
		if(this.equals(NONE)){
			return true;
		}
		if(this.equals(BRONZE) && !(rank.equals(NONE))){
			return true;
		}
		if(this.equals(SILVER) && (rank.equals(SILVER) || rank.equals(GOLD))){
			return true;
		}
		if(this.equals(GOLD) && rank.equals(GOLD)){
			return true;
		}
		return false;
	}
}
