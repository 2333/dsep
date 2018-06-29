package com.dsep.util.datacheck;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.activemq.filter.function.makeListFunction;

/**
 * 时间规则逻辑检查类
 * 
 * @author lubin
 * @author YangJunLin
 * 
 */
public class LogicDateRulesUtil {

	/**
	 * 检测日期格式是否符合YYYYMM格式,并且满足基本的常识。满足返回true,不满足返回false.
	 * 
	 * @param str
	 * @return
	 * @author YangJunLin
	 */
	public static boolean checkDateIsSixNum(String str) {
		String regex = "^(1[89]|20)\\d{2}(0[1-9]|1[0-2])$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 检查日期符合出生年月规则,检验出生年月格式及合理性.格式为YYYYMM其中年龄应该小于150岁大于18岁.
	 * 
	 * @param str
	 * @return
	 * @author YangJunLin
	 */
	public static String checkDateBirthday(String str) {
		if (LogicStringRulesUtil.stringNullUtil(str)) {
			if ((checkDateIsSixNum(str))) {
				String subYear = str.substring(0, 4); // 获取出生日期的年份。
				int bornYear = Integer.parseInt(subYear);
				Calendar calendar = Calendar.getInstance();
				int currentYear = calendar.get(Calendar.YEAR); // 获取当前年份。
				int age = currentYear - bornYear; // 获得时间差，也就是年龄。
				if ((18 <= age) && (age <= 120))
					return LogicCheckErrorCode.Check_Pass;
				else
					return LogicCheckErrorCode.BirthDay_Logic_Error;
			} else
				return LogicCheckErrorCode.Date_Format_Error;

		} else
			return LogicCheckErrorCode.Value_Null_Error;
	}

	/**
	 * 检查一般年月规则,时间格式为YYYYMM，且不能超过当前的日期。
	 * 
	 * @param str
	 * @return
	 * @author YangJunLin
	 */
	public static String checkDateGeneralYearMonth(String str) {
		if (LogicStringRulesUtil.stringNullUtil(str)) {
			if (LogicDateRulesUtil.checkDateIsSixNum(str)) {
				String subYear = str.substring(0, 4);
				String subMonth = str.substring(4, 6);
				int year = Integer.parseInt(subYear);
				int month = Integer.parseInt(subMonth);
				Calendar calendar = Calendar.getInstance();
				int currentYear = calendar.get(Calendar.YEAR); // 获取当前年份。
				int currentMonth = calendar.get(Calendar.MONTH) + 1;
				if ((year < currentYear)
						|| ((year == currentYear) && (month <= currentMonth)))
					return LogicCheckErrorCode.Check_Pass;
				else
					return LogicCheckErrorCode.Date_Logic_Error;
			} else
				return LogicCheckErrorCode.Date_Format_Error;
		} else
			return LogicCheckErrorCode.Value_Null_Error;
	}

	/**
	 * 检查一般年月日，对应规则ID为D7.格式应当为YYYYMMDD。
	 * 
	 * @param str
	 * @return
	 * @author YangJunLin
	 */
	public static String checkDateGeneralYearMonthDay(String str) {
		if (LogicStringRulesUtil.stringNullUtil(str)) {
			String regex = "^(1[89]|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])$";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(str);
			if (m.matches())
				return LogicCheckErrorCode.Check_Pass;
			else
				return LogicCheckErrorCode.Date_Format_Error;
		} else {
			return LogicCheckErrorCode.Value_Null_Error;
		}
	}

	/**
	 * 项目或其它结束时间规则，参数为开始年月，结束时间必须大于开始时间。格式（YYYYMM）,对应规则为D3.
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @author YangJunLin
	 */
	public static String checkDateFinishYearMonth(String startDate,
			String endDate) {
		if (LogicStringRulesUtil.stringNullUtil(endDate)) {
			if (LogicStringRulesUtil.stringNullUtil(startDate)) {
				if ((LogicDateRulesUtil.checkDateIsSixNum(startDate) && (LogicDateRulesUtil
						.checkDateIsSixNum(endDate)))) {
					int startTime = Integer.parseInt(startDate);
					int endTime = Integer.parseInt(endDate);
					int time = endTime - startTime;
					if (time >= 0)
						return LogicCheckErrorCode.Check_Pass;
					else
						return LogicCheckErrorCode.Start_End_Error;
				} else
					return LogicCheckErrorCode.Date_Format_Error;
			} else
				return LogicCheckErrorCode.Start_Null_Error;
		} else
			return LogicCheckErrorCode.Value_Null_Error;
	}

	/**
	 * 检查结束年月日
	 * 项目或其它结束时间规则，参数为开始年月日，结束时间必须大于开始时间且开始或结束时间在评估范围之内（3年之内）。格式（YYYYMMDD）
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @author YangJunLin
	 */
	public static String checkDateFinishYearMonthDay(String startDate,
			String endDate, int period) {
		if (LogicStringRulesUtil.stringNullUtil(endDate)) {
			if (LogicStringRulesUtil.stringNullUtil(startDate)) {
				String regex = "^(1[89]|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])$";
				Pattern p = Pattern.compile(regex);
				Matcher ms = p.matcher(endDate);
				Matcher mn = p.matcher(startDate);
				if (ms.matches() && mn.matches()) {
					int startYear = Integer.parseInt(startDate.substring(0, 4));
					int startTime = Integer.parseInt(startDate);
					int endYear = Integer.parseInt(endDate.substring(0, 4));
					int endTime = Integer.parseInt(endDate);
					int time = endTime - startTime; // 结束时间和开始时间之差。
					Calendar calendar = Calendar.getInstance();
					int currentYear = calendar.get(Calendar.YEAR);
					double startPeriod = Math.abs((double) currentYear
							- startYear);
					double endPeriod = Math.abs((double) currentYear - endYear);
					if ((time > 0)
							&& (startPeriod <= period || endPeriod <= period))
						return LogicCheckErrorCode.Check_Pass;
					else
						return LogicCheckErrorCode.Start_End_Error;
				} else
					return LogicCheckErrorCode.Date_Format_Error;
			} else
				return LogicCheckErrorCode.Start_Null_Error;
		} else
			return LogicCheckErrorCode.Value_Null_Error;
	}

	/**
	 * 检查年度规则（评估有效年度内）,系统当前年度之前的五年之内。格式（YYYY）
	 * 
	 * @param year
	 * @param period
	 * @return
	 * @author YangJunLin
	 */
	public static String checkDateYear(String year, int startDate, int endDate) {
		if ((LogicStringRulesUtil.stringNullUtil(year))) {
			int date = Integer.parseInt(year);
			if (date > startDate && date <= endDate)
				return LogicCheckErrorCode.Check_Pass;
			else
				return LogicCheckErrorCode.Date_Interval_Error;
		} else
			return LogicCheckErrorCode.Value_Null_Error;
	}

	/**
	 * 检查年月规则（评估有效时间范围内）,系统当前年月的三年之内。格式（YYYYMM）.
	 * 
	 * @param year
	 * @param period
	 * @return
	 * @author YangJunLin
	 */
	public static String checkDateYearMonth(String year, int startDate,
			int endDate) {
		if ((LogicStringRulesUtil.stringNullUtil(year))) {
			if (checkDateIsSixNum(year)) {
				int date = Integer.parseInt(year);
				if (date >= startDate && date <= endDate)
					return LogicCheckErrorCode.Check_Pass;
				else
					return LogicCheckErrorCode.Date_Interval_Error;
			} else
				return LogicCheckErrorCode.Date_Format_Error;

		} else
			return LogicCheckErrorCode.Value_Null_Error;
	}

	/**
	 * 检查年月期限,格式YYYYMM-YYYYMM，起始年月不得超过系统当前时间。
	 * 
	 * @return
	 * @author YangJunLin
	 */
	public static String checkDatePeriod(String periodDate) {
		if ((LogicStringRulesUtil.stringNullUtil(periodDate))) {
			String regex = "^(1[89]|20)\\d{2}(0[1-9]|1[0-2])-(1[89]|20)\\d{2}(0[1-9]|1[0-2])$";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(periodDate);
			if (m.matches()) {
				String subYear = periodDate.substring(0, 4);
				String subMonth = periodDate.substring(4, 6);
				int year = Integer.parseInt(subYear);
				int month = Integer.parseInt(subMonth);
				int startTime = Integer.parseInt(periodDate.substring(0, 6));
				int endTime = Integer.parseInt(periodDate.substring(7, 13));
				Calendar calendar = Calendar.getInstance();
				int currentYear = calendar.get(Calendar.YEAR); // 获取当前年份。
				int currentMonth = calendar.get(Calendar.MONTH) + 1;
				if (startTime >= endTime) {
					return LogicCheckErrorCode.Date_Logic_Error;
				}
				if (((year < currentYear) || ((year == currentYear) && (month <= currentMonth)))
						&& (startTime < endTime)) { // 时间要小于当前时间，年份和月份都要小于。
					return LogicCheckErrorCode.Check_Pass;
				} else {
					return LogicCheckErrorCode.Start_End_Error;
				}
			} else {
				return LogicCheckErrorCode.Date_Format_Error;
			}
		} else
			return LogicCheckErrorCode.Value_Null_Error;
	}

	/*
	 * public static void main(String[] args) {
	 * System.out.println(checkDatePeriod("201508-199203")); }
	 */

	public static String checkYearOfBeforeNow(String year) {
		if ((LogicStringRulesUtil.stringNullUtil(year))) {
			// 注释掉的是检查年和月的
			//String regex = "^(19|20)\\d{2}(0[1-9]|1[0-2])$";
			
			String regex = "^(19|20)\\d{2}$";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(year);
			if (m.matches()) {
				int oldYear = Integer.parseInt(year);
				Calendar calendar = Calendar.getInstance();
				int currentYear = calendar.get(Calendar.YEAR); // 获取当前年份。
				if (oldYear <= currentYear) {
					return LogicCheckErrorCode.Check_Pass;
				} else {
					return LogicCheckErrorCode.Date_Logic_Error;
				}
			} else {
				return LogicCheckErrorCode.Date_Format_Error;
			}
		} else
			return LogicCheckErrorCode.Value_Null_Error;
	}

	public static String checkYearAndMonthOfBeforeNow(String str) {
		if (LogicStringRulesUtil.stringNullUtil(str)) {
			if (LogicDateRulesUtil.checkDateIsSixNum(str)) {
				String subYear = str.substring(0, 4);
				String subMonth = str.substring(4, 6);
				int year = Integer.parseInt(subYear);
				int month = Integer.parseInt(subMonth);
				Calendar calendar = Calendar.getInstance();
				int currentYear = calendar.get(Calendar.YEAR); // 获取当前年份。
				int currentMonth = calendar.get(Calendar.MONTH) + 1;
				if ((year < currentYear)
						|| ((year == currentYear) && (month <= currentMonth)))
					return LogicCheckErrorCode.Check_Pass;
				else
					return LogicCheckErrorCode.Date_Logic_Error;
			} else
				return LogicCheckErrorCode.Date_Format_Error;
		} else
			return LogicCheckErrorCode.Value_Null_Error;
	}

	public static String checkGeneralYear(String year) {
		if ((LogicStringRulesUtil.stringNullUtil(year))) {
			String regex = "^(19|20)\\d{2}$";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(year);
			if (m.matches()) {
				return LogicCheckErrorCode.Check_Pass;
			} else {
				return LogicCheckErrorCode.Date_Format_Error;
			}
		} else
			return LogicCheckErrorCode.Value_Null_Error;
	}

	public static String checkGeneralYearAndMonth(String year) {
		if ((LogicStringRulesUtil.stringNullUtil(year))) {
			String regex = "^(19|20)\\d{2}(0[1-9]|1[0-2])$";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(year);
			if (m.matches()) {
				return LogicCheckErrorCode.Check_Pass;
			} else {
				return LogicCheckErrorCode.Date_Format_Error;
			}
		} else
			return LogicCheckErrorCode.Value_Null_Error;
	}

	/**
	 * 检查填写日期是否在开始日期beginTime和结束日期endTime之间 
	 * 同时需要满足填写日期早于另一个限制日期anotherRestrictTimeStr
	 * 时间格式yyyy-MM-dd
	 * 
	 * p_next测试记录：通过！(com.dsep.unitTest.LogicDateRulesUtilTest)
	 */
	public static String checkYearMonthBetweenTwoColumnValues(
			String inputTimeStr, String beginTimeStr, String endTimeStr,
			String anotherRestrictTimeStr) {
		inputTimeStr           = inputTimeStr.replaceAll("-", "");
		beginTimeStr           = beginTimeStr.replaceAll("-", "");
		endTimeStr             = endTimeStr.replaceAll("-", "");
		anotherRestrictTimeStr = anotherRestrictTimeStr.replaceAll("-", "");
		
		Calendar inputTime = Calendar.getInstance();
		Calendar beginTime = Calendar.getInstance();
		Calendar endTime = Calendar.getInstance();
		Calendar graduateTime = Calendar.getInstance();

		DateFormat df1 = new SimpleDateFormat("yyyyMM");
		DateFormat df2 = new SimpleDateFormat("yyyyMMdd");
		
		
		if (endTimeStr.length() <= 6) {
			try {
				inputTime.setTime(df1.parse(inputTimeStr));
				beginTime.setTime(df1.parse(beginTimeStr));
				endTime.setTime(df1.parse(endTimeStr));
				graduateTime.setTime(df1.parse(anotherRestrictTimeStr));
			} catch (ParseException e) {
				return LogicCheckErrorCode.Date_Format_Error;
			}
		} else {
			try {
				inputTime.setTime(df2.parse(inputTimeStr));
				beginTime.setTime(df2.parse(beginTimeStr));
				endTime.setTime(df2.parse(endTimeStr));
				graduateTime.setTime(df2.parse(anotherRestrictTimeStr));
			} catch (ParseException e) {
				return LogicCheckErrorCode.Date_Format_Error;
			}
		}
		
		

		// 如果填写日期早于开始日期
		if (inputTime.compareTo(beginTime) < 0) {
			return LogicCheckErrorCode.DATE_EARILER_THAN_BEGIN_DATE_Error;
		}

		// 如果填写日期晚于结束日期
		if (inputTime.compareTo(endTime) > 0) {
			return LogicCheckErrorCode.DATE_LATER_THAN_END_DATE_Error;
		}

		// 如果填写日期晚于毕业日期
		if (inputTime.compareTo(graduateTime) > 0) {
			return LogicCheckErrorCode.DATE_LATER_THAN_GRADUATE_DATE_Error;
		}

		return LogicCheckErrorCode.Check_Pass;
	}

	/**
	 * 检查填写日期是否在开始日期beginTime和结束日期endTime之间 
	 * 数据库中日期格式是yyyyMM（注：如果yyyy-MM-dd格式的数据将不予通过）
	 * 
	 * p_next测试记录：通过！(com.dsep.unitTest.LogicDateRulesUtilTest)
	 */
	public static String checkYearMonthBetweenTwoDates(String inputTimeStr,
			String beginTimeStr, String endTimeStr) {
		Boolean flag = false;
		try {
			String check = "(19|20){1}[0-9]{1}[0-9]{1}(01|02|03|04|05|06|07|08|09|10|11|12){1}";

			Pattern regex = Pattern.compile(check);
			Matcher matcher1 = regex.matcher(inputTimeStr);
			Matcher matcher2 = regex.matcher(beginTimeStr);
			Matcher matcher3 = regex.matcher(endTimeStr);
			
			flag = matcher1.matches() || matcher2.matches()
					|| matcher3.matches();
		} catch (Exception e) {
			flag = false;
			return LogicCheckErrorCode.Date_Format_Error;
		}
		if (!flag) {
			return LogicCheckErrorCode.Date_Format_Error;
		}
		
		
		DateFormat df = new SimpleDateFormat("yyyyMM");
		Calendar inputTime = Calendar.getInstance();
		Calendar beginTime = Calendar.getInstance();
		Calendar endTime = Calendar.getInstance();

		try {
			inputTime.setTime(df.parse(inputTimeStr));
			beginTime.setTime(df.parse(beginTimeStr));
			endTime.setTime(df.parse(endTimeStr));

		} catch (ParseException e) {
			return LogicCheckErrorCode.Date_Format_Error;
		}

		// 如果填写日期早于开始日期
		// a.compareTo(b) a比b早返回-1, a和b相等返回0, a比b晚返回1
		if (inputTime.compareTo(beginTime) < 0) {
			return LogicCheckErrorCode.DATE_EARILER_THAN_BEGIN_DATE_Error;
		}

		// 如果填写日期晚于结束日期
		if (inputTime.compareTo(endTime) > 0) {
			return LogicCheckErrorCode.DATE_LATER_THAN_END_DATE_Error;
		}

		return LogicCheckErrorCode.Check_Pass;
	}

	/**
	 * 检查填写日期是否在两个值之间 数据库中日期格式是yyyy-MM-dd
	 * 
	 * p_next测试记录：通过！(com.dsep.unitTest.LogicDateRulesUtilTest)
	 */
	public static String checkDateBetweenTwoDates(String inputTimeStr,
			String beginTimeStr, String endTimeStr) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar inputTime = Calendar.getInstance();
		Calendar beginTime = Calendar.getInstance();
		Calendar endTime = Calendar.getInstance();

		try {
			inputTime.setTime(df.parse(inputTimeStr));
			beginTime.setTime(df.parse(beginTimeStr));
			endTime.setTime(df.parse(endTimeStr));

		} catch (ParseException e) {
			return LogicCheckErrorCode.Date_Format_Error;
		}

		// 如果填写日期早于开始日期
		if (inputTime.compareTo(beginTime) < 0) {
			return LogicCheckErrorCode.DATE_EARILER_THAN_BEGIN_DATE_Error;
		}

		// 如果填写日期晚于结束日期
		if (inputTime.compareTo(endTime) > 0) {
			return LogicCheckErrorCode.DATE_LATER_THAN_END_DATE_Error;
		}

		return LogicCheckErrorCode.Check_Pass;
	}

