package com.videoClub.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videoClub.dto.ActionDTO;
import com.videoClub.exception.EmptyGenreList;
import com.videoClub.exception.EmptyOfferList;
import com.videoClub.exception.EntityNotDeletable;
import com.videoClub.exception.EntityNotFound;
import com.videoClub.model.Action;
import com.videoClub.model.ActionEvent;
import com.videoClub.model.Discount;
import com.videoClub.model.FreeContent;
import com.videoClub.model.FreeMinutes;
import com.videoClub.model.enumeration.ActionType;
import com.videoClub.model.enumeration.Genre;
import com.videoClub.model.enumeration.Rank;
import com.videoClub.repository.ActionRepository;
import com.videoClub.service.ActionEventService;
import com.videoClub.service.ActionService;
import com.videoClub.service.OfferService;

@Service
public class ActionServiceImpl implements ActionService{
	
	@Autowired
	private ActionRepository actionRepository;
	
	@Autowired
	private ActionEventService actionEventService;
	
	@Autowired
	private OfferService offerService;

	@Override
	public Action getOne(Long id) {
		Optional<Action> action = actionRepository.findById(id);
		if(action.isPresent()){
			return action.get();
		}
		else{
			throw new EntityNotFound(id);
		}
	}

	@Override
	public Action update(ActionDTO actionDTO) {
		Action action = getOne(actionDTO.getId());
		ActionType type = ActionType.valueOf(actionDTO.getActionType());
		boolean delete = type.equals(action.getActionType());
		if(type.equals(ActionType.DISCOUNT)){
			if(actionDTO.getDiscountOffersIds().isEmpty()){
				throw new EmptyOfferList();
			}
			Discount discount = new Discount();
			discount.setActionType(ActionType.DISCOUNT);
			discount.setAmount(actionDTO.getAmount());
			discount.setDescription(actionDTO.getDescription());
			discount.setTitleRank(Rank.valueOf(actionDTO.getRank()));
			for(Long id : actionDTO.getDiscountOffersIds()){
				discount.getDiscountOffers().add(offerService.getOne(id));
			}
			discount.setActionEvent(action.getActionEvent());
			discount.setId(action.getId());
			action = discount;
		}
		else if(type.equals(ActionType.FREE_CONTENT)){
			if(actionDTO.getGenres().isEmpty()){
				throw new EmptyGenreList();
			}
			FreeContent freeContent = new FreeContent();
			freeContent.setActionType(ActionType.FREE_CONTENT);
			freeContent.setDescription(actionDTO.getDescription());
			freeContent.setTitleRank(Rank.valueOf(actionDTO.getRank()));
			for(String genre : actionDTO.getGenres()){
				freeContent.getFreeGenres().add(Genre.valueOf(genre));
			}
			freeContent.setActionEvent(action.getActionEvent());
			freeContent.setId(action.getId());
			action = freeContent;
		}
		else if(type.equals(ActionType.FREE_MINUTES)){
			FreeMinutes freeMinutes = new FreeMinutes();
			freeMinutes.setActionType(ActionType.FREE_MINUTES);
			freeMinutes.setDescription(actionDTO.getDescription());
			freeMinutes.setTitleRank(Rank.valueOf(actionDTO.getRank()));
			freeMinutes.setAmount(actionDTO.getAmount());
			freeMinutes.setActionEvent(action.getActionEvent());
			freeMinutes.setId(action.getId());
			action = freeMinutes;
		}
		if(delete){
			delete(action.getId());
		}
		Action newAction = actionRepository.save(action);
		if(delete){
			delete(actionDTO.getId());
		}
		return newAction;
	}

	@Override
	public List<Action> getAll() {
		return actionRepository.findAll();
	}

	@Override
	public List<Action> getByActionEventId(Long id) {
		ActionEvent actionEvent = actionEventService.getOne(id);
		return actionEvent.getActions();
	}

	@Override
	public void delete(Long id) {
		Action action = getOne(id);
		if(action.getActionEvent().getActions().size() == 1){
			throw new EntityNotDeletable();
		}
		action.setActionEvent(null);
		actionRepository.deleteById(id);
	}
}
