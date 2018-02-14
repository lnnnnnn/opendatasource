package com.onezetta.solr;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import edu.nju.opensource.common.beans.ELingual;

public class SolrSearcher {
	public static final String SOLR_EN_URL = "http://192.168.1.110:8983/solr/opendatasource";
	public static final String SOLR_CN_URL = "http://192.168.1.110:8983/solr/opendatasource_cn_jp_kor";
	
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
	
	public static void postSeach( String[] fields, String[] keywords, ELingual lingual ) {
		
		if( "".equals(getPostUrl(lingual))) return;
		SolrClient solr = new HttpSolrClient(getPostUrl(lingual));
        try {
        	SolrQuery query = new SolrQuery();
        	for(int i=0; i<fields.length; i++){
        		query.add("q", fields[i] + ":" + keywords[i]);
        	}
        	
        	QueryResponse response = solr.query(query);
        	long numFound = response.getResults().getNumFound();
        	System.out.println("has Found : " + numFound);
        	ListIterator<SolrDocument> iter = response.getResults().listIterator();
            while (iter.hasNext()) {
            	
                SolrDocument doc = iter.next();
                Map<String, Collection<Object>> values = doc.getFieldValuesMap();

                Iterator<String> names = doc.getFieldNames().iterator();
                while (names.hasNext()) {
                    String name = names.next();
                    System.out.print(name);
                    System.out.print(" = ");

                    Collection<Object> vals = values.get(name);
                    Iterator<Object> valsIter = vals.iterator();
                    while (valsIter.hasNext()) {
                        Object obj = valsIter.next();
                        System.out.println(obj.toString());
                    }
                }
            }
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
    }
}
