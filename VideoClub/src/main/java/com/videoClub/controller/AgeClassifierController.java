package com.videoClub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.videoClub.model.AgeClassifier;
import com.videoClub.service.AgeClassifierService;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
//@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class AgeClassifierController {

	@Autowired
	private AgeClassifierService ageClassifierService;
	
	@GetMapping(value = "/age_classifiers", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AgeClassifier>> getAll() {
		return new ResponseEntity<>(ageClassifierService.getAll(), HttpStatus.OK);
	}
}
