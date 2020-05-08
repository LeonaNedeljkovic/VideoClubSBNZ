package com.videoClub.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserDto {

	private Long id;

	@Size(min = 3, max = 15, message = "Length of name must be between 3 and 15.")
	@NotBlank(message = "Name is not allowed to be empty.")
	private String name;

	@Size(min = 3, max = 15, message = "Length of last name must be between 3 and 15.")
	@NotBlank(message = "Last name is not allowed to be empty.")
	private String lastName;

	@Email
	private String email;

	@Size(min = 3, max = 15, message = "Length of username must be between 3 and 15.")
	@NotBlank(message = "Username is not allowed to be empty.")
	private String username;

	@Size(min = 5, message = "Password must be at least 5 characters long.")
	@NotBlank(message = "Password is not allowed to be empty.")
	private String password;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserDto(Long id,
			@Size(min = 3, max = 15, message = "Length of name must be between 3 and 15.") @NotBlank(message = "Name is not allowed to be empty.") String name,
			@Size(min = 3, max = 15, message = "Length of last name must be between 3 and 15.") @NotBlank(message = "Last name is not allowed to be empty.") String lastName,
			@Email String email,
			@Size(min = 3, max = 15, message = "Length of username must be between 3 and 15.") @NotBlank(message = "Username is not allowed to be empty.") String username,
			@Size(min = 5, message = "Password must be at least 5 characters long.") @NotBlank(message = "Password is not allowed to be empty.") String password) {
		super();
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
	}

	public UserDto() {
		super();
	}

}
