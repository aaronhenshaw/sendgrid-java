package com.grandst.sendgrid.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class GridEmail {

	private ArrayList<String> to; //required, 1000 batch
	private ArrayList<String> toname;
	private String subject; //required
	private String html; //required (one or both of html & text)
	private String text; //required (one or both of html & text)
	private String from; //required
	private String bcc;
	private String fromname;
	private String replyto;
	private Date date; //hmmm
    private ArrayList<Attachment> attachments;

	
	//TODO: headers later. not vital.
	private HashMap<String, String> headers;
	
	//TODO: implement files and x-smtpapi params
	//private Object files; //not implementing
	//private String xsmtpapi; //not implementing
	
	public GridEmail() {
		super();
	}
	
	public GridEmail(ArrayList<String> to, ArrayList<String> toname,
			String subject, String html, String text, String from, String bcc,
			String fromname, String replyto, Date date,
			HashMap<String, String> headers) {
		super();
		this.to = to;
		this.toname = toname;
		this.subject = subject;
		this.html = html;
		this.text = text;
		this.from = from;
		this.bcc = bcc;
		this.fromname = fromname;
		this.replyto = replyto;
		this.date = date;
		this.headers = headers;
	}
	
	public boolean validate() {
		if (to == null || to.size() == 0)
			return false;
		else if (toname != null && toname.size() != 0 && toname.size() != to.size()) //to and toname need to be equal
			return false;
		else if (subject == null || subject.trim().equals(""))
			return false;
		else if ((html == null || html.equals("")) && (text == null || text.trim().equals("")))
			return false;
		else if (from == null && from.trim().equals(""))
			return false;
		return true;
	}
	
	public ArrayList<String> getTo() {
		return to;
	}
	public void setTo(ArrayList<String> to) {
		this.to = to;
	}
	public ArrayList<String> getToname() {
		return toname;
	}
	public void setToname(ArrayList<String> toname) {
		this.toname = toname;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getBcc() {
		return bcc;
	}
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}
	public String getFromname() {
		return fromname;
	}
	public void setFromname(String fromname) {
		this.fromname = fromname;
	}
	public String getReplyto() {
		return replyto;
	}
	public void setReplyto(String replyto) {
		this.replyto = replyto;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public HashMap<String, String> getHeaders() {
		return headers;
	}
	public void setHeaders(HashMap<String, String> headers) {
		this.headers = headers;
	}

    public ArrayList<Attachment> getAttachments() {
        return this.attachments;
    }

    public void addFile(Attachment attachment) {
        this.addAttachment(attachment);
    }

    public void addFile(File file) throws FileNotFoundException {
        this.addAttachment(file);
    }

    public void addAttachment(Attachment attachment) {
        this.attachments.add(attachment);
    }

    public void addAttachment(File file) throws FileNotFoundException {
        Attachment attachment = new Attachment(file);
        this.addAttachment(attachment);
    }

    public static class Attachment {
        public final String name;
        public final InputStream contents;

        public Attachment(File file) throws FileNotFoundException {
            this.name = file.getName();
            this.contents = new FileInputStream(file);
        }

        public Attachment(String name, InputStream contents) {
            this.name = name;
            this.contents = contents;
        }
    }

}
