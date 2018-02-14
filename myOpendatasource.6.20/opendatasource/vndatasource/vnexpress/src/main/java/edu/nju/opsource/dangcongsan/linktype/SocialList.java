package edu.nju.opsource.dangcongsan.linktype;

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

public class SocialList extends LinkType {

	private Document doc = null;
	private Site site = null;
	private LinkedList<Link> newItems = null;
	private Link nextPage = null;

	public SocialList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SocialList(String linkTypeName) {
		super.setLinkTypeName(linkTypeName);
	}

	public SocialList(Site site) {

		super.setLinkTypeName("dangsangsan.sociallist");
		this.site = site;
	}

	public void get(String url) {

		super.get(url);
		String content = new EasyHttpDownloader(url).run();

		if (content != null) {
			this.doc = Jsoup.parse(content, this.site.getUrl().getUrl());

			System.out.println(" ... has Crawled.");
		} else {
			setState(ELinkState.CRAWLFAILED);
			System.out.println(" ... crawled failed.");
		}
	}

	// 把新闻列表条目的链接插入表
	@Override
	public boolean handle(Link parentlink) throws Exception {

		if (getState() == ELinkState.CRAWLFAILED)
			return false;

		Elements news = this.doc.select("div#dnn_ContentPane div.w-list div.col-md-8 a.item-title");
		this.newItems = new LinkedList();

		boolean flag = false;
		for (Element newItem : news) {
			Element tmp = newItem;
			if ((tmp != null)) {
				Link link = new Link(tmp.attr("abs:href"), new Social(), ELinkState.UNCRAWL, parentlink,
						this.site).setLinkDate(new java.sql.Date(new Date().getTime()));

				int rst = link.insert();
				if (rst == -1)
					flag = true;// link exist
			}
		}
		if (flag) {

			setState(ELinkState.CRAWLED);
			return false;
		}

		Elements activePageE = this.doc.select("div.phanPage li.active");
		
		//Element nextPageE = activePageE.first().nextElementSibling();
		Element nextPageE=((activePageE!=null&&activePageE.size()>0)?activePageE.first().nextElementSibling():
			this.doc.select("div.phanPage i.fa.fa-chevron-left").first().parent().parent().nextElementSibling());				
		if (nextPageE != null) {

			Element a = nextPageE.select("a").first();
			
			//System.out.println(a);
			String url = a.attr("abs:href");

			this.nextPage = new Link(url, new SocialList(this.site), ELinkState.UNCRAWL, parentlink,
					this.site).setLinkDate(new java.sql.Date(new Date().getTime()));

			/*
			 * System.out.println(nextPageE .first().attr("abs:href"));
			 */

			this.nextPage.insert();

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

	

}

