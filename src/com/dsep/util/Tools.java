package com.dsep.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.meta.domain.search.SearchRule;
import com.meta.domain.search.SearchType;

public class Tools {
	
	public static Map<String,String> requestData2Map(HttpServletRequest requestData,List<String> keys){
		Map<String,String> map=new HashMap<String,String>();
		for(String key:keys)
		{	
			if(key.equals("ATTACH_ID")){//对附件进行特殊处理
				if(requestData.getParameter(key)!=null&&
						!requestData.getParameter(key).equals("请上传附件")){
					map.put(key, requestData.getParameter(key));
				}else{
					map.put(key, "");
				}
			}else{
				map.put(key, requestData.getParameter(key));
			}
			
		}
		return map;
	}
	public static Map<String,String> requestData2Map(HttpServletRequest requestData,
													 List<String> keys,
													 List<String> fileKeys){
		Map<String,String> map=new HashMap<String,String>();
		for(String key:keys)
		{	
			if(fileKeys.contains(key)){
				if(!requestData.getParameter(key).equals("请上传附件")){
					map.put(key, requestData.getParameter(key));
				}else{
					map.put(key, "");
				}
			}else{
				map.put(key, requestData.getParameter(key));
			}
		}
		return map;
	}
	/**
	 * 是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){ 
		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
	}
	/**
	 * URL解码
	 * @param param 参数
	 * @param type 编码类型
	 * @return 解码后的值
	 */
	public static String urlDecoder(String param,String type){
		try {
			return URLDecoder.decode(param, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
}
