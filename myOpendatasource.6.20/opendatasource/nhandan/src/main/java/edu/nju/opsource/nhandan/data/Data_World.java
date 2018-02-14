
package edu.nju.opsource.nhandan.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import edu.nju.opensource.common.beans.Data;

public class Data_World extends Data{
	@SerializedName("abstract_s")
	   private String Abstract;
	 
	   @SerializedName("imgUri_S")
	   private String imgUri;
	   
	   
	   private String contentWithTag;
	   
	   
	   
	public String getAbstract() {
		return Abstract;
	}



	public void setAbstract(String abstract1) {
		Abstract = abstract1;
	}



	public String getImgUri() {
		return imgUri;
	}



	public void setImgUri(String imgUri) {
		this.imgUri = imgUri;
	}



	public String getContentWithTag() {
		return contentWithTag;
	}



	public void setContentWithTag(String contentWithTag) {
		this.contentWithTag = contentWithTag;
	}



	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		//return null;
		Gson gson=new Gson();
		return gson.toJson(this);
	}
 
}

