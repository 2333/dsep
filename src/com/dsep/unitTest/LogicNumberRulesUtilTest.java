package com.dsep.unitTest;

import org.junit.Test;

import com.dsep.util.datacheck.LogicNumberRulesUtil;

public class LogicNumberRulesUtilTest {
	@Test
	public void checkStringPercentTest() {
		String str = "100%";
		System.out.println(LogicNumberRulesUtil.checkStringPercent(str));

		str = "0%";
		System.out.println(LogicNumberRulesUtil.checkStringPercent(str));

		str = "0.00001%";
		System.out.println(LogicNumberRulesUtil.checkStringPercent(str));

		str = "10.0001%";
		System.out.println(LogicNumberRulesUtil.checkStringPercent(str));

		str = "00.010%";
		System.out.println(LogicNumberRulesUtil.checkStringPercent(str));

		str = "01.010%";
		System.out.println(LogicNumberRulesUtil.checkStringPercent(str));

		str = "101%";
		System.out.println(LogicNumberRulesUtil.checkStringPercent(str));

		str = "100.001%";
		System.out.println(LogicNumberRulesUtil.checkStringPercent(str));

		str = "-0.01%";
		System.out.println(LogicNumberRulesUtil.checkStringPercent(str));

		str = "-1%";
		System.out.println(LogicNumberRulesUtil.checkStringPercent(str));

		str = "test%";
		System.out.println(LogicNumberRulesUtil.checkStringPercent(str));
	}
	
	@Test
	public void checkStringISBNTest() {
		String str = "7-309-04547-5";
		System.out.println(LogicNumberRulesUtil.checkStringISBN(str));
	
		str = "9787302147510";
		System.out.println(LogicNumberRulesUtil.checkStringISBN(str));
		
		str = "9787513015035";
		System.out.println(LogicNumberRulesUtil.checkStringISBN(str));
		
		str = "7111165616";
		System.out.println(LogicNumberRulesUtil.checkStringISBN(str));

		str = "9787111165613";
		System.out.println(LogicNumberRulesUtil.checkStringISBN(str));

		str = "730213880X";
		System.out.println(LogicNumberRulesUtil.checkStringISBN(str));
		
		str = "7564105607";
		System.out.println(LogicNumberRulesUtil.checkStringISBN(str));
	}
}
