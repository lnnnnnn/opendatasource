package com.onezetta.downloader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class EasyHttpDownloader {
    private String encode;
    boolean isGzip;
    public static final String CHARSET_GBK =  "GBK";
    public static final String CHARSET_UTF8 =  "UTF-8";
    public static final int SOCKETTIMEOUT = 30000;
    public static final int CONNECTTIMEOUT = 30000;
    private String url;
    
    private CloseableHttpClient client = HttpClientBuilder.create().build();
    private  HttpGet httpGet; 

    public EasyHttpDownloader(String url) {
        encode = "";
        this.encode = "UTF-8";
        this.isGzip = false;
       // this.url = url;
        this.url=transIllegalChar(url);
        this.httpGet = isGzip ? getGzipHttpGet() : getNormalHttpGet();
    }

    public EasyHttpDownloader(String url, boolean isGzip) {
        encode = "";
        this.encode = "UTF-8";
        this.isGzip = isGzip;
        this.url = url;
        this.httpGet = isGzip ? getGzipHttpGet() : getNormalHttpGet();
        
        
        this.url=transIllegalChar(url);
    }

    
    public static String transIllegalChar(String tmpurl){
    	return tmpurl.replace(" ", "%20");
    	/*StringBuffer url=new StringBuffer(tmpurl);
    	int len=url.length();
        for(int i=0;i<len;i++){
        	switch(url.charAt(i)){
        	case ' ':url.replace(i, i, "%20");
        	         break;
        	}
        	 
        }
      //  System.out.println("transto");
       // System.out.println(url);
        return url.toString();*/
        	 
    	/*
    	    URL 中+号表示空格                                 %2B   
    	空格 URL中的空格可以用+号或者编码           %20 
    	/   分隔目录和子目录                                     %2F     
    	?    分隔实际的URL和参数                             %3F     
    	%    指定特殊字符                                          %25     
    	#    表示书签                                                  %23     
    	&    URL 中指定的参数间的分隔符                  %26     
    	=    URL 中指定参数的值                                %3D*/
    }
    public EasyHttpDownloader(String url, boolean isGzip, String encode) {
        this.encode = "";
        this.encode = encode;
        this.isGzip = isGzip;
       // this.url = url;
        this.url=transIllegalChar(url);
        this.httpGet = isGzip ? getGzipHttpGet() : getNormalHttpGet();
       
    }

    public String run(String cookie){
    	this.httpGet.addHeader("Cookie",cookie); 
    	return run();
    }
    
    public String run(){
        HttpResponse response = null;

        try {
            response = client.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                String respContent = EntityUtils.toString(entity, this.encode).trim();
                if( !this.encode.equals("UTF-8") )
                    respContent = convertToThisCharset( respContent );
                return respContent;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();

        } catch (IOException e) {   
            e.printStackTrace();

        } finally{
			
        }
        return null;
    }

    private HttpGet getNormalHttpGet(){

        HttpGet httpGet = new HttpGet(this.url);
        httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;");
        httpGet.addHeader("Accept-Language", "zh-cn");
        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3");
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKETTIMEOUT).setConnectTimeout(CONNECTTIMEOUT).build();
        httpGet.setConfig(requestConfig);
        return httpGet;

    }

    private HttpGet getGzipHttpGet(){
        HttpGet httpGet = getNormalHttpGet();
        httpGet.addHeader("Accept-Encoding", "gzip, deflate");
        return httpGet;
    }

    private String convertToThisCharset(String rst){
        try{
            String tmp = new String( rst.getBytes(this.encode), "utf-8");
            return tmp;
        }catch (Exception e){
        }

        return null;
    }


    public String getUrl() {
    	
        return url;
    }

    public void setUrl(String url) {
    	
        this.url=transIllegalChar(url);
       
    }

    public static void main(String[] args){
        String url = "http://www.philstar.com/headlines/2016/06/10/1591455/304-colleges-get-nod-hike-tuition";

        EasyHttpDownloader downloader = new EasyHttpDownloader(url, true, "utf-8");
        System.out.println( downloader.run() );
        //System.out.println(transIllegalChar("y x z"));
        
    }

}
