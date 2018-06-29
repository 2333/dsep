package com.dsep.controller.survey;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dsep.domain.dsepmeta.survey.SurveyXMLItem;
import com.dsep.domain.dsepmeta.survey.SurveyXMLQuestion;
import com.dsep.util.GUID;
import com.dsep.util.survey.QType;

/**
 * 几个不同打分类型的controller公用的util方法合集，包含方法
 * 1、extractEvalResult()
 * 2、setCurrentBatchExpertInfoIntoSession()
 * 3、
 *
 */
public class UtilCommon {
	// 提取从前台传递的题目，将其转换为SurveyQuestion类型
	public static List<SurveyXMLQuestion> extract(List<QInfo> list,
			String paperName, String paperIntro) {
		List<SurveyXMLQuestion> qList = new ArrayList<SurveyXMLQuestion>();
		for (int i = 0; i < list.size(); i++) {
			QInfo data = list.get(i);
			SurveyXMLQuestion q = new SurveyXMLQuestion();
			String qId = GUID.get();
			q.setqId(qId);
			q.setPaperName(paperName);
			q.setPaperIntro(paperIntro);
			q.setqType(data.getqType());
			q.setqStem(data.getqStem());

			if (QType.SCQ.toString().equals(data.getqType())) {
				String[] str1Vals = data.getStr1Vals();
				q.setTotalNum(str1Vals.length);
				q.setMaxNum(1);
				q.setMinNum(1);

				q.setNecessary("necessary");

				List<SurveyXMLItem> itemList = new ArrayList<SurveyXMLItem>();

				for (String str1Val : str1Vals) {
					SurveyXMLItem item = new SurveyXMLItem();
					item.setItemId(GUID.get());
					item.setStr1(str1Val);
					item.setSelectBoxNum(1);
					item.setGapFillingNum(0);
					item.setParentQRefId(qId);
					item.setView("换行");
					itemList.add(item);
				}
				q.setItems(itemList);
				qList.add(q);
			} else if (QType.MCQ.toString().equals(data.getqType())) {
				String[] str1Vals = data.getStr1Vals();
				q.setTotalNum(str1Vals.length);
				q.setMaxNum(str1Vals.length);
				q.setMinNum(1);

				q.setNecessary("necessary");

				List<SurveyXMLItem> itemList = new ArrayList<SurveyXMLItem>();

				for (String str1Val : str1Vals) {
					SurveyXMLItem item = new SurveyXMLItem();
					item.setItemId(GUID.get());
					item.setStr1(str1Val);
					item.setSelectBoxNum(1);
					item.setGapFillingNum(0);
					item.setParentQRefId(qId);
					item.setView("换行");
					itemList.add(item);
				}
				q.setItems(itemList);
				qList.add(q);
			} else if (QType.blankQ.toString().equals(data.getqType())) {

				String[] str1Vals = data.getStr1Vals();
				List<SurveyXMLItem> itemList = new ArrayList<SurveyXMLItem>();

				// 填空题前台只传来一段文字，数组只有下标为0的唯一元素
				String str1Val = str1Vals[0];
				// 没有把\n变成myChangeLine
				//String rawStr1Val = str1Val;
				str1Val = str1Val.replace("\n", "myChangeLine");
				Pattern pattern = Pattern.compile("[^_]*_+");

				Matcher matcher = pattern.matcher(str1Val);
				int len = 0;
				int totalNum = 0;
				while (matcher.find()) {
					totalNum++;
					SurveyXMLItem item = new SurveyXMLItem();
					// 把前台textarea中的rawData直接set到item中，方便ftl中解析
					item.setRawData(str1Val);
					item.setItemId(GUID.get());
					item.setParentQRefId(qId);
					item.setSelectBoxNum(0);
					item.setGapFillingNum(1);
					String str1AndBlank = matcher.group();
					len += str1AndBlank.length();
					Pattern pattern2 = Pattern.compile("_+");
					Matcher matcher2 = pattern2.matcher(str1AndBlank);
					while (matcher2.find()) {
						String blank = matcher2.group();
						String str1 = str1AndBlank.substring(0,
								str1AndBlank.length() - blank.length());
						item.setStr1(str1);
					}

					item.setSelectBoxNum(0);
					item.setGapFillingNum(1);
					itemList.add(item);

				}

				itemList.get(itemList.size() - 1).setStr2(
						str1Val.substring(len, str1Val.length()));
				q.setTotalNum(totalNum);
				q.setMaxNum(totalNum);
				q.setMinNum(totalNum);
				q.setNecessary("necessary");
				q.setItems(itemList);
				qList.add(q);
			} else if (QType.matrixQ.toString().equals(data.getqType())) {
				// 获取矩阵题的第一个子题目
				data = list.get(++i);
				while (!"end".equals(data.getMatrixEnd())) {
					SurveyXMLQuestion q2 = createMatrixSubQ(data, q, qId);
					q.getSubQuestions().add(q2);

					data = list.get(++i);
				}
				// 刚刚++i获取的到了matrixEnd即某个矩阵的最后一个子题目，但是没有处理
				data = list.get(i);
				SurveyXMLQuestion q2 = createMatrixSubQ(data, q, qId);
				q.getSubQuestions().add(q2);

				qList.add(q);
			} else if (QType.mixQ.toString().equals(data.getqType())) {
				String[] str1Vals = data.getStr1Vals();
				List<SurveyXMLItem> itemList = new ArrayList<SurveyXMLItem>();

				// 如果有一个选项有选择也有填空，那么prevChooseItem会指向选择选项
				// 记录填空选项的rawData
				SurveyXMLItem prevChooseItem = null;
				for (String str1Val : str1Vals) {
					// 表明是选择部分，不是填空部分（填空部分前台会加入fromTextarea来标记）
					if (!(str1Val.startsWith("fromTextarea"))) {
						SurveyXMLItem item = new SurveyXMLItem();
						prevChooseItem = item;
						item.setItemId(GUID.get());
						item.setStr1(str1Val);
						item.setSelectBoxNum(1);
						item.setGapFillingNum(0);
						item.setParentQRefId(qId);
						item.setView("换行");
						itemList.add(item);
					} else {
						str1Val = str1Val.substring("fromTextarea".length());
						str1Val = str1Val.replace("\n", "myChangeLine");
						// 设置rawData方便模板的处理
						prevChooseItem.setRawData(str1Val);
						Pattern pattern = Pattern.compile("[^_]*_+");

						Matcher matcher = pattern.matcher(str1Val);
						int len = 0;
						int totalNum = 0;
						while (matcher.find()) {
							totalNum++;
							SurveyXMLItem item = new SurveyXMLItem();
							item.setItemId(GUID.get());
							item.setParentQRefId(qId);
							item.setSelectBoxNum(0);
							item.setGapFillingNum(1);
							// 占位符，表示这个item是填空
							item.setBlank("blank");
							item.setView("换行");
							String str1AndBlank = matcher.group();
							len += str1AndBlank.length();
							Pattern pattern2 = Pattern.compile("_+");
							Matcher matcher2 = pattern2.matcher(str1AndBlank);
							while (matcher2.find()) {
								String blank = matcher2.group();
								String str1 = str1AndBlank.substring(0,
										str1AndBlank.length() - blank.length());
								item.setStr1(str1);
							}

							item.setSelectBoxNum(0);
							item.setGapFillingNum(1);
							itemList.add(item);
						}

						itemList.get(itemList.size() - 1).setStr2(
								str1Val.substring(len, str1Val.length()));
					}

				}

				q.setTotalNum(0);
				q.setMaxNum(0);
				q.setMinNum(0);
				q.setNecessary("necessary");
				q.setItems(itemList);
				qList.add(q);
			} else if (QType.paneQ.toString().equals(data.getqType())) {
				qList.add(q);
			} else if (QType.hintQ.toString().equals(data.getqType())) {
				qList.add(q);
			}
		}
		return qList;
	}

