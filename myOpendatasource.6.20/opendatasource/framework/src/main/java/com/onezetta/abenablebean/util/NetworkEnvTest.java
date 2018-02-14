package com.onezetta.abenablebean.util;

import com.onezetta.downloader.EasyHttpDownloader;

public class NetworkEnvTest {
	public static boolean test(String url){
		boolean flag = false;
		try{
			String rst = new EasyHttpDownloader(url).run();
			if( rst != null )
				return true;				
		}catch(Exception e){
			
		}		
		return flag;
	}
}
