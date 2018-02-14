package com.onezetta.hdfs;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapFile;

import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

public class MapFileOperator {
	public static final int INTWRITABLE = 1;
	public static final int TEXT = 2;
	public static final int BYTESWRITABLE = 3;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void write(Object key, Object value, String fileDirPath) throws IOException {  
        Configuration conf = new Configuration();  
        URI uri = URI.create( fileDirPath );
        Path path = new Path( uri );
        
        Writable keyWritable = null;
        if( key instanceof Integer){
        	keyWritable = new IntWritable((Integer) key);  
        }else if( key instanceof String ){
        	keyWritable = new Text((String) key);  
        }
        
        Writable valueWritable = null;
        if( value instanceof Integer){
        	valueWritable  = new IntWritable((Integer) value);  
        }else if( value instanceof String){
        	valueWritable  = new Text((String) value); 
        }else if( value instanceof byte[]){
        	valueWritable = new BytesWritable((byte[]) value);	
        }
        
        org.apache.hadoop.io.MapFile.Writer.Option keyClass = MapFile.Writer.keyClass( (Class<? extends WritableComparable>) keyWritable.getClass() );
        org.apache.hadoop.io.SequenceFile.Writer.Option valueClass = SequenceFile.Writer.valueClass( valueWritable.getClass());
        
        MapFile.Writer writer = new MapFile.Writer(conf, path, keyClass, valueClass, MapFile.Writer.compression(SequenceFile.CompressionType.NONE));
        writer.append((WritableComparable)keyWritable, valueWritable);
        IOUtils.closeStream(writer);  
    }  
   
	
	@SuppressWarnings("rawtypes")
	public static Object get(String fileDirPath, Object key, Class<?> valueClass) throws IOException {  
		int valueClassFlag = -1;
		Configuration conf = new Configuration();  
		URI uri = URI.create( fileDirPath );
		Path path = new Path( uri );

		Writable keyWritable = null;
        if( key instanceof Integer){
        	keyWritable = new IntWritable((Integer) key);  
        }else if( key instanceof String ){
        	keyWritable = new Text((String) key);  
        }
        
        Writable valueWritable = null;
        if( valueClass.equals(Integer.class)){
        	valueWritable  = new IntWritable();  
        	valueClassFlag = MapFileOperator.INTWRITABLE;
        }else if( valueClass.equals(String.class) ){
        	valueClassFlag = MapFileOperator.TEXT;
        	valueWritable  = new Text(); 
        }else if( valueClass.equals(byte[].class) ){
        	valueClassFlag = MapFileOperator.BYTESWRITABLE;
        	valueWritable = new BytesWritable();	
        }
		MapFile.Reader reader = new MapFile.Reader(path, conf);
		reader.get((WritableComparable)keyWritable, valueWritable);
		
		Object rst = null;
		switch(valueClassFlag){
			case MapFileOperator.BYTESWRITABLE:
				rst = ((BytesWritable)valueWritable).copyBytes();
				break;
			case MapFileOperator.TEXT:
				rst = ((Text)valueWritable).toString();
				break;
			case MapFileOperator.INTWRITABLE:
				rst = ((IntWritable)valueWritable);
				break;				
		}
		IOUtils.closeStream(reader); 
		return rst;
	}
}
