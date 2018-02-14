package com.onezetta.hdfs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan; 

public class HBaseUtil {
	
	public static Configuration configuration; 
    static { 
        configuration = HBaseConfiguration.create(); 
        configuration.set("hbase.zookeeper.property.clientPort", HdfsConfiguration.getHBaseClienPort()); 
        configuration.set("hbase.zookeeper.quorum", HdfsConfiguration.getHBaseZooKeeperQuorum()); 
        configuration.set("hbase.master", HdfsConfiguration.getHBaseMaster()); 
    } 
    
    @SuppressWarnings("deprecation")
	public static void createTable(String tableName, String[] columnFamilies) { 
        try { 
            HBaseAdmin hBaseAdmin = new HBaseAdmin(configuration); 
            if (hBaseAdmin.tableExists(tableName)) {
                return;
            } 
            
            HTableDescriptor tableDescriptor = new HTableDescriptor(tableName); 
            for(String columnFamily : columnFamilies){
            	 tableDescriptor.addFamily(new HColumnDescriptor( columnFamily )); 
            }
           
            hBaseAdmin.createTable(tableDescriptor); 
        } catch (MasterNotRunningException e) {  
            e.printStackTrace(); 
        } catch (ZooKeeperConnectionException e) { 
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
    } 
    
 
    public static void dropTable(String tableName) { 
        try { 
            HBaseAdmin admin = new HBaseAdmin(configuration); 
            admin.disableTable(tableName); 
            admin.deleteTable(tableName); 
        } catch (MasterNotRunningException e) { 
            e.printStackTrace(); 
        } catch (ZooKeeperConnectionException e) { 
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
 
    } 
    
    public static void deleteRow(String tablename, String rowkey)  { 
        try { 
            HTable table = new HTable(configuration, tablename); 
            List list = new ArrayList(); 
            Delete d1 = new Delete(rowkey.getBytes()); 
            list.add(d1); 
            table.delete(list); 
             
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
    } 
    
    public static ResultScanner  QueryAll(String tableName) { 
        HTablePool pool = new HTablePool(configuration, 1000); 
        HTable table = (HTable) pool.getTable(tableName); 
        try { 
            ResultScanner rs = table.getScanner(new Scan()); 
            return rs;
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
        return null;
    } 
}
