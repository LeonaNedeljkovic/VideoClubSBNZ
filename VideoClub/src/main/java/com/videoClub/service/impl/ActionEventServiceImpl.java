package com.videoClub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videoClub.repository.ActionEventRepository;
import com.videoClub.service.ActionEventService;

@Service
public class ActionEventServiceImpl implements ActionEventService{

	@Autowired
	private ActionEventRepository actionEventRepository;
}
