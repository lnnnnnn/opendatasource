package edu.nju.opsource.philstar;

import org.junit.Test;

import edu.nju.opensource.common.action.OpenDataSource;
import edu.nju.opensource.common.beans.Link;
import edu.nju.opensource.common.beans.Site;
import junit.framework.TestCase;

public class TestOpenDataSource extends TestCase{
	@Test
	public void testOpenDataSource(){
		OpenDataSource ds = new PhilStarDataOpenSource("opensource.xml");
		try {
			ds.init_Envirment();
			ds.init_Sites();
			Site site = ds.getSites().get("06f6884c-9fd5-4346-aaa7-3e7316eb7865");
			
			assertNotNull(site);
			Link link = site.getUrl();
			link.setSite(site);
			site.getUrl().getLinkType().get( link.getUrl() );
			site.getUrl().getLinkType().handle(link);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
