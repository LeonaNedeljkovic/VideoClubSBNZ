package com.videoClub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.videoClub.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{

	public List<Notification> findByUserId(Long UserId);
}
