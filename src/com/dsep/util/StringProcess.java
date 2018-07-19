package com.dsep.util;

public class StringProcess {
	public static boolean isNull(String theString){
		if(theString == null || theString.equals("null") || theString.equals("") || theString.equals("undefined")){
			return true;
		}
		else{
			return false;
		}
	}
	
	public static void testOutput(){
		System.out.println("************************************************");
		System.out.println("************************************************");
		System.out.println("************************************************");
		System.out.println("************************************************");
		System.out.println("************************************************");
		System.out.println("************************************************");
	}
	
	public static void testOutput(String result){
		System.out.println("************************************************");
		System.out.println("************************************************");
		System.out.println("************************************************");
		System.out.println(result);
		System.out.println("************************************************");
		System.out.println("************************************************");
		System.out.println("************************************************");
	}
}
