package com.videoClub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.videoClub.model.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long>{

}
