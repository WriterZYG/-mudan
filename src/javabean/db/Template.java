package javabean.db;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dataAnalysis.WithReglExpr;
/**
 * 模板类，包括模板名，模板的倾向性，模板对应的事件，模板中倾向性词语的位置，模板对应的规则
 * @author syq
 * 
 */
public class Template implements WithReglExpr{
    private int templateId;	
	private String templateName;	
	private int templatePolarity;	
	private int polarityWordPosition ;
	private String templateRule;	
	private int ownerEventId;
	private ArrayList<KeywordGroup> concepts=new ArrayList<KeywordGroup>();
	public Template() {}
	public Template(int templateId, String templateName,String templateRule)
	{
		super();
		this.templateId = templateId;
		this.templateName = templateName;
		this.templateRule=templateRule;
	}
	

//	public String generateExp() {
//		String exp = null;
//		if (ownerEvent.getStatus()==0) {
//			if (templateRule.getGroups().size() > 0) {
//				//exp = "(.{0,30}(";
//				exp="(";
//	
//				for (KeywordGroup kg : templateRule.getGroups()) {
//					if(kg.generateExp()!=null){
//					if(kg!=templateRule.getGroups().get(templateRule.getGroups().size()-1))  exp += kg.generateExp() + "(.{0,8}?)";
//					//if(kg!=groups.get(groups.size()-1))  exp += kg.generateExp() ;
//					else  exp += kg.generateExp();
//					}
//				}
//				exp +=  ")";
//			//	exp +=  ").{0,30})";
//			}
//		}
//		else if( ownerEvent.getStatus()==1 ) {
//			if (templateRule.getGroups().size() > 0) {
//				//exp = "(.{0,30}?(";
//				exp="(";
//				for (KeywordGroup kg : templateRule.getGroups()) {
//					//System.out.println("kg"+kg.generateExp());
//					if(kg.generateExp()!=null){
//					if(kg!=templateRule.getGroups().get(templateRule.getGroups().size()-1))  exp += kg.generateExp() + "(.{0,5}?)";
//					else  exp += kg.generateExp();
//					}
//				}
//				exp +=  ")";
//				//exp +=  ").{0,30}?)";
//			}
//		}
//		return exp;
//	}
	@Override
	/**修改
	 * 生成正则表达式
	 * @return
	 * @author syq
	 */
	public String generateExp() {
		String exp = null;		
		templateRule=templateRule.replaceAll("\\+","{8}");
		Pattern p=Pattern.compile("\\{(\\d*?)\\}");
		Matcher m = p.matcher(templateRule);
		while(m.find()){
		templateRule=m.replaceAll("(.{0,"+m.group(1)+"}?)");}		
		exp = templateRule;
        return exp;
	}
	public int getTemplateId() {
		return templateId;
	}
	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public int getTemplatePolarity() {
		return templatePolarity;
	}
	public void setTemplatePolarity(int templatePolarity) {
		this.templatePolarity = templatePolarity;
	}
	public int getPolarityWordPosition() {
		return polarityWordPosition;
	}
	public void setPolarityWordPosition(int polarityWordPosition) {
		this.polarityWordPosition = polarityWordPosition;
	}
	public String getTemplateRule() {
		return templateRule;
	}
	public void setTemplateRule(String templateRule) {
		this.templateRule = templateRule;
	}
	public int getownerEventId() {
		return ownerEventId;
	}
	public void setownerEventId(int ownerEventId) {
		this.ownerEventId = ownerEventId;
	}
	public void setConcepts(ArrayList<KeywordGroup> concepts) {
		this.concepts = concepts;
	}
	public ArrayList<KeywordGroup> getConcepts() {
		return concepts;
	}
}
