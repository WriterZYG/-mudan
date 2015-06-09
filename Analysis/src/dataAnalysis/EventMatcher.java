package dataAnalysis;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javabean.ConceptTable;
import javabean.MatchedResult;
import javabean.db.Event;
import javabean.db.KeywordGroup;
import javabean.db.Template;
/**
 * 根据传入的事件对一段文本片段进行匹配
 * @author syq
 */
public class EventMatcher {
	/**
	 * 当前的事件模板
	 */
	private Template t=null;
	/**
	 * 括号对应的默认距离
	 */
	private int parentheses_distance = 8;
	/**
	 * 加号对应的默认距离
	 */
	private int plus_distance = 8;
	
	/**
	 * 是否显示测试信息
	 */
    private boolean isTest = false;



	/**
	 * @return find函数根据事件里面模板挨个匹配，一旦匹配成功返回第一个。
	 */
    /**
     * 根据事件里面模板挨个匹配，一旦匹配成功返回第一个。
     * @param src 被匹配的原文件片段
     * @param e 当前匹配所用的事件
     * @param pageId 当前文本片段所属页面
     * @param templates 当前事件剩余模板
     * @return 匹配到的结果
     */
	public ArrayList<MatchedResult> find(String src,Event e,String pageId,ArrayList<Template> templates) {
		if(isTest){
			System.out.println(src+"::::"+e.getEventID());
			System.out.println("模版数量："+templates.size());
		}
		while(templates.size()>0)
		{
//			for(KeywordGroup k : templates.get(i).getConcepts())	//匹配模板包含的所有概念
//			{
//				getConceptmap(k,templates.get(i),src,used,pageId);
//			}
			

			ArrayList<String> op_queue = new ArrayList<String>();
			Stack<ConceptTable> table_stack = new Stack<ConceptTable>();
			String rule = templates.get(0).getTemplateRule();
			if(isTest)
				System.out.println("模版规则："+rule);
			Pattern templateP = Pattern.compile("(\\{\\d*?\\}|\\(|\\)|\\[|\\]|\\+)");
			String[] templaterule = templateP.split(rule);
			for(int i=0;i<templaterule.length;i++)
			{
				System.out.println(templaterule[i]);
			}
			int null_num = 0;
			//将字符串表示的概念转化为用table表示的概念,并倒序压入一个由table构成的表栈
			//同时将操作符按顺序加入一个队列
			if(isTest)
				System.out.print("概念：");
			boolean isFind = false;
			for(int rule_count=templaterule.length-1;rule_count>=0;rule_count--){
				if(templaterule[rule_count].length()==0){
					null_num++;
					continue;
				}
				isFind = false;
				if(e.getConceptMap().containsKey(templaterule[rule_count]))
				{
					KeywordGroup kwg = e.getConceptByName(templaterule[rule_count]);
					getConceptmap(kwg,src,pageId);

					if(!kwg.getResultMaps().containsKey(pageId)){
						System.err.println(pageId+"找不到\""+templaterule[rule_count]+"\"in\t"+rule);
					}
					table_stack.push(kwg.getResultMaps().get(pageId));
					if(isTest){
						System.out.print(table_stack.peek().getConceptName()+"\t");
					}
					isFind = true;
				}
				else if(!isFind&&WebPageAnalyzer.global_concept.containsKey(templaterule[rule_count])){
					KeywordGroup kwg = WebPageAnalyzer.global_concept.get(templaterule[rule_count]);
					getConceptmap(kwg,src,pageId);

					if(!WebPageAnalyzer.global_concept.get(templaterule[rule_count]).getResultMaps().containsKey(pageId)){
						System.err.println(pageId+"找不到（通用）"+templaterule[rule_count]);
					}
					table_stack.push(WebPageAnalyzer.global_concept.get(templaterule[rule_count]).getResultMaps().get(pageId));
					isFind = true;
					if(isTest)
						System.out.print(table_stack.peek().getConceptName()+"(通用)"+"\t");
				}
				if(!isFind)
					System.err.println("概念:\""+templaterule[rule_count]+"\"找不到");
			}
			if(isTest)
				System.out.println();
			if(table_stack.size()<(templaterule.length-null_num)){
				System.err.println(rule+"概念不全，无法匹配");
				templates.remove(0);			
				continue;
			}
//			System.out.println("栈大小"+table_stack.size());
				
			Matcher templateM = templateP.matcher(rule);
			while(templateM.find()){
				op_queue.add(templateM.group());
			}

//			System.out.print("操作符：");
//			for(String op_test:op_queue){
//				System.out.print(op_test+"\t");
//			}
//			System.out.print("\r\n");
			
			int op_count=0;
			while(op_count<op_queue.size()){
				if(op_queue.get(op_count).equals("(")){
					op_count+=doLittleParentheses(op_queue,op_count,table_stack);
				}
				else if(op_queue.get(op_count).equals("[")){
					op_count+=doMidParentheses(op_queue,op_count,table_stack);
				}
				else{
					op_count+=doPlus(op_queue,op_count,table_stack);
				}
			}
			//某次运算已经为空则直接结束本次匹配
			if(op_count>op_queue.size()){				
				templates.remove(0);			
				continue;
			}
			ConceptTable resultTable= table_stack.pop();
			ArrayList<MatchedResult> resultList = resultTable.getStartResultMaps();
			t = templates.get(0);
			for(MatchedResult mr:resultList){
				mr.setT(t);
			}
			if(resultList.size()>0){
				System.out.println("匹配到模版："+resultTable.getConceptName()+"结果为:"+src.substring(resultList.get(0).getStart(),resultList.get(0).getEnd()));
				templates.remove(0);
				return resultList;
			}
			templates.remove(0);			
		}
		if(isTest)
			System.out.println("没有匹配到事件");
		return null;
		
		
		
			
//		
//			Pattern p = Pattern.compile("\\[(.*?)\\]\\(.*?\\)");
//			Matcher m = p.matcher(templates.get(i).getTemplateRule());
//			while(m.find())
//			{
//				String concept_one =m.group(1);
//				String concept_two =m.group(2);
//				KeywordGroup one = null,two=null;
//				for(KeywordGroup k : templates.get(i).getConcepts())	
//				{
//					if(k.getKeywordGroupName().equals(concept_one))
//					{
//						one = k;continue;
//					}
//					if(k.getKeywordGroupName().equals(concept_two))
//					{
//						two = k;
//					}
//				}
//				HashMap<String,MatchedResult> one_maps =one.getStartResultMaps();
//				HashMap<String,MatchedResult> two_maps =one.getStartResultMaps();	
//			}
			
			

	}
	
