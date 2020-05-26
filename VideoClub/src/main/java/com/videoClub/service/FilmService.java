package com.videoClub.service;

import java.util.List;

import com.videoClub.dto.FilmDTO;
import com.videoClub.model.Film;
import com.videoClub.model.RegisteredUser;
import com.videoClub.model.User;

public interface FilmService {

	public Film save(FilmDTO filmDTO);
	public Film getOne(Long id);
	public List<Film> getAll();
	//public List<VideoContent> getByActors();
	//public List<VideoContent> getByDirector(Long directorId);
	public void delete(Long id);
	public Film rateFilm(Long filmId, Integer rate, RegisteredUser user);
	public List<Film> saveFilmToFavourites(Long filmId, User user);
}
