package com.videoClub.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videoClub.dto.VideoContentDTO;
import com.videoClub.exception.ArtistNotFound;
import com.videoClub.model.Artist;
import com.videoClub.model.VideoContent;
import com.videoClub.model.enumeration.Genre;
import com.videoClub.repository.VideoContentRepository;
import com.videoClub.service.ArtistService;
import com.videoClub.service.VideoContentService;

@Service
public class VideoContentServiceImpl implements VideoContentService{

	@Autowired
	private VideoContentRepository videoContentRepository;
	
	@Autowired
	private ArtistService artistService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Override
	public VideoContent save(VideoContentDTO videoContentDTO) {
		VideoContent videoContent = new VideoContent();
		List<Artist> actors = new ArrayList<Artist>();
		for(Long id : videoContentDTO.getActorIds()){
			actors.add(artistService.getOne(id));
		}
		videoContent.setActors(actors);
		videoContent.setDirector(artistService.getOne(videoContentDTO.getDirectorId()));
		videoContent.setName(videoContentDTO.getName());
		videoContent.setDescription(videoContentDTO.getDescription());
		videoContent.setDuration(videoContentDTO.getDuration());
		videoContent.setYear(videoContentDTO.getYear());
		videoContent.setGenre(Genre.valueOf(videoContentDTO.getGenre()));
		videoContent.setPublishDate(LocalDate.parse(sdf.format(new Date()),df));
		videoContent.setRating(0);
		return videoContentRepository.save(videoContent);
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
		return videoContentRepository.findAll();
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}
}
