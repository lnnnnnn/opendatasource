package edu.nju.opsource.philstar;

import org.junit.Before;
import org.junit.Test;

import edu.nju.opensource.common.action.OpenDataSource;
import edu.nju.opensource.common.beans.Site;
import junit.framework.TestCase;

public class TestSite extends TestCase{
	private OpenDataSource ds = null;
	private String siteUUID = "ea7956c6-30a7-11e6-ac61-9e71128cae77";
	
	@Before
	public void init(){
		ds = new PhilStarDataOpenSource("opensource.xml");
	}
	
	@Test
	public void testSite(){
		Site site = ds.getSites().get( siteUUID );
		String linkTypeName = site.getUrl().getLinkTypeName();
		
	}

}
