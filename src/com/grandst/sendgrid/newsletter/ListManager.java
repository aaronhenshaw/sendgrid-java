package com.grandst.sendgrid.newsletter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.grandst.sendgrid.SendGrid;
import com.grandst.sendgrid.bean.GridEmailAddress;

public final class ListManager {

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
			client.execute(post);
			//HttpResponse resp = client.execute(post);			
			//String respTxt = EntityUtils.toString(resp.getEntity());
			//System.out.println(respTxt);
			//meh do this later
		} finally {
			client.getConnectionManager().shutdown();
		}
		
		return true;
	}
	
}
