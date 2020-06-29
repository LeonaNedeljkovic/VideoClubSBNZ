package com.videoClub.model.drl;

import com.videoClub.model.enumeration.Rank;

public class Immunity {

	private int AcquirePoints;
	private Rank immunityRank;
	
	public Immunity(int acquirePoints, Rank immunityRank) {
		super();
		AcquirePoints = acquirePoints;
		this.immunityRank = immunityRank;
	}

	public Immunity() {
		super();
	}

	public int getAcquirePoints() {
		return AcquirePoints;
	}

	public void setAcquirePoints(int acquirePoints) {
		AcquirePoints = acquirePoints;
	}

	public Rank getImmunityRank() {
		return immunityRank;
	}

	public void setImmunityRank(Rank immunityRank) {
		this.immunityRank = immunityRank;
	}
}
