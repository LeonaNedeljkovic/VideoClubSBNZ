package com.videoClub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videoClub.repository.ActionRepository;
import com.videoClub.service.ActionService;

@Service
public class ActionServiceImpl implements ActionService{
	
	@Autowired
	private ActionRepository actionRepository;
}
