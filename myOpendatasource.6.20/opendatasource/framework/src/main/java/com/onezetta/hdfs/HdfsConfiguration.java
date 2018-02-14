package com.onezetta.hdfs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by hj on 2016/5/31.
 */

public class HdfsConfiguration {

    static Properties p = null;

    static{
        p = new Properties();
        InputStream in = HdfsConfiguration.class.getResourceAsStream("/setting.properties");
        try {
            p.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getHdfsMasterName(){
        return p.getProperty( "HDFSMASTERNAME" );
    }

    public static String getHdfsTestPath(){
        return p.getProperty( "HDFSTESTPATH" );
    }
    
    public static String getHdfsDataPath(){
        return p.getProperty( "HDFSDATAPATH" );
    }

    public static String getHBaseMaster(){
        return p.getProperty( "HBASE.MASTER" );
    }
    
    public static String getHBaseClienPort(){
        return p.getProperty( "HBASE.ZOOKEEPER.CLIENTPORT" );
    }
    
    public static String getHBaseZooKeeperQuorum(){
        return p.getProperty( "HBASE.ZOOKEEPER.QUORUM" );
    }
    
    public static String getCrawledRawDataTableName(){
        return p.getProperty( "CRAWLEDDATATABLE" );
    }
    
    public static String getHbaseTestUrl(){
        return p.getProperty( "HBASE.TESTURL" );
    }
    public static String getVpnTestUrl(){
        return p.getProperty( "VPN.TESTURL" );
    }
    public static String getSeleniumDrvPath(){
        return p.getProperty( "WIN_SELENIUM_DRV_PATH" );
    }
}
