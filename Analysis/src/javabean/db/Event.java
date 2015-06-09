package javabean.db;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;



/**
 * 事件类，包括事件名和模板，一个事件对应多个模板
 * @author syq
 */
public class Event {
	private int eventID;
	private String eventName;
	private int parentEventId;
	private int status;
	private ArrayList<KeywordGroup> concepts=new ArrayList<KeywordGroup>();
	private ConcurrentHashMap<String,KeywordGroup> conceptMap = new ConcurrentHashMap<String,KeywordGroup>(0);
	private ArrayList<Template> templates = new ArrayList<Template>();
	public Event() {
	}

	public Event(int eventID, String eventName, ArrayList<KeywordGroup> concepts,ArrayList<Template> templates) {
		super();
		this.eventID = eventID;
		this.eventName = eventName;
		this.setTemplates(templates);
		this.setConcepts(concepts);
	}
	
	public Event(Event e){
    	this.setConcepts(e.getConcepts());
    	this.setEventID(e.getEventID());
    	this.setEventName(e.getEventName());
    	this.setParentEventId(e.getParentEventId());
    	this.setStatus(e.getStatus());
    	this.setTemplates(e.getTemplates());
	}
	
	public int getEventID() {
		return eventID;
	}
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public int getParentEventId() {
		return parentEventId;
	}
	public void setParentEventId(int parentEventId) {
		this.parentEventId = parentEventId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public ArrayList<Template> getTemplates() {
		return templates;
	}
	public void setTemplates(ArrayList<Template> templates) {
		
		this.templates = templates;
		if (templates != null) {
			for (int i = 0; i < templates.size(); i++) {
				templates.get(i).setownerEventId(this.eventID);
				
			}
		}
	}

	public void setConcepts(ArrayList<KeywordGroup> concepts) {
		this.concepts = concepts;
		if (concepts != null) {
			for (int i = 0; i < concepts.size(); i++) {
				concepts.get(i).setOwnEventId(this.eventID);
				
			}
		}
	}

	public ArrayList<KeywordGroup> getConcepts() {
		return concepts;
	}

	
	public void setConceptMap(ConcurrentHashMap<String,KeywordGroup> conceptMap) {
		this.conceptMap = conceptMap;
	}
	
	public ConcurrentHashMap<String,KeywordGroup> getConceptMap() {
		return conceptMap;
	}

	/**
	 * 根据概念名获取概念
	 * @param name 概念名
	 * @return 概念类
	 */
	public KeywordGroup getConceptByName(String name) {
		return conceptMap.get(name);
	}
	
	
	/**
	 * 根据该事件中因pageId生存的表
	 * @param pageId 需要清理的页面id
	 */
	public void cleanMap(String pageId){
		for(String name:conceptMap.keySet()){
			conceptMap.get(name).getResultMaps().remove(pageId);
		}
//		System.gc();
	}
	

}
