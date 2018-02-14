package org.framework.test;

import java.io.File;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.onezetta.abenablebean.util.FileUtil;
import com.onezetta.solr.Data;
import com.onezetta.solr.SolrInterface;
import com.onezetta.solr.SolrSearcher;

import edu.nju.opensource.common.beans.ELingual;
import junit.framework.TestCase;

public class TestSolrDats extends TestCase{
	
	public void testData(){
		String file = "E:/philstar.business.column2016.07.02[12.00].2412.json";
		String jsonStr = FileUtil.getFileContent(file);
		SolrInterface.postSolr(SolrInterface.addDocInJsonStr(jsonStr), ELingual.CHN);
		/*
		String[] field = {"content"};
		String[] keywords = {"天啊"};
		
		SolrSearcher.postSeach(field, keywords, ELingual.CHN);*/
	}
}
