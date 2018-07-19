package com.dsep.util.expert.eval;

import java.util.ArrayList;
import java.util.List;

import com.dsep.entity.expert.EvalResult;

public class QuickSort {
	public static void sort(List<EvalResult> list, int s, int t) {
		int i = s, j = t;
		EvalResult tmp = null;
		if (s < t) {
			tmp = list.get(s);
			while (i != j) {
				while (j > i
						&& toInt(list.get(j).getEvalValue()) >= toInt(tmp
								.getEvalValue())) {
					j--;
				}
				list.set(i, list.get(j));
				while (i < j
						&& toInt(list.get(i).getEvalValue()) < toInt(tmp
								.getEvalValue())) {
					i++;
				}
				list.set(j, list.get(i));
			}
			list.set(i, tmp);
			sort(list, s, i - 1);
			sort(list, i + 1, t);
		}
	}

	private static int toInt(String str) {
		return Integer.valueOf(str);
	}
	
	public static void main(String[] args) {
		List<EvalResult> list = new ArrayList<EvalResult>();
		EvalResult r1 = new EvalResult();
		r1.setEvalValue("10");
		list.add(r1);
		EvalResult r2 = new EvalResult();
		r2.setEvalValue("12");
		list.add(r2);
		EvalResult r3 = new EvalResult();
		r3.setEvalValue("7");
		list.add(r3);
		EvalResult r4 = new EvalResult();
		r4.setEvalValue("8");
		list.add(r4);
		EvalResult r5 = new EvalResult();
		r5.setEvalValue("5");
		list.add(r5);
		EvalResult r6 = new EvalResult();
		r6.setEvalValue("6");
		list.add(r6);
		EvalResult r7 = new EvalResult();
		r7.setEvalValue("8");
		list.add(r7);
		EvalResult r8 = new EvalResult();
		r8.setEvalValue("8");
		list.add(r8);
		EvalResult r9 = new EvalResult();
		r9.setEvalValue("8");
		list.add(r9);
		EvalResult r10 = new EvalResult();
		r10.setEvalValue("1");
		list.add(r10);
		EvalResult r11 = new EvalResult();
		r11.setEvalValue("3");
		list.add(r11);
		EvalResult r12 = new EvalResult();
		r12.setEvalValue("3");
		list.add(r12);
		sort(list,0, list.size() - 1);
		for (EvalResult r : list) {
			System.out.println(r.getEvalValue());
		}
	}
}
