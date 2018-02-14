package edu.nju.opsource.vietnamplus.linktype;

import edu.nju.opensource.common.beans.Site;

public class LinkType_World extends LinkType_Politics {
	public LinkType_World(String linkTypeName) {
		super.setLinkTypeName(linkTypeName);
	}

	public LinkType_World() {
		super.setLinkTypeName("vietnamplus.world");
	}

	public LinkType_World(Site site) {
		// TODO Auto-generated constructor stub
	}
}
