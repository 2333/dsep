package com.dsep.util.datacheck;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.activemq.filter.function.makeListFunction;

/**
 * 逻辑检查数字规则工具类
 * 
 * @author lubin
 * 
 */
public class LogicNumberRulesUtil {

	/**
	 * 检查数字是否非负整数、正整数要求，
	 * 
	 * @param str
	 * @param index
	 *            index=1表示为正整数，index=0表示为非负整数
	 * @return
	 */
	public static String checkNumberInteger(String str, int index) {
		if (LogicStringRulesUtil.stringNullUtil(str)) {
			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher isNum = pattern.matcher(str);
			if (isNum.matches()) {
				String regex;
				if (index >= 1) {
					regex = "^[1-9]\\d*$";
					Pattern p = Pattern.compile(regex);
					Matcher m = p.matcher(str);
					if (m.matches())
						return LogicCheckErrorCode.Check_Pass;
					else
						return LogicCheckErrorCode.Number_Zero_Error;
				} else {
					regex = "^[1-9]\\d*|0$";
					Pattern p = Pattern.compile(regex);
					Matcher m = p.matcher(str);
					if (m.matches())
						return LogicCheckErrorCode.Check_Pass;
					else
						return LogicCheckErrorCode.Number_Integer_Error;
				}
			} else
				return LogicCheckErrorCode.Number_String_Error;
		} else
			return LogicCheckErrorCode.Value_Null_Error;
	}

	/**
	 * 检查数字为非负数
	 * 
	 * @param str
	 * @return
	 */
	public static String checkNumberFloat(String str) {
		if (LogicStringRulesUtil.stringNullUtil(str)) {
			String regex = "^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0$";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(str);
			if (m.matches())
				return LogicCheckErrorCode.Check_Pass;
			else
				return LogicCheckErrorCode.Number_Nonnegative_Error;
		} else
			return LogicCheckErrorCode.Value_Null_Error;
	}

	public static String checkNumPositiveInt(String str) {
		if (LogicStringRulesUtil.stringNullUtil(str)) {
			String regex = "^[1-9]\\d*$";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(str);
			if (m.matches())
				return LogicCheckErrorCode.Check_Pass;
			else
				return LogicCheckErrorCode.Number_Integer_Error;
		} else
			return LogicCheckErrorCode.Number_String_Error;
	}
	
	public static String checkNumInColumn(String data, String sum) {
		if (LogicStringRulesUtil.stringNullUtil(data)
				&& LogicStringRulesUtil.stringNullUtil(data)) {
			String regex = "^[1-9]\\d*$|^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0$";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(data);
			Matcher m2 = p.matcher(sum);

			if (m.matches() && m2.matches()) {
				if (Float.parseFloat(data) >= 0
						&& Float.parseFloat(data) <= Float.parseFloat(sum)) {
					return LogicCheckErrorCode.Check_Pass;
				} else {
					return LogicCheckErrorCode.NUM_IN_COLUMN_Error;
				}
			} else
				return LogicCheckErrorCode.Number_Nonnegative_Error;
		} else
			return LogicCheckErrorCode.Number_String_Error;
	}
	
	public static String checkNumPositiveIntOrNull(String str) {
		if (LogicStringRulesUtil.stringNullUtil(str)) {
			String regex = "^[1-9]\\d*$";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(str);
			if (m.matches())
				return LogicCheckErrorCode.Check_Pass;
			else
				return LogicCheckErrorCode.Number_Integer_Error;
		} else
			return LogicCheckErrorCode.Check_Pass;
	}

	public static String checkNumNonNegativeInt(String str) {
		if (LogicStringRulesUtil.stringNullUtil(str)) {
			String regex = "^([1-9]\\d*|0)$";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(str);
			if (m.matches())
				return LogicCheckErrorCode.Check_Pass;
			else
				return LogicCheckErrorCode.Number_Nonnegative_Error;
		} else
			return LogicCheckErrorCode.Number_String_Error;
	}

	public static String checkNumNonnegativeNum(String str) {
		if (LogicStringRulesUtil.stringNullUtil(str)) {
			String regex = "^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0$";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(str);
			if (m.matches())
				return LogicCheckErrorCode.Check_Pass;
			else
				return LogicCheckErrorCode.Number_Nonnegative_Error;
		} else
			return LogicCheckErrorCode.Value_Null_Error;
	}

