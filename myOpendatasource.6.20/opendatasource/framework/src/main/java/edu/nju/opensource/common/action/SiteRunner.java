package edu.nju.opensource.common.action;

import edu.nju.opensource.common.beans.Link;
import edu.nju.opensource.common.beans.Site;

public class SiteRunner {
	private Site site;
	
	public SiteRunner(Site site){
		this.site = site;
	}
	
	public boolean runTest(){
		try{
			Link link = site.getUrl();
			
			link.setLinkType(OpenDataSource.getLinkTypeObject(link.getLinkTypeName()));
			link.setSite(site);

			link.getLinkType().setLinkId(link.getId() + "");
			link.getLinkType().get(link.getUrl());

			link.getLinkType().handle( link );

			link.setEstate(link.getLinkType().getState().getIndex());
			
			link.update();
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
}
