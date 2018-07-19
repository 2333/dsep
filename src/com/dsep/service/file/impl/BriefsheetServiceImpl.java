package com.dsep.service.file.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.dsep.service.dsepmeta.dsepmetas.DMCommonService;
import com.dsep.service.dsepmeta.dsepmetas.DMCollectService;
import com.dsep.service.file.BriefsheetService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.briefsheet.AbstractPDF;
import com.dsep.util.briefsheet.HTMLGenerator;
import com.dsep.util.briefsheet.PathProvider;
import com.dsep.util.briefsheet.XMLParser;

public class BriefsheetServiceImpl implements BriefsheetService {
	private DMCollectService collectService;
	private DMCommonService dmCommonService;
	
	@Override
	public String generateBriefSheet(AbstractPDF briefSheet) throws Exception {
		//step1:get entityIds from .xml config file
		Map<String, String> entries = 
				XMLParser.getEntries(PathProvider.getTemplateRootPath(), 
						"test.xml", briefSheet.getTemplateName(), "EntityId");
		//step2:get dataModel according to the entityIds
		Map<String,Object> dataModel = new HashMap<String,Object>();
		if(entries != null){
			for(Entry<String, String> titleEntry : entries.entrySet()){
				List<Map<String,String>> gridData = 
						dmCommonService.getData(titleEntry.getValue(), 
								briefSheet.getParams(), null, true, 0, 0);
				dataModel.put(titleEntry.getKey(), gridData);
			}
		}
		//step3:fill freemarker template with dataModel and get htmlString
		String htmlString = 
				HTMLGenerator.generateHTMLString(briefSheet.getTemplateName()+".ftl", 
						dataModel);
		//step4:generate briefsheet PDF with htmlString
		Map<String, String> fileResult = briefSheet.generate(htmlString);
		return JsonConvertor.obj2JSON(fileResult);
	}
	
	public DMCollectService getCollectService() {
		return collectService;
	}
	public void setCollectService(DMCollectService collectService) {
		this.collectService = collectService;
	}
	public DMCommonService getDmCommonService() {
		return dmCommonService;
	}
	public void setDmCommonService(DMCommonService dmCommonService) {
		this.dmCommonService = dmCommonService;
	}
}
