package javabean.db;

import java.util.ArrayList;
import java.util.Date;

import util.TimeUtil;

/**
 * 存储文档，结构同数据库
 * @author Bys
 *
 */
public class Page {
	private String id;
	private String url,fn,webSite,webSiteplate;
	private Date downloadDate;
	private String title;
	private String summary;
	private Date publishDate;
	private int type;
	private int sitePriority,newsLevel;
	private String content;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFn() {
		return fn;
	}
	public void setFn(String fn) {
		this.fn = fn;
	}
	public String getWebSite() {
		return webSite;
	}
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
	public Date getDownloadDate() {
		return downloadDate;
	}
	public void setDownloadDate(Date downloadDate) {
		this.downloadDate = downloadDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getSitePriority() {
		return sitePriority;
	}
	public void setSitePriority(int sitePriority) {
		this.sitePriority = sitePriority;
	}
	public int getNewsLevel() {
		return newsLevel;
	}
	public void setNewsLevel(int newsLevel) {
		this.newsLevel = newsLevel;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}
	public String getWebSiteplate() {
		return webSiteplate;
	}
	public void setWebSiteplate(String webSiteplate) {
		this.webSiteplate = webSiteplate;
	}

	//测试页面流程时间
	public String readDocTime,findDocTime,analysisDocTime;
	public long blockT;
	public ArrayList<String> regionTimes=new ArrayList<String>(),chkDupTimes=new ArrayList<String>(),insertTimes=new ArrayList<String>();
	public String getTimeStr(){
		String str=id+";"+this.content.length()+";"+readDocTime+";"+findDocTime+";"+TimeUtil.decimalFormat.format(((double)blockT)/1000)+";"+regionTimes.toString()+";"+chkDupTimes.toString()+";"+insertTimes.toString()+";"+analysisDocTime;
		return str;
	}
}
