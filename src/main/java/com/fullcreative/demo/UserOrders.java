package com.fullcreative.demo;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class UserOrders {
	
	
	@PrimaryKey
	@Persistent
	public String id;
	
	@Persistent
	public String userMailID;
	
	@Persistent
	public String Orders;
	
	@Persistent
	public String Status;
	
	
	public UserOrders(String id, String userMailID, String orders, String status) {
		super();
		this.id = id;
		this.userMailID = userMailID;
		Orders = orders;
		Status = status;
	}
	
	

	@Override
	public String toString() {
		return "UserOrders [id=" + id + ", userMailID=" + userMailID + ", Orders=" + Orders + ", Status=" + Status
				+ "]";
	}
	

}
