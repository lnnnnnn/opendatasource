package edu.nju.opsource.vietnamplus;

import java.io.File;

import com.onezetta.abenablebean.util.DBTableCheckor;
import com.onezetta.abenablebean.util.DBTableInitor;
import com.onezetta.abenablebean.util.FileUtil;

import edu.nju.opensource.common.action.OpenDataSource;
import edu.nju.opsource.vietnamplus.data.Data_Graph;

public class VietnamplusOpenDataSource extends OpenDataSource{
	
	public static final String imagePath = "e:/opensource_data/images/";
	/**
	 * @param configXml
	 */
	/**
	 * @param configXml
	 */
	public VietnamplusOpenDataSource(String configXml) {
		// TODO Auto-generated constructor stub
		super(configXml);
	}
	public void init_Envirment()  throws Exception{
		super.init_Envirment();
		boolean LinkTableIsExist = new DBTableCheckor(Data_Graph.class).isTableExist(new Data_Graph().getTableName());
		if (!LinkTableIsExist) new DBTableInitor(Data_Graph.class, new Data_Graph());
		if (!new File( imagePath ).exists()) FileUtil.createDirectory("", imagePath ); 

	}
	public static void main(String[] args){
		VietnamplusOpenDataSource ds = new VietnamplusOpenDataSource("opensource.xml");
		try {
			ds.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
