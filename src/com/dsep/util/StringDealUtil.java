package com.dsep.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class StringDealUtil {
	
	public static String removeBeforeBlank(String str){
		String newStr = str+"a";
	    return newStr.trim().substring(0, newStr.trim().length()-1);
	}
	public static String removeAfterBlank(String str){
		String newStr = "a"+str;
		return newStr.trim().substring(1);
				
	}
	public static String removeAllBlank(String str){
		return str.replaceAll("\\s*", "");
	}
	public static String removeBeforeAndAfterBlank(String str){
		return str.trim();
	}
	
	public static String getSubStrFromLittleBrackets(String str){
		Pattern pattern = Pattern.compile(".*?\\((.*?)\\).*?");
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			return matcher.group(1);
		}else{
			return null;
		}
	}
	public static String getSubStrFromMidBrackets(String str){
		Pattern pattern = Pattern.compile(".*?\\[(.*?)\\].*?");
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			return matcher.group(1);
		}else{
			return null;
		}
	}
	public static String getSubStrFromBigBrackets(String str){
		Pattern pattern = Pattern.compile(".*?\\{(.*?)\\}.*?");
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			return matcher.group(1);
		}else{
			return null;
		}
	}
	
	public static String operWithInteger(String number,int count,String oper){
		if("+".equals(oper)){
			return String.valueOf(((Integer.valueOf(number)+Integer.valueOf(count))));
		}else{
			return String.valueOf(((Integer.valueOf(number)-Integer.valueOf(count))));
		}
	}
}
