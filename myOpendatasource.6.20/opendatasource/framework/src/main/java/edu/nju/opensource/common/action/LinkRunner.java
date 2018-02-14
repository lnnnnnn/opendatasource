package edu.nju.opensource.common.action;

import edu.nju.opensource.common.beans.Link;
import edu.nju.opensource.common.beans.LinkType;
import edu.nju.opensource.common.beans.Site;

public class LinkRunner {
	private Site site;
	private Link link;
	
	public LinkRunner(Site site, Link link){
		this.site = site;
		this.link = link;
	}
	
	public boolean runTest(){
		try {
			LinkType linkType = OpenDataSource.getLinkTypeObject( this.link.getLinkTypeName());
			
			link.setLinkType( linkType );
			link.setSite(site);
	
			link.getLinkType().setLinkId(link.getId() + "");
			link.getLinkType().get(link.getUrl());
			
			link.getLinkType().handle( link );
			link.setEstate(link.getLinkType().getState().getIndex());
			link.update();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
	

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}
	
}