	/**
	 * @author Bys
	 * @param op_queue
	 * @param op_count
	 * @param table_stack
	 * @return 从表栈取出对应操作数完成[A+B+…](C)的操作，将结果压入栈。返回操作用掉操作符个数
	 */
	private int doMidParentheses(ArrayList<String> op_queue,int op_count,Stack<ConceptTable> table_stack){
		if(op_count>op_queue.size())
			return op_count;
//		System.out.println("栈大小"+table_stack.size());
		int offset = 1;
		while(!op_queue.get(op_count+offset).equals("]")){
			if(op_queue.get(op_count+offset).equals("(")){
				offset+=doLittleParentheses(op_queue,op_count+offset,table_stack);
			}
			else{
				offset+=doPlus(op_queue,op_count+offset,table_stack);
			}
			if(op_count+offset>op_queue.size())
				return op_queue.size()+1;
		}
		ConceptTable tmpTable = table_stack.pop();
		if(!isTest&&tmpTable.getStartResultMaps().size()==0){
			System.out.println("\""+tmpTable.getConceptName()+"\"匹配为空！");
			return op_queue.size()+1;
		}
		tmpTable.setConceptName("["+tmpTable.getConceptName()+"]");
		if(isTest)
			System.out.println(tmpTable.getConceptName());
		table_stack.push(tmpTable);
		offset++;
		if(!op_queue.get(op_count+offset).equals("(")){
			System.err.println("表达式错误![]后无()");
			return offset;
		}
		else{
			offset+=doLittleParentheses(op_queue,op_count+offset,table_stack);
			return offset;
		}
	}
	
	
	/**
	 * @author Bys
	 * @param op_queue
	 * @param op_count
	 * @param table_stack
	 * @return 当出现加号开始一直往后进行加，直到碰到]或者操作符用尽
	 */
	private int doPlus(ArrayList<String> op_queue,int op_count,Stack<ConceptTable> table_stack){
		if(op_count>op_queue.size())
			return op_count;
		
		
//		System.out.println("栈大小"+table_stack.size());
		int offset = 1;
		ConceptTable tmpTable = new ConceptTable();
		if((op_count+offset)==op_queue.size()||op_queue.get(op_count+offset).equals("]")||op_queue.get(op_count+offset).equals(")")){
			tmpTable = table_stack.pop();
			if(op_queue.get(op_count).equals("+"))
				table_stack.push(plus_op(tmpTable,table_stack.pop()));
			else{
				int distance = Integer.parseInt(op_queue.get(op_count).replaceAll("\\{|\\}", ""));
				table_stack.push(plus_op(tmpTable,table_stack.pop(),distance));
//				offset++;
			}
			tmpTable = table_stack.peek();
			if(!isTest&&tmpTable.getStartResultMaps().size()==0){
//				System.out.println("\""+tmpTable.getConceptName()+"\"匹配为空！");
				return op_queue.size()+1;
			}
			return offset;
		}
		else if(op_queue.get(op_count+offset).equals("(")){
			tmpTable = table_stack.pop();
			if(!isTest&&tmpTable.getStartResultMaps().size()==0){
//				System.out.println("\""+tmpTable.getConceptName()+"\"匹配为空！");
				return op_queue.size()+1;
			}
			offset+=doLittleParentheses(op_queue,op_count+offset,table_stack);
			if((op_count+offset)==op_queue.size()||op_queue.get(op_count+offset).equals("]")){
				return offset;
			}
			else{
				offset+=doPlus(op_queue,op_count+offset,table_stack);
				return offset;
			}
		}
		else if(op_queue.get(op_count+offset).equals("[")){
			tmpTable = table_stack.pop();
			if(!isTest&&tmpTable.getStartResultMaps().size()==0){
//				System.out.println("\""+tmpTable.getConceptName()+"\"匹配为空！");
				return op_queue.size()+1;
			}
			offset+=doMidParentheses(op_queue,op_count+offset,table_stack);
			if(op_queue.get(op_count).equals("+"))
				table_stack.push(plus_op(tmpTable,table_stack.pop()));
			else{
				int distance = Integer.parseInt(op_queue.get(op_count).replaceAll("\\{|\\}", ""));
				table_stack.push(plus_op(tmpTable,table_stack.pop(),distance));
				offset++;
			}
			if((op_count+offset)==op_queue.size()||op_queue.get(op_count+offset).equals("]")){
				return offset;
			}
			else{
				offset+=doPlus(op_queue,op_count+offset,table_stack);
				return offset;
			}
		}
		else{
			tmpTable = table_stack.pop();
			if(!isTest&&tmpTable.getStartResultMaps().size()==0){
//				System.out.println("\""+tmpTable.getConceptName()+"\"匹配为空！");
				return op_queue.size()+1;
			}
			offset+=doPlus(op_queue,op_count+offset,table_stack);
			if(op_queue.get(op_count).equals("+"))
				table_stack.push(plus_op(tmpTable,table_stack.pop()));
			else{
				int distance = Integer.parseInt(op_queue.get(op_count).replaceAll("\\{|\\}", ""));
				table_stack.push(plus_op(tmpTable,table_stack.pop(),distance));
				offset++;
			}
			return offset;
		}
	}
	
	
	/**
	 * @author Bys
	 * @param op_queue
	 * @param op_count
	 * @param table_stack
	 * @return 从表栈中取出2个操作数完成A(B)或者A({n}B)操作，结果压入栈
	 */
	private int doLittleParentheses(ArrayList<String> op_queue,int op_count,Stack<ConceptTable> table_stack){
		if(op_count>op_queue.size())
			return op_count;
//		System.out.println("栈大小"+table_stack.size());
		int offset = 1;
		ConceptTable tmpTable = new ConceptTable();
		if(op_queue.get(op_count+offset).equals(")")){
			tmpTable = parentheses_op(table_stack.pop(),table_stack.pop());
			if(!isTest&&tmpTable.getStartResultMaps().size()==0){
//				System.out.println("\""+tmpTable.getConceptName()+"\"匹配为空！");
				return op_queue.size()+1;
			}
			table_stack.push(tmpTable);
			offset++;
			return offset;
		}
		else if(op_queue.get(op_count+offset).substring(0, 1).equals("{")){
			int distance = Integer.parseInt(op_queue.get(op_count+offset).replaceAll("\\{|\\}", ""));
			tmpTable = parentheses_op(table_stack.pop(),table_stack.pop(),distance);
			if(!isTest&&tmpTable.getStartResultMaps().size()==0){
//				System.out.println("\""+tmpTable.getConceptName()+"\"匹配为空！");
				return op_queue.size()+1;
			}
			table_stack.push(tmpTable);
			offset+=2;
			return offset;
		}
		else if(op_queue.get(op_count+offset).equals("+")){
			tmpTable = table_stack.pop();
			offset+=doPlus(op_queue,op_count+offset,table_stack);
			tmpTable = parentheses_op(tmpTable,table_stack.pop());
			if(!isTest&&tmpTable.getStartResultMaps().size()==0){
//				System.out.println("\""+tmpTable.getConceptName()+"\"匹配为空！");
				return op_queue.size()+1;
			}
			table_stack.push(tmpTable);
			offset++;
			return offset;
		}
		System.err.println("小括号内有其他运算符！");
		offset++;
		return offset;
	}
	
	
	/**
	 * @author Bys
	 * @param ct1 小括号运算符的外操作数。为一张位置表
	 * @param ct2 小括号运算符的内操作数。为一张位置表
	 * @return 结果位置表，满足A(B)的概念出现的位置,缺省距离parentheses_distance=8
	 */
	private ConceptTable parentheses_op(ConceptTable ct1,ConceptTable ct2){
		ConceptTable result = parentheses_op(ct1,ct2,parentheses_distance);
		result.setConceptName(ct1.getConceptName()+"("+ct2.getConceptName()+")");
		if(isTest)
			System.out.println(result.getConceptName());
		return result;
	}
	
	
	/**
	 * @author Bys
	 * @param ct1 小括号运算符的外操作数。为一张位置表
	 * @param ct2 小括号运算符的内操作数。为一张位置表
	 * @param distance 2操作数之间的距离
	 * @return 结果位置表，满足A({distance}B)的概念出现的位置
	 */
	private ConceptTable parentheses_op(ConceptTable ct1,ConceptTable ct2,int distance){
		ArrayList<MatchedResult> resultList = new ArrayList<MatchedResult>(0);
		boolean isStart = false;
		MatchedResult scope = new MatchedResult();
		for(MatchedResult mr:ct1.getStartResultMaps()){
			isStart = false;
			scope.setStart(0);
			scope.setEnd(mr.getEnd()+distance);
			
			for(MatchedResult mr2:ct2.getStartResultMaps()){
				if(mr.cross(mr2)){
					continue;
				}
//				如果不在范围内，且曾经开始过，也就是超出范围，则其后项必定也超出范围
				else if(!scope.contains(mr2)){
					if(isStart)
						break;
					}
//				如果mr2与mr扩展后的范围有交集，则将mr与mr2合并，设开始标记为真
				else{
					if(mr2.getEnd()>=mr.getStart()-distance){
						MatchedResult temp = new MatchedResult(mr.getStart()<mr2.getStart()?mr.getStart():mr2.getStart(),
								mr.getEnd()>mr2.getEnd()?mr.getEnd():mr2.getEnd());
//						if(temp.getEnd()-temp.getStart()>20)
//							System.err.println("????");
						if(temp.getEnd()-temp.getStart()<30){
							resultList.add(temp);
							isStart = true;
						}
					}
				}
			}
		}
		scope = null;
		if(isTest&&distance!=parentheses_distance)
			System.out.println(ct1.getConceptName()+"({"+distance+"}"+ct2.getConceptName()+")");
		return new ConceptTable(resultList,ct1.getConceptName()+"({"+distance+"}"+ct2.getConceptName()+")");
	}
	
