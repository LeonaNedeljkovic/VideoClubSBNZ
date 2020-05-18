package com.videoClub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.videoClub.dto.VideoContentDTO;
import com.videoClub.model.VideoContent;
import com.videoClub.service.VideoContentService;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class VideoContentController {

	@Autowired
	private VideoContentService videoContentService;
	
	@PostMapping(value = "/video_content", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<VideoContent> createVideoContent(@RequestBody VideoContentDTO videoContentDTO) {
		return new ResponseEntity<>(videoContentService.save(videoContentDTO), HttpStatus.OK);
	}
	
	@PutMapping(value = "/video_content/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<VideoContent> updateVideoContent(@RequestBody VideoContentDTO videoContentDTO) {
		return null;
	}
	
	@GetMapping(value = "/video_content/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VideoContent> getVideoContentInfo(@PathVariable(value = "id") Long id) {
		return new ResponseEntity<>(videoContentService.getOne(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/video_contents", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<VideoContent>> getVideoContents() {
		return new ResponseEntity<>(videoContentService.getAll(), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/video_content/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteVideoContent() {
		// TODO implementirati...
	}
}
