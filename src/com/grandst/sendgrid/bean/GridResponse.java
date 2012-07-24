package com.grandst.sendgrid.bean;

import java.util.ArrayList;

public class GridResponse {

	private boolean attempted;
	
	private String message;
	private ArrayList<String> errors;
	
	public GridResponse(boolean attempted, String message) {
		super();
		this.attempted = attempted;
		this.message = message;
	}

	public GridResponse(boolean attempted, String message, ArrayList<String> errors) {
		super();
		this.message = message;
		this.errors = errors;
		this.attempted = attempted;
	}
	
	public boolean isSuccess() {
		return message.equals("success");
	}
	
	public ArrayList<String> getErrors() {
		return errors;
	}
	
	public void setErrors(ArrayList<String> errors) {
		this.errors = errors;
	}
	
	public void addError(String error) {
		if (this.errors == null)
			this.errors = new ArrayList<String>();
		this.errors.add(error);
	}

	public boolean isAttempted() {
		return attempted;
	}

	public void setAttempted(boolean attempted) {
		this.attempted = attempted;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
