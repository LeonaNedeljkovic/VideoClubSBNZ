package com.videoClub.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videoClub.model.AgeClassifier;
import com.videoClub.repository.AgeClassifierRepository;
import com.videoClub.service.AgeClassifierService;

@Service
public class AgeClassifierServiceImpl implements AgeClassifierService {

	@Autowired
	private AgeClassifierRepository ageClassifierRepository;
	
	@Override
	public List<AgeClassifier> getAll() {
		return ageClassifierRepository.findAll();
	}

	@Override
	public List<AgeClassifier> saveAll(List<AgeClassifier> classifiers) {
		return ageClassifierRepository.saveAll(classifiers);
	}
}
