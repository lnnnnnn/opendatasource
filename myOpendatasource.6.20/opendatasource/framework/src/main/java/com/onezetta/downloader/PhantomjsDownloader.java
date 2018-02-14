package com.onezetta.downloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import com.onezetta.abenablebean.util.FileUtil;
import com.onezetta.abenablebean.util.OSJudge;
import com.onezetta.hdfs.HdfsConfiguration;

import edu.nju.opensource.common.action.OpenDataSource;

public class PhantomjsDownloader {
	private String url;
	private static String phantomjsExePath;
	private static String phantomjsFilePath;
	
	static Properties p = null;

    static{
        p = new Properties();
        InputStream in = HdfsConfiguration.class.getResourceAsStream("/setting.properties");
        try {
            p.load(in);
            if( OSJudge.isWin() ){
            	PhantomjsDownloader.phantomjsExePath = p.getProperty("WIN_PHANTOMJS_PATH");
                PhantomjsDownloader.phantomjsFilePath = p.getProperty("WIN_PHANTOMJS_JS_PATH");
    		}else{
    			PhantomjsDownloader.phantomjsExePath = p.getProperty("LINUX_PHANTOMJS_PATH");
                PhantomjsDownloader.phantomjsFilePath = p.getProperty("LINUX_PHANTOMJS_JS_PATH");
    		}
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public PhantomjsDownloader(String url){
		this.url = url;
		
			
	}
	
	public String run(){
		Process process = null;
		StringBuffer sb = new StringBuffer();
		String output = "";
		String curThreadHashCode = "";
		try{
			curThreadHashCode = Math.abs(Thread.currentThread().hashCode()) + "";  
			String execCmd = PhantomjsDownloader.phantomjsExePath 
							 + " " + PhantomjsDownloader.phantomjsFilePath 
							 + " " + url 
							 + " " + OpenDataSource.getTmpDataDir() + curThreadHashCode;
			
			process = Runtime.getRuntime().exec( execCmd );
			BufferedReader input= new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = "";
			while((line = input.readLine()) != null){
				sb.append(line);
			}
			output = sb.toString();
			
			input.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		if( output.indexOf("SUCCESS") != -1)
			return FileUtil.getFileContent(OpenDataSource.getTmpDataDir() + curThreadHashCode);
		else 
			return null;		
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPhantomjsExePath() {
		return phantomjsExePath;
	}

	public void setPhantomjsExePath(String phantomjsExePath) {
		this.phantomjsExePath = phantomjsExePath;
	}

	public String getPhantomjsFilePath() {
		return phantomjsFilePath;
	}

	public void setPhantomjsFilePath(String phantomjsFilePath) {
		this.phantomjsFilePath = phantomjsFilePath;
	}

	public static void main(String[] args){
		PhantomjsDownloader downloader = new PhantomjsDownloader("http://www.msn.com/en-ph/news/national/");
		System.out.println(downloader.run());
	}
	
}
