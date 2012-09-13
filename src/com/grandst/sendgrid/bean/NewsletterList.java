package com.grandst.sendgrid.bean;

public class NewsletterList {

	public NewsletterList(String list) {
		super();
		this.list = list;
	}
	
	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}

	private String list;

	@Override
	public String toString() {
		return "NewsletterList [list=" + list + "]";
	}
	
}
