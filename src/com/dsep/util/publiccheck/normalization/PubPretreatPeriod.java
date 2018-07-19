package com.dsep.util.publiccheck.normalization;

import java.util.LinkedList;
import java.util.List;

/**
 * 预处理期间           tested
 * @author YuYaolin
 *
 */
public class PubPretreatPeriod {

	public static String pretreat(String periodStr){

		String result="";
		int index=0;
		
		periodStr=periodStr.replace(" ", "");
		if(isBlank(periodStr)){
			 return null;
		 }
		
		for(int i=0;i<periodStr.toCharArray().length;i++){
			if(isSplitcode(periodStr.toCharArray()[i])){
				index=i;
				break;
			}
		}
		String beginYear="";
		String beginMonth="";
		int temp1=0;
		int temp2=0;
		
		for(int i=0;i<index;i++){
			char cc=periodStr.toCharArray()[i];
			if(isNumber(cc)){
				
				if(beginYear.length()<4){
					beginYear=beginYear+cc;
					if(beginYear.length()==2&&(beginYear.equals("19")||beginYear.equals("20"))){

					}else if(beginYear.length()==2){
						if(Integer.parseInt(beginYear)>50){
							beginYear="19"+beginYear;
						}else{
							beginYear="20"+beginYear;
						}
					}else;
				}else if(beginMonth.length()<2){
					if(temp1==0){
						temp1=i;
						beginMonth=beginMonth+cc;
					}  
					else{
						temp2=i;
						if(temp2-temp1==1)
							beginMonth=beginMonth+cc;
						else
							break;
					}
				}
			}
		}
		
		if(beginMonth.length()==0){
			beginMonth="00";
		}
		else if(beginMonth.length()==1){
			beginMonth="0"+beginMonth;
		}
		String beginDate=beginYear+beginMonth;
		
		
		String endYear="";
		String endMonth="";
		int temp3=0;
		int temp4=0;
		
		for(int i=index;i<periodStr.toCharArray().length;i++){
			char cc=periodStr.toCharArray()[i];
			if(isNumber(cc)){
				
				if(endYear.length()<4){
					endYear=endYear+cc;
					if(endYear.length()==2&&(endYear.equals("19")||endYear.equals("20"))){

					}else if(endYear.length()==2){
						if(Integer.parseInt(endYear)>50){
							endYear="19"+endYear;
						}else{
							endYear="20"+endYear;
						}
					}else;
				}else if(endMonth.length()<2){
					if(temp3==0){
						temp3=i;
						endMonth=endMonth+cc;
					}  
					else{
						temp4=i;
						if(temp4-temp3==1)
							endMonth=endMonth+cc;
						else
							break;
					}
				}
			}
		}
		
		if(endMonth.length()==0){
			endMonth="00";
		}else if(endMonth.length()==1){
			endMonth="0"+endMonth;
		}
		String endDate=endYear+endMonth;
		
		result=beginDate+"&&"+endDate;
		
		return result;
	}
	
	public static boolean isSplitcode(char cc){
		List<Character> split = new LinkedList<Character>();
		split.add('至');
		split.add('到');
		split.add('-');
		split.add('—');
		if(split.contains(cc))
			return true;
		else
			return false;
	}
	private static boolean isNumber(char a){
		if(a=='0'||a=='1'||a=='2'||a=='3'||a=='4'||a=='5'||a=='6'||a=='7'||a=='8'||a=='9')
			return true;
		else
			return false;
	}
	private static boolean isBlank(String head){
		 if(head.equals(""))
			 return true;
		 else if(head.contains("看不清"))
			 return true;
		 else
			 return false;
	 }
	/*public static void main(String[]args){
		String period="2008-2009年";
		String period1="2008至2009/12";
		String result=pretreat(period);
		String result1=pretreat(period1);
		System.out.println(period);
		System.out.println(result);
		System.out.println(period1);
		System.out.println(result1);
	}*/
}
