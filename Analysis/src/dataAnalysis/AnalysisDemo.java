package dataAnalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;



import md.base.storage.WebPageStorage;

import javabean.EventList;
import javabean.Keyword;
import javabean.MatchedResult;
import javabean.db.Event;
import javabean.db.KeywordGroup;
import javabean.db.Page;
import javabean.db.Template;

import snooway.dao.DataDao;
import snooway.dao.PoolConection;
import snooway.dao.PoolConnectCfg;
import util.AnalysisProperties;

public class AnalysisDemo {


	static AnalysisProperties aprop = AnalysisProperties.getInstance(); 
	private static WebPageStorage storage = new WebPageStorage();
	public static DataDao da = DataDao.createDao();
	private static ConcurrentHashMap<String, KeywordGroup> concept_map = new ConcurrentHashMap<String, KeywordGroup>();
	public static WebPageAnalyzer demo = new WebPageAnalyzer();
	static final int NoStop = 0, DocMatchStop = 1, EventMatchStop = 2,
			TemMatchStop = 3;
	private static String postMatchClass = aprop.getPostMatchClass();
	static{
		try {
			/**
			 * 本地读取器
			 */
			System.out.println("DocUrl:"+aprop.getDocUrl());
			String baseUrl=aprop.getDocUrl();
			storage.useHttpFileSystem(baseUrl);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void initMap(KeywordGroup kg,
			ConcurrentHashMap<String, KeywordGroup> map) {
		// System.out.println(kg.getKeywordGroupName());
		for (KeywordGroup child : kg.getChildKeywordGroup()) {
			initMap(child, map);
		}
		map.put(kg.getKeywordGroupName(), kg);
	}
	
	public static void main(String[] args) throws Exception {
		
		
		
		
	    demo.conceptPublic = da.getEventByEventId(-1, "公共事件");
	    demo.eventList = da.getEventList();
	    EventList eventList1 = demo.eventList;
	
	    Event publicEvent = demo.conceptPublic;
	    ArrayList<Event> EventAll = demo.eventList.getEvents();
	    ArrayList<KeywordGroup> global_kg = publicEvent.getConcepts();
	    for (KeywordGroup global_k : global_kg) {
			initMap(global_k, demo.global_concept);
		}
	    Event event_init = null;
		for (int i = 0; i < EventAll.size() - 1; i++) {
			event_init = (Event) EventAll.get(i);
			ArrayList<Template> Template = event_init.getTemplates();
			for (Template template : Template) {
				for (KeywordGroup k : template.getConcepts()) // 匹配模板包含的所有概念
				{
					initMap(k, event_init.getConceptMap());
				}
			}
		}
		
		boolean estop = false;// 表示是否停止匹配某个事件
		boolean dstop = false;// 表示是否停止匹配某篇文章
		boolean isMatch = false;// 表示是否匹配到
		
		Page pagedemo = new Page();
		File filedemo = new File("testdemo/0D2544F1144EBA9AF10FD970C0F8894C.txt");
		InputStreamReader isr= new InputStreamReader(new FileInputStream(filedemo),"UTF-8");
		BufferedReader br = new BufferedReader(isr);
		StringBuffer sb = new StringBuffer();
		String s;
		while((s = br.readLine())!=null)
		{
			sb.append(s);
			sb.append("\n");
		}
		br.close();
		isr.close();
		pagedemo.setContent(sb.toString());
		pagedemo.setId("0D2544F1144EBA9AF10FD970C0F8894C");
		Event e = EventAll.get(8);
		EventMatcher em = new EventMatcher();
		ArrayList<MatchedResult> mrList;
		// ArrayList<KeywordGroup> concepts = e.getConcepts();
		ArrayList<Template> templates = new ArrayList<Template>(
				e.getTemplates());
		
		 mrList = em.find(pagedemo.getContent(), e, pagedemo.getId(), templates);
		 PostMatcher htpm = null;
		 htpm = (PostMatcher) Class.forName(postMatchClass).newInstance();
		 MatchedResult mr = mrList.get(0);
			
				System.out.println(mr.getStart());
				int stopFlag = htpm.postMatcher(mr, pagedemo, e, 0);
//				System.out.println("stopFlag" + stopFlag);
//
//				switch (stopFlag) {
//				case NoStop:
//					break;
//				case TemMatchStop:
//					break;
//				case DocMatchStop:
//					dstop = true;
//					break;
//				case EventMatchStop:
//					isMatch = true;
//					estop = true;
//					break;
//				}
//				if (estop || dstop)
//					break;
			
		//System.out.println(mrList.get(0).getStart());

		
		
	}

}
