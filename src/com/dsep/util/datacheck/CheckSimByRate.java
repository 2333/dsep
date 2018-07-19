package com.dsep.util.datacheck;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CheckSimByRate {

	private static int min(int one, int two, int three) {
		int min = one;
		if (two < min) {
			min = two;
		}
		if (three < min) {
			min = three;
		}
		return min;
	}

	public static int ld(String str1, String str2) {
		int d[][];
		int n = str1.length();
		int m = str2.length();
		int i;
		int j;
		char ch1;
		char ch2;
		int temp;
		if (n == 0) {
			return m;
		}
		if (m == 0) {
			return n;
		}
		d = new int[n + 1][m + 1];
		for (i = 0; i <= n; i++) {
			d[i][0] = i;
		}
		for (j = 0; j <= m; j++) {
			d[0][j] = j;
		}
		for (i = 1; i <= n; i++) {
			ch1 = str1.charAt(i - 1);

			for (j = 1; j <= m; j++) {
				ch2 = str2.charAt(j - 1);
				if (ch1 == ch2) {
					temp = 0;
				} else {
					temp = 1;
				}
				d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1]
						+ temp);
			}
		}
		return d[n][m];
	}

	public static double sim(String str1, String str2) {
		int ld = ld(str1, str2);
		return 1 - (double) ld / Math.max(str1.length(), str2.length());
	}

	public static List<String[]> checkSimByRate(List<String[]> list,
			double simNum) {
		List<String[]> result = new LinkedList<String[]>();
		String resId = "";
		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size(); j++) {
				double res = sim(list.get(i)[1].toString(),
						list.get(j)[1].toString());
				if (res >= simNum)
					resId += list.get(j)[0].toString() + ",";
			}
			if (!resId.equals("")) {
				resId += list.get(i)[0].toString() + ",";
				result.add(resId.split(","));
				resId = "";
			}
		}
		return result;
	}

	public static List<String[]> checkSimByRate2(List<Object[]> list,
			double simNum) {
		System.out.println("匹配长度：" + list.size());
		long times = 0;
		List<String[]> result = new ArrayList<String[]>();
		String resId = "";
		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size(); j++) {
				double res = sim(list.get(i)[1].toString(),
						list.get(j)[1].toString());
				if (res >= simNum)
					resId += list.get(j)[0].toString() + ",";
				times = times + 1;
			}
			if (!resId.equals("")) {
				resId += list.get(i)[0].toString() + ",";
				result.add(resId.split(","));
				resId = "";
			}
		}
		return result;
	}

	public static List<String[]> checkSimByRateForColumns(List<String[]> list,
			double simNum) {
		List<String[]> result = new LinkedList<String[]>();
		int columnLength = 0;
		if (list.size() != 0)
			columnLength = list.get(0).length;
		String resId = "";
		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size(); j++) {
				for (int q = 1; q < columnLength; q++) {
					double res = sim(list.get(i)[1].toString(),
							list.get(j)[1].toString());
					if (res >= simNum) {
						resId += list.get(j)[0].toString() + ",";
						break;
					}
				}
			}
			if (!resId.equals("")) {
				resId += list.get(i)[0].toString() + ",";
				result.add(resId.split(","));
				resId = "";
			}
		}
		return result;
	}
}
