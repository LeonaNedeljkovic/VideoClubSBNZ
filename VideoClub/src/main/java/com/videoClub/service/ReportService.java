package com.videoClub.service;

import com.videoClub.dto.ReportDTO;
import com.videoClub.model.Report;

public interface ReportService {
	
	public Report save(ReportDTO reportDTO);

}
