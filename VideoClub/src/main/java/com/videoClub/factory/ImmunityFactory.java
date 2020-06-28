package com.videoClub.factory;

import com.videoClub.bean.Immunity;
import com.videoClub.model.enumeration.Rank;

public class ImmunityFactory {

	private Immunity bronzeImmunity;
	private Immunity silverImmunity;
	private Immunity goldImmunity;
	
	public ImmunityFactory(Immunity bronzeImmunity, Immunity silverImmunity, Immunity goldImmunity) {
		super();
		this.bronzeImmunity = bronzeImmunity;
		this.silverImmunity = silverImmunity;
		this.goldImmunity = goldImmunity;
	}

	public ImmunityFactory() {
		super();
	}
	
	public int getAcquirePoints(Rank immunityRank){
		if(immunityRank.equals(Rank.BRONZE)){
			return bronzeImmunity.getAcquirePoints();
		}
		else if(immunityRank.equals(Rank.SILVER)){
			return silverImmunity.getAcquirePoints();
		}
		else if(immunityRank.equals(Rank.GOLD)){
			return goldImmunity.getAcquirePoints();
		}
		return 0;
	}
	
	public Rank acquiredImmunity(Integer immunityPoints){
		if(immunityPoints >= bronzeImmunity.getAcquirePoints()
				&& immunityPoints < silverImmunity.getAcquirePoints()){
			return Rank.BRONZE;
		}
		else if(immunityPoints >= silverImmunity.getAcquirePoints()
				&& immunityPoints < goldImmunity.getAcquirePoints()){
			return Rank.SILVER;
		}
		else if(immunityPoints >= goldImmunity.getAcquirePoints()){
			return Rank.GOLD;
		}
		else{
			return Rank.NONE;
		}
	}
}
