package edu.nju.opsource.dangcongsan.linktype;

import edu.nju.opensource.common.beans.Site;

public class Science  extends News {
	public Science(String linkTypeName) {
		super.setLinkTypeName(linkTypeName);
	}

	public Science() {
		super.setLinkTypeName("dangcongsan.science");
	}

	public Science(Site site) {
		// TODO Auto-generated constructor stub
	}

}
