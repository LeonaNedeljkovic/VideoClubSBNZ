package com.videoClub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.videoClub.model.Badge;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {

	@Transactional
	@Modifying
	public void deleteByUserId(Long userId);
}
