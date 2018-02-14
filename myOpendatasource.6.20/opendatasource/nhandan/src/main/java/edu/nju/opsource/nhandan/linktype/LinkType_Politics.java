package edu.nju.opsource.nhandan.linktype;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.onezetta.abenablebean.util.FileUtil;
import com.onezetta.downloader.EasyHttpDownloader;
import com.onezetta.downloader.GetImage;

import edu.nju.opensource.common.action.OpenDataSource;
import edu.nju.opensource.common.beans.ELinkState;
import edu.nju.opensource.common.beans.Link;
import edu.nju.opensource.common.beans.LinkType;
import edu.nju.opensource.common.beans.Site;
import edu.nju.opsource.nhandan.data.Data_Politics;




public class LinkType_Politics extends LinkType{
    
	 private String pageContent = null;
     private Document doc = null;
	 private Data_Politics data = null;
	 
	 
	 
	 public LinkType_Politics(String linkTypeName) {
		      super.setLinkTypeName(linkTypeName);
		    }
		  
		    public LinkType_Politics() {
		      super.setLinkTypeName("nhandan.politics");
		    }
	 public LinkType_Politics(Site site) {
			// TODO Auto-generated constructor stub
		}
	public void get(String url)//赋值给this.doc,this.pageContent
	    {
	      super.get(url);
	      this.pageContent = new EasyHttpDownloader(url).run();
	      if (this.pageContent != null){
	        this.doc = Jsoup.parse(this.pageContent);
			System.out.println( " ... has Crawled.");
		  }
	      else{
	        setState(ELinkState.CRAWLFAILED);
			System.out.println( " ... crawled failed.");
		  }
	    }
	@Override
	public boolean handle(Link link) throws Exception {
		
		 if (getState() == ELinkState.CRAWLFAILED) return false;
		     String tmpDir = OpenDataSource.getTmpDataDir() + getLinkUniqeID(super.getLinkId()) + "/";
		     
		     String dataDir = OpenDataSource.getUnuploadDataDir() + getLinkUniqeID(super.getLinkId()) + ".zip";
		     getDataFromDoc();

			  this.data.setUrl( link.getUrl() );
			  this.data.setSiteUUID( link.getSite().getUuid() );
		 
		     FileUtil.createDirectory("", tmpDir);
		     if (new File(tmpDir).exists()) {
		       FileUtil.writeStringToFile(this.pageContent, tmpDir + getLinkUniqeID(super.getLinkId()) + ".html");
		       FileUtil.writeStringToFile(this.data.toJSON(), tmpDir + getLinkUniqeID(super.getLinkId()) + ".json");
		       GetImage.downloadImage(this.data.getImgUri(), tmpDir+getImgName(this.data.getImgUri()));
		     
		     
		     
		       FileUtil.zipFile(tmpDir, dataDir);
		       setState(ELinkState.CRAWLED);
		       return true;
		     }
		     setState(ELinkState.CRAWLFAILED);
		     return false;
	}
    public String getImgName(String uri){
    	int ind=-1,len=uri.length();
    	for(int i=0;i<len;i++) if(uri.charAt(i)=='/')ind=i;
    	String name=uri.substring(ind+1);
    	return name;
    }
	private void getDataFromDoc() {
		// TODO Auto-generated method stub
		Data_Politics data_Politics = new Data_Politics();
		 
		     Elements paragraph = this.doc.select("div.ndcontent p");
		     StringBuffer content = new StringBuffer();
		     StringBuffer contentWithTag = new StringBuffer();
		     for (Element e : paragraph) {
		       content.append(e.text());
		       contentWithTag.append(e.outerHtml());
		     }
		 
		     data_Politics.setContent(content.toString());
		     data_Politics.setContentWithTag(contentWithTag.toString());
		 
		     data_Politics.setDate(getLinkDate() == null ? new Date() : getLinkDate());
		 /*
		  * select
		  */
		     Elements titleE = this.doc.select("div.fontM.ndtitle h3");
		     if ((titleE == null) || (titleE.size() == 0)) data_Politics.setTitle("NULLDATAFIELD"); else {
		    	 data_Politics.setTitle(titleE.first().text());
		     }
		     Elements abstractE = this.doc.select("div.ndcontent.ndb p");
		     if ((abstractE == null) || (abstractE.size() == 0)) data_Politics.setAbstract("NULLDATAFIELD"); else {
		    	 data_Politics.setAbstract(abstractE.first().text());
		     }
		     /*
		      * src
		      */
		     Elements titleImgE = this.doc.select("img.nd_img");
		     if ((titleImgE == null) || (titleImgE.size() == 0)) data_Politics.setImgUri("NULLDATAFIELD"); else {
		    	 data_Politics.setImgUri("http://en.nhandan.com.vn/"+titleImgE.first().attr("src"));
		     }
		     this.data = data_Politics;
	}

