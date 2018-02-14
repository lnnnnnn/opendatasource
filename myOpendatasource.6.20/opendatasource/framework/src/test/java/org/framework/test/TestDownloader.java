package org.framework.test;

import org.junit.Test;

import com.onezetta.abenablebean.util.FileUtil;
import com.onezetta.downloader.EasyHttpDownloader;

import junit.framework.TestCase;

public class TestDownloader extends TestCase{
	@Test
	public void testDownloader(){
		String url = "http://www.msn.com/en-ph/news/national/enrile-backs-shift-to-federal-government/ar-AAhh9HV";
		String content = new EasyHttpDownloader(url).run();
		FileUtil.writeStringToFile(content, "e:/test.html");
	}
}
