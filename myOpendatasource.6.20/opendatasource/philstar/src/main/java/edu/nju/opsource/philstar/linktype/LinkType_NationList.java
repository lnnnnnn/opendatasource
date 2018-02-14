/*    */ package edu.nju.opsource.philstar.linktype;
/*    */ 
/*    */ import edu.nju.opensource.common.beans.ELinkState;
/*    */ import edu.nju.opensource.common.beans.Link;
/*    */ import edu.nju.opensource.common.beans.LinkType_List;
/*    */ import edu.nju.opensource.common.beans.Site;
/*    */ import java.util.LinkedList;
/*    */ import org.jsoup.nodes.Document;
/*    */ import org.jsoup.nodes.Element;
/*    */ import org.jsoup.select.Elements;
/*    */ 
/*    */ public class LinkType_NationList extends LinkType_List
/*    */ {
/*    */   public LinkType_NationList()
/*    */   {
/* 17 */     super("philstar.nationlist");
/*    */   }
/*    */ 
/*    */   public LinkType_NationList(Site site) {
/* 21 */     super("philstar.nationlist");
/* 22 */     super.setSite(site);
/*    */   }
/*    */ 
/*    */   public boolean handle(Link parentlink) throws Exception
/*    */   {
/* 27 */     if (getState() == ELinkState.CRAWLFAILED) return false;
/*    */ 
/* 29 */     if (getNewItems() == null) setNewItems(new LinkedList());
/* 30 */     Elements opinions = getDoc().select("table.views-table tr");
/*    */ 
/* 32 */     boolean flag = false;
/* 33 */     for (Element e : opinions)
/*    */     {
/* 35 */       Elements tmp = e.select("span.article-title a");
/* 36 */       if ((tmp != null) && (tmp.size() > 0)) {
/* 37 */         String url = tmp.first().attr("abs:href");
/* 38 */         int rst = new Link(url, new LinkType_Nation(), ELinkState.UNCRAWL, parentlink, super.getSite()).insert();
/* 39 */         if (rst == -1) flag = true;
/*    */       }
/*    */     }
/* 42 */     if (flag) {
/* 43 */       setState(ELinkState.CRAWLED);
/* 44 */       return false;
/*    */     }
/*    */  System.out.println("NationList-45");
System.out.println("---insertNextPage");
/* 47 */     Elements nextPageE = getDoc().select("li.pager-next a");
/* 48 */     if ((nextPageE != null) && (nextPageE.size() != 0))
/*    */     {
/* 54 */       Link nextPage = new Link(
				 nextPageE.first().attr("abs:href"), 
				 new LinkType_NationList(super.getSite()), 
				 ELinkState.UNCRAWL, 
				 parentlink, 
				 super.getSite());
/* 55 */       super.setNextPage(nextPage);
/* 56 */       nextPage.insert();
/*    */     }
/* 58 */     setState(ELinkState.CRAWLED);
/* 59 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\workspace\opendatasource\philstar\target\philstar-0.0.1-SNAPSHOT.jar
 * Qualified Name:     edu.nju.opsource.philstar.linktype.LinkType_NationList
 * JD-Core Version:    0.6.2
 */