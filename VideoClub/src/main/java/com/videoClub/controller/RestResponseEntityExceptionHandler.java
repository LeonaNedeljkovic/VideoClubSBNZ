package com.videoClub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.videoClub.dto.MessageDto;
import com.videoClub.exception.*;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value = EntityNotDeletable.class)
    protected ResponseEntity<MessageDto> handleEntityNotDeletable(EntityNotDeletable e) {
		return new ResponseEntity<>(new MessageDto("Entity Not Deletable", e.getMessage()), HttpStatus.OK);
    }
	
	@ExceptionHandler(value = EntityNotFound.class)
    protected ResponseEntity<MessageDto> handleEntityNotFound(EntityNotFound e) {
		return new ResponseEntity<>(new MessageDto("Entity Not Found", e.getMessage()), HttpStatus.OK);
    }
	
	@ExceptionHandler(value = InvalidDate.class)
    protected ResponseEntity<MessageDto> handleInvalidDate(InvalidDate e) {
		return new ResponseEntity<>(new MessageDto("Invalid Date", e.getMessage()), HttpStatus.OK);
    }
	
	@ExceptionHandler(value = EmptyOfferList.class)
    protected ResponseEntity<MessageDto> handleEmptyOfferList(EmptyOfferList e) {
		return new ResponseEntity<>(new MessageDto("Empty Offer List", e.getMessage()), HttpStatus.OK);
    }
	
	@ExceptionHandler(value = EmptyGenreList.class)
    protected ResponseEntity<MessageDto> handleEmptyGenreList(EmptyGenreList e) {
		return new ResponseEntity<>(new MessageDto("Empty Genre List", e.getMessage()), HttpStatus.OK);
    }
	
	@ExceptionHandler(value = IllegalArgumentException.class)
    protected ResponseEntity<MessageDto> handleIllegalArgumentException() {
		return new ResponseEntity<>(new MessageDto("Illegal Argument Exception","Illegal Argument Exception handled."), HttpStatus.OK);
    }
	
	@ExceptionHandler(value = NotLoggedIn.class)
    protected ResponseEntity<MessageDto> handleNotLoggedIn(NotLoggedIn e) {
		return new ResponseEntity<>(new MessageDto("Not Logged In", e.getMessage()), HttpStatus.FORBIDDEN);
    }
	
	@ExceptionHandler(value = EntityForbidden.class)
    protected ResponseEntity<MessageDto> handleEntityForbidden(EntityForbidden e) {
		return new ResponseEntity<>(new MessageDto("Entity Forbidden", e.getMessage()), HttpStatus.FORBIDDEN);
    }
	
	@ExceptionHandler(value = NotEnoughMinutes.class)
    protected ResponseEntity<MessageDto> handleNotEnoughMinutes(NotEnoughMinutes e) {
		return new ResponseEntity<>(new MessageDto("Not Enough Minutes", e.getMessage()), HttpStatus.OK);
    }
	
	@ExceptionHandler(value = InvalidParameters.class)
    protected ResponseEntity<MessageDto> handleInvalidReview(InvalidParameters e) {
		return new ResponseEntity<>(new MessageDto("Invalid Parameters", e.getMessage()), HttpStatus.OK);
    }
	
	@ExceptionHandler(value = InvalidDiscount.class)
    protected ResponseEntity<MessageDto> handleInvalidDiscount(InvalidDiscount e) {
		return new ResponseEntity<>(new MessageDto("Invalid Discount", e.getMessage()), HttpStatus.OK);
    }
	
	@ExceptionHandler(value = InvalidFreeMinutes.class)
    protected ResponseEntity<MessageDto> handleInvalidFreeMinutes(InvalidFreeMinutes e) {
		return new ResponseEntity<>(new MessageDto("Invalid Free Minutes", e.getMessage()), HttpStatus.OK);
    }
	
	@ExceptionHandler(value = InvalidImmunity.class)
    protected ResponseEntity<MessageDto> handleInvalidImmunity(InvalidImmunity e) {
		return new ResponseEntity<>(new MessageDto("Invalid Immunity", e.getMessage()), HttpStatus.OK);
    }
	
	@ExceptionHandler(value = InvalidTitle.class)
    protected ResponseEntity<MessageDto> handleInvalidTitle(InvalidTitle e) {
		return new ResponseEntity<>(new MessageDto("Invalid Title", e.getMessage()), HttpStatus.OK);
    }
	
	@ExceptionHandler(value = FilmNotReviewed.class)
    protected ResponseEntity<MessageDto> handleFilmNotReviewed(FilmNotReviewed e) {
		return new ResponseEntity<>(new MessageDto("Film Not Reviewed", e.getMessage()), HttpStatus.OK);
    }
    
    @ExceptionHandler(value = InvalidRate.class)
    protected ResponseEntity<MessageDto> handleInvalidRate(InvalidRate e) {
		return new ResponseEntity<>(new MessageDto("Invalid Rate", e.getMessage()), HttpStatus.OK);
    }
    
    @ExceptionHandler(value = ReviewNotRated.class)
    protected ResponseEntity<MessageDto> handleReviewNotRated(ReviewNotRated e) {
		return new ResponseEntity<>(new MessageDto("Review Not Rated", e.getMessage()), HttpStatus.OK);
    }
    
    @ExceptionHandler(value = TooManyPurchasesFromUser.class)
    protected ResponseEntity<MessageDto> handleTooManyPurchasesFromUser(TooManyPurchasesFromUser e) {
		return new ResponseEntity<>(new MessageDto("Too many purchases from user", e.getMessage()), HttpStatus.OK);
    }
    
    @ExceptionHandler(value = RestrictedAgeCategory.class)
    protected ResponseEntity<MessageDto> handleRestrictedAgeCategory(RestrictedAgeCategory e) {
		return new ResponseEntity<>(new MessageDto("Restricted Age Category", e.getMessage()), HttpStatus.OK);
    }
}
