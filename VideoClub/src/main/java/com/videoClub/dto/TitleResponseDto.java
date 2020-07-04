package com.videoClub.dto;

public class TitleResponseDto {

	private int bronzeTitleAcquire;
	private int silverTitleAcquire;
	private int goldTitleAcquire;
	private int bronzeTitleSave;
	private int silverTitleSave;
	private int goldTitleSave;
	private int bronzeTitleReward;
	private int silverTitleReward;
	private int goldTitleReward;
	
	public TitleResponseDto(int bronzeTitleAcquire, int silverTitleAcquire, int goldTitleAcquire, int bronzeTitleSave,
			int silverTitleSave, int goldTitleSave, int bronzeTitleReward, int silverTitleReward, int goldTitleReward) {
		super();
		this.bronzeTitleAcquire = bronzeTitleAcquire;
		this.silverTitleAcquire = silverTitleAcquire;
		this.goldTitleAcquire = goldTitleAcquire;
		this.bronzeTitleSave = bronzeTitleSave;
		this.silverTitleSave = silverTitleSave;
		this.goldTitleSave = goldTitleSave;
		this.bronzeTitleReward = bronzeTitleReward;
		this.silverTitleReward = silverTitleReward;
		this.goldTitleReward = goldTitleReward;
	}

	public TitleResponseDto() {
		super();
	}

	public int getBronzeTitleAcquire() {
		return bronzeTitleAcquire;
	}

	public void setBronzeTitleAcquire(int bronzeTitleAcquire) {
		this.bronzeTitleAcquire = bronzeTitleAcquire;
	}

	public int getSilverTitleAcquire() {
		return silverTitleAcquire;
	}

	public void setSilverTitleAcquire(int silverTitleAcquire) {
		this.silverTitleAcquire = silverTitleAcquire;
	}

	public int getGoldTitleAcquire() {
		return goldTitleAcquire;
	}

	public void setGoldTitleAcquire(int goldTitleAcquire) {
		this.goldTitleAcquire = goldTitleAcquire;
	}

	public int getBronzeTitleSave() {
		return bronzeTitleSave;
	}

	public void setBronzeTitleSave(int bronzeTitleSave) {
		this.bronzeTitleSave = bronzeTitleSave;
	}

	public int getSilverTitleSave() {
		return silverTitleSave;
	}

	public void setSilverTitleSave(int silverTitleSave) {
		this.silverTitleSave = silverTitleSave;
	}

	public int getGoldTitleSave() {
		return goldTitleSave;
	}

	public void setGoldTitleSave(int goldTitleSave) {
		this.goldTitleSave = goldTitleSave;
	}

	public int getBronzeTitleReward() {
		return bronzeTitleReward;
	}

	public void setBronzeTitleReward(int bronzeTitleReward) {
		this.bronzeTitleReward = bronzeTitleReward;
	}

	public int getSilverTitleReward() {
		return silverTitleReward;
	}

	public void setSilverTitleReward(int silverTitleReward) {
		this.silverTitleReward = silverTitleReward;
	}

	public int getGoldTitleReward() {
		return goldTitleReward;
	}

	public void setGoldTitleReward(int goldTitleReward) {
		this.goldTitleReward = goldTitleReward;
	}
}
