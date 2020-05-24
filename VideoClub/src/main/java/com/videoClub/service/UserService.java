package com.videoClub.service;

import java.util.List;
import com.videoClub.model.User;

public interface UserService {
	User findById(Long id);

	User findByUsername(String username);

	List<User> findAll();

	String editProfile(User user);

	User findUserByToken(String token);

	User save(User user);
	
	public List<User> save(List<User> users);
	
}
