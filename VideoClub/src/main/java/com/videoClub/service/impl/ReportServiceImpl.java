package com.videoClub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videoClub.dto.ReportDTO;
import com.videoClub.model.Report;
import com.videoClub.repository.ReportRepository;
import com.videoClub.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	ReportRepository reportRepository;

	@Override
	public Report save(ReportDTO reportDTO) {
		return reportRepository.save(new Report(reportDTO.getEarned(),reportDTO.getDate(),reportDTO.getNumberOfViews(),reportDTO.getFilm()));
	}

}
