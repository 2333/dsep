package com.dsep.util.crawler.zFire;

import org.apache.commons.codec.digest.DigestUtils;

public class KeyAllocator {
	/**
	 * 给马老发放Key,直接将输出结果不空行append到D:/MYSOUQ/develop/key的第一行即可
	 * @param args
	 */



	public static void main(String[] args) {
		String computerName = "MATHY";
		String mac_addr = "A4-1F-72-5C-0C-F8";
		System.out.println(DigestUtils.shaHex(DigestUtils.shaHex(mac_addr)));
        System.out.println(DigestUtils.shaHex(DigestUtils.shaHex(DigestUtils.shaHex(computerName))));
	}
}
