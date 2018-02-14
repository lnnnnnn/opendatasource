package edu.nju.opsource.dangcongsan;

import java.io.File;

import com.onezetta.abenablebean.util.DBTableCheckor;
import com.onezetta.abenablebean.util.DBTableInitor;
import com.onezetta.abenablebean.util.FileUtil;

import edu.nju.opensource.common.action.OpenDataSource;
import edu.nju.opsource.dangcongsan.data.Data_Graph;

public class DangcongsanOpenDataSource extends OpenDataSource{
	
	public static final String imagePath = "e:/opensource_data/images/";
	/**
	 * @param configXml
	 */
	/**
	 * @param configXml
	 */
	public DangcongsanOpenDataSource(String configXml) {
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
		DangcongsanOpenDataSource ds = new DangcongsanOpenDataSource("opensource.xml");
		try {
			ds.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
