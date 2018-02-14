package com.onezetta.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

import com.onezetta.abenablebean.util.FileUtil;

import java.io.*;
import java.net.URI;

/**
 * Created by hj on 2016/5/31.
 */
public class HdfsFileOperator {
    private Configuration conf = new Configuration ();

    public boolean createDirWithSplash(String createDirPath) throws IOException {
        URI uri = URI.create ( createDirPath );
        FileSystem fs = FileSystem.get (uri, conf);
        boolean rst = fs.mkdirs ( new Path(uri));
        fs.close();;
        return rst;
    }


    public void saveTextFile(String storePathWithSplash , String file) throws IOException {

        String fileName = "";

        if( file.indexOf("/") != -1)
            fileName = file.substring( file.lastIndexOf("/")+1 );
        else
            fileName = file;

        URI uri = URI.create ( storePathWithSplash + fileName );
        FileSystem fs = FileSystem.get (uri, conf);

        try(FSDataOutputStream fsDataOutputStream = fs.create( new Path(uri) )) {
            fsDataOutputStream.writeChars(FileUtil.getFileContent(file) );

            fsDataOutputStream.close();
        }
        fs.close();
    }

    public boolean saveBinaryFile(String storePathWithSplash , String file){
    	boolean flag;
    	try{
    		URI uri = URI.create ( storePathWithSplash );
            FileSystem fs = FileSystem.get(uri,conf);
            if( !fs.exists(new Path( storePathWithSplash )))  return false;

            String fileName = "";
            if( file.indexOf("/") != -1)
                fileName = file.substring( file.lastIndexOf("/")+1 );
            else
                fileName = file;

            InputStream in = new BufferedInputStream(new FileInputStream(file));

            OutputStream out = fs.create(new Path( storePathWithSplash + fileName ) , new Progressable() {
                public void progress() {
                    System.out.print(".");
                }
            });
            IOUtils.copyBytes(in, out, 4096, true);
            flag = true;
    	}catch(Exception e){
    		flag = false;
    	}
        return flag;
    }

    public void getFile(String fileAbsolutePath , String outfile) throws IOException {

        fileAbsolutePath = HdfsConfiguration.getHdfsMasterName() + fileAbsolutePath.substring(1);

        URI uri = URI.create ( fileAbsolutePath );
        FileSystem fs = FileSystem.get(uri,conf);
        if( !fs.exists(new Path( fileAbsolutePath )))  return ;
        //System.out.print( fileAbsolutePath );
        FSDataInputStream in = fs.open(new Path(uri));

        OutputStream out = new FileOutputStream( new File( outfile ));
        IOUtils.copyBytes(in, out, 4096, true);

    }


}
