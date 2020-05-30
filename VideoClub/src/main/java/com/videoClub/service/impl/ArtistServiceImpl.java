package com.videoClub.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videoClub.exception.EntityNotDeletable;
import com.videoClub.exception.EntityNotFound;
import com.videoClub.model.Artist;
import com.videoClub.model.Film;
import com.videoClub.model.RegisteredUser;
import com.videoClub.model.User;
import com.videoClub.model.drl.ArtistRateBadge;
import com.videoClub.model.drl.UserConclusion;
import com.videoClub.repository.ArtistRepository;
import com.videoClub.service.ArtistService;
import com.videoClub.service.FilmService;
import com.videoClub.service.ReviewService;
import com.videoClub.service.UserService;

@Service
public class ArtistServiceImpl implements ArtistService{

	@Autowired
	private ArtistRepository artistRepository;
	
	@Autowired
	private FilmService filmService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReviewService reviewService;
	

	@Override
	public Artist save(Artist artist) {
		return artistRepository.save(artist);
	}

	@Override
	public Artist getOne(Long id) {
		Optional<Artist> artist = artistRepository.findById(id);
		if(artist.isPresent()){
			return artist.get();
		}
		else{
			throw new EntityNotFound(id);
		}
	}

	@Override
	public List<Artist> getAll() {
		return artistRepository.findAll();
	}

	@Override
	public List<Artist> getActorsOfVideoContent(Long videoId) {
		Film video = filmService.getOne(videoId);
		return video.getActors();
	}

	@Override
	public Artist getDirectorOfVideoContent(Long videoId) {
		Film video = filmService.getOne(videoId);
		return video.getDirector();
	}

	@Override
	public void delete(Long id) {
		Artist artist = getOne(id);
		
		if(!(artist.getRoles().isEmpty()) || !(artist.getDirected().isEmpty())){
			throw new EntityNotDeletable();
		}
		
		artistRepository.deleteById(id);
	}

	@Override
	public List<Artist> getWatchedArtists(Long userId) {
		return artistRepository.getWatchedArtists(userId);
	}

	
}
