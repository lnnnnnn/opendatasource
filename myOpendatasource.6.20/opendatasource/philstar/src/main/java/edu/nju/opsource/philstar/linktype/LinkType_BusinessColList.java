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
/*    */ public class LinkType_BusinessColList extends LinkType_List
/*    */ {
/*    */   public LinkType_BusinessColList()
/*    */   {
/* 16 */     super("philstar.business.columns");
/*    */   }
/*    */ 
/*    */   public LinkType_BusinessColList(Site site) {
/* 20 */     super("philstar.business.columns");
/* 21 */     super.setSite(site);
/*    */   }
/*    */ 
/*    */   public boolean handle(Link parentlink) throws Exception
/*    */   {
/* 26 */     if (getState() == ELinkState.CRAWLFAILED) return false;
/*    */ 
/* 28 */     if (getNewItems() == null) setNewItems(new LinkedList());
/* 29 */     Elements opinions = getDoc().select("table.views-table tr");
/*    */ 
/* 31 */     boolean flag = false;
/* 32 */     for (Element e : opinions)
/*    */     {
/* 34 */       Elements tmp = e.select("span.article-title a");
/* 35 */       if ((tmp != null) && (tmp.size() > 0)) {
/* 36 */         String url = tmp.first().attr("abs:href");
/* 37 */         int rst = new Link(url, new LinkType_BusinessColumn(), ELinkState.UNCRAWL, parentlink, super.getSite()).insert();
/* 38 */         if (rst == -1) flag = true;
/*    */       }
/*    */     }
/* 41 */     if (flag) {
/* 42 */       setState(ELinkState.CRAWLED);
/* 43 */       return false;
/*    */     }
/*    */ 
/* 46 */     Elements nextPageE = getDoc().select("li.pager-next a");
/* 47 */     if ((nextPageE != null) && (nextPageE.size() != 0))
/*    */     {
/* 53 */       Link nextPage = new Link(
				  nextPageE.first().attr("abs:href"), 
				  new LinkType_BusinessColList(super.getSite()), 
				  ELinkState.UNCRAWL, 
				  parentlink, 
				  super.getSite()
				);
/* 54 */       super.setNextPage(nextPage);
/* 55 */       nextPage.insert();
/*    */     }
/* 57 */     setState(ELinkState.CRAWLED);
/* 58 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\workspace\opendatasource\philstar\target\philstar-0.0.1-SNAPSHOT.jar
 * Qualified Name:     edu.nju.opsource.philstar.linktype.LinkType_BusinessColList
 * JD-Core Version:    0.6.2
 */