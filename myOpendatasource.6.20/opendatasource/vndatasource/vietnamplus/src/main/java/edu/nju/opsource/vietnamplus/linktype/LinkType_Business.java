package edu.nju.opsource.vietnamplus.linktype;

import edu.nju.opensource.common.beans.Site;

public class LinkType_Business extends LinkType_Politics {
	public LinkType_Business(String linkTypeName) {
		super.setLinkTypeName(linkTypeName);
	}

	public LinkType_Business() {
		super.setLinkTypeName("vietnamplus.business");
	}

	public LinkType_Business(Site site) {
		// TODO Auto-generated constructor stub
	}
}
