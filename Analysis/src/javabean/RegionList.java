package javabean;

import java.util.ArrayList;

import javabean.db.Region;

import dataAnalysis.WithReglExpr;

/**
 * 地名列表
 * @author hanxin
 */
public class RegionList implements WithReglExpr{
	ArrayList<Region> regions=new ArrayList<Region>();

	public RegionList(ArrayList<Region> regions) {
		this.regions = regions;
	}

	/**
	 *生成地名的正则表达式，传入eventnews进行匹配
	 *
	 */
	
	public String generateExp() {	
		if (regions.size() > 0) {
			String exp = "(";
			for (Region p : regions) {
				String s=p.getRegionAbbr();
				if (s!=null&&(!s.trim().equals(""))) {
					exp += s + "|";
				}
			}
			exp = exp.substring(0, exp.length() - 1);
			exp += ")";
			return exp;
		} else
			return null;
	}
	
	
	
	public ArrayList<Region> getRegions() {
		return regions;
	}

	public void setRegions(ArrayList<Region> regions) {
		this.regions = regions;
	}
	
	public void addRegion(Region e)
	{
		this.regions.add(e);
	}
}

