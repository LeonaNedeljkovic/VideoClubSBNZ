package com.videoClub.service.impl;

import java.util.List;
import java.util.Optional;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videoClub.dto.ReviewDTO;
import com.videoClub.exception.ReviewNotFound;
import com.videoClub.model.Action;
import com.videoClub.model.FreeContent;
import com.videoClub.model.RegisteredUser;
import com.videoClub.model.Review;
import com.videoClub.model.TimeInterval;
import com.videoClub.repository.ReviewRepository;
import com.videoClub.repository.TimeIntervalRepository;
import com.videoClub.service.ReviewService;
import com.videoClub.service.VideoContentService;

@Service
public class ReviewServiceImpl implements ReviewService{

	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private TimeIntervalRepository timeIntervalRepository;
	
	@Autowired
	private VideoContentService videoContentService;
	
	@Autowired
	private KieContainer kieContainer;
	
	@Override
	public Review save(ReviewDTO reviewDTO, RegisteredUser user) {
		KieSession kieSession = kieContainer.newKieSession("reviewRulesSession");
		List<Review> reviews = reviewRepository.getByVideoContent(reviewDTO.getVideoContentId(), user.getId());
		Review review = null;
		for(Action action : user.getAction()){
			kieSession.insert(action);
		}
		if(reviews.isEmpty()){
			review = new Review();
			review.setUser(user);
			review.setVideoContent(videoContentService.getOne(reviewDTO.getVideoContentId()));
			TimeInterval interval = new TimeInterval();
			interval.setStartMinute(reviewDTO.getStartMinute());
			interval.setEndMinute(reviewDTO.getEndMinute());
			interval.setReview(review);
			review.getTimeIntervals().add(interval);
			kieSession.insert(interval);
		}
		else{
			review = reviews.get(0);
			TimeInterval interval = new TimeInterval();
			interval.setStartMinute(reviewDTO.getStartMinute());
			interval.setEndMinute(reviewDTO.getEndMinute());
			interval.setReview(review);
			review.getTimeIntervals().add(interval);
			kieSession.insert(interval);
		}
		kieSession.insert(user);
		kieSession.fireAllRules();
		kieSession.dispose();
		return reviewRepository.save(review);
	}

	@Override
	public Review getOne(Long id) {
		Optional<Review> review = reviewRepository.findById(id);
		if(review.isPresent()){
			return review.get();
		}
		else{
			throw new ReviewNotFound(id);
		}
	}
}
