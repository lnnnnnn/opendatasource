package edu.nju.opsource.dangcongsan.linktype;

import edu.nju.opensource.common.beans.Site;

public class Overseas extends News {
	public Overseas(String linkTypeName) {
		super.setLinkTypeName(linkTypeName);
	}

	public Overseas() {
		super.setLinkTypeName("dangcongsan.overseas");
	}

	public Overseas(Site site) {
		// TODO Auto-generated constructor stub
	}

}