package edu.nju.opsource.dangcongsan.linktype;

import edu.nju.opensource.common.beans.Site;

public class Activities extends News {
	public Activities(String linkTypeName) {
		super.setLinkTypeName(linkTypeName);
	}

	public Activities() {
		super.setLinkTypeName("dangcongsan.activities");
	}

	public Activities(Site site) {
		// TODO Auto-generated constructor stub
	}

}
