package edu.nju.opsource.vietnamplus.linktype;

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

public class LinkType_CultureList extends LinkType {

	private Document doc = null;
	private Site site = null;
	private LinkedList<Link> newItems = null;
	private Link nextPage = null;

	public LinkType_CultureList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LinkType_CultureList(String linkTypeName) {
		super.setLinkTypeName(linkTypeName);
	}

	public LinkType_CultureList(Site site) {

		super.setLinkTypeName("vietnameplus.culturelist");
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

		Elements news = this.doc.select("div.story-listing.slist-03 article.story");

		this.newItems = new LinkedList();

		boolean flag = false;
		for (Element newItem : news) {
			Element tmp = newItem.select("p.meta").first().nextElementSibling();
			if ((tmp != null)) {
				Link link = new Link(tmp.attr("abs:href"), new LinkType_Culture(), ELinkState.UNCRAWL, parentlink,
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

		Elements activePageE = this.doc.select("span#ctl00_mainContent_ctl00_ContentList1_pager li.active");

		Element nextPageE = activePageE.first().nextElementSibling();

		if (nextPageE != null) {

			Element a = nextPageE.select("a").first();
			String url = a.attr("abs:href");

			this.nextPage = new Link(url, new LinkType_CultureList(this.site), ELinkState.UNCRAWL, parentlink,
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
