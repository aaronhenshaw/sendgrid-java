package com.grandst.sendgrid.newsletter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grandst.sendgrid.SendGrid;
import com.grandst.sendgrid.bean.GridResponse;
import com.grandst.sendgrid.bean.Newsletter;
import com.grandst.sendgrid.bean.NewsletterIdentity;
import com.grandst.sendgrid.bean.NewsletterList;

public final class NewsletterManager {

	
	public static boolean createNewsletter(Newsletter n, SendGrid sg) throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		try {
			HttpPost post = new HttpPost(SendGrid.SENDGRID_BASE_API_URL + "newsletter/add.json");
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();			
			nvps.add(new BasicNameValuePair("identity", n.getIdentity()));
			nvps.add(new BasicNameValuePair("name", n.getName()));
			nvps.add(new BasicNameValuePair("subject", n.getSubject()));
			nvps.add(new BasicNameValuePair("text", n.getText()));
			nvps.add(new BasicNameValuePair("html", n.getHtml()));
			nvps.add(new BasicNameValuePair("api_user", sg.getApiUser()));
			nvps.add(new BasicNameValuePair("api_key", sg.getApiKey()));
			post.setEntity(new UrlEncodedFormEntity(nvps));
			HttpResponse resp = client.execute(post);		
			String respTxt = EntityUtils.toString(resp.getEntity());
			Gson g = new Gson();
			GridResponse gr = g.fromJson(respTxt, GridResponse.class);
			if (gr.getMessage() != null && !gr.getMessage().equals("") && gr.getMessage().equals("success"))
				return true; //nailed it
			return false;
		} finally {
			client.getConnectionManager().shutdown();
		}
	}
	
	public static boolean editNewsletter(Newsletter n, SendGrid sg) throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		try {
			HttpPost post = new HttpPost(SendGrid.SENDGRID_BASE_API_URL + "newsletter/edit.json");
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();			
			nvps.add(new BasicNameValuePair("identity", n.getIdentity()));
			nvps.add(new BasicNameValuePair("name", n.getName()));
			nvps.add(new BasicNameValuePair("subject", n.getSubject()));
			nvps.add(new BasicNameValuePair("text", n.getText()));
			nvps.add(new BasicNameValuePair("html", n.getHtml()));
			nvps.add(new BasicNameValuePair("api_user", sg.getApiUser()));
			nvps.add(new BasicNameValuePair("api_key", sg.getApiKey()));
			post.setEntity(new UrlEncodedFormEntity(nvps));
			HttpResponse resp = client.execute(post);		
			String respTxt = EntityUtils.toString(resp.getEntity());
			Gson g = new Gson();
			GridResponse gr = g.fromJson(respTxt, GridResponse.class);
			if (gr.getMessage() != null && !gr.getMessage().equals("") && gr.getMessage().equals("success"))
				return true; //nailed it
			return false;
		} finally {
			client.getConnectionManager().shutdown();
		}
	}
	
	public static ArrayList<Newsletter> getNewsletters(SendGrid sg) throws ParseException, IOException {
		HttpClient client = new DefaultHttpClient();
		try {
			String url = SendGrid.SENDGRID_BASE_API_URL + "newsletter/list.json?api_user=" + sg.getApiUser() + "&api_key=" + sg.getApiKey();
			HttpGet get = new HttpGet(url);
			HttpResponse resp = client.execute(get);
			String respTxt = EntityUtils.toString(resp.getEntity());
			Gson gson = new Gson();
			Type collectionType = new TypeToken<ArrayList<Newsletter>>(){}.getType();
			return gson.fromJson(respTxt, collectionType);
		} finally {
			client.getConnectionManager().shutdown();
		}
	}
	
	public static Newsletter getNewsletter(String newsletterName, SendGrid sg) throws ParseException, IOException {
		HttpClient client = new DefaultHttpClient();
		try {
			String url = SendGrid.SENDGRID_BASE_API_URL + "newsletter/get.json?name=" + URLEncoder.encode(newsletterName, "UTF-8") + "&api_user=" + sg.getApiUser() + "&api_key=" + sg.getApiKey();
			HttpGet get = new HttpGet(url);
			HttpResponse resp = client.execute(get);
			String respTxt = EntityUtils.toString(resp.getEntity());
			Gson gson = new Gson();
			return gson.fromJson(respTxt, Newsletter.class);
		} finally {
			client.getConnectionManager().shutdown();
		}
	}
	
	public static boolean deleteNewsletter(String newsletterName, SendGrid sg) throws ClientProtocolException, IOException{
		HttpClient client = new DefaultHttpClient();
		try {
			HttpPost post = new HttpPost(SendGrid.SENDGRID_BASE_API_URL + "newsletter/delete.json");
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();			
			nvps.add(new BasicNameValuePair("name", newsletterName));
			nvps.add(new BasicNameValuePair("api_user", sg.getApiUser()));
			nvps.add(new BasicNameValuePair("api_key", sg.getApiKey()));
			post.setEntity(new UrlEncodedFormEntity(nvps));
			HttpResponse resp = client.execute(post);		
			String respTxt = EntityUtils.toString(resp.getEntity());
			Gson g = new Gson();
			GridResponse gr = g.fromJson(respTxt, GridResponse.class);
			if (gr.getMessage() != null && !gr.getMessage().equals("") && gr.getMessage().equals("success"))
				return true; //nailed it
			return false;
		} finally {
			client.getConnectionManager().shutdown();
		}
	}
	
	public static boolean addListToNewsletter(NewsletterList nl, Newsletter n, SendGrid sg) throws ParseException, IOException {
		HttpClient client = new DefaultHttpClient();
		try {
			HttpPost post = new HttpPost(SendGrid.SENDGRID_BASE_API_URL + "newsletter/recipients/add.json");
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();			
			nvps.add(new BasicNameValuePair("name", n.getName()));
			nvps.add(new BasicNameValuePair("list", nl.getList()));
			nvps.add(new BasicNameValuePair("api_user", sg.getApiUser()));
			nvps.add(new BasicNameValuePair("api_key", sg.getApiKey()));
			post.setEntity(new UrlEncodedFormEntity(nvps));
			HttpResponse resp = client.execute(post);		
			String respTxt = EntityUtils.toString(resp.getEntity());
			Gson g = new Gson();
			GridResponse gr = g.fromJson(respTxt, GridResponse.class);
			if (gr.getMessage() != null && !gr.getMessage().equals("") && gr.getMessage().equals("success"))
				return true; //nailed it
			return false;
		} finally {
			client.getConnectionManager().shutdown();
		}
	}
	
	public static boolean scheduleNewsletter(Newsletter n, Integer minutesTillDelivery, SendGrid sg) throws ParseException, IOException {
		HttpClient client = new DefaultHttpClient();
		try {
			HttpPost post = new HttpPost(SendGrid.SENDGRID_BASE_API_URL + "newsletter/schedule/add.json");
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();			
			nvps.add(new BasicNameValuePair("name", n.getName()));
			if (minutesTillDelivery != null)
				nvps.add(new BasicNameValuePair("after", minutesTillDelivery.toString()));
			else
				nvps.add(new BasicNameValuePair("after", "0"));
			nvps.add(new BasicNameValuePair("api_user", sg.getApiUser()));
			nvps.add(new BasicNameValuePair("api_key", sg.getApiKey()));
			post.setEntity(new UrlEncodedFormEntity(nvps));
			HttpResponse resp = client.execute(post);		
			String respTxt = EntityUtils.toString(resp.getEntity());
			Gson g = new Gson();
			GridResponse gr = g.fromJson(respTxt, GridResponse.class);
			if (gr.getMessage() != null && !gr.getMessage().equals("") && gr.getMessage().equals("success"))
				return true; //nailed it
			return false;
		} finally {
			client.getConnectionManager().shutdown();
		}
	}
	
	/*
	############
	#### NEWSLETTER IDENTITY FUNCTIONS
	############
	*/
	
	public static ArrayList<NewsletterIdentity> getIdentities(SendGrid sg) throws ParseException, IOException {
		HttpClient client = new DefaultHttpClient();
		try {
			String url = SendGrid.SENDGRID_BASE_API_URL + "newsletter/identity/list.json?api_user=" + sg.getApiUser() + "&api_key=" + sg.getApiKey();
			HttpGet get = new HttpGet(url);
			HttpResponse resp = client.execute(get);
			String respTxt = EntityUtils.toString(resp.getEntity());
			Gson gson = new Gson();
			Type collectionType = new TypeToken<ArrayList<NewsletterIdentity>>(){}.getType();
			return gson.fromJson(respTxt, collectionType);
		} finally {
			client.getConnectionManager().shutdown();
		}
	}
	
	public static NewsletterIdentity getIdentity(String identity, SendGrid sg) throws ParseException, IOException {
		HttpClient client = new DefaultHttpClient();
		try {
			String url = SendGrid.SENDGRID_BASE_API_URL + "newsletter/identity/get.json?identity=" + identity + "&api_user=" + sg.getApiUser() + "&api_key=" + sg.getApiKey();
			HttpGet get = new HttpGet(url);
			HttpResponse resp = client.execute(get);
			String respTxt = EntityUtils.toString(resp.getEntity());
			Gson gson = new Gson();
			return gson.fromJson(respTxt, NewsletterIdentity.class);
		} finally {
			client.getConnectionManager().shutdown();
		}
	}
	
	public static boolean deleteIdentity(String identity, SendGrid sg) throws ClientProtocolException, IOException{
		HttpClient client = new DefaultHttpClient();
		try {
			HttpPost post = new HttpPost(SendGrid.SENDGRID_BASE_API_URL + "newsletter/identity/delete.json");
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();			
			nvps.add(new BasicNameValuePair("identity", identity));
			nvps.add(new BasicNameValuePair("api_user", sg.getApiUser()));
			nvps.add(new BasicNameValuePair("api_key", sg.getApiKey()));
			post.setEntity(new UrlEncodedFormEntity(nvps));
			HttpResponse resp = client.execute(post);		
			String respTxt = EntityUtils.toString(resp.getEntity());
			Gson g = new Gson();
			GridResponse gr = g.fromJson(respTxt, GridResponse.class);
			if (gr.getMessage() != null && !gr.getMessage().equals("") && gr.getMessage().equals("success"))
				return true; //nailed it
			return false;
		} finally {
			client.getConnectionManager().shutdown();
		}
	}
	
	public static boolean createIdentity(NewsletterIdentity ni, SendGrid sg) throws ParseException, IOException {
		HttpClient client = new DefaultHttpClient();
		try {
			HttpPost post = new HttpPost(SendGrid.SENDGRID_BASE_API_URL + "newsletter/identity/add.json");
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();			
			nvps.add(new BasicNameValuePair("identity", ni.getIdentity()));
			nvps.add(new BasicNameValuePair("name", ni.getName()));
			nvps.add(new BasicNameValuePair("email", ni.getEmail()));
			if (ni.getReplyto() != null)
				nvps.add(new BasicNameValuePair("replyto", ni.getReplyto()));
			nvps.add(new BasicNameValuePair("address", ni.getAddress()));
			nvps.add(new BasicNameValuePair("city", ni.getCity()));
			nvps.add(new BasicNameValuePair("state", ni.getState()));
			nvps.add(new BasicNameValuePair("zip", ni.getZip()));
			nvps.add(new BasicNameValuePair("country", ni.getCountry()));
			nvps.add(new BasicNameValuePair("api_user", sg.getApiUser()));
			nvps.add(new BasicNameValuePair("api_key", sg.getApiKey()));
			post.setEntity(new UrlEncodedFormEntity(nvps));
			HttpResponse resp = client.execute(post);		
			String respTxt = EntityUtils.toString(resp.getEntity());
			Gson g = new Gson();
			GridResponse gr = g.fromJson(respTxt, GridResponse.class);
			if (gr.getMessage() != null && !gr.getMessage().equals("") && gr.getMessage().equals("success"))
				return true; //nailed it
			return false;
		} finally {
			client.getConnectionManager().shutdown();
		}
	}
	
	public static boolean editEntity(NewsletterIdentity ni, SendGrid sg) throws ParseException, IOException {
		HttpClient client = new DefaultHttpClient();
		try {
			HttpPost post = new HttpPost(SendGrid.SENDGRID_BASE_API_URL + "newsletter/identity/edit.json");
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();			
			nvps.add(new BasicNameValuePair("identity", ni.getIdentity()));
			if (ni.getName() != null)
				nvps.add(new BasicNameValuePair("name", ni.getName()));			
			nvps.add(new BasicNameValuePair("email", ni.getEmail()));
			if (ni.getReplyto() != null)
				nvps.add(new BasicNameValuePair("replyto", ni.getReplyto()));
			if (ni.getAddress() != null)
				nvps.add(new BasicNameValuePair("address", ni.getAddress()));
			if (ni.getCity() != null)
				nvps.add(new BasicNameValuePair("city", ni.getCity()));
			if (ni.getState() != null)
				nvps.add(new BasicNameValuePair("state", ni.getState()));
			if (ni.getZip() != null)
				nvps.add(new BasicNameValuePair("zip", ni.getZip()));
			if (ni.getCountry() != null)
				nvps.add(new BasicNameValuePair("country", ni.getCountry()));
			nvps.add(new BasicNameValuePair("api_user", sg.getApiUser()));
			nvps.add(new BasicNameValuePair("api_key", sg.getApiKey()));
			post.setEntity(new UrlEncodedFormEntity(nvps));
			HttpResponse resp = client.execute(post);		
			String respTxt = EntityUtils.toString(resp.getEntity());
			Gson g = new Gson();
			GridResponse gr = g.fromJson(respTxt, GridResponse.class);
			if (gr.getMessage() != null && !gr.getMessage().equals("") && gr.getMessage().equals("success"))
				return true; //nailed it
			return false;
		} finally {
			client.getConnectionManager().shutdown();
		}
	}
	
	public static void main(String[] args) {
		/* some test scripts
		String sendGridUser = "user";
		String sendGridKey = "key";

		SendGrid sg = new SendGrid(sendGridUser, sendGridKey);
		
		try {
			System.out.println("Getting Identities ---");
			NewsletterIdentity first = null;
			for(NewsletterIdentity ni : getIdentities(sg)) {				
				if (first == null)
					first = getIdentity(ni.getIdentity(), sg);
			}
			if (first != null) {
				System.out.println("Got an identity...");
				System.out.println(first);
				
				//create a newsletter plz
				
				createNewsletter(new Newsletter(
					"First Newsletter"
					, first.getIdentity()
					, "This is the email Subject"
					, "Plain Body!"
					, "<em>Haha!!<em> <strong>Htmeezy Beezyy</strong>"					
				), sg);
				System.out.println(getNewsletters(sg));
				Newsletter nl = null;
				for(Newsletter n : getNewsletters(sg)) {
					if (nl == null)
					nl = getNewsletter(n.getName(), sg);
				}
				
				addListToNewsletter(new NewsletterList("testing"), nl, sg);
				System.out.println("Sending newsletter... " + nl.getName());
				scheduleNewsletter(nl, 0, sg);
				
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
				*/
	}
	
}
