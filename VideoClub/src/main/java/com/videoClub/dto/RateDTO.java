package com.videoClub.dto;

public class RateDTO {

	private Long videoId;
	private Integer rate;
	
	public RateDTO() {
		super();
	}
	
	public RateDTO(Long videoId, Integer rate) {
		super();
		this.videoId = videoId;
		this.rate = rate;
	}
	
	public Long getVideoId() {
		return videoId;
	}
	
	public void setVideoId(Long videoId) {
		this.videoId = videoId;
	}
	
	public Integer getRate() {
		return rate;
	}
	
	public void setRate(Integer rate) {
		this.rate = rate;
	}
}
