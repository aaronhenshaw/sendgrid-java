package com.grandst.sendgrid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class SendGrid {

	public static final String SENDGRID_URL = "https://sendgrid.com";
	public static final String SENDGRID_BASE_API_URL = SENDGRID_URL + "/api/";
	
	//lets have this object hold things like the auth.
	private String apiUser;
	private String apiKey;
	
	public SendGrid(String apiUser, String apiKey) {
		super();
		this.apiUser = apiUser;
		this.apiKey = apiKey;
	}
	
	public String getApiUser() {
		return apiUser;
	}
	
	public void setApiUser(String apiUser) {
		this.apiUser = apiUser;
	}
	
	public String getApiKey() {
		return apiKey;
	}
	
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}	
	
	public boolean validate() {
		return !(apiUser == null || apiKey == null || apiUser.equals("") || apiKey.equals(""));
	}
	
	public void unsubscribe(String email) throws ClientProtocolException, IOException {
		if (!this.validate())
			return;
		HttpClient client = new DefaultHttpClient();
		try {
			HttpPost post = new HttpPost(SendGrid.SENDGRID_BASE_API_URL + "unsubscribes.add.json");
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("email", email));
			post.setEntity(new UrlEncodedFormEntity(nvps));
			client.execute(post);
		} finally {
			client.getConnectionManager().shutdown();
		}
	}
	
}
