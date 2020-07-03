package com.videoClub.dto;

import java.util.ArrayList;
import java.util.List;

public class FilmDTO {

	private long id;
	private String name;
	private String description;
	private String genre;
	private int year;
	private List<Long> actorIds = new ArrayList<Long>();
	private Long directorId;
	private Long writtenId;
	private int duration;
	private String poster;
	
	public FilmDTO() {
		super();
	}

	public FilmDTO(long id, String name, String description, String genre, int year, List<Long> actorIds,
			Long directorId, Long writtenId,int duration, String poster) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.genre = genre;
		this.year = year;
		this.actorIds = actorIds;
		this.directorId = directorId;
		this.writtenId=writtenId;
		this.duration = duration;
		this.poster=poster;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public List<Long> getActorIds() {
		return actorIds;
	}

	public void setActorIds(List<Long> actorIds) {
		this.actorIds = actorIds;
	}

	public Long getDirectorId() {
		return directorId;
	}

	public void setDirectorId(Long directorId) {
		this.directorId = directorId;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public Long getWrittenId() {
		return writtenId;
	}

	public void setWrittenId(Long writtenId) {
		this.writtenId = writtenId;
	}
	
	
	
	
}