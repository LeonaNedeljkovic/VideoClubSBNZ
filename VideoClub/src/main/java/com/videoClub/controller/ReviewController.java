package com.videoClub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.videoClub.dto.ReviewDTO;
import com.videoClub.exception.EntityForbidden;
import com.videoClub.exception.NotLoggedIn;
import com.videoClub.model.RegisteredUser;
import com.videoClub.model.Review;
import com.videoClub.service.ReviewService;
import com.videoClub.service.impl.CustomUserDetailsService;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
//@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@PostMapping(value = "/review", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	public ResponseEntity<Review> saveReview(@RequestBody ReviewDTO reviewDTO) {
		RegisteredUser user = (RegisteredUser) this.customUserDetailsService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		if(user == null){
			throw new NotLoggedIn();
		}
		return new ResponseEntity<>(reviewService.save(reviewDTO, user), HttpStatus.OK);
	}
	
	@GetMapping(value = "/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	public ResponseEntity<List<Review>> getByUser() {
		RegisteredUser user = (RegisteredUser) this.customUserDetailsService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		if(user == null){
			throw new NotLoggedIn();
		}
		return new ResponseEntity<>(user.getReviews(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/review/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	public ResponseEntity<Review> getOne(@PathVariable(value = "id") Long id) {
		RegisteredUser user = (RegisteredUser) this.customUserDetailsService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		if(user == null){
			throw new NotLoggedIn();
		}
		Review review = reviewService.getOne(id);
		if(review.getUser().getId() != user.getId()){
			throw new EntityForbidden();
		}
		return new ResponseEntity<>(review, HttpStatus.OK);
	}
	
}
