package com.dsep.util.publiccheck.normalization;

import java.util.LinkedList;
import java.util.List;

/**
 * 预处理学校代码或者学校名称,将用#分隔的代码拆分为List链表  tested
 * @author YuYaolin
 *
 */
public class PubPretreatSchCode {
	public static String pretreat(String schCodeStr){
		
		//在这里要进行字符串的去空格处理
		schCodeStr=schCodeStr.replaceAll(" ", "");
		if(isBlank(schCodeStr)){
			 return null;
		 }
		
		return schCodeStr;
	}
	private static boolean isBlank(String head){
		 if(head.equals(""))
			 return true;
		 else if(head.contains("看不清"))
			 return true;
		 else
			 return false;
	 }
	/***********以下为测试代码******************************/
	/*public static void main(String [] args){
		String codes= "10487#80020#999 99# ";
		List<String> splitresult=pretreat(codes);
		System.out.println(codes);
		for(int i=0;i<splitresult.size();i++){
			System.out.println(i+" : "+splitresult.get(i));
		}
	}*/
	/***********以上为测试代码******************************/
}
