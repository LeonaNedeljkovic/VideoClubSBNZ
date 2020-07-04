package com.videoClub.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.videoClub.dto.ReviewDTO;
import com.videoClub.dto.ReviewDetailsDTO;
import com.videoClub.event.FilmWatchEvent;
import com.videoClub.exception.EntityNotFound;
import com.videoClub.model.Action;
import com.videoClub.model.Film;
import com.videoClub.model.RegisteredUser;
import com.videoClub.model.Review;
import com.videoClub.model.TimeInterval;
import com.videoClub.model.enumeration.AgeCategory;
import com.videoClub.model.enumeration.Gender;
import com.videoClub.repository.ReviewRepository;
import com.videoClub.service.ReviewService;
import com.videoClub.service.FilmService;

@Service
public class ReviewServiceImpl implements ReviewService{

	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private FilmService filmService;
	
	@Autowired
	private KieContainer kieContainer;
	
	@Autowired
	@Qualifier(value = "cepReportSession")
	private KieSession cepReportSession;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
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
			review.setRate(0);
			review.setFilm(filmService.getOne(reviewDTO.getVideoContentId()));
			review.setWatchedTime(LocalDateTime.parse(sdf.format(new Date()),df));
			TimeInterval interval = new TimeInterval();
			interval.setStartMinute(reviewDTO.getStartMinute());
			interval.setEndMinute(reviewDTO.getEndMinute());
			interval.setReview(review);
			review.getTimeIntervals().add(interval);
			kieSession.insert(interval);
		}
		else{
			review = reviews.get(0);
			review.setWatchedTime(LocalDateTime.parse(sdf.format(new Date()),df));
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
		cepReportSession.insert(new FilmWatchEvent(filmService.getOne(reviewDTO.getVideoContentId())));
		return reviewRepository.save(review);
	}
	
	@Override
	public ReviewDetailsDTO getReviewDetails(RegisteredUser user, Long filmId){
		ReviewDetailsDTO details = new ReviewDetailsDTO(false, false, 0, false);
		for(Review review : user.getReviews()){
			if(review.getFilm().getId() == filmId){
				details.setStartedWatching(true);
				if(review.isWatched()){
					details.setWatched(true);
				}
				else{
					details.setWatched(false);
				}
				details.setRate(review.getRate());
			}
		}
		for(Film film : user.getFavouriteFilms()){
			if(film.getId() == filmId){
				details.setAddedToFavourites(true);
			}
		}
		return details;
	}
	
	@Override
	public Review save(Review review){
		return reviewRepository.save(review);
	}

	@Override
	public Review getOne(Long id) {
		Optional<Review> review = reviewRepository.findById(id);
		if(review.isPresent()){
			return review.get();
		}
		else{
			throw new EntityNotFound(id);
		}
	}

	@Override
	public Optional<Review> findByFilmIdAndUserId(Long film, Long user) {
		return reviewRepository.findByFilmIdAndUserId(film, user);
	}

	@Override
	public List<Review> getLastReviews(Long userId) {
		return reviewRepository.getLastReviews(userId);
	}
	
	@Override
	public List<Review> getReviewsOfFavouriteFilms(Long userId) {
		return reviewRepository.getReviewsOfFavouriteFilms(userId);
	}

	@Override
	public List<Review> getReviewsByAgeAndGender(Long userId, AgeCategory category, Gender gender) {
		return reviewRepository.getReviewsByAgeAndGender(userId, category, gender);
	}
	
	
}
