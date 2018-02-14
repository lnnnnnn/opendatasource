package com.onezetta.hdfs;

import java.io.File;
import java.io.IOException;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import com.onezetta.abenablebean.util.FileUtil;

public class CrawledRawDataTable extends HBaseUtil{
	private static final String[] columns = {"rawZipFile"};
	private static final String[] prefixs = {"fileData"};
	private HTable rawDataTable = null;
	
	public CrawledRawDataTable(){
		super.createTable(HdfsConfiguration.getCrawledRawDataTableName(), columns);
		try {
			HConnection connection = HConnectionManager.createConnection( super.configuration );
			rawDataTable = new HTable(super.configuration, HdfsConfiguration.getCrawledRawDataTableName() );
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void insert(String rowKey, String zipFilePath){
		if( rawDataTable==null || !new File(zipFilePath).exists() )
			return ;
		
		try {
			Put p = new Put(Bytes.toBytes(rowKey));			
			byte[] fileBytes = FileUtil.toByteArray(zipFilePath);
			
			p.add(Bytes.toBytes(columns[0]), Bytes.toBytes(prefixs[0]), fileBytes);
			rawDataTable.put(p);
			rawDataTable.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public byte[] get(String rowKey){
		if( rawDataTable !=null ){
			Get g = new Get(Bytes.toBytes(rowKey));
			g.addColumn(
					Bytes.toBytes(columns[0]), 
					Bytes.toBytes(prefixs[0])
			);
			try {
				Result r = rawDataTable.get(g);
				return r.getValue(
						Bytes.toBytes(columns[0]), 
						Bytes.toBytes(prefixs[0]));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;					
	}
}
