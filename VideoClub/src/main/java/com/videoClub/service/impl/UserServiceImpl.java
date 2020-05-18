package com.videoClub.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videoClub.exception.ArtistNotFound;
import com.videoClub.model.User;
import com.videoClub.repository.UserRepository;
import com.videoClub.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	public User findUserByToken(String token) {
		return userRepository.findByToken(token);
	}

	//@Transactional(readOnly = false)
	public void save(User user) {
		userRepository.save(user);
	}

	//@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String editProfile(User user) {
		User userToEdit = userRepository.findByUsername(user.getUsername());
		if (userToEdit == null) {
			return "User with given username does not exist.";
		}

		try {
			userRepository.save(userToEdit);
		} catch (Exception e) {
			return "Database error.";
		}

		return null;
	}

	@Override
	public User findById(Long id) {
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()){
			return user.get();
		}
		else{
			throw new ArtistNotFound(id);
		}
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

}
