package com.videoClub.dto;

public class ReviewDetailsDTO {

	private boolean watched;
	private boolean addedToFavourites;
	private int rate;
	private boolean startedWatching;
	
	public ReviewDetailsDTO() {
		super();
	}

	public ReviewDetailsDTO(boolean watched, boolean addedToFavourites, int rate, boolean startedWatching) {
		super();
		this.watched = watched;
		this.addedToFavourites = addedToFavourites;
		this.rate = rate;
		this.startedWatching = startedWatching;
	}

	public boolean isWatched() {
		return watched;
	}

	public void setWatched(boolean watched) {
		this.watched = watched;
	}

	public boolean isAddedToFavourites() {
		return addedToFavourites;
	}

	public void setAddedToFavourites(boolean addedToFavourites) {
		this.addedToFavourites = addedToFavourites;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public boolean isStartedWatching() {
		return startedWatching;
	}

	public void setStartedWatching(boolean startedWatching) {
		this.startedWatching = startedWatching;
	}
}
