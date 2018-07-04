package com.dsep.util;

import java.text.NumberFormat;

public class NumberFormatUtil {
	
	public static String getNumberFormat(int number,int length){
		NumberFormat formatter = NumberFormat.getNumberInstance();   
	    formatter.setMinimumIntegerDigits(length);   
	    formatter.setGroupingUsed(false);   
	    return formatter.format(number);
	}

}
