package edu.nju.opensource.common.beans;

import com.onezetta.abenablebean.util.Str2MD5;
import com.onezetta.dbablebean.dbenablebeananntation.ColDef;
import com.onezetta.dbablebean.dbenablebeananntation.SP;
import com.onezetta.dbenablebean.DBEnableBean;
import java.util.LinkedList;

@SP(
	table="link",
	sps="getUnCrawlLinks : select * from {tableName} where estate=1;"
		+ "getCrawlFailLinks : select * from {tableName} where estate=4 or estate=2;"
		+ "getLinksByLastHopLinkId : select * from {tableName} where lastHopLinkId={lastHopLinkId};"
		+ "getHandleFailLinks : select * from {tableName} where estate=5 or estate=7;"
		+ "getLinkBySiteUUID: select * from {tableName} where siteUUID={siteUUID};"
		+ "getLinkByMD5: select * from {tableName} where md5={md5};"
		+ "getLinkBySiteUUIDAndLinkTypeName: select * from {tableName} where siteUUID={siteUUID} and linkTypeName={linkTypeName};"
		+ "getFailLinkForRetry: select * from {tableName} where ( estate=4 or estate=2 ) and crawlTryCnt<4", 
	create="create table link ("
		+ "id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY, "
		+ "linkTypeName varchar(255), "
		+ "url text, lastHopLinkId int(11), "
		+ "estate int(11), "
		+ "siteUUID varchar(255),"
		+ "linkDate date,md5 varchar(32),"
		+ "crawlTryCnt int(2)"
	+ ");"
)

public class Link extends DBEnableBean
{

  @ColDef(off=true)
  private LinkType linkType;

  @ColDef(off=true)
  private ELinkState state;

  @ColDef(off=true)
  private Link lastHopLink;

  @ColDef(off=true)
  private Site site;
  private String linkTypeName;
  private int estate = -1;
  private int lastHopLinkId = -1;
  private String url = "";
  private String siteUUID = "";
  private java.sql.Date linkDate = null;
  private String md5 = "";
  private int crawlTryCnt = 0;
  
  @ColDef(off=true)
  public static final int crawlReTryLimit = 4;
  
  public Link() {  }
  public Link(String url, LinkType linkType, ELinkState state, Link lastHopLink, Site site) {
	this.lastHopLink = lastHopLink;
	this.site = site;
	this.state = state;
	this.linkType = linkType;

	this.linkTypeName = linkType.getLinkTypeName();
	this.estate = state.getIndex();
	this.lastHopLinkId = (lastHopLink == null ? -1 : lastHopLink.getId());
	this.siteUUID = site.getUuid();
	this.url = url;
  }

  public String linkName() {
	return getSite().getName() + "_" + this.linkTypeName + "_" + this.linkType.getLinkUniqeID(new StringBuilder().append("").append(getId()).toString());
  }

  public int insert() {
	LinkedList links = new Link().setMd5(getMd5()).query("getLinkByMD5");
	if ((links == null) || (links.size() == 0)) {
		super.insert();
		return getId();
    }
	return -1;
  }

  public int insert(boolean flag) {
	LinkedList links = new Link().setMd5(getMd5()).query("getLinkByMD5");
	if ((links == null) || (links.size() == 0)) { insert(); }
	else if (flag) {
		((DBEnableBean)links.get(0)).delete();
		
		System.out.println("Link:89");
		System.out.println(this.getLinkTypeName());
		super.insert();
		return getId();
    }

	return -1;
  }

  public java.sql.Date getLinkDate() {
	//java.util.Date date = new java.util.Date();
	//java.sql.Date d = new java.sql.Date(date.getTime());
	 
	//return d;
	  return this.linkDate;
  }

  public Link setLinkDate(java.sql.Date linkDate) {
	this.linkDate = linkDate;
	return this;
  }

  public LinkType getLinkType() {
	return this.linkType;
  }

  public void setLinkType(LinkType linkType) {
	this.linkType = linkType;
  }

  public ELinkState getState() {
	return this.state;
  }

  public void setState(ELinkState state) {
	this.state = state;
	this.estate = state.getIndex();
  }

  public Link getLastHopLink() {
	return this.lastHopLink;
  }

  public void setLastHopLink(Link lastHopLink) {
	this.lastHopLink = lastHopLink;
  }

  public String getLinkTypeName() {
	if (this.linkType != null)
		this.linkTypeName = this.linkType.getClass().getName();
	return this.linkTypeName;
  }

  public void setLinkTypeName(String linkTypeName)
  {
	this.linkTypeName = linkTypeName;
  }

  public int getEstate() {
	if (this.state != null)
		this.estate = this.state.getIndex();
	return this.estate;
  }

  public void setEstate(int estate) {
	this.estate = estate;
  }

  public int getLastHopLinkId() {
	return this.lastHopLinkId;
  }

  public Link setLastHopLinkId(int lastHopLinkId) {
	this.lastHopLinkId = lastHopLinkId;
	return this;
  }

  public String getUrl() {
	return this.url;
  }

  public void setUrl(String url) {
	this.url = url;
  }

  public String getSiteUUID() {
	return this.siteUUID;
  }

  public void setSiteUUID(String siteUUID) {
	this.siteUUID = siteUUID;
  }

  public Site getSite() {
	return this.site;
  }

  public void setSite(Site site) {
	this.site = site;
	if (this.linkType != null) this.linkType.setSite(site); 
  }

  public String getMd5()
  {
	return "".equals(this.md5) ? Str2MD5.MD5(this.url, 32) : this.md5;
  }

  public Link setMd5(String md5) {
	this.md5 = md5;
	return this;
  }
  
  public int getCrawlTryCnt() {
	return crawlTryCnt;
  }
	
  public void setCrawlTryCnt(int crawlTryCnt) {
	this.crawlTryCnt = crawlTryCnt;
  }

}
