package edu.nju.opsource.nhandan.linktype;

import java.util.LinkedList;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.nju.opensource.common.beans.ELinkState;
import edu.nju.opensource.common.beans.Link;
import edu.nju.opensource.common.beans.LinkType_List;
import edu.nju.opensource.common.beans.Site;

public class LinkType_SpecialReports_Page extends LinkType_List{

	public LinkType_SpecialReports_Page(String linkTypeName, Site site) {
		super(linkTypeName, site);
		// TODO Auto-generated constructor stub
	}
	public LinkType_SpecialReports_Page()
	/*    */   {
	/* 16 */     super("nhandan.specialReportsPage");
	/*    */   }

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
   
	
	 public boolean handle( Link parentLink ) throws Exception
	   {
	     if (getState() == ELinkState.CRAWLFAILED) return false;
	 
	     if (getNewItems() == null) setNewItems(new LinkedList());
	     Elements news = getDoc().select("div.media-body");
	  //  System.out.println(news);
	     boolean flag = false;
	     for (Element e : news)
	     {
	      // Elements tmp = 
	    	 
	    	// System.out.println(e);
	    	 //插入文章的连接
	    	 Elements tmp = e.select("div div a");
	    	// System.out.println(tmp);
	       if ((tmp != null) && (tmp.size() > 0)) {
	         String url = tmp.first().attr("abs:href");
	        // System.out.println(url);
	         int rst = new Link(url, new LinkType_SpecialReports(), ELinkState.UNCRAWL, parentLink, super.getSite()).insert();
	         if (rst == -1) flag = true;
	       }
	       
	       //插入列表的链接
	       
	       Elements head = e.select("h4 a");
	     //  System.out.println(head);
	       if ((head != null) && (head.size() > 0)) {
	         String url = head.first().attr("abs:href");
	         Link headLink= new Link(url, new LinkType_SpecialReportsList(super.getSite()), ELinkState.UNCRAWL, parentLink, super.getSite());
	         int rstcpy =headLink.insert();
	         if (rstcpy == -1) flag = true;
	        // super.setNextPage(headLink);
	       }
	       
	     }
	     if (flag) {
	       setState(ELinkState.CRAWLED);
	       return false;
	     }
	 
	     Elements nextPageE = getDoc().select("li.next a");
	     if ((nextPageE != null) && (nextPageE.size() != 0))
	     {
	       Link nextPage = new Link(
			nextPageE.first().attr("abs:href"), 
			new LinkType_SpecialReports(super.getSite()), 
			ELinkState.UNCRAWL, 
			parentLink, 
			super.getSite()
		   );
	       super.setNextPage(nextPage);
	       nextPage.insert();
	     }
	     setState(ELinkState.CRAWLED);
	     return true;
	   }
}
