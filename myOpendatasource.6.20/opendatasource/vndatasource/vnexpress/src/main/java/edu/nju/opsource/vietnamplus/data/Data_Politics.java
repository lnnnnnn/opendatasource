package edu.nju.opsource.vietnamplus.data;

import com.google.gson.Gson;

import edu.nju.opensource.common.beans.Data;

public class Data_Politics extends Data{
	private String contentWithTag;
	public String getContentWithTag() {
		return contentWithTag;
	}

	public void setContentWithTag(String contentWithTag) {
		this.contentWithTag = contentWithTag;
	}

	public String toJSON() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
