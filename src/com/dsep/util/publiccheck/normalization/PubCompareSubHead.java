package com.dsep.util.publiccheck.normalization;

import com.dsep.util.datacheck.CheckSimByCompositive;

public class PubCompareSubHead {
	/**
	 * 比对带有副标题的题目名字                           tested
	 * @param localStr
	 * @param pubStr
	 * @return 比对通过返回true,否则返回false
	 */
	public static boolean compare(String localStr,String pubStr){
		double sim;
		if(pubStr==null)
			return true;
		
		String head=pubStr.substring(6, pubStr.indexOf("$"));
		sim=CheckSimByCompositive.checkSimOfTwoString(head,localStr);
		if(pubStr.contains("sub%%")){
			head=head+pubStr.substring(pubStr.indexOf("sub%%")+5);
			double temp=CheckSimByCompositive.checkSimOfTwoString(head,localStr);
			sim=temp>sim?temp:sim;
		}
		return sim>0.8?true:false;
	}
	
	/*public static void main(String []args){
		String localValue="利用面向对象数据库与关系数据库管理IFC数据的比较";
		String pubValue="head%%利用面向对象数据库与关系数据库管理IFC数据的比较$$$$$sub%%asd";
		boolean result=compare(localValue,pubValue);
		System.out.println(result);
	}*/
}
