package com.videoClub.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videoClub.exception.ArtistNotDeletable;
import com.videoClub.exception.ArtistNotFound;
import com.videoClub.model.Artist;
import com.videoClub.model.VideoContent;
import com.videoClub.repository.ArtistRepository;
import com.videoClub.service.ArtistService;
import com.videoClub.service.VideoContentService;

@Service
public class ArtistServiceImpl implements ArtistService{

	@Autowired
	private ArtistRepository artistRepository;
	
	@Autowired
	private VideoContentService videoContentService;

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
			throw new ArtistNotFound(id);
		}
	}

	@Override
	public List<Artist> getAll() {
		return artistRepository.findAll();
	}

	@Override
	public List<Artist> getActorsOfVideoContent(Long videoId) {
		VideoContent video = videoContentService.getOne(videoId);
		return video.getActors();
	}

	@Override
	public Artist getDirectorOfVideoContent(Long videoId) {
		VideoContent video = videoContentService.getOne(videoId);
		return video.getDirector();
	}

	@Override
	public void delete(Long id) {
		Artist artist = getOne(id);
		
		if(!(artist.getRoles().isEmpty()) || !(artist.getDirected().isEmpty())){
			throw new ArtistNotDeletable(artist);
		}
		
		artistRepository.deleteById(id);
	}
}
