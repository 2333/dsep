package com.dsep.domain.dsepmeta.survey;

import java.util.List;

// 对应XML的问卷每道题目的选项的结构
public class SurveyXMLItem {
	private String itemId;
	private String rawData;
	private String str1;
	private String blank;
	private String str2;
	private Integer selectBoxNum;
	private Integer gapFillingNum;
	private List<String> logicQRefIds;
	private String parentQRefId;
	private String view;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getRawData() {
		return rawData;
	}

	public void setRawData(String rawData) {
		this.rawData = rawData;
	}

	public String getStr1() {
		return str1;
	}

	public void setStr1(String str1) {
		this.str1 = str1;
	}

	public String getBlank() {
		return blank;
	}

	public void setBlank(String blank) {
		this.blank = blank;
	}

	public String getStr2() {
		return str2;
	}

	public void setStr2(String str2) {
		this.str2 = str2;
	}

	public Integer getSelectBoxNum() {
		return selectBoxNum;
	}

	public void setSelectBoxNum(Integer selectBoxNum) {
		this.selectBoxNum = selectBoxNum;
	}

	public Integer getGapFillingNum() {
		return gapFillingNum;
	}

	public void setGapFillingNum(Integer gapFillingNum) {
		this.gapFillingNum = gapFillingNum;
	}

	public List<String> getLogicQRefIds() {
		return logicQRefIds;
	}

	public void setLogicQRefIds(List<String> logicQRefIds) {
		this.logicQRefIds = logicQRefIds;
	}

	public String getParentQRefId() {
		return parentQRefId;
	}

	public void setParentQRefId(String parentQRefId) {
		this.parentQRefId = parentQRefId;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

}
