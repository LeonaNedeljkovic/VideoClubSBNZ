package com.videoClub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.videoClub.model.Action;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long>{

}