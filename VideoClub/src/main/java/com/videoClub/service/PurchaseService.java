package com.videoClub.service;

import com.videoClub.model.Purchase;
import com.videoClub.model.RegisteredUser;

public interface PurchaseService {

	public Purchase save(RegisteredUser user, long offerId);
	public Purchase getOne(Long id);
	public double getLastMonthPayment(RegisteredUser user);
}
