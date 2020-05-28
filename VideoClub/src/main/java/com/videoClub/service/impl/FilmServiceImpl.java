package com.videoClub.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videoClub.comparator.RecommendedFilmComparator;
import com.videoClub.dto.FilmDTO;
import com.videoClub.exception.EntityNotFound;
import com.videoClub.exception.FilmNotReviewed;
import com.videoClub.exception.ReviewNotRated;
import com.videoClub.model.Artist;
import com.videoClub.model.Film;
import com.videoClub.model.RegisteredUser;
import com.videoClub.model.Review;
import com.videoClub.model.User;
import com.videoClub.model.drl.RecommendedFilm;
import com.videoClub.model.drl.UserConclusion;
import com.videoClub.model.enumeration.Genre;
import com.videoClub.repository.FilmRepository;
import com.videoClub.service.ArtistService;
import com.videoClub.service.FilmService;
import com.videoClub.service.ReviewService;
import com.videoClub.service.UserService;

@Service
public class FilmServiceImpl implements FilmService{

	@Autowired
	private FilmRepository filmRepository;
	
	@Autowired
	private ArtistService artistService;
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private KieContainer kieContainer;

	@Override
	public Film save(FilmDTO filmDTO) {
		Film film = new Film();
		List<Artist> actors = new ArrayList<Artist>();
		for(Long id : filmDTO.getActorIds()){
			actors.add(artistService.getOne(id));
		}
		film.setActors(actors);
		film.setDirector(artistService.getOne(filmDTO.getDirectorId()));
		film.setName(filmDTO.getName());
		film.setDescription(filmDTO.getDescription());
		film.setDuration(filmDTO.getDuration());
		film.setYear(filmDTO.getYear());
		film.setGenre(Genre.valueOf(filmDTO.getGenre()));
		film.setRating(0);
		return filmRepository.save(film);
	}

	@Override
	public Film getOne(Long id) {
		Optional<Film> video = filmRepository.findById(id);
		if(video.isPresent()){
			return video.get();
		}
		else{
			throw new EntityNotFound(id);
		}
	}

	@Override
	public List<Film> getAll() {
		return filmRepository.findAll();
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Film rateFilm(Long filmId, Integer rate, RegisteredUser user) {
		Optional<Review> reviewOpt = reviewService.findByFilmIdAndUserId(filmId, user.getId());
		Review review = null;
		if(reviewOpt.isPresent()){
			review = reviewOpt.get();
		}
		else{
			throw new FilmNotReviewed();
		}
		review.setRate(rate);
		Film film = getOne(filmId);
		film.addNewRate(rate, review.getId());
		reviewService.save(review);
		return filmRepository.save(film);
	}

	@Override
	public List<Film> saveFilmToFavourites(Long filmId, User user) {
		Optional<Review> reviewOpt = reviewService.findByFilmIdAndUserId(filmId, user.getId());
		Review review = null;
		Film film = getOne(filmId);
		if(reviewOpt.isPresent()){
			review = reviewOpt.get();
		}
		else{
			throw new FilmNotReviewed();
		}
		if(review.getRate() < 5){
			throw new ReviewNotRated();
		}
		((RegisteredUser) user).getFavouriteFilms().add(film);
		RegisteredUser saved = (RegisteredUser) userService.save(user);
		return saved.getFavouriteFilms();
	}

	@Override
	public List<RecommendedFilm> getRecommendedFilms(RegisteredUser user) {
		UserConclusion conclusion = reviewService.fireRulesForNewReview(user);
		List<Film> unwatched = filmRepository.getUnwatchedFilms(user.getId());
		System.out.println("ISPRED");
		KieSession kieSession = kieContainer.newKieSession("filmRecommendationRulesSession");
		kieSession.insert(conclusion);
		List<RecommendedFilm> recommendedFilms = new ArrayList<RecommendedFilm>();
		for(Film f : unwatched){
			RecommendedFilm rf = new RecommendedFilm(0.0,f);
			recommendedFilms.add(rf);
			kieSession.insert(rf);
		}
		kieSession.fireAllRules();
		kieSession.dispose();
		System.out.println("IZA");
		Collections.sort(recommendedFilms, new RecommendedFilmComparator());
		recommendedFilms = recommendedFilms.stream().filter(recommendedFilm -> recommendedFilm.getRecommendPoints() > 0)
                .collect(Collectors.toList());
		if(recommendedFilms.size() > 15) {
			recommendedFilms = recommendedFilms.subList(0, 14);
		}
		return recommendedFilms;
	}
}
