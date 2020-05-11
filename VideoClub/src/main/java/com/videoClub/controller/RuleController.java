package com.videoClub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.videoClub.bean.BronzeImmunity;
import com.videoClub.bean.BronzeTitle;
import com.videoClub.bean.GoldImmunity;
import com.videoClub.bean.GoldTitle;
import com.videoClub.bean.SilverImmunity;
import com.videoClub.bean.SilverTitle;
import com.videoClub.dto.PointsDTO;
import com.videoClub.model.enumeration.Rank;
import com.videoClub.service.RuleService;

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
	
	@Autowired
	private RuleService ruleService;
	
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
	
	@PutMapping(value = "/bronze_immunity_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setBronzeImmunity(@RequestBody PointsDTO points) {
		ruleService.setImmunityPoints(Rank.BRONZE, points.getValue());
		bronzeImmunity.setAcquirePoints(points.getValue());
		//kie session update...
		return new ResponseEntity<>(new PointsDTO(bronzeImmunity.getAcquirePoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/silver_immunity_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setSilverImmunity(@RequestBody PointsDTO points) {
		ruleService.setImmunityPoints(Rank.SILVER, points.getValue());
		silverImmunity.setAcquirePoints(points.getValue());
		//kie session update...
		return new ResponseEntity<>(new PointsDTO(silverImmunity.getAcquirePoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/gold_immunity_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setGoldImmunity(@RequestBody PointsDTO points) {
		ruleService.setImmunityPoints(Rank.GOLD, points.getValue());
		goldImmunity.setAcquirePoints(points.getValue());
		//kie session update...
		return new ResponseEntity<>(new PointsDTO(goldImmunity.getAcquirePoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/bronze_title/acquire_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setBronzeTitleAcquire(@RequestBody PointsDTO points) {
		ruleService.setTitleAcquirePoints(Rank.BRONZE, points.getValue());
		bronzeTitle.setAcquirePoints(points.getValue());
		//kie session update...
		return new ResponseEntity<>(new PointsDTO(bronzeTitle.getAcquirePoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/bronze_title/save_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setBronzeTitleSave(@RequestBody PointsDTO points) {
		ruleService.setTitleSavePoints(Rank.BRONZE, points.getValue());
		bronzeTitle.setSavePoints(points.getValue());
		//kie session update...
		return new ResponseEntity<>(new PointsDTO(bronzeTitle.getSavePoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/bronze_title/reward_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setBronzeTitleReward(@RequestBody PointsDTO points) {
		ruleService.setTitleRewardPoints(Rank.BRONZE, points.getValue());
		bronzeTitle.setRewardPoints(points.getValue());
		//kie session update...
		return new ResponseEntity<>(new PointsDTO(bronzeTitle.getRewardPoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/silver_title/acquire_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setSilverTitleAcquire(@RequestBody PointsDTO points) {
		ruleService.setTitleAcquirePoints(Rank.SILVER, points.getValue());
		silverTitle.setAcquirePoints(points.getValue());
		//kie session update...
		return new ResponseEntity<>(new PointsDTO(silverTitle.getAcquirePoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/silver_title/save_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setSilverTitleSave(@RequestBody PointsDTO points) {
		ruleService.setTitleSavePoints(Rank.SILVER, points.getValue());
		silverTitle.setSavePoints(points.getValue());
		//kie session update...
		return new ResponseEntity<>(new PointsDTO(silverTitle.getSavePoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/silver_title/reward_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setSilverTitleReward(@RequestBody PointsDTO points) {
		ruleService.setTitleRewardPoints(Rank.SILVER, points.getValue());
		silverTitle.setRewardPoints(points.getValue());
		//kie session update...
		return new ResponseEntity<>(new PointsDTO(silverTitle.getRewardPoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/gold_title/acquire_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setGoldTitleAcquire(@RequestBody PointsDTO points) {
		ruleService.setTitleAcquirePoints(Rank.GOLD, points.getValue());
		goldTitle.setAcquirePoints(points.getValue());
		//kie session update...
		return new ResponseEntity<>(new PointsDTO(goldTitle.getAcquirePoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/gold_title/save_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setGoldTitleSave(@RequestBody PointsDTO points) {
		ruleService.setTitleSavePoints(Rank.GOLD, points.getValue());
		goldTitle.setSavePoints(points.getValue());
		//kie session update...
		return new ResponseEntity<>(new PointsDTO(goldTitle.getSavePoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/gold_title/reward_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setGoldTitleReward(@RequestBody PointsDTO points) {
		ruleService.setTitleRewardPoints(Rank.GOLD, points.getValue());
		goldTitle.setRewardPoints(points.getValue());
		//kie session update...
		return new ResponseEntity<>(new PointsDTO(goldTitle.getRewardPoints()), HttpStatus.OK);
	}
	
}