	/**
	 * 验证ISBN是否合法
	 * p_next测试记录：通过！(com.dsep.unitTest.LogicNumberRulesUtilTest)
	 */
	public static String checkStringISBN(String isbn) {
		try {
			isbn = isbn.replace("-", "");
			/*
			 * 从以978打头的ISBN-13转换成ISBN-10
			 * 注意：只有前级为978的ISBN-13才有对应的ISBN-10
			 */
			if (isbn.length() == 13 && isbn.startsWith("978")) {
				// ****************************************************************
				// 当ISBN为13位时，进行的校验，用于2007年1月1日后的出版物
				// 数据格式：从左至右前12位为ISBN数据，第13位为校验位
				// 校验方法：
				// (1) 从左至右将前12位数的取其奇位数和和偶位数和
				// (2) 将偶位数和乘3，并其与奇位数和的和，得加权和
				// (3) 第13位校验位计算方法，校验位为C：
				//         M + C ≡ 0 (mod 10)
				// ****************************************************************

				// ISBN为13位数据时，前3位目前只能是“978”（已实行）或“979”（暂未实行）
				if (!isbn.startsWith("978") && !isbn.startsWith("979")) {
					return LogicCheckErrorCode.ISBN_Error;
				}
				char[] cs = isbn.toCharArray();
				// 取出前12位数字进行加权和计算
				int countEven = 0;
				int countOdd = 0;
				for (int i = 0; i < 12; i++) {
					int c = cs[i] - '0';
					// 若前12位数据中有非数字字符，则抛出异常
					if (c < 0 || c > 9) {
						return LogicCheckErrorCode.ISBN_Error;
					}
					// 分别计算奇位数和偶位数的和
					if ((i & 0x1) == 0) {
						countOdd += c;
					} else {
						countEven += c;
					}
				}
				// 求加权和
				int count = countOdd + (countEven * 3);

				// 取出校验位数据            
				if (cs[12] < '0' || cs[12] > '9') {
					// 校验位为非0~9字符时，抛出异常
					return LogicCheckErrorCode.ISBN_Error;
				}

				int checkBitInt = cs[12] - '0';
				// 进行校验
				if ((count + checkBitInt) % 10 == 0) {
					return LogicCheckErrorCode.Check_Pass; // 校验成功
				} else {
					return LogicCheckErrorCode.ISBN_Error; // 校验失败
				}
			}
			if (isbn.length() != 10) {
				return LogicCheckErrorCode.ISBN_Error;
			}

			int weight = 10;
			int rollingSum = 0;
			for (int i = 0; i < 9; i++) {
				int isbnDigit = Character.digit(isbn.charAt(i), 10);
				rollingSum += isbnDigit * weight--;
			}

			int mod = rollingSum % 11;
			mod = 11 - mod;
			if (mod == 11)
				mod = 0;

			char checkSum = isbn.charAt(9);
			if (Character.toLowerCase(checkSum) == 'x') {
				if (mod == 10)
					return LogicCheckErrorCode.Check_Pass;
			} else {
				if (Character.digit(checkSum, 10) == mod)
					return LogicCheckErrorCode.Check_Pass;
			}
			return LogicCheckErrorCode.ISBN_Error;
		} catch (Exception e) {
			return LogicCheckErrorCode.ISBN_Error;
		}
	}

	/**
	 * 检查是否是百分号类型 [0-9].xxxx% [1-9][0-9].xxxx%
	 * 
	 * [0-9]% [1-9][0-9]%
	 * 
	 * 100.0000% 100%
	 * 
	 * p_next测试记录：通过！(com.dsep.unitTest.LogicNumberRulesUtilTest)
	 */
	public static String checkStringPercent(String str) {
		if (str.endsWith("%")) {
			str = str.substring(0, str.length() - 1);
			// 浮点类型
			if (str.contains(".")) {
				Double d;
				try {
					d = Double.valueOf(str);
				} catch (Exception e) {
					return LogicCheckErrorCode.Number_String_Error;
				}
				if (d < 0) {
					return LogicCheckErrorCode.Number_Nonnegative_Error;
				} else if (d > 100) {
					return LogicCheckErrorCode.Percent_Bigger_Than_100_Error;
				}

			}
			// 整形
			else {
				Integer i;
				try {
					i = Integer.valueOf(str);
				} catch (Exception e) {
					return LogicCheckErrorCode.Number_String_Error;
				}
				if (i < 0) {
					return LogicCheckErrorCode.Number_Nonnegative_Error;
				} else if (i > 100) {
					return LogicCheckErrorCode.Percent_Bigger_Than_100_Error;
				}
			}
		} else {
			return LogicCheckErrorCode.Percent_Format_Error;
		}

		return LogicCheckErrorCode.Check_Pass;
	}
}
