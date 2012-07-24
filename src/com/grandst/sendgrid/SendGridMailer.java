package com.grandst.sendgrid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.grandst.sendgrid.bean.GridEmail;
import com.grandst.sendgrid.bean.GridResponse;

public final class SendGridMailer {

	public static GridResponse send(GridEmail email, SendGrid sg) throws ClientProtocolException, IOException {
		if (!email.validate() || !sg.validate())
			return new GridResponse(false, "");
		HttpClient client = new DefaultHttpClient();
		try {
			HttpPost post = new HttpPost(SendGrid.SENDGRID_BASE_API_URL + "mail.send.json");
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			
			//TODO: handle if email to is > 1000... batches
			for(int i=0; i<email.getTo().size(); i++) {
				nvps.add(new BasicNameValuePair("to[]",email.getTo().get(i)));
				if (email.getToname() != null && email.getToname().get(i) != null)
					nvps.add(new BasicNameValuePair("toname[]",email.getToname().get(i)));
			}
			nvps.add(new BasicNameValuePair("subject",email.getSubject()));
			if (email.getHtml() != null && !email.getHtml().equals(""))
				nvps.add(new BasicNameValuePair("html",email.getHtml()));
			if (email.getText() != null && !email.getText().equals(""))
				nvps.add(new BasicNameValuePair("text",email.getText()));
			nvps.add(new BasicNameValuePair("from",email.getFrom()));
			
			if (email.getBcc() != null && !email.getBcc().equals(""))
				nvps.add(new BasicNameValuePair("bcc",email.getBcc()));
			if (email.getFromname() != null && !email.getFromname().equals(""))
				nvps.add(new BasicNameValuePair("fromname",email.getFromname()));
			if (email.getReplyto() != null && !email.getReplyto().equals(""))
				nvps.add(new BasicNameValuePair("replyto",email.getReplyto()));
			
			nvps.add(new BasicNameValuePair("api_user", sg.getApiUser()));
			nvps.add(new BasicNameValuePair("api_key", sg.getApiKey()));
			
			
			post.setEntity(new UrlEncodedFormEntity(nvps));
			HttpResponse resp = client.execute(post);			
			String respTxt = EntityUtils.toString(resp.getEntity());
			Gson gson = new Gson();
			return gson.fromJson(respTxt, GridResponse.class);
		} finally {
			client.getConnectionManager().shutdown();
		}
	}
	
}
