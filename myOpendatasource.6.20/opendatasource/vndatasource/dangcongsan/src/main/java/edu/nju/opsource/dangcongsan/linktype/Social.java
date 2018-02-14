package edu.nju.opsource.dangcongsan.linktype;

import edu.nju.opensource.common.beans.Site;

public class Social extends News {
	public Social(String linkTypeName) {
		super.setLinkTypeName(linkTypeName);
	}

	public Social() {
		super.setLinkTypeName("dangcongsan.social");
	}

	public Social(Site site) {
		// TODO Auto-generated constructor stub
	}

}
