package javabean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * 概念表。存储某个概念在文中出现的位置
 * @author Bys
 *
 */
public class ConceptTable {
	private ArrayList<MatchedResult> startResultMaps;
//	private ArrayList<MatchedResult> endResultMaps;
	private int size;
	private String conceptName;
	
	public ConceptTable(){
		startResultMaps = new ArrayList<MatchedResult>(0);
//		endResultMaps = new ArrayList<MatchedResult>(0);
		size = 0;
		conceptName = "";
	}
	
	public ConceptTable(ArrayList<MatchedResult> mrList,String conceptName){
		setConceptTable(mrList,conceptName);
	}
	
	public void setConceptTable(ArrayList<MatchedResult> mrList,String conceptName){
		startResultMaps = new ArrayList<MatchedResult>(mrList);
//		endResultMaps = new ArrayList<MatchedResult>(mrList);
		
		Collections.sort(startResultMaps,new Comparator<MatchedResult>(){   
		    public int compare(MatchedResult mr1, MatchedResult mr2) {      
		        return (mr1.getStart() - mr2.getStart()); 
		    }
		}); 
		
//		Collections.sort(endResultMaps,new Comparator<MatchedResult>(){   
//		    public int compare(MatchedResult mr1, MatchedResult mr2) {      
//		        return (mr2.getEnd() - mr1.getEnd()); 
//		    }
//		}); 
		size = startResultMaps.size();
		this.conceptName = conceptName;
	}
	
	
	public void setStartResultMaps(ArrayList<MatchedResult> startResultMaps) {
		this.startResultMaps = startResultMaps;
	}
	public ArrayList<MatchedResult> getStartResultMaps() {
		return startResultMaps;
	}
	
//	public void setEndResultMaps(ArrayList<MatchedResult> EndResultMaps) {
//		this.endResultMaps = EndResultMaps;
//	}
//	public ArrayList<MatchedResult> getEndResultMaps() {
//		return endResultMaps;
//	}
	
	public void setConceptName(String conceptName){
		this.conceptName = conceptName;
	}
	
	public String getConceptName(){
		return conceptName;
	}
	
	public int size(){
		return size;
	}
}
