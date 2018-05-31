package com.dsep.unitTest;

import org.junit.Test;

import com.dsep.util.StringDealUtil;

public class TestStringUtil {
	
	@Test
	public void testStringUtil(){
		String str="abs";
		System.out.println("去除字符串首空格 "+StringDealUtil.removeBeforeBlank(str));
		System.out.println("去除字符串尾空格 "+StringDealUtil.removeAfterBlank(str));
		System.out.println("去除字符串中所有字符 "+StringDealUtil.removeAllBlank(str));
		System.out.println("去除首尾字符 "+StringDealUtil.removeBeforeAndAfterBlank(str));
	}
	@Test
	public void testStringBrackets(){
		String big = "{DIC:[LM]}";
		String mid = "[abc]";
		String little = "(abc)";
		System.out.println(StringDealUtil.getSubStrFromBigBrackets("大括号： "+big));

		System.out.println(StringDealUtil.getSubStrFromMidBrackets("中括号： "+mid));

		System.out.println(StringDealUtil.getSubStrFromLittleBrackets("小括号： "+little));
	}
	
	public void setStringValue(String theString){
		theString = "dsafdfasdfxzvcsdfas";
	}
	
	@Test
	public void mainFunction(){
		String str = "sad";
		setStringValue(str);
		System.out.println(str);
	}

}
