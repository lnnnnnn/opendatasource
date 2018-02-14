package edu.nju.opsource.nhandan;

import org.apache.commons.io.IOUtils;   

import java.io.*;   
   
/**  
 * Created with IntelliJ IDEA.  
 * User: lsz  
 * Date: 14-4-22  
 * Time: 下午1:17  
 * utils for http  
 */   
public class HttpUtils {   
    public static String getAjaxCotnent(String url) throws IOException {   
        Runtime rt = Runtime.getRuntime(); 
       // Process p = rt.exec("phantomjs.exe d:/phantomjsFile/code.js "+url);
        Process p = rt.exec("phantomjs.exe D:/phantomjsFile/crawl2.js ");//这里我的codes.js是保存在c盘下面的phantomjs目录   
        InputStream is = p.getInputStream();   
        BufferedReader br = new BufferedReader(new InputStreamReader(is));   
        StringBuffer sbf = new StringBuffer();   
        String tmp = "";   
        while((tmp = br.readLine())!=null){   
            sbf.append(tmp);   
        }   
        System.out.println(sbf.toString());   
        return sbf.toString();   
    }   
   
    public static void main(String[] args) throws IOException {   
        getAjaxCotnent("");   
    }   
}   
