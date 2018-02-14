package org.framework.test;


import org.junit.Test;

import com.onezetta.abenablebean.util.DBTableInitor;
import com.onezetta.dbablebean.dbenablebeananntation.SP;
import com.onezetta.dbenablebean.DBEnableBean;

import junit.framework.TestCase;

@SP(
		table="test", 
		sps="",
		create = "create table test (" +
				"id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
				"name varchar(255), " +
				"note varchar(255) );"		 
)
class HiBean extends DBEnableBean{
	private String name;
	private String note;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}

public class TestHiBean extends TestCase{
	@Test
	public void testHiBeanInitor(){
		HiBean h = new HiBean();
		new DBTableInitor(HiBean.class, h);
	}
}
