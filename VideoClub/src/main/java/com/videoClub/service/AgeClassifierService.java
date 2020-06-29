package com.videoClub.service;

import java.util.List;

import com.videoClub.model.AgeClassifier;

public interface AgeClassifierService {

	public List<AgeClassifier> getAll();
	public List<AgeClassifier> saveAll(List<AgeClassifier> categories);
}
