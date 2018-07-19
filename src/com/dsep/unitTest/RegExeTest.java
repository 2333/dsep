package com.dsep.unitTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;



public class RegExeTest {
	
	public void jout(Object result){
		System.out.println(result+"");
	}
	
	@Test
	public void test(){
		/**
		 * 邮箱的正则表达式
		 */
		Pattern regPattern = Pattern.compile("\\d{9}@[a-zA-Z0-9]+.com");
		/**
		 * 查找冠词the的正则表达式
		 */
		Pattern thePattern = Pattern.compile("[tT]he");
		/**
		 * 查找单词color或colour的正则表达式
		 */
		Pattern colorPattern = Pattern.compile("colou?r");
		jout("colorPattern result:"+colorPattern.matcher("colour").matches()+"");
		
		jout("thePattern result:"+thePattern.matcher("The one is best").find()+"");
		
		jout("邮箱的正则表达式:"+regPattern.matcher("408488316@qq.com").find());
	}
	
	public void outPutMatchResult(String regString,Object matchString){
		/**
		 * 匹配500MHz,32Gb,Company,Max,$999.99这样的数字
		 */
		Pattern computerPattern = Pattern.compile(regString);
		
		Matcher matcher = computerPattern.matcher(matchString.toString());
		
		jout("正则表达式"+regString+"匹配"+matchString+":"+
			matcher.find());
		jout(" start:"+matcher.start()+",end:"+matcher.end());
		jout("group:"+matcher.group());
	}
	
	/**
	 * 用matchString数组中对应的字符串替换regString数组中相同位置的正则表达式
	 * processString为被处理的字符串
	 * @param regString
	 * @param matchString
	 * @param processString
	 */
	public void replaceRegString(String[] regString,String[] matchString,Object processString){
		if( regString.length != matchString.length){
			jout("匹配字符串和正则表达式字符串的长度应该相等");
			return;
		}
		
		Pattern[] pattern = new Pattern[regString.length];
		Matcher[] matcher = new Matcher[regString.length];
		
		for(int i=0;i < regString.length;i++){
			pattern[i] = Pattern.compile(regString[i]);
			matcher[i] = pattern[i].matcher(processString.toString());
			processString = new StringBuffer(matcher[i].replaceAll(matchString[i]));
			jout(processString);
		}
	}
	
	@Test
	public void computer_test(){
		
		/*String regString = "\\d+(.\\d{1,2})?";*/
		
		String regString = "\\[";
		
		String matchString = "[][][]";
		
		outPutMatchResult(regString, matchString);
	}
	
	@Test
	public void regExpressReplace_test() throws IOException{
		
		InputStream inputStream = new FileInputStream("E:\\日语学习\\Readme.txt");
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"gbk");             
		@SuppressWarnings("resource")
		BufferedReader bufferReader = new BufferedReader(inputStreamReader); 
		
		StringBuffer processString = new StringBuffer();
		String line = null;
		while ((line = bufferReader.readLine()) != null)            
		{                 
			processString.append(line+"\n");             
		}  
		
		jout(processString);
		
		String[] regString =new String[]{"\\[","\\]"};
		
		String[] matchString = new String[]{"<",">"};
		
		replaceRegString(regString, matchString, processString);
	}
	
}
