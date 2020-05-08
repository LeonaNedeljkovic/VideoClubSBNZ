package com.videoClub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.videoClub.model.VerificationToken;
import com.videoClub.repository.VerificationTokenRepository;
import com.videoClub.service.VerificationTokenService;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

	@Autowired
	private VerificationTokenRepository verificationTokenRepository;

	@Override
	public void saveToken(VerificationToken token) {
		verificationTokenRepository.save(token);

	}
}
