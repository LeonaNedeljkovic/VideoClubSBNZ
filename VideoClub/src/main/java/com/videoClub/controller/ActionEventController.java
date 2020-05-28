package com.videoClub.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.videoClub.dto.ActionEventDTO;
import com.videoClub.dto.MessageDto;
import com.videoClub.model.ActionEvent;
import com.videoClub.model.Purchase;
import com.videoClub.model.RegisteredUser;
import com.videoClub.model.User;
import com.videoClub.model.enumeration.Rank;
import com.videoClub.service.ActionEventService;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
//@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class ActionEventController {

	@Autowired
	private ActionEventService actionEventService;

	@Autowired
	private KieContainer kieContainer;

	@PostMapping(value = "/action_event", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<ActionEvent> createActionEvent(@RequestBody ActionEventDTO actionEventDTO) {
		return new ResponseEntity<>(actionEventService.save(actionEventDTO), HttpStatus.OK);
	}

	@PutMapping(value = "/action_event", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	// @PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<ActionEvent> updateActionEvent(@RequestBody ActionEventDTO actionEventDTO) {
		return new ResponseEntity<>(actionEventService.update(actionEventDTO), HttpStatus.OK);
	}

	@GetMapping(value = "/action_event/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ActionEvent> getActionEvent(@PathVariable(value = "id") Long id) {
		return new ResponseEntity<>(actionEventService.getOne(id), HttpStatus.OK);
	}

	@GetMapping(value = "/action_event/action/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ActionEvent> getActionEventByAction(@PathVariable(value = "id") Long id) {
		return new ResponseEntity<>(actionEventService.getByActionId(id), HttpStatus.OK);
	}

	@GetMapping(value = "/action_events", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ActionEvent>> getActionEvents() {
		return new ResponseEntity<>(actionEventService.getAll(), HttpStatus.OK);
	}

	@DeleteMapping(value = "/action_event/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	// @PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<MessageDto> deleteActionEvent(@PathVariable(value = "id") Long id) {
		actionEventService.delete(id);
		return new ResponseEntity<>(new MessageDto("OK", "Action event successfully deleted."), HttpStatus.OK);
	}

	@Scheduled(cron = "* * * * * *")
	public void deleteExpiredActionEvents() {
		List<ActionEvent> actionEvents = actionEventService.getAll();
		if (actionEvents.size() > 0) {
			KieSession kieSession = initializeKieSession("actionRulesSession");
			for (ActionEvent ae : actionEvents) {
				kieSession.insert(ae);
			}
			kieSession.fireAllRules();
			kieSession.dispose();
		}
	}

	private KieSession initializeKieSession(String sessionName) {
		KieSession kieSession = kieContainer.newKieSession(sessionName);
		kieSession.setGlobal("actionEventService", actionEventService);
		kieSession.setGlobal("now",LocalDate.now());
		return kieSession;
	}
}
