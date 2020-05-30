package com.videoClub.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.videoClub.dto.ActionDTO;
import com.videoClub.dto.MessageDto;
import com.videoClub.model.Action;
import com.videoClub.service.ActionService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
//@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class ActionController {

	@Autowired
	private ActionService actionService;
	
	@PutMapping(value = "/action", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Action> updateAction(@RequestBody ActionDTO actionDTO) {
		return new ResponseEntity<>(actionService.update(actionDTO), HttpStatus.OK);
	}
	
	@GetMapping(value = "/action/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Action> getAction(@PathVariable(value = "id") Long id) {
		return new ResponseEntity<>(actionService.getOne(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/actions", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Action>> getActions() {
		return new ResponseEntity<>(actionService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/actions/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Action>> getActionsByActionEvent(@PathVariable(value = "id") Long id) {
		return new ResponseEntity<>(actionService.getByActionEventId(id), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/action/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<MessageDto> deleteAction(@PathVariable(value = "id") Long id) {
		actionService.delete(id);
		return new ResponseEntity<>(new MessageDto("OK", "Action successfully deleted."), HttpStatus.OK);
	}
	
	
}