package com.videoClub.service;

import com.videoClub.model.enumeration.Rank;

public interface RuleService {

	public int getTitleAcquirePoints(Rank titleRank);
	public int getTitleSavePoints(Rank titleRank);
	public int getTitleRewardPoints(Rank titleRank);
	public int getImmunityPoints(Rank immunityRank);
	
	public void setTitleAcquirePoints(Rank titleRank, int value);
	public void setTitleSavePoints(Rank titleRank, int value);
	public void setTitleRewardPoints(Rank titleRank, int value);
	public void setImmunityPoints(Rank immunityRank, int value);
}