	/**
	 * @author Bys
	 * @param ct1 加法运算符的左操作数。为一张位置表
	 * @param ct2 加法运算符的右操作数。为一张位置表
	 * @return 结果位置表，满足A+B的概念出现的位置。缺省值plus_distance=8
	 */
	private ConceptTable plus_op(ConceptTable ct1,ConceptTable ct2){
		ConceptTable result = plus_op(ct1,ct2,plus_distance);
		result.setConceptName(ct1.getConceptName()+"+"+ct2.getConceptName());
		if(isTest)
			System.out.println(result.getConceptName());
		return result;
	}
	
	/**
	 * @author Bys
	 * @param ct1 加法运算符的左操作数。为一张位置表
	 * @param ct2 加法运算符的右操作数。为一张位置表
	 * @param distance 加法对应的2操作数之间的距离
	 * @return 结果位置表，满足A{distance}B的概念出现的位置
	 */
	private ConceptTable plus_op(ConceptTable ct1,ConceptTable ct2,int distance){
		ArrayList<MatchedResult> resultList = new ArrayList<MatchedResult>(0);
		boolean isStart = false;
		MatchedResult scope = new MatchedResult();
		for(MatchedResult mr:ct1.getStartResultMaps()){
			isStart = false;
			scope.setStart(mr.getEnd());
			scope.setEnd(mr.getEnd()+distance);
			
			for(MatchedResult mr2:ct2.getStartResultMaps()){
				if(mr.cross(mr2)){
					continue;
				}
//				如果不在范围内，且曾经开始过，也就是超出范围，则其后项必定也超出范围
				if(!scope.contains(mr2)){
					if(isStart)
						break;
				}
//				如果mr2与mr扩展后的范围有交集，则将mr与mr2合并，设开始标记为真
				else{
					MatchedResult temp = new MatchedResult(mr.getStart()<mr2.getStart()?mr.getStart():mr2.getStart(),
							mr.getEnd()>mr2.getEnd()?mr.getEnd():mr2.getEnd());						
//					if(temp.getEnd()-temp.getStart()>20)
//								System.err.println("????");
					if(temp.getEnd()-temp.getStart()<30){
						resultList.add(temp);
						isStart = true;
					}
				}
			}
		}
		scope = null;
		if(isTest&&distance!=plus_distance)
			System.out.println(ct1.getConceptName()+"{"+distance+"}"+ct2.getConceptName());
		return new ConceptTable(resultList,ct1.getConceptName()+"{"+distance+"}"+ct2.getConceptName());
	}
	
	
	/**
	 * 获取概念与文本匹配映射
	 * @param k 概念
	 * @param src 被分析文本 
	 * @param pageId 
	 */
    public void getConceptmap(KeywordGroup k,String src,String pageId)   //获取概念的文本匹配映射
    {
		ArrayList<MatchedResult> concept_result = new ArrayList<MatchedResult>(0);
    	if(k.getResultMaps().containsKey(pageId)){
//    		if(!WebPageAnalyzer.usedMap.containsKey(pageId))
//        		System.err.println("?????????????????????????????????????????????????????????????????????");

//    		if(WebPageAnalyzer.conceptMap.containsKey(k.getKeywordGroupName()))
//    			System.err.println(pageId+"已有一般概念"+k.getKeywordGroupName());
//    		else if(WebPageAnalyzer.global_concept.containsKey(k.getKeywordGroupName()))
//    			System.err.println(pageId+"已有通用概念"+k.getKeywordGroupName());
    		return;
    	}
    	else
    	{

//    		System.err.println(k.equals(WebPageAnalyzer.conceptMap.get(k).getKeywordGroupName()));
    		if(k.getChildKeywordGroup().size()>0)
    		{
    		   List<KeywordGroup> childs =k.getChildKeywordGroup();
    		   for(KeywordGroup ck:childs)
    		   {
    			   getConceptmap(ck,src,pageId);
    			   ConceptTable childTable = ck.getResultMaps().get(pageId);
    			   concept_result.addAll(childTable.getStartResultMaps());
    		   }
    		}
    		else
    		{
    			Pattern p = Pattern.compile(k.generateExp());
    			Matcher m = p.matcher(src);
    			while(m.find())
    			{
    				concept_result.add(new MatchedResult(m.start(),m.end()));
    				if(true){
    					System.out.println("概念："+k.getKeywordGroupName());
    					System.out.println("匹配到关键词："+m.group()+m.start()+"-"+m.end());
    				}
    			}
    		}    		
//    		Vector<String> used = WebPageAnalyzer.usedMap.get(pageId);
//    		if(used.contains(k.getKeywordGroupName()))        		
//    			System.err.println("?????????????????????????????????????????????????????????????????????");

//    		if(!used.contains(k.getKeywordGroupName()))
//    			used.add(k.getKeywordGroupName());
    		k.getResultMaps().put(pageId, new ConceptTable(concept_result,k.getKeywordGroupName()));
//    		System.err.println(k.getKeywordGroupName()+":"+k.getResultMaps().size());

    	}
    }
    
    

}
