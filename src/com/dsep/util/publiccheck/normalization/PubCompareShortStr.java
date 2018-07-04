package com.dsep.util.publiccheck.normalization;

import org.apache.commons.lang.StringUtils;

public class PubCompareShortStr {
	/**
	 * 逐字符比对短字符串  tested
	 * @param localStr 本地字符串
	 * @param pubStr 公共库字符串
	 * @return 比对通过返回true,否则返回false
	 */
	public static boolean compare(String localStr,String pubStr){
		if(pubStr==null)
			return true;
		
		localStr=localStr.replace(" ", "");
		pubStr=pubStr.replace(" ", "");
		if(StringUtils.isNotBlank(pubStr)){
			if(localStr.equals(pubStr))
				return true;
			else 
				return false;
		}else
			return true;
	}
	
	/*public static void main(String args[]){
		String localValue="利用面向对象数据库与关系数据库管理IFC数据的比较dfg";
		String pubValue="利用面向对象数据库与关系数据库管理IFC数据的比较134";
		boolean result=compare(localValue,pubValue);
		System.out.println(result);
	}*/
}
