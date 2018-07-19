package com.dsep.util;

import java.util.UUID;

public class GUID {
	/**
	 * 获得一个新的GUID，以字符串的格式返回，不带“-”分隔符
	 * @return 返回一个GUID
	 */
	public static String get(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString().toUpperCase().replaceAll("-", "");
	}
}
