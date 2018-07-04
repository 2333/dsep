package com.dsep.unitTest;

import junit.framework.Assert;

import org.junit.Test;

import com.dsep.util.datacheck.LogicCheckErrorCode;
import com.dsep.util.datacheck.LogicStringRulesUtil;

public class LogicStringRulesUtilTest {
	/*
	 * 该方法内的数据已测试通过！ 
	 */
	@Test
	public void checkDataLenghtTest() {
		String data = "中文测试";
		Integer restrict = 3;
		String actual = LogicStringRulesUtil.checkDataLenght(data, restrict);
		Assert.assertEquals(LogicCheckErrorCode.Data_Length_Error , actual);
		
		data = "中文测试";
		restrict = 4;
		actual = LogicStringRulesUtil.checkDataLenght(data, restrict);
		Assert.assertEquals(LogicCheckErrorCode.Check_Pass , actual);
		
		data = "中文测试";
		restrict = 5;
		actual = LogicStringRulesUtil.checkDataLenght(data, restrict);
		Assert.assertEquals(LogicCheckErrorCode.Check_Pass , actual);
		
		// 实际长度是31
		data = "English Test Containing Blank ！";
		restrict = 30;
		actual = LogicStringRulesUtil.checkDataLenght(data, restrict);
		Assert.assertEquals(LogicCheckErrorCode.Data_Length_Error , actual);
		
		data = "English Test Containing Blank ！";
		restrict = 31;
		actual = LogicStringRulesUtil.checkDataLenght(data, restrict);
		Assert.assertEquals(LogicCheckErrorCode.Check_Pass , actual);
		
		data = "English Test Containing Blank ！";
		restrict = 32;
		actual = LogicStringRulesUtil.checkDataLenght(data, restrict);
		Assert.assertEquals(LogicCheckErrorCode.Check_Pass , actual);
	}
	
	
	/*
	 * 该方法内的数据已测试通过！ 
	 */
	@Test
	public void checkStringPhoneOrEmailTest() {
		
		String data = "18900894040";
		String actual = LogicStringRulesUtil.checkStringPhoneOrEmail(data);
		Assert.assertEquals(LogicCheckErrorCode.Check_Pass , actual);
		
		data = "137 0089 4040";
		actual = LogicStringRulesUtil.checkStringPhoneOrEmail(data);
		Assert.assertEquals(LogicCheckErrorCode.Check_Pass , actual);
		
		data = "151 337 89301";
		actual = LogicStringRulesUtil.checkStringPhoneOrEmail(data);
		Assert.assertEquals(LogicCheckErrorCode.Check_Pass , actual);
		
		data = "251 337 89301";
		actual = LogicStringRulesUtil.checkStringPhoneOrEmail(data);
		Assert.assertEquals(LogicCheckErrorCode.Email_Phone_Error , actual);
		
		data = "1393378930";
		actual = LogicStringRulesUtil.checkStringPhoneOrEmail(data);
		Assert.assertEquals(LogicCheckErrorCode.Email_Phone_Error , actual);

		data = "test@tset.qq.google.163.com";
		actual = LogicStringRulesUtil.checkStringPhoneOrEmail(data);
		Assert.assertEquals(LogicCheckErrorCode.Check_Pass , actual);
		
		data = "test@163com";
		actual = LogicStringRulesUtil.checkStringPhoneOrEmail(data);
		Assert.assertEquals(LogicCheckErrorCode.Email_Phone_Error , actual);
		
		data = "te@st@163.com";
		actual = LogicStringRulesUtil.checkStringPhoneOrEmail(data);
		Assert.assertEquals(LogicCheckErrorCode.Email_Phone_Error , actual);
		
		// 数字邮箱是可以的，例如qq邮箱
		data = "234@163.com";
		actual = LogicStringRulesUtil.checkStringPhoneOrEmail(data);
		Assert.assertEquals(LogicCheckErrorCode.Check_Pass , actual);
		
		data = "test$@163.com";
		actual = LogicStringRulesUtil.checkStringPhoneOrEmail(data);
		Assert.assertEquals(LogicCheckErrorCode.Email_Phone_Error , actual);
		
		data = "010-82537789";
		actual = LogicStringRulesUtil.checkStringPhoneOrEmail(data);
		Assert.assertEquals(LogicCheckErrorCode.Check_Pass , actual);
		
		data = "010-8253778";
		actual = LogicStringRulesUtil.checkStringPhoneOrEmail(data);
		Assert.assertEquals(LogicCheckErrorCode.Check_Pass , actual);
		
		data = "(010)8253778";
		actual = LogicStringRulesUtil.checkStringPhoneOrEmail(data);
		Assert.assertEquals(LogicCheckErrorCode.Check_Pass , actual);
		
		data = "010-8253778";
		actual = LogicStringRulesUtil.checkStringPhoneOrEmail(data);
		Assert.assertEquals(LogicCheckErrorCode.Check_Pass , actual);
		
		data = "0571-8253778";
		actual = LogicStringRulesUtil.checkStringPhoneOrEmail(data);
		Assert.assertEquals(LogicCheckErrorCode.Check_Pass , actual);
		
		data = "0108253778";
		actual = LogicStringRulesUtil.checkStringPhoneOrEmail(data);
		Assert.assertEquals(LogicCheckErrorCode.Email_Phone_Error , actual);
		
		data = "(010)-8253778";
		actual = LogicStringRulesUtil.checkStringPhoneOrEmail(data);
		Assert.assertEquals(LogicCheckErrorCode.Check_Pass , actual);
		
		data = "8253778";
		actual = LogicStringRulesUtil.checkStringPhoneOrEmail(data);
		Assert.assertEquals(LogicCheckErrorCode.Email_Phone_Error , actual);
		
		data = "0571-825377890";
		actual = LogicStringRulesUtil.checkStringPhoneOrEmail(data);
		Assert.assertEquals(LogicCheckErrorCode.Email_Phone_Error , actual);

		// 域名要两位及以上
		data = "test@w.c";
		actual = LogicStringRulesUtil.checkStringPhoneOrEmail(data);
		Assert.assertEquals(LogicCheckErrorCode.Email_Phone_Error , actual);

	}
	
}
