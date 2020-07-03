package com.videoClub.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videoClub.dto.ReportDTO;
import com.videoClub.model.Film;
import com.videoClub.model.Report;
import com.videoClub.repository.FilmRepository;
import com.videoClub.repository.ReportRepository;
import com.videoClub.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	ReportRepository reportRepository;

	@Autowired
	FilmRepository filmRepository;

	@Override
	public Report save(ReportDTO reportDTO) {
		if (reportDTO.getFilm() != null) {
			Film film = filmRepository.getOne(reportDTO.getFilm().getId());
			return reportRepository.save(new Report(reportDTO.getEarned(), reportDTO.getDate().toString(),
					reportDTO.getNumberOfViews(), film));
		} else {
			return reportRepository.save(new Report(reportDTO.getEarned(), reportDTO.getDate().toString(),
					reportDTO.getNumberOfViews(), null));
		}
	}

	@Override
	public List<Report> getAll() {
		return reportRepository.findAll();
	}

}
