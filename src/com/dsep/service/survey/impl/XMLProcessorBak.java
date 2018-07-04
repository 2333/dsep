package com.dsep.service.survey.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.dsep.dao.dsepmeta.survey.QuestionnaireDao;
import com.dsep.domain.dsepmeta.survey.LogicArr;
import com.dsep.domain.dsepmeta.survey.SurveyXMLQuestion;
import com.dsep.entity.survey.Questionnaire;
import com.dsep.util.GUID;
import com.dsep.util.survey.QType;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class XMLProcessorBak {
	
	

	private static Configuration cfg = null;

	private static Template SCQTemplate = null, MCQTemplate = null,
			blankQTemplate = null, matrixQTemplate = null, mixQTemplate = null,
			paneQTemplate = null, hintQTemplate = null,
			modifyJSPTemplate = null, paperNameTemplate = null,
			paperIntroTemplate = null, paperNameForUpdateTemplate = null,
			paperIntroForUpdateTemplate = null;

	private static HTMLAndXMLGenerator instance = null;

	// 存储生成结果的路径名
	private static String storagePath = "";

	// 存放各类ftl模板的路径名
	private static String templatePath = "";

	// 单例模式
	public static HTMLAndXMLGenerator getSingleFreeMarker(
			String surveyStoragePath, String surveyTemplatePath) {
		// path每次调用该函数都会更新
		HTMLAndXMLGenerator.storagePath = surveyStoragePath;
		HTMLAndXMLGenerator.templatePath = surveyTemplatePath;
		if (null == instance) {
			return new HTMLAndXMLGenerator();
		}
		return instance;
	}

	// private的构造方法,单例模式
	private HTMLAndXMLGenerator() {
		// 初始化工作
		cfg = new Configuration();
		// 设置模板文件位置
		try {
			cfg.setDirectoryForTemplateLoading(new File(templatePath));

			// 使用Configuration实例加载指定模板
			SCQTemplate = cfg.getTemplate("_SCQTemplate.ftl");
			MCQTemplate = cfg.getTemplate("_MCQTemplate.ftl");
			blankQTemplate = cfg.getTemplate("_blankQTemplate.ftl");
			matrixQTemplate = cfg.getTemplate("_matrixQTemplate.ftl");
			mixQTemplate = cfg.getTemplate("_mixQTemplate.ftl");
			paneQTemplate = cfg.getTemplate("_paneQTemplate.ftl");
			hintQTemplate = cfg.getTemplate("_hintQTemplate.ftl");
			modifyJSPTemplate = cfg.getTemplate("_modifyJSPTemplate.ftl", "UTF-8");
			paperNameTemplate = cfg.getTemplate("_paperNameTemplate.ftl");
			paperIntroTemplate = cfg.getTemplate("_paperIntroTemplate.ftl");
			paperNameForUpdateTemplate = cfg
					.getTemplate("_paperNameForUpdateTemplate.ftl");
			paperIntroForUpdateTemplate = cfg
					.getTemplate("_paperIntroForUpdateTemplate.ftl");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void destroy() {
		instance = null;
	}

	// 生成每个题目的XML，可编辑的HTML以及最终可发布的HTML
	public String generateXMLAndHTMLFiles(List<SurveyXMLQuestion> list,
			String currentQGUID, List<LogicArr> logicArr) throws Exception {
		// 设置XML的输出路径，即保存XML和HTML的文件夹路径
		String resultPath = storagePath + File.separator + currentQGUID;

		File quesFile = new File(resultPath);
		if (!quesFile.exists() && !quesFile.isDirectory()) {
			quesFile.mkdirs();
		} else {
			System.out.println("目录存在！");
		}

		// 生成n个XML文件，表示前台创建的n道题目
		ArrayList<String> htmlPathList = generateQuestions(list, resultPath);

		// 生成可编辑的HTML
		generateUpdateJSPFile(list, resultPath);

		// 生成实际可以发布的HTML
		generateHTMLFile(htmlPathList, resultPath, logicArr);

		return resultPath;
	}

	private ArrayList<String> generateQuestions(List<SurveyXMLQuestion> list,
			String resultPath) throws UnsupportedEncodingException,
			FileNotFoundException, TemplateException, IOException, Exception {
		// 每个HTML的完整路径集合
		ArrayList<String> htmlPathList = new ArrayList<String>();

		boolean firstSetPaperNameAndIntro = true;
		Map<String, SurveyXMLQuestion> rootMap = new HashMap<String, SurveyXMLQuestion>();
		for (int i = 0; i < list.size(); i++) {
			SurveyXMLQuestion q = list.get(i);
			rootMap.put("q", q);

			// 循环第一次处理问卷的名称和介绍，后续不再处理
			if (firstSetPaperNameAndIntro) {
				// 预览问卷的模板
				Writer paperNameOut = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(resultPath
								+ File.separator + "paperName.dat"), "utf-8"));
				Writer paperIntroOut = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(resultPath
								+ File.separator + "paperIntro.dat"), "utf-8"));

				paperNameTemplate.process(rootMap, paperNameOut);
				paperIntroTemplate.process(rootMap, paperIntroOut);

				// 修改问卷的模板
				Writer paperNameOutForUpdate = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(resultPath
								+ File.separator + "paperNameForUpdate.dat"),
								"utf-8"));
				Writer paperIntroOutForUpdate = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(resultPath
								+ File.separator + "paperIntroForUpdate.dat"),
								"utf-8"));
				paperNameForUpdateTemplate.process(rootMap,
						paperNameOutForUpdate);
				paperIntroForUpdateTemplate.process(rootMap,
						paperIntroOutForUpdate);
				// 设为false，该if中的语句只在第一次执行
				firstSetPaperNameAndIntro = false;
				paperNameOut.close();
				paperIntroOut.close();
				htmlPathList.add(resultPath + File.separator + "paperName.dat");
				htmlPathList
						.add(resultPath + File.separator + "paperIntro.dat");
			}

			String outPath = resultPath + File.separator + "第" + (i + 1) + "题";
			String xmlOutPath = outPath + ".xml";
			String htmlOutPath = outPath + ".html";
			htmlPathList.add(htmlOutPath);

			// 合并处理（模板 + 数据模型）
			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(xmlOutPath), "utf-8"));
			if (QType.SCQ.toString().equals(q.getqType())) {
				SCQTemplate.process(rootMap, out);

				convertXmlHtml(xmlOutPath, templatePath + File.separator
						+ "_SCQ.xsl", htmlOutPath);
			} else if (QType.MCQ.toString().equals(q.getqType())) {
				MCQTemplate.process(rootMap, out);

				convertXmlHtml(xmlOutPath, templatePath + File.separator
						+ "_MCQ.xsl", htmlOutPath);
			} else if (QType.blankQ.toString().equals(q.getqType())) {
				blankQTemplate.process(rootMap, out);

				convertXmlHtml(xmlOutPath, templatePath + File.separator
						+ "_blankQ.xsl", htmlOutPath);
			} else if (QType.matrixQ.toString().equals(q.getqType())) {
				matrixQTemplate.process(rootMap, out);

				convertXmlHtml(xmlOutPath, templatePath + File.separator
						+ "_matrixQ.xsl", htmlOutPath);

			} else if (QType.mixQ.toString().equals(q.getqType())) {
				mixQTemplate.process(rootMap, out);

				convertXmlHtml(xmlOutPath, templatePath + File.separator
						+ "_mixQ.xsl", htmlOutPath);
			} else if (QType.paneQ.toString().equals(q.getqType())) {
				paneQTemplate.process(rootMap, out);

				convertXmlHtml(xmlOutPath, templatePath + File.separator
						+ "_paneQ.xsl", htmlOutPath);
			} else if (QType.hintQ.toString().equals(q.getqType())) {
				hintQTemplate.process(rootMap, out);

				convertXmlHtml(xmlOutPath, templatePath + File.separator
						+ "_hintQ.xsl", htmlOutPath);
			}
			out.flush();
			out.close();
		}
		return htmlPathList;
	}

	private void generateUpdateJSPFile(List<SurveyXMLQuestion> list,
			String resultPath) {
		Map<String, List<SurveyXMLQuestion>> rootMap = new HashMap<String, List<SurveyXMLQuestion>>();
		rootMap.put("qList", list);
		String outPath = resultPath + File.separator + "update.jsp";

		// 合并处理（模板 + 数据模型）
		Writer out;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(outPath), "utf-8"));
			try {
				modifyJSPTemplate.process(rootMap, out);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch ( Exception e) {
			e.printStackTrace();
		}

	}

	// 通过转化后的每个题的html路径，将题目复制到同一个html文件下
	public void generateHTMLFile(List<String> htmlPathList, String resultPath,
			List<LogicArr> logicArr) {
		Iterator<String> itor = htmlPathList.iterator();
		BufferedReader reader = null;
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(resultPath + File.separator
							+ "real.html"), "utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 构造逻辑规则JSON
		String logicInput = "<input type=\"hidden\" value=\"[";
		for (LogicArr ele : logicArr) {
			// {'qId1':'qn','item1':'someTxt','qId2':'qN'}
			String tmp = "{'qId1':'" + ele.getqId1() + "','item1':'"
					+ ele.getItem1() + "','qId2':'" + ele.getqId2() + "'},";
			logicInput += tmp;
		}
		// remove last ','
		logicInput = logicInput.substring(0, logicInput.length() - 1);
		logicInput += "]\"></input>";

		try {
			System.out.println(logicInput);
			writer.write(logicInput);
			writer.newLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		while (itor.hasNext()) {
			try {
				reader = new BufferedReader(new InputStreamReader(
						new FileInputStream(itor.next()), "utf-8"));
				String line = reader.readLine();
				while (line != null) {
					String input = line.replaceAll("myChangeLine", "<br/>");
					writer.write(input);
					writer.newLine();
					line = reader.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			reader.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 使用XSLT将XML文档转换成HTML
	 * 
	 * @param xmlFileName
	 *            源XML文件名
	 * @param xslFileName
	 *            XSL文件名
	 * @param htmlFileName
	 *            输出的HTML文件名
	 * @return 返回HTML文件名
	 */
	public String convertXmlHtml(String xmlFileName, String xslFileName,
			String htmlFileName) throws Exception {
		// 创建XSLT引擎的工厂
		TransformerFactory tFactory = TransformerFactory.newInstance();
		// 创建XSLT引擎要使用的XSL文件源
		StreamSource source = new StreamSource(new File(xslFileName));
		// 创建XSLT引擎
		Transformer tx = tFactory.newTransformer(source);

		// 设置XSLT引擎的输出属性，使之输出为HTML格式，并且支持中文。
		Properties properties = tx.getOutputProperties();
		properties.setProperty(OutputKeys.ENCODING, "UTF-8");
		properties.setProperty(OutputKeys.METHOD, "html");
		tx.setOutputProperties(properties);

		// 创建XML文件源和HTML文件的结果流
		StreamSource xmlSource = new StreamSource(new File(xmlFileName));
		File targetFile = new File(htmlFileName);
		StreamResult result = new StreamResult(targetFile);
		// result = "<meta charset ='utf-8'>" + result;
		// 实现XSLT转换，根据XSL文件源将XML文件源转换成HTML结果流
		tx.transform(xmlSource, result);
		return targetFile.getAbsolutePath();
	}
}
