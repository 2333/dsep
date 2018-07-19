package com.dsep.util.publiccheck.normalization;
/**
 * 比对日期
 * @author YuYaolin
 *
 */
public class PubCompareDate {
	/**
	 * 日期正确返回TRUE，日期错误返回FALSE tested 
	 * @param localDate  本地日期
	 * @param pubDate   公共库日期
	 * @return 比对通过返回true,否则返回false
	 */
	public static boolean compare(String localDate,String pubDate){
		int pubDateType;
		int localDateType;
		
		pubDateType = identPubDate(pubDate);
		localDateType = identLocalDate(localDate);
		 
		if(pubDate==null)
			return true;
		
		if(localDateType==1){                             //本地日期为YYYY
			if(!pubDate.contains("+")&&!pubDate.contains("-")){
				int pubyear=Integer.parseInt(pubDate.substring(0, 4));
				int localyear=Integer.parseInt(localDate.substring(0, 4));
				if(pubyear==localyear)
					return true;
				else 
					return false;
			}else if(pubDate.indexOf("+")==4){
				int pubyear=Integer.parseInt(pubDate.substring(0, 4));
				int localyear=Integer.parseInt(localDate.substring(0, 4));
				if(pubyear<localyear)
					return true;
				else 
					return false;
			}else if(pubDate.indexOf("+")==6){
				int pubyear=Integer.parseInt(pubDate.substring(0, 4));
				int localyear=Integer.parseInt(localDate.substring(0, 4));
				if(pubyear<=localyear)
					return true;
				else 
					return false;
			}else if(pubDate.indexOf("-")==4){
				int pubyear=Integer.parseInt(pubDate.substring(0, 4));
				int localyear=Integer.parseInt(localDate.substring(0, 4));
				if(pubyear>localyear)
					return true;
				else 
					return false;
			}else if(pubDate.indexOf("-")==6){
				int pubyear=Integer.parseInt(pubDate.substring(0, 4));
				int localyear=Integer.parseInt(localDate.substring(0, 4));
				if(pubyear>=localyear)
					return true;
				else 
					return false;
			}
		}else if(localDateType==2){                              //本地日期为YYYYMM
			if(pubDateType ==1){
				int localyear=Integer.parseInt(localDate.substring(0, 4));
				int pubyear=Integer.parseInt(pubDate.substring(0,4));
				if(pubyear==localyear)
					return true;
				else 
					return false;
			}else if(pubDateType ==2){
				int localyear=Integer.parseInt(localDate.substring(0, 4));
				int pubyear=Integer.parseInt(pubDate.substring(0,4));
				if(pubyear<localyear)
					return true;
				else 
					return false;
			}else if(pubDateType ==3){
				int localyear=Integer.parseInt(localDate.substring(0, 4));
				int pubyear=Integer.parseInt(pubDate.substring(0,4));
				if(pubyear>localyear)
					return true;
				else 
					return false;
			}else if(pubDateType ==4){
				int local=Integer.parseInt(localDate.substring(0, 6));
				int pub=Integer.parseInt(pubDate.substring(0,6));
				if(pub==local)
					return true;
				else 
					return false;
			}else if(pubDateType ==5){
				int local=Integer.parseInt(localDate.substring(0, 6));
				int pub=Integer.parseInt(pubDate.substring(0,6));
				if(pub<local)
					return true;
				else 
					return false;
			}else if(pubDateType ==6){
				int local=Integer.parseInt(localDate.substring(0, 6));
				int pub=Integer.parseInt(pubDate.substring(0,6));
				if(pub>local)
					return true;
				else 
					return false;
			}else if(pubDateType ==7){
				int local=Integer.parseInt(localDate.substring(0, 6));
				int pub=Integer.parseInt(pubDate.substring(0,6));
				if(pub==local)
					return true;
				else 
					return false;
			}
		}else if(localDateType==3){
			if(pubDateType==1||pubDateType==2||pubDateType==3){
				int local=Integer.parseInt(localDate.substring(0, 4));
				int pub=Integer.parseInt(pubDate.substring(0,4));
				if(pubDateType==1)
					return local==pub?true:false;
				if(pubDateType==2)
					return local>pub?true:false;
				if(pubDateType==3)
					return local<pub?true:false;
			}else if(pubDateType==4||pubDateType==5||pubDateType==6){
				int local=Integer.parseInt(localDate.substring(0, 6));
				int pub=Integer.parseInt(pubDate.substring(0,6));
				if(pubDateType==4)
					return local==pub?true:false;
				if(pubDateType==5)
					return local>pub?true:false;
				if(pubDateType==6)
					return local<pub?true:false;
			}else if(pubDateType==7){
				if(localDate.equals(pubDate))
					return true;
				else 
					return false;
			}
		}
		return true;
	}
	/**
	 * 辨别公共库日期数据的类型
	 * 1为YYYY？？？？
	 * 2为YYYY++??
	 * 3为YYYY--？？
	 * 4为YYYYMM？？
	 * 5为YYYYMM++
	 * 6为YYYYMM--
	 * 7为YYYYMMDD
	 * 0表示日期格式错误
	 * @param localDate
	 * @return
	 */
	private static int identPubDate(String pubDate){
		int indexWildcard = pubDate.indexOf("?");
		if(indexWildcard==4){
			return 1;                //YYYY某某年
		}else if(indexWildcard==6&&pubDate.charAt(4)=='+'){
			return 2;                //YYYY++某某年以后
		}else if(indexWildcard==6&&pubDate.charAt(4)=='-'){
			return 3;                  //YYYY--某某年以前
		}else if(indexWildcard==6&&pubDate.charAt(4)!='+'&&pubDate.charAt(4)!='-'){
			return 4;                  //YYYYMM某年某月
		}else if(indexWildcard==-1&&pubDate.charAt(6)=='+'){
			return 5;                 //YYYYMM++某年某月以后
		}else if(indexWildcard==-1&&pubDate.charAt(6)=='-'){
			return 6;                   //YYYYMM--某年某月以前
		}else if(indexWildcard==-1&&pubDate.charAt(6)!='+'&&pubDate.charAt(6)!='-'){
			return 7;                      //YYYYMMDD某年某月某日
		}else
			return 0;
	}
	/**
	 * 辨别本地数据库日期的类型
	 * 1表示YYYY
	 * 2表示YYYYMM
	 * 3表示YYYYMMDD
	 * 0表示日期错误
	 * @param localDate
	 * @return
	 */
	private static int identLocalDate(String localDate){
		if(localDate.length()==4)
			return 1;
		else if(localDate.length()==6)
			return 2;
		else if(localDate.length()==8)
			return 3;
		else 
			return 0;
	}
	
	public static void main(String args[]){
		String localValue1="201211";
		String pubValue1="201211??";
		boolean result1=compare(localValue1,pubValue1);
		System.out.println(localValue1+" : "+pubValue1);
		System.out.println(result1);
		/****************************************/
		String localValue2="20091120";
		String pubValue2="20091120";
		boolean result2=compare(localValue2,pubValue2);
		System.out.println(localValue2+" : "+pubValue2);
		System.out.println(result2);
		
	}
}
