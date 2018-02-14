package com.onezetta.downloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.onezetta.abenablebean.util.GetPath;

public class GetImage {
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		/*String url = "https://img.alicdn.com/bao/uploaded/i1/TB1F.QOLVXXXXXGXXXXXXXXXXXX_!!0-item_pic.jpg";
		byte[] btImg = getImageFromNetByUrl(url);
		if(null != btImg && btImg.length > 0){
			System.out.println("读取到：" + btImg.length + " 字节");

			String uri = GetPath.getJARPathWithOutSplit() + "/test.jpg";
			System.out.println( uri );
			writeImageToDisk(btImg, uri);
		}else{
			System.out.println("没有从该连接获得内容");
		}*/
		String uri="https://img.alicdn.com/bao/uploaded/i1/TB1F.QOLVXXXXXGXXXXXXXXXXXX_!!0-item_pic.jpg";
		downloadImage(uri,"E:\\testGetImg\\"+"pic.jpg");

	}
	
	/**
	 * 将图片写入到磁盘
	 * @param img 图片数据流
	 * @param fileName 文件保存时的名称
	 */
	public static boolean writeImageToDisk(byte[] img, String fileName){
		try {
			File file = new File(fileName);
			FileOutputStream fops = new FileOutputStream(file);
			fops.write(img);
			fops.flush();
			fops.close();
			//System.out.println("图片已经写入到C盘");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 根据地址获得数据的字节流
	 * @param strUrl 网络连接地址
	 * @return
	 */
	public static byte[] getImageFromNetByUrl(String strUrl){
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
			byte[] btImg = readInputStream(inStream);//得到图片的二进制数据
			return btImg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 从输入流中获取数据
	 * @param inStream 输入流
	 * @return
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream inStream) throws Exception{
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while( (len=inStream.read(buffer)) != -1 ){
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}

	public static final int NetworkError = -1;
	public static final int WriteFileError = -2;
	public static final int Success = 1;

	public static int downloadImage( String url, String outputImageFileName ){
		byte[] btImg = getImageFromNetByUrl(url);
		if(null != btImg && btImg.length > 0){
			if(writeImageToDisk( btImg, outputImageFileName))
				return Success;
			else
				return WriteFileError;
		}else
			return NetworkError;
	}
	public static byte[] getImageFromNetByUrl(String strUrl, int fileSizeLimit){
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			conn.setReadTimeout( 5 * 1000 );
			int fileLength = conn.getContentLength();
			
			if( fileLength >= fileSizeLimit ){
				InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
				byte[] btImg = readInputStream(inStream);//得到图片的二进制数据
				return btImg;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static int downloadImage( String url, String outputImageFileName, int fileSizeLimit ){
		byte[] btImg = getImageFromNetByUrl(url, fileSizeLimit);
		if(null != btImg && btImg.length > 0){
			if(writeImageToDisk( btImg, outputImageFileName))
				return Success;
			else
				return WriteFileError;
		}else
			return NetworkError;
	}
}