package com.videoClub.service;

import java.util.List;

import com.videoClub.model.Notification;

public interface NotificationService {

	public Notification open(Long id, Long userId);
	public List<Notification> findAll(Long userId);
}
