package com.grandst.sendgrid.bean;

public class GridEmailAddress {

	private String email;
	private String name;
	
	public GridEmailAddress(String email, String name) {
		super();
		this.email = email;
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
