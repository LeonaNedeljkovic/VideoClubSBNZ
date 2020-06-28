package com.videoClub.bean;

import com.videoClub.model.enumeration.Rank;

public class Title {

	private int AcquirePoints;
	private int SavePoints;
	private int RewardPoints;
	private Rank titleRank;
	
	public Title(int acquirePoints, int savePoints, int rewardPoints, Rank titleRank) {
		super();
		AcquirePoints = acquirePoints;
		SavePoints = savePoints;
		RewardPoints = rewardPoints;
		this.titleRank = titleRank;
	}

	public Title() {
		super();
	}

	public int getAcquirePoints() {
		return AcquirePoints;
	}

	public void setAcquirePoints(int acquirePoints) {
		AcquirePoints = acquirePoints;
	}

	public int getSavePoints() {
		return SavePoints;
	}

	public void setSavePoints(int savePoints) {
		SavePoints = savePoints;
	}

	public int getRewardPoints() {
		return RewardPoints;
	}

	public void setRewardPoints(int rewardPoints) {
		RewardPoints = rewardPoints;
	}

	public Rank getTitleRank() {
		return titleRank;
	}

	public void setTitleRank(Rank titleRank) {
		this.titleRank = titleRank;
	}
}
