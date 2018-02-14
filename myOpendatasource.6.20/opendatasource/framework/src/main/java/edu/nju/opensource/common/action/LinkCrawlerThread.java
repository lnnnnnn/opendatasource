/*    */ package edu.nju.opensource.common.action;
/*    */ 
/*    */ import com.onezetta.threadpool.TaskBaseImpl;
/*    */ import edu.nju.opensource.common.beans.Link;
/*    */ import edu.nju.opensource.common.beans.LinkType;
/*    */ 
/*    */ public class LinkCrawlerThread extends TaskBaseImpl
/*    */ {
/*    */   private Link link;
/*    */ 
/*    */   public void stratWork()
/*    */     throws Exception
/*    */   {
/* 13 */     LinkType linkType = this.link.getLinkType();
/* 14 */     linkType.get(this.link.getUrl());
/* 15 */     linkType.handle( this.link );
/*    */   }
/*    */ 
/*    */   public Link getLink() {
/* 19 */     return this.link;
/*    */   }
/*    */ 
/*    */   public void setLink(Link link) {
/* 23 */     this.link = link;
/*    */   }
/*    */ }

/* Location:           C:\workspace\opendatasource\philstar\target\philstar-0.0.1-SNAPSHOT.jar
 * Qualified Name:     edu.nju.opensource.common.action.LinkCrawlerThread
 * JD-Core Version:    0.6.2
 */