package com.videoClub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.videoClub.model.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

}
