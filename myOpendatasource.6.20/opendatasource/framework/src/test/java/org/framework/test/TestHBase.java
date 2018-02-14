package org.framework.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.onezetta.abenablebean.util.FileUtil;
import com.onezetta.hdfs.CrawledRawDataTable;

import edu.nju.opensource.common.action.OpenDataSource;
import junit.framework.TestCase;

public class TestHBase extends TestCase{
	/*
	@Test
	public void ctestTable(){
		new CrawledRawDataTable().insert(
				"philstar.business2015.10.29[12.00].10664.zip", 
				"e:/opensource_data/unupload/philstar.business2015.10.29[12.00].10664.zip");
	}
	
	public void testGetValue(){
		try {
			FileUtil.byteArraytoFile(
					"e:/text-new.zip", 
					new CrawledRawDataTable().get("philstar.business2015.10.29[12.00].10664.zip")
			);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
	@Test
	public void testfeedDog(){
		  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  String cxt = format.format(new Date()) + "\t" +  ((false) ? "RUN": "END");
		  
		  System.out.println( cxt );
		  String feedFile =  OpenDataSource.DOGFEED_FILE ;
		  
	      try {
	    	    	  
	    	  FileWriter fileWritter = new FileWriter( feedFile );
	    	  fileWritter.write( cxt );
	    	  fileWritter.flush();
	          fileWritter.close();
	          
	      } catch (IOException e) {
	    	  e.printStackTrace();
	      }
	  }
	
}
