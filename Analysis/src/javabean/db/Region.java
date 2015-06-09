package javabean.db;




/**
 * 行政区域类
 * @author hanxin
 */
public class Region {
	private int regionID;//地名ID。地名包含省、市、县，ID默认使用邮政编码
	private int provinceID;//地名所属省份的ID
	private String regionName;//地名名称
	private int regionLevel;//地名级别：123分别代表省市县
	private int parentID;//上一级地名的ID。如果本级地名为省，该ID为0
	private String regionAbbr;
	
	public Region(int regionID){
		this.regionID=regionID;	
	}
	public Region(int regionID,int provinceID,String regionName,int regionLevel,int parentID,String regionAbbr){
		this.regionID=regionID;	
		this.provinceID=provinceID;
		this.regionName=regionName;
		this.regionLevel=regionLevel;
		this.parentID=parentID;
		this.regionAbbr=regionAbbr;
		
	}
	
	public void setRegionID(int regionID) {
		this.regionID = regionID;
	}
	public int getRegionID() {
		return regionID;
	}
	public void setProvinceID(int provinceID) {
		this.provinceID = provinceID;
	}
	public int getProvinceID() {
		return provinceID;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionLevel(int regionLevel) {
		this.regionLevel = regionLevel;
	}
	public int getRegionLevel() {
		return regionLevel;
	}
	public void setParentID(int parentID) {
		this.parentID = parentID;
	}
	public int getParentID() {
		return parentID;
	}
	
	

	public void setRegionAbbr(String regionAbbr) {
		this.regionAbbr = regionAbbr;
	}
	public String getRegionAbbr() {
		return regionAbbr;
	}

}
