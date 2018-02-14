/*     */ package edu.nju.opsource.philstar.linktype;
/*     */ 
/*     */ import com.onezetta.abenablebean.util.FileUtil;
/*     */ import com.onezetta.downloader.EasyHttpDownloader;

import edu.nju.opensource.common.action.OpenDataSource;
/*     */ import edu.nju.opensource.common.beans.ELinkState;
import edu.nju.opensource.common.beans.Link;
/*     */ import edu.nju.opensource.common.beans.LinkType;
/*     */ import edu.nju.opensource.common.beans.Site;
/*     */ import edu.nju.opsource.philstar.data.Data_HeadLine;
/*     */ import java.io.File;
/*     */ import java.io.InputStream;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ import org.jsoup.Jsoup;
/*     */ import org.jsoup.nodes.Document;
/*     */ import org.jsoup.nodes.Element;
/*     */ import org.jsoup.select.Elements;
/*     */ 
/*     */ public class LinkType_HeadLine extends LinkType
/*     */ {
/*  27 */   private String pageContent = null;
/*  28 */   private Document doc = null;
/*  29 */   private Data_HeadLine data = null;
/*     */ 
/*     */   public LinkType_HeadLine(String linkTypeName) {
/*  32 */     super.setLinkTypeName(linkTypeName);
/*     */   }
/*     */ 
/*     */   public LinkType_HeadLine() {
/*  36 */     super.setLinkTypeName("philstar.headline");
/*     */   }
/*     */ 
/*     */   public void get(String url)//赋值给this.doc,this.pageContent
/*     */   {
/*  41 */     super.get(url);
/*  42 */     this.pageContent = new EasyHttpDownloader(url).run();
/*  43 */     if (this.pageContent != null){
/*  44 */       this.doc = Jsoup.parse(this.pageContent);
				System.out.println( " ... has Crawled.");
			  }
/*     */     else{
/*  46 */       setState(ELinkState.CRAWLFAILED);
				System.out.println( " ... crawled failed.");
			  }
/*     */   }
/*     */ 
/*     */   public Data_HeadLine getData()
/*     */   {
/*  51 */     return this.data;
/*     */   }
/*     */ //Give assignment to this.data
/*     */   private void getDataFromDoc() {
/*  55 */     Data_HeadLine data_HeadLine = new Data_HeadLine();
/*     */ 
/*  57 */     Elements paragraph = this.doc.select("div.field-name-body p");
/*  58 */     StringBuffer content = new StringBuffer();
/*  59 */     StringBuffer contentWithTag = new StringBuffer();
/*  60 */     for (Element e : paragraph) {
/*  61 */       content.append(e.text());
/*  62 */       contentWithTag.append(e.outerHtml());
/*     */     }
/*     */ 
/*  65 */     data_HeadLine.setContent(content.toString());
/*  66 */     data_HeadLine.setContentWithTag(contentWithTag.toString());
/*     */ 
/*  68 */     data_HeadLine.setDate(getLinkDate() == null ? new Date() : getLinkDate());
/*     */ 
/*  70 */     Elements titleE = this.doc.select("h1#page-title");
/*  71 */     if ((titleE == null) || (titleE.size() == 0)) data_HeadLine.setTitle("NULLDATAFIELD"); else {
/*  72 */       data_HeadLine.setTitle(titleE.first().text());
/*     */     }
/*  74 */     Elements abstractE = this.doc.select("div.field-name-field-image-caption");
/*  75 */     if ((abstractE == null) || (abstractE.size() == 0)) data_HeadLine.setAbstract("NULLDATAFIELD"); else {
/*  76 */       data_HeadLine.setAbstract(abstractE.first().text());
/*     */     }
/*  78 */     Elements authorE = this.doc.select("span.article-author-info");
/*  79 */     if ((authorE == null) || (authorE.size() == 0)) data_HeadLine.setAuthor("NULLDATAFIELD"); else {
/*  80 */       data_HeadLine.setAuthor(authorE.first().text());
/*     */     }
/*  82 */     Elements titleImgE = this.doc.select("div.field-name-field-image-link img");
/*  83 */     if ((titleImgE == null) || (titleImgE.size() == 0)) data_HeadLine.setImgUri("NULLDATAFIELD"); else {
/*  84 */       data_HeadLine.setImgUri(titleImgE.first().attr("src"));
/*     */     }
/*  86 */     this.data = data_HeadLine;
/*     */   }
/*     */ 
/*     */   public boolean handle( Link link ) throws Exception
/*     */   {
/*  91 */     if (getState() == ELinkState.CRAWLFAILED) return false;
/*  92 */     String tmpDir = OpenDataSource.TMP_DATA_DIR + getLinkUniqeID(super.getLinkId()) + "/";
/*  93 */     String dataDir = OpenDataSource.UNUPLOAD_DATA_DIR + getLinkUniqeID(super.getLinkId()) + ".zip";
/*  94 */     getDataFromDoc();

			  this.data.setUrl( link.getUrl() );
			  this.data.setSiteUUID( link.getSite().getUuid() );
/*     */ 
/*  96 */     FileUtil.createDirectory("", tmpDir);
/*  97 */     if (new File(tmpDir).exists()) {
/*  98 */       FileUtil.writeStringToFile(this.pageContent, tmpDir + getLinkUniqeID(super.getLinkId()) + ".html");
/*  99 */       FileUtil.writeStringToFile(this.data.toJSON(), tmpDir + getLinkUniqeID(super.getLinkId()) + ".json");
/* 100 */       FileUtil.zipFile(tmpDir, dataDir);
/* 101 */       setState(ELinkState.CRAWLED);
/* 102 */       return true;
/*     */     }
/* 104 */     setState(ELinkState.CRAWLFAILED);
/* 105 */     return false;
/*     */   }
/*     */ 
/*     */   public Date getLinkDate()
/*     */   {
/* 110 */     if (this.doc != null) {
/* 111 */       Element e = this.doc.select("span.article-date-info").first();
/* 112 */       String dateStr = e.text();
/*     */ 
/* 114 */       SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy - hh:mma", Locale.US);
/* 115 */       Date date = null;
/*     */       try {
/* 117 */         date = format.parse(dateStr);
/*     */       } catch (ParseException ex) {
/* 119 */         dateStr = dateStr.substring(dateStr.indexOf(" ") + 1);
/*     */         try {
/* 121 */           date = format.parse(dateStr);
/*     */         } catch (ParseException e1) {
/* 123 */           e1.printStackTrace();
/*     */         }
/*     */       }
/* 126 */       return date;
/*     */     }
/* 128 */     return null;
/*     */   }
/*     */ 
/*     */   public String getLinkTextData()
/*     */   {
/* 133 */     if (this.pageContent != null) {
/* 134 */       return this.pageContent;
/*     */     }
/* 136 */     return null;
/*     */   }
/*     */ 
/*     */   public InputStream getLinkBinaryData()
/*     */   {
/* 142 */     return null;
/*     */   }
/*     */ 
/*     */   public String getLinkUniqeID(String linkeId)//链接类名+日期+链接编号
/*     */   {
/* 147 */     if (getLinkDate() != null) {
/* 148 */       SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd[hh.mm]");
/* 149 */       return getLinkTypeName() + format.format(getLinkDate()) + "." + linkeId;
/*     */     }
/* 151 */     return null;
/*     */   }
/*     */ 
/*     */   public void setSite(Site site)
/*     */   {
/*     */   }
/*     */ }                             

/* Location:           C:\workspace\opendatasource\philstar\target\philstar-0.0.1-SNAPSHOT.jar
 * Qualified Name:     edu.nju.opsource.philstar.linktype.LinkType_HeadLine
 * JD-Core Version:    0.6.2
 */