package com.videoClub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.videoClub.model.Film;
import com.videoClub.model.enumeration.Genre;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long>{

	@Query("SELECT f FROM Film f " +
		    "WHERE f.id NOT IN "
		    + "(SELECT r.film.id FROM Review r WHERE r.user.id = ?1)")
	public List<Film> getUnwatchedFilms(Long userId);
	
	@Query("SELECT f FROM Film f " +
		    "WHERE f.id NOT IN "
		    + "(SELECT r.film.id FROM Review r WHERE r.user.id = ?1) "
		  + "AND (f.director.id = ?2 OR f.writtenBy.id = ?2 "
		  + 	"OR ?2 IN (SELECT a.id FROM f.actors a))")
	public List<Film> getUnwatchedFilmsByArtist(Long userId, Long artistId);
	
	@Query("SELECT f FROM Film f " +
		    "ORDER BY f.rating DESC")
	public List<Film> getTopRated();
	
	@Query("SELECT r.film from Review r WHERE r.watched = 1 "+
			"GROUP BY r.film ORDER BY count(r.film) DESC")
	public List<Film> getMostPopular();
	
	@Query("SELECT f from Film f WHERE f.genre = ?1 "+
			"ORDER BY f.rating DESC")
	public List<Film> getByGenre(Genre genre);
	
	@Query("SELECT f from Film f "+
			"WHERE lower(f.name) like lower(concat('%', ?1,'%'))")
	public List<Film> getByName(String filmName);
}
