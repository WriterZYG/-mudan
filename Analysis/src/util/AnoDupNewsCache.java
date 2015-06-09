package util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javabean.DupNewsAll;

import dataAnalysis.WebPageAnalyzer;


/**
 * 重复舆情缓存，默认将最近3天的种子舆情信息缓存在内存，新舆情直接在内存中比较
 * @author Bys
 *
 */
public class AnoDupNewsCache {
	private final static int CACHE_DAY = AnalysisProperties.getInstance().getCacheDays();
	
	private static Map<String, AnoDupNewsCache> instanceMap = new HashMap<String, AnoDupNewsCache>(CACHE_DAY,1);
//	private ArrayList< LinkedList<DupNewsAll> > cache = null;
	private ArrayList<DupNewsAll> cache = null;

	@SuppressWarnings("unused")
	private Date lastVisit = null;

	private AnoDupNewsCache() {
//		cache = new ArrayList< LinkedList<DupNewsAll> >();
		cache = new ArrayList<DupNewsAll>();
	}
	
	/**
	 * 根据日期获取当日去重缓存句柄
	 * @param date
	 * @return
	 */
	public static AnoDupNewsCache getInstance(Date date){
		AnoDupNewsCache instance = instanceMap.get(WebPageAnalyzer.dateFormat.format(date));
//		if(instance == null){
//			instance = addCache(date);
//		}
		return instance;
	}
	
	
	public static AnoDupNewsCache addCache(Date date){
		if(!instanceMap.containsKey(WebPageAnalyzer.dateFormat.format(date))){
			AnoDupNewsCache instance = new AnoDupNewsCache();
			instance.cache = WebPageAnalyzer.da.getAllForJiangXi2(date);
			instance.lastVisit = new Date();
			instanceMap.put(WebPageAnalyzer.dateFormat.format(date), instance);
		}
		return instanceMap.get(date);
	}
	
	public static boolean removeCache(Date date){
		if(instanceMap.containsKey(WebPageAnalyzer.dateFormat.format(date))){
			instanceMap.remove(WebPageAnalyzer.dateFormat.format(date));
			return true;
		}
		return false;
	}
	
	public DupNewsAll checkDup(DupNewsAll news){
		//没有当天缓存直接不去重
		if(cache == null)
			return null;
		else{
			//否则先根据regionIds进行二分查找，得到候选结果
			List<DupNewsAll> candidate = searchDup(news.getRegionIds());
			//再在候选结果中查找是否有重复的
			int index = candidate.indexOf(news);
			if(index != -1)
				return candidate.get(index);
			else {
				return null;
			}
		}
	}
	
	public DupNewsAll get(int index){
		return cache.get(index);
	}

	/**
	 * 二分查找和regionIds完全相同的重复记录
	 * @param regionIds
	 * @return
	 */
	public List<DupNewsAll> searchDup(String regionIds){
		return cache;
	}
	
	
	public void add(DupNewsAll news) {
		cache.add(news);
	}
	
}
