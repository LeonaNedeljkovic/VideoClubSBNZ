package com.videoClub.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.videoClub.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{

	@Query("SELECT DISTINCT review FROM Review review " +
		    "WHERE review.film.id = ?1 AND review.user.id = ?2")
	public List<Review> getByVideoContent(Long id, Long userId);
	
	public Optional<Review> findByFilmIdAndUserId(Long film, Long user);
	
	@Query("SELECT r FROM Review r " +
		    "WHERE r.user.id = ?1 AND r.film.id NOT IN "
		    + "(SELECT f.id FROM r.user.favouriteFilms f)  "+
			"ORDER BY r.watchedTime DESC")
	public List<Review> getLastReviews(Long userId);
	
	@Query("SELECT DISTINCT r FROM Review r " +
		    "WHERE r.user.id = ?1 AND r.film.id IN "+
			"(SELECT f.id FROM r.user.favouriteFilms f) ")
	public List<Review> getReviewsOfFavouriteFilms(Long userId);
}
