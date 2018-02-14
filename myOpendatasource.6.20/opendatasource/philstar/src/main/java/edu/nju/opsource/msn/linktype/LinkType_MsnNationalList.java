package edu.nju.opsource.msn.linktype;

import java.util.LinkedList;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.nju.opensource.common.beans.ELinkState;
import edu.nju.opensource.common.beans.Link;
import edu.nju.opensource.common.beans.LinkType_List;
import edu.nju.opensource.common.beans.Site;
import edu.nju.opsource.philstar.linktype.LinkType_Nation;
import edu.nju.opsource.philstar.linktype.LinkType_NationList;

@SuppressWarnings("serial")
public class LinkType_MsnNationalList extends LinkType_List{
	public LinkType_MsnNationalList() {
		super("msn.en-ph.nationallist");
	}

	public LinkType_MsnNationalList(String linkTypeName) {
		super("msn.en-ph.nationallist");
	}
	
	public LinkType_MsnNationalList(Site site) {
		super("philstar.nationlist");
		super.setSite(site);
	}
	
	public boolean handle(Link parentlink) throws Exception
	{
		System.out.println( this.getLinkTextData());
		if (getState() == ELinkState.CRAWLFAILED) return false;
		
		if (getNewItems() == null) setNewItems(new LinkedList<Link>());
		Elements opinions = getDoc().select("div[data-aop*=newscategory] ul li");
		boolean flag = false;
		for (Element e : opinions)
		{
			Elements tmp = e.select("a");
			if ((tmp != null) && (tmp.size() > 0)) {
				String url = tmp.first().attr("abs:href");
				/*
				int rst = new Link(
							url, 
							new LinkType_MsnNational(), 
							ELinkState.UNCRAWL, 
							parentlink, 
							super.getSite()
				).insert();
				if (rst == -1) flag = true;*/
				System.out.println( url );
			}
		}
		if (flag) {
			setState(ELinkState.CRAWLED);
			return false;
		}

		setState(ELinkState.CRAWLED);
		return true;
	}
}
