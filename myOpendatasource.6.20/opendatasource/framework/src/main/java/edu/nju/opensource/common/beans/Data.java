package edu.nju.opensource.common.beans;

import java.util.Date;

public abstract class Data
{
	private String id;
	private String title;
	private Date date;
	private String content;
	private String url;
	private String siteUUID;
	private int lingual;
	public static final String NULLDATAFIELD = "NULLDATAFIELD";

	public abstract String toJSON();
	public String getTitle()
	{
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDate(){
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
			
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getSiteUUID() {
		return siteUUID;
	}
			
	public void setSiteUUID(String siteUUID) {
		this.siteUUID = siteUUID;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getLingual() {
		return lingual;
	}
	public void setLingual(int lingual){
		this.lingual = lingual;
	}
			
}

