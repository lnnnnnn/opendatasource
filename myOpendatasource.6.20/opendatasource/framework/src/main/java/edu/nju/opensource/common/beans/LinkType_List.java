/*    */ package edu.nju.opensource.common.beans;
/*    */ 
/*    */ import com.onezetta.downloader.EasyHttpDownloader;
/*    */ import java.io.InputStream;
/*    */ import java.util.Date;
/*    */ import java.util.LinkedList;
/*    */ import org.jsoup.Jsoup;
/*    */ import org.jsoup.nodes.Document;
/*    */ 
/*    */ public class LinkType_List extends LinkType
/*    */ {
/* 15 */   private Document doc = null;
/* 16 */   private Site site = null;
/* 17 */   private LinkedList<Link> newItems = null;
/* 18 */   private Link nextPage = null;
/*    */ 
/*    */   public LinkType_List(String linkTypeName) {
/* 21 */     super.setLinkTypeName(linkTypeName);
/* 22 */     setState(ELinkState.UNCRAWL);
/*    */   }
/*    */ 
/*    */   public LinkType_List(String linkTypeName, Site site) {
/* 26 */     super.setLinkTypeName(linkTypeName);
/* 27 */     this.site = site;
/* 28 */     setState(ELinkState.UNCRAWL);
/*    */   }
/*    */ 
/*    */   public void get(String url) {
/* 32 */     super.get(url);
/* 33 */     String content = new EasyHttpDownloader(url).run();
/* 34 */     if (content != null){
/* 35 */       this.doc = Jsoup.parse(content, this.site.getUrl().getUrl());
			   //System.out.println( content + " ... has Crawled.");
			 }
/*    */     else{
/* 37 */       setState(ELinkState.CRAWLFAILED);
			   System.out.println( " ... crawled failed.");
			 }
/*    */   }
/*    */ 
/*    */   public boolean handle(Link link) throws Exception
/*    */   {
/* 43 */     return false;
/*    */   }
/*    */ 
/*    */   public String getLinkTextData()
/*    */   {
/* 49 */     return null;
/*    */   }
/*    */ 
/*    */   public Document getDoc() {
/* 53 */     return this.doc;
/*    */   }
/*    */ 
/*    */   public Site getSite() {
/* 57 */     return this.site;
/*    */   }
/*    */ 
/*    */   public LinkedList<Link> getNewItems() {
/* 61 */     return this.newItems;
/*    */   }
/*    */ 
/*    */   public Link getNextPage() {
/* 65 */     return this.nextPage;
/*    */   }
/*    */ 
/*    */   public void setDoc(Document doc) {
/* 69 */     this.doc = doc;
/*    */   }
/*    */ 
/*    */   public void setNewItems(LinkedList<Link> newItems) {
/* 73 */     this.newItems = newItems;
/*    */   }
/*    */ 
/*    */   public void setNextPage(Link nextPage) {
/* 77 */     this.nextPage = nextPage;
/*    */   }
/*    */ 
/*    */   public InputStream getLinkBinaryData()
/*    */   {
/* 83 */     return null;
/*    */   }
/*    */ 
/*    */   public String getLinkUniqeID(String key)
/*    */   {
/* 89 */     return null;
/*    */   }
/*    */ 
/*    */   public Date getLinkDate()
/*    */   {
/* 95 */     return null;
/*    */   }
/*    */ 
/*    */   public void setSite(Site site) {
/* 99 */     this.site = site;
/*    */   }
/*    */ }

/* Location:           C:\workspace\opendatasource\philstar\target\philstar-0.0.1-SNAPSHOT.jar
 * Qualified Name:     edu.nju.opensource.common.beans.LinkType_List
 * JD-Core Version:    0.6.2
 */