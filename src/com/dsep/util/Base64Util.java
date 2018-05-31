package com.dsep.util;

import java.lang.reflect.Method;


public class Base64Util {
	
	/***
	 * encode by Base64
	 */
	public static String encodeBase64(byte[]input) throws Exception{
		Class<?> clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
		Method mainMethod= clazz.getMethod("encode", byte[].class);
		mainMethod.setAccessible(true);
		 Object retObj=mainMethod.invoke(null, new Object[]{input});
		 return (String)retObj;
	}
	/***
	 * decode by Base64
	 */
	public static byte[] decodeBase64(String input) throws Exception{
		Class<?> clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
		Method mainMethod= clazz.getMethod("decode", String.class);
		mainMethod.setAccessible(true);
		Object retObj=mainMethod.invoke(null, input);
		return (byte[])retObj;
	}

    //加密
	public static String encode(String origin){
		
		String object = null;
		try {
			object = encodeBase64(origin.getBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}
	//解密
	public static String decode(String object){
		String origin = null;
		try {
			byte[] origin_byte = decodeBase64(object);
			origin = new String(origin_byte ,"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return origin;
	}
    
    public static void main(String[] args){
    	String a = "用户及密码_49F5B80C5254474790027CABB5A75166.txt";
    	System.out.println("a字符串："+ a);
		System.out.println("a编码："+ encode(a));
		System.out.println("a解码："+ decode(encode(a)));
    
    }

}
