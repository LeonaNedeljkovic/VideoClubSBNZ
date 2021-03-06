package com.videoClub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.videoClub.comparator.ArtistComparator;
import com.videoClub.dto.MessageDto;
import com.videoClub.model.Artist;
import com.videoClub.service.ArtistService;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class ArtistController {

	@Autowired
	private ArtistService artistService;
	
	@PostMapping(value = "/artist", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Artist> createArtist(@RequestBody Artist artist) {
		return new ResponseEntity<>(artistService.save(artist), HttpStatus.OK);
	}
	
	@PutMapping(value = "/artist", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Artist> updateArtist(@RequestBody Artist artist) {
		return new ResponseEntity<>(artistService.save(artist), HttpStatus.OK);
	}
	
	@GetMapping(value = "/artist/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Artist> getArtist(@PathVariable(value = "id") Long id) {
		return new ResponseEntity<>(artistService.getOne(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/artists", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Artist>> getArtists() {
		List<Artist> getAll = artistService.getAll();
		java.util.Collections.sort(getAll, new ArtistComparator());
		return new ResponseEntity<>(getAll, HttpStatus.OK);
	}
	
	@GetMapping(value = "/artists/actors/{videoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Artist>> getActorsOfVideoContent(@PathVariable(value = "videoId") Long videoId) {
		return new ResponseEntity<>(artistService.getActorsOfVideoContent(videoId), HttpStatus.OK);
	}
	
	@GetMapping(value = "/artists/director/{videoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Artist> getDirectorOfVideoContent(@PathVariable(value = "videoId") Long videoId) {
		return new ResponseEntity<>(artistService.getDirectorOfVideoContent(videoId), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/artist/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<MessageDto> deleteArtist(@PathVariable(value = "id") Long id) {
		artistService.delete(id);
		return new ResponseEntity<>(new MessageDto("OK", "Artist successfully deleted."), HttpStatus.OK);
	}

}
