/*    */ package edu.nju.opsource.philstar.linktype;
/*    */ 
/*    */ import edu.nju.opensource.common.beans.Data;
/*    */ import edu.nju.opensource.common.beans.LinkType_Data;
/*    */ import edu.nju.opsource.philstar.data.Data_Business_Column;
/*    */ import edu.nju.opsource.philstar.data.Data_Opinion;
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.Locale;
/*    */ import org.jsoup.nodes.Document;
/*    */ import org.jsoup.nodes.Element;
/*    */ import org.jsoup.select.Elements;
/*    */ 
/*    */ public class LinkType_BusinessColumn extends LinkType_Data
/*    */ {
/*    */   private Data_Opinion data;
/*    */ 
/*    */   public LinkType_BusinessColumn()
/*    */   {
/* 21 */     super("philstar.business.column");
/*    */   }
/*    */ 
/*    */   public Data getData() {
/* 25 */     return this.data;
/*    */   }
/*    */ 
/*    */   public void getDataFromDoc() {
/* 29 */     Data_Business_Column data_business_column = new Data_Business_Column();
/*    */ 
/* 31 */     Elements paragraph = getDoc().select("div.field-name-body p");
/* 32 */     StringBuffer content = new StringBuffer();
/* 33 */     StringBuffer contentWithTag = new StringBuffer();
/* 34 */     for (Element e : paragraph) {
/* 35 */       content.append(e.text());
/* 36 */       contentWithTag.append(e.outerHtml());
/*    */     }
/*    */ 
/* 39 */     data_business_column.setContent(content.toString());
/* 40 */     data_business_column.setContentWithTag(contentWithTag.toString());
/*    */ 
/* 42 */     data_business_column.setDate(getLinkDate() == null ? new Date() : getLinkDate());
/*    */ 
/* 44 */     Elements titleE = getDoc().select("h1#page-title");
/* 45 */     if ((titleE == null) || (titleE.size() == 0)) data_business_column.setTitle("NULLDATAFIELD"); else {
/* 46 */       data_business_column.setTitle(titleE.first().text());
/*    */     }
/*    */ 
/* 49 */     Elements authorE = getDoc().select("span.article-author-info");
/* 50 */     if ((authorE == null) || (authorE.size() == 0)) data_business_column.setAuthor("NULLDATAFIELD"); else {
/* 51 */       data_business_column.setAuthor(authorE.first().text());
/*    */     }
/* 53 */     Elements titleImgE = getDoc().select("img.article-author-photo");
/* 54 */     if ((titleImgE == null) || (titleImgE.size() == 0)) data_business_column.setImgUri("NULLDATAFIELD"); else {
/* 55 */       data_business_column.setImgUri(titleImgE.first().attr("abs:src"));
/*    */     }
/* 57 */     this.data = data_business_column;
/*    */   }
/*    */ 
/*    */   public Date getLinkDate()
/*    */   {
/* 63 */     if (getDoc() != null) {
/* 64 */       Element e = getDoc().select("span.article-date-info").first();
/* 65 */       String dateStr = e.text();
/*    */ 
/* 67 */       SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy - hh:mma", Locale.US);
/* 68 */       Date date = null;
/*    */       try {
/* 70 */         date = format.parse(dateStr);
/*    */       } catch (ParseException ex) {
/* 72 */         dateStr = dateStr.substring(dateStr.indexOf(" ") + 1);
/*    */         try {
/* 74 */           date = format.parse(dateStr);
/*    */         } catch (ParseException e1) {
/* 76 */           e1.printStackTrace();
/*    */         }
/*    */       }
/* 79 */       return date;
/*    */     }
/* 81 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\workspace\opendatasource\philstar\target\philstar-0.0.1-SNAPSHOT.jar
 * Qualified Name:     edu.nju.opsource.philstar.linktype.LinkType_BusinessColumn
 * JD-Core Version:    0.6.2
 */