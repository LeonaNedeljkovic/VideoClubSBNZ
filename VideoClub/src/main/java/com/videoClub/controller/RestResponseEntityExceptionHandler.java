package com.videoClub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.videoClub.dto.MessageDto;
import com.videoClub.exception.ActionEventNotFound;
import com.videoClub.exception.ActionNotDeletable;
import com.videoClub.exception.ActionNotFound;
import com.videoClub.exception.ArtistNotDeletable;
import com.videoClub.exception.ArtistNotFound;
import com.videoClub.exception.EmptyGenreList;
import com.videoClub.exception.EmptyOfferList;
import com.videoClub.exception.InvalidDate;
import com.videoClub.exception.OfferNotDeletable;
import com.videoClub.exception.OfferNotFound;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = ArtistNotDeletable.class)
    protected ResponseEntity<MessageDto> handleArtistNotDeletable(ArtistNotDeletable e) {
		return new ResponseEntity<>(new MessageDto("Artist Not Deletable", e.getMessage()), HttpStatus.OK);
    }
	
	@ExceptionHandler(value = ArtistNotFound.class)
    protected ResponseEntity<MessageDto> handleArtistNotFound(ArtistNotFound e) {
		return new ResponseEntity<>(new MessageDto("Artist Not Found", e.getMessage()), HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(value = OfferNotFound.class)
    protected ResponseEntity<MessageDto> handleOfferNotFound(OfferNotFound e) {
		return new ResponseEntity<>(new MessageDto("Offer Not Found", e.getMessage()), HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(value = OfferNotDeletable.class)
    protected ResponseEntity<MessageDto> handleOfferNotDeletable(OfferNotDeletable e) {
		return new ResponseEntity<>(new MessageDto("Offer Not Deletable", e.getMessage()), HttpStatus.OK);
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
	
	@ExceptionHandler(value = ActionEventNotFound.class)
    protected ResponseEntity<MessageDto> handleActionEventNotFound(ActionEventNotFound e) {
		return new ResponseEntity<>(new MessageDto("Action Event Not Found", e.getMessage()), HttpStatus.OK);
    }
	
	@ExceptionHandler(value = ActionNotFound.class)
    protected ResponseEntity<MessageDto> handleActionNotFound(ActionNotFound e) {
		return new ResponseEntity<>(new MessageDto("Action Not Found", e.getMessage()), HttpStatus.OK);
    }
	
	@ExceptionHandler(value = ActionNotDeletable.class)
    protected ResponseEntity<MessageDto> handleActionNotDeletable(ActionNotDeletable e) {
		return new ResponseEntity<>(new MessageDto("Action Not Deletable", e.getMessage()), HttpStatus.OK);
    }
}
