package com.dsep.util.publiccheck.normalization;
/**
 * 比对时期              tested
 * @author YuYaolin
 *
 */
public class PubComparePeriod {
	/**
	 * 
	 * @param date1  本地数据库起始日期
	 * @param date2  本地数据库结束日期
	 * @param period  公共库日期
	 * @return 比对通过返回true,否则返回false
	 */
/*	public static boolean compare(String date1,String date2, String  periodStr){
		String begin;
		String end;
		String [] period=new String [2];
		if(periodStr==null)
			return true;
		period=periodStr.split("&&");
		
		if(date1.length()==4){
			begin=period[0].substring(0,4);
			end=period[1].substring(0,4);
		}else if(date1.length()==6){
			begin=period[0].substring(0,4);
			end=period[1].substring(0,4);
		}else
			return false;
		
		if(Integer.parseInt(date1)==Integer.parseInt(begin)&&Integer.parseInt(date2)==Integer.parseInt(end))
			return true;
		else
			return false;
	}*/
	
	/**
	 * 比对起始时间或者结束时间
	 * @param localDate 本地时间，起始时间以*打头，结束时间以#打头
	 * @param periodStr 格式为YYYYMM&&YYYYMM，如果为年份，则MM位用0占位，及YYYY00&&YYYY00
	 * @return
	 */
	public static boolean compare(String localDate,String periodStr){
		localDate=localDate.replace(" ", "");
		String pubDate="";
		if(localDate.contains("*")&&localDate.indexOf('*')==0){                          //起始时间
			//处理起始时间
			localDate=localDate.replace("*", "");
			pubDate=periodStr.substring(0,periodStr.indexOf("&&"));
			
			
		}
		else if(localDate.contains("#")&&localDate.indexOf('#')==0){
			//处理结束时间
			localDate=localDate.replace("#", "");
			pubDate=periodStr.substring(periodStr.indexOf("&&")+2,periodStr.length());
		}
		
		if(localDate.length()==4){
			pubDate=pubDate.substring(0,4);
			if(Integer.parseInt(localDate)==Integer.parseInt(pubDate))
				return true;
			else
				return false;
		}else if(localDate.length()==6){
			if(pubDate.charAt(4)=='0'&&pubDate.charAt(5)=='0'){
				localDate=localDate.substring(0,4)+"00";
			}
			if(Integer.parseInt(localDate)==Integer.parseInt(pubDate))
				return true;
			else
				return false;
		}else if(localDate.length()==8){
			if(pubDate.charAt(4)=='0'&&pubDate.charAt(5)=='0'){
				localDate=localDate.substring(0,4)+"00";
			}else
				localDate=localDate.substring(0,6);
			if(Integer.parseInt(localDate)==Integer.parseInt(pubDate))
				return true;
			else
				return false;
		}else 
			return false;
	
	}
	
	public static void main(String[] args){
		String localValue1="*2008";
		String localValue2="#2010";
		String period="200800&&201000";
		boolean result1=compare(localValue1,period);
		System.out.println(result1);
		boolean result2=compare(localValue2,period);
		System.out.println(result2);
	}
}
