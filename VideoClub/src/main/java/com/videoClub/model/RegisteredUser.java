package com.videoClub.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.videoClub.model.enumeration.Rank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class RegisteredUser extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "registry_date")
	private LocalDateTime registryDate;
	
	@Column(name = "immunity_points")
	private int immunityPoints;
	
	@Column(name = "available_minutes")
	private int availableMinutes;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "title")
	private Rank title;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "immunity")
	private Rank immunity;
	
	@ManyToMany
	private List<Action> action = new ArrayList<Action>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Review> reviews = new ArrayList<Review>();
	
	@ManyToMany
	private List<Film> favouriteFilms = new ArrayList<Film>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Purchase> purchases = new ArrayList<Purchase>();

	public RegisteredUser() {
		super();
	}

	public RegisteredUser(LocalDateTime registryDate, int immunityPoints, int availableMinutes, Rank title,
			Rank immunity, List<Action> action, List<Review> reviews, List<Film> favouriteFilms,
			List<Purchase> purchases) {
		super();
		this.registryDate = registryDate;
		this.immunityPoints = immunityPoints;
		this.availableMinutes = availableMinutes;
		this.title = title;
		this.immunity = immunity;
		this.action = action;
		this.reviews = reviews;
		this.favouriteFilms = favouriteFilms;
		this.purchases = purchases;
	}

	public RegisteredUser(Long id, String username, String password, String email, LocalDateTime registryDate,
			int immunityPoints, int availableMinutes, Rank title, Rank immunity, List<Action> action,
			List<Review> reviews, List<Film> favouriteFilms, List<Purchase> purchases) {
		super(id, username, password, email);
		this.registryDate = registryDate;
		this.immunityPoints = immunityPoints;
		this.availableMinutes = availableMinutes;
		this.title = title;
		this.immunity = immunity;
		this.action = action;
		this.reviews = reviews;
		this.favouriteFilms = favouriteFilms;
		this.purchases = purchases;
	}
	
	public LocalDateTime getRegistryDate() {
		return registryDate;
	}

	public void setRegistryDate(LocalDateTime registryDate) {
		this.registryDate = registryDate;
	}

	public int getImmunityPoints() {
		return immunityPoints;
	}

	public void setImmunityPoints(int immunityPoints) {
		this.immunityPoints = immunityPoints;
	}

	public int getAvailableMinutes() {
		return availableMinutes;
	}

	public void setAvailableMinutes(int availableMinutes) {
		this.availableMinutes = availableMinutes;
	}

	public Rank getTitle() {
		return title;
	}

	public void setTitle(Rank title) {
		this.title = title;
	}

	public Rank getImmunity() {
		return immunity;
	}

	public void setImmunity(Rank immunity) {
		this.immunity = immunity;
	}

	public List<Action> getAction() {
		return action;
	}

	public void setAction(List<Action> action) {
		this.action = action;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public List<Purchase> getPurchases() {
		return purchases;
	}

	public void setPurchases(List<Purchase> purchases) {
		this.purchases = purchases;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public List<Film> getFavouriteFilms() {
		return favouriteFilms;
	}

	public void setFavouriteFilms(List<Film> favouriteFilms) {
		this.favouriteFilms = favouriteFilms;
	}

	public boolean containsDiscount(){
		for(Action a : action){
			if(a instanceof Discount){
				return true;
			}
		}
		return false;
	}
	
	public boolean containsFreeContent(){
		for(Action a : action){
			if(a instanceof FreeContent){
				return true;
			}
		}
		return false;
	}
	
	public boolean containsFreeMinutes(){
		for(Action a : action){
			if(a instanceof FreeMinutes){
				return true;
			}
		}
		return false;
	}
	
}
