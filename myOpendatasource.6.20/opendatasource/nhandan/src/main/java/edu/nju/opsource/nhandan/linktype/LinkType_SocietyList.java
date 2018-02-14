

package edu.nju.opsource.nhandan.linktype;
import java.io.InputStream;
import java.util.Date;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.onezetta.downloader.EasyHttpDownloader;

import edu.nju.opensource.common.beans.ELinkState;
import edu.nju.opensource.common.beans.Link;
import edu.nju.opensource.common.beans.LinkType;
import edu.nju.opensource.common.beans.Site;

public class LinkType_SocietyList extends LinkType{
    
	
	 private Document doc = null;
	 private Site site = null;
	 private LinkedList<Link> newItems = null;
	 private Link nextPage = null;
	 
	 
	 public LinkType_SocietyList() {
		super();
		// TODO Auto-generated constructor stub
	}
	 public LinkType_SocietyList(String linkTypeName) {
		      super.setLinkTypeName(linkTypeName);
		    }
	public LinkType_SocietyList(Site site) {
		//System.out.println("Politics_list-35:");
		//System.out.println(site);
		    super.setLinkTypeName("nhandan.societyList");
		    this.site = site;
		  }
	
	
	 public void get(String url) {
		 
		     super.get(url);
		     String content = new EasyHttpDownloader(url).run();
		     
		     if (content != null){
		   
		       this.doc = Jsoup.parse(content, this.site.getUrl().getUrl());
		     //  System.out.println(this.doc+"LinkPoliticsList-40");
				System.out.println( " ... has Crawled.");
			  }
		     else{
		       setState(ELinkState.CRAWLFAILED);
				System.out.println( " ... crawled failed.");
			  }
		   }
	 //把新闻列表条目的链接插入表
	@Override
	public boolean handle(Link parentlink) throws Exception {
		// TODO Auto-generated method stub
  
     if (getState() == ELinkState.CRAWLFAILED) return false;
     
    
     Elements news = this.doc.select("h3.media-heading");
  
     this.newItems = new LinkedList();
 
     boolean flag = false;
     for (Element newItem : news) {
       Elements tmp = newItem.select("a.pull-left");
       if ((tmp != null) && (tmp.size() != 0))
       {
         Link link = new Link(tmp.first().attr("abs:href"), new LinkType_Society(), ELinkState.UNCRAWL, parentlink, this.site);
         System.out.println(tmp.first().attr("abs:href"));
         int rst = link.insert();	
         if (rst == -1) flag = true;//link exist
       }
     }
     if (flag) {
       setState(ELinkState.CRAWLED);
       return false;
     }
  
     Elements nextPageE = this.doc.select("li.next a");
  
     if ((nextPageE != null) && (nextPageE.size() != 0)) {
    	 System.out.println(nextPageE
         .first().attr("abs:href"));
       this.nextPage = new Link(nextPageE
         .first().attr("abs:href"), new LinkType_SocietyList(this.site), ELinkState.UNCRAWL, parentlink, this.site);
 
     }
     setState(ELinkState.CRAWLED);
     return true;
   
		
	}

	@Override
	public String getLinkTextData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getLinkBinaryData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLinkUniqeID(String paramString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getLinkDate() {
		// TODO Auto-generated method stub
		return null;
	}
	 public Site getSite() {
		      return this.site;
		    }
	@Override
	public void setSite(Site site) {
		// TODO Auto-generated method stub
		 this.site = site;
	}
	
	public LinkedList<Link> getNewItems() {
		     return this.newItems;
		   }
		 
		   public void setNewItems(LinkedList<Link> newItems) {
		     this.newItems = newItems;
		   }
		 
		   public Link getNextPage() {
		     return this.nextPage;
		   }
		 
		   public void setNextPage(Link nextPage) {
		     this.nextPage = nextPage;
		   }
		@Override
		public String toString() {
			return "LinkType_SocietyList [doc=" + doc + ", site=" + site + ", newItems=" + newItems + ", nextPage="
					+ nextPage + "]";
		}
         
}
