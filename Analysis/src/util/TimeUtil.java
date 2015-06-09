package util;

import java.util.*;
import java.text.*;

/**
 * 一些时间转换工具
 *
 */
public class TimeUtil {
	public static DecimalFormat decimalFormat = new DecimalFormat("#0.000");
	public static String statTimeBySecond(Date start,Date end){
		DecimalFormat df = new DecimalFormat("#0.000");
		return df.format((double)(end.getTime()-start.getTime())/1000);
	}
	
	/** 
	* 字符串转换成日期 
	* @param str 
	* @return date 
	*/  
	public static Date StrToDate(String str) {  
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
	   Date date = null;  
	    try {
			date = format.parse(str);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	   return date;  
	}  
	
	/** 
	* 日期转换成字符串
	* @param date 
	* @return str 
	*/  
	public static String DateToStr(Date date) {  
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
	   String str = null;  
	   str = format.format(date);  
	   return str;  
	}  
	
	public static String getDate() {
		java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
		return date.toString();
	}

	public static void getPreciseCurrentTime() {
		long startTime =System.currentTimeMillis();  // 获取开始时间
		long endTime = System.currentTimeMillis(); // 获取结束时间
		System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
		/*
		 * long startTime=System.currentTimeMillis(); //获取开始时间 　　doSomeThing();
		 * //测试的代码段 　　long endTime=System.currentTimeMillis(); //获取结束时间
		 */
	}
	
	/*public static long getConsumeTime(Object oj){
		long startTime =System.currentTimeMillis(); 
		Object o=oj;
		long endTime = System.currentTimeMillis(); // 获取结束时间
		long between =endTime-startTime;
		return between;
	}*/

	@SuppressWarnings("unused")
	public static String getTime() {
		Date now = new Date();

		Calendar cal = Calendar.getInstance();

		DateFormat d1 = DateFormat.getDateInstance(); // 默认语言（汉语）下的默认风格（MEDIUM风格，比如：2008-6-16
														// 20:54:53）
		String str1 = d1.format(now);
		DateFormat d2 = DateFormat.getDateTimeInstance();
		String str2 = d2.format(now);
		DateFormat d3 = DateFormat.getTimeInstance();
		String str3 = d3.format(now);
		DateFormat d4 = DateFormat.getInstance(); // 使用SHORT风格显示日期和时间
		String str4 = d4.format(now);
		DateFormat d5 = DateFormat.getDateTimeInstance(DateFormat.FULL,
				DateFormat.FULL); // 显示日期，周，时间（精确到秒）
		String str5 = d5.format(now);
		DateFormat d6 = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG); // 显示日期。时间（精确到秒）
		String str6 = d6.format(now);
		DateFormat d7 = DateFormat.getDateTimeInstance(DateFormat.SHORT,
				DateFormat.SHORT); // 显示日期，时间（精确到分）
		String str7 = d7.format(now);
		DateFormat d8 = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
				DateFormat.MEDIUM); // 显示日期，时间（精确到分）
		String str8 = d8.format(now);// 与SHORT风格相比，这种方式最好用
		/*
		 * System.out.println("用Date方式显示时间: " +
		 * now);//此方法显示的结果和Calendar.getInstance().getTime()一样
		 * 
		 * 
		 * System.out.println("用DateFormat.getDateInstance()格式化时间后为：" + str1);
		 * System.out.println("用DateFormat.getDateTimeInstance()格式化时间后为：" +
		 * str2); System.out.println("用DateFormat.getTimeInstance()格式化时间后为：" +
		 * str3); System.out.println("用DateFormat.getInstance()格式化时间后为：" +
		 * str4);
		 * 
		 * System.out.println(
		 * "用DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.FULL)格式化时间后为："
		 * + str5);System.out.println(
		 * "用DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG)格式化时间后为："
		 * + str6);System.out.println(
		 * "用DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT)格式化时间后为："
		 * + str7);System.out.println(
		 * "用DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM)格式化时间后为："
		 * + str8);
		 */
		return str2;
	}

	/**
	 * 接受YYYY-MM-DD的日期字符串参数,返回两个日期相差的天数
	 * @param start
	 * @param end
	 * @return
	 * @throws ParseException
	 */
	public long getDistDates(String start,String end) throws ParseException  
	{
	      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
	      Date startDate = sdf.parse(start);   
	      Date endDate = sdf.parse(end);
	      return getDistDates(startDate,endDate);
	}  
	
	/**
	 * 返回两个日期相差的天数
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	public long getDistDates(Date startDate,Date endDate)  
	{
		long totalDate = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		long timestart = calendar.getTimeInMillis();
		calendar.setTime(endDate);
		long timeend = calendar.getTimeInMillis();
		totalDate = Math.abs((timeend - timestart))/(1000*60*60*24);
		return totalDate;
	} 
	
	/**
	 * 获取与_fromdate相差_monthCount个月的日期
	 * @param _fromdate YYYY-MM-DD
	 * @param _monthCount 
	 * @return
	 * @throws ParseException
	 */
	public String getDistMonths(String _fromdate,int _monthCount) throws ParseException
	{  
		String resultDate = "";
		int year,month,date;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(_fromdate));
		year = calendar.get(Calendar.YEAR);
		date = calendar.get(Calendar.DAY_OF_MONTH);
		month = calendar.get(Calendar.MONTH) + 1 + _monthCount;
		int c = new Integer((month-1)/12);
		month = month%12;
		if(month == 0)
		month = 12;
		year += c;
		resultDate = year + "-" + month + "-" + date;
		return resultDate;
	}
	
	/**
	 * 计算每个月的天数，以数组返回
	 * @param months
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public String[] countMonthDates(int months,Date date) throws ParseException  
	{  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String _date = date == null ? sdf.format(new Date()) : sdf.format(date);//如果不给定起算时间则从今天算起  

	    return countMonthDates(months,_date);  
	}	

	/**
	 * 计算每个月的天数，以数组返回
	 * @param months
	 * @param date
	 * @return
	 * @throws ParseException
	 */ 
	public String[] countMonthDates(int months,String date) throws ParseException  
	{  
	    String[] results = null;//结果  
	    String _today = date == null ? new SimpleDateFormat("yyyy-MM-dd").format(new Date()) : date;//如果不给定起算时间则从今天算起  
	    int _months = months > 0 ? months : 24;//如果不给定计算的月数则计算未来两年里面的24月  
	    String startDate = getDistMonths(_today,0);//获得起算日期的YYYY-MM-DD格式的字符串日期  
	    results = new String[_months];
	    for(int i = 1; i <= _months; i++)  
	    {  
	        String nextMonthDate = getDistMonths(_today,i);//每个月的截至日期  
	        String desc = startDate + " 至 " + nextMonthDate;  
	        long dates = getDistDates(startDate,nextMonthDate);//返回天数
	        results[i-1] = desc + " ：共 " + dates + " 天！";
	        startDate = nextMonthDate;//当前月的结束日期作为下一个月的起始日期  
	    }  
	    return results;  
	}

	public static void main(String args[]) {
//		getPreciseCurrentTime();
//		Date date = new Date();
//		String str = DateToStr(date);
//		Date newDate = StrToDate(str);
//		System.out.println(newDate);
		System.out.println(statTimeBySecond(new Date(), new Date(new Date().getTime()+12345L)));
	}
}
