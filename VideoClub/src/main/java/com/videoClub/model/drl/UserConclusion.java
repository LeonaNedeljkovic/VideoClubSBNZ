package com.videoClub.model.drl;

import java.util.ArrayList;
import java.util.List;

import com.videoClub.model.RegisteredUser;

public class UserConclusion {

	private RegisteredUser user;
	private List<Badge> badges = new ArrayList<Badge>();
	
	public UserConclusion(RegisteredUser user, List<Badge> badges) {
		super();
		this.user = user;
		this.badges = badges;
	}

	public UserConclusion() {
		super();
	}

	public RegisteredUser getUser() {
		return user;
	}

	public void setUser(RegisteredUser user) {
		this.user = user;
	}

	public List<Badge> getBadges() {
		return badges;
	}

	public void setBadges(List<Badge> badges) {
		this.badges = badges;
	}
}
