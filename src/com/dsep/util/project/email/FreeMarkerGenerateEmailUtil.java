package com.dsep.util.project.email;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.dsep.entity.project.PassItem;
import com.dsep.entity.project.TeamMember;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerGenerateEmailUtil {

	private static Configuration cfg = null;
	private static Template template = null;

	private static FreeMarkerGenerateEmailUtil instance = null;

	/**
	 * 单例模式
	 */
	public static FreeMarkerGenerateEmailUtil getSingleFreeMarkerGenerateEmailUtil(
			String basePath, String templateName, String templateContent) {
		if (null == instance) {
			instance = new FreeMarkerGenerateEmailUtil(basePath, templateName,
					templateContent);
			return instance;
		}
		return instance;
	}

	public static void destroy() {
		instance = null;
	}

	// private的构造方法,单例模式
	private FreeMarkerGenerateEmailUtil(String basePath, String templateName,
			String templateContent) {
		BufferedWriter bw = null;
		File file = new File(basePath + File.separator + "EmailModule"
				+ File.separator + "project" + File.separator + templateName
				+ ".ftl");
		try {
			bw = new BufferedWriter(new FileWriter(file));
			bw.write(templateContent);
			bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 初始化工作
		cfg = new Configuration();
		// 设置模板文件位置
		try {
			cfg.setDirectoryForTemplateLoading(new File(basePath
					+ File.separator + "EmailModule" + File.separator
					+ "project" + File.separator));

			// 使用Configuration实例加载指定模板
			template = cfg.getTemplate(templateName + ".ftl");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 模板 + 数据模型 = 输出
	// Convention Over Configuration, 以EmailType枚举名作为文件名
	public String generateEmail(String basePath, PassItem item,
			String templateName) throws Exception {
		// 创建数据模型
		Map<String, String> rootMap = new HashMap<String, String>();
		rootMap.put("project", item.getItemName());
		Set<TeamMember> members = item.getTeamMembers();
		TeamMember principal = null;
		for (TeamMember m : members) {
			if (m.getIsPrincipal()) {
				principal = m;
				break;
			}
		}
		
		rootMap.put("teacher", principal.getName());

		// 合并处理（模板 + 数据模型）
		Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(basePath + "/EmailModule/project"
						+ templateName + ".html"), "utf-8"));
		template.process(rootMap, out);

		StringBuffer sb = new StringBuffer();
		File f = new File(basePath + "/EmailModule/project" + templateName
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