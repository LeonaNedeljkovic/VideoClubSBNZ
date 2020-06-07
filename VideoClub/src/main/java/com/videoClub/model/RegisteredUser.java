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

import com.videoClub.model.enumeration.AgeCategory;
import com.videoClub.model.enumeration.Gender;
import com.videoClub.model.enumeration.Rank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class RegisteredUser extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "age")
	private int age;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "age_category")
	private AgeCategory ageCategory;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "gender")
	private Gender gender;

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
	
	@Column(name="allowed_to_purchase")
	private Boolean allowedToPurchase;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Review> reviews = new ArrayList<Review>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Notification> notifications = new ArrayList<Notification>();
	
	@ManyToMany
	private List<Film> favouriteFilms = new ArrayList<Film>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Purchase> purchases = new ArrayList<Purchase>();
	

	public RegisteredUser() {
		super();
	}

	public RegisteredUser(LocalDateTime registryDate, int immunityPoints, int availableMinutes, Rank title,
			Rank immunity, List<Action> action, List<Review> reviews, List<Notification> notificationa,
			List<Film> favouriteFilms, List<Purchase> purchases, int age, AgeCategory ageCategory, Boolean allowedToPurchase, Gender gender) {
		super();
		this.registryDate = registryDate;
		this.immunityPoints = immunityPoints;
		this.availableMinutes = availableMinutes;
		this.title = title;
		this.immunity = immunity;
		this.action = action;
		this.reviews = reviews;
		this.notifications = notificationa;
		this.favouriteFilms = favouriteFilms;
		this.purchases = purchases;
		this.age = age;
		this.ageCategory = ageCategory;
		this.allowedToPurchase = allowedToPurchase;
		this.gender = gender;
	}
	
	public RegisteredUser(Long id, String username, String password, String email, LocalDateTime registryDate,
			int immunityPoints, int availableMinutes, Rank title, Rank immunity, List<Action> action,
			List<Review> reviews, List<Notification> notificationa, List<Film> favouriteFilms,
			List<Purchase> purchases, int age, AgeCategory ageCategory, Boolean allowedToPurchase, Gender gender) {
		super(id, username, password, email,true);
		this.registryDate = registryDate;
		this.immunityPoints = immunityPoints;
		this.availableMinutes = availableMinutes;
		this.title = title;
		this.immunity = immunity;
		this.action = action;
		this.reviews = reviews;
		this.notifications = notificationa;
		this.favouriteFilms = favouriteFilms;
		this.purchases = purchases;
		this.age = age;
		this.ageCategory = ageCategory;
		this.allowedToPurchase = allowedToPurchase;
		this.gender = gender;
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
	
	

	public Boolean getAllowedToPurchase() {
		return allowedToPurchase;
	}

	public void setAllowedToPurchase(boolean allowedToPurchase) {
		this.allowedToPurchase = allowedToPurchase;
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

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public AgeCategory getAgeCategory() {
		return ageCategory;
	}

	public void setAgeCategory(AgeCategory ageCategory) {
		this.ageCategory = ageCategory;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
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
}
