package com.dsep.controller.survey;

import com.dsep.domain.dsepmeta.survey.LogicArr;

public class UtilDataFromJSP {
	private QInfo[] qInfo = new QInfo[] {};
	private String paperName;
	private String paperIntro;
	private LogicArr[] logicArr = new LogicArr[] {};
	private String type;

	public QInfo[] getqInfo() {
		return qInfo;
	}

	public void setqInfo(QInfo[] qInfo) {
		this.qInfo = qInfo;
	}

	public String getPaperName() {
		return paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

	public String getPaperIntro() {
		return paperIntro;
	}

	public void setPaperIntro(String paperIntro) {
		this.paperIntro = paperIntro;
	}

	public LogicArr[] getLogicArr() {
		return logicArr;
	}

	public void setLogicArr(LogicArr[] logicArr) {
		this.logicArr = logicArr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}

class QInfo {
	private String qType;
	private String qSequ;
	private String qStem;
	private String necessary;
	private String fQRef;
	private String[] str1Vals = new String[] {};
	private Integer itemNum;
	private String matrixEnd;

	public String[] getStr1Vals() {
		return str1Vals;
	}

	public void setStr1Vals(String[] str1Vals) {
		this.str1Vals = str1Vals;
	}

	public String getqSequ() {
		return qSequ;
	}

	public void setqSequ(String qSequ) {
		this.qSequ = qSequ;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public String getqType() {
		return qType;
	}

	public void setqType(String qType) {
		this.qType = qType;
	}

	public String getqStem() {
		return qStem;
	}

	public void setqStem(String qStem) {
		this.qStem = qStem;
	}

	public String getNecessary() {
		return necessary;
	}

	public void setNecessary(String necessary) {
		this.necessary = necessary;
	}

	public String getfQRef() {
		return fQRef;
	}

	public void setfQRef(String fQRef) {
		this.fQRef = fQRef;
	}

	public String getMatrixEnd() {
		return matrixEnd;
	}

	public void setMatrixEnd(String matrixEnd) {
		this.matrixEnd = matrixEnd;
	}
}
