package edu.nju.opsource.dangcongsan.linktype;

import edu.nju.opensource.common.beans.Site;

public class Culture extends News {
	public Culture(String linkTypeName) {
		super.setLinkTypeName(linkTypeName);
	}

	public Culture() {
		super.setLinkTypeName("dangcongsan.culture");
	}

	public Culture(Site site) {
		// TODO Auto-generated constructor stub
	}

}
