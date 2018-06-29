package com.dsep.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class String2Date {
	
	public static Date string2Date(String dateString,String format){
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date date = sdf.parse(dateString);
			return date;
		}
		catch (ParseException e)
		{
			return null;
		}
	
		
	}

}
