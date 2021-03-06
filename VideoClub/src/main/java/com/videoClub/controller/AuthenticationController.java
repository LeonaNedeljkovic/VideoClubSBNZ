package com.videoClub.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.videoClub.auth.JwtAuthenticationRequest;
import com.videoClub.dto.MessageDto;
import com.videoClub.dto.UserDto;
import com.videoClub.event.LoggingEvent;
import com.videoClub.exception.NotLoggedIn;
import com.videoClub.model.Administrator;
import com.videoClub.model.RegisteredUser;
import com.videoClub.model.User;
import com.videoClub.model.UserTokenState;
import com.videoClub.model.enumeration.UserRole;
import com.videoClub.security.TokenHelper;
import com.videoClub.service.UserService;
import com.videoClub.service.impl.CustomUserDetailsService;

@RestController
@RequestMapping(value = "/auth")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class AuthenticationController {

	@Autowired
	TokenHelper tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private UserService userService;

	@Autowired
	@Qualifier("loggingKieSession")
	private KieSession kieSession;

	@PostMapping(value = "/registerUser")
	public ResponseEntity<Boolean> registerUser(@RequestBody UserDto user) {
		Boolean result = this.userDetailsService.registerUser(user, UserRole.ROLE_REGISTERED_USER);
		if (result == true) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping(value = "/registerAdmin")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Boolean> registerAdmin(@RequestBody UserDto user) {
		Boolean result = this.userDetailsService.registerUser(user, UserRole.ROLE_ADMIN);
		if (result == true) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}

	}
	
	@GetMapping(value = "/user")
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	public ResponseEntity<RegisteredUser> getUser() {
		RegisteredUser user = (RegisteredUser) this.userDetailsService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		if(user == null){
			throw new NotLoggedIn();
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PostMapping(value = "/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
			HttpServletResponse response) throws AuthenticationException, IOException {
		final Authentication authentication;
		try {
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			User user = userService.findByUsername(authenticationRequest.getUsername());
			if (user != null) {
				LoggingEvent loggingEvent = new LoggingEvent(user,false);
				kieSession.insert(loggingEvent);
				kieSession.fireAllRules();
				userService.save(loggingEvent.getUser());
				if(loggingEvent.getUser().getAllowedToLogIn() == false) {
					return new ResponseEntity<>(new MessageDto("Not allowed to login.", "You can not login after three failed attempts. Try again after 3 minutes."), HttpStatus.OK);
				}
			}
			return new ResponseEntity<>(new MessageDto("Wrong username or password.", "Error"), HttpStatus.OK);
		} catch (DisabledException e) {
			return new ResponseEntity<>(new MessageDto("Account is not verified. Check your email.", "Error"),
					HttpStatus.OK);
		}

		User user = (User) authentication.getPrincipal();
		LoggingEvent loggingEvent = new LoggingEvent(user,true);
		kieSession.insert(loggingEvent);
		kieSession.fireAllRules();
		userService.save(user);

		if (user.getAllowedToLogIn() == true) {

			// Ubaci username + password u kontext

			SecurityContextHolder.getContext().setAuthentication(authentication);
			// System.out.println("NAME"+SecurityContextHolder.getContext().getAuthentication().getName());
			// Kreiraj token
			String jwt = tokenUtils.generateToken(user.getUsername());
			int expiresIn = tokenUtils.getExpiredIn();
			UserRole userType = null;

			if (user instanceof Administrator) {
				userType = UserRole.ROLE_ADMIN;
			} else {
				userType = UserRole.ROLE_REGISTERED_USER;
			}

			// Vrati token kao odgovor na uspesno autentifikaciju
			return new ResponseEntity<>(new UserTokenState(jwt, expiresIn, userType), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new MessageDto("Not allowed to login.", "You can not login after three failed attempts. Try again after 3 minutes."), HttpStatus.OK);
		}
	}

}
