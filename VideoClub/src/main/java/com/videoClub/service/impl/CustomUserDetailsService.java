package com.videoClub.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
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
import com.videoClub.factory.AgeClassifierFactory;
import com.videoClub.model.Administrator;
import com.videoClub.model.AgeClassifier;
import com.videoClub.model.Authority;
import com.videoClub.model.RegisteredUser;
import com.videoClub.model.User;
import com.videoClub.model.enumeration.Rank;
import com.videoClub.model.enumeration.UserRole;
import com.videoClub.repository.UserRepository;
import com.videoClub.service.AgeClassifierService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AgeClassifierService ageClassifierService;
	
	@Autowired
	private KieContainer kieContainer;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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
		if (role.equals(UserRole.ROLE_REGISTERED_USER)){
			RegisteredUser newUser = new RegisteredUser();
			Authority a = new Authority();
			a.setName(UserRole.ROLE_REGISTERED_USER);
			List<Authority> authorities = new ArrayList<>();
			authorities.add(a);
			
			newUser.setAuthorities(authorities);
			newUser.setEmail(user.getEmail());
			newUser.setUsername(user.getUsername());
			newUser.setPassword(this.encodePassword(user.getPassword()));
			newUser.setLastPasswordResetDate(new Timestamp(System.currentTimeMillis()));
			newUser.setRegistryDate(LocalDateTime.parse(sdf.format(new Date()),df));
			newUser.setImmunityPoints(0);
			newUser.setAvailableMinutes(60);
			newUser.setTitle(Rank.NONE);
			newUser.setImmunity(Rank.NONE);
			newUser.setAllowedToPurchase(true);
			newUser.setAllowedToLogIn(true);
			newUser.setAge(user.getAge());
			newUser.setGender(user.getGender());
			
			KieSession kieSession = kieContainer.newKieSession("ageClassificationRulesSession");
			kieSession.setGlobal("ageClassifierFactory", initializeAgeClassifier());
			kieSession.insert(newUser);
			kieSession.fireAllRules();
			kieSession.dispose();
			
			this.userRepository.save(newUser);
		}else {
			Administrator newUser = new Administrator();
			Authority a = new Authority();
			a.setName(UserRole.ROLE_ADMIN);
			List<Authority> authorities = new ArrayList<>();
			authorities.add(a);
			newUser.setAuthorities(authorities);
			newUser.setEmail(user.getEmail());
			newUser.setUsername(user.getUsername());
			newUser.setPassword(this.encodePassword(user.getPassword()));
			newUser.setLastPasswordResetDate(new Timestamp(System.currentTimeMillis()));
			this.userRepository.save(newUser);
		}
		return true;
	}
	
	public AgeClassifierFactory initializeAgeClassifier(){
		List<AgeClassifier> classifiers = ageClassifierService.getAll();
		return new AgeClassifierFactory(classifiers);
	}
	
	public Boolean editUser(UserDto userDto) {
		 User user = (User) this.loadUserByUsername(userDto.getUsername());
		 user.setPassword(this.encodePassword(userDto.getPassword())); 
		 user.setEmail(userDto.getEmail());
		 saveUser(user);
		 return true;
	}

}
