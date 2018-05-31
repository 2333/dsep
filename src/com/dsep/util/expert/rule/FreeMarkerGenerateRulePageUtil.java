package com.dsep.util.expert.rule;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dsep.entity.expert.ExpertSelectionRuleMeta;
import com.dsep.service.expert.rule.RuleMetaService;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 该工具使用前必读:
 * 利用com.dsep.unitTest.expert.GenerateRuleAndDetailsTest测试用例生成jsp
 * 
 * ！！！特别声明！！！
 * 1、该工具不保证页面的样式精美，您应该根据情况对CSS作出相应的改动
 * 2、运行完本程序之后如果您没有看见dialog_editRule_new_generated和dialog_addRule_new_generated.jsp页面，
 *   请在WebContent/Expert/rule文件夹上按F5刷新:)
 * 3、ftl模板中的sequ等字段都是有用的，如果在运行时发生异常，您应该检查GetRuleAndRuleDetailUtil捕获的字段是否在模板中少写了
 * 	  注：sequ、itemName、restrict1、operator这几个字段都是有用的，每个detail里面都应该有；restrict2、restrict3等在有些情况下也是有用的
 */
public class FreeMarkerGenerateRulePageUtil {

	private static Configuration cfg = null;
	private static Template template = null;
	private static Template template2 = null;

	private static FreeMarkerGenerateRulePageUtil instance = null;

	private static String path = System.getProperty("user.dir")
			+ "\\src\\com\\dsep\\util\\expert\\rule\\";

	private static String outputPath = System.getProperty("user.dir")
			+ "\\WebContent\\Expert\\rule\\";

	public static FreeMarkerGenerateRulePageUtil getSingleInstance() {
		if (null == instance) {
			return new FreeMarkerGenerateRulePageUtil();
		}
		return instance;
	}

	public static void destroy() {
		instance = null;
	}

	// private的构造方法,单例模式
	private FreeMarkerGenerateRulePageUtil() {
		// 初始化工作
		cfg = new Configuration();
		// 设置模板文件位置
		try {
			cfg.setDirectoryForTemplateLoading(new File(path));

			// 使用Configuration实例加载指定模板
			template = cfg.getTemplate("_addRulePageTemplate.ftl");
			template2 = cfg.getTemplate("_editRulePageTemplate.ftl");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 模板 + 数据模型 = 输出
	public void generateFiles(RuleMetaService ruleMetaService) throws Exception {

		


		Map<String, List<RuleDetail>> rootMap = new HashMap<String, List<RuleDetail>>();
		List<RuleDetail> list = new ArrayList<RuleDetail>();

		List<ExpertSelectionRuleMeta> metas = ruleMetaService.getAll();
		for (ExpertSelectionRuleMeta m : metas) {
			String sequence = String.valueOf(m.getId());
			String chName = m.getRuleCHName();
			String itemName = m.getRuleENName();
			String prior = m.getIsPrior()? "1" : "0";
			String operator = m.getOperatorName();
			RuleDetail detail = new RuleDetail(sequence, chName, itemName,
					prior, operator);
			list.add(detail);
		}
		rootMap.put("list", list);

		// 合并处理（模板 + 数据模型）
		Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(outputPath
						+ "dialog_addRule_new_generated.jsp"), "utf-8"));

		Writer out2 = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(outputPath
						+ "dialog_editRule_new_generated.jsp"), "utf-8"));
		template.process(rootMap, out);
		template2.process(rootMap, out2);
		out.close();
		out2.close();
	}
}
