package com.videoClub.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videoClub.dto.ActionDTO;
import com.videoClub.dto.ActionEventDTO;
import com.videoClub.exception.EmptyGenreList;
import com.videoClub.exception.EmptyOfferList;
import com.videoClub.exception.EntityNotFound;
import com.videoClub.exception.InvalidDate;
import com.videoClub.model.Action;
import com.videoClub.model.ActionEvent;
import com.videoClub.model.Discount;
import com.videoClub.model.FreeContent;
import com.videoClub.model.RegisteredUser;
import com.videoClub.model.User;
import com.videoClub.model.enumeration.ActionType;
import com.videoClub.model.enumeration.Genre;
import com.videoClub.model.enumeration.Rank;
import com.videoClub.repository.ActionEventRepository;
import com.videoClub.service.ActionEventService;
import com.videoClub.service.ActionService;
import com.videoClub.service.OfferService;
import com.videoClub.service.UserService;

@Service
public class ActionEventServiceImpl implements ActionEventService{

	@Autowired
	private ActionEventRepository actionEventRepository;
	
	@Autowired
	private ActionService actionService;
	
	@Autowired
	private OfferService offerService;
	
	@Autowired
	private UserService userService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	@Autowired
	private KieContainer kieContainer;

	@Override
	public ActionEvent save(ActionEventDTO actionEventDTO) {
		KieSession kieSession = kieContainer.newKieSession("actionRulesSession");
		checkDates(actionEventDTO.getStartDate(), actionEventDTO.getEndDate());
		ActionEvent actionEvent = new ActionEvent();
		actionEvent.setStartDate(LocalDate.parse(sdf.format(actionEventDTO.getStartDate()),df));
		actionEvent.setEndDate(LocalDate.parse(sdf.format(actionEventDTO.getEndDate()),df));
		actionEvent.setName(actionEventDTO.getName());
		
		for(ActionDTO action : actionEventDTO.getActions()){
			ActionType type = ActionType.valueOf(action.getActionType());
			if(type.equals(ActionType.DISCOUNT)){
				if(action.getDiscountOffersIds().isEmpty()){
					throw new EmptyOfferList();
				}
				Discount discount = new Discount();
				discount.setAmount(action.getAmount());
				discount.setDescription(action.getDescription());
				discount.setTitleRank(Rank.valueOf(action.getRank()));
				discount.setActionEvent(actionEvent);
				for(Long id : action.getDiscountOffersIds()){
					discount.getDiscountOffers().add(offerService.getOne(id));
				}
				actionEvent.getActions().add(discount);
				kieSession.insert(discount);
			}
			else if(type.equals(ActionType.FREE_CONTENT)){
				if(action.getGenres().isEmpty()){
					throw new EmptyGenreList();
				}
				FreeContent freeContent = new FreeContent();
				freeContent.setDescription(action.getDescription());
				freeContent.setTitleRank(Rank.valueOf(action.getRank()));
				freeContent.setActionEvent(actionEvent);
				for(String genre : action.getGenres()){
					freeContent.getFreeGenres().add(Genre.valueOf(genre));
				}
				actionEvent.getActions().add(freeContent);
				kieSession.insert(freeContent);
			}
		}
		for(User user : userService.findAll()){
			if(user instanceof RegisteredUser){
				kieSession.insert(user);
			}
		}
		kieSession.fireAllRules();
		kieSession.dispose();
		return actionEventRepository.save(actionEvent);
	}
	
	@Override
	public ActionEvent update(ActionEventDTO actionEventDTO) {
		ActionEvent actionEvent = getOne(actionEventDTO.getId());
		checkDates(actionEventDTO.getStartDate(), actionEventDTO.getEndDate());
		actionEvent.setStartDate(LocalDate.parse(sdf.format(actionEventDTO.getStartDate()),df));
		actionEvent.setEndDate(LocalDate.parse(sdf.format(actionEventDTO.getEndDate()),df));
		actionEvent.setName(actionEventDTO.getName());
		return actionEventRepository.save(actionEvent);
	}

	@Override
	public ActionEvent getOne(Long id) {
		Optional<ActionEvent> actionEvent = actionEventRepository.findById(id);
		if(actionEvent.isPresent()){
			return actionEvent.get();
		}
		else{
			throw new EntityNotFound(id);
		}
	}

	@Override
	public List<ActionEvent> getAll() {
		return actionEventRepository.findAll();
	}

	@Override
	public ActionEvent getByActionId(Long actionId) {
		Action action = actionService.getOne(actionId);
		return action.getActionEvent();
	}

	@Override
	public void delete(Long id) {
		ActionEvent actionEvent = getOne(id);
		actionEventRepository.deleteById(actionEvent.getId());
	}

	@Override
	public void checkDates(Date startDate, Date endDate) {
		if(startDate.after(endDate)){
			throw new InvalidDate();
		}
		
		Date now = new Date();
		if(startDate.before(now) || endDate.before(now)){
			throw new InvalidDate();
		}
	}
}
