package com.dsep.unitTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Test;

import com.dsep.util.datacheck.LogicCheckErrorCode;
import com.dsep.util.datacheck.LogicDateRulesUtil;

public class LogicDateRulesUtilTest {
	@Test
	public void checkYearMonthBetweenTwoColumnValuesTest() {
		String inputTimeStr = "2014-10-15";
		String beginTimeStr = "2014-10-14";
		String endTimeStr = "2014-10-16";
		String anotherRestrictTimeStr = "2014-11-01";
		String actual = LogicDateRulesUtil
				.checkYearMonthBetweenTwoColumnValues(inputTimeStr,
						beginTimeStr, endTimeStr, anotherRestrictTimeStr);
		System.out.println(actual);
		Assert.assertEquals(LogicCheckErrorCode.Check_Pass, actual);

		inputTimeStr = "2014-10-15";
		beginTimeStr = "2014-10-15";
		endTimeStr = "2014-10-15";
		anotherRestrictTimeStr = "2014-10-15";
		actual = LogicDateRulesUtil.checkYearMonthBetweenTwoColumnValues(
				inputTimeStr, beginTimeStr, endTimeStr, anotherRestrictTimeStr);
		System.out.println(actual);
		Assert.assertEquals(LogicCheckErrorCode.Check_Pass, actual);

		inputTimeStr = "2014-10-15";
		beginTimeStr = "2014-10-16";
		endTimeStr = "2014-10-18";
		anotherRestrictTimeStr = "2014-10-20";
		actual = LogicDateRulesUtil.checkYearMonthBetweenTwoColumnValues(
				inputTimeStr, beginTimeStr, endTimeStr, anotherRestrictTimeStr);
		System.out.println(actual);
		Assert.assertEquals(
				LogicCheckErrorCode.DATE_EARILER_THAN_BEGIN_DATE_Error, actual);

		inputTimeStr = "2014-10-15";
		beginTimeStr = "2014-10-10";
		endTimeStr = "2014-10-14";
		anotherRestrictTimeStr = "2014-10-20";
		actual = LogicDateRulesUtil.checkYearMonthBetweenTwoColumnValues(
				inputTimeStr, beginTimeStr, endTimeStr, anotherRestrictTimeStr);
		System.out.println(actual);
		Assert.assertEquals(LogicCheckErrorCode.DATE_LATER_THAN_END_DATE_Error,
				actual);

		inputTimeStr = "20141015";
		beginTimeStr = "20141010";
		endTimeStr = "20141014";
		anotherRestrictTimeStr = "20141020";
		actual = LogicDateRulesUtil.checkYearMonthBetweenTwoColumnValues(
				inputTimeStr, beginTimeStr, endTimeStr, anotherRestrictTimeStr);
		System.out.println(actual);
		Assert.assertEquals(LogicCheckErrorCode.Date_Format_Error, actual);

		inputTimeStr = "2014-10-15";
		beginTimeStr = "2014-10-10";
		endTimeStr = "2014-10-18";
		anotherRestrictTimeStr = "2014-06-20";
		actual = LogicDateRulesUtil.checkYearMonthBetweenTwoColumnValues(
				inputTimeStr, beginTimeStr, endTimeStr, anotherRestrictTimeStr);
		System.out.println(actual);
		Assert.assertEquals(
				LogicCheckErrorCode.DATE_LATER_THAN_GRADUATE_DATE_Error, actual);
	}

	@Test
	public void checkYearMonthBetweenTwoDatesTest() {
		String inputTimeStr = "201410";
		String beginTimeStr = "201407";
		String endTimeStr = "201512";
		String actual = LogicDateRulesUtil.checkYearMonthBetweenTwoDates(
				inputTimeStr, beginTimeStr, endTimeStr);
		System.out.println(actual);
		Assert.assertEquals(LogicCheckErrorCode.Check_Pass, actual);

		inputTimeStr = "201407";
		beginTimeStr = "201407";
		endTimeStr = "201407";
		actual = LogicDateRulesUtil.checkYearMonthBetweenTwoDates(inputTimeStr,
				beginTimeStr, endTimeStr);
		System.out.println(actual);
		Assert.assertEquals(LogicCheckErrorCode.Check_Pass, actual);

		inputTimeStr = "2014-06";
		beginTimeStr = "2014-07-01";
		endTimeStr = "2014-07-01";
		actual = LogicDateRulesUtil.checkYearMonthBetweenTwoDates(inputTimeStr,
				beginTimeStr, endTimeStr);
		System.out.println(actual);
		Assert.assertEquals(LogicCheckErrorCode.Date_Format_Error, actual);

		inputTimeStr = "201406";
		beginTimeStr = "201405";
		endTimeStr = "201405";
		actual = LogicDateRulesUtil.checkYearMonthBetweenTwoDates(inputTimeStr,
				beginTimeStr, endTimeStr);
		System.out.println(actual);
		Assert.assertEquals(LogicCheckErrorCode.DATE_LATER_THAN_END_DATE_Error,
				actual);

		inputTimeStr = "201404";
		beginTimeStr = "201405";
		endTimeStr = "201405";
		actual = LogicDateRulesUtil.checkYearMonthBetweenTwoDates(inputTimeStr,
				beginTimeStr, endTimeStr);
		System.out.println(actual);
		Assert.assertEquals(
				LogicCheckErrorCode.DATE_EARILER_THAN_BEGIN_DATE_Error, actual);
	}

