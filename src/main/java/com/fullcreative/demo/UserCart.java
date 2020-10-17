package com.fullcreative.demo;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class UserCart {
	
	
	@PrimaryKey
	@Persistent
	public String userId;
	
	@Persistent
	public String ProductID;

	public UserCart(String userId, String productID) {
		super();
		this.userId = userId;
		ProductID = productID;
	}

	@Override
	public String toString() {
		return "UserCart [userId=" + userId + ", ProductID=" + ProductID + "]";
	}

}
