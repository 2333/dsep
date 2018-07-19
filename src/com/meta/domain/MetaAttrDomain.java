package com.meta.domain;

import java.io.Serializable;
import java.util.Map;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.meta.domain.search.SearchOperator;
import com.meta.domain.search.SearchRule;
import com.meta.domain.search.SearchType;
import com.meta.entity.MetaAttrStyle;
import com.meta.entity.MetaAttribute;
import com.meta.entity.MetaControlType;
import com.meta.entity.MetaDataType;
import com.meta.entity.MetaPercentData;

/*
 * 元属性领域模型，包括属性信息和显示样式信息
 */
public class MetaAttrDomain implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5774684842864373505L;
	private MetaAttribute attribute;
	private MetaAttrStyle attrStyle;
	private MetaDicDomain dic;
	private MetaCheckRuleDomain checkRule;

	/**
	 * 构造函数
	 * 
	 * @param attribute
	 *            属性信息
	 * @param attrStyle
	 *            属性显示风格信息
	 */
	public MetaAttrDomain(MetaAttribute attribute, MetaAttrStyle attrStyle) {
		this.attribute = attribute;
		this.attrStyle = attrStyle;
	}
	/**
	 * 针对该属性，构造一个查询规则
	 * @param op 查询条件操作符
	 * @param data 查询条件数据
	 * @return
	 */
	public SearchRule newSearchRule(SearchOperator op, String data){
		return new SearchRule(getName(), op, data, getSearchType());
	}
	/**
	 * 见当前数据类型转化为查询数据类型
	 * @return
	 */
	public SearchType getSearchType(){
		switch (getDataTypeObject()) {
		case INT:
			return SearchType.INT;
		case DOUBLE:
			return SearchType.NUMBER;
		case DATE:
			return SearchType.DATE;
		case DATETIME:
			return SearchType.DATETIME;
		case YEAR:
			return SearchType.YEAR;
		case YEARMONTH:
			return SearchType.YEARMONTH;
		default:
			return SearchType.STRING;
		}
	}
	/**
	 * 获得属性对应的ID
	 * 
	 * @return
	 */
	public String getId() {
		return attribute.getId();
	}

	/**
	 * 获得属性名称，也即是数据表的字段名
	 * 
	 * @return
	 */
	public String getName() {
		return attribute.getName();
	}

	/**
	 * 获得当前属性对应的字段名，对于属性和字段一对一的，则改名字等于字段名（此函数的写法存在问题：暴露了数据类型？？）；
	 * 对于一个属性对应多个字段的情况下，此处选择其中一个主要的字段类型（可根据情况定义）
	 * 
	 * @return
	 */
	public String getColumnName() {
		String columnName = attribute.getName();
		/*if (getDataTypeObject() == MetaDataType.PERCENT) {
			columnName = MetaPercentData.getValueColumn(attribute.getName());
		}*/
		return columnName;
	}

	/**
	 * 获得属性中文名称
	 * 
	 * @return
	 */
	public String getChsName() {
		return attribute.getChsName();
	}

	/**
	 * 获得属性数据类型，以字符串形式表达
	 * 
	 * @return
	 */
	public String getDataType() {
		return attribute.getDataType();
	}

	/**
	 * 获得属性数据类型，以数据类型对象的形式返回
	 * 
	 * @return
	 */
	public MetaDataType getDataTypeObject() {
		return MetaDataType.getDataType(getDataType());
	}

	/**
	 * 获得数据长度
	 * 
	 * @return
	 */
	public int getDataLength() {
		return attribute.getDataLength();
	}

	/**
	 * 获得数据是否允许空，true允许空，false不允许
	 * 
	 * @return
	 */
	public boolean isNull() {
		if (attribute.getIsNull().equals("0")) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 获得属性顺序号
	 * 
	 * @return
	 */
	public int getSeqNo() {
		return attribute.getSeqNo();
	}

	/**
	 * 获得属性索引好，如果索引号为100，则为主键
	 * 
	 * @return
	 */
	public int getIndexNo() {
		return attribute.getIndexNo();
	}

	/**
	 * 获得该属性对应的字典类型ID，只针对字典类型有效
	 * 
	 * @return
	 */
	public String getDicId() {
		return attribute.getDicId();
	}

	/**
	 * 获得属性的数据处理规则
	 * 
	 * @return
	 */
	public String getDataRuleId() {
		return attribute.getDataRule();
	}

	/**
	 * 获得属性对应的校验规则ID
	 * 
	 * @return
	 */
	public String getCheckRuleId() {
		return attribute.getCheckRule();
	}

	/**
	 * 获得属性的备注信息
	 * 
	 * @return
	 */
	public String getRemark() {
		return attribute.getRemark();
	}
	/**
	 * 获取是否该字段需要查重,返回布尔类型，0表示不需要查重，非0表示需要查重
	 * @return 返回布尔类型，false不需要查重，true需要查重
	 */
	public boolean isSimCheck(){
		if (attribute.getIsSimCheck().equals("0")) {
			return false;
		} else {
			return true;
		}		
	}
	/**
	 * 获取是否该字段需要查重的字符串类型
	 * @return 返回字符串类型
	 */
	public String getIsSimCheck(){
		return attribute.getIsSimCheck();
	}
	/**
	 * 获得属性显示风格ID
	 * 
	 * @return
	 */
	public String getStyleId() {
		return attrStyle.getId();
	}

	/**
	 * 获得属性显示长度
	 * 
	 * @return
	 */
	public int getDispLength() {
		return attrStyle.getDispLength();
	}

	/**
	 * 获得属性是否可编辑信息，true-可编辑，false-不可编辑
	 * 
	 * @return
	 */
	public boolean isEditable() {
		if (attrStyle.getEditable().equals("0")) {
			return false;
		} else {
			return true;
		}
	}
	/**
	 * 获取属性是否可排序，true-可编辑，false - 不可编辑
	 * @return
	 */
	public boolean isSortable(){
		if(attrStyle.getSortable().equals("0")){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 获得属性是否隐藏不显示信息，true-可编辑，false-不可编辑
	 * 
	 * @return
	 */
	public boolean isHidden() {
		if (attrStyle.getIsHidden().equals("0")) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 获得属性对应的处理控件类型，字符串格式
	 * 
	 * @return
	 */
	public String getControlType() {
		return attrStyle.getControlType();
	}

	/**
	 * 获得属性对应的处理控件类型，枚举格式
	 * 
	 * @return
	 */
	public MetaControlType getControlTypeObject() {
		return MetaControlType.getControlType(getControlType());
	}

	/**
	 * 获得属性在当前界面上的列的序号位置
	 * 
	 * @return
	 */
	public int getColNo() {
		return attrStyle.getColNo();
	}

	/**
	 * 获得属性在当前界面上的列的行号位置
	 * 
	 * @return
	 */
	public int getRowNo() {
		return attrStyle.getRowNo();
	}

	/**
	 * 获得属性在当前界面所占的列数
	 * 
	 * @return
	 */
	public int getColNums() {
		return attrStyle.getColNums();
	}

	/**
	 * 获得属性在当前界面上所占的行数
	 * 
	 * @return
	 */
	public int getRowNums() {
		return attrStyle.getRowNums();
	}

	/**
	 * 获得属性在当前界面上的样式类
	 * 
	 * @return
	 */
	public String getCss() {
		return attrStyle.getCss();
	}

	/**
	 * 获得当前属性显示对齐方式字符串
	 * 
	 * @return
	 */
	public String getAlign() {
		return attrStyle.getAlign();
	}

	/**
	 * 返回当前属性对应的字典的内容
	 * 
	 * @return
	 */
	public Map<String, String> getDicItems() {
		if (dic != null) {
			return dic.getDicItems();
		} else {
			return null;
		}
	}

	/**
	 * 获得校验规则名称
	 * 
	 * @return
	 */
	public String getCheckRuleName() {
		return checkRule.getName();
	}

	/**
	 * 获得属性逻辑检查规则分类
	 * 
	 * @return
	 */
	public String getCheckRuleCategory() {
		return checkRule.getCategory();
	}

	/**
	 * 获得校验规则对应的JS
	 * 
	 * @return
	 */
	public String getCheckRuleJS() {
		return checkRule.getJsCheck();
	}

	/**
	 * 获得校验规则对应的类信息
	 * 
	 * @return
	 */
	public String getCheckRuleClass() {
		return checkRule.getClassCheck();
	}

	/**
	 * 获得校验规则注释
	 * 
	 * @return
	 */
	public String getCheckRuleRemark() {
		return checkRule.getRemark();
	}

	/**
	 * 获得校验规则对应的可用参数个数
	 * 
	 * @return
	 */
	public int getCheckRuleParamCount() {
		return checkRule.getParamCount();
	}

	/**
	 * 获得校验规则对应的参数类型
	 * 
	 * @return
	 */
	public String[] getCheckRuleParamType() {
		if (checkRule != null)
			return checkRule.getParamType();
		return new String[0];
	}

	/**
	 * 获得校验规则对应的参数取值
	 * 
	 * @return
	 */
	public String[] getCheckRuleParamValue() {
		if (checkRule != null)
			return checkRule.getParamValue();
		return new String[0];
	}

	// 私有属性对应的getter和setter
	public MetaAttribute getAttribute() {
		return attribute;
	}

	public void setAttribute(MetaAttribute attribute) {
		this.attribute = attribute;
	}

	public MetaAttrStyle getAttrStyle() {
		return attrStyle;
	}

	public void setAttrStyle(MetaAttrStyle attrStyle) {
		this.attrStyle = attrStyle;
	}

	public MetaDicDomain getDic() {
		return dic;
	}

	public void setDic(MetaDicDomain dic) {
		this.dic = dic;
	}

	public MetaCheckRuleDomain getCheckRule() {
		return checkRule;
	}

	public void setCheckRule(MetaCheckRuleDomain checkRule) {
		this.checkRule = checkRule;
	}
}
