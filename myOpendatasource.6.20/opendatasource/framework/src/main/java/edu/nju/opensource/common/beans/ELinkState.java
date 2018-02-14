/*    */ package edu.nju.opensource.common.beans;
/*    */ 
/*    */ public enum ELinkState
/*    */ {
/*  4 */   UNCRAWL("uncrawl", 1), CRAWLING("crawling", 2), 
/*  5 */   CRAWLED("crawled", 3), CRAWLFAILED("crawlfailed", 4),
		   HANDLING("handleing", 5), HANDLED("handled", 6),
		   HANDLEFAILED("handlefailed", 7);;
/*    */ 
/*    */   private String name;
/*    */   private int index;
/*    */ 
/* 11 */   private ELinkState(String name, int index) { this.name = name;
/* 12 */     this.index = index; }
/*    */ 
/*    */   public static ELinkState getELinkState(String name)
/*    */   {
/* 16 */     for (ELinkState c : values()) {
/* 17 */       if (c.getName().equals(name)) {
/* 18 */         return c;
/*    */       }
/*    */     }
/* 21 */     return null;
/*    */   }
/*    */ 
/*    */   public static String getName(int index) {
/* 25 */     for (ELinkState c : values()) {
/* 26 */       if (c.getIndex() == index) {
/* 27 */         return c.name;
/*    */       }
/*    */     }
/* 30 */     return null;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 34 */     return this.name;
/*    */   }
/*    */   public void setName(String name) {
/* 37 */     this.name = name;
/*    */   }
/*    */   public int getIndex() {
/* 40 */     return this.index;
/*    */   }
/*    */   public void setIndex(int index) {
/* 43 */     this.index = index;
/*    */   }
/*    */ }

/* Location:           C:\workspace\opendatasource\philstar\target\philstar-0.0.1-SNAPSHOT.jar
 * Qualified Name:     edu.nju.opensource.common.beans.ELinkState
 * JD-Core Version:    0.6.2
 */