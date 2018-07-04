package com.dsep.util.publiccheck.normalization;

import org.apache.commons.lang.StringUtils;

import com.dsep.util.datacheck.CheckSimByCompositive;

public class PubCompareLongStr {
	/**
	 * 用拆词算法比对长字符串  tested
	 * @param localStr 本地字符串
	 * @param pubStr  公共库字符串
	 * @return 比对通过返回true,否则返回false
	 */
	public static boolean compare(String localStr,String pubStr){
		double sim;
		localStr=localStr.replace(" ", "");
		if(pubStr==null)
			return true;
		pubStr=pubStr.replace(" ", "");
		if(StringUtils.isNotBlank(pubStr)){
			sim=CheckSimByCompositive.checkSimOfTwoString(localStr, pubStr);
			if(sim>=0.9)
				return true;
			else
				return false;
		}else 
			return true;
	}
	/*public static void main(String args[]){
		String pubValue="利用面向对象数据库与关系数据库管理IFC数据的比较dfg";
		String localValue="利用面向对象数据库与关系数据库管理IFC数据的比较134";
		boolean result=compare(localValue,pubValue);
		System.out.println("pubValue: "+pubValue);
		System.out.println("localValue: "+localValue);
		System.out.println(result);
		
		
	}*/
}
