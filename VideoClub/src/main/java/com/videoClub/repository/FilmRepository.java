package com.videoClub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.videoClub.model.Film;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long>{

	@Query("SELECT f FROM Film f " +
		    "WHERE f.id NOT IN "
		    + "(SELECT r.film.id FROM Review r WHERE r.user.id = ?1)")
	public List<Film> getUnwatchedFilms(Long userId);
}
