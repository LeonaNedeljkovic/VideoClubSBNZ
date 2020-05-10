package com.videoClub.bean;

import org.springframework.stereotype.Component;

@Component
public class GoldImmunity {

	private int AcquirePoints;
	
	public GoldImmunity(int acquirePoints) {
		super();
		AcquirePoints = acquirePoints;
	}

	public GoldImmunity() {
		super();
	}

	public int getAcquirePoints() {
		return AcquirePoints;
	}

	public void setAcquirePoints(int acquirePoints) {
		AcquirePoints = acquirePoints;
	}
}
