package edu.nju.opsource.dangcongsan.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import edu.nju.opensource.common.beans.Data;

public class Data_News extends Data{
	@SerializedName("abstract_s")
	private String Abstract;
	private String contentWithTag;
	public String getContentWithTag() {
		return contentWithTag;
	}

	public void setContentWithTag(String contentWithTag) {
		this.contentWithTag = contentWithTag;
	}
    
	public String getAbstract() {
		return Abstract;
	}

	public void setAbstract(String abstract1) {
		Abstract = abstract1;
	}

	public String toJSON() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
