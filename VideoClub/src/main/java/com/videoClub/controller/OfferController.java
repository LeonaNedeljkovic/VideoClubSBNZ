package com.videoClub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.videoClub.dto.MessageDto;
import com.videoClub.model.Offer;
import com.videoClub.service.OfferService;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class OfferController {

	@Autowired
	private OfferService offerService;
	
	@PostMapping(value = "/offer", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Offer> createOffer(@RequestBody Offer offer) {
		return new ResponseEntity<>(offerService.save(offer), HttpStatus.OK);
	}
	
	@PutMapping(value = "/offer/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Offer> updateOffer(@RequestBody Offer offer) {
		return new ResponseEntity<>(offerService.save(offer), HttpStatus.OK);
	}
	
	@GetMapping(value = "/offer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Offer> getOffer(@PathVariable(value = "id") Long id) {
		return new ResponseEntity<>(offerService.getOne(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/offers", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Offer>> getOffers() {
		return new ResponseEntity<>(offerService.getAll(), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/offer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<MessageDto> deleteOffer(@PathVariable(value = "id") Long id) {
		offerService.delete(id);
		return new ResponseEntity<>(new MessageDto("OK", "Offer successfully deleted."), HttpStatus.OK);
	}
}
