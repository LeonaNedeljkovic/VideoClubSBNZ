package com.videoClub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.videoClub.dto.MessageDto;
import com.videoClub.exception.ArtistNotDeletable;
import com.videoClub.exception.ArtistNotFound;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = ArtistNotDeletable.class)
    protected ResponseEntity<MessageDto> handleArtistNotDeletable(ArtistNotDeletable e) {
		return new ResponseEntity<>(new MessageDto("Artist Not Deletable", e.getMessage()), HttpStatus.OK);
    }
	
	@ExceptionHandler(value = ArtistNotFound.class)
    protected ResponseEntity<MessageDto> handleArtistNotFound(ArtistNotFound e) {
		return new ResponseEntity<>(new MessageDto("Artist Not Found", e.getMessage()), HttpStatus.OK);
    }
}
