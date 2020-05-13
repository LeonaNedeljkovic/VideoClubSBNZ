package com.videoClub.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ActionEventDTO {

	private Long id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date startDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date endDate;
	private String name;
	private List<ActionDTO> actions = new ArrayList<ActionDTO>();

	public ActionEventDTO(Long id, Date startDate, Date endDate, String name, List<ActionDTO> actions) {
		super();
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.name = name;
		this.actions = actions;
	}

	public ActionEventDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<ActionDTO> getActions() {
		return actions;
	}

	public void setActions(List<ActionDTO> actions) {
		this.actions = actions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
