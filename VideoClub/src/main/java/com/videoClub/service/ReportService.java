package com.videoClub.service;

import java.util.List;

import com.videoClub.dto.ReportDTO;
import com.videoClub.model.Report;

public interface ReportService {
	
	public Report save(ReportDTO reportDTO);
	public List<Report> getAll();

}
