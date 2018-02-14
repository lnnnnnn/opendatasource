/*    */ package edu.nju.opsource.philstar.linktype;
/*    */ 
/*    */ import edu.nju.opensource.common.beans.Data;
/*    */ import edu.nju.opensource.common.beans.LinkType_Data;
/*    */ import edu.nju.opsource.philstar.data.Data_Nation;
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.Locale;
/*    */ import org.jsoup.nodes.Document;
/*    */ import org.jsoup.nodes.Element;
/*    */ import org.jsoup.select.Elements;
/*    */ 
/*    */ public class LinkType_Nation extends LinkType_Data
/*    */ {
/*    */   private Data_Nation data;
/*    */ 
/*    */   public LinkType_Nation()
/*    */   {
/* 20 */     super("philstar.nation");
/*    */   }
/*    */ 
/*    */   public Data getData() {
/* 24 */     return this.data;
/*    */   }
/*    */ 
/*    */   public void getDataFromDoc() {
/* 28 */     Data_Nation data_Nation = new Data_Nation();
/*    */ 
/* 30 */     Elements paragraph = getDoc().select("div.field-name-body p");
/* 31 */     StringBuffer content = new StringBuffer();
/* 32 */     StringBuffer contentWithTag = new StringBuffer();
/* 33 */     for (Element e : paragraph) {
/* 34 */       content.append(e.text());
/* 35 */       contentWithTag.append(e.outerHtml());
/*    */     }
/*    */ 
/* 38 */     data_Nation.setContent(content.toString());
/* 39 */     data_Nation.setContentWithTag(contentWithTag.toString());
/*    */ 
/* 41 */     data_Nation.setDate(getLinkDate() == null ? new Date() : getLinkDate());
/*    */ 
/* 43 */     Elements titleE = getDoc().select("h1#page-title");
/* 44 */     if ((titleE == null) || (titleE.size() == 0)) data_Nation.setTitle("NULLDATAFIELD"); else {
/* 45 */       data_Nation.setTitle(titleE.first().text());
/*    */     }
/*    */ 
/* 48 */     Elements authorE = getDoc().select("span.article-author-info a");
/* 49 */     if ((authorE == null) || (authorE.size() == 0)) data_Nation.setAuthor("NULLDATAFIELD"); else {
/* 50 */       data_Nation.setAuthor(authorE.first().text());
/*    */     }
/* 52 */     this.data = data_Nation;
/*    */   }
/*    */ 
/*    */   public Date getLinkDate()
/*    */   {
/* 57 */     if (getDoc() != null) {
/* 58 */       Element e = getDoc().select("span.article-date-info").first();
/* 59 */       String dateStr = e.text();
/*    */ 
/* 61 */       SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy - hh:mma", Locale.US);
/* 62 */       Date date = null;
/*    */       try {
/* 64 */         date = format.parse(dateStr);
/*    */       } catch (ParseException ex) {
/* 66 */         dateStr = dateStr.substring(dateStr.indexOf(" ") + 1);
/*    */         try {
/* 68 */           date = format.parse(dateStr);
/*    */         } catch (ParseException e1) {
/* 70 */           e1.printStackTrace();
/*    */         }
/*    */       }
/* 73 */       return date;
/*    */     }
/* 75 */     return null;
/*    */   }
               public static void main(String[] args){
            	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);
            	   Date date = null;    String dateStr = "Sunday, 2016-07-10 10:19:10"; 
            	          try {
            	            date = format.parse(dateStr);
            	          } catch (ParseException ex) {
            	        	//  System.out.println("e");
            	            dateStr = dateStr.substring(dateStr.indexOf(" ") + 1);
            	            try {
            	              date = format.parse(dateStr);
            	            } catch (ParseException e1) {
            	              e1.printStackTrace();
            	            }
                          }
            	          
            	          System.out.println(date);
               }
 }

/* Location:           C:\workspace\opendatasource\philstar\target\philstar-0.0.1-SNAPSHOT.jar
 * Qualified Name:     edu.nju.opsource.philstar.linktype.LinkType_Nation
 * JD-Core Version:    0.6.2
 */