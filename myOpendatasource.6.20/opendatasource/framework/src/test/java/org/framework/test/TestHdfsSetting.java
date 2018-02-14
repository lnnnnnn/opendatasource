package org.framework.test;

import org.junit.Test;

import com.onezetta.hdfs.HdfsConfiguration;

import edu.nju.opensource.common.beans.ELingual;
import junit.framework.TestCase;

public class TestHdfsSetting extends  TestCase{
	
	@Test
	public void ctestHdfsSetting(){
		String mastername = HdfsConfiguration.getHdfsMasterName();
		System.out.println( mastername );
	}
	
	public void test(){
		System.out.println( ELingual.ENG.getIndex() );;
	}
}
