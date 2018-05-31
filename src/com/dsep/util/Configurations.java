package com.dsep.util;

import org.apache.commons.lang.StringUtils;
/**
 * 读取全局配置文件信息
 * @author thbin
 *
 */
public class Configurations {
	
	/**
	 * 读取配置文件获得当前DomainID
	 * @return
	 */
	private String domainId;
	private String preFix;
	private String postFix;
	private String contextPath;
	private String winCreateReportUrl;
	private String templatePath;
	private static Configurations configurations;
	public void initMethod(){
		configurations=this;
		configurations.domainId=this.domainId;
		configurations.postFix=this.postFix;
		configurations.preFix=this.preFix;
		configurations.contextPath = this.contextPath;
		configurations.winCreateReportUrl = this.winCreateReportUrl;
		configurations.templatePath = this.templatePath;
	}
	static public String getUrlContextPath(){
		return configurations.getContextPath();
	}
	static public String getCurrentDomainId(){
		//return "D201301";
		return configurations.getDomainId();
	}
	static public String getTablePrefix(){
		//return "DSEP";
		return configurations.getPreFix();
	}
	static public String getTablePostfix(){
		//return "2013";
		return configurations.getPostFix();
	}
	
	static public String getBackupTable(String tableName, String versionId){
		return tableName + getBackupTablePostfix(versionId);
	}
	
	static public String getBackupTablePostfix(String versionId){
		if(StringUtils.isNotBlank(versionId)){
			return "_BAK";
		}else{
			return "";
		}
	}
	static public String getWinReportUrl(){
		return configurations.getWinCreateReportUrl();
	}
	static public String getTemplateRootPath(){
		return configurations.getTemplatePath();
	}
	public String getDomainId() {
		return domainId;
	}
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}
	public String getPreFix() {
		return preFix;
	}
	public void setPreFix(String preFix) {
		this.preFix = preFix;
	}
	public String getPostFix() {
		return postFix;
	}
	public void setPostFix(String postFix) {
		this.postFix = postFix;
	}
	public String getContextPath() {
		return contextPath;
	}
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	public String getWinCreateReportUrl() {
		return winCreateReportUrl;
	}
	public void setWinCreateReportUrl(String winCreateReportUrl) {
		this.winCreateReportUrl = winCreateReportUrl;
	}
	public String getTemplatePath() {
		return templatePath;
	}
	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	public static Configurations getConfigurations() {
		return configurations;
	}
	public static void setConfigurations(Configurations configurations) {
		Configurations.configurations = configurations;
	}
	
}
