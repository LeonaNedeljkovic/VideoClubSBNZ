package com.videoClub.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.ObjectFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videoClub.comparator.AgeCategoryReportComparator;
import com.videoClub.comparator.RecommendedFilmComparator;
import com.videoClub.dto.FilmDTO;
import com.videoClub.exception.EntityNotFound;
import com.videoClub.exception.FilmNotReviewed;
import com.videoClub.model.Artist;
import com.videoClub.model.Film;
import com.videoClub.model.RegisteredUser;
import com.videoClub.model.Review;
import com.videoClub.model.User;
import com.videoClub.model.drl.FinalReport;
import com.videoClub.model.drl.RecommendedFilm;
import com.videoClub.model.enumeration.AgeCategory;
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
		film.setWrittenBy(artistService.getOne(filmDTO.getWrittenId()));
		film.setName(filmDTO.getName());
		film.setDescription(filmDTO.getDescription());
		film.setDuration(filmDTO.getDuration());
		film.setYear(filmDTO.getYear());
		film.setGenre(Genre.valueOf(filmDTO.getGenre()));
		film.setRating(0);
		film.setPoster(filmDTO.getPoster());
		
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
		KieSession kieSession = kieContainer.newKieSession("reviewRulesSession");
		kieSession.insert(review);
		kieSession.fireAllRules();
		kieSession.dispose();
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
		((RegisteredUser) user).getFavouriteFilms().add(film);
		review.setUser((RegisteredUser) user);
		KieSession kieSession = kieContainer.newKieSession("reviewRulesSession");
		kieSession.insert(review);
		kieSession.fireAllRules();
		kieSession.dispose();
		RegisteredUser saved = (RegisteredUser) userService.save(user);
		return saved.getFavouriteFilms();
	}
	
	@Override
	public List<RecommendedFilm> getRecommendedFilmsByDefault(RegisteredUser user, int number) {
		return getRecommendedFilms(user, filmRepository.getUnwatchedFilms(user.getId()), number, true);
	}
	
	@Override
	public List<RecommendedFilm> getRecommendedFilmsByArtis(RegisteredUser user, Long artistId, int number) {
		return getRecommendedFilms(user, filmRepository.getFilmsByArtist(artistId), number, false);
	}
	
	@Override
	public List<RecommendedFilm> getRecommendedFilmsByGenre(RegisteredUser user, Genre genre, int number) {
		return getRecommendedFilms(user, filmRepository.getByGenre(genre), number, false);
	}

	@Override
	public List<RecommendedFilm> getRecommendedFilms(RegisteredUser user, List<Film> films, int number, boolean strip) {
		KieSession kieSession = kieContainer.newKieSession("filmRecommendationRulesSession");
		kieSession.getAgenda().getAgendaGroup("user-flags").setFocus();
		kieSession.insert(user);
		for(Artist a : artistService.getWatchedArtists(user.getId())){
			kieSession.insert(a);
		}
		insertGenres(kieSession);
		
		kieSession.getAgenda().getAgendaGroup("user-flags").setFocus();
		kieSession.fireAllRules();
		
		kieSession.getAgenda().getAgendaGroup("flags-recommendation").setFocus();
		for(Review r : reviewService.getReviewsByAgeAndGender(user.getId(), user.getAgeCategory(), user.getGender())){
			kieSession.insert(r);
		}
		List<RecommendedFilm> recommendedFilms = new ArrayList<RecommendedFilm>();
		for(Film f : films){
			kieSession.insert(f);
		}
		kieSession.fireAllRules();
		Collection<?> output = kieSession.getObjects(new RecommendedFilmObjectFilter());
		for(Object o : output){
			RecommendedFilm rf = (RecommendedFilm) o; 
			recommendedFilms.add(rf);
		}
		kieSession.dispose();
		Collections.sort(recommendedFilms, new RecommendedFilmComparator());
		if(strip){
			recommendedFilms = recommendedFilms.stream().filter(recommendedFilm -> recommendedFilm.getRecommendPoints() > 0)
                .collect(Collectors.toList());
		}
		if(recommendedFilms.size() > number) {
			recommendedFilms = recommendedFilms.subList(0, number);
		}
		return recommendedFilms;
	}

	@Override
	public List<Film> save(List<Film> films) {
		return filmRepository.saveAll(films);
	}
	
	public void insertGenres(KieSession kieSession){
		kieSession.insert(Genre.ACTION);
		kieSession.insert(Genre.ADVENTURE);
		kieSession.insert(Genre.ANIMATED);
		kieSession.insert(Genre.COMEDY);
		kieSession.insert(Genre.DOCUMENTARY);
		kieSession.insert(Genre.DRAMA);
		kieSession.insert(Genre.HISTORICAL);
		kieSession.insert(Genre.HORROR);
		kieSession.insert(Genre.MUSIC);
		kieSession.insert(Genre.SCIFI);
		kieSession.insert(Genre.THRILLER);
		kieSession.insert(Genre.WESTERN);
		kieSession.insert(Genre.FAMILY);
	}
	
	class RecommendedFilmObjectFilter implements ObjectFilter {
        @Override
        public boolean accept(Object object) {
            String className = object.getClass().getName();
            return className.contains("RecommendedFilm");
        }
    }

	@Override
	public List<Film> getTopRated(int number) {
		List<Film> films = filmRepository.getTopRated();
		if(number < films.size() && number > 0){
			films = films.subList(0, number);
		}
		return films;
	}

	@Override
	public List<Film> getMostPopular(int number) {
		List<Film> films = filmRepository.getMostPopular();
		if(number < films.size() && number > 0){
			films = films.subList(0, number);
		}
		return films;
	}

	@Override
	public List<Film> getByGenre(Genre genre) {
		return filmRepository.getByGenre(genre);
	}
	
	@Override
	public List<Film> getByArtist(Long artistId) {
		return filmRepository.getFilmsByArtist(artistId);
	}

	@Override
	public List<Film> getByName(String filmName) {
		return filmRepository.getByName(filmName);
	}

	@Override
	public FinalReport getRecommendedInfo(FilmDTO filmDTO) {
		Film film = new Film();
		List<Artist> actors = new ArrayList<Artist>();
		for(Long id : filmDTO.getActorIds()){
			actors.add(artistService.getOne(id));
		}
		film.setActors(actors);
		film.setDirector(artistService.getOne(filmDTO.getDirectorId()));
		film.setWrittenBy(artistService.getOne(filmDTO.getWrittenId()));
		film.setName(filmDTO.getName());
		film.setDescription(filmDTO.getDescription());
		film.setDuration(filmDTO.getDuration());
		film.setYear(filmDTO.getYear());
		film.setGenre(Genre.valueOf(filmDTO.getGenre()));
		film.setRating(0);
		film.setPoster(filmDTO.getPoster());
		
		KieSession kieSession = kieContainer.newKieSession("adminRecommendationRulesSession");
		kieSession.insert(film);
		for(RegisteredUser registeredUser: userService.getAllRegisteredUsers()) {
			kieSession.insert(registeredUser);
		}
		
		kieSession.insert(AgeCategory.CHILD);
		kieSession.insert(AgeCategory.TEEN);
		kieSession.insert(AgeCategory.YOUNG_ADULT);
		kieSession.insert(AgeCategory.ADULT);
		kieSession.insert(AgeCategory.ELDER);
		
		kieSession.fireAllRules();
		kieSession.dispose();
		
		FinalReport report = null;
		Collection<?> types = kieSession.getObjects(new FinalReportObjectFilter());
		for(Object o : types){
			report = (FinalReport) o;
			Collections.sort(report.getAgeCategoryReports(), new AgeCategoryReportComparator());
		}
		return report;
	}
	
	class FinalReportObjectFilter implements ObjectFilter {
        @Override
        public boolean accept(Object object) {
            String className = object.getClass().getName();
            return className.contains("FinalReport");
        }
    }
}
