package com.dsep.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateProcess {
	
	private static String getDateString(SimpleDateFormat format,Date theDate){
		String showingDate = "";
		if( theDate != null )
			showingDate = format.format(theDate);
		else {
			showingDate = "暂无";
		}
		return showingDate;
	}
	
	private static String getFormatDateString(SimpleDateFormat format,Date theDate){
		String showingDate = "";
		if( theDate != null )
			showingDate = format.format(theDate);
		return showingDate;
	}
	
	public static Date getDatebaseDateFromInput(String inputTime)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = sdf.parse(inputTime);
		return endDate;
	}
	
	/**
	 * 转变日期型，使其能被插入到数据库中
	 * @param theDate
	 * @return
	 */
	public static String getDataBaseDate(Date theDate){
		String dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(theDate);
		return "to_date('" + dateString + "','yyyy-MM-dd HH24:mi:ss')"; 
	}
	
	/**
	 * 转变日期型，使其能被插入到数据库中
	 * @param theDate
	 * @return
	 */
	public static String getDataBaseDate(Object theDate){
		String dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(theDate);
		return "to_date('" + dateString + "','yyyy-MM-dd HH24:mi:ss')"; 
	}
	
	/**
	 * 返回格式为'yyyy-MM-dd'
	 * @param theDate
	 * @return
	 */
	public static String getShowingDate(Date theDate){
		SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
		String showingDate = getDateString(format, theDate);
		return showingDate;
	}
	
	/**
	 * 返回格式为'yyyy-MM-dd'
	 * @param theDate
	 * @return
	 */
	public static String getShowingFormatDate(Date theDate){
		SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
		String showingDate = getFormatDateString(format, theDate);
		return showingDate;
	}
	
	/**
	 *返回格式'yyyy' 
	 * @param theDate
	 * @return
	 */
	public static String getShowYear(Date theDate){
		SimpleDateFormat format =  new SimpleDateFormat("yyyy");
		return getDateString(format,theDate);
	}
	/**
	 *返回格式'yyyy-mm' 
	 * @param theDate
	 * @return
	 */
	public static String getShowYearMonth(Date theDate){
		SimpleDateFormat format =  new SimpleDateFormat("yyyyMM");
		return getDateString(format,theDate);
	}
	/**
	 * 如果字符串为空则返回暂无
	 * @param str
	 * @return
	 */
	public static String getShowingString(String str){
		if(str == null || str == "")
			return "暂无";
		else
			return str;
	}
	
	/**
	 * 返回格式为'yyyy-MM-dd HH:mm:ss'
	 * @param theDate
	 * @return
	 */
	public static String getShowingTime(Date theDate){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return getDateString(format,theDate);
	}
	
	/**
	 * 比较两个日期型的大小，只比较年月日，不比较时分秒
	 * @param firstDate
	 * @param secondDate
	 * @return 返回字符串">"、"<"或"="
	 */
	public static String CompareDateTimeByDate(Date firstDate,Date secondDate){
		if( firstDate.getYear() > secondDate.getYear()){
			return ">";
		}
		else if( firstDate.getYear() < secondDate.getYear()){
			return "<";
		}
		else if( firstDate.getMonth() > secondDate.getMonth()){
			return ">";
		}
		else if( firstDate.getMonth() < secondDate.getMonth()){
			return "<";
		}
		else if( firstDate.getDate() > secondDate.getDate()){
			return ">";
		}
		else if( firstDate.getDate() < secondDate.getDate()){
			return "<";
		}
		else{
			return "=";
		}
	}
	
}
