package com.videoClub.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.videoClub.model.Review;
import com.videoClub.model.enumeration.AgeCategory;
import com.videoClub.model.enumeration.Gender;
import com.videoClub.model.enumeration.Genre;

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
	
	@Query("SELECT r.film.genre from Review r where r.user.id = ?1 and r.watched = 1 "+
	"GROUP BY r.film.genre ORDER BY count(r.film.genre) DESC")
	public Page<Genre> topThreeUserGenre(Long userId,Pageable pageable);
	
	@Query("SELECT r FROM Review r " +
		    "WHERE r.user.id != ?1 AND r.user.ageCategory = ?2 AND r.user.gender = ?3")
	public List<Review> getReviewsByAgeAndGender(Long userId, AgeCategory category, Gender gender);
}
