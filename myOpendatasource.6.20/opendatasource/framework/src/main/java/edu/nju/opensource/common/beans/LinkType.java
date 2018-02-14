/*    */ package edu.nju.opensource.common.beans;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.PrintStream;
/*    */ import java.io.Serializable;
/*    */ import java.util.Date;
/*    */ 
/*    */ public abstract class LinkType
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 8979412271388314850L;
/*    */   private String linkTypeName;
/*    */   private String linkId;
/*    */   private ELinkState state;
/*    */ 
/*    */   public void get(String url)
/*    */   {
/* 16 */     System.out.print("[INFO] Now Crawling: " + url); 
		   } 
/*    */   public abstract boolean handle(Link link) throws Exception;
/*    */ 
/*    */   public abstract String getLinkTextData();
/*    */ 
/*    */   public abstract InputStream getLinkBinaryData();
/*    */ 
/*    */   public abstract String getLinkUniqeID(String paramString);
/*    */ 
/*    */   public abstract Date getLinkDate();
/*    */ 
/*    */   public abstract void setSite(Site paramSite);
/*    */ 
/* 26 */   public String getLinkTypeName() { return this.linkTypeName; }
/*    */ 
/*    */   public void setLinkTypeName(String linkTypeName) {
/* 29 */     this.linkTypeName = linkTypeName;
/*    */   }
/*    */   public String getLinkId() {
/* 32 */     return this.linkId;
/*    */   }
/*    */   public void setLinkId(String linkId) {
/* 35 */     this.linkId = linkId;
/*    */   }
/*    */   public ELinkState getState() {
/* 38 */     return this.state;
/*    */   }
/*    */   public void setState(ELinkState state) {
/* 41 */     this.state = state;
/*    */   }
/*    */ }

/* Location:           C:\workspace\opendatasource\philstar\target\philstar-0.0.1-SNAPSHOT.jar
 * Qualified Name:     edu.nju.opensource.common.beans.LinkType
 * JD-Core Version:    0.6.2
 */