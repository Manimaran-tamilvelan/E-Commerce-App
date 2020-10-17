package com.fullcreative.demo;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class ShippingAddress {

	@PrimaryKey
	@Persistent
	public String currentUser;
	@Persistent
	public String firstName;
	@Persistent
	public String lastName;
	@Persistent
	public String address;
	@Persistent
	public String townOrCity;
	@Persistent
	public String pincode;
	
	@Persistent
	public String mobileNo;
	
	@Persistent
	public String mailId;
	
	
	public ShippingAddress(String currentUser, String firstName, String lastName, String address, String townOrCity, String pincode, String mobileNo, String mailId) {
		super();
		this.currentUser = currentUser;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.townOrCity = townOrCity;
		this.pincode = pincode;
		
		this.mobileNo = mobileNo;
		this.mailId = mailId;
	}

	@Override
	public String toString() {
		return "ShippingAddress [currentUser=" + currentUser + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", address=" + address + ", townOrCity=" + townOrCity + ", pincode=" + pincode + ", mobileNo="
				+ mobileNo + ", mailId=" + mailId + "]";
	}






}
