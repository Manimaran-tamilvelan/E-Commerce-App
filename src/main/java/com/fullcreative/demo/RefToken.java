package com.fullcreative.demo;

import java.io.Serializable;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable
public class RefToken implements Serializable {	
	
	@PrimaryKey
	@Persistent
	private String user;
	@Persistent
	private String token;
	
	public RefToken(String user, String token) {
		super();
		this.user = user;
		this.token = token;
	}

	
	@Override
	public String toString() {
		return "RefToken [user=" + user + ", Token=" + token + "]";
	}

}
