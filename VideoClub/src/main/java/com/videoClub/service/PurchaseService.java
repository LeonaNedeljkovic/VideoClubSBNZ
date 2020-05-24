package com.videoClub.service;

import java.time.LocalDateTime;
import java.util.List;

import com.videoClub.model.Purchase;
import com.videoClub.model.RegisteredUser;

public interface PurchaseService {

	public Purchase save(RegisteredUser user, long offerId);
	public Purchase getOne(Long id);
	public List<Purchase> getLastMonthPurchases(Long userId, LocalDateTime now, LocalDateTime monthAgo);
}
