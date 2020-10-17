package com.fullcreative.demo;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Products {

	@PrimaryKey
	@Persistent
	public String uuid;
	
	@Persistent
	public String ProductName;
	
	@Persistent
	public String ProductValue;
	
	@Persistent
	public String AvailableStock;

	public Products(String uuid, String ProductName, String ProductValue, String AvailableStock) {
		super();
		this.uuid = uuid;
		this.ProductName = ProductName;
		this.ProductValue = ProductValue;
		this.AvailableStock = AvailableStock;
	}

	@Override
	public String toString() {
		return "Products [uuid=" + uuid + ", ProductName=" + ProductName + ", ProductValue=" + ProductValue
				+ ", AvailableStock=" + AvailableStock + "]";
	}

	
	
}
