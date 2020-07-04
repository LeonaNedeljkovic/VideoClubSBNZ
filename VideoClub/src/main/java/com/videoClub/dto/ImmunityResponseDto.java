package com.videoClub.dto;

public class ImmunityResponseDto {

	private int bronzeImmunityAcquire;
	private int silverImmunityAcquire;
	private int goldImmunityAcquire;
	
	public ImmunityResponseDto(int bronzeImmunityAcquire, int silverImmunityAcquire, int goldImmunityAcquire) {
		super();
		this.bronzeImmunityAcquire = bronzeImmunityAcquire;
		this.silverImmunityAcquire = silverImmunityAcquire;
		this.goldImmunityAcquire = goldImmunityAcquire;
	}

	public ImmunityResponseDto() {
		super();
	}

	public int getBronzeImmunityAcquire() {
		return bronzeImmunityAcquire;
	}

	public void setBronzeImmunityAcquire(int bronzeImmunityAcquire) {
		this.bronzeImmunityAcquire = bronzeImmunityAcquire;
	}

	public int getSilverImmunityAcquire() {
		return silverImmunityAcquire;
	}

	public void setSilverImmunityAcquire(int silverImmunityAcquire) {
		this.silverImmunityAcquire = silverImmunityAcquire;
	}

	public int getGoldImmunityAcquire() {
		return goldImmunityAcquire;
	}

	public void setGoldImmunityAcquire(int goldImmunityAcquire) {
		this.goldImmunityAcquire = goldImmunityAcquire;
	}
}
