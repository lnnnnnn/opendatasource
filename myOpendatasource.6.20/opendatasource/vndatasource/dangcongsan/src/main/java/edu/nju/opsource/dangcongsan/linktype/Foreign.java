package edu.nju.opsource.dangcongsan.linktype;

import edu.nju.opensource.common.beans.Site;

public class Foreign  extends News {
	public Foreign(String linkTypeName) {
		super.setLinkTypeName(linkTypeName);
	}

	public Foreign() {
		super.setLinkTypeName("dangcongsan.foreign");
	}

	public Foreign(Site site) {
		// TODO Auto-generated constructor stub
	}

}