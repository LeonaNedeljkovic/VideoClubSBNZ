package com.videoClub.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.videoClub.service.ActionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ActionController {

	@Autowired
	private ActionService actionService;
	
	@PostMapping(value = "/action", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void createAction() {
		// TODO implementirati...
	}
	
	@PutMapping(value = "/action/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void updateAction() {
		// TODO implementirati...
	}
	
	@GetMapping(value = "/action/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void getAction() {
		// TODO implementirati...
	}
	
	@GetMapping(value = "/actions", produces = MediaType.APPLICATION_JSON_VALUE)
	public void getActions() {
		// TODO implementirati...
	}
	
	@GetMapping(value = "/actions/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void getActionsByActionEvent() {
		// TODO implementirati...
	}
	
	@DeleteMapping(value = "/action/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteAction() {
		// TODO implementirati...
	}
}