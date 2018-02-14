package com.onezetta.downloader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;

public class EasyHttpsDownloader {
	String encode = "";
	boolean isGzip;
	public static final String CHARSET_GBK =  "GBK";
	public static final String CHARSET_UTF8 =  "UTF-8";
	public static final int SOCKETTIMEOUT = 30000;
	public static final int CONNECTTIMEOUT = 30000;
	private String url;
	
	public EasyHttpsDownloader(String url) {
		this.encode = "UTF-8";
		this.isGzip = false;
		this.url = url;
	}

	public EasyHttpsDownloader(String url, boolean isGzip) {
		this.encode = "UTF-8";
		this.isGzip = isGzip;
		this.url = url;
	}

	public EasyHttpsDownloader(String url, String encode) {
		this.encode = encode;
		this.isGzip = false;
		this.url = url;
	}

	public boolean downloadImage(String fileName ){
		HttpClient client = getNewHttpClient();
		//CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpGet httpGet = isGzip ? getGzipHttpGet() : getNormalHttpGet();
		HttpResponse response = null;

		try {
			response = client.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				byte[] respBytes = EntityUtils.toByteArray(entity);

				if( fileName.indexOf("?") != -1)
					fileName = fileName.substring(0, fileName.indexOf("?"));
				File file = new File( fileName );
				FileOutputStream fops = new FileOutputStream(file);
				fops.write( respBytes );
				fops.flush();
				fops.close();

				return true;
			}

			} catch (ClientProtocolException e) {
				e.printStackTrace();

			} catch (IOException e) {
				e.printStackTrace();

			} finally{
			/*
			if( response != null )
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}*/
				System.out.println( this.getUrl());
			}
			return false;
	}

	public String run(){
		HttpClient client = getNewHttpClient();
		//CloseableHttpClient client = HttpClientBuilder.create().build(); 
		HttpGet httpGet = isGzip ? getGzipHttpGet() : getNormalHttpGet();
		HttpResponse response = null;
		
		try {
			response = client.execute(httpGet);
		    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
		    	HttpEntity entity = response.getEntity();
		    	String respContent = EntityUtils.toString(entity, this.encode).trim();
			    return respContent;
		    }
		   
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
			
		} finally{
			/*
			if( response != null )
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}*/
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
	
	public HttpClient getNewHttpClient() {
	    try {
	        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
	        trustStore.load(null, null);

	        SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
	        sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

	        HttpParams params = new BasicHttpParams();
	        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

	        SchemeRegistry registry = new SchemeRegistry();
	        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
	        registry.register(new Scheme("https", sf, 443));

	        ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

	        return new DefaultHttpClient(ccm, params);
	    } catch (Exception e) {
	        return new DefaultHttpClient();
	    }
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public static void main(String[] args){
		String url = "https://img.alicdn.com/imgextra/i3/1765328414/TB2DrVajFXXXXcrXpXXXXXXXXXX_!!1765328414.jpg";//"https://dsc.taobaocdn.com/i8/430/890/43289483628/TB1LM0rJpXXXXbdXVXX8qtpFXlX.desc%7Cvar%5Edesc%3Bsign%5E376a93b3da79090eed4624d2cd70f147%3Blang%5Egbk%3Bt%5E1441116737";
		EasyHttpsDownloader  e = new EasyHttpsDownloader(url);
		System.out.println( e.downloadImage("e:/titleimageSS.jpg") );
	}
	

}
