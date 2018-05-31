package com.dsep.service.reportGenerator.impl;

import com.dsep.service.reportGenerator.ReportGeneratorService;
import com.dsep.util.XFireWithWindows;

public class ReportGeneratorServiceImpl  implements ReportGeneratorService  {

	@Override
	public String reportGenerator(String fileName) {
		return  "http://192.168.3.75:8089/"+XFireWithWindows.generatorReport(new String[]{fileName});
	}

	@Override
	public String generateWinReport(String unitId, String discId,
			String reportType, String introUrl) {
		// TODO Auto-generated method stub
		return XFireWithWindows.generatorWinReport(unitId, discId, reportType, introUrl);
	}

}
