package com.meta.entity;

import java.io.Serializable;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public enum MetaControlType  implements Serializable{
	INPUT("I"),//单行文本
	TEXTAREA("T"),//多行文本
	DATE("D"),//日期
	YEAR("Y"),//年
	YEARMONTH("M"),//年月
	SELECT("S"),//下拉选择框
	MULSELECT("MS"),//多选的下拉框
	PROMPT("PT"),//autocomplete提示
	LINKPROMPT("LP"),//autocomplete关联提示
	MULPROMPT("MP"),//多选的autocomplete提示
	EDITABLE("E"),//可编辑多选控件
	RADIO("R"),//单选
	CHECKBOX("C"),//多选
	PERCENT("P"),//百分号
	CHECKTEXT("CT"),//多选并可以输入
	RADIOTEXT("RT"),//单选并可以输入
	FILE("F"),//文件
	LINKSELECT("LS"),//联动的下拉框
	ONLYREADINPUT("OI"),//只读的input
	ONLYREADRN("ON");//只读有行号限制
	
	private final String controlType;
	private MetaControlType(String controlType){
		this.controlType = controlType;
	}
	public String getControlType(){
		return controlType;
	}
	
	@Override
	public String toString() {
		return controlType;
	}
	/**
	 * 
	 * @param controlType
	 * @return
	 */	
	public static MetaControlType getControlType(String controlType)
	{
		String upperControlType=controlType.toUpperCase();
		if(upperControlType.equals("I")) return MetaControlType.INPUT;
		if(upperControlType.equals("T")) return MetaControlType.TEXTAREA;
		if(upperControlType.equals("D")) return MetaControlType.DATE;
		if(upperControlType.equals("Y")) return MetaControlType.YEAR;
		if(upperControlType.equals("M")) return MetaControlType.YEARMONTH;
		if(upperControlType.equals("S")) return MetaControlType.SELECT;
		if(upperControlType.equals("PT")) return MetaControlType.PROMPT;
		if(upperControlType.equals("LP")) return MetaControlType.LINKPROMPT;
		if(upperControlType.equals("E")) return MetaControlType.EDITABLE;
		if(upperControlType.equals("R")) return MetaControlType.RADIO;
		if(upperControlType.equals("C")) return MetaControlType.CHECKBOX;
		if(upperControlType.equals("P")) return MetaControlType.PERCENT;
		if(upperControlType.equals("F")) return MetaControlType.FILE;	
		if(upperControlType.equals("CT"))return MetaControlType.CHECKTEXT;
		if(upperControlType.equals("RT"))return MetaControlType.RADIOTEXT;
		if(upperControlType.equals("LS")) return MetaControlType.LINKSELECT;
		if(upperControlType.equals("MP")) return MetaControlType.MULPROMPT;
		if(upperControlType.equals("OI")) return MetaControlType.ONLYREADINPUT;
		if(upperControlType.equals("ON")) return MetaControlType.ONLYREADRN;
		if(upperControlType.equals("MS")) return MetaControlType.MULSELECT;
		return MetaControlType.INPUT;
	}
}
