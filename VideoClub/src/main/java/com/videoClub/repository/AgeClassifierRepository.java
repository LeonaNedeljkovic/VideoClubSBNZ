package com.videoClub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.videoClub.model.AgeClassifier;

@Repository
public interface AgeClassifierRepository extends JpaRepository<AgeClassifier, Long> {

}
