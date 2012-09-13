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
import com.grandst.sendgrid.bean.GridEmailAddress;
import com.grandst.sendgrid.bean.GridResponse;
import com.grandst.sendgrid.bean.NewsletterList;

public final class ListManager {
	
	public static boolean createList(NewsletterList nl, SendGrid sg) throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		try {
			HttpPost post = new HttpPost(SendGrid.SENDGRID_BASE_API_URL + "newsletter/lists/add.json");
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();			
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
	
	public static boolean deleteList(NewsletterList nl, SendGrid sg) throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		try {
			HttpPost post = new HttpPost(SendGrid.SENDGRID_BASE_API_URL + "newsletter/lists/delete.json");
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();			
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
	
	
	
	public static boolean addRecipient(GridEmailAddress ga, NewsletterList nl, SendGrid sg) throws ParseException, IOException {
		return addRecipient(ga, nl.getList(), sg);
	}
	
	
	public static boolean addRecipient(GridEmailAddress ga, String listName, SendGrid sg) throws ParseException, IOException {
		if (!sg.validate())
			return false;
		
		HttpClient client = new DefaultHttpClient();
		try {
			HttpPost post = new HttpPost(SendGrid.SENDGRID_BASE_API_URL + "newsletter/lists/email/add.json");
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();			
			nvps.add(new BasicNameValuePair("list", listName));
			Gson gs = new Gson();
			nvps.add(new BasicNameValuePair("data", gs.toJson(ga)));
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
	
	public static boolean addRecipients(ArrayList<GridEmailAddress> gas, NewsletterList nl, SendGrid sg) throws ParseException, IOException {
		return addRecipients(gas, nl.getList(), sg);
	}
	
	public static boolean addRecipients(ArrayList<GridEmailAddress> gas, String listName, SendGrid sg) throws ParseException, IOException {
		if (!sg.validate())
			return false;
		if (gas.size() <= 1000) {
			return addRecipients1k(gas, listName, sg);
		} else {
			boolean worked = false;
			int forCeil = (int)Math.ceil(gas.size()/1000);
			for(int i=0; i<forCeil; i++) {
				//lets just say this works :)
				worked = addRecipients1k(gas.subList(i*1000, ((i+1)*1000)-1), listName, sg);
				if (!worked)
					return worked;
			}
			return worked;
		}
	}
	
	private static boolean addRecipients1k(List<GridEmailAddress> gas, String listName, SendGrid sg) throws ParseException, IOException {		
		HttpClient client = new DefaultHttpClient();
		try {
			HttpPost post = new HttpPost(SendGrid.SENDGRID_BASE_API_URL + "newsletter/lists/email/add.json");
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();			
			nvps.add(new BasicNameValuePair("list", listName));
			Gson gs = new Gson();
			for(GridEmailAddress gea : gas) {
				nvps.add(new BasicNameValuePair("data", gs.toJson(gea)));
			}
			//nvps.add(new BasicNameValuePair("data", json));
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
	
	public static boolean removeRecipient(GridEmailAddress ga, NewsletterList nl, SendGrid sg) throws ParseException, IOException {
		if (!sg.validate())
			return false;
		
		HttpClient client = new DefaultHttpClient();
		try {
			HttpPost post = new HttpPost(SendGrid.SENDGRID_BASE_API_URL + "newsletter/lists/email/delete.json");
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();			
			nvps.add(new BasicNameValuePair("list", nl.getList()));
			nvps.add(new BasicNameValuePair("email", ga.getEmail()));
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
	
	public static ArrayList<NewsletterList> getNewsletterLists(SendGrid sg) throws ParseException, IOException {
		HttpClient client = new DefaultHttpClient();
		try {
			String url = SendGrid.SENDGRID_BASE_API_URL + "newsletter/lists/get.json?api_user=" + sg.getApiUser() + "&api_key=" + sg.getApiKey();
			HttpGet get = new HttpGet(url);
			HttpResponse resp = client.execute(get);
			String respTxt = EntityUtils.toString(resp.getEntity());
			Gson gson = new Gson();
			Type collectionType = new TypeToken<ArrayList<NewsletterList>>(){}.getType();
			return gson.fromJson(respTxt, collectionType);
		} finally {
			client.getConnectionManager().shutdown();
		}
	}
	
	public static ArrayList<GridEmailAddress> getEmailsForNewsletterList(NewsletterList nl, SendGrid sg) throws ParseException, IOException {
		HttpClient client = new DefaultHttpClient();
		try {
			String url = SendGrid.SENDGRID_BASE_API_URL + "newsletter/lists/email/get.json?list=" + URLEncoder.encode(nl.getList(), "UTF-8") + "&api_user=" + sg.getApiUser() + "&api_key=" + sg.getApiKey();
			HttpGet get = new HttpGet(url);
			HttpResponse resp = client.execute(get);
			String respTxt = EntityUtils.toString(resp.getEntity());
			Gson gson = new Gson();
			Type collectionType = new TypeToken<ArrayList<GridEmailAddress>>(){}.getType();
			return gson.fromJson(respTxt, collectionType);
		} finally {
			client.getConnectionManager().shutdown();
		}
	}
	
	/* todoooo
	public static ArrayList<Newsletter> getNewsletters(SendGrid sg) {
		HttpClient client = new DefaultHttpClient();
		ArrayList<Newsletter> newsLetters = new ArrayList<Newsletter>();
		try {
			String url = SendGrid.SENDGRID_BASE_API_URL + "newsletter/list.json?api_user=" + sg.getApiUser() + "&api_key=" + sg.getApiKey();
			HttpGet get = new HttpGet(url);
			HttpResponse resp = client.execute(get);
			String respTxt = EntityUtils.toString(resp.getEntity());
			//Gson gson = new Gson();
			System.out.println(respTxt);
			//wow...
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}
		return newsLetters;
	}
	*/
	
	public static void main(String[] args) {
		/*	some test scripts...	
		String sendGridUser = "user";
		String sendGridKey = "key";
		SendGrid sg = new SendGrid(sendGridUser, sendGridKey);
		System.out.println("Getting Newsletter Lists---");
		try {
			ArrayList<NewsletterList> nls = getNewsletterLists(sg);
			System.out.println("Getting Recipients for Newsletter Lists---");
			for(NewsletterList nl : nls) {
				System.out.println("List: " + nl.getList());
				System.out.println(getEmailsForNewsletterList(nl, sg));
			}
			//System.out.println("Deleting List...");
			//deleteList(new NewsletterList("testing"), sg);
			//System.out.println("Creating New List...");
			//createList(new NewsletterList("testing"), sg);
				
			NewsletterList nl = new NewsletterList("testing");
			ArrayList<GridEmailAddress> newEmails = new ArrayList<GridEmailAddress>();
			//newEmails.add(new GridEmailAddress("email@website.com", "Aaron Henshaw"));
			//addRecipients(newEmails, nl, sg);
			//addRecipient(new GridEmailAddress("email2@website.com", "Grand St Rocks"), nl, sg);
			//System.out.println(getEmailsForNewsletterList(nl, sg));
			 * 
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		*/
	}
	
}
