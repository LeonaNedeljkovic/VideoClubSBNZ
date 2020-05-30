package com.videoClub.service;

import java.util.List;
import java.util.Optional;

import com.videoClub.dto.ReviewDTO;
import com.videoClub.model.RegisteredUser;
import com.videoClub.model.Review;
import com.videoClub.model.drl.UserConclusion;
import com.videoClub.model.enumeration.Genre;

public interface ReviewService {

	public Review save(ReviewDTO reviewDTO, RegisteredUser user);
	public Review getOne(Long id);
	public Optional<Review> findByFilmIdAndUserId(Long film, Long user);
	public List<Review> getLastReviews(Long userId);
	public UserConclusion fireRulesForNewReview(RegisteredUser user);
	public Review save(Review review);
	public List<Genre> topThreeUserGenre(Long id);
}
