package com.dsep.domain.dsepmeta.survey;

import java.util.ArrayList;
import java.util.List;

//对应XML的问卷每道题目的结构
public class SurveyXMLQuestion {
	private String paperName;
	private String paperIntro;

	// GUID
	private String qId;
	// q1 q2 .. qN
	private String qSequ;
	private String qType;
	private String qStem;
	private Integer totalNum;
	private Integer minNum;
	private Integer maxNum;
	private List<SurveyXMLQuestion> subQuestions = new ArrayList<SurveyXMLQuestion>();
	private String necessary;
	private String fQRef;
	private List<SurveyXMLItem> items;

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

	public String getqId() {
		return qId;
	}

	public void setqId(String qId) {
		this.qId = qId;
	}

	public String getqSequ() {
		return qSequ;
	}

	public void setqSequ(String qSequ) {
		this.qSequ = qSequ;
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

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getMinNum() {
		return minNum;
	}

	public void setMinNum(Integer minNum) {
		this.minNum = minNum;
	}

	public Integer getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}

	public List<SurveyXMLQuestion> getSubQuestions() {
		return subQuestions;
	}

	public void setSubQuestions(List<SurveyXMLQuestion> subQuestions) {
		this.subQuestions = subQuestions;
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

	public List<SurveyXMLItem> getItems() {
		return items;
	}

	public void setItems(List<SurveyXMLItem> items) {
		this.items = items;
	}

}
