package com.videoClub.service;

import java.util.List;

import com.videoClub.dto.FilmDTO;
import com.videoClub.model.Film;
import com.videoClub.model.RegisteredUser;
import com.videoClub.model.User;
import com.videoClub.model.drl.FinalReport;
import com.videoClub.model.drl.RecommendedFilm;
import com.videoClub.model.enumeration.Genre;

public interface FilmService {

	public Film save(FilmDTO filmDTO);
	public Film getOne(Long id);
	public List<Film> getAll();
	public List<Film> getTopRated(int number);
	public List<Film> getMostPopular(int number);
	public List<Film> getByName(String filmName);
	public List<Film> getByGenre(Genre genre);
	public List<Film> getByArtist(Long artistId);
	public void delete(Long id);
	public Film rateFilm(Long filmId, Integer rate, RegisteredUser user);
	public List<Film> saveFilmToFavourites(Long filmId, User user);
	public FinalReport getRecommendedInfo(FilmDTO film);
	public List<Film> save(List<Film> films);
	public List<RecommendedFilm> getRecommendedFilmsByDefault(RegisteredUser user, int number);
	public List<RecommendedFilm> getRecommendedFilmsByArtis(RegisteredUser user, Long artistId, int number);
	public List<RecommendedFilm> getRecommendedFilmsByGenre(RegisteredUser user, Genre genre, int number);
	public List<RecommendedFilm> getRecommendedFilms(RegisteredUser user, List<Film> films, int number, boolean strip);
}
