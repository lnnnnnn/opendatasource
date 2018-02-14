package org.framework.test;

import org.junit.Test;

import com.onezetta.downloader.PhantomjsDownloader;

import junit.framework.TestCase;

public class TestPhantomjsDownloader extends TestCase{
	@Test
	public void testDownloader(){
		PhantomjsDownloader downloader = new PhantomjsDownloader("http://www.msn.com/en-ph/news/national/");
		System.out.println(downloader.run());
	}
}
