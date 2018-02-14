package com.onezetta.solr;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.nju.opensource.common.beans.ELingual;

public class SolrInterface {
	public static final String SOLR_EN_URL = "http://192.168.1.8:8983/solr/open_ds_en/update";
	public static final String SOLR_CN_URL = "http://192.168.1.8:8983/solr/open_ds_cn/update";
	
	@SuppressWarnings("incomplete-switch")
	public static String getPostUrl(ELingual elingual){
		String rst = "";
		switch( elingual ){
			case ENG :
				rst = SOLR_EN_URL;
				break;
			case CHN:
				rst = SOLR_CN_URL;
				break;				
		}
		return rst;
	}
	
	public static void postSolr( String jsonStr, ELingual lingual ) {
		
		if( "".equals(getPostUrl(lingual))) return;
		
		System.out.println("request Text is " + jsonStr);
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost( getPostUrl(lingual)  );
            StringEntity entity  = new StringEntity(jsonStr, "UTF-8");
            entity.setContentType("application/json");
 
            post.setEntity(entity);                
            HttpResponse response = httpClient.execute(post);
            HttpEntity httpEntity = response.getEntity();
            InputStream in = httpEntity.getContent();
 
            String encoding = httpEntity.getContentEncoding() == null ? "UTF-8" : httpEntity.getContentEncoding().getName();
            encoding = encoding == null ? "UTF-8" : encoding;
            String responseText = IOUtils.toString(in, encoding);
            System.out.println("response Text is " + responseText);
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
    }
	
	public static String addDocInJsonStr(String jsonStr){
		JsonObject obj =  new JsonParser().parse(jsonStr).getAsJsonObject();
		if( obj == null ) return null;
		
		JsonObject dataObj =  new Data().convertToSolrDataJsonObj( jsonStr );
		if( dataObj == null ) 	return null;
		
		JsonObject docJsonObj = new JsonObject();
		docJsonObj.add("doc", dataObj);
		
		JsonObject addIndexObj = new JsonObject();
		addIndexObj.add("add", docJsonObj);
		return new Gson().toJson(addIndexObj);
	}
	
 
}
