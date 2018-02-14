package org.framework.test;

import java.io.IOException;

import org.junit.Test;

import com.onezetta.abenablebean.util.FileUtil;
import com.onezetta.hdfs.HdfsConfiguration;
import com.onezetta.hdfs.MapFileOperator;

import junit.framework.TestCase;

public class MapFileOperatorTest extends TestCase{
	
	@Test
	public void testWriter(){
		String fileDirPath = HdfsConfiguration.getHdfsTestPath() + "test.map";
		String[] srcFilePath = {
					"E:/opensource_data/unupload/philstar.nation2016.06.21[12.00].4223.zip",
					"E:/opensource_data/unupload/philstar.nation2016.06.21[12.00].4229.zip"
		};
		try {
			for(int i=0; i<srcFilePath.length; i++){
				byte[] test = FileUtil.toByteArray(srcFilePath[i]);
				MapFileOperator.write("test_" + i, test, fileDirPath);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	@Test
	public void testReader(){
		String fileDirPath = HdfsConfiguration.getHdfsTestPath() + "test.map";
		String srcFilePath = "E:/opensource_data/test.zip";
		try {
			byte[] rst = (byte[])MapFileOperator.get(fileDirPath, "secTest", byte[].class);
			FileUtil.byteArraytoFile(srcFilePath, rst);
			System.out.println((String)MapFileOperator.get(fileDirPath, "fTest", String.class));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
}
