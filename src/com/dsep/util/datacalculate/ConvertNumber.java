package com.dsep.util.datacalculate;

public class ConvertNumber {

	/**
	 * 保留小数的有效数字
	 * @param d1 小数
	 * @param n 需要保留的有效位数
	 * @return n位有效数字的小数
	 */
	public static double convertDouble(double d1,int n){
		double result=0.0;
		int pow=(int)Math.pow(10, n);
		int d2=(int)(d1*pow);
		result=(double)d2/pow;
		
		return result;
	}
	
	/**
	 * 判断是否是数字
	 * @param str
	 * @return
	 */
	public static boolean isNum(String str){
		for(int i=0;i<str.length();i++){
			char cc=str.charAt(i);
			if(!(cc>=48&&cc<=57||cc==46))
				return false;
		}
		return true;
	}
}
