package edu.nju.opsource.vietnamplus.linktype;

import edu.nju.opensource.common.beans.Site;

public class LinkType_Culture extends LinkType_Politics {
	public LinkType_Culture(String linkTypeName) {
		super.setLinkTypeName(linkTypeName);
	}

	public LinkType_Culture() {
		super.setLinkTypeName("vietnamplus.culture");
	}

	public LinkType_Culture(Site site) {
		// TODO Auto-generated constructor stub
	}
}
