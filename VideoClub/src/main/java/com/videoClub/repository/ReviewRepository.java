package com.videoClub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.videoClub.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{

	@Query("SELECT DISTINCT review FROM Review review " +
		    "WHERE review.videoContent.id = ?1 AND review.user.id = ?2")
	List<Review> getByVideoContent(Long id, Long userId);
}
