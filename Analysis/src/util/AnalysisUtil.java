package util;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



import md.base.fs.FileSystem;
import md.base.fs.FileSystemFactory;
import md.base.storage.WebPageStorage;
import javabean.db.KeywordGroup;
import javabean.db.Page;
import javabean.db.Template;

/**
 * 分析需要用到的一些工具方法
 * @author Bys
 *
 */
public class AnalysisUtil {

	static AnalysisProperties aprop = AnalysisProperties.getInstance(); 
//	private  String pageRootPath = null;
	public static final SimpleDateFormat downloadSdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	private static WebPageStorage storage = new WebPageStorage(true);
	private static WebPageStorage storage = new WebPageStorage();
	static{
		try {
			/**
			 * 本地读取器
			 */
//			FileSystem fs = FileSystemFactory.createLargeScaleLocalFileSystem(aprop.getDocUrl());
			/**
			 * 
			 */
			System.out.println("DocUrl:"+aprop.getDocUrl());
//			FileSystem fs = FileSystemFactory.createWebFileSystem(aprop.getDocUrl(), "key");
//			storage.setFileSystem(fs);
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
	
//    private static String fileSystem = aprop.getFileSystem();

	/**
	 * 获取page对应的content。除了微博以外的page的content存在文件系统中
	 * @param page 需要获取content的page
	 * @return 获取到的正文
	 */
	public String getCoreContent_new(Page page){
		String content = null;
		if(page.getType()==3||page.getType()==6){
			return page.getSummary();
		
		}
		else{
			try {
				System.out.println("pageId:"+page.getId());
				content = storage.get(page.getId(),true);
//				System.out.println(content);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return content;
		}
	}
	
	/**
	 * 将一个长整形表示的时间转化为年月日时分秒格式的字符串
	 * @param timeLong 长整形表示的时间
	 * @return yyyy-MM-dd HH:mm:ss 格式的字符串
	 */
	public static String getTimeString(Long timeLong){
		return downloadSdf.format(new Date(timeLong));
	}
	
	/**
	 * 将一个长整形表示的时间格式化到整秒
	 * @param timeLong 长整形表示的时间
	 * @return 格式化后的时间
	 */
	public static long formatTimelong(Long timeLong){
		try {
			return downloadSdf.parse(getTimeString(timeLong)).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * 判断模板是一般性的模板还是包括数据库字段的模板，type=0表示一般性的，type=1表示有概念是从数据库读取的
	 * @param tr
	 * @return 需要从数据库中读取的概念
	 */
	public KeywordGroup getTemplateType(Template tr){
		 ArrayList<KeywordGroup> groups = tr.getConcepts();
		 for(KeywordGroup group:groups){
			 
			 int type = group.getType();
			 System.out.println(group.getKeywordGroupName()+": "+type);
			 if(type == 1) return group;
		 }
		 
		 return null;	
	}
	
}


