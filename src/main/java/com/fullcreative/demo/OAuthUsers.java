package com.fullcreative.demo;

import java.io.Serializable;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable
public class OAuthUsers implements Serializable {	
	
	@PrimaryKey
	@Persistent
	private String userId;
	@Persistent
	private String mailID;
	
	public OAuthUsers(String userId, String mailID) {
		super();
		this.userId = userId;
		this.mailID = mailID;
	}

	
	@Override
	public String toString() {
		return "RefToken [user=" + userId + ", Token=" + mailID + "]";
	}

}
