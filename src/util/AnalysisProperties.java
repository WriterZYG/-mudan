package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javabean.AnalysisUnits;

/**
 * 分析程序配置读取器
 * @author Bys
 *
 */
public class AnalysisProperties {

	private static AnalysisProperties instance;

	private  boolean polarityJudge;
	private  int provinceId;
	private  String mustContainWords;
	private String tablePrefix;
	private String databaseType = "mysql";
	private String postMatchClass;
	private boolean debug = false;
	private String eventName = "all";
	private boolean loopOnce = true;
	private int refreshTime;//程序刷新时间,单位为s
	private boolean all_dup=true;
	private String logLocaltion = "/file/AnalysisLog/";
	private int ThreadsNum = 5;
	private int maxTryNum = 3;
	private int timeGap = 10;
	private String regionConfigPath;
	private String docUrl;
	private String dirPath = "";
	private int cacheDays = 2;
	private int cacheNum=128;
	private int appNum=1;
	private boolean testTime = true;
	private boolean old=false;
	public boolean isOld() {
		return old;
	}

	public void setOld(boolean old) {
		this.old = old;
	}

	public boolean isTestTime() {
		return testTime;
	}

	public void setTestTime(boolean testTime) {
		this.testTime = testTime;
	}

	public String getDatabaseType() {
		return databaseType;
	}

	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}

	private AnalysisProperties() {
		
	}
	
	/**
	 * 读取配置文件获取分析配置的单例
	 * @return 分析配置的单例
	 */
	public static AnalysisProperties getInstance() {
		if (instance==null) {

			instance = new AnalysisProperties();
			Properties props = new Properties();	
			FileInputStream fis=null;
			InputStreamReader reader;
			try {
				instance.dirPath = AnalysisProperties.class.getResource("").getPath();
				if(instance.dirPath.contains("jar"))
					instance.dirPath = instance.dirPath.split("file:")[1].split("Analysis\\.jar")[0];
				else
					instance.dirPath = "";
				fis = new FileInputStream(instance.dirPath+"analysis.properties");
				reader = new InputStreamReader(fis, "utf-8");
				props.load(reader);
				//			reader.close();
				if (fis!=null) 
					fis.close();	
				String mc = props.getProperty("mustContain");
				if (mc!=null)
				instance.setMustContainWords (mc);
				instance.setPolarityJudge (Boolean.parseBoolean(props.getProperty("polarityJudge", "false")));	
				instance.setProvinceId (Integer.parseInt( props.getProperty("provinceId", "0") ));	
				instance.setTablePrefix(props.getProperty("tablePrefix", ""));
				instance.setDatabaseType(props.getProperty("databaseType", "mysql"));
				instance.setPostMatchClass(props.getProperty("postMatcherClass"));
				instance.setDebug(Boolean.parseBoolean(props.getProperty("debug", "false")));
				instance.setEventName(props.getProperty("eventName","all"));
				instance.setLoopOnce(Boolean.parseBoolean(props.getProperty("loopOnce")));
				instance.setRefreshTime(Integer.parseInt( props.getProperty("refreshTime")));
				instance.setAll_dup(Boolean.parseBoolean( props.getProperty("all_dup")));
				instance.setThreadsNum(Integer.parseInt(props.getProperty("ThreadsNum")));
				instance.setCacheDays(Integer.parseInt(props.getProperty("cacheDays")));
				instance.setCacheDays(Integer.parseInt(props.getProperty("cacheNum")));//
				instance.setAppNum(Integer.parseInt(props.getProperty("AppNum")));//
				instance.setLogLocaltion(props.getProperty("logLocaltion"));
				instance.setMaxTryNum(Integer.parseInt(props.getProperty("maxTryNum")));
				instance.setTimeGap(Integer.parseInt(props.getProperty("timeGap")));
				instance.setRegionConfigPath(props.getProperty("regionConfigPath"));
				instance.setDocUrl(props.getProperty("docUrl"));
				instance.setTestTime(Boolean.parseBoolean(props.getProperty("testTime", "true")));
				instance.setOld(Boolean.parseBoolean(props.getProperty("old", "false")));
				SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd"); 

				// Redirect output stream
				if (AnalysisProperties.getInstance().isDebug() == false) {
					FileOutputStream outfile = new FileOutputStream(instance.getLogLocaltion()+"/analysis"+df.format(new Date())+".log",true);
					PrintStream ps = new PrintStream(outfile);
					FileOutputStream errfile = new FileOutputStream(instance.getLogLocaltion()+"/error"+df.format(new Date())+".log",true);
					PrintStream psErr = new PrintStream(errfile);
					System.setOut(ps);
					System.setErr(psErr);
				}		
				System.out.println("------------------------------------------------------------------------");
				System.out.println("new process start at "+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
				System.out.println("analysis.properties loaded!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	public static void storeRecord(AnalysisUnits analysisUnits){
		try {
			File save = new File("analysis.save");
			FileOutputStream fos = new FileOutputStream(save);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(analysisUnits);
			oos.close();
			fos.close();
			System.out.println("分析进度存储成功！");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void storeRecord(AnalysisUnits analysisUnits,String errorLog){
		try {
			File errorDir = new File("error/");
			if(!errorDir.exists())
				errorDir.mkdir();
			File save = new File("error/"+errorLog);
			FileOutputStream fos = new FileOutputStream(save);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(analysisUnits);
			oos.close();
			fos.close();
			System.out.println("分析进度存储成功！");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public  void setPolarityJudge(boolean polarityJudge) {
		this.polarityJudge = polarityJudge;
	}
	public  boolean isPolarityJudge() {
		return polarityJudge;
	}
	public  int getProvinceId() {
		return provinceId;
	}
	public  void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
	public  String getMustContainWords() {
		return mustContainWords;
	}
	public  void setMustContainWords(String mustContainWords) {
		this.mustContainWords = mustContainWords;
	}


	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}

	public String getTablePrefix() {
		return tablePrefix;
	}


	public void setPostMatchClass(String postMatchClass) {
		this.postMatchClass = postMatchClass;
	}

	public String getPostMatchClass() {
		return postMatchClass;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventName() {
		return eventName;
	}

	public void setLoopOnce(boolean loopOnce) {
		this.loopOnce = loopOnce;
	}

	public boolean isLoopOnce() {
		return loopOnce;
	}

	public void setRefreshTime(int refreshTime) {
		this.refreshTime = refreshTime;
	}

	public int getRefreshTime() {
		return refreshTime;
	}

	public boolean isAll_dup() {
		return all_dup;
	}

	public void setAll_dup(boolean all_dup) {
		this.all_dup = all_dup;
	}


	public int getThreadsNum() {
		return ThreadsNum;
	}

	public void setThreadsNum(int threadsNum) {
		this.ThreadsNum = threadsNum;
	}
	
	public int getMaxTryNum() {
		return maxTryNum;
	}

	public void setMaxTryNum(int maxTryNum) {
		this.maxTryNum = maxTryNum;
	}	

	public int getTimeGap() {
		return timeGap;
	}

	public void setTimeGap(int timeGap) {
		this.timeGap = timeGap;
	}

	public void setLogLocaltion(String logLocaltion) {
		this.logLocaltion = logLocaltion;
	}

	public String getLogLocaltion() {
		return logLocaltion;
	}

	public String getDirPath() {
		return dirPath;
	}

	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}

	public String getRegionConfigPath() {
		return regionConfigPath;
	}

	public void setRegionConfigPath(String regionConfigPath) {
		this.regionConfigPath = regionConfigPath;
	}

	public String getDocUrl() {
		return docUrl;
	}

	public void setDocUrl(String docUrl) {
		this.docUrl = docUrl;
	}

	public int getCacheDays() {
		return cacheDays;
	}

	public void setCacheDays(int cacheDays) {
		this.cacheDays = cacheDays;
	}

	public int getCacheNum() {
		return cacheNum;
	}

	public void setCacheNum(int cacheNum) {
		this.cacheNum = cacheNum;
	}
	
	public int getAppNum() {
		return appNum;
	}

	public void setAppNum(int appNum) {
		this.appNum = appNum;
	}
	
	


}
