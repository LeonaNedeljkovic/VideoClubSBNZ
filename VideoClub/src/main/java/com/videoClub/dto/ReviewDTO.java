package com.videoClub.dto;

public class ReviewDTO {

	private long id;
	private long videoContentId;
	private int startMinute;
	private int endMinute;
	
	public ReviewDTO() {
		super();
	}

	public ReviewDTO(long id, long videoContentId, int startMinute, int endMinute) {
		super();
		this.id = id;
		this.videoContentId = videoContentId;
		this.startMinute = startMinute;
		this.endMinute = endMinute;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getVideoContentId() {
		return videoContentId;
	}

	public void setVideoContentId(long videoContentId) {
		this.videoContentId = videoContentId;
	}

	public int getStartMinute() {
		return startMinute;
	}

	public void setStartMinute(int startMinute) {
		this.startMinute = startMinute;
	}

	public int getEndMinute() {
		return endMinute;
	}

	public void setEndMinute(int endMinute) {
		this.endMinute = endMinute;
	}
}