	@Override
	public String getLinkTextData() {
		// TODO Auto-generated method stub
		 if (this.pageContent != null) {
			   return this.pageContent;
			 }
			 return null;
	
	}

	@Override
	public InputStream getLinkBinaryData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLinkUniqeID(String linkeId) {
		if (getLinkDate() != null) {
			   SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd[hh.mm]");
			   return getLinkTypeName() + format.format(getLinkDate()) + "." + linkeId;
			 }
			 return null;
	}

	@Override
	public Date getLinkDate() {
		if (this.doc != null) {
			      Element e = this.doc.select("div.icon_date_top div.pull-left").first();
			      String dateStr = e.text();
			      
			      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);
			      Date date = null;
			      try {
			        date = format.parse(dateStr);
			      } catch (ParseException ex) {
			        dateStr = dateStr.substring(dateStr.indexOf(" ") + 1);
			        try {
			          date = format.parse(dateStr);
			        } catch (ParseException e1) {
			          e1.printStackTrace();
			        }
			      }
			      return date;
			    }
			    return null;
	}
	public Data_Politics getData()
	 {
	   return this.data;
	 }
	@Override
	public void setSite(Site paramSite) {
		// TODO Auto-generated method stub
		
	}
	public boolean existsViewMore(String url,String selector){
		boolean exist=true;
		
		String pageContent = new EasyHttpDownloader(url).run();
	      if (pageContent != null){
	        Document doc = Jsoup.parse(pageContent);
	        
	        Elements vm = doc.select(selector);
		     if ((vm == null) || (vm.size() == 0)) exist=false;
	      }
	        
		return exist;
	}
	public static void main(String[] args){
		//String uri="http://en.nhandan.com.vn//cdn/en/media/k2/items/src/450/0fe319afd7fdda82c17be0736a4a8755.jpg";
        //System.out.println((new LinkType_Politics()).getImgName(uri));
		
		/*try {
			String urlStr="http://e.vnexpress.net/news/news?cate_id=1003894&page";
			 URL url = new URL(urlStr);  
			 HttpURLConnection con = (HttpURLConnection) url.openConnection();  
			 int state = con.getResponseCode();  
			// int status=con.getS
			System.out.println(state);  
			 if (state == 200) {  
			  System.out.println("URL可用！");  
			 }
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		String url="http://e.vnexpress.net/news/news?cate_id=1003894&page=79";
		 HttpGet httpGet = new HttpGet(url);
		 CloseableHttpClient client = HttpClientBuilder.create().build();
		 HttpResponse response = null;

	       
	            try {
					response = client.execute(httpGet);
					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						System.out.println("URL可用！");  
						
						String pageContent = new EasyHttpDownloader(url).run();
					      if (pageContent != null){
					    	  //System.out.println("existContent");
					    	  
					    	  Document doc = Jsoup.parse(pageContent);
					    	  
					    	  Elements nextPageE = doc.select("div.info_news_center a");
					          System.out.println(nextPageE.first().text());
					      }
						
					}else{
						System.out.println("bukeyong");
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
		  /* LinkType_Politics lp=new LinkType_Politics();
		   String url="http://e.vnexpress.net/news/news?cate_id=1003894&page=77";
		   String sel="a#vnexpress_folder_load_more";
		   System.out.println(lp.existsViewMore(url, sel));*/
	}
}
