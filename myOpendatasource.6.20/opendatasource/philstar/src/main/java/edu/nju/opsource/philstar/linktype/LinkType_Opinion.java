/*    */ package edu.nju.opsource.philstar.linktype;
/*    */ 
/*    */ import edu.nju.opensource.common.beans.Data;
/*    */ import edu.nju.opensource.common.beans.LinkType_Data;
/*    */ import edu.nju.opsource.philstar.data.Data_Opinion;
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.Locale;
/*    */ import org.jsoup.nodes.Document;
/*    */ import org.jsoup.nodes.Element;
/*    */ import org.jsoup.select.Elements;
/*    */ 
/*    */ public class LinkType_Opinion extends LinkType_Data
/*    */ {
/*    */   private Data_Opinion data;
/*    */ 
/*    */   public LinkType_Opinion()
/*    */   {
/* 20 */     super("philstar.opinion");
/*    */   }
/*    */ 
/*    */   public Data getData() {
/* 24 */     return this.data;
/*    */   }
/*    */ 
/*    */   public void getDataFromDoc() {
/* 28 */     Data_Opinion data_Opinion = new Data_Opinion();
/*    */ 
/* 30 */     Elements paragraph = getDoc().select("div.field-name-body p");
/* 31 */     StringBuffer content = new StringBuffer();
/* 32 */     StringBuffer contentWithTag = new StringBuffer();
/* 33 */     for (Element e : paragraph) {
/* 34 */       content.append(e.text());
/* 35 */       contentWithTag.append(e.outerHtml());
/*    */     }
/*    */ 
/* 38 */     data_Opinion.setContent(content.toString());
/* 39 */     data_Opinion.setContentWithTag(contentWithTag.toString());
/*    */ 
/* 41 */     data_Opinion.setDate(getLinkDate() == null ? new Date() : getLinkDate());
/*    */ 
/* 43 */     Elements titleE = getDoc().select("h1#page-title");
/* 44 */     if ((titleE == null) || (titleE.size() == 0)) data_Opinion.setTitle("NULLDATAFIELD"); else {
/* 45 */       data_Opinion.setTitle(titleE.first().text());
/*    */     }
/*    */ 
/* 48 */     Elements authorE = getDoc().select("span.article-author-info");
/* 49 */     if ((authorE == null) || (authorE.size() == 0)) data_Opinion.setAuthor("NULLDATAFIELD"); else {
/* 50 */       data_Opinion.setAuthor(authorE.first().text());
/*    */     }
/* 52 */     Elements titleImgE = getDoc().select("img.article-author-photo");
/* 53 */     if ((titleImgE == null) || (titleImgE.size() == 0)) data_Opinion.setImgUri("NULLDATAFIELD"); else {
/* 54 */       data_Opinion.setImgUri(titleImgE.first().attr("abs:src"));
/*    */     }
/* 56 */     this.data = data_Opinion;
/*    */   }
/*    */ 
/*    */   public Date getLinkDate()
/*    */   {
/* 62 */     if (getDoc() != null) {
/* 63 */       Element e = getDoc().select("span.article-date-info").first();
/* 64 */       String dateStr = e.text();
/*    */ 
/* 66 */       SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy - hh:mma", Locale.US);
/* 67 */       Date date = null;
/*    */       try {
/* 69 */         date = format.parse(dateStr);
/*    */       } catch (ParseException ex) {
/* 71 */         dateStr = dateStr.substring(dateStr.indexOf(" ") + 1);
/*    */         try {
/* 73 */           date = format.parse(dateStr);
/*    */         } catch (ParseException e1) {
/* 75 */           e1.printStackTrace();
/*    */         }
/*    */       }
/* 78 */       return date;
/*    */     }
/* 80 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\workspace\opendatasource\philstar\target\philstar-0.0.1-SNAPSHOT.jar
 * Qualified Name:     edu.nju.opsource.philstar.linktype.LinkType_Opinion
 * JD-Core Version:    0.6.2
 */