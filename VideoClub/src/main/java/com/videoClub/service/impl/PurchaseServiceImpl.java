package com.videoClub.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videoClub.exception.ArtistNotFound;
import com.videoClub.model.Offer;
import com.videoClub.model.Purchase;
import com.videoClub.model.RegisteredUser;
import com.videoClub.repository.PurchaseRepository;
import com.videoClub.service.OfferService;
import com.videoClub.service.PurchaseService;
import com.videoClub.service.UserService;

@Service
public class PurchaseServiceImpl implements PurchaseService{
	
	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Autowired
	private OfferService offerService;
	
	@Autowired
	private UserService userService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Override
	public Purchase save(RegisteredUser user, long offerId) {
		Purchase purchase = new Purchase();
		RegisteredUser userUpdate = (RegisteredUser) userService.findById(user.getId());
		purchase.setDate(LocalDate.parse(sdf.format(new Date()),df));
		purchase.setUser(userUpdate);
		Offer offer = offerService.getOne(offerId);
		purchase.setOffer(offer);
		purchase.setPrice(offer.getPrice());
		purchase.setPurchasedMinutes(offer.getMinutes());
		purchase.setDiscount(0);
		userUpdate.setImmunityPoints((int)(userUpdate.getImmunityPoints() + offer.getPrice()));
		userUpdate.setAvailableMinutes(offer.getMinutes());
		userService.save(userUpdate);
		return purchaseRepository.save(purchase);
	}

	@Override
	public Purchase getOne(Long id) {
		Optional<Purchase> purchase = purchaseRepository.findById(id);
		if(purchase.isPresent()){
			return purchase.get();
		}
		else{
			throw new ArtistNotFound(id);
		}
	}

	@Override
	public double getLastMonthPayment(RegisteredUser user) {
		// TODO Auto-generated method stub
		return 0;
	}
}
