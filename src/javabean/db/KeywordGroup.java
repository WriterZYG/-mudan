package javabean.db;

import java.util.*;
import java.util.concurrent.*;

import javabean.ConceptTable;
import javabean.Keyword;

import util.AnalysisProperties;

import dataAnalysis.WithReglExpr;

/**
 * 概念类，包括概念名和一组概念，以及概念在模板中的顺序
 * @author syq
 */

public class KeywordGroup implements WithReglExpr{

	static AnalysisProperties aprop = AnalysisProperties.getInstance(); 

	private int ownEventId;
	private int keywordGroupID;
	private String keywordGroupName;
	private int sequence;// 表示词组的顺序
	private int type = 0;     //标记概念是本体还是数据库定义的
	private String preForbidden;
	private String postForbidden;
	private ArrayList<Keyword> keywords = new ArrayList<Keyword>();
	private List<KeywordGroup> childKeywordGroup= new ArrayList<KeywordGroup>();
//	private HashMap<String,MatchedResult> resultMaps = new HashMap<String,MatchedResult>();
	private ConcurrentHashMap<String,ConceptTable> resultMaps = new ConcurrentHashMap<String,ConceptTable>(0);

private int parentKeywordGroupId;

//	private ArrayList<String> tags=new ArrayList<String>();
	
	public KeywordGroup() {}
	public KeywordGroup(int keywordGroupID, String keywordGroupName,
			int sequence, ArrayList<Keyword> keywords,int parentKeywordGroupId) {
		super();
		this.keywordGroupID = keywordGroupID;
		this.keywordGroupName = keywordGroupName;
		this.sequence = sequence;
		this.keywords = keywords;
		this.parentKeywordGroupId=parentKeywordGroupId;
		
	}
	/**
	 * @author syq
	 * 生成概念的正则表达式
	 */
	public String generateExp() {
		if (keywords.size() > 0) {
			String exp = "(";
			for (Keyword keyword : keywords) {
				String s = keyword.getKeywordName();
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

	public ArrayList<Keyword> getKeywords() {
		return keywords;
	}
	public int getKeywordGroupID() {
		return keywordGroupID;
	}

	public void setKeywordGroupID(int keywordGroupID) {
		this.keywordGroupID = keywordGroupID;
	}

	public String getKeywordGroupName() {
		return keywordGroupName;
	}

	public void setKeywordGroupName(String keywordGroupName) {
		this.keywordGroupName = keywordGroupName;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setKeywords(ArrayList<Keyword> keywords) {
		this.keywords = keywords;
	}
	public String getPreForbidden() {
		return preForbidden;
	}
	public void setPreForbidden(String preForbidden) {
		this.preForbidden = preForbidden;
	}
	public String getPostForbidden() {
		return postForbidden;
	}
	public void setPostForbidden(String postForbidden) {
		this.postForbidden = postForbidden;
	}
	public void setParentKeywordGroupId(int parentKeywordGroupId) {
		this.parentKeywordGroupId = parentKeywordGroupId;
	}
	public int getParentKeywordGroupId() {
		return parentKeywordGroupId;
	}
	public void setOwnEventId(int ownEventId) {
		this.ownEventId = ownEventId;
	}
	public int getOwnEventId() {
		return ownEventId;
	}
	public void setResultMaps(ConcurrentHashMap<String,ConceptTable> resultMaps) {
		this.resultMaps = resultMaps;
	}
	public ConcurrentHashMap<String,ConceptTable> getResultMaps() {
		return resultMaps;
	}
	public void setChildKeywordGroup(List<KeywordGroup> childKeywordGroup) {
		this.childKeywordGroup = childKeywordGroup;
	}
	public List<KeywordGroup> getChildKeywordGroup() {
		return childKeywordGroup;
	}
}
