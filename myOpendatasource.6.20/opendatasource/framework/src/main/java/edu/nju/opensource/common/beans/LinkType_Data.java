/*    */ package edu.nju.opensource.common.beans;
/*    */ 
/*    */ import com.onezetta.abenablebean.util.FileUtil;
/*    */ import com.onezetta.downloader.EasyHttpDownloader;

import edu.nju.opensource.common.action.OpenDataSource;

/*    */ import java.io.File;
/*    */ import java.io.InputStream;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import org.jsoup.Jsoup;
/*    */ import org.jsoup.nodes.Document;
/*    */ 
/*    */ public class LinkType_Data extends LinkType
/*    */ {
/* 18 */   private String pageContent = null;
/* 19 */   private Document doc = null;
/*    */ 
/*    */   public LinkType_Data(String linkTypeName) {
/* 22 */     super.setLinkTypeName(linkTypeName);
/*    */   }
/*    */   public Data getData() {
/* 25 */     return null;
/*    */   }
/*    */   public void getDataFromDoc() {
/*    */   }
/*    */   public Date getLinkDate() {
/* 30 */     return null;
/*    */   }
/*    */ 
/*    */   public void get(String url)
/*    */   {
/* 35 */     super.get(url);
/* 36 */     this.pageContent = new EasyHttpDownloader(url).run();
/* 37 */     if (this.pageContent != null){
/* 38 */       this.doc = Jsoup.parse(this.pageContent);
			   System.out.println( " ... has Crawled.");
			   setState(ELinkState.CRAWLED);
			 }
/*    */     else{
/* 40 */       setState(ELinkState.CRAWLFAILED);
			   System.out.println( " ... crawl failed.");
			 }
/*    */   }
/*    */ 
/*    */   public boolean handle(Link link) throws Exception
/*    */   {
/* 46 */     if (getState() == ELinkState.CRAWLFAILED) return false;
/* 47 */     String tmpDir = OpenDataSource.getTmpDataDir() + getLinkUniqeID(super.getLinkId()) + "/";
/* 48 */     String dataDir = OpenDataSource.getUnuploadDataDir() + getLinkUniqeID(super.getLinkId()) + ".zip";
/* 49 */     getDataFromDoc();
			 System.out.println("LinkType_Data:53");
			 System.out.println(tmpDir);
			 this.getData().setUrl( link.getUrl() );
			 this.getData().setSiteUUID( link.getSite().getUuid() );
/*    */ 
/* 51 */     FileUtil.createDirectory("", tmpDir);
/* 52 */     if (new File(tmpDir).exists()) {
/* 53 */       FileUtil.writeStringToFile(this.pageContent, tmpDir + getLinkUniqeID(super.getLinkId()) + ".html");
			   this.getData().setId( getLinkUniqeID(super.getLinkId()) );
/* 54 */       FileUtil.writeStringToFile(this.getData().toJSON(), tmpDir + getLinkUniqeID(super.getLinkId()) + ".json");
/* 55 */       FileUtil.zipFile(tmpDir, dataDir);
/* 56 */       setState(ELinkState.HANDLED);
/* 57 */       return true;
/*    */     }
/* 59 */     setState(ELinkState.HANDLEFAILED);
/* 60 */     return false;
/*    */   }
/*    */ 
/*    */   public String getPageContent() {
/* 64 */     return this.pageContent;
/*    */   }
/*    */   public void setPageContent(String pageContent) {
/* 67 */     this.pageContent = pageContent;
/*    */   }
/*    */   public Document getDoc() {
/* 70 */     return this.doc;
/*    */   }
/*    */   public void setDoc(Document doc) {
/* 73 */     this.doc = doc;
/*    */   }
/*    */ 
/*    */   public String getLinkTextData()
/*    */   {
/* 78 */     if (this.pageContent != null) {
/* 79 */       return this.pageContent;
/*    */     }
/* 81 */     return null;
/*    */   }
/*    */ 
/*    */   public InputStream getLinkBinaryData()
/*    */   {
/* 87 */     return null;
/*    */   }
/*    */ 
/*    */   public String getLinkUniqeID(String linkeId)
/*    */   {
/* 92 */     if (getLinkDate() != null) {
/* 93 */       SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd[hh.mm]");
              System.out.println("LinkType_Data:100");
              System.out.println(getLinkTypeName());
/* 94 */       return getLinkTypeName() + format.format(getLinkDate()) + "." + linkeId;
/*    */     }
/* 96 */     return null;
/*    */   }
/*    */ 
/*    */   public void setSite(Site site)
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\workspace\opendatasource\philstar\target\philstar-0.0.1-SNAPSHOT.jar
 * Qualified Name:     edu.nju.opensource.common.beans.LinkType_Data
 * JD-Core Version:    0.6.2
 */