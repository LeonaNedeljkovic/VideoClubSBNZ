package com.videoClub.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videoClub.exception.ArtistNotFound;
import com.videoClub.model.VideoContent;
import com.videoClub.repository.VideoContentRepository;
import com.videoClub.service.VideoContentService;

@Service
public class VideoContentServiceImpl implements VideoContentService{

	@Autowired
	private VideoContentRepository videoContentRepository;

	@Override
	public VideoContent save(VideoContent video) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VideoContent getOne(Long id) {
		Optional<VideoContent> video = videoContentRepository.findById(id);
		if(video.isPresent()){
			return video.get();
		}
		else{
			throw new ArtistNotFound(id);
		}
	}

	@Override
	public List<VideoContent> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}
}
