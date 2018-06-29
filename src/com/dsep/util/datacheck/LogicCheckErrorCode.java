package com.dsep.util.datacheck;

public class LogicCheckErrorCode {
	
	/**
	 * 检查通过
	 */
	public static String Check_Pass = "CHECKPASS";
	
	/**
	 * 字段值为空
	 */
	public static String Value_Null_Error = "字段值为空";
	
	/**
	 * 字段值包含中文
	 * 适用于字段值不包含中文情况
	 */
	public static String Value_Chinese_Error = "字段值包含中文";
	
	/**
	 * ISBN格式不正确
	 */
	public static String ISBN_Format_Error = "ISBN格式不正确";
	
	/**
	 * 日期格式不正确
	 */
	public static String Date_Format_Error = "日期格式不正确";
	
	/**
	 * 出生年月不符合逻辑
	 */
	public static String BirthDay_Logic_Error = "出生年月不符合逻辑";
	
	/**
	 * 年度不在当前评估有效期内
	 */
	public static String Year_Interval_Error = "年度不在当前评估有效期内";
	
	/**
	 * 时间不在当前评估有效期
	 */
	public static String Date_Interval_Error = "时间不在当前评估有效期";
	
	/**
	 * 结束时间小于开始时间
	 */
	public static String Start_End_Error = "结束时间小于开始时间";
	
	/**
	 * 日期不符合逻辑
	 */
	public static String Date_Logic_Error = "日期不符合逻辑";
	
	/**
	 * 起始时间为空
	 */
	public static String Start_Null_Error = "起始时间为空";
	
	/**
	 * 字段值不为数字
	 */
	public static String Value_Number_Error = "字段值不为数字";
	
	/**
	 * 字段值必须大于零
	 */
	public static String Number_Zero_Error = "字段值必须大于零";
	
	/**
	 * 字段值必须为正整数
	 */
	public static String Number_Integer_Error = "字段值必须为正整数";
	
	
	public static String Data_Length_Error = "字段长度超限";
	
	/**
	 * 字段值必须为非负数
	 */
	public static String Number_Nonnegative_Error = "字段值必须为非负数";
	
	/**
	 * 字段值必须数字
	 */
	public static String Number_String_Error = "字段值必须为数字";
	/**
	 * 限填字段值错误
	 */
	public static String Value_Restriction_Error = "限填字段值错误";
	
	/**
	 * 参与单位、学科数格式错误
	 */
	public static String Number_Format_Error = "参与单位、学科数格式错误";
	
	/**
	 * 单位署名次序大于参与单位数
	 */
	public static String Unit_Number_Error = "单位署名次序大于参与单位总数";
	
	/**
	 * 本学科所占比不应大于100%
	 */
	public static String Percent_100_Error = "本学科所占比不应大于100%";
	
	/**
	 * 仅有一个参与学科，本学科参与百分应为100%
	 */
	public static String Percent_OneDis_Error = "仅有一个参与学科，本学科参与百分应为100%";
	
	/**
	 * 有多个参与学科，本学科参与百分不应为100%
	 */
	public static String Percent_ManyDis_Error = "有多个参与学科，本学科参与百分不应为100%";
	
	
	/**
	 * 最后一个字段不等于前几个字段之和
	 */
	public static String AGGREGATE_ERROR = "最后一个字段不等于前几个字段之和";
	
	
	public static String DATE_EARILER_THAN_BEGIN_DATE_Error = "填写日期早于开始日期";
	
	
	public static String DATE_LATER_THAN_END_DATE_Error = "填写日期晚于结束日期";
	
	
	public static String DATE_LATER_THAN_GRADUATE_DATE_Error = "填写日期晚于毕业日期";
	
	
	public static String ISBN_Error = "ISBN不正确";
	
	public static String Email_Phone_Error = "不满足邮箱，手机或是固定电话格式";
	
	public static String Percent_Format_Error = "字段类型应该是百分号类型";

	public static String Percent_Bigger_Than_100_Error = "字段值不应大于100%";
	
	public static String Col_Comparation_Error = "字段应该小于";
	
	public static String DATE_Interval_Error = "日期间隔错误";
	
	public static String NUM_IN_COLUMN_Error = "值应该是非负小数，且小于指定的列";
} 