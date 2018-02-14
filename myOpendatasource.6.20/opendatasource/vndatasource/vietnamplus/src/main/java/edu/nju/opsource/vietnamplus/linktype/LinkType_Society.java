package edu.nju.opsource.vietnamplus.linktype;

import edu.nju.opensource.common.beans.Site;

public class LinkType_Society extends LinkType_Politics {
	public LinkType_Society(String linkTypeName) {
		super.setLinkTypeName(linkTypeName);
	}

	public LinkType_Society() {
		super.setLinkTypeName("vietnamplus.society");
	}

	public LinkType_Society(Site site) {
		// TODO Auto-generated constructor stub
	}
}
