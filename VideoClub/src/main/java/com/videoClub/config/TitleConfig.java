package com.videoClub.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.videoClub.model.drl.Immunity;
import com.videoClub.model.drl.Title;
import com.videoClub.model.enumeration.Rank;
import com.videoClub.service.RuleService;

@Configuration
public class TitleConfig {
	
	@Autowired
	private RuleService ruleService;
	
	@Bean(name = "bronzeImmunity")
	public Immunity bronzeImmunity() {
		return new Immunity (ruleService.getImmunityPoints(Rank.BRONZE), Rank.BRONZE);
	}
	
	@Bean(name = "silverImmunity")
	public Immunity silverImmunity() {
		return new Immunity (ruleService.getImmunityPoints(Rank.SILVER), Rank.SILVER);
	}
	
	@Bean(name = "goldImmunity")
	public Immunity goldImmunity() {
		return new Immunity(ruleService.getImmunityPoints(Rank.GOLD), Rank.GOLD);
	}
	
	@Bean(name = "bronzeTitle")
	public Title bronzeTitle() {
		int acquirePoints = ruleService.getTitleAcquirePoints(Rank.BRONZE);
		int savePoints = ruleService.getTitleSavePoints(Rank.BRONZE);
		int rewardPoints = ruleService.getTitleRewardPoints(Rank.BRONZE);
		return new Title(acquirePoints, savePoints, rewardPoints, Rank.BRONZE);
	}
	
	@Bean(name = "silverTitle")
	public Title silverTitle() {
		int acquirePoints = ruleService.getTitleAcquirePoints(Rank.SILVER);
		int savePoints = ruleService.getTitleSavePoints(Rank.SILVER);
		int rewardPoints = ruleService.getTitleRewardPoints(Rank.SILVER);
		return new Title(acquirePoints, savePoints, rewardPoints, Rank.SILVER);
	}
	
	@Bean(name = "goldTitle")
	public Title goldTitle() {
		int acquirePoints = ruleService.getTitleAcquirePoints(Rank.GOLD);
		int savePoints = ruleService.getTitleSavePoints(Rank.GOLD);
		int rewardPoints = ruleService.getTitleRewardPoints(Rank.GOLD);
		return new Title(acquirePoints, savePoints, rewardPoints, Rank.GOLD);
	}

}
