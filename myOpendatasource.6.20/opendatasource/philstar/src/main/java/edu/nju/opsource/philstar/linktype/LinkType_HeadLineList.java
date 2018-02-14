/*     */ package edu.nju.opsource.philstar.linktype;
/*     */ 
/*     */ import com.onezetta.downloader.EasyHttpDownloader;
/*     */ import edu.nju.opensource.common.beans.ELinkState;
/*     */ import edu.nju.opensource.common.beans.Link;
/*     */ import edu.nju.opensource.common.beans.LinkType;
/*     */ import edu.nju.opensource.common.beans.Site;
/*     */ import java.io.InputStream;
/*     */ import java.util.Date;
/*     */ import java.util.LinkedList;
/*     */ import org.jsoup.Jsoup;
/*     */ import org.jsoup.nodes.Document;
/*     */ import org.jsoup.nodes.Element;
/*     */ import org.jsoup.select.Elements;
/*     */ 
/*     */ public class LinkType_HeadLineList extends LinkType
/*     */ {
/*  21 */   private Document doc = null;
/*  22 */   private Site site = null;
/*  23 */   private LinkedList<Link> newItems = null;
/*  24 */   private Link nextPage = null;
/*     */ 
/*     */   public LinkType_HeadLineList(String linkTypeName) {
/*  27 */     super.setLinkTypeName(linkTypeName);
/*     */   }
/*     */ 
/*     */   public LinkType_HeadLineList() {
/*  31 */     super.setLinkTypeName("philstar.headlinelist");
/*     */   }
/*     */ 
/*     */   public LinkType_HeadLineList(Site site) {
/*  35 */     super.setLinkTypeName("philstar.headlinelist");
/*  36 */     this.site = site;
/*     */   }
/*     */ 
/*     */   public void get(String url) {
/*  40 */     super.get(url);
/*  41 */     String content = new EasyHttpDownloader(url).run();
/*     */ 
/*  43 */     if (content != null){
/*  44 */       this.doc = Jsoup.parse(content, this.site.getUrl().getUrl());
				System.out.println( " ... has Crawled.");
			  }
/*     */     else{
/*  46 */       setState(ELinkState.CRAWLFAILED);
				System.out.println( " ... crawled failed.");
			  }
/*     */   }
/*     */ //把列表中的每一项插入
/*     */   public boolean handle(Link parentlink)
/*     */     throws Exception
/*     */   {
/*  52 */     if (getState() == ELinkState.CRAWLFAILED) return false;
/*  53 */     Elements news = this.doc.select("table.views-table tr");
/*  54 */     this.newItems = new LinkedList();
/*     */ 
/*  56 */     boolean flag = false;
/*  57 */     for (Element newItem : news) {
/*  58 */       Elements tmp = newItem.select("span.article-title a");
/*  59 */       if ((tmp != null) && (tmp.size() != 0))
/*     */       {
/*  61 */         Link link = new Link(tmp.first().attr("abs:href"), new LinkType_HeadLine(), ELinkState.UNCRAWL, parentlink, this.site);
/*  62 */         int rst = link.insert();	
/*  63 */         if (rst == -1) flag = true;
/*     */       }
/*     */     }
/*  66 */     if (flag) {
/*  67 */       setState(ELinkState.CRAWLED);
/*  68 */       //return false;
/*     */     }
/*     */ 
/*  71 */     Elements nextPageE = this.doc.select("li.pager-next a");
/*  72 */     if ((nextPageE != null) && (nextPageE.size() != 0)) {
/*  73 */       this.nextPage = new Link(nextPageE
/*  74 */         .first().attr("abs:href"), new LinkType_HeadLineList(this.site), ELinkState.UNCRAWL, parentlink, this.site);
/*     */     System.out.println("Headline-76");
System.out.println("---prepareinsertNextPage");
/*  79 */      if( this.nextPage.insert()!=-1)System.out.println("---insertNextPageSuc");;
/*     */     }
/*  81 */     setState(ELinkState.CRAWLED);
/*  82 */     return true;
/*     */   }
/*     */ 
/*     */   public Date getLinkDate()
/*     */   {
/*  87 */     return null;
/*     */   }
/*     */ 
/*     */   public String getLinkTextData()
/*     */   {
/*  92 */     return null;
/*     */   }
/*     */ 
/*     */   public InputStream getLinkBinaryData()
/*     */   {
/*  97 */     return null;
/*     */   }
/*     */ 
/*     */   public String getLinkUniqeID(String linkId)
/*     */   {
/* 103 */     return null;
/*     */   }
/*     */ 
/*     */   public Site getSite() {
/* 107 */     return this.site;
/*     */   }
/*     */ 
/*     */   public void setSite(Site site) {
/* 111 */     this.site = site;
/*     */   }
/*     */ 
/*     */   public LinkedList<Link> getNewItems() {
/* 115 */     return this.newItems;
/*     */   }
/*     */ 
/*     */   public void setNewItems(LinkedList<Link> newItems) {
/* 119 */     this.newItems = newItems;
/*     */   }
/*     */ 
/*     */   public Link getNextPage() {
/* 123 */     return this.nextPage;
/*     */   }
/*     */ 
/*     */   public void setNextPage(Link nextPage) {
/* 127 */     this.nextPage = nextPage;
/*     */   }
/*     */ }

/* Location:           C:\workspace\opendatasource\philstar\target\philstar-0.0.1-SNAPSHOT.jar
 * Qualified Name:     edu.nju.opsource.philstar.linktype.LinkType_HeadLineList
 * JD-Core Version:    0.6.2
 */