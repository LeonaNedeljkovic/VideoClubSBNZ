package com.videoClub.bean;

import org.springframework.stereotype.Component;

@Component
public class BronzeImmunity {

	private int acquirePoints;
	
	public BronzeImmunity(int acquirePoints) {
		super();
		this.acquirePoints = acquirePoints;
	}

	public BronzeImmunity() {
		super();
	}

	public int getAcquirePoints() {
		return acquirePoints;
	}

	public void setAcquirePoints(int acquirePoints) {
		this.acquirePoints = acquirePoints;
	}
}