	@Test
	public void checkDateBetweenTwoDatesTest() {
		String inputTimeStr = "2014-09-20";
		String beginTimeStr = "2014-09-19";
		String endTimeStr   = "2014-09-21";

		String actual = LogicDateRulesUtil.checkDateBetweenTwoDates(
				inputTimeStr, beginTimeStr, endTimeStr);
		System.out.println(actual);
		Assert.assertEquals(LogicCheckErrorCode.Check_Pass, actual);
		
		inputTimeStr = "2014-09-20";
		beginTimeStr = "2014-09-20";
		endTimeStr   = "2014-09-20";

		actual = LogicDateRulesUtil.checkDateBetweenTwoDates(
				inputTimeStr, beginTimeStr, endTimeStr);
		System.out.println(actual);
		Assert.assertEquals(LogicCheckErrorCode.Check_Pass, actual);

		
		inputTimeStr = "20140920";
		beginTimeStr = "20140920";
		endTimeStr   = "20140920";

		actual = LogicDateRulesUtil.checkDateBetweenTwoDates(
				inputTimeStr, beginTimeStr, endTimeStr);
		System.out.println(actual);
		Assert.assertEquals(LogicCheckErrorCode.Date_Format_Error, actual);

		inputTimeStr = "2014-09-19";
		beginTimeStr = "2014-09-20";
		endTimeStr   = "2014-09-20";

		actual = LogicDateRulesUtil.checkDateBetweenTwoDates(
				inputTimeStr, beginTimeStr, endTimeStr);
		System.out.println(actual);
		Assert.assertEquals(LogicCheckErrorCode.DATE_EARILER_THAN_BEGIN_DATE_Error, actual);

		inputTimeStr = "2014-09-21";
		beginTimeStr = "2014-09-20";
		endTimeStr   = "2014-09-20";

		actual = LogicDateRulesUtil.checkDateBetweenTwoDates(
				inputTimeStr, beginTimeStr, endTimeStr);
		System.out.println(actual);
		Assert.assertEquals(LogicCheckErrorCode.DATE_LATER_THAN_END_DATE_Error, actual);
	}
	
	@Test
	public void checkDateOneCloTest() {
		String currentTimeStr = "2014-09-14";
		String compareTimeStr = "2014-09-20"; 
		String compareNumStr = "5";
		// @是对currentTime做加法
		// 比如currentTime是出国时间，compareTime是回国时间
		// 它们之间的间隔必须大于compareNum
		String operator = "@";

		String actual = LogicDateRulesUtil.checkDateOneClo(currentTimeStr,
				compareTimeStr, compareNumStr, operator);
		System.out.println(actual);
		Assert.assertEquals(LogicCheckErrorCode.Check_Pass, actual);
		
		currentTimeStr = "2014-09-15";
		compareTimeStr = "2014-09-20"; 
		compareNumStr  = "5";
		operator = "@";

		actual = LogicDateRulesUtil.checkDateOneClo(currentTimeStr,
				compareTimeStr, compareNumStr, operator);
		System.out.println(actual);
		Assert.assertEquals(LogicCheckErrorCode.Check_Pass, actual);
		
		currentTimeStr = "2014-09-16";
		compareTimeStr = "2014-09-20"; 
		compareNumStr  = "5";
		operator = "@";

		actual = LogicDateRulesUtil.checkDateOneClo(currentTimeStr,
				compareTimeStr, compareNumStr, operator);
		System.out.println(actual);
		Assert.assertEquals(currentTimeStr + "，" + compareTimeStr
				+ LogicCheckErrorCode.DATE_Interval_Error, actual);
		
		currentTimeStr = "2014-09-30";
		compareTimeStr = "2014-10-04"; 
		compareNumStr  = "5";
		operator = "@";

		actual = LogicDateRulesUtil.checkDateOneClo(currentTimeStr,
				compareTimeStr, compareNumStr, operator);
		System.out.println(actual);
		Assert.assertEquals(currentTimeStr + "，" + compareTimeStr
				+ LogicCheckErrorCode.DATE_Interval_Error, actual);
		
		
		currentTimeStr = "2014-10-04";
		compareTimeStr = "2014-09-30"; 
		compareNumStr  = "5";
		operator = "#";

		actual = LogicDateRulesUtil.checkDateOneClo(currentTimeStr,
				compareTimeStr, compareNumStr, operator);
		System.out.println(actual);
		Assert.assertEquals(currentTimeStr + "，" + compareTimeStr
				+ LogicCheckErrorCode.DATE_Interval_Error, actual);
	}
}