	/**
	 * 检查本列与指定列的日期
	 * 
	 * p_next测试记录：前端使用控件输入,故没有检测2014-18-50这样的输入！
	 * 其他通过！
	 * (com.dsep.unitTest.LogicDateRulesUtilTest)
	 */
	public static String checkDateOneClo(String currentTimeStr,
			String compareTimeStr, String compareNumStr, String operator) {
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar currentTime = Calendar.getInstance();
		Calendar compareTime = Calendar.getInstance();

		try {
			currentTime.setTime(df.parse(currentTimeStr));
			compareTime.setTime(df.parse(compareTimeStr));

		} catch (ParseException e) {
			return LogicCheckErrorCode.Date_Format_Error;
		}

		// @表示加法
		if (operator.equals("@")) {
			Integer compareNum = Integer.valueOf(compareNumStr);
			// 比如currentTime是出国时间，compareTime是回国时间
			// 它们之间的间隔必须大于compareNum
			currentTime.add(Calendar.DATE, compareNum);

			// 如果移动后currentTime晚于compareTime
			if (currentTime.compareTo(compareTime) > 0) {
				return currentTimeStr + "，" + compareTimeStr
						+ LogicCheckErrorCode.DATE_Interval_Error;
			}
		} else if (operator.equals("#")) {
			Integer compareNum = 0 - Integer.valueOf(compareNumStr);
			currentTime.add(Calendar.DATE, compareNum);
			// 如果移动后currentTime早于compareTime
			if (currentTime.compareTo(compareTime) < 0) {
				return currentTimeStr + "，" + compareTimeStr
						+ LogicCheckErrorCode.DATE_Interval_Error;
			}
		}
		return LogicCheckErrorCode.Check_Pass;
	}
}
