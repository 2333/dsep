package com.dsep.util.expert.eval;

public class EvalProgressPCTFormatter {
	public static String getStdPCT(Double number) {
		number *= 100;
		return String.format("%.1f", number) + "%";
	}
}
