package com.onezetta.solr;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Data {
	private String id;
	private String author;
	private String content;
	private String title;
	
	private Date date;
	private String url;
	private String siteUUID;
	
	public Data convertToSolrData(JsonObject jsonObj){
		Data data = new Data();
		data.setAuthor( jsonObj.get("author_s") == null ? null : jsonObj.get("author_s").getAsString() );
		data.setContent( jsonObj.get("content")  == null ? null : jsonObj.get("content").getAsString());
		data.setTitle( jsonObj.get("title")  == null ? null : jsonObj.get("title").getAsString());
		data.setUrl( jsonObj.get("url")  == null ? null : jsonObj.get("url").getAsString());
		data.setSiteUUID(jsonObj.get("siteUUID")  == null ? null : jsonObj.get("siteUUID").getAsString());
		data.setId(jsonObj.get("id")  == null ? null : jsonObj.get("id").getAsString());
		
		
		String dateStr = (jsonObj.get("date")  == null ? null : jsonObj.get("date").getAsString());
		
		if(dateStr == null) data.setDate(null);
		else{
			
			SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy hh:mm:ss a", Locale.US);
			try {
				data.setDate( format.parse(dateStr) );
			}catch(Exception e){
				e.printStackTrace();
				data.setDate( null );
			}
		}		
		
		return data;
	}
	
	public JsonObject convertToSolrDataJsonObj(String jsonStr){
		JsonObject obj = new JsonParser().parse( jsonStr ).getAsJsonObject();
		Data newData = convertToSolrData( obj);;
		obj = new JsonParser().parse( new Gson().toJson(newData) ).getAsJsonObject();
		if( obj.get("date") != null){
			obj.addProperty("date", newData.getDateStr());
			return obj;
		}
		return null; 
	}
	
	public String getDateStr(){
		
		if( this.date != null){
			String t = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.US).format(this.date);
			return t;
		}

		return "";
	}
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	
	
}
