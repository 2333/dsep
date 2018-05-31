package com.dsep.util.expert.email;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.dsep.entity.expert.Expert;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerGenerateEmailUtil {

	private static Configuration cfg = null;
	private static Template template = null;

	private static FreeMarkerGenerateEmailUtil instance = null;

	/**
	 * 只能返回一种类型的instance，所以给一个专家单个邮箱发和给一个专家多个邮箱发要分开写，用两个不同的instance
	 * 这里用约定大于配置的方式，即枚举的名字是文件模板的名字
	 * 接收的参数：枚举类型.name()
	 */
	public static FreeMarkerGenerateEmailUtil getSingleFreeMarkerGenerateEmailUtil(
			String basePath, String templateName) {
			if (null == instance) {
				instance = new FreeMarkerGenerateEmailUtil(basePath, templateName);
				return instance;
			}
			return instance;
	}
	
	public static void destroy() {
		instance = null;
	}
	
	// private的构造方法,单例模式
	private FreeMarkerGenerateEmailUtil(String basePath, String templateName) {
		// 初始化工作
		cfg = new Configuration();
		// 设置模板文件位置
		try {
			cfg.setDirectoryForTemplateLoading(new File(basePath
					+ "/EmailModule/"));

			// 使用Configuration实例加载指定模板
			template = cfg.getTemplate(templateName + ".ftl");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	// 模板 + 数据模型 = 输出
	// Convention Over Configuration, 以EmailType枚举名作为文件名
	public String generateEmail(String basePath, Expert expert, String templateName, String emailAddr, String validateCode)
			throws Exception {
		// 创建数据模型
		Map<String, String> rootMap = new HashMap<String, String>();
		rootMap.put("id", expert.getId());
		rootMap.put("expertName", expert.getExpertName());
		rootMap.put("discId", expert.getRealDiscId());
		rootMap.put("expertEmail1", expert.getExpertEmail1());
		rootMap.put("expertEmail2", expert.getExpertEmail2());
		rootMap.put("expertEmail3", expert.getExpertEmail3());
		rootMap.put("validateCode1", expert.getValidateCode1());
		rootMap.put("validateCode2", expert.getValidateCode2());
		rootMap.put("validateCode3", expert.getValidateCode3());
		rootMap.put("emailAddr", emailAddr);
		rootMap.put("validateCode", validateCode);

		// 合并处理（模板 + 数据模型）
		Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(basePath
						+ "/EmailModule/"
						+ templateName + ".html"), "utf-8"));
		template.process(rootMap, out);
		
		StringBuffer sb = new StringBuffer();
		File f = new File(basePath
				+ "/EmailModule/" + templateName
				+ ".html");
		try {
			FileInputStream in = new FileInputStream(f);
			byte[] buffer = new byte[2014];
			int length = -1;
			while (-1 != (length = in.read(buffer, 0, buffer.length))) {
				sb.append(new String(buffer, 0, length));
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();

	}
	
}