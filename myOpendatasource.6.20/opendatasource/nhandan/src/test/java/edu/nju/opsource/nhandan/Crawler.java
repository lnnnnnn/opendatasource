package edu.nju.opsource.nhandan;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;



public class Crawler {
	
	 public static final int SOCKETTIMEOUT = 30000;
	 public static final int CONNECTTIMEOUT = 30000;
	 private String encode="utf-8";
	 private String url;
	 private CloseableHttpClient client = HttpClientBuilder.create().build();
	 private  HttpGet httpGet;
	 private  HttpPost httpPost;
	 private String pageContent = null;
	 private Document doc = null;
	 
	 
	 
	 public String getPageContent() {
		return pageContent;
	}
	public void setPageContent(String pageContent) {
		this.pageContent = pageContent;
	}
	public Document getDoc() {
		return doc;
	}
	public void setDoc(Document doc) {
		this.doc = doc;
	}
	public Crawler(String url) {
		super();
		this.url = url;
	}
	public  HttpGet getNormalHttpGet(){
         HttpGet httpGet = new HttpGet(this.url);
        httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;");
        httpGet.addHeader("Accept-Language", "zh-cn");
        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3");
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKETTIMEOUT).setConnectTimeout(CONNECTTIMEOUT).build();
        httpGet.setConfig(requestConfig);
        return httpGet;

    }
	public  HttpPost getNormalHttpPost(){
	 HttpPost httpPost = new HttpPost(this.url);//创建HttpPost对象  
     
     List <NameValuePair> params = new ArrayList<NameValuePair>();  
     params.add(new BasicNameValuePair("cate_id", "1003894"));  
     params.add(new BasicNameValuePair("page", "2"));  
       
         try {
			httpPost.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     return httpPost;
     }
	 private String convertToThisCharset(String rst){
	        try{
	            String tmp = new String( rst.getBytes(this.encode), "utf-8");
	            return tmp;
	        }catch (Exception e){
	        }

	        return null;
	    }
	 public String runByGet(){
	        HttpResponse response = null;
	        httpGet=getNormalHttpGet();
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
	 public String runByPost(){
	        HttpResponse response = null;
	        httpPost=getNormalHttpPost();
	        try {
	            response = client.execute(httpPost);
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
	 public void get(String url)//赋值给this.doc
	    {
	      this.pageContent = runByGet();
	      if (this.pageContent != null){
	        this.doc = Jsoup.parse(this.pageContent);
			
		  }
	      else{
	        
			System.out.println( " ... crawled failed.");
		  }
	    }
	 
	 public static void testHttpPost(){
		 //String url="https://detail.tmall.com/item.htm?spm=a222t.7794920.fdigt.15.toj3Lg&id=522169321891&skuId=3201261540546&rn=870e70c02239d3cf10b156e9f9aa9e4f&scm=search-api.3c_fp.870e70c02239d3cf10b156e9f9aa9e4f.2";
		 
		 String url="http://e.vnexpress.net/category/loadmorenews";
		 
		 
		 Crawler c=new Crawler(url);
		// String content=c.run();
		 c.setPageContent(c.runByPost())  ;
		// System.out.println(c.getDoc());
		 
		String resData=c.getPageContent();
		System.out.println(resData);
		Gson gson=new Gson();
		InnerResponseByHttpPost res=gson.fromJson(resData,InnerResponseByHttpPost.class);
		
		System.out.println(res);
		Document resDoc=Jsoup.parse(res.getMessage());
		System.out.println(resDoc);
		 Elements hrefs = resDoc.select("h4.title_news_site a");
		 
		 for(Element e : hrefs){
			 String href=e.attr("abs:href");
			 System.out.println(href);
		 }
	    
		 
	 }
	 public static void testHttpGet(){
		 //String url="https://detail.tmall.com/item.htm?spm=a222t.7794920.fdigt.15.toj3Lg&id=522169321891&skuId=3201261540546&rn=870e70c02239d3cf10b156e9f9aa9e4f&scm=search-api.3c_fp.870e70c02239d3cf10b156e9f9aa9e4f.2";
		 
		 String url="http://thestandard.com.ph/api/category/json?page=3&category=1&column=0&totItems=40500&currentItems=16";
		 Crawler c=new Crawler(url);
		// String content=c.run();
		 c.get(url);
		// System.out.println(c.getDoc());
		 
		String resData=c.getPageContent();
		Gson gson=new Gson();
		InnerResponseByHttpGet res=gson.fromJson(resData,InnerResponseByHttpGet.class);
		
		System.out.println(res);
		Document resDoc=Jsoup.parse(res.getData());
		System.out.println(resDoc);
		 Elements hrefs = resDoc.select("div.img-container-masonry a");
		 
		 for(Element e : hrefs){
			 String href=e.attr("abs:href");
			 System.out.println(href);
		 }
	    /* if ((price == null) || (price.size() == 0)) {
	    	 System.out.println("null");
	    	 
	     }else{
	    	 
	    	 System.out.println(price.first().text());
	     }*/
		 
	 }
	 public static void main(String []args){
		 testHttpGet();
	 }
	 public class InnerResponseByHttpGet{
			private String type;
			private boolean lastpage;
			private String data;
			
			
			public String getData() {
				return data;
			}


			public void setData(String data) {
				this.data = data;
			}


			@Override
			public String toString() {
				return "InnerResponse [type=" + type + ", lastpage=" + lastpage + ", data=" + data + "]";
			}
			
			
			
		}
	 
	 public class InnerResponseByHttpPost{
			private int error;
			private String message;
			private boolean end;
			
			
			public String getMessage() {
				return message;
			}


			public void setMessage(String message) {
				this.message = message;
			}
	 }
}
