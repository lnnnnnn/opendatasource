/*    */ package edu.nju.opensource.common.beans;
/*    */ 
/*    */ public enum ELingual
/*    */ {
/*  4 */   CHN("chn", 1), JAP("jap", 2), VIE("vietnam", 3), ENG("eng", 4);
/*    */ 
/*    */   private String name;
/*    */   private int index;
/*    */ 
/* 10 */   private ELingual(String name, int index) { this.name = name;
/* 11 */     this.index = index; }
/*    */ 
/*    */   public static ELingual getLingual(String name)
/*    */   {
/* 15 */     for (ELingual c : values()) {
/* 16 */       if (c.getName().equals(name)) {
/* 17 */         return c;
/*    */       }
/*    */     }
/* 20 */     return null;
/*    */   }

/*    */   public static ELingual getLingual(int index)
/*    */   {
/* 15 */     for (ELingual c : values()) {
/* 16 */       if (c.getIndex() == index ) {
/* 17 */         return c;
/*    */       }
/*    */     }
/* 20 */     return null;
/*    */   }
/*    */ 
/*    */   public static String getName(int index) {
/* 24 */     for (ELingual c : values()) {
/* 25 */       if (c.getIndex() == index) {
/* 26 */         return c.name;
/*    */       }
/*    */     }
/* 29 */     return null;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 33 */     return this.name;
/*    */   }
/*    */   public void setName(String name) {
/* 36 */     this.name = name;
/*    */   }
/*    */   public int getIndex() {
/* 39 */     return this.index;
/*    */   }
/*    */   public void setIndex(int index) {
/* 42 */     this.index = index;
/*    */   }
/*    */ }

/* Location:           C:\workspace\opendatasource\philstar\target\philstar-0.0.1-SNAPSHOT.jar
 * Qualified Name:     edu.nju.opensource.common.beans.ELingual
 * JD-Core Version:    0.6.2
 */