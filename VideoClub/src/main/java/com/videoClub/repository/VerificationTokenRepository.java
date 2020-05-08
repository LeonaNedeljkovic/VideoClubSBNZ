package com.videoClub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.videoClub.model.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
}
