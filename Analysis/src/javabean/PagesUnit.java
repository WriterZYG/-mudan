package javabean;

import java.io.Serializable;
import java.util.Vector;

import util.AnalysisUtil;


/**
 * 分析获取文章的基本单元，包含分析范围的起止id和此范围内未分析的文章id
 * @author Bys
 *
 */
public class PagesUnit implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9158343477498981489L;
	private long start;
	private long end;
	private Vector<String> unanalysis;
	
	public PagesUnit(long start,long end){
		start = AnalysisUtil.formatTimelong(start);
		end = AnalysisUtil.formatTimelong(end);
		
		if(start <= end){
			this.start = start;
			this.end = end;
		}
		else{
			this.start = end;
			this.end = start;
		}
		unanalysis = new Vector<String>();
	}
	
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	
	public long getEnd() {
		return end;
	}
	public void setEnd(long end) {
		this.end = end;
	}
	
	public void addUnanalysis(String id){
		if(!unanalysis.contains(id))
			unanalysis.add(id);
		else{
			System.err.println(id+"("+id+") between ("+AnalysisUtil.getTimeString(start)+","+AnalysisUtil.getTimeString(end)+")已经存在！");
			System.err.println("属于范围"+AnalysisUtil.getTimeString(start)+"-"+AnalysisUtil.getTimeString(end));
		}
	}
	
	public void addUnanalysis(Vector<String> unanalysis){
		for(String id:unanalysis){
		if(!this.unanalysis.contains(id))
			this.unanalysis.add(id);
		}
	}
	
	public Vector<String> getUnanalysis(){
		return unanalysis;
	}

	
	
//	public void setUnanalysis(List<Integer> unanalysis){
//		this.unanalysis =  Collections.synchronizedList(unanalysis);
//	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return AnalysisUtil.getTimeString(start)+"-"+AnalysisUtil.getTimeString(end);
	}
}
