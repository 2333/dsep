package com.dsep.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtil {
	
	/**
	 * 如果是整数（包括+，-）
	 * 如果是小数（包括+，-）
	 * 返回true
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str){
		Pattern pattern = Pattern.compile("^\\d+$|-\\d+$|\\d+\\.\\d+$|-\\d+\\.\\d+$");
		Matcher isNum = pattern.matcher(str);
		if(isNum.matches()){
			return true;
		}else{
			return false;
		}
	}

}
