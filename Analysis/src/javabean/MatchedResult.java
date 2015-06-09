package javabean;

import javabean.db.Template;


/**
 * 抽取到的concept或者模板在文本中的起始位置
 * @author syq 
 */
public class MatchedResult {
	private int start;
	private int end;
	private Template t;
	
	public MatchedResult()
    {
    	this.start=-1;
    	this.end=-1;
    	t = new Template();
    }
	
    public MatchedResult(int start,int end)
    {
    	this.start=start;
    	this.end=end;
    }
	
    public MatchedResult(int start,int end,Template t)
    {
    	this.start=start;
    	this.end=end;
    	this.t = t;
    }
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}
	
	public Template getT() {
		return t;
	}

	public void setT(Template t) {
		this.t = t;
	}

	public boolean contains(MatchedResult mr){
		if(mr.getEnd()<=this.end+mr.getEnd()-mr.getStart()&&mr.getStart()>=this.start)
			return true;
		else
			return false;
	}
	
	/*
	 * mr的开始大于自己的开始并且小于自己的结束，返回真
	 * mr的开始小于自己的开始并且mr的结束大于自己的开始，返回真
	 * 
	 * 举例：          start      end
	 *   mr:      start  
	 *     true
	 *     
	 *           start    end
	 *   mr:  start    end    (end)*
	 *     true
	 *     */
	public boolean cross(MatchedResult mr){
		if(mr.start>=this.start&&mr.start<this.end)
			return true;
		else if(mr.start<this.start&&mr.end>this.start)
			return true;
		else
			return false;
	}
	
}
