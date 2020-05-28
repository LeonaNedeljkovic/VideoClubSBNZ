package com.videoClub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.videoClub.dto.FilmDTO;
import com.videoClub.exception.NotLoggedIn;
import com.videoClub.model.Film;
import com.videoClub.model.RegisteredUser;
import com.videoClub.model.drl.RecommendedFilm;
import com.videoClub.service.FilmService;
import com.videoClub.service.impl.CustomUserDetailsService;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class FilmController {

	@Autowired
	private FilmService filmService;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@PostMapping(value = "/film", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Film> createFilm(@RequestBody FilmDTO filmDTO) {
		return new ResponseEntity<>(filmService.save(filmDTO), HttpStatus.OK);
	}
	
	@PutMapping(value = "/film/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Film> updateFilm(@RequestBody FilmDTO filmDTO) {
		return null;
	}
	
	@PostMapping(value = "/film/rate/{id}/{rate}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Film> rateFilm(@PathVariable(value = "id") Long id, @PathVariable(value = "rate") Integer rate) {
		RegisteredUser user = (RegisteredUser) this.customUserDetailsService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		if(user == null){
			throw new NotLoggedIn();
		}
		return new ResponseEntity<>(filmService.rateFilm(id, rate, user), HttpStatus.OK);
	}
	
	@PostMapping(value = "/film/favourites/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Film>> addToFavourites(@PathVariable(value = "id") Long id) {
		RegisteredUser user = (RegisteredUser) this.customUserDetailsService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		if(user == null){
			throw new NotLoggedIn();
		}
		return new ResponseEntity<>(filmService.saveFilmToFavourites(id, user), HttpStatus.OK);
	}
	
	@GetMapping(value = "/film/recommended", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	public ResponseEntity<List<RecommendedFilm>> getRecommended() {
		RegisteredUser user = (RegisteredUser) this.customUserDetailsService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		if(user == null){
			throw new NotLoggedIn();
		}
		return new ResponseEntity<>(filmService.getRecommendedFilms(user), HttpStatus.OK);
	}
	
	@GetMapping(value = "/film/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Film> getFilmInfo(@PathVariable(value = "id") Long id) {
		return new ResponseEntity<>(filmService.getOne(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/films", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Film>> getFilms() {
		return new ResponseEntity<>(filmService.getAll(), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/film/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteFilm() {
		// TODO implementirati...
	}
}