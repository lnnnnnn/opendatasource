/*    */ package edu.nju.opsource.philstar.linktype;
/*    */ 
/*    */ import edu.nju.opensource.common.beans.Data;
/*    */ import edu.nju.opensource.common.beans.LinkType_Data;
/*    */ import edu.nju.opsource.philstar.data.Data_World;
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.Locale;
/*    */ import org.jsoup.nodes.Document;
/*    */ import org.jsoup.nodes.Element;
/*    */ import org.jsoup.select.Elements;
/*    */ 
/*    */ public class LinkType_World extends LinkType_Data
/*    */ {
/*    */   private Data_World data;
/*    */ 
/*    */   public LinkType_World()
/*    */   {
/* 20 */     super("philstar.world");
/*    */   }
/*    */ 
/*    */   public Data getData() {
/* 24 */     return this.data;
/*    */   }
/*    */ 
/*    */   public void getDataFromDoc() {
/* 28 */     Data_World data_World = new Data_World();
/*    */ 
/* 30 */     Elements paragraph = super.getDoc().select("div.field-name-body div div");
/* 31 */     StringBuffer content = new StringBuffer();
/* 32 */     StringBuffer contentWithTag = new StringBuffer();
/* 33 */     for (Element e : paragraph) {
/* 34 */       content.append(e.text());
/* 35 */       contentWithTag.append(e.outerHtml());
/*    */     }
/*    */ 
/* 38 */     data_World.setContent(content.toString());
/* 39 */     data_World.setContentWithTag(contentWithTag.toString());
/*    */ 
/* 41 */     data_World.setDate(getLinkDate() == null ? new Date() : getLinkDate());
/*    */ 
/* 43 */     Elements titleE = super.getDoc().select("h1#page-title");
/* 44 */     if ((titleE == null) || (titleE.size() == 0)) data_World.setTitle("NULLDATAFIELD"); else {
/* 45 */       data_World.setTitle(titleE.first().text());
/*    */     }
/* 47 */     Elements abstractE = super.getDoc().select("div.field-name-field-image-caption");
/* 48 */     if ((abstractE == null) || (abstractE.size() == 0)) data_World.setAbstract("NULLDATAFIELD"); else {
/* 49 */       data_World.setAbstract(abstractE.first().text());
/*    */     }
/* 51 */     Elements authorE = super.getDoc().select("span.article-author-info a");
/* 52 */     if ((authorE == null) || (authorE.size() == 0)) data_World.setAuthor("NULLDATAFIELD"); else {
/* 53 */       data_World.setAuthor(authorE.first().text());
/*    */     }
/* 55 */     Elements titleImgE = super.getDoc().select("div.field-name-field-image-link img");
/* 56 */     if ((titleImgE == null) || (titleImgE.size() == 0)) data_World.setImgUri("NULLDATAFIELD"); else {
/* 57 */       data_World.setImgUri(titleImgE.first().attr("src"));
/*    */     }
/* 59 */     this.data = data_World;
/*    */   }
/*    */ 
/*    */   public Date getLinkDate()
/*    */   {
/* 65 */     if (getDoc() != null) {
/* 66 */       Element e = getDoc().select("span.article-date-info").first();
/* 67 */       String dateStr = e.text();
/*    */ 
/* 69 */       SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy - hh:mma", Locale.US);
/* 70 */       Date date = null;
/*    */       try {
/* 72 */         date = format.parse(dateStr);
/*    */       } catch (ParseException ex) {
/* 74 */         dateStr = dateStr.substring(dateStr.indexOf(" ") + 1);
/*    */         try {
/* 76 */           date = format.parse(dateStr);
/*    */         } catch (ParseException e1) {
/* 78 */           e1.printStackTrace();
/*    */         }
/*    */       }
/* 81 */       return date;
/*    */     }
/* 83 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\workspace\opendatasource\philstar\target\philstar-0.0.1-SNAPSHOT.jar
 * Qualified Name:     edu.nju.opsource.philstar.linktype.LinkType_World
 * JD-Core Version:    0.6.2
 */