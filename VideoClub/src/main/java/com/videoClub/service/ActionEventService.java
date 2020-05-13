package com.videoClub.service;

import java.util.Date;
import java.util.List;

import com.videoClub.dto.ActionEventDTO;
import com.videoClub.model.ActionEvent;

public interface ActionEventService {

	public ActionEvent save(ActionEventDTO actionEventDTO);
	public ActionEvent update(ActionEventDTO actionEventDTO);
	public ActionEvent getOne(Long id);
	public List<ActionEvent> getAll();
	public ActionEvent getByActionId(Long actionId);
	public void delete(Long id);
	public void checkDates(Date startDate, Date endDate);
}
