package com.videoClub.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.videoClub.bean.BronzeImmunity;
import com.videoClub.bean.BronzeTitle;
import com.videoClub.bean.GoldImmunity;
import com.videoClub.bean.GoldTitle;
import com.videoClub.bean.SilverImmunity;
import com.videoClub.bean.SilverTitle;
import com.videoClub.model.enumeration.Rank;
import com.videoClub.service.RuleService;

@Configuration
public class Config {

	@Autowired
	private RuleService ruleService;
	
	@Bean
	public BronzeImmunity bronzeImmunity() {
		return new BronzeImmunity(ruleService.getImmunityPoints(Rank.BRONZE));
	}
	
	@Bean
	public SilverImmunity silverImmunity() {
		return new SilverImmunity(ruleService.getImmunityPoints(Rank.SILVER));
	}
	
	@Bean
	public GoldImmunity goldImmunity() {
		return new GoldImmunity(ruleService.getImmunityPoints(Rank.GOLD));
	}
	
	@Bean
	public BronzeTitle bronzeTitle() {
		int acquirePoints = ruleService.getTitleAcquirePoints(Rank.BRONZE);
		int savePoints = ruleService.getTitleSavePoints(Rank.BRONZE);
		int rewardPoints = ruleService.getTitleRewardPoints(Rank.BRONZE);
		return new BronzeTitle(acquirePoints, savePoints, rewardPoints);
	}
	
	@Bean
	public SilverTitle silverTitle() {
		int acquirePoints = ruleService.getTitleAcquirePoints(Rank.SILVER);
		int savePoints = ruleService.getTitleSavePoints(Rank.SILVER);
		int rewardPoints = ruleService.getTitleRewardPoints(Rank.SILVER);
		return new SilverTitle(acquirePoints, savePoints, rewardPoints);
	}
	
	@Bean
	public GoldTitle goldTitle() {
		int acquirePoints = ruleService.getTitleAcquirePoints(Rank.GOLD);
		int savePoints = ruleService.getTitleSavePoints(Rank.GOLD);
		int rewardPoints = ruleService.getTitleRewardPoints(Rank.GOLD);
		return new GoldTitle(acquirePoints, savePoints, rewardPoints);
	}
}
