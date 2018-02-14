package edu.nju.opsource.dangcongsan.data;

import com.onezetta.abenablebean.util.DBTableClear;
import com.onezetta.abenablebean.util.DBTableInitor;
import com.onezetta.abenablebean.util.Str2MD5;
import com.onezetta.dbablebean.dbenablebeananntation.SP;
import com.onezetta.dbenablebean.DBEnableBean;

@SP(
		table="graph",
		sps="getByUrlMD5 : select * from {tableName} where urlMD5={urlMD5};", 
		create="create table graph ("
			+ "id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY, "
			+ "url text, "
			+ "urlMD5 varchar(255),"
			+ "file varchar(255)"
		+ ");"
	)
public class Data_Graph extends DBEnableBean{
	private String url;
	private String urlMD5;
	private String file;
	
	public String getUrl() {
		return url;
	}
	public Data_Graph setUrl(String url) {
		this.url = url;
		return this;
	}
	public String getUrlMD5() {
		return urlMD5;
	}
	public Data_Graph setUrlMD5(String urlMD5) {
		this.urlMD5 = urlMD5;
		return this;
	}
	public String getFile() {
		return file;
	}
	public Data_Graph setFile(String file) {
		this.file = file;
		return this;
	}
	
	public String getUrlMD5(String url){
		return Str2MD5.MD5(url, 32);
	}
	public static void main(String[] args){
		Data_Graph graph = (Data_Graph)new Data_Graph();
		graph.setFile("fdfa").setUrl("fdsaf").setUrlMD5("fdfs");
		//new DBTableInitor(Data_Graph.class, graph);
		new DBTableClear(Data_Graph.class);
	}
}
