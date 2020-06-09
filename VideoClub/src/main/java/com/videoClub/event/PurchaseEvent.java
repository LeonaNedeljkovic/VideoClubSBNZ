package com.videoClub.event;

import java.io.Serializable;
import java.util.Date;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import com.videoClub.model.Purchase;
import com.videoClub.model.RegisteredUser;

@Role(Role.Type.EVENT)
@Expires("30h")
public class PurchaseEvent implements Serializable {
	private static final long serialVersionUID = 1L;
	private RegisteredUser user;
	private Purchase purchase;

	public PurchaseEvent(Purchase purchase, RegisteredUser user) {
		super();
		this.user = user;
		this.purchase = purchase;
	}

	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

	public RegisteredUser getUser() {
		return user;
	}

	public void setUser(RegisteredUser user) {
		this.user = user;
	}

}
