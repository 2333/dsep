package com.dsep.util.publiccheck.normalization;

/**
 * 预处理书名或者案例名称
 * @author YuYaolin
 *
 */
public class PubPretreatBookName {
	
	/**
	 * 去除书名号  tested
	 * @param bookName
	 * @return
	 */
	public static String pretreat(String bookName){
		String result="";
		
		bookName=bookName.replaceAll(" ", "");
		if(isBlank(bookName)){
			 return null;
		 }
		
		bookName=bookName.replace("《", "");
		result=bookName.replace("》", "");
		
		return result;
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
		String localValue="看不清";
		System.out.println(pretreat(localValue));
	}*/
	
}
