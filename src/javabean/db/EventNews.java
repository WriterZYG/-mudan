package javabean.db;

import java.util.Date;

/**
 * 抽取出来的内容，包括标题，正文，网站来源，对应事件id，网址，精确发表时间，倾向性
 * @author hx
 */
public class EventNews {
	private String title;
	private String content;
	private String site;
	private int eventId;
	private int templateID;
	private String url,webSiteplate;
	private Date preciseDate;
	private String id;
	private String pageId;
	private int polarity;
	private int srcType,sitePriority,newsLevel;
	





	
	public EventNews(String title, String content, String site, int eventId,
			int templateID, String url, Date preciseDate, int polarity,
			int srcType, int sitePriority, int newsLevel) {
		super();
		this.title = title;
		this.content = content;
		this.site = site;
		this.eventId = eventId;
		this.templateID = templateID;
		this.url = url;
		this.preciseDate = preciseDate;
		this.polarity = polarity;
		this.srcType = srcType;
		this.sitePriority = sitePriority;
		this.newsLevel = newsLevel;
	}



	public EventNews(String title, String content, String site, int eventId,
			int templateID, String url, Date preciseDate, int polarity) {
		super();
		this.title = title;
		this.content = content;
		this.site = site;
		this.eventId = eventId;
		this.templateID = templateID;
		this.url = url;
		this.preciseDate = preciseDate;
		this.polarity = polarity;
	}

	

	public EventNews() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setPolarity(int polarity) {
		this.polarity = polarity;
	}

	public int getPolarity() {
		return polarity;
	}

	public void setPreciseDate(Date preciseDate) {
		this.preciseDate = preciseDate;
	}

	public Date getPreciseDate() {
		return preciseDate;
	}

	@Override
	public String toString() {
		return "EventNews [content=" + content + ", eventId=" + eventId
				+ ", id=" + id + ", polarity=" + polarity + ", preciseDate="
				+ preciseDate + ", site=" + site + ", title=" + title
				+ ", url=" + url + "]";
	}

	public void setTemplateID(int templateID) {
		this.templateID = templateID;
	}

	public int getTemplateID() {
		return templateID;
	}



	public void setSitePriority(int sitePriority) {
		this.sitePriority = sitePriority;
	}



	public int getSitePriority() {
		return sitePriority;
	}



	public void setNewsLevel(int newsLevel) {
		this.newsLevel = newsLevel;
	}



	public int getNewsLevel() {
		return newsLevel;
	}



	public void setSrcType(int srcType) {
		this.srcType = srcType;
	}



	public int getSrcType() {
		return srcType;
	}



	public String getWebSiteplate() {
		return webSiteplate;
	}



	public void setWebSiteplate(String webSiteplate) {
		this.webSiteplate = webSiteplate;
	}



	public String getPageId() {
		return pageId;
	}



	public void setPageId(String pageId) {
		this.pageId = pageId;
	}


	
}
