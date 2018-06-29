package com.dsep.util;

public class TextConfiguration {

	private String discInfoFill; //学科信息填报
	private String discReportManage;//学科报告管理
	private String discNumber;//学科编号
	private String discName;//学科名称
	private String discBaseInfo;//学科基本信息
	private String discResultBackup;//学科成果备份与还原
	private String discPreManage;//学科预参评管理
	private String discResultManage;//学科成果管理
	private String discResultQuery;//学科成果查询
	private String disc;//学科
	private static TextConfiguration textConfiguration;
	public void initConfigMethod(){
		textConfiguration = this;
		textConfiguration.discInfoFill = this.discInfoFill;
		textConfiguration.discReportManage = this.discReportManage;
		textConfiguration.discNumber = this.discNumber;
		textConfiguration.discName = this.discName;
		textConfiguration.discResultBackup = this.discResultBackup;
		textConfiguration.discPreManage = this.discPreManage;
		textConfiguration.discResultManage = this.discResultManage;
		textConfiguration.discResultQuery = this.discResultQuery;
		textConfiguration.disc = this.disc;
		textConfiguration.discBaseInfo = this.discBaseInfo;
		
	}

	public String getDiscResultManage() {
		return discResultManage;
	}

	public void setDiscResultManage(String discResultManage) {
		this.discResultManage = discResultManage;
	}

	public String getDiscResultQuery() {
		return discResultQuery;
	}

	public void setDiscResultQuery(String discResultQuery) {
		this.discResultQuery = discResultQuery;
	}

	public String getDiscPreManage() {
		return discPreManage;
	}

	public void setDiscPreManage(String discPreManage) {
		this.discPreManage = discPreManage;
	}

	public String getDiscResultBackup() {
		return discResultBackup;
	}
	public void setDiscResultBackup(String discResultBackup) {
		this.discResultBackup = discResultBackup;
	}
	public String getDiscBaseInfo() {
		return discBaseInfo;
	}
	public void setDiscBaseInfo(String discBaseInfo) {
		this.discBaseInfo = discBaseInfo;
	}
	public String getDiscInfoFill() {
		return discInfoFill;
	}
	public void setDiscInfoFill(String discInfoFill) {
		this.discInfoFill = discInfoFill;
	}
	public String getDiscReportManage() {
		return discReportManage;
	}
	public void setDiscReportManage(String discReportManage) {
		this.discReportManage = discReportManage;
	}
	public String getDiscNumber() {
		return discNumber;
	}
	public void setDiscNumber(String discNumber) {
		this.discNumber = discNumber;
	}
	public String getDiscName() {
		return discName;
	}
	public void setDiscName(String discName) {
		this.discName = discName;
	}
	public String getDisc() {
		return disc;
	}
	public void setDisc(String disc) {
		this.disc = disc;
	}

	public static TextConfiguration getTextConfiguration() {
		return textConfiguration;
	}

	public static void setTextConfiguration(TextConfiguration textConfiguration) {
		TextConfiguration.textConfiguration = textConfiguration;
	}
	
}
