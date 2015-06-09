package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dataAnalysis.WebPageAnalyzer;

import javabean.db.Region;

import snooway.dao.DataDao;

/**
 *JiangxiPostMatcher 所需的一些工具类
 * @author hx
 * 
 */
public class JiangxiUtil {
	
//	private static String negtiveWordList = "不是|不行|不能|不可能|没有|绝没|并未|尚未|仍未|还未|从未|不要|反";
	DataDao da = WebPageAnalyzer.da;


	public int getMaxLevelofRegions(List<Integer> regionList){
		int maxId=0;
		
		for(int id:regionList){
			/*Region r = da.getRegionById(id);
			if(r!=null&&r.getRegionLevel()>maxId)
			  maxId = r.getRegionLevel();*/
			int regionLevel=(""+id).length()/2;
			if(regionLevel>maxId)
				maxId=regionLevel;
		}
		return maxId;
	}
	
	
	/**
	 * 
	 * @param regionList
	 * @return
	 */
	public List<Integer> getMaxLevelRegions(List<Integer> regionList) {
		/*List<Integer>  hs = new ArrayList<Integer>();
		if (regionList != null) {		
			int maxId = getMaxLevelRegions(regionList);
			for (int id : regionList) {
			Region  r = da.getRegionById(id);// 得到地区层级
		//	System.out.println("Region:"+r);
			if(r!=null&&r.getRegionLevel()==maxId)
			    hs.add(id);
				if((""+id).length()/2==maxId)
					hs.add(id);
			}	
			return hs;
		}
		return null;*/
		LinkedList<Integer> regionIds=new LinkedList<Integer>();
		HashMap<Integer, LinkedList<Integer>> province2Region = new HashMap<Integer, LinkedList<Integer>>();
		for(int regionId:regionList){
			int regionLevel=(regionId+"").length()/2;
			int provinceId=Integer.parseInt((regionId+"").substring(0, 2));
			LinkedList<Integer> maxLevelRegions=province2Region.get(provinceId);
			if(maxLevelRegions==null){
				maxLevelRegions=new LinkedList<Integer>();
				maxLevelRegions.add(regionLevel);
				maxLevelRegions.add(regionId);
				province2Region.put(provinceId, maxLevelRegions);
			}else {
				if(regionLevel>maxLevelRegions.get(0)){
					maxLevelRegions.clear();
					maxLevelRegions.add(regionLevel);
					maxLevelRegions.add(regionId);
				}else if(regionLevel==maxLevelRegions.get(0)){
					maxLevelRegions.add(regionId);
				}
			}
		}
		for(LinkedList<Integer> regions:province2Region.values()){
			regions.remove(0);
			regionIds.addAll(regions);
		}
		System.out.println();
		return regionIds;
	}
	
	
	public int getMaxLevelRegionsFromName(List<String> regionList){
		int maxId=0;
		
		for(String str:regionList){
			Region r = da.getRegionByRegionName(str);
			if(r!=null&&r.getRegionLevel()>maxId)
			  maxId = r.getRegionLevel();
		}
		return maxId;
	}
	
	
	/**
	 * 
	 * @param regionList
	 * @return
	 */
	public HashSet<String> getFromName(List<String> regionList) {
		HashSet<String>  hs = new HashSet<String>();
		
		if (regionList != null) {		
			for (String str : regionList) {
			Region  r = da.getRegionByRegionName(str);// 得到地区层级
		//	System.out.println("Region:"+r);
			if(r!=null&&r.getRegionLevel()==getMaxLevelRegionsFromName(regionList))
			    hs.add(str);
			}	
			return hs;
		}
		return null;
	}
	
	

	public boolean compare(HashSet<String> h1,HashSet<String> h2){
	  if(h1.size()!=h2.size()) return false;
		
		if(h1.containsAll(h2)&&h2.containsAll(h1)) return true;
		
		else return false;
	
		
	}
	