	private static SurveyXMLQuestion createMatrixSubQ(QInfo data,
			SurveyXMLQuestion q, String qId) {
		SurveyXMLQuestion q2 = new SurveyXMLQuestion();
		String q2Id = GUID.get();
		q2.setqId(q2Id);
		q2.setfQRef(qId);
		q2.setqType(QType.matrixQ.toString());
		q2.setqStem(data.getqStem());
		q2.setNecessary("necessary");

		String[] str1Vals = data.getStr1Vals();

		q2.setTotalNum(str1Vals.length);
		q2.setMaxNum(1);
		q2.setMinNum(1);

		List<SurveyXMLItem> itemList = new ArrayList<SurveyXMLItem>();

		for (String str1Val : str1Vals) {
			SurveyXMLItem item = new SurveyXMLItem();
			item.setItemId(GUID.get());
			item.setStr1(str1Val);
			item.setSelectBoxNum(1);
			item.setGapFillingNum(0);
			item.setParentQRefId(q2Id);
			itemList.add(item);
		}
		q2.setItems(itemList);
		return q2;
	}

	/** 关于填空题正则的验证
	 * public static void main(String[] args) {
		Pattern pattern = Pattern.compile("[^_]*_+");

		String str1Val = "___，test___test___、托尔斯泰____abc。";
		Matcher matcher = pattern.matcher(str1Val);
		int len = 0;
		while (matcher.find()) {
			System.out.println(matcher.group());
			String str1AndBlank = matcher.group();
			len += str1AndBlank.length();
			Pattern pattern2 = Pattern.compile("_+");
			Matcher matcher2 = pattern2.matcher(str1AndBlank);
			while (matcher2.find()) {
				String blank = matcher2.group();
				String str1 = str1AndBlank.substring(0, str1AndBlank.length()
						- blank.length());
				System.out.println(str1 + blank);
			}
		}
		System.out.println(str1Val.substring(len, str1Val.length()));
	}
	 */

}
