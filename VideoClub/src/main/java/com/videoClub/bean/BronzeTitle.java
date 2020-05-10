package com.videoClub.bean;

import org.springframework.stereotype.Component;

@Component
public class BronzeTitle {

	private int AcquirePoints;
	private int SavePoints;
	private int RewardPoints;
	
	public BronzeTitle(int acquirePoints, int savePoints, int rewardPoints) {
		super();
		AcquirePoints = acquirePoints;
		SavePoints = savePoints;
		RewardPoints = rewardPoints;
	}

	public BronzeTitle() {
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
}
