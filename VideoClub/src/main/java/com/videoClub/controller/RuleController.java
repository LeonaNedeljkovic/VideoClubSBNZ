package com.videoClub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.videoClub.bean.BronzeImmunity;
import com.videoClub.bean.BronzeTitle;
import com.videoClub.bean.GoldImmunity;
import com.videoClub.bean.GoldTitle;
import com.videoClub.bean.SilverImmunity;
import com.videoClub.bean.SilverTitle;
import com.videoClub.dto.PointsDTO;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RuleController {
	
	@Autowired
	private BronzeImmunity bronzeImmunity;
	
	@Autowired
	private SilverImmunity silverImmunity;
	
	@Autowired
	private GoldImmunity goldImmunity;
	
	@Autowired
	private BronzeTitle bronzeTitle;
	
	@Autowired
	private SilverTitle silverTitle;
	
	@Autowired
	private GoldTitle goldTitle;
	
	@GetMapping(value = "/bronze_immunity_points", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PointsDTO> getBronzeImmunity() {
		PointsDTO points = new PointsDTO(bronzeImmunity.getAcquirePoints());
		return new ResponseEntity<>(points, HttpStatus.OK);
	}
	
	@GetMapping(value = "/silver_immunity_points", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PointsDTO> getSilverImmunity() {
		PointsDTO points = new PointsDTO(silverImmunity.getAcquirePoints());
		return new ResponseEntity<>(points, HttpStatus.OK);
	}
	
	@GetMapping(value = "/gold_immunity_points", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PointsDTO> getGoldImmunity() {
		PointsDTO points = new PointsDTO(goldImmunity.getAcquirePoints());
		return new ResponseEntity<>(points, HttpStatus.OK);
	}
	
	@GetMapping(value = "/bronze_title/acquire_points", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PointsDTO> getBronzeTitleAcquire() {
		PointsDTO points = new PointsDTO(bronzeTitle.getAcquirePoints());
		return new ResponseEntity<>(points, HttpStatus.OK);
	}
	
	@GetMapping(value = "/bronze_title/save_points", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PointsDTO> getBronzeTitleSave() {
		PointsDTO points = new PointsDTO(bronzeTitle.getSavePoints());
		return new ResponseEntity<>(points, HttpStatus.OK);
	}
	
	@GetMapping(value = "/bronze_title/reward_points", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PointsDTO> getBronzeTitleReward() {
		PointsDTO points = new PointsDTO(bronzeTitle.getRewardPoints());
		return new ResponseEntity<>(points, HttpStatus.OK);
	}
	
	@GetMapping(value = "/silver_title/acquire_points", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PointsDTO> getSilverTitleAcquire() {
		PointsDTO points = new PointsDTO(silverTitle.getAcquirePoints());
		return new ResponseEntity<>(points, HttpStatus.OK);
	}
	
	@GetMapping(value = "/silver_title/save_points", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PointsDTO> getSilverTitleSave() {
		PointsDTO points = new PointsDTO(silverTitle.getSavePoints());
		return new ResponseEntity<>(points, HttpStatus.OK);
	}
	
	@GetMapping(value = "/silver_title/reward_points", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PointsDTO> getSilverTitleReward() {
		PointsDTO points = new PointsDTO(silverTitle.getRewardPoints());
		return new ResponseEntity<>(points, HttpStatus.OK);
	}
	
	@GetMapping(value = "/gold_title/acquire_points", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PointsDTO> getGoldTitleAcquire() {
		PointsDTO points = new PointsDTO(goldTitle.getAcquirePoints());
		return new ResponseEntity<>(points, HttpStatus.OK);
	}
	
	@GetMapping(value = "/gold_title/save_points", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PointsDTO> getGoldTitleSave() {
		PointsDTO points = new PointsDTO(goldTitle.getSavePoints());
		return new ResponseEntity<>(points, HttpStatus.OK);
	}
	
	@GetMapping(value = "/gold_title/reward_points", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PointsDTO> getGoldTitleReward() {
		PointsDTO points = new PointsDTO(goldTitle.getRewardPoints());
		return new ResponseEntity<>(points, HttpStatus.OK);
	}
	
}