package com.dsep.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VersionNoCreate {
	public static String CreateVersionNo(String unitId,String discId,String streamNo)
	{
		String nowTime = getTime();
		//拼接 格式为：学校id+学科id+时间+流水号
		if (discId != null)
			return ""+unitId+discId+nowTime+streamNo;
		else return ""+unitId+nowTime+streamNo;
	}
	/**
	 * 
	 * @param args
	 * @return 当前系统时间，精确到分，格式为：yyyymm
	 */
	private static String getTime() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
}

