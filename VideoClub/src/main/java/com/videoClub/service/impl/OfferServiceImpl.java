package com.videoClub.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videoClub.exception.EntityNotDeletable;
import com.videoClub.exception.EntityNotFound;
import com.videoClub.model.Offer;
import com.videoClub.repository.OfferRepository;
import com.videoClub.service.OfferService;

@Service
public class OfferServiceImpl implements OfferService{

	@Autowired
	private OfferRepository offerRepository;

	@Override
	public Offer save(Offer offer) {
		return offerRepository.save(offer);
	}

	@Override
	public Offer getOne(Long id) {
		Optional<Offer> offer = offerRepository.findById(id);
		if(offer.isPresent()){
			return offer.get();
		}
		else{
			throw new EntityNotFound(id);
		}
	}

	@Override
	public List<Offer> getAll() {
		return offerRepository.findAll();
	}

	@Override
	public void delete(Long id) {
		Offer offer = getOne(id);
		if(getAll().isEmpty() || !(offer.getDiscounts().isEmpty())){
			throw new EntityNotDeletable();
		}
		offerRepository.deleteById(id);
	}
}
