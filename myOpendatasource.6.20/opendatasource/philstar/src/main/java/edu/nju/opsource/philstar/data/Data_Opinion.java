/*    */ package edu.nju.opsource.philstar.data;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ import edu.nju.opensource.common.beans.Data;
/*    */ 
/*    */ public class Data_Opinion extends Data
/*    */ {
/*    */ 
/*    */   @SerializedName("author_s")
/*    */   private String author;
/*    */ 
/*    */   @SerializedName("imgUri_S")
/*    */   private String imgUri;
/*    */   private String contentWithTag;
/*    */ 
/*    */   public String getAuthor()
/*    */   {
/* 18 */     return this.author;
/*    */   }
/*    */   public void setAuthor(String author) {
/* 21 */     this.author = author;
/*    */   }
/*    */ 
/*    */   public String getContentWithTag() {
/* 25 */     return this.contentWithTag;
/*    */   }
/*    */   public void setContentWithTag(String contentWithTag) {
/* 28 */     this.contentWithTag = contentWithTag;
/*    */   }
/*    */ 
/*    */   public String getImgUri() {
/* 32 */     return this.imgUri;
/*    */   }
/*    */   public void setImgUri(String imgUri) {
/* 35 */     this.imgUri = imgUri;
/*    */   }
/*    */ 
/*    */   public String toJSON()
/*    */   {
/* 40 */     Gson gson = new Gson();
/* 41 */     return gson.toJson(this);
/*    */   }
@Override
public String toString() {
	return "Data_Opinion [author=" + author + ", imgUri=" + imgUri + ", contentWithTag=" + contentWithTag + "]";
}

/*    */ }

/* Location:           C:\workspace\opendatasource\philstar\target\philstar-0.0.1-SNAPSHOT.jar
 * Qualified Name:     edu.nju.opsource.philstar.data.Data_Opinion
 * JD-Core Version:    0.6.2
 */