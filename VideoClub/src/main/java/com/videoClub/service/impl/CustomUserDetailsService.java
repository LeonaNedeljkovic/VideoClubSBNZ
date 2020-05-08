package com.videoClub.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.videoClub.dto.UserDto;
import com.videoClub.model.Administrator;
import com.videoClub.model.Authority;
import com.videoClub.model.Badge;
import com.videoClub.model.Purchase;
import com.videoClub.model.Rate;
import com.videoClub.model.RegisteredUser;
import com.videoClub.model.Review;
import com.videoClub.model.User;
import com.videoClub.model.enumeration.UserRole;
import com.videoClub.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	public boolean saveUser(User ru) {
		this.userRepository.save(ru);
		/*try {
			this.userRepository.save(ru);
		} catch (Exception e) {
			return false;
		}
		*/
		return true;
	}

	public boolean usernameTaken(String username) {
		User user = userRepository.findByUsername(username);

		return user != null;
	}

	// Funkcija koja na osnovu username-a iz baze vraca objekat User-a
	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
	
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		} else {
			return user;
		}
	}

	public UserDetails loadUserById(long id) {
		User user = userRepository.findById(id);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with id '%s'.", id));
		} else {
			return user;
		}
	}

	public String encodePassword(String password) {
		return this.passwordEncoder.encode(password);
	}

	// Funkcija pomocu koje korisnik menja svoju lozinku
	public void changePassword(String oldPassword, String newPassword) {

		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String username = currentUser.getName();
		if (authenticationManager != null) {
		

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
		} else {

			return;
		}

		User user = (User) loadUserByUsername(username);

		// pre nego sto u bazu upisemo novu lozinku, potrebno ju je hesirati
		// ne zelimo da u bazi cuvamo lozinke u plain text formatu
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);

	}
	
	public boolean registerUser(UserDto user, UserRole role) {
		if (this.usernameTaken(user.getUsername())) {
			return false;
		}
		if (role.equals(UserRole.REGISTERED_USER)){
			RegisteredUser newUser = new RegisteredUser();
			Authority a = new Authority();
			a.setName(UserRole.REGISTERED_USER);
			newUser.setBadges(new ArrayList<Badge>());
			newUser.setReviews(new ArrayList<Review>());
			newUser.setRates(new ArrayList<Rate>());
			newUser.setPurchases(new ArrayList<Purchase>());
			newUser.setImmunityPoints(0);
			newUser.setAvailableMinutes(0);
			newUser.setTitle(null);
			newUser.setImmunity(null);
			newUser.setAction(null);
			List<Authority> authorities = new ArrayList<>();
			authorities.add(a);
			newUser.setAuthorities(authorities);
			newUser.setId(user.getId());
			newUser.setEmail(user.getEmail());
			newUser.setUsername(user.getUsername());
			newUser.setPassword(this.encodePassword(user.getPassword()));
			newUser.setLastPasswordResetDate(new Timestamp(System.currentTimeMillis()));
			this.userRepository.save(newUser);
		}else {
			Administrator newUser = new Administrator();
			Authority a = new Authority();
			a.setName(UserRole.ADMIN);
			List<Authority> authorities = new ArrayList<>();
			authorities.add(a);
			newUser.setAuthorities(authorities);
			newUser.setId(user.getId());
			newUser.setEmail(user.getEmail());
			newUser.setUsername(user.getUsername());
			newUser.setPassword(this.encodePassword(user.getPassword()));
			newUser.setLastPasswordResetDate(new Timestamp(System.currentTimeMillis()));
			this.userRepository.save(newUser);
		}
		
		
		return true;
	}
	
	public Boolean editUser(UserDto userDto) {
		 User user = (User) this.loadUserByUsername(userDto.getUsername());
		 user.setPassword(this.encodePassword(userDto.getPassword())); 
		 user.setEmail(userDto.getEmail());
		 saveUser(user);
		 return true;
	}

}
