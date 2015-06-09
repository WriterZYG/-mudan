package javabean;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import util.AnalysisProperties;
import util.AnalysisUtil;


/**
 * 记录分析过的所有单元
 * @author Bys
 *
 */
public class AnalysisUnits implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7121931133965367661L;
	private Vector <PagesUnit> analysisUnits;
	
	public AnalysisUnits(){
		analysisUnits = new Vector<PagesUnit>();
	}
	
	
	
	/**
	 * 按id从小到大的顺序插入新的单元，如果可以合并则合并相关单元
	 * @param unit
	 */
	public void add(PagesUnit unit){
		if (unit.getStart()>unit.getEnd()) {
			System.err.println((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())+":分析单元插入错误！");
			return;
		}
		boolean doMerge = false;
		if(analysisUnits.size() == 0){
			analysisUnits.add(unit);
		}
		else{
			//从后向前寻找可以插入的位子，从最后一个之后查找到第一个之后
			for(int i = analysisUnits.size()-1;i>=0;i--){
				if(analysisUnits.get(i).getStart()<unit.getStart()){

					//2范围有交叉
					if(analysisUnits.get(i).getEnd()>unit.getStart()){
						System.err.println((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())+":分析单元插入错误！");
						AnalysisProperties.storeRecord(this, (new SimpleDateFormat("MM-dd-HH-mm-ss")).format(new Date())+".log");
						unit.setStart(analysisUnits.get(i).getEnd()+1);
					}
						
					doMerge = false;
					//如果和后面一个单元邻接则合并2个单元
					if(i<analysisUnits.size()-1&&analysisUnits.get(i+1).getStart()==unit.getEnd()){
						analysisUnits.get(i+1).setStart(unit.getStart());
						analysisUnits.get(i+1).addUnanalysis(unit.getUnanalysis());
						doMerge = true;
					}
					//如果和前一个单元邻接也合并2个单元
					if(analysisUnits.get(i).getEnd()==unit.getStart()){
						if(!doMerge){
							analysisUnits.get(i).setEnd(unit.getEnd());
							analysisUnits.get(i).addUnanalysis(unit.getUnanalysis());
							doMerge = true;
						}
						//如果已经和后一个单元合并过了又可以和前一个合并，则合并3个单元
						else{
							analysisUnits.get(i+1).setStart(analysisUnits.get(i).getStart());
							analysisUnits.get(i+1).addUnanalysis(analysisUnits.get(i).getUnanalysis());
							analysisUnits.remove(i);
						}
					}
					//如果和前后都不能合并则直接插入
					if(!doMerge)
						analysisUnits.add(i+1, unit);
					return;
				}
			}
			//如果可以和第一条合并
			if(analysisUnits.get(0).getStart()==unit.getEnd()){
				analysisUnits.get(0).setStart(unit.getStart());
				analysisUnits.get(0).addUnanalysis(unit.getUnanalysis());
			}
			//如果和第一条记录有冲突
			else if(analysisUnits.get(0).getStart()<unit.getEnd()){
				System.err.println((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())+":分析单元插入错误！");
				AnalysisProperties.storeRecord(this, (new SimpleDateFormat("MM-dd-HH-mm-ss")).format(new Date())+".log");
				unit.setEnd(analysisUnits.get(0).getStart()-1);
			}
			else
				analysisUnits.add(0, unit);
		}
	}
	
	/**
	 * 从分析单元中移除一篇文档（因为此文档已被分析过）
	 * @param id 需要移除的文档id
	 * @param downloadDate 下载时间，用来查找该id文档所在单元
	 */
	public synchronized void removePage(String id,Date downloadDate){
		long time_num = AnalysisUtil.formatTimelong(downloadDate.getTime());
		System.out.println("id("+id+")准备清除！downloadDate:"+AnalysisUtil.getTimeString(downloadDate.getTime()));
		for(int i = analysisUnits.size()-1;i>=0;i--){
			if(time_num<=analysisUnits.get(i).getEnd()&&time_num>analysisUnits.get(i).getStart()){
				synchronized(analysisUnits.get(i).getUnanalysis()){
					if(!analysisUnits.get(i).getUnanalysis().contains(id)){
						System.err.println("id"+id+"不存在！"+analysisUnits.get(i).getUnanalysis().size());
						return;
					}
					else{
						analysisUnits.get(i).getUnanalysis().remove(analysisUnits.get(i).getUnanalysis().indexOf(id));
						System.out.println("id"+id+"已移除！"+analysisUnits.get(i).getUnanalysis().size());
						return;
					}
				}
			}
				
		}
//		System.err.println("id"+id+"不在范围内！");

	}
	
	public PagesUnit get(int index){
		return analysisUnits.get(index);
	}
	
	public int size(){
		return analysisUnits.size();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return analysisUnits.toString();
	}
}
