package com.videoClub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.videoClub.model.VideoContent;

@Repository
public interface VideoContentRepository extends JpaRepository<VideoContent, Long>{

}
