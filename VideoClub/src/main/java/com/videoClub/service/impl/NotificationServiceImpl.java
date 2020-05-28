package com.videoClub.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videoClub.exception.EntityForbidden;
import com.videoClub.exception.EntityNotFound;
import com.videoClub.model.Notification;
import com.videoClub.repository.NotificationRepository;
import com.videoClub.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;
	
	@Override
	public Notification open(Long id, Long userId) {
		Optional<Notification> notificationOpt = notificationRepository.findById(id);
		Notification notification = null;
		if(notificationOpt.isPresent()){
			notification = notificationOpt.get();
		}
		else{
			throw new EntityNotFound(id);
		}
		if(notification.getUser().getId() != userId){
			throw new EntityForbidden();
		}
		notification.setOpened(true);
		return notificationRepository.save(notification);
	}

	@Override
	public List<Notification> findAll(Long userId) {
		return notificationRepository.findByUserId(userId);
	}

}
