package com.dsep.util.publiccheck.normalization;

import java.util.LinkedList;
import java.util.List;


/**
 * 预处理日期  tested
 * YYYYMMDD格式存储时间，空位用？占用，--表示以前，++表示以后
 * @author YuYaolin
 *
 */
public class PubPretreatDate {
	/**
	 * 预处理日期，空位用？占用，--表示以前，++表示以后,返回“ERROR”表示这是一个错误格式
	 * @param strDate
	 * @return  返回空表示不用比对
	 */
	public static String pretreat(String strDate){
		
		strDate=strDate.replaceAll(" ","");
		String item = "";
		if(isBlank(strDate)){
			 return null;
		 }

		int type=identDateType(strDate);
		if(type ==1)
			item=convertYYYYagoOrafter(strDate);
		else if(type==2)
			item=convertYY_MM_DD(strDate);
		else if(type==3)
			item=convertYYYY(strDate);
		else if(type==4)
			item=convertYYYYMM(strDate);
		else if(type==5)
			item=convertYYYYMMDD(strDate);
		else if(type==6)
			item=convertyearmouthdate(strDate);
		else if(type==7)
			item=convertyearMonth(strDate);
		else 
			item="ERROR";
		return item;
	}
/*************************日期格式转换函数**************************/
	/**
	 * 类型2
	 * 转换格式为YY/MM/DD,YY-MM-DD,YY MM DD，YY：MM：DD,YY.MM.DD等日期格式;
	 * @param strDate
	 * @return  YYYYMMDD
	 */
	private static String convertYY_MM_DD(String strDate){
		String result="";
		strDate=strDate.replaceAll(" ", "");
		String year="10";
		String month="12";
		String day="12";
		
		if(strDate.length()==8){
			year = strDate.substring(0,2);
			month = strDate.substring(3,5);
			day = strDate.substring(6,8);
		}
		else if(strDate.length()==6){
			year = strDate.substring(0,2);
			month = strDate.substring(2,4);
			day = strDate.substring(4,6);
		}
		else throw new Error("格式有误");
		
		
		if(Integer.parseInt(year)>25)
			result=result+"19"+year;
		else
			result=result+"20"+year;
		
		result=result+month;
		
		result=result+day;
		
		return result;
	}
	/**类型3
	 * 转换YYYY的日期格式
	 * @param strDate
	 * @return   YYYY??????
	 */
	private static String convertYYYY(String strDate){
		String result="";
		strDate=strDate.replaceAll(" ", "");
		
		result=result+strDate+"????";
		
		return result;
	}
	/**类型4
	 * 转换YYYYMM，或者YYYY-MM或者YYYY/MM的格式
	 * @param strDate
	 * @return   YYYYMM??
	 */
	private static String convertYYYYMM(String strDate){
		String result="";
		strDate=strDate.replaceAll(" ", "");
		
		if(strDate.length()==7){
			result=result+strDate.substring(0,4)+strDate.substring(5,7)+"??";
		}
		
		if(strDate.length()==6){
			result=result+strDate+"??";
		}
		else throw new Error("格式有误");
		return result;
	}
	/**类型1
	 * 转换“YYYY年以前”，“YYYY年以后”,"YYYY年前"，“YYYY年后”的格式
	 * @param strDate
	 * @return   YYYY++??或者YYYY--??
	 */
	private static String convertYYYYagoOrafter(String strDate){
		String result="";
		strDate=strDate.replaceAll(" ", "");
		
		if(strDate.contains("前"))
			result=result+strDate.substring(0,4)+"--"+"??";
		else if(strDate.contains("后"))
			result=result+strDate.substring(0,4)+"++"+"??";
		
		return result;
	}
	/**
	 * 类型5
	 * 转换YYYYMMDD
	 * @param strDate
	 * @return
	 */
	private static String convertYYYYMMDD(String strDate){
		String result="";
		strDate=strDate.replaceAll(" ", "");
		
		result=strDate;
		return result;
	}
	/**
	 * 
	 * @param strDate
	 * @return
	 */
	private static String convertyearmouthdate(String strDate){
		String result="";
		List<Integer> specialIndex=new LinkedList<Integer>();
		strDate=strDate.replaceAll(" ", "");
		boolean flag=false;
		
		for(int i=0;i<strDate.toCharArray().length;i++){
			char cc=strDate.toCharArray()[i];
			if(!isNumber(cc)&&flag==false){
				specialIndex.add(i);
				flag=true;
			}else if(isNumber(cc)&&flag==true){
				flag=false;
			}
		}
		if(specialIndex.size()==1){          //有一个特殊字符
			int index = specialIndex.get(0);
			if(index==2){
				String year=strDate.substring(0,index);
				if(Integer.parseInt(year)>25)
					result=result+"19"+year;
				else
					result=result+"20"+year;
				
				if(strDate.length()-index==2){
					result=result+"0"+strDate.substring(index+1, strDate.length())+"??";
				}else{
					result=result+strDate.substring(index+1, strDate.length())+"??";
				}
			}
			else if(index==4){
				result=strDate.substring(0, index-1)+strDate.substring(index, strDate.length())+"??";
			}else throw new Error("未知的格式");
			
		}else if(specialIndex.size()==2){           //有两个特殊字符
			
			int index1=specialIndex.get(0);
			int index2=specialIndex.get(1);
			
			if(index1==2){
				String year=strDate.substring(0,index1);
				if(Integer.parseInt(year)>25)
					result=result+"19"+year;
				else
					result=result+"20"+year;
				
				if(index2-index1==2){
					result=result+"0"+strDate.substring(index1+1, index2);
				}else{
					result=result+strDate.substring(index1+1, index2);
				}
			}
			else if(index1==4){
				if(index2-index1==2){
					result=strDate.substring(0, index1)+"0"+strDate.substring(index1+1, index2);
				}else
					result=strDate.substring(0, index1)+strDate.substring(index1+1, index2);
			}else throw new Error("未知的格式");
			
			if(strDate.length()-index2==2){
				result=result+"0"+strDate.substring(index2+1, strDate.length());
			}else{
				result=result+strDate.substring(index2+1, strDate.length());
			}
		}else if(specialIndex.size()==3){              //有三个特殊字符
			int index1=specialIndex.get(0);
			int index2=specialIndex.get(1);
			int index3=specialIndex.get(2);
			
			if(index1==2){
				String year=strDate.substring(0,index1);
				if(Integer.parseInt(year)>25)
					result=result+"19"+year;
				else
					result=result+"20"+year;
				
				if(index2-index1==2){
					result=result+"0"+strDate.substring(index1+1, index2);
				}else{
					result=result+strDate.substring(index1+1, index2);
				}
			}
			else if(index1==4){
				if(index2-index1==2)
				result=strDate.substring(0, index1)+"0"+strDate.substring(index1+1, index2);
			}else throw new Error("未知的格式");
			
			if(index3-index2==2){
				result=result+"0"+strDate.substring(index2+1, index3);
			}else{
				result=result+strDate.substring(index2+1, index3);
			}
		}else throw new Error("未知的格式");
		
		return result;
	}
	private static String convertyearMonth(String strDate){
		int index_start=-1;
		int index_end=-1;
		int count=0;
		String year="";
		String month="";
		boolean flag=false;
		int length=strDate.length();
		for(int i=0;i<length;i++){
			char cc=strDate.charAt(i);
			if(isNumber(cc)==false){
				flag=true;
			}else if(i==length-1&&isNumber(cc)==true){
				flag=true;
			}
			
			if(flag==true){	
				index_start=index_end;
				index_end=i;
				if(index_end==length-1){
					index_end++;
				}
				count++;
				if(count==1){
					year=strDate.substring(index_start+1,index_end);
					
				}else if(count==2){
					month=strDate.substring(index_start+1,index_end);
					if(Integer.parseInt(month)<10)
						month="0"+month;
				}else
					return "ERROR";
				
				flag=false;
			}
		}
		String result=year+month+"??";
		return result;
		
	}
	/*********************识别日期格式******************************/
	private static int identDateType(String strDate){
		strDate=strDate.replaceAll(" ","");
		int type;
		
		if(strDate.contains("前")||strDate.contains("后"))
			type=1;            //YYYY以前，YYYY以后
		else if(strDate.length()==8&&(strDate.charAt(2)=='/'||strDate.charAt(2)=='-'||strDate.charAt(2)==':'))
			type=2;            //YY/MM/DD,YY-MM-DD,YY.MM.DD等
		else if(strDate.length()==8&&allNumber(strDate))
			type=5;
		else if(strDate.length()==4&&identYYYYorYYMM(strDate)==1)
			type=3;            //年份YYYY
		else if(strDate.length()==7)
			type=4;             //YYYY-MM,YYYY/MM
		else if(strDate.length()==6&&allNumber(strDate)&&identYYYYMMorYYMMDD(strDate)==1)
			type=4;             //YYYYMM
		else if(strDate.length()==6&&identYYYYMMorYYMMDD(strDate)==2)
			type=2;				//YYMMDD
		else if(YMD_or_YM(strDate)==3)
			type=6;               
		else if(YMD_or_YM(strDate)==2)
			type=7;
		else if(YMD_or_YM(strDate)==1)
			type=0;
		else 
			type=0;
		return type;
	}
	/**
	 * 返回1表示这是一个年份的格式YYYY
	 * @param strDate
	 * @return
	 */
	private static int identYYYYorYYMM(String strDate){
		String first2letter="";
		
		first2letter=strDate.substring(0, 2);
		if(first2letter.equals("20")||first2letter.equals("19"))
			return 1;
		else 
			return 2;
	}
	/**
	 * 返回1表示YYYYMM，返回2表示YYMMDD
	 * @param strDate
	 * @return
	 */
	private static int identYYYYMMorYYMMDD(String strDate){
		String year="";
		year=strDate.substring(0,4);
		
		if(identYYYYorYYMM(year)==1)
			return 1;                //YYYYMM格式
		else
			return 2;
	}
	/**
	 * 
	 * @param strDate
	 * @return
	 */
	
