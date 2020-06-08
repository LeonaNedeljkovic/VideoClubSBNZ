package com.videoClub.event;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import com.videoClub.model.Film;

@Role(Role.Type.EVENT)
@Expires("30h")
public class FilmWatchEvent {

	private Film film;

	public FilmWatchEvent() {
		super();
	}

	public FilmWatchEvent(Film film) {
		super();
		this.film = film;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

}
