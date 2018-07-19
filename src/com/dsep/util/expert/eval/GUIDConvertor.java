package com.dsep.util.expert.eval;

import java.math.BigInteger;


// 一个常见的GUID：U83T0...9tI3
// 在GET方法传递的时候字母会被浏览器转码变成%形式，导致路由无效
// 这个类将会把GUID中的字母全部转为数字，防止浏览器转码
public class GUIDConvertor {
	//只有字母和数字[0-9a-zA-Z]、一些特殊符号"$-_.+!*'(),"[不包括双引号]、以及某些保留字，才可以不经过编码直接用于URL
	
	
	public static String expertEvalGUIDEncode(String guid) {
		BigInteger bigInt = new BigInteger(guid.getBytes());
		return bigInt.toString();
	}
	public static String expertEvalGUIDDecode(String convertedGuid) {
		System.out.println(convertedGuid.length());
		// convert back
		BigInteger bigInt  = new BigInteger(convertedGuid.replaceAll("PSFT_",""));
		
		String textBack = new String(bigInt.toByteArray());
		System.out.println(textBack.length());
		return textBack;
	}
	
	public static void main(String[] args) {
		String text = "AFJIiE4A39D6414A3C7C01E51i977FD3";
		System.out.println("Test string = " + expertEvalGUIDEncode(text));

		System.out.println(expertEvalGUIDDecode(expertEvalGUIDEncode(text)));

		
	}
}
