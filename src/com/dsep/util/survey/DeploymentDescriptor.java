package com.dsep.util.survey;

import java.io.File;

public class DeploymentDescriptor {
	private static String sp = File.separator;
	public static String descriptor = "/DSEP/evaluation/";
	public static String homeRoute = "/DSEP/evaluation/progress/";

	// 系统的问卷snippet模板
	public static String surveyTemplatePath = "WEB-INF" + sp + "classes" + sp
			+ "com" + sp + "dsep" + sp + "util" + sp + "survey" + sp
			+ "template";

	/*public static String surveyQuesTempaltePath = "WEB-INF" + sp + "classes"
			+ sp + "com" + sp + "dsep" + sp + "util" + sp + "survey" + sp
			+ "QuesTemplate";*/

	// 新建问卷在服务器上的存储位置
	/*public static String surveyStoragePath = "WEB-INF" + sp + "classes" + sp
			+ "com" + sp + "dsep" + sp + "util" + sp + "survey";*/
	public static String surveyStoragePath = "survey";


	// 问卷模板在服务器上的存储位置
	public static String surveyQNRTempaltePath = "survey" + sp
			+ "QNRTemplate";
}
