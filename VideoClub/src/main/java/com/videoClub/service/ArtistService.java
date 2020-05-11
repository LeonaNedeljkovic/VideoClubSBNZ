package com.videoClub.service;

import java.util.List;

import com.videoClub.model.Artist;

public interface ArtistService {

	public Artist save(Artist artist);
	public Artist getOne(Long id);
	public List<Artist> getAll();
	public List<Artist> getActorsOfVideoContent(Long videoId);
	public Artist getDirectorOfVideoContent(Long videoId);
	public void delete(Long id);
}
