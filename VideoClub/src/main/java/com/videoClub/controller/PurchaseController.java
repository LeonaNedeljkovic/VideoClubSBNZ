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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.videoClub.exception.EntityForbidden;
import com.videoClub.exception.NotLoggedIn;
import com.videoClub.model.Purchase;
import com.videoClub.model.RegisteredUser;
import com.videoClub.service.PurchaseService;
import com.videoClub.service.impl.CustomUserDetailsService;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class PurchaseController {

	@Autowired
	private PurchaseService purchaseService;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@PostMapping(value = "/purchase/{offerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	public ResponseEntity<Purchase> createPurchase(@PathVariable(value = "offerId") Long offerId) {
		RegisteredUser user = (RegisteredUser) this.customUserDetailsService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		if(user == null){
			throw new NotLoggedIn();
		}
		return new ResponseEntity<>(purchaseService.save(user, offerId), HttpStatus.OK);
	}
	
	@GetMapping(value = "/purchase/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	public ResponseEntity<Purchase> getPurchase(@PathVariable(value = "id") Long id) {
		RegisteredUser user = (RegisteredUser) this.customUserDetailsService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		if(user == null){
			throw new NotLoggedIn();
		}
		Purchase purchase = purchaseService.getOne(id);
		if(purchase.getUser().getId() != user.getId()){
			throw new EntityForbidden();
		}
		return new ResponseEntity<>(purchase, HttpStatus.OK);
	}
	
	@GetMapping(value = "/purchases", produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	public ResponseEntity<List<Purchase>> getPurchasesByUser() {
		RegisteredUser user = (RegisteredUser) this.customUserDetailsService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		if(user == null){
			throw new NotLoggedIn();
		}
		return new ResponseEntity<>(user.getPurchases(), HttpStatus.OK);
	}
}
