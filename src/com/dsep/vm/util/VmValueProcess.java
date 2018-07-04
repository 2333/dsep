package com.dsep.vm.util;

import com.dsep.util.StringProcess;

public class VmValueProcess {
	public static String getShowingString(String paramString){
		if( StringProcess.isNull(paramString)){
			return "/";
		}
		else
			return paramString;
	}
}
