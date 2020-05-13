package com.videoClub.service;

import java.util.List;

import com.videoClub.model.Offer;

public interface OfferService {

	public Offer save(Offer offer);
	public Offer getOne(Long id);
	public List<Offer> getAll();
	public void delete(Long id);
}