	/**
	 * 得到标红的事件
	 * @param text
	 * @return
	 */
	public String getCoretext(String text) {
		String _text = text;
/*		Pattern p1 = Pattern.compile("<span class=\"br\">");
		Matcher m1 = p1.matcher(text);
		int start = 0,end = text.length() ;
		if(m1.find()){
			start = m1.end();			
		}
		
		Pattern p2 = Pattern.compile("</span>");
		Matcher m2 = p2.matcher(text);
		while(m2.find()){
			end=m2.start();
			if(end>start) break;
		}
		
		String coreText =  text.substring(start, end);
	//	String filterReg = "<span class=\"br\">|<span class=\"bg\">|</span>";
		String filterReg1 = "<span class=\"br\">";
		String filterReg2 = "<span class=\"bg\">";
		String filterReg3 = "</span>";
		coreText=coreText.replace(filterReg1, "").replace(filterReg2, "").replace(filterReg3, "");
		return coreText;*/
		StringBuffer coreText=new StringBuffer("");
		String eventLabel="<span class=\"eventlabel\">";
		String areaLabel="<span class=\"arealabel\">";
		String endLabel="</span>";
		Pattern pattern=Pattern.compile(eventLabel);
		Matcher matcher=pattern.matcher(text);
		int start=0,end=0,count=0;//count当栈使用
		if(matcher.find()){
			count=1;
			pattern=Pattern.compile(endLabel+"|"+areaLabel+"|"+endLabel);
			text=text.substring(matcher.end());
			matcher=pattern.matcher(text);
			while(count!=0){
				if(matcher.find()){
					String label=matcher.group();
					if(label.equals(endLabel))
						count--;
					else 
						count++;
					coreText.append(text.substring(start,matcher.start()));
					start=matcher.end();
				}else{
					System.err.println(_text);
					return _text;
				}
			}
		}
		return coreText.toString();
		

	}
	/**
	 * 判断某个地名是否属于某个省
	 */
	public boolean isRegionOfProvince(String abbr,int provinceId){
		Region region = da.getRegionByRegionName(abbr);
	//	System.out.println("断点1"+region.getProvinceID());
		if(region!=null&&region.getProvinceID()==provinceId){
			return true;
		}
		return false;
		
	}
	
	
	
	
	
	public int opinionExtractor(String content, int templatePolarity,
			String negative, int strbegin, int x_length) {
		int polarity = templatePolarity;// //返回值初始为 模板极性

		if (strbegin > content.length())// ///////对于输入错误的 直接返回 正面
			return polarity;

		Pattern p = Pattern.compile(negative);

		String contenttemp = "";// //////////取评价词前X个字

		contenttemp = content.substring(Math.max(0, strbegin - x_length),
				strbegin);

		Matcher m = p.matcher(contenttemp);// /////处理负面词 出现 *-1

		if (m.find()) {
			polarity = polarity * (-1);
		}

		// 如果评价词前面有一个“不”字，则极性反转
		if (contenttemp.indexOf("不") == x_length - 1) {
			polarity = polarity * (-1);
		}

		boolean flag = contenttemp.indexOf("有没有") != -1
				|| contenttemp.indexOf("是不是") != -1;
		// ||contenttemp.indexOf("不是一般")!=-1
		// 把 “不是一般”去了吧 感觉加了反而不怎么好

		if (flag)// /对于 “有没有” “是不是” 的处理
			return 1;
		if (contenttemp.indexOf("不是一般") != -1)
			polarity = polarity * (-1);
		// System.out.println("极性是2："+polarity);
		return polarity;
	}

	/**
	 * Most important: return true if this MatchedResult is to be killed *
	 * 
	 * @author hx 
	 * 所有地域去噪音,符合规则返回true。不符合返回false 如果地名后有跟“吧”，不通过
	 * 地名如果有跟：县，市，州，区,地委，则通过 如果在上下文中（不包含地名本身）有云南和昆明，通过 else return false
	 */
	public boolean monitorRule(String text,String regionName)
	{

		int regionEnd = 0;
		int regionStart = 0;

		Pattern pattern = Pattern.compile(regionName);
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			regionEnd = matcher.end();
			regionStart = matcher.start();
		}
		String rule = text.substring(Math.max(0, regionStart-3), regionEnd
				+ Math.min(text.length() - regionEnd, 6));// 地名的前3后6个字
		// 如果地名后有跟“吧”，不通过

		// System.out.println(rule);
		if (rule.indexOf("吧") == 0 || rule.indexOf("网") == 0||rule.contains("新浪") ||rule.contains("播报")||rule.contains("新闻")||rule.contains("论坛")||rule.contains("社区")||rule.contains("晚报")||rule.contains("频道")) {
			// 如果是**吧，直接return
			System.out.println("网站地址排除！～");
			return true;
		}
		return false;
	}
	
	
	/**
	 * @author Bys
	 * @param name
	 * @return
	 */
	public int toID(String name){
		Region r = da.getRegionByRegionName(name);
		if(r!=null)
			return r.getRegionID();
		else 
			return -1;
	}

//	public static void main(String[] args) {
////		JiangxiUtil yu = new JiangxiUtil();
//		//	PoolConection.init("DataAccess.properties");
//	//	List<String> regionList = new ArrayList<String>();
//	//	regionList.add("云南");
//	//	regionList.add("昆明");
//		
//	//	System.out.println(yu.get(regionList));
//		
//		
////		String str = "<span class=\"br\"><span class=\"bg\">昆明</span>市市长</span>都管不了这事？真邪门！ 我们打<span class=\"bg\">昆明</span>市长热线12345，今天得";
////		System.out.println(yu.getCoretext(str));
//		
//		
//	}

}
