

package edu.nju.opsource.nhandan.linktype;

import java.io.File;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

import edu.nju.opsource.nhandan.data.Data_SpecialReports;




public class LinkType_SpecialReports extends LinkType{
    
	 private String pageContent = null;
     private Document doc = null;
	 private Data_SpecialReports data = null;
	 
	 
	 
	 public LinkType_SpecialReports(String linkTypeName) {
		 /*  32 */     super.setLinkTypeName(linkTypeName);
		 /*     */   }
		 /*     */ 
		 /*     */   public LinkType_SpecialReports() {
		 /*  36 */     super.setLinkTypeName("nhandan.specialreports");
		 /*     */   }
	 public LinkType_SpecialReports(Site site) {
			// TODO Auto-generated constructor stub
		 setLinkTypeName("nhandan.specialreports");
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
           // System.out.println("LinkType_SpecialReports:70");
            //System.out.println(tmpDir);
			  this.data.setUrl( link.getUrl() );
			  this.data.setSiteUUID( link.getSite().getUuid() );
		 
		     FileUtil.createDirectory("", tmpDir);
		     if (new File(tmpDir).exists()) {
		       FileUtil.writeStringToFile(this.pageContent, tmpDir + getLinkUniqeID(super.getLinkId()) + ".html");
		       FileUtil.writeStringToFile(this.data.toJSON(), tmpDir + getLinkUniqeID(super.getLinkId()) + ".json");
		       GetImage.downloadImage(this.data.getImgUri(), tmpDir+"pic.jpg");
		       FileUtil.zipFile(tmpDir, dataDir);
		       setState(ELinkState.CRAWLED);
		       return true;
		     }
		     setState(ELinkState.CRAWLFAILED);
		     return false;
	}

	private void getDataFromDoc() {
		// TODO Auto-generated method stub
		Data_SpecialReports data_SR = new Data_SpecialReports();
		 
		     Elements paragraph = this.doc.select("div.ndcontent p");
		     StringBuffer content = new StringBuffer();
		     StringBuffer contentWithTag = new StringBuffer();
		     for (Element e : paragraph) {
		       content.append(e.text());
		       contentWithTag.append(e.outerHtml());
		     }
		 
		     data_SR.setContent(content.toString());
		     data_SR.setContentWithTag(contentWithTag.toString());
		 
		     data_SR.setDate(getLinkDate() == null ? new Date() : getLinkDate());
		 /*
		  * select
		  */
		     Elements titleE = this.doc.select("div.fontM.ndtitle h3");
		     if ((titleE == null) || (titleE.size() == 0)) data_SR.setTitle("NULLDATAFIELD"); else {
		    	 data_SR.setTitle(titleE.first().text());
		     }
		     Elements abstractE = this.doc.select("div.ndcontent.ndb p");
		     if ((abstractE == null) || (abstractE.size() == 0)) data_SR.setAbstract("NULLDATAFIELD"); else {
		    	 data_SR.setAbstract(abstractE.first().text());
		     }
		     /*
		      * src
		      */
		     Elements titleImgE = this.doc.select("img.nd_img");
		     if ((titleImgE == null) || (titleImgE.size() == 0)) data_SR.setImgUri("NULLDATAFIELD"); else {
		    	 data_SR.setImgUri("http://en.nhandan.com.vn/"+titleImgE.first().attr("src"));
		     }
		     this.data = data_SR;
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
			    // System.out.println("linktype-sp-151:date");
			    // System.out.println(e);
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
	public Data_SpecialReports getData()
	 {
	   return this.data;
	 }
	@Override
	public void setSite(Site paramSite) {
		// TODO Auto-generated method stub
		
	}
	public static void main(String[] args){
		
	}
}

