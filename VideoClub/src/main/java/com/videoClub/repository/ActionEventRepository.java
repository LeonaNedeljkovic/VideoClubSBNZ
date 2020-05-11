package com.videoClub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.videoClub.model.ActionEvent;

@Repository
public interface ActionEventRepository extends JpaRepository<ActionEvent, Long>{

}