package com.videoClub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.videoClub.model.TimeInterval;

@Repository
public interface TimeIntervalRepository extends JpaRepository<TimeInterval, Long>{

}
