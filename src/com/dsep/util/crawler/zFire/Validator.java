package com.dsep.util.crawler.zFire;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.commons.codec.digest.DigestUtils;

import com.dsep.util.crawler.spider.SiteSetting;

public class Validator {
	public static boolean validate() throws Exception {
		boolean hasComputerName = false;
		boolean hasMacAddr = false;
		FileReader reader = new FileReader(SiteSetting.key);
		BufferedReader br = new BufferedReader(reader);
		
		String line = null;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
			System.out.println(DigestUtils.shaHex(DigestUtils.shaHex(ComputerInfo.getMacAddress())));
			System.out.println(DigestUtils.shaHex(DigestUtils.shaHex(DigestUtils.shaHex(ComputerInfo.getComputerName()))));
			if (line.trim().contains(DigestUtils.shaHex(DigestUtils.shaHex(ComputerInfo.getMacAddress())))) {
				hasMacAddr = true;
			} 
			if (line.contains(DigestUtils.shaHex(DigestUtils.shaHex(DigestUtils.shaHex(ComputerInfo.getComputerName()))))) {
				hasComputerName = true;
			}
		}
		//关闭读取流
		br.close();
		reader.close();
		
        return hasMacAddr && hasComputerName;
		
		
	}
}
