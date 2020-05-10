package com.videoClub.bean;

import org.springframework.stereotype.Component;

@Component
public class SilverImmunity {

	private int AcquirePoints;
	
	public SilverImmunity(int acquirePoints) {
		super();
		AcquirePoints = acquirePoints;
	}

	public SilverImmunity() {
		super();
	}

	public int getAcquirePoints() {
		return AcquirePoints;
	}

	public void setAcquirePoints(int acquirePoints) {
		AcquirePoints = acquirePoints;
	}
}
