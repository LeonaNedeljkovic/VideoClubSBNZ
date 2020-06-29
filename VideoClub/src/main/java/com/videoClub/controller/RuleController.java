package com.videoClub.controller;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.drools.template.ObjectDataCompiler;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.videoClub.model.drl.Immunity;
import com.videoClub.model.drl.Title;
import com.videoClub.dto.PointsDTO;
import com.videoClub.dto.ReportDTO;
import com.videoClub.factory.ImmunityFactory;
import com.videoClub.factory.TitleFactory;
import com.videoClub.model.AgeClassifier;
import com.videoClub.model.Film;
import com.videoClub.model.Purchase;
import com.videoClub.model.RegisteredUser;
import com.videoClub.model.User;
import com.videoClub.model.enumeration.Rank;
import com.videoClub.service.AgeClassifierService;
import com.videoClub.service.FilmService;
import com.videoClub.service.PurchaseService;
import com.videoClub.service.RuleService;
import com.videoClub.service.UserService;
import com.videoClub.template.FilmAgeRestrictionTemplate;
import com.videoClub.template.FreeMinutes;
import com.videoClub.template.GenreAgeRestrictionTemplate;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class RuleController {
	
	@Autowired
	private RuleService ruleService;
	
	@Autowired
	private FilmService filmService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PurchaseService purchaseService;
	
	@Autowired
	private AgeClassifierService ageClassifierService;
	
	@Autowired
	private KieContainer kieContainer;
	
	@Autowired
	@Qualifier(value = "cepReportSession")
	private KieSession cepReportSession;
	
	@Autowired
	@Qualifier(value = "bronzeImmunity")
	private Immunity bronzeImmunity;
	
	@Autowired
	@Qualifier(value = "silverImmunity")
	private Immunity silverImmunity;
	
	@Autowired
	@Qualifier(value = "goldImmunity")
	private Immunity goldImmunity;
	
	@Autowired
	@Qualifier(value = "bronzeTitle")
	private Title bronzeTitle;
	
	@Autowired
	@Qualifier(value = "silverTitle")
	private Title silverTitle;
	
	@Autowired
	@Qualifier(value = "goldTitle")
	private Title goldTitle;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	@PutMapping(value = "/classify_user/age", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<User>> classifyUserByAge(@RequestBody List<AgeClassifier> ageClassifiers) {
		InputStream template = RuleController.class.getResourceAsStream("/templates/classifyUserByAge.drt");
		ObjectDataCompiler converter = new ObjectDataCompiler();
	    String drl = converter.compile(ageClassifiers, template);
	    KieSession ksession = initializeKieSessionFromDRL(drl);
	    List<User> users = userService.findAll();
	    for(User user : users){
			if(user instanceof RegisteredUser){
				ksession.insert(user);
			}		
		}
	    ksession.fireAllRules();
        ksession.dispose();
        ageClassifierService.saveAll(ageClassifiers);
        return new ResponseEntity<>(userService.save(users), HttpStatus.OK);
	}
	
	@PutMapping(value = "/restrict_genre/age", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Film>> restrictGenreByAge(@RequestBody List<GenreAgeRestrictionTemplate> freeMinutes) {
		InputStream template = RuleController.class.getResourceAsStream("/templates/genreRestrictionByAge.drt");
		ObjectDataCompiler converter = new ObjectDataCompiler();
	    String drl = converter.compile(freeMinutes, template);
	    KieSession ksession = initializeKieSessionFromDRL(drl);
	    List<Film> films = filmService.getAll();
	    for(Film film : films){
			ksession.insert(film);		
		}
	    ksession.fireAllRules();
        ksession.dispose();
        return new ResponseEntity<>(filmService.save(films), HttpStatus.OK);
	}
	
	@PutMapping(value = "/restrict_film/age", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Film>> restrictFilmByAge(@RequestBody List<FilmAgeRestrictionTemplate> restricted) {
		InputStream template = RuleController.class.getResourceAsStream("/templates/filmRestrictionByAge.drt");
		ObjectDataCompiler converter = new ObjectDataCompiler();
	    String drl = converter.compile(restricted, template);
	    KieSession ksession = initializeKieSessionFromDRL(drl);
	    List<Film> films = filmService.getAll();
	    for(Film film : films){
			ksession.insert(film);		
		}
	    ksession.fireAllRules();
        ksession.dispose();
        return new ResponseEntity<>(filmService.save(films), HttpStatus.OK);
	}
	
	@PutMapping(value = "/free_minutes/age", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<User>> freeMinutesByAge(@RequestBody List<FreeMinutes> freeMinutes) {
		InputStream template = RuleController.class.getResourceAsStream("/templates/freeMinutesByAge.drt");
		ObjectDataCompiler converter = new ObjectDataCompiler();
	    String drl = converter.compile(freeMinutes, template);
	    KieSession ksession = initializeKieSessionFromDRL(drl);
	    List<User> users = userService.findAll();
	    for(User user : users){
			if(user instanceof RegisteredUser){
				ksession.insert(user);
			}		
		}
	    ksession.fireAllRules();
        ksession.dispose();
        return new ResponseEntity<>(userService.save(users), HttpStatus.OK);
	}
	
	@PutMapping(value = "/free_minutes/title", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<User>> freeMinutesByTitle(@RequestBody List<FreeMinutes> freeMinutes) {
		InputStream template = RuleController.class.getResourceAsStream("/templates/freeMinutesByTitle.drt");
		ObjectDataCompiler converter = new ObjectDataCompiler();
	    String drl = converter.compile(freeMinutes, template);
	    KieSession ksession = initializeKieSessionFromDRL(drl);
	    List<User> users = userService.findAll();
	    for(User user : users){
			if(user instanceof RegisteredUser){
				ksession.insert(user);
			}		
		}
	    ksession.fireAllRules();
        ksession.dispose();
        return new ResponseEntity<>(userService.save(users), HttpStatus.OK);
	}
	
	@PutMapping(value = "/free_minutes/age/title", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<User>> freeMinutesByAgeAndTitle(@RequestBody List<FreeMinutes> freeMinutes) {
		InputStream template = RuleController.class.getResourceAsStream("/templates/freeMinutesByAgeAndTitle.drt");
		ObjectDataCompiler converter = new ObjectDataCompiler();
	    String drl = converter.compile(freeMinutes, template);
	    KieSession ksession = initializeKieSessionFromDRL(drl);
	    List<User> users = userService.findAll();
	    for(User user : users){
			if(user instanceof RegisteredUser){
				ksession.insert(user);
			}		
		}
	    ksession.fireAllRules();
        ksession.dispose();
        return new ResponseEntity<>(userService.save(users), HttpStatus.OK);
	}
	
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
		KieSession kieSession = initializeKieSession("entityDefinerRulesSession");
		Immunity newImmunity = new Immunity(points.getValue(), Rank.BRONZE);
		kieSession.insert(newImmunity);
		kieSession.fireAllRules();
		kieSession.dispose();
		bronzeImmunity.setAcquirePoints(points.getValue());
		ruleService.setImmunityPoints(Rank.BRONZE, points.getValue());
		return new ResponseEntity<>(new PointsDTO(bronzeImmunity.getAcquirePoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/silver_immunity_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setSilverImmunity(@RequestBody PointsDTO points) {
		KieSession kieSession = initializeKieSession("entityDefinerRulesSession");
		Immunity newImmunity = new Immunity(points.getValue(), Rank.SILVER);
		kieSession.insert(newImmunity);
		kieSession.fireAllRules();
		kieSession.dispose();
		silverImmunity.setAcquirePoints(points.getValue());
		ruleService.setImmunityPoints(Rank.SILVER, points.getValue());
		return new ResponseEntity<>(new PointsDTO(silverImmunity.getAcquirePoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/gold_immunity_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setGoldImmunity(@RequestBody PointsDTO points) {
		KieSession kieSession = initializeKieSession("entityDefinerRulesSession");
		Immunity newImmunity = new Immunity(points.getValue(), Rank.GOLD);
		kieSession.insert(newImmunity);
		kieSession.fireAllRules();
		kieSession.dispose();
		goldImmunity.setAcquirePoints(points.getValue());
		ruleService.setImmunityPoints(Rank.GOLD, points.getValue());
		return new ResponseEntity<>(new PointsDTO(goldImmunity.getAcquirePoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/bronze_title/acquire_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setBronzeTitleAcquire(@RequestBody PointsDTO points) {
		KieSession kieSession = initializeKieSession("entityDefinerRulesSession");
		Title newTitle = new Title(points.getValue(), bronzeTitle.getSavePoints(), bronzeTitle.getRewardPoints(), Rank.BRONZE);
		kieSession.insert(newTitle);
		kieSession.fireAllRules();
		kieSession.dispose();
		bronzeTitle.setAcquirePoints(points.getValue());
		ruleService.setTitleAcquirePoints(Rank.BRONZE, points.getValue());
		return new ResponseEntity<>(new PointsDTO(bronzeTitle.getAcquirePoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/bronze_title/save_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setBronzeTitleSave(@RequestBody PointsDTO points) {
		KieSession kieSession = initializeKieSession("entityDefinerRulesSession");
		Title newTitle = new Title(bronzeTitle.getAcquirePoints(), points.getValue(), bronzeTitle.getRewardPoints(), Rank.BRONZE);
		kieSession.insert(newTitle);
		kieSession.fireAllRules();
		kieSession.dispose();
		bronzeTitle.setSavePoints(points.getValue());
		ruleService.setTitleSavePoints(Rank.BRONZE, points.getValue());
		return new ResponseEntity<>(new PointsDTO(bronzeTitle.getSavePoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/bronze_title/reward_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setBronzeTitleReward(@RequestBody PointsDTO points) {
		KieSession kieSession = initializeKieSession("entityDefinerRulesSession");
		Title newTitle = new Title(bronzeTitle.getAcquirePoints(), bronzeTitle.getSavePoints(), points.getValue(), Rank.BRONZE);
		kieSession.insert(newTitle);
		kieSession.fireAllRules();
		kieSession.dispose();
		bronzeTitle.setRewardPoints(points.getValue());
		ruleService.setTitleRewardPoints(Rank.BRONZE, points.getValue());
		return new ResponseEntity<>(new PointsDTO(bronzeTitle.getRewardPoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/silver_title/acquire_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setSilverTitleAcquire(@RequestBody PointsDTO points) {
		KieSession kieSession = initializeKieSession("entityDefinerRulesSession");
		Title newTitle = new Title(points.getValue(), silverTitle.getSavePoints(), silverTitle.getRewardPoints(), Rank.SILVER);
		kieSession.insert(newTitle);
		kieSession.fireAllRules();
		kieSession.dispose();
		silverTitle.setAcquirePoints(points.getValue());
		ruleService.setTitleAcquirePoints(Rank.SILVER, points.getValue());
		return new ResponseEntity<>(new PointsDTO(silverTitle.getAcquirePoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/silver_title/save_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setSilverTitleSave(@RequestBody PointsDTO points) {
		KieSession kieSession = initializeKieSession("entityDefinerRulesSession");
		Title newTitle = new Title(silverTitle.getAcquirePoints(), points.getValue(), silverTitle.getRewardPoints(), Rank.SILVER);
		kieSession.insert(newTitle);
		kieSession.fireAllRules();
		kieSession.dispose();
		silverTitle.setSavePoints(points.getValue());
		ruleService.setTitleSavePoints(Rank.SILVER, points.getValue());
		return new ResponseEntity<>(new PointsDTO(silverTitle.getSavePoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/silver_title/reward_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setSilverTitleReward(@RequestBody PointsDTO points) {
		KieSession kieSession = initializeKieSession("entityDefinerRulesSession");
		Title newTitle = new Title(silverTitle.getAcquirePoints(), silverTitle.getSavePoints(), points.getValue(), Rank.SILVER);
		kieSession.insert(newTitle);
		kieSession.fireAllRules();
		kieSession.dispose();
		silverTitle.setRewardPoints(points.getValue());
		ruleService.setTitleRewardPoints(Rank.SILVER, points.getValue());
		kieSession.setGlobal("silverTitle", silverTitle);
		return new ResponseEntity<>(new PointsDTO(silverTitle.getRewardPoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/gold_title/acquire_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setGoldTitleAcquire(@RequestBody PointsDTO points) {
		KieSession kieSession = initializeKieSession("entityDefinerRulesSession");
		Title newTitle = new Title(points.getValue(), goldTitle.getSavePoints(), goldTitle.getRewardPoints(), Rank.GOLD);
		kieSession.insert(newTitle);
		kieSession.fireAllRules();
		kieSession.dispose();
		goldTitle.setAcquirePoints(points.getValue());
		ruleService.setTitleAcquirePoints(Rank.GOLD, points.getValue());
		return new ResponseEntity<>(new PointsDTO(goldTitle.getAcquirePoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/gold_title/save_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setGoldTitleSave(@RequestBody PointsDTO points) {
		KieSession kieSession = initializeKieSession("entityDefinerRulesSession");
		Title newTitle = new Title(goldTitle.getAcquirePoints(), points.getValue(), goldTitle.getRewardPoints(), Rank.GOLD);
		kieSession.insert(newTitle);
		kieSession.fireAllRules();
		kieSession.dispose();
		goldTitle.setSavePoints(points.getValue());
		ruleService.setTitleSavePoints(Rank.GOLD, points.getValue());
		return new ResponseEntity<>(new PointsDTO(goldTitle.getSavePoints()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/gold_title/reward_points", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PointsDTO> setGoldTitleReward(@RequestBody PointsDTO points) {
		KieSession kieSession = initializeKieSession("entityDefinerRulesSession");
		Title newTitle = new Title(goldTitle.getAcquirePoints(), goldTitle.getSavePoints(), points.getValue(), Rank.GOLD);
		kieSession.insert(newTitle);
		kieSession.fireAllRules();
		kieSession.dispose();
		goldTitle.setRewardPoints(points.getValue());
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
	
	@Scheduled(cron="0 0 * * * *")
	public void fireReportRules() {
		ReportDTO reportDTO = new ReportDTO(0.0,LocalDate.now(),null,0L);
		FactHandle factHandle = cepReportSession.insert(reportDTO);
		cepReportSession.fireAllRules();
		System.out.println("Earned in last 24h "+reportDTO.getEarned());
		if(reportDTO.getFilm() != null) {
			System.out.println("Most watched film is "+ reportDTO.getFilm().getName());
		}
		System.out.println("Number of views in the last 24h "+reportDTO.getNumberOfViews());
		cepReportSession.delete(factHandle);
	}
	
	private KieSession initializeKieSession(String sessionName){
		KieSession kieSession = kieContainer.newKieSession(sessionName);
		TitleFactory titleFactory = new TitleFactory(
				goldTitle,
				silverTitle,
				bronzeTitle);
		ImmunityFactory immunityFactory = new ImmunityFactory(
				bronzeImmunity,
				silverImmunity,
				goldImmunity);
		kieSession.setGlobal("titleFactory", titleFactory);
		kieSession.setGlobal("immunityFactory", immunityFactory);
		return kieSession;
	}
	
	private KieSession initializeKieSessionFromDRL(String drl){
        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(drl, ResourceType.DRL);
        
        Results results = kieHelper.verify();
        
        if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)){
            List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
            for (Message message : messages) {
                System.out.println("Error: "+message.getText());
            }
            
            throw new IllegalStateException("Compilation errors were found. Check the logs.");
        }
        
        return kieHelper.build().newKieSession();
    }
}