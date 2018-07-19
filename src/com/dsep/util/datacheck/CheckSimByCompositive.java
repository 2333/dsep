package com.dsep.util.datacheck;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

/**
 * @author lubin
 * @author YangJunLin
 *         本相似度匹配算法由三部分组成：词形，占0.8；词序，占0.1；句长，占0.1；具体算法规则是根据两篇相关论文综合得到的。
 * 
 */
public class CheckSimByCompositive {

	/**
	 * @author YangJunLin
	 * @author lubin
	 * @param content
	 * @return 具体的分词算法，返回LIST。
	 */
	public static List<String> getWords(String content) {
		List<String> list = new ArrayList<String>();
		Reader input = new StringReader(content);
		IKSegmenter iks = new IKSegmenter(input, true);
		Lexeme lexeme = null;
		try {
			while ((lexeme = iks.next()) != null) {
				list.add(lexeme.getLexemeText());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取字符串长度
	 */
	public static int getStringLength(String str) {
		return str.length();
	}

	/**
	 * 计算句长相似度
	 */
	public static double getLengthSim(String str1, String str2) {
		return 1.0 - Math
				.abs((double) (getStringLength(str1) - getStringLength(str2))
						/ (getStringLength(str1) + getStringLength(str2)));
	}

	/**
	 * 计算词形相似度
	 * 
	 * @author lubin
	 */
	public static double getWordsSim(List<String> list1, List<String> list2) {
		return (double) 2 * getCommonWordsLength(list1, list2)
				/ (list1.size() + list2.size());
	}

	/**
	 * 计算分词后的列表中，共同包含的词语的个数
	 * 
	 * @author lubin
	 */
	public static int getCommonWordsLength(List<String> list1,
			List<String> list2) {
		int length = 0;
		if (list1.size() >= list2.size()) {
			for (String str : list2) {
				if (list1.contains(str))
					length++;
			}
		} else {
			for (String str : list1) {
				if (list2.contains(str))
					length++;
			}
		}
		return length;
	}

	/**
	 * @author YangJunLin
	 * @param list1
	 * @param list2
	 * @return 计算两个STRING的逆序值。
	 */
	public static double getNiXuSim(List<String> list1, List<String> list2) {
		String[] str1 = new String[list1.size()];
		String[] str2 = new String[list2.size()];
		list1.toArray(str1);
		list2.toArray(str2);
		int n1 = str1.length;
		int n2 = str2.length;
		double record3 = 0.0;
		int onwes = 0; // 共有的并且只有一次的词语的总数。
		int count = 0;
		if (n1 <= n2) {
			int[] num = new int[n1];
			for (int i = 0; i < n1; i++) {
				int temp = 0;
				for (int j = 0; j < n2; j++) {
					if (str1[i].equals(str2[j])) {
						num[i] = j;
						temp++;
					}
				}
				if (temp != 1) {
					num[i] = 100; // 设置边界值。
				} else if (temp == 1) {
					onwes++;
				}
			}
			if (onwes < 1) {
				record3 = 0;
			} else if (onwes == 1) {
				record3 = 1.0;
			} else {
				for (int i = 0; i < n1; i++) {
					for (int j = i; j < n1; j++) {
						if (num[i] == 100) {
							break;
						} else {
							if (num[i] > num[j]) {
								count++;
							}
						}
					}
				}
				// record3 = 1.0 - count / (onwes - 1.0);
				record3 = 1.0 - count / ((n1 * (n1 - 1)) / 2.0);
			}
			// for (int a : num) {
			// System.out.print(a + " ");
			// }
			// System.out.println("onwes=" + onwes);
			// System.out.println("count=" + count);
		} else {
			int[] num = new int[n2];
			for (int i = 0; i < n2; i++) {
				int temp = 0;
				for (int j = 0; j < n1; j++) {
					if (str1[j].equals(str2[i])) {
						num[i] = j;
						temp++;
					}
				}
				if (temp != 1) {
					num[i] = 100; // 设置边界值。
				} else if (temp == 1) {
					onwes++;
				}
				if (onwes == 0) {
					record3 = 0;
				} else if (onwes == 1) {
					record3 = 1;
				} else {
					for (int i1 = 0; i1 < n2; i1++) {
						for (int j = i1; j < n2; j++) {
							if (num[i1] == 100) {

							} else {
								if (num[i1] > num[j]) {
									count++;
								}
							}
						}
					}
					// record3 = 1.0 - count / (onwes - 1.0);
					record3 = 1.0 - count / ((n2 * (n2 - 1)) / 2.0);
				}
			}
		}
		// System.out.println(n1 + " " + n2);
		// System.out.println(record3);
		return record3;
	}

	/**
	 * 判断一个字符在字符串中出现的次数
	 */
	// public static int getCount(String s, String ch) {
	// return s.split(ch).length - 1;
	// }

	/**
	 * 过滤list，得到所有的词都只出现一次的词语
	 */
	// public static List<String> getOnceList(List<String> list) {
	// List<String> resList = new ArrayList<String>();
	// for (String str : list) {
	// if (getCount(list.toString(), str) == 1)
	// resList.add(str);
	// }
	// return resList;
	// }

	/**
	 * 判断一个单词在list中的位置序号
	 */
	// public static int getSequenceNo(List<String> list, String str) {
	// int sequenceNo = -1;
	// for (int i = 0; i < list.size(); i++) {
	// if (list.get(i).equals(str)) {
	// sequenceNo = i;
	// break;
	// }
	// }
	// return sequenceNo;
	// }

	/**
	 * 计算Pfirst向量,此向量不仅包含了位置信息，还包含所在位置的值信息
	 */
	// public static List<String> getFirstVector(List<String> commonWords,
	// List<String> oldFirstList) {
	// List<String> list = new ArrayList<String>();
	// for (String str : commonWords) {
	// list.add(getSequenceNo(oldFirstList, str) + "," + str);
	// }
	// return list;
	// }

	/**
	 * 计算Psecond向量,用于计算逆序数
	 */
	// public static int[] getSecondVector(List<String> Pfirst,
	// List<String> oldSecondList) {
	// int[] arr = new int[Pfirst.size()];
	// int i = 0;
	// for (String str : oldSecondList) {
	// for (String vector : Pfirst) {
	// if (str.equals(vector.split(",")[1]))
	// arr[i++] = Integer.parseInt(vector.split(",")[0]);
	// }
	// }
	// return arr;
	// }

	// public static double getWordOrderSim(List<String> list1, List<String>
	// list2) {
	// List<String> commonWords = getCommonWords(getOnceList(list1),
	// getOnceList(list2));
	// if (commonWords.size() == 0 || commonWords == null)
	// return 0.0;
	// else {
	// List<String> Pfirst = getFirstVector(commonWords, list1);
	// int maxInvertedSeqNum = Pfirst.size() * (Pfirst.size() - 1) / 2;
	// if (maxInvertedSeqNum == 0)
	// return 0.0;
	// else
	// return (double) (1.0 - CountInvertedSeqUtil
	// .getInvertedSeqNum(getSecondVector(Pfirst, list2))
	// / maxInvertedSeqNum);
	// }
	// }

	/**
	 * 得到公共子序列（仅出现一次）
	 */
	// public static List<String> getCommonWords(List<String> list1,
	// List<String> list2) {
	// List<String> list = new ArrayList<String>();
	// if (list1.size() >= list2.size()) {
	// for (String str : list2) {
	// if (list1.contains(str))
	// list.add(str);
	// }
	// } else {
	// for (String str : list1) {
	// if (list2.contains(str))
	// list.add(str);
	// }
	// }
	// return list;
	// }
	//
	/**
	 * @author YangJunLin
	 * @param list
	 * @param simNum
	 * @return 返回一个对比结果的LIST.
	 */
	public static List<String[]> checkSimilarity(List<String[]> list,
			double simNum) {
		// Date d1 = new Date();
		List<String[]> result = new ArrayList<String[]>();
		List<List<String>> IKSeg = new ArrayList<List<String>>();
		for (String[] contents : list) {
			IKSeg.add(getWords(contents[1].toString()));
		}
		// Date d2 = new Date();
		// System.out.println("分词耗时......" + (d2.getTime() - d1.getTime()));
		for (int i = 0; i < IKSeg.size(); i++) {
			String resId = "";
			for (int j = i + 1; j < IKSeg.size(); j++) {
				// double sim = 0.8
				// * getWordsSim(IKSeg.get(i), IKSeg.get(j))
				// + 0.15
				// * getLengthSim(list.get(i)[1].toString(),
				// list.get(j)[1].toString()) + 0.05
				// * getNiXuSim(IKSeg.get(i), IKSeg.get(j));
				double sim1 = checkSimOfTwoString(list.get(i)[1].toString(),
						list.get(j)[1].toString());
				if (sim1 >= simNum)
					resId += list.get(j)[0].toString() + ",";
			}
			if (!resId.equals("")) {
				resId += list.get(i)[0].toString() + ",";
				result.add(resId.split(","));

				// CheckSimByCompositive.writerText2("第" + (i + 1) + "组：",
				// false,
				// simNum);
				// CheckSimByCompositive.writerText2(resId, true, simNum);

				resId = "";
			}
		}

		// for(int i=0; i<list.size();i++){
		// for(int j=i+1;j<list.size();j++){
		// if(separationSim(list.get(i)[1].toString(),list.get(j)[1].toString())
		// >= simNum){
		// resId+=list.get(j)[1].toString()+",";
		// }
		// }
		// if(!resId.equals("")){
		// resId+=list.get(i)[1].toString()+",";
		// result.add(resId.split(","));
		// resId="";
		// }
		// }

		return result;
	}

	/**
	 * 用于查重多列的算法
	 * 
	 * @param list
	 * @param simNum
	 * @return
	 */
	public static List<String[]> checkSimilarityForColumns(List<String[]> list,
			double simNum) {
		List<String[]> result = new ArrayList<String[]>();
		if(list.size() == 0) return result;
		int columnLength = list.get(0).length;
		for (int i = 0; i < list.size(); i++) {
			String resId = "";
			for (int j = i + 1; j < list.size(); j++) {
				for (int q = 1; q < columnLength; q++) {
					double sim1 = checkSimOfTwoString(
							list.get(i)[q].toString(),
							list.get(j)[q].toString());
					if (sim1 >= simNum) {
						resId += list.get(j)[0].toString() + ",";
						break;
					}
				}
			}
			if (!resId.equals("")) { // sim_id添加进数组的最后一个元素
				resId += list.get(i)[0].toString() + ",";
				result.add(resId.split(","));
				resId = "";
			}
		}
		return result;
	}

	/**
	 * @param str1
	 * @param str2
	 * @return 返回两个字符串的相似度。
	 * @author YangJunLin
	 */
	public static double checkSimOfTwoString(String str1, String str2) {
		List<String> list1 = getWords(str1);
		List<String> list2 = getWords(str2);
		/*
		 * for (String str : list1) { System.out.print(str + " "); }
		 * System.out.println(); for (String str : list2) { System.out.print(str
		 * + " "); }
		 */
		return (double) 0.8 * getWordsSim(list1, list2) + 0.1
				* getLengthSim(str1, str2) + 0.1 * getNiXuSim(list1, list2);
	}

	// public static void writerText(String content, boolean bool) {
	// File dirFile = new File("D://log_similarity");
	// if (!dirFile.exists()) {
	// dirFile.mkdir();
	// }
	// try {
	// // new FileWriter(path + "t.txt", true) 这里加入true 可以不覆盖原有TXT文件内容 续写
	// BufferedWriter bw = new BufferedWriter(new FileWriter(
	// "D://log_similarity//log_similarity3.txt", true));
	// bw.write(content);
	// if (bool)
	// bw.newLine();
	// bw.flush();
	// bw.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

	// public static void writerText2(String content, boolean bool, double num)
	// {
	// File dirFile = new File("D://log_similarity");
	// if (!dirFile.exists()) {
	// dirFile.mkdir();
	// }
	// try {
	// // new FileWriter(path + "t.txt", true) 这里加入true 可以不覆盖原有TXT文件内容 续写
	// BufferedWriter bw = new BufferedWriter(new FileWriter(
	// "D://log_similarity//log_similarity" + num + ".txt", true));
	// bw.write(content);
	// if (bool)
	// bw.newLine();
	// bw.flush();
	// bw.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

	/*
	 * public static void main(String args[]) { String str1 =
	 * "基于Trustie 的神州数码软件生产线改造及应用示范"; String str2 =
	 * "示范应用基于Trustie的神州数码软件生产线改造及"; System.out.println(checkSimOfTwoStr(str1,
	 * str2)); }
	 */
	
	/***********************查重修正版代码暂时用算法**************************/
	public static boolean isAnalogous(String a, String b, double sim){
		double simDoor = checkSimOfTwoString(a, b);
		if(simDoor >= sim)
			return true;
		else 
			return false;
	}
	
	public static void main(String[] args){
		String a = "新视野大学生英语教学";
		String b = "实验室管理制度";
		List<String> aWords = getWords(a);
		List<String> bWords = getWords(b);
		double simDoor = checkSimOfTwoString(a, b);
		System.out.println("a字符串：" + a);
		System.out.println("b字符串：" + b);
		System.out.println("a字符串分词结果：" + aWords);
		System.out.println("b字符串分词结果：" + bWords);

		System.out.println("a与b的相似度为：" + simDoor);
	}
}
