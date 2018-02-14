package edu.nju.opsource.dangcongsan.linktype;

import java.io.File;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.onezetta.abenablebean.util.FileUtil;
import com.onezetta.abenablebean.util.Str2MD5;
import com.onezetta.dbenablebean.DBEnableBean;
import com.onezetta.downloader.EasyHttpDownloader;
import com.onezetta.downloader.GetImage;

import edu.nju.opensource.common.action.OpenDataSource;
import edu.nju.opensource.common.beans.ELinkState;
import edu.nju.opensource.common.beans.Link;
import edu.nju.opensource.common.beans.LinkType;
import edu.nju.opensource.common.beans.Site;
import edu.nju.opsource.dangcongsan.DangcongsanOpenDataSource;
import edu.nju.opsource.dangcongsan.data.Data_Graph;
import edu.nju.opsource.dangcongsan.data.Data_News;


public class News extends LinkType {

	private String pageContent = null;
	private Document doc = null;
	private Data_News data = null;

	public News(String linkTypeName) {
		super.setLinkTypeName(linkTypeName);
	}

	public News() {
		super.setLinkTypeName("dangcongsan.news");
	}

	public News(Site site) {
		// TODO Auto-generated constructor stub
	}

	public void get(String url)// 赋值给this.doc,this.pageContent
	{
		super.get(url);
		this.pageContent = new EasyHttpDownloader(url).run();
		if (this.pageContent != null) {
			this.doc = Jsoup.parse(this.pageContent);
			System.out.println(" ... has Crawled.");
		} else {
			setState(ELinkState.CRAWLFAILED);
			System.out.println(" ... crawled failed.");
		}
	}

	@Override
	public boolean handle(Link link) throws Exception {

		if (getState() == ELinkState.CRAWLFAILED)
			return false;
		String tmpDir = OpenDataSource.getTmpDataDir() + getLinkUniqeID(super.getLinkId()) + "/";

		String dataDir = OpenDataSource.getUnuploadDataDir() + getLinkUniqeID(super.getLinkId()) + ".zip";
		getDataFromDoc();
		crawlImg();
		this.data.setUrl(link.getUrl());
		this.data.setSiteUUID(link.getSite().getUuid());

		FileUtil.createDirectory("", tmpDir);
		if (new File(tmpDir).exists()) {
			FileUtil.writeStringToFile(this.pageContent, tmpDir + getLinkUniqeID(super.getLinkId()) + ".html");
			FileUtil.writeStringToFile(this.data.toJSON(), tmpDir + getLinkUniqeID(super.getLinkId()) + ".json");

			FileUtil.zipFile(tmpDir, dataDir);
			setState(ELinkState.CRAWLED);
			return true;
		}
		setState(ELinkState.CRAWLFAILED);
		return false;
	}

	public void crawlImg() {
		Elements images = this.doc.select("body").select("img");
		for (Element image : images) {
			String url = image.absUrl("src");
			if (url.indexOf("?") != -1)
				url = url.substring(0, url.indexOf("?"));
			// System.out.println( url );
			String urlMD5 = Str2MD5.MD5(url, 32);
			LinkedList<DBEnableBean> graphs = new Data_Graph().setUrlMD5(urlMD5).query("getByUrlMD5");

			if (graphs == null || graphs.size() == 0) {
				System.out.print("...." + url);
				int rst = GetImage.downloadImage(url,
						DangcongsanOpenDataSource.imagePath + url.substring(url.lastIndexOf("/") + 1), 4096);
				if (rst == GetImage.Success) {
					image.attr("src", url.substring(url.lastIndexOf("/") + 1));
					System.out.println(".... crawled");
				}
				new Data_Graph().setUrlMD5(urlMD5).setUrl(url)
						.setFile(DangcongsanOpenDataSource.imagePath + url.substring(url.lastIndexOf("/") + 1))
						.insert();
			}
		}
	}

	public String getImgName(String uri) {
		int ind = -1, len = uri.length();
		for (int i = 0; i < len; i++)
			if (uri.charAt(i) == '/')
				ind = i;
		String name = uri.substring(ind + 1);
		return name;
	}

	private void getDataFromDoc() {
		// TODO Auto-generated method stub
		Data_News dn = new Data_News();

		/*
		 * Elements paragraph = this.doc.select("p.Normal");
		 * //System.out.println(paragraph); StringBuffer content = new
		 * StringBuffer(); StringBuffer contentWithTag = new StringBuffer(); for
		 * (Element e : paragraph) { content.append(e.text());
		 * contentWithTag.append(e.outerHtml()); }
		 */
//		Elements paragraph = this.doc.select("span#dnn_ctr16742_newsviewer_ctl00_lbContent p");

		Elements paragraph = this.doc.select("div.post-content p");

		StringBuffer content = new StringBuffer();
		StringBuffer contentWithTag = new StringBuffer();
		for(Element e : paragraph) 
		{ content.append(e.text());
		 contentWithTag.append(e.outerHtml()); 
		 }
         dn.setContent(content.toString());
         dn.setContentWithTag(contentWithTag.toString());
		dn.setDate(getLinkDate() == null ? new Date() : getLinkDate());
		/*
		 * select
		 */
		Elements titleE = this.doc.select("h1.post-title");
		if ((titleE == null) || (titleE.size() == 0))
			dn.setTitle("NULLDATAFIELD");
		else {
			dn.setTitle(titleE.first().text());
		}
		Elements abstractE = this.doc.select("div.post-summary h2");
		if ((abstractE == null) || (abstractE.size() == 0))
			dn.setAbstract("NULLDATAFIELD");
		else {
			dn.setAbstract(titleE.first().text());
		}
		
		this.data = dn;
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
			Element e = this.doc.select("div.post-head span.post-subinfo").first();

			String dateStr = e.text();// =tmpStr.substring(tmpStr.indexOf("&nbsp"))
			SimpleDateFormat format = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.US);
			Date date = null;
			try {
				date = format.parse(dateStr.trim());
			} catch (ParseException ex) {
				ex.printStackTrace();
			}
			return date;
		}
		return null;
	}

	public Data_News getData() {
		return this.data;
	}

	@Override
	public void setSite(Site paramSite) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		String str = "09:02 19/08/2016";
		SimpleDateFormat format = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.US);
		try {
			Date date = format.parse(str);
			System.out.println(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
