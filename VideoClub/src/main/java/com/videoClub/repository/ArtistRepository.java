package com.videoClub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.videoClub.model.Artist;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long>{

	@Query("SELECT DISTINCT artist FROM Artist artist WHERE artist.id IN "
			+ "(SELECT DISTINCT a FROM Artist a INNER JOIN a.roles film "
		    +	"WHERE film.id IN (SELECT r.film.id from Review r WHERE r.user.id = ?1)) "
		    + "OR artist.id IN "
		    + "(SELECT r.film.director.id from Review r WHERE r.user.id = ?1) "
		    + "OR artist.id IN "
		    + "(SELECT r.film.writtenBy.id from Review r WHERE r.user.id = ?1)")
	public List<Artist> getWatchedArtists(Long userId);
}