/*    */ package edu.nju.opsource.philstar.linktype;
/*    */ 
/*    */ import edu.nju.opensource.common.beans.Data;
/*    */ import edu.nju.opensource.common.beans.LinkType_Data;
/*    */ import edu.nju.opsource.philstar.data.Data_Business;
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.Locale;
/*    */ import org.jsoup.nodes.Document;
/*    */ import org.jsoup.nodes.Element;
/*    */ import org.jsoup.select.Elements;
/*    */ 
/*    */ public class LinkType_Business extends LinkType_Data
/*    */ {
/*    */   private Data_Business data;
/*    */ 
/*    */   public LinkType_Business()
/*    */   {
/* 20 */     super("philstar.business");
/*    */   }
/*    */ 
/*    */   public Data getData() {
/* 24 */     return this.data;
/*    */   }
/*    */ 
/*    */   public void getDataFromDoc() {
/* 28 */     Data_Business data_Business = new Data_Business();
/*    */ 
/* 30 */     Elements paragraph = super.getDoc().select("div.field-name-body p");
/* 31 */     StringBuffer content = new StringBuffer();
/* 32 */     StringBuffer contentWithTag = new StringBuffer();
/* 33 */     for (Element e : paragraph) {
/* 34 */       content.append(e.text());
/* 35 */       contentWithTag.append(e.outerHtml());
/*    */     }
/*    */ 
/* 38 */     data_Business.setContent(content.toString());
/* 39 */     data_Business.setContentWithTag(contentWithTag.toString());
/*    */ 
/* 41 */     data_Business.setDate(getLinkDate() == null ? new Date() : getLinkDate());
/*    */ 
/* 43 */     Elements titleE = super.getDoc().select("h1#page-title");
/* 44 */     if ((titleE == null) || (titleE.size() == 0)) data_Business.setTitle("NULLDATAFIELD"); else {
/* 45 */       data_Business.setTitle(titleE.first().text());
/*    */     }
/* 47 */     Elements abstractE = super.getDoc().select("div.field-name-field-image-caption");
/* 48 */     if ((abstractE == null) || (abstractE.size() == 0)) data_Business.setAbstract("NULLDATAFIELD"); else {
/* 49 */       data_Business.setAbstract(abstractE.first().text());
/*    */     }
/* 51 */     Elements authorE = super.getDoc().select("span.article-author-info a");
/* 52 */     if ((authorE == null) || (authorE.size() == 0)) data_Business.setAuthor("NULLDATAFIELD"); else {
/* 53 */       data_Business.setAuthor(authorE.first().text());
/*    */     }
/* 55 */     Elements titleImgE = super.getDoc().select("div.field-name-field-image-link img");
/* 56 */     if ((titleImgE == null) || (titleImgE.size() == 0)) data_Business.setImgUri("NULLDATAFIELD"); else {
/* 57 */       data_Business.setImgUri(titleImgE.first().attr("src"));
/*    */     }

/* 59 */     this.data = data_Business;
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
 * Qualified Name:     edu.nju.opsource.philstar.linktype.LinkType_Business
 * JD-Core Version:    0.6.2
 */