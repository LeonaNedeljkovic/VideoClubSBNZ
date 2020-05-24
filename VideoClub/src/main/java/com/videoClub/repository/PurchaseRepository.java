package com.videoClub.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.videoClub.model.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long>{

	@Query("SELECT p FROM Purchase p WHERE p.user.id = ?1 "+
			"AND p.date < ?2 AND p.date >= ?3")
	public List<Purchase> getLastMonthPurchases(Long userId, LocalDateTime now, LocalDateTime monthAgo);
}
