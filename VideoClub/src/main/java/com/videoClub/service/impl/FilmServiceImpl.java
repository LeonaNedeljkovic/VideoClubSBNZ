package com.videoClub.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videoClub.dto.FilmDTO;
import com.videoClub.exception.EntityNotFound;
import com.videoClub.exception.FilmNotReviewed;
import com.videoClub.exception.ReviewNotRated;
import com.videoClub.model.Artist;
import com.videoClub.model.Film;
import com.videoClub.model.RegisteredUser;
import com.videoClub.model.Review;
import com.videoClub.model.User;
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
	public Film rateFilm(Long filmId, Integer rate, User user) {
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
		Film retval = filmRepository.save(film);
		reviewService.save(review);
		reviewService.fireRulesForNewReview(user);
		return retval;
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
}
