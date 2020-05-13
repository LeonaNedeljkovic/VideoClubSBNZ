package com.videoClub.service;

import java.util.List;

import com.videoClub.dto.ActionDTO;
import com.videoClub.model.Action;

public interface ActionService {

	public Action update(ActionDTO actionDTO);
	public Action getOne(Long id);
	public List<Action> getAll();
	public List<Action> getByActionEventId(Long id);
	public void delete(Long id);
}
