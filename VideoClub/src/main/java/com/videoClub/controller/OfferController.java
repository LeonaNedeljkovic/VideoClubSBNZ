package com.videoClub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.videoClub.service.OfferService;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class OfferController {

	@Autowired
	private OfferService offerService;
	
	@PostMapping(value = "/offer", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void createOffer() {
		// TODO implementirati...
	}
	
	@PutMapping(value = "/offer/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void updateOffer() {
		// TODO implementirati...
	}
	
	@GetMapping(value = "/offer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void getOffer() {
		// TODO implementirati...
	}
	
	@GetMapping(value = "/offers", produces = MediaType.APPLICATION_JSON_VALUE)
	public void getOffers() {
		// TODO implementirati...
	}
	
	@DeleteMapping(value = "/offer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteOffer() {
		// TODO implementirati...
	}
}
