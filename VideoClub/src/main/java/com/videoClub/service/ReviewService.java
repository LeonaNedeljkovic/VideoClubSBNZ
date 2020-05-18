package com.videoClub.service;

import java.util.List;

import com.videoClub.dto.ReviewDTO;
import com.videoClub.model.RegisteredUser;
import com.videoClub.model.Review;

public interface ReviewService {

	public Review save(ReviewDTO reviewDTO, RegisteredUser user);
	public Review getOne(Long id);
}
