package com.videoClub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videoClub.repository.OfferRepository;
import com.videoClub.service.OfferService;

@Service
public class OfferServiceImpl implements OfferService{

	@Autowired
	private OfferRepository offerRepository;
}
