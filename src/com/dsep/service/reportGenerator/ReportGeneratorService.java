package com.dsep.service.reportGenerator;

public interface ReportGeneratorService {
	public abstract String  reportGenerator(String fileName);
	
	/**
	 * 生成某个学校、学科的简况表
	 * @param unitId
	 * @param discId
	 * @param repterType （简况表类型 专业、自检）
	 * @param introUrl（学科简介的路径）
	 * @return
	 */
	public abstract String generateWinReport(String unitId,String discId,
			String repterType,String introUrl);
}
