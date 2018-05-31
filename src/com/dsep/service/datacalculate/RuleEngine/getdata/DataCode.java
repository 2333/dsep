package com.dsep.service.datacalculate.RuleEngine.getdata;

public class DataCode {

	/**
	 * 学校ID
	 */
	public static final String UNIT_ID="UNIT_ID";
	/**
	 * 学科ID
	 */
	public static final String DISC_ID="DISC_ID";
	/**
	 * 实体ID
	 */
	public static final String ENTITY_ID="entityID";
	/**
	 * 数据表的表名
	 */
	public static final String TABLE_NAME="tableName";
	/**
	 * 所取字段的字段名称
	 */
	public static final String FIELD="field";
	/**
	 * 取数据将会用到的条件
	 */
	public static final String CONDITION="condition";
	/**
	 * 数据限制指令(参数)
	 */
	public static final String LIMIT="limit";
	/**
	 * 
	 */
	public static final String NUMBER="number";
	/**
	 * 末级指标ID
	 */
	public static final String INDEX_ID="indexID";
	/**
	 * 折算系数对应的数据类别项
	 */
	public static final String INDEX_CONT="indexCont";
	/**
	 * 折算系数
	 */
	public static final String INDEX_FACTOR="AVG_VALUE";
	/**
	 * 指令名称
	 */
	public static final String COMMANDER_NAME="commanderName";
	/**
	 * 本单位或者本学科参与的百分比或者排名
	 */
	public static final String SORT="sort";
	/**
	 * 获奖等级
	 */
	public static final String AWARDLEVEL="awardLevel";
	
	/////////////////命令名称////////////////////////////////////////////////
	/**
	 * 指令名--折算系数求和
	 */
	public static final String COMM_SUMFACTOR="SUMFACTOR";    //将类型按折算系数求和
	/**
	 * 指令名--计数
	 */
	public static final String COMM_COUNT="COUNT";           //计数用的count
	/**
	 * 指令名--取到一个折算系数
	 */
	public static final String COMM_FACTOR="FACTOR";       //取到一个折算系数
	/**
	 * 指令名--取到某一个数值
	 */
	public static final String COMM_NUMBER="VALUE";       //取到一个数字
	/**
	 * 指令名--加总一列数字
	 */
	public static final String COMM_SUMNUMBER="SUMNUMBER";  //将一列数字加总求和
}
