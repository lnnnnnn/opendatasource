package edu.nju.opensource.common.action;

import com.onezetta.abenablebean.util.DBTableCheckor;
import com.onezetta.abenablebean.util.DBTableInitor;
import com.onezetta.abenablebean.util.FileUtil;
import com.onezetta.abenablebean.util.NetworkEnvTest;
import com.onezetta.abenablebean.util.OSJudge;
import com.onezetta.dbenablebean.DBEnableBean;
import com.onezetta.hdfs.HdfsConfiguration;
import com.onezetta.threadpool.MyThreadPool;
import edu.nju.opensource.common.beans.ELingual;
import edu.nju.opensource.common.beans.ELinkState;
import edu.nju.opensource.common.beans.ENation;
import edu.nju.opensource.common.beans.Link;
import edu.nju.opensource.common.beans.LinkType;
import edu.nju.opensource.common.beans.Site;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class OpenDataSource
{
  public static final String UNUPLOAD_DATA_DIR = "/opensource_data/unupload/";
  public static final String UPLOADED_DATA_DIR = "/opensource_data/uploaded/";
  public static final String TMP_DATA_DIR = "/opensource_data/tmp/";
  public static final String DOGFEED_FILE = "feed.txt";
  public static final String UPLOAD_SUFFIX = "-uploaded";
  public static final String VPNTESTURL = "www.google.com";
  public static final String HBASETESTURL = "";
  
  
  private String configXml;
  private HashMap<String, Site> sites;
  private LinkedList<LinkType> linkTypes;
  private MyThreadPool pool = null;

  public OpenDataSource() {
  }
  public OpenDataSource(String configXml) {
	  this.configXml = configXml;
	  this.sites = new HashMap<String, Site>();
	  this.linkTypes = new LinkedList<LinkType>();
  }

  public void init_Envirment()  throws Exception
  {
	boolean LinkTableIsExist = new DBTableCheckor(Link.class).isTableExist(new Link().getTableName());
	if (!LinkTableIsExist) new DBTableInitor(Link.class, new Link());
	
	if (!new File( OpenDataSource.getUnuploadDataDir() ).exists()) FileUtil.createDirectory("", OpenDataSource.getUnuploadDataDir());
	if (!new File( OpenDataSource.getUploadedDataDir() ).exists()) FileUtil.createDirectory("", OpenDataSource.getUploadedDataDir()); 
	if (!new File( OpenDataSource.getTmpDataDir() ).exists()) FileUtil.createDirectory("", OpenDataSource.getTmpDataDir() ); 
  }

  public void init_ThreadPool()   throws Exception
  {
	this.pool = new MyThreadPool(10, 5, 120);
  }

  public void init_Sites() throws JDOMException, IOException {
	if (getConfigXml() != null)
    {
		SAXBuilder builder = new SAXBuilder();
		InputStream in = OpenDataSource.class.getResourceAsStream("/" + getConfigXml());
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List<Element> list = root.getChild("Sites").getChildren("Site");
		for (Element e : list) {
			String name = e.getAttributeValue("name");
			String uuid = e.getAttributeValue("uuid");
			String nation = e.getAttributeValue("nation");
			String lingual = e.getAttributeValue("lingual");
			String desc = e.getChildTextTrim("describe");

			Site s = new Site();
			s.setLingual(ELingual.getLingual(lingual));
			s.setName(name);
			s.setNation(ENation.getENation(nation));
			s.setUuid(uuid);
			s.setNote(desc);

			Element linkElement = e.getChild("Link");
			String url = linkElement.getChild("Url").getText();

			String linkTypeName = linkElement.getChild("Linktype").getText();
			String LinkTypeClass = linkElement.getChild("Linktype").getAttributeValue("class");
					
			LinkType linkType = null;
			try{
				linkType = getLinkTypeObject(LinkTypeClass);
				linkType.setLinkTypeName(linkTypeName);
			} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
	        } catch (InstantiationException e1) {
	        		e1.printStackTrace();
	        } catch (IllegalAccessException e1) {
	        		e1.printStackTrace();
	        }
			Link link = new Link(url, linkType, ELinkState.getELinkState("uncrawl"), null, s);
			s.setUrl(link);
			this.sites.put(s.getUuid(), s);
      }
      try {
    	  if (in != null) in.close(); 
      }catch (Exception e) { e.printStackTrace(); }
    }
  }

  private void processUnCrawledLink()
  {
	LinkedList<DBEnableBean> curUncrawlLinks = new Link().query("getUnCrawlLinks");
	while ((curUncrawlLinks != null) && (curUncrawlLinks.size() > 0))
    {
		for (DBEnableBean bean : curUncrawlLinks) {
			Link link = (Link)bean;
			try {	
				link.setLinkType(getLinkTypeObject(link.getLinkTypeName()));
				link.setSite((Site)this.sites.get(link.getSiteUUID()));
			
				link.getLinkType().setLinkId(link.getId() + "");
				
				//STARTING FEED DOG, Print Date AND state: 2010-10-06:15:20:30\tRUNNING
				feedDog( true );
				link.setEstate(ELinkState.CRAWLING.getIndex());
				link.update();
				link.getLinkType().get(link.getUrl());
				link.setEstate(link.getLinkType().getState().getIndex());
				//System.out.println( "State is: ---------------------" + link.getEstate() );
				link.update();
				
				
				/*if(link.getUrl().equals("http://search.sina.com.cn/?range=all&c=news&q=%E5%8D%97%E6%B5%B7+%E4%B8%AD%E5%9B%BD&from=home&ie=utf-8&col=&source=&country=&size=&time=&a=&page=9&pf=2141911105&ps=2132736116&dpc=1")){
					System.out.println("in");
					
				};*/
				if(link.getEstate() == ELinkState.CRAWLFAILED.getIndex())	continue;
				link.setEstate(ELinkState.HANDLING.getIndex());
				link.update();
				link.getLinkType().handle( link );	
				link.setEstate(link.getLinkType().getState().getIndex());
				//System.out.println( "State is: ---------------------" + link.getEstate() );
				link.update();
			}catch (Exception e)
			{
				link.setEstate(ELinkState.HANDLEFAILED.getIndex());
				link.update();
				e.printStackTrace();
			}
		}
		curUncrawlLinks = new Link().query("getUnCrawlLinks");
    }
  }

  private void feedDog(boolean run){
	  String cxt = new Date().getTime()/1000 + "\t" +  ((run) ? "RUN": "END");
	  
	  System.out.println( cxt );
	  String feedFile = OpenDataSource.DOGFEED_FILE ;
	  
      try {
    	  FileWriter fileWritter = new FileWriter( OpenDataSource.getTmpDataDir() + feedFile );
    	  fileWritter.write( cxt );
    	  fileWritter.flush();
          fileWritter.close();
      } catch (IOException e) {
    	  e.printStackTrace();
      }
  }
  
  public void run() throws Exception
  {
	  init_Envirment();
	  init_Sites();
	  /*
	  if( !NetworkEnvTest.test(HdfsConfiguration.getHbaseTestUrl())
		  ||
		  !NetworkEnvTest.test(HdfsConfiguration.getVpnTestUrl())
		){
		  System.out.println( "[ERROR] Network Envirment Test FAILED!............");;
		  return;
	  }
	  */	  
	  processUnCrawledLink();

	  Iterator iter = this.sites.entrySet().iterator();
	  while (iter.hasNext()) {
		  Map.Entry entry = (Map.Entry)iter.next();
		  try {
			  Site site = (Site)entry.getValue();
			  Link link = site.getUrl();
			  link.setLinkType(getLinkTypeObject(link.getLinkTypeName()));
			  link.setSite(site);

			  link.getLinkType().setLinkId(link.getId() + "");
			  
			  //STARTING FEED DOG, Print Date AND state: 2010-10-06:15:20:30\tRUNNING
			  feedDog( true );
			  link.getLinkType().get(link.getUrl());			  
			  link.getLinkType().handle( link );
		  } catch (Exception e) {
			  e.printStackTrace();
		  }

	  }

	  processUnCrawledLink();
	  //STARTING FEED DOG, Print Date AND state
	  feedDog(false);
  }

  public static LinkType getLinkTypeObject(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
	Class clazz = Class.forName(className);
	LinkType linkType = (LinkType)clazz.newInstance();
	linkType.setState(ELinkState.UNCRAWL);
	return linkType;
  }

  public String getConfigXml() {
	return this.configXml;
  }

  public HashMap<String, Site> getSites() {
	return this.sites;
  }

  public void setSites(HashMap<String, Site> sites) {
	this.sites = sites;
  }

  public void setConfigXml(String configXml) {
	this.configXml = configXml;
  }

  public LinkedList<LinkType> getLinkTypes() {
	return this.linkTypes;
  }

  public void setLinkTypes(LinkedList<LinkType> linkTypes) {
	this.linkTypes = linkTypes;
  }

  public static String getUnuploadDataDir() {
	  if( OSJudge.isWin())
			return "e:" + UNUPLOAD_DATA_DIR;
	  return UNUPLOAD_DATA_DIR;
  }
	
  public static String getUploadedDataDir() {
	  if( OSJudge.isWin())
		  return "e:" + UPLOADED_DATA_DIR;
	  return UPLOADED_DATA_DIR;
  }
	
  public static String getTmpDataDir() {
	  if( OSJudge.isWin())
		  return "e:" + TMP_DATA_DIR;
	  return TMP_DATA_DIR;
  }
	

}
