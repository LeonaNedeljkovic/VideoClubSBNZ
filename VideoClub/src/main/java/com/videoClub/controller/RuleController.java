package com.videoClub.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.videoClub.model.Purchase;
import com.videoClub.model.RegisteredUser;
import com.videoClub.model.User;
import com.videoClub.model.enumeration.Rank;
import com.videoClub.service.PurchaseService;
import com.videoClub.service.RuleService;
import com.videoClub.service.UserService;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
//@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
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
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PurchaseService purchaseService;
	
	@Autowired
	private KieContainer kieContainer;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
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
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setBronzeImmunity(@RequestBody PointsDTO points) {
		KieSession kieSession = initializeKieSession("entityDefinerRulesSession");
		bronzeImmunity.setAcquirePoints(points.getValue());
		kieSession.insert(bronzeImmunity);
		kieSession.fireAllRules();
		kieSession.dispose();
		ruleService.setImmunityPoints(Rank.BRONZE, points.getValue());
		return new ResponseEntity<>(new PointsDTO(bronzeImmunity.getAcquirePoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/silver_immunity_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setSilverImmunity(@RequestBody PointsDTO points) {
		KieSession kieSession = initializeKieSession("entityDefinerRulesSession");
		silverImmunity.setAcquirePoints(points.getValue());
		kieSession.insert(silverImmunity);
		kieSession.fireAllRules();
		kieSession.dispose();
		ruleService.setImmunityPoints(Rank.SILVER, points.getValue());
		return new ResponseEntity<>(new PointsDTO(silverImmunity.getAcquirePoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/gold_immunity_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setGoldImmunity(@RequestBody PointsDTO points) {
		KieSession kieSession = initializeKieSession("entityDefinerRulesSession");
		goldImmunity.setAcquirePoints(points.getValue());
		kieSession.insert(goldImmunity);
		kieSession.fireAllRules();
		kieSession.dispose();
		ruleService.setImmunityPoints(Rank.GOLD, points.getValue());
		return new ResponseEntity<>(new PointsDTO(goldImmunity.getAcquirePoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/bronze_title/acquire_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setBronzeTitleAcquire(@RequestBody PointsDTO points) {
		KieSession kieSession = initializeKieSession("entityDefinerRulesSession");
		bronzeTitle.setAcquirePoints(points.getValue());
		kieSession.insert(bronzeTitle);
		kieSession.fireAllRules();
		kieSession.dispose();
		ruleService.setTitleAcquirePoints(Rank.BRONZE, points.getValue());
		return new ResponseEntity<>(new PointsDTO(bronzeTitle.getAcquirePoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/bronze_title/save_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setBronzeTitleSave(@RequestBody PointsDTO points) {
		KieSession kieSession = initializeKieSession("entityDefinerRulesSession");
		bronzeTitle.setSavePoints(points.getValue());
		kieSession.insert(bronzeTitle);
		kieSession.fireAllRules();
		kieSession.dispose();
		ruleService.setTitleSavePoints(Rank.BRONZE, points.getValue());
		return new ResponseEntity<>(new PointsDTO(bronzeTitle.getSavePoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/bronze_title/reward_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setBronzeTitleReward(@RequestBody PointsDTO points) {
		KieSession kieSession = initializeKieSession("entityDefinerRulesSession");
		bronzeTitle.setRewardPoints(points.getValue());
		kieSession.insert(bronzeTitle);
		kieSession.fireAllRules();
		kieSession.dispose();
		ruleService.setTitleRewardPoints(Rank.BRONZE, points.getValue());
		return new ResponseEntity<>(new PointsDTO(bronzeTitle.getRewardPoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/silver_title/acquire_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setSilverTitleAcquire(@RequestBody PointsDTO points) {
		KieSession kieSession = initializeKieSession("entityDefinerRulesSession");
		silverTitle.setAcquirePoints(points.getValue());
		kieSession.insert(silverTitle);
		kieSession.fireAllRules();
		kieSession.dispose();
		ruleService.setTitleAcquirePoints(Rank.SILVER, points.getValue());
		return new ResponseEntity<>(new PointsDTO(silverTitle.getAcquirePoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/silver_title/save_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setSilverTitleSave(@RequestBody PointsDTO points) {
		KieSession kieSession = initializeKieSession("entityDefinerRulesSession");
		silverTitle.setSavePoints(points.getValue());
		kieSession.insert(silverTitle);
		kieSession.fireAllRules();
		kieSession.dispose();
		ruleService.setTitleSavePoints(Rank.SILVER, points.getValue());
		return new ResponseEntity<>(new PointsDTO(silverTitle.getSavePoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/silver_title/reward_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setSilverTitleReward(@RequestBody PointsDTO points) {
		KieSession kieSession = initializeKieSession("entityDefinerRulesSession");
		silverTitle.setRewardPoints(points.getValue());
		kieSession.insert(silverTitle);
		kieSession.fireAllRules();
		kieSession.dispose();
		ruleService.setTitleRewardPoints(Rank.SILVER, points.getValue());
		kieSession.setGlobal("silverTitle", silverTitle);
		return new ResponseEntity<>(new PointsDTO(silverTitle.getRewardPoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/gold_title/acquire_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setGoldTitleAcquire(@RequestBody PointsDTO points) {
		KieSession kieSession = initializeKieSession("entityDefinerRulesSession");
		goldTitle.setAcquirePoints(points.getValue());
		kieSession.insert(goldTitle);
		kieSession.fireAllRules();
		kieSession.dispose();
		ruleService.setTitleAcquirePoints(Rank.GOLD, points.getValue());
		return new ResponseEntity<>(new PointsDTO(goldTitle.getAcquirePoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/gold_title/save_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setGoldTitleSave(@RequestBody PointsDTO points) {
		KieSession kieSession = initializeKieSession("entityDefinerRulesSession");
		goldTitle.setSavePoints(points.getValue());
		kieSession.insert(goldTitle);
		kieSession.fireAllRules();
		kieSession.dispose();
		ruleService.setTitleSavePoints(Rank.GOLD, points.getValue());
		return new ResponseEntity<>(new PointsDTO(goldTitle.getSavePoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/gold_title/reward_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setGoldTitleReward(@RequestBody PointsDTO points) {
		KieSession kieSession = initializeKieSession("entityDefinerRulesSession");
		goldTitle.setRewardPoints(points.getValue());
		kieSession.insert(goldTitle);
		kieSession.fireAllRules();
		kieSession.dispose();
		ruleService.setTitleRewardPoints(Rank.GOLD, points.getValue());
		return new ResponseEntity<>(new PointsDTO(goldTitle.getRewardPoints()), HttpStatus.OK);
	}
	
	@Scheduled(cron="0 0 0 1 1/1 *")
	public void fireTitleRules() {
		List<User> users = userService.findAll();
		KieSession kieSession = initializeKieSession("titleRulesSession");
		Calendar lastMonth = Calendar.getInstance();
		lastMonth.setTime(new Date());
		lastMonth.add(Calendar.MONTH, -1);
		LocalDateTime date1 = LocalDateTime.parse(sdf.format(new Date()), df);
		LocalDateTime date2 = LocalDateTime.parse(sdf.format(lastMonth.getTime()), df);
		for(User user : users){
			if(user instanceof RegisteredUser){
				((RegisteredUser) user).setImmunity(Rank.NONE);
				for(Purchase p : purchaseService.getLastMonthPurchases(user.getId(),date1, date2)){
					kieSession.insert(p);
				}
				kieSession.insert(user);
			}		
		}
		kieSession.fireAllRules();
		kieSession.dispose();
		userService.save(users);
	}
	
	private KieSession initializeKieSession(String sessionName){
		KieSession kieSession = kieContainer.newKieSession(sessionName);
		kieSession.setGlobal("bronzeImmunity", bronzeImmunity);
		kieSession.setGlobal("silverImmunity", silverImmunity);
		kieSession.setGlobal("goldImmunity", goldImmunity);
		kieSession.setGlobal("bronzeTitle", bronzeTitle);
		kieSession.setGlobal("silverTitle", silverTitle);
		kieSession.setGlobal("goldTitle", goldTitle);
		return kieSession;
	}
	
}