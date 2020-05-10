package com.videoClub.service.impl;
import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videoClub.model.enumeration.DefinerType;
import com.videoClub.model.enumeration.Rank;
import com.videoClub.repository.RuleRepository;
import com.videoClub.service.RuleService;

@Service
public class RuleServiceImpl implements RuleService {

	@Autowired
	private RuleRepository ruleRepository;

	@Override
	public int getTitleAcquirePoints(Rank titleRank) {
		List<Integer> values = ruleRepository.getPoints(titleRank, DefinerType.TITLE_ACQUIRED);
		if (!(values.isEmpty())){
			return values.get(0);
		}
		return 0;
	}

	@Override
	public int getTitleSavePoints(Rank titleRank) {
		List<Integer> values = ruleRepository.getPoints(titleRank, DefinerType.TITLE_SAVED);
		if (!(values.isEmpty())){
			return values.get(0);
		}
		return 0;
	}

	@Override
	public int getTitleRewardPoints(Rank titleRank) {
		List<Integer> values = ruleRepository.getPoints(titleRank, DefinerType.TITLE_REWARD);
		if (!(values.isEmpty())){
			return values.get(0);
		}
		return 0;
	}

	@Override
	public int getImmunityPoints(Rank immunityRank) {
		List<Integer> values = ruleRepository.getPoints(immunityRank, DefinerType.IMMUNITY_ACQUIRED);
		if (!(values.isEmpty())){
			return values.get(0);
		}
		return 0;
	}

	@Override
	public void setTitleAcquirePoints(Rank titleRank, int value) {
		ruleRepository.updatePoints(titleRank, DefinerType.TITLE_ACQUIRED, value);
	}

	@Override
	public void setTitleSavePoints(Rank titleRank, int value) {
		ruleRepository.updatePoints(titleRank, DefinerType.TITLE_SAVED, value);
	}

	@Override
	public void setTitleRewardPoints(Rank titleRank, int value) {
		ruleRepository.updatePoints(titleRank, DefinerType.TITLE_REWARD, value);
	}

	@Override
	public void setImmunityPoints(Rank immunityRank, int value) {
		ruleRepository.updatePoints(immunityRank, DefinerType.IMMUNITY_ACQUIRED, value);
	}
}
