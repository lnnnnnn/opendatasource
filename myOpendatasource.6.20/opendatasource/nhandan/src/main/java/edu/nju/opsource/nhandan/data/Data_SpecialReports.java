package edu.nju.opsource.nhandan.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import edu.nju.opensource.common.beans.Data;

public class Data_SpecialReports extends Data{

	 @SerializedName("author_s")
	 /*    */   private String author;
	 /*    */ 
	 /*    */   @SerializedName("abstract_s")
	 /*    */   private String Abstract;
	 /*    */ 
	 /*    */   @SerializedName("imgUri_S")
	 /*    */   private String imgUri;
	 /*    */   private String contentWithTag;
	 /*    */ 
	 /*    */   public String getAuthor()
	 /*    */   {
	 /* 20 */     return this.author;
	 /*    */   }
	 /*    */   public void setAuthor(String author) {
	 /* 23 */     this.author = author;
	 /*    */   }
	 /*    */ 
	 /*    */   public String getAbstract() {
	 /* 27 */     return this.Abstract;
	 /*    */   }
	 /*    */   public void setAbstract(String abstract1) {
	 /* 30 */     this.Abstract = abstract1;
	 /*    */   }
	 /*    */   public String getContentWithTag() {
	 /* 33 */     return this.contentWithTag;
	 /*    */   }
	 /*    */   public void setContentWithTag(String contentWithTag) {
	 /* 36 */     this.contentWithTag = contentWithTag;
	 /*    */   }
	 /*    */ 
	 /*    */   public String getImgUri() {
	 /* 40 */     return this.imgUri;
	 /*    */   }
	 /*    */   public void setImgUri(String imgUri) {
	 /* 43 */     this.imgUri = imgUri;
	 /*    */   }
	 /*    */ 
	 /*    */   public String toJSON() {
	 /* 47 */     Gson gson = new Gson();
	 /* 48 */     return gson.toJson(this);
	 /*    */   }

}
