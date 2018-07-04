package com.mzule.simplemail;

import java.io.FileWriter;
import java.io.IOException;

public class Logger {
	 static String logPath = "E:/memberInfo20171220.log";
	public static void log(String info, int level) {
		if (level == 1) {
			log(info);
		} else {
			
		}
	}
	public static void log(String info) {
		FileWriter fw;
		try {
			fw = new FileWriter(logPath, true);
			System.out.println(info);
			fw.write(info); 
			fw.write("\n\r\n");
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
	public static void logStackTrace(StackTraceElement[] stackTraceElements) {
		for (StackTraceElement stackTraceElement : stackTraceElements) {
			log(stackTraceElement.toString());
		}
		
	}
}
