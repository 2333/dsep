package com.dsep.util.datacheck;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 逻辑检查工具类，根据逻辑规则实现。 字符串规则校验
 * 
 * @author lubin
 * @author YangJunLin
 * 
 */
public class LogicStringRulesUtil {

	/**
	 * 判断字符串是否为空 对应规则ID为S1
	 * 
	 * @param str
	 * @return
	 * @author lubin
	 */
	public static String checkStringIsNotNull(String str) {
		if (stringNullUtil(str))
			return LogicCheckErrorCode.Check_Pass;
		else
			return LogicCheckErrorCode.Value_Null_Error;
	}

	/**
	 * 检查字符串是否由26个字母或数字组成的字符串 对应规则ID为S2
	 * 
	 * @param str
	 * @return
	 */
	public static String checkStringIsNotChinese(String str) {
		if (stringNullUtil(str)) {
			String regex = "^[A-Za-z0-9./-]+$";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(str);
			if (m.matches())
				return LogicCheckErrorCode.Check_Pass;
			else
				return LogicCheckErrorCode.Value_Chinese_Error;
		} else
			return LogicCheckErrorCode.Value_Null_Error;
	}

	/**
	 * 检查ISBN号，ISBN号由数字和连接符“-”组成。 对应规则ID为S3
	 * 
	 * @param str
	 * @return
	 * @author YangJunLin
	 */
	public static String checkStringIsISBN(String str) {
		if (stringNullUtil(str)) {
			String regex = "^\\d{3}-\\d-\\d{3}-\\d{5}-\\d$";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(str);
			if (m.matches())
				return LogicCheckErrorCode.Check_Pass;
			else
				return LogicCheckErrorCode.ISBN_Format_Error;
		} else
			return LogicCheckErrorCode.Value_Null_Error;
	}

	/**
	 * 判断数据是否非空，空则返回false，非空返回true
	 * 
	 * @param str
	 * @return
	 */
	public static boolean stringNullUtil(String str) {
		if (str == null || str.isEmpty())
			return false;
		else
			return true;
	}

	/**
	 * 检查数据长度，数据长度应该小于或者等于限定长度
	 * p_next测试记录：通过！(com.dsep.unitTest.LogicStringRulesUtilTest)
	 * 
	 */
	public static String checkDataLenght(String data, Integer standardLength) {
		Integer dataLength = data.length();
		if (dataLength > standardLength) {
			return LogicCheckErrorCode.Data_Length_Error;
		} else {
			return LogicCheckErrorCode.Check_Pass;
		}
	}

	/**
	 * 判断邮箱/手机/
	 * p_next测试记录：通过！(com.dsep.unitTest.LogicStringRulesUtilTest)
	 * 
	 */
	public static String checkStringPhoneOrEmail(String str) {
		// 验证邮箱
		boolean emailFlag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(str);
			emailFlag = matcher.matches();
		} catch (Exception e) {
			emailFlag = false;
		}

		// 验证手机号
		boolean cellphoneFlag = false;
		try {
			String check = "^(\\+86)?(13[0-9]|15[0-9]|18[0-9])\\d{8}$";
			// 去除手机号的空格
			str = str.replace(" ", "");
			
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(str);
			cellphoneFlag = matcher.matches();
		} catch (Exception e) {
			cellphoneFlag = false;
		}

		/**
		 *  验证座机号
		 *  telephone格式（3位或者4位区号 加 "-" 加 7位或者8位号码）
		 *  (010)8027503 
		 *  (010)-8027503 
		 *  010-8023333
		 */
		boolean telephoneFlag = false;
		try {
			String check1 = "[0]{1}[0-9]{2,3}-[0-9]{7,8}";
			String check2 = "\\([0]{1}[0-9]{2,3}\\)-[0-9]{7,8}";
			String check3 = "\\([0]{1}[0-9]{2,3}\\)[0-9]{7,8}";

			Pattern regex1 = Pattern.compile(check1);
			Matcher matcher1 = regex1.matcher(str);

			Pattern regex2 = Pattern.compile(check2);
			Matcher matcher2 = regex2.matcher(str);

			Pattern regex3 = Pattern.compile(check3);
			Matcher matcher3 = regex3.matcher(str);

			telephoneFlag = matcher1.matches() || matcher2.matches()
					|| matcher3.matches();
		} catch (Exception e) {
			telephoneFlag = false;
		}
		if (emailFlag || cellphoneFlag || telephoneFlag) {
			return LogicCheckErrorCode.Check_Pass;
		} else {
			return LogicCheckErrorCode.Email_Phone_Error;
		}
	}
}
