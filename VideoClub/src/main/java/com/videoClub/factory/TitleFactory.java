package com.videoClub.factory;

import com.videoClub.model.drl.Title;
import com.videoClub.model.enumeration.Rank;

public class TitleFactory {

	private Title goldTitle;
	private Title silverTitle;
	private Title bronzeTitle;
	
	public TitleFactory(Title goldTitle, Title silverTitle, Title bronzeTitle) {
		super();
		this.goldTitle = goldTitle;
		this.silverTitle = silverTitle;
		this.bronzeTitle = bronzeTitle;
	}

	public TitleFactory() {
		super();
	}

	public Integer getAcquirePoints(Rank titleRank){
		if(titleRank.equals(Rank.BRONZE)){
			return bronzeTitle.getAcquirePoints();
		}
		else if(titleRank.equals(Rank.SILVER)){
			return silverTitle.getAcquirePoints();
		}
		else if(titleRank.equals(Rank.GOLD)){
			return goldTitle.getAcquirePoints();
		}
		return 0;
	}
	
	public Integer getSavePoints(Rank titleRank){
		if(titleRank.equals(Rank.BRONZE)){
			return bronzeTitle.getSavePoints();
		}
		else if(titleRank.equals(Rank.SILVER)){
			return silverTitle.getSavePoints();
		}
		else if(titleRank.equals(Rank.GOLD)){
			return goldTitle.getSavePoints();
		}
		return 0;
	}
	
	public int getRewardPoints(Rank titleRank){
		if(titleRank.equals(Rank.BRONZE)){
			return bronzeTitle.getRewardPoints();
		}
		else if(titleRank.equals(Rank.SILVER)){
			return silverTitle.getRewardPoints();
		}
		else if(titleRank.equals(Rank.GOLD)){
			return goldTitle.getRewardPoints();
		}
		return 0;
	}
	
	public Rank acquiredTitle(int purchasedMinutes){
		if(purchasedMinutes >= bronzeTitle.getAcquirePoints()
			&& purchasedMinutes < silverTitle.getAcquirePoints()){
			return Rank.BRONZE;
		}
		else if(purchasedMinutes >= silverTitle.getAcquirePoints()
			&& purchasedMinutes < goldTitle.getAcquirePoints()){
			return Rank.SILVER;
		}
		else if(purchasedMinutes >= goldTitle.getAcquirePoints()){
			return Rank.GOLD;
		}
		return Rank.NONE;
	}
	
	public Rank savedTitle(Integer purchasedMinutes, Rank currentTitle){
		if(purchasedMinutes >= bronzeTitle.getSavePoints()
			&& purchasedMinutes < silverTitle.getAcquirePoints()
			&& currentTitle.equals(Rank.BRONZE)){
			return Rank.BRONZE;
		}
		else if(purchasedMinutes >= silverTitle.getSavePoints()
			&& purchasedMinutes < goldTitle.getAcquirePoints()
			&& currentTitle.equals(Rank.SILVER)){
			return Rank.SILVER;
		}
		else if(purchasedMinutes >= goldTitle.getSavePoints()
				&& currentTitle.equals(Rank.GOLD)){
			return Rank.GOLD;
		}
		return Rank.NONE;
	}
}
