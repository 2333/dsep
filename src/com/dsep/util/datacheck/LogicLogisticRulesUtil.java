package com.dsep.util.datacheck;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 参与单位、参与学科排序数据的逻辑检查
 * @author lubin
 * @author yuyaolin
 */
public class LogicLogisticRulesUtil {

	public static String checkDisciplineOrUnitsRank(String str) {
		if (LogicStringRulesUtil.stringNullUtil(str)) {
			String regex = "";
			if (!str.contains("%")) {
				regex = "^[1-9]\\d*\\([1-9]\\d*\\)$"; //匹配a(b)格式的正则表达式
				if (!checkDataFormat(str, regex)) //如果数据的格式错误
					return LogicCheckErrorCode.Number_Format_Error;
				return checkRank(str);
			} else {
				//				regex = "^[1-9]\\d*\\([1-9]\\d*\\%\\)$";   //匹配a[b%]格式的正则表达式
				regex = "^[1-9]\\d*\\[[1-9]\\d*\\%\\]|[1-9]\\d*\\[\\d*\\.\\d+\\%\\]$";
				if (!checkDataFormat(str, regex)) //如果数据的格式错误
					return LogicCheckErrorCode.Number_Format_Error;
				return checkPercent(str);
			}
		} else
			return LogicCheckErrorCode.Value_Null_Error;

	}

	public static void main(String[] args) {
		checkDisciplineOrUnitsRank("5[1%]");
	}

	/**
	 * 检查a(b)排序的逻辑是否正确
	 * 
	 * @param str
	 * @return
	 */
	public static String checkRank(String str) {
		int index_a;
		int index_b;
		int num_a;
		int num_b;
		index_a = str.indexOf("(");
		index_b = str.indexOf(")");
		num_a = Integer.parseInt(str.substring(0, index_a));
		num_b = Integer.parseInt(str.substring(index_a + 1, index_b));

		if (num_a == 1 && num_b == 1)
			return LogicCheckErrorCode.Check_Pass;
		else if (num_a > 1 && num_b > 0 && num_b <= num_a)
			return LogicCheckErrorCode.Check_Pass;
		else if (num_b > num_a)
			return LogicCheckErrorCode.Unit_Number_Error;
		else
			return "ERROR";
	}

	/**
	 * 检查a(b%)或者a[b%]百分比的逻辑是否正确
	 * 
	 * @author yuyaolin
	 * @param str
	 * @return
	 */
	public static String checkPercent(String str) {
		int index_a;
		int index_b;
		double num_a;
		double num_b;

		index_a = str.indexOf("[");

		index_b = str.indexOf("%");
		//		num_a = Integer.parseInt(str.substring(0, index_a));
		num_a = Double.parseDouble(str.substring(0, index_a));
		//		num_b = Integer.parseInt(str.substring(index_a + 1, index_b));
		num_b = Double.parseDouble(str.substring(index_a + 1, index_b));

		if (num_a == 1 && num_b == 100)
			return LogicCheckErrorCode.Check_Pass;
		else if (num_a > 1 && num_b > 0 && num_b < 100)
			return LogicCheckErrorCode.Check_Pass;
		else if (num_a == 1 && num_b != 100)
			return LogicCheckErrorCode.Percent_OneDis_Error;
		else if (num_a != 1 && num_b == 100)
			return LogicCheckErrorCode.Percent_ManyDis_Error;
		else if (num_b > 100)
			return LogicCheckErrorCode.Percent_100_Error;
		else
			return "ERROR";
	}

	/**
	 * 检查数据格式是否正确
	 * @param str
	 * @param regex
	 * @return
	 */
	public static boolean checkDataFormat(String str, String regex) {
		if (LogicStringRulesUtil.stringNullUtil(str)) {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(str);
			return m.matches();
		} else
			return false;
	}

	/**
	 * 最后一个数字是否等于前面数字之和
	 * @return
	 */
	public static String checkAggregateCols(List<Integer> list) {
		int sum = 0;
		for (int i = 0; i < list.size() - 1; i++) {
			sum += list.get(i);
		}
		if (sum == list.get(list.size() - 1)) {
			return LogicCheckErrorCode.Check_Pass;
		} else {
			return LogicCheckErrorCode.AGGREGATE_ERROR;
		}
	}

	/**
	 * str1小于str2
	 */
	public static String checkLessThanOneCol(String str1, String str2) {
		Integer i, j;
		try {
			i = Integer.valueOf(str1);
			j = Integer.valueOf(str2);
		} catch (Exception e) {
			return LogicCheckErrorCode.Value_Number_Error;
		}
		if (i <= j) {
			return LogicCheckErrorCode.Check_Pass;
		} else {
			return LogicCheckErrorCode.Col_Comparation_Error + "str2";
		}
	}

}
