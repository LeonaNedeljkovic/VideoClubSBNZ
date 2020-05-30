package com.videoClub.event;

import java.io.Serializable;
import java.util.Date;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import com.videoClub.model.User;

@Role(Role.Type.EVENT)
@Timestamp("executionTime")
@Expires("10m")
public class LoggingEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	private Date executionTime;
	private User user;
	private Boolean successful;

	public LoggingEvent(User user,Boolean successful) {
		super();
		this.executionTime = new Date();
		this.user = user;
		this.successful = successful;
	}

	public LoggingEvent() {
		super();
	}

	public Date getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Date executionTime) {
		this.executionTime = executionTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getSuccessful() {
		return successful;
	}

	public void setSuccessful(Boolean successful) {
		this.successful = successful;
	}
	
	
	
	

}
