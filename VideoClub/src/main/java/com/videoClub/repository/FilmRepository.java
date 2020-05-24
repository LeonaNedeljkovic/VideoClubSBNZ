package com.videoClub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.videoClub.model.Film;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long>{

}
