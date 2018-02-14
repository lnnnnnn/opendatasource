/*    */ package edu.nju.opsource.philstar.data;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ import edu.nju.opensource.common.beans.Data;
/*    */ 
/*    */ public class Data_Nation extends Data
/*    */ {
/*    */ 
/*    */   @SerializedName("author_s")
/*    */   private String author;
/*    */   private String contentWithTag;
/*    */ 
/*    */   public String getAuthor()
/*    */   {
/* 17 */     return this.author;
/*    */   }
/*    */   public void setAuthor(String author) {
/* 20 */     this.author = author;
/*    */   }
/*    */ 
/*    */   public String getContentWithTag() {
/* 24 */     return this.contentWithTag;
/*    */   }
/*    */   public void setContentWithTag(String contentWithTag) {
/* 27 */     this.contentWithTag = contentWithTag;
/*    */   }
/*    */ 
/*    */   public String toJSON()
/*    */   {
/* 32 */     Gson gson = new Gson();
/* 33 */     return gson.toJson(this);
/*    */   }
/*    */ }

/* Location:           C:\workspace\opendatasource\philstar\target\philstar-0.0.1-SNAPSHOT.jar
 * Qualified Name:     edu.nju.opsource.philstar.data.Data_Nation
 * JD-Core Version:    0.6.2
 */