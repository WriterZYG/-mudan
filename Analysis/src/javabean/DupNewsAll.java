package javabean;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 舆情去重所需数据
 * @author Bys
 *
 */
public class DupNewsAll {
	private String id;
	private String title;
	private String coretext;
	private String regionIds;
//	private int seedId;
	private String mainInfoId;
	private int dupCount;
	private Date mainInfoDate;
	private int eventId;
	private int provinceId;
	public static final int NO_DUP=1;
	public static final int IS_MAIN=2;
	public static final int IS_NOMARL=3;
	public static final int DATE_OUT=0;

	
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

	public String getCoretext() {
		return coretext;
	}

	public void setCoretext(String coretext) {
		this.coretext = coretext;
	}
	
	public String getRegionIds() {
		return regionIds;
	}

	public void setRegionIds(String regionIds) {
		this.regionIds = regionIds;
	}
	
	public void setRegionIds(List<Integer> regionList) {
		this.regionIds = "";
		Collections.sort(regionList);
		for(int id:regionList){
			this.regionIds+="|"+id;
		}
	}
	
	
//	public int getSeedId() {
//		return seedId;
//	}
//
//	public void setSeedId(int seedId) {
//		this.seedId = seedId;
//	}

	
	public Date getMainInfoDate() {
		return mainInfoDate;
	}

	public void setMainInfoDate(Date mainInfoDate) {
		this.mainInfoDate = mainInfoDate;
	}

	public String getMainInfoId() {
		return mainInfoId;
	}

	public void setMainInfoId(String mainInfoId) {
		this.mainInfoId = mainInfoId;
	}

	public int getDupCount() {
		return dupCount;
	}

	public void setDupCount(int dupCount) {
		this.dupCount = dupCount;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public DupNewsAll(){
		
	}
	
	public DupNewsAll(String id,String title,String content,List<Integer> regionIds,int provinceId,int eventId){
		setId(id);
		setTitle(title);
		setCoretext(content);
		setRegionIds(regionIds);
		setProvinceId(provinceId);
		setEventId(eventId);
	}
	
	public DupNewsAll(String id,String title,String content,String regionIds,int provinceId,int eventId){
		setId(id);
		setTitle(title);
		setCoretext(content);
		setRegionIds(regionIds);
		setProvinceId(provinceId);
		setEventId(eventId);
	}
	
	public boolean isDup(DupNewsAll dupNewsAll){
		//如果地域不完全相同则直接认为不重复
		if(!this.regionIds.equals(dupNewsAll.getRegionIds()))
			return false;
		if(this.provinceId!=(dupNewsAll.getProvinceId()))
			return false;
		if(this.eventId!=dupNewsAll.getEventId())
			return false;
		//地域相同时，如果标题相同则认为重复
		if(this.title.equals(dupNewsAll.getTitle()))
			return true;
		//标题不同，但是标红部分相同，也认为是重复
		if(this.coretext.equals(dupNewsAll.getCoretext()))
			return true;
		else
			return false;
	}
	
	public boolean equals(Object o){
		DupNewsAll dupNewsAll = (DupNewsAll)o;
		return isDup(dupNewsAll);
		
	}
}