	private static boolean isNumber(char a){
		if(a=='0'||a=='1'||a=='2'||a=='3'||a=='4'||a=='5'||a=='6'||a=='7'||a=='8'||a=='9')
			return true;
		else
			return false;
	}
	private static boolean allNumber(String str){
		for(int i=0;i<str.length();i++){
			char ch=str.charAt(i);
			if(isNumber(ch)==false)
				return false;
		}
		return true;
	} 
	private static int YMD_or_YM(String str){
		int index_start=-1;
		int index_end=-1;
		int count=0;
		String year="";
		String month="";
		String day="";
		boolean flag=false;
		int length=str.length();
		for(int i=0;i<length;i++){
			char cc=str.charAt(i);
			if(isNumber(cc)==false){
				if((isNumber(str.charAt(i-1))==false||isNumber(str.charAt(i+1))==false)&&i!=length-1){	
					return 0;
				}
				flag=true;
			}else if(i==length-1&&isNumber(cc)==true){
				flag=true;
			}
			if(flag==true){
				index_start=index_end;
				index_end=i;
				if(index_end==length-1){
					index_end++;
				}
				count++;
				if(count==1){
					year=str.substring(index_start+1,index_end);
					if(year.length()!=4)
						return 0;
				}else if(count==2){
					month=str.substring(index_start+1,index_end);
					int number = Integer.parseInt(month);
					if(number<1||number>12)
						return 0;
				}else if(count==3){
					day=str.substring(index_start+1,index_end);
					int number = Integer.parseInt(day);
					if(number<1||number>31)
						return 0;
				}
				
				flag=false;
			}
			
		}
		
		return count;
		
	}
	private static boolean isBlank(String head){
		 if(head.equals(""))
			 return true;
		 else if(head.contains("看不清"))
			 return true;
		 else
			 return false;
	 }
	public static void main(String[] args){
		String date1="2009年以前";
		String date2="2010年以后";
		String date3="10-03-02";
		String date4="20100302";
		String date5="201009";
		String date6="2010";
		String date7="2007-3-9";
		String date8="2007年3月9";
		String date10="2009/8";
		System.out.println("test begin……");
		String date11=pretreat(date1);
		System.out.println(date11);
		String date21=pretreat(date2);
		System.out.println(date21);
		String date31=pretreat(date3);
		System.out.println(date31);
		String date41=pretreat(date4);
		System.out.println(date41);
		String date51=pretreat(date5);
		System.out.println(date51);
		String date71=pretreat(date7);
		System.out.println(date71);
		String date81=pretreat(date8);
		System.out.println(date81);
		String date101=pretreat(date10);
		System.out.println(date101);

	}
}
