package com.dsep.controller.dataprocess;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("reportManagement")
public class ReportManagementController {

	@RequestMapping("toGenerateReport")
	public String toGenerateReport(Model model,String tableId)
	{
		return "/ReportsManage/report_generate";
	}
	
	@RequestMapping("toDownLoadReport")
	public String toDownLoadReport(Model model,String tableId)
	{
		return "/ReportsManage/report_download";
	}
}
