package com.dsep.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class MD5 
{
	/**
	 * @param 原始字符串
	 * @return 加密后的字符串
	 */
	public  static String getMd5Str(String orgStr)  
	{
		/*MessageDigest md = null;
		try 
		{
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		md.update(orgStr.getBytes());
		byte[] bytes=md.digest();
		StringBuffer strBuilder=new StringBuffer();
		for(int i=0;i<bytes.length;i++)
		{    
            int v=bytes[i]&0xff; 
            if(v<16)
            	strBuilder.append(0); 
            strBuilder.append(Integer.toHexString(v));
		}
		return strBuilder.toString();*/
		return orgStr;
	}
	
}
