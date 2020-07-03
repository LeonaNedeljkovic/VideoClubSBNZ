package com.videoClub.controller;

import java.util.ArrayList;
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

import com.videoClub.dto.MessageDto;
import com.videoClub.dto.OfferDTO;
import com.videoClub.exception.NotLoggedIn;
import com.videoClub.model.Action;
import com.videoClub.model.Discount;
import com.videoClub.model.Offer;
import com.videoClub.model.RegisteredUser;
import com.videoClub.service.OfferService;
import com.videoClub.service.impl.CustomUserDetailsService;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class OfferController {

	@Autowired
	private OfferService offerService;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@PostMapping(value = "/offer", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Offer> createOffer(@RequestBody Offer offer) {
		return new ResponseEntity<>(offerService.save(offer), HttpStatus.OK);
	}
	
	@PutMapping(value = "/offer/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
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
	
	@GetMapping(value = "/offers/actions", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	public ResponseEntity<List<OfferDTO>> getOffersWithActions() {
		RegisteredUser user = (RegisteredUser) this.customUserDetailsService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		if(user == null){
			throw new NotLoggedIn();
		}
		List<Offer> offers = offerService.getAll();
		List<OfferDTO> offersDto = new ArrayList<OfferDTO>();
		for(Offer offer: offers){
			boolean discountExists = false;
			for(Action action : offer.getDiscounts()){
				for(Action userAction : user.getAction()){
					if(userAction instanceof Discount && userAction.getId() == action.getId()){
						discountExists = true;
						offersDto.add(new OfferDTO(offer.getId(), offer.getMinutes(), offer.getPrice(), ((Discount)action).getAmount()));
					}
				}
			}
			if(!(discountExists)){
				offersDto.add(new OfferDTO(offer.getId(), offer.getMinutes(), offer.getPrice(), 0));
			}
		}
		return new ResponseEntity<>(offersDto, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/offer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<MessageDto> deleteOffer(@PathVariable(value = "id") Long id) {
		offerService.delete(id);
		return new ResponseEntity<>(new MessageDto("OK", "Offer successfully deleted."), HttpStatus.OK);
	}
}
