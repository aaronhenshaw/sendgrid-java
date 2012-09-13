package com.grandst.sendgrid.bean;

public class Newsletter {

	public Newsletter(String name, String identity, String subject,
			String text, String html) {
		super();
		this.name = name;
		this.identity = identity;
		this.subject = subject;
		this.text = text;
		this.html = html;
	}

	private String name;
	private String identity;
	private String subject;
	private String text;
	private String html;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	@Override
	public String toString() {
		return "Newsletter [name=" + name + ", identity=" + identity
				+ ", subject=" + subject + ", text=" + text + ", html=" + html
				+ "]";
	}
	
	
	
}
