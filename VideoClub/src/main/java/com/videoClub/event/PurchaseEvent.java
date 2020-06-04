package com.videoClub.event;

import java.io.Serializable;
import java.util.Date;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import com.videoClub.model.Purchase;
import com.videoClub.model.RegisteredUser;

@Role(Role.Type.EVENT)
@Timestamp("executionTime")
@Expires("1h")
public class PurchaseEvent implements Serializable {
	private static final long serialVersionUID = 1L;
	private RegisteredUser user;
	private Purchase purchase;
	private Date executionTime;

	public PurchaseEvent(Purchase purchase, RegisteredUser user) {
		super();
		this.user = user;
		this.purchase = purchase;
		this.executionTime = new Date();
	}

	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

	public Date getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Date executionTime) {
		this.executionTime = executionTime;
	}

	public RegisteredUser getUser() {
		return user;
	}

	public void setUser(RegisteredUser user) {
		this.user = user;
	}

}
