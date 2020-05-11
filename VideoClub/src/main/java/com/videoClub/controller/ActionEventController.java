package com.videoClub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.videoClub.service.ActionEventService;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ActionEventController {

	@Autowired
	private ActionEventService actionEventService;
	
	@PostMapping(value = "/action_event", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void createActionEvent() {
		// TODO implementirati...
	}
	
	@PutMapping(value = "/action_event/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void updateActionEvent() {
		// TODO implementirati...
	}
	
	@GetMapping(value = "/action_event/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void getActionEvent() {
		// TODO implementirati...
	}
	
	@GetMapping(value = "/action_event/action/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void getActionEventByAction() {
		// TODO implementirati...
	}
	
	@GetMapping(value = "/action_events", produces = MediaType.APPLICATION_JSON_VALUE)
	public void getActionEvents() {
		// TODO implementirati...
	}
	
	@DeleteMapping(value = "/action_event/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteActionEvent() {
		// TODO implementirati...
	}
}
