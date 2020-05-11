package com.videoClub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.videoClub.service.VideoContentService;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class VideoContentController {

	@Autowired
	private VideoContentService videoContentService;
	
	@PostMapping(value = "/video_content", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void createVideoContent() {
		// TODO implementirati...
	}
	
	@PutMapping(value = "/video_content/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void updateVideoContent() {
		// TODO implementirati...
	}
	
	@GetMapping(value = "/video_content/info/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void getVideoContentInfo() {
		// TODO implementirati...
	}
	
	@GetMapping(value = "/video_content/watch/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_USER')")
	public void getVideoContentWatch() {
		// TODO implementirati...
	}
	
	@GetMapping(value = "/video_contents", produces = MediaType.APPLICATION_JSON_VALUE)
	public void getVideoContents() {
		// TODO implementirati...
	}
	
	@GetMapping(value = "/video_contents/actors", produces = MediaType.APPLICATION_JSON_VALUE)
	public void geVideoContentsByActors() {
		// TODO implementirati...
	}
	
	@GetMapping(value = "/video_contents/director", produces = MediaType.APPLICATION_JSON_VALUE)
	public void geVideoContentsByDirector() {
		// TODO implementirati...
	}
	
	@GetMapping(value = "/video_contents/genre", produces = MediaType.APPLICATION_JSON_VALUE)
	public void geVideoContentsByGenre() {
		// TODO implementirati...
	}
	
	@DeleteMapping(value = "/video_content/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteVideoContent() {
		// TODO implementirati...
	}
}
