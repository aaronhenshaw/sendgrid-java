package com.grandst.sendgrid.bean;

public class NewsletterIdentity {

	public NewsletterIdentity(String identity, String name, String email,
			String address, String city, String state, String zip,
			String country) {
		super();
		this.identity = identity;
		this.name = name;
		this.email = email;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
	}
	
	public NewsletterIdentity(String identity, String name, String email,
			String replyto, String address, String city, String state,
			String zip, String country) {
		super();
		this.identity = identity;
		this.name = name;
		this.email = email;
		this.replyto = replyto;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
	}
	
	private String identity;
	private String name;
	private String email;
	private String replyto;
	private String address;
	private String city;
	private String state;
	private String zip;
	private String country;
	
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getReplyto() {
		return replyto;
	}
	public void setReplyto(String replyto) {
		this.replyto = replyto;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "NewsletterIdentity [Identity=" + identity + ", name=" + name
				+ ", email=" + email + ", replyto=" + replyto + ", address="
				+ address + ", city=" + city + ", state=" + state + ", zip="
				+ zip + ", country=" + country + "]";
	}
		
}
