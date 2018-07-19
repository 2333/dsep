package com.dsep.controller.publicDataManage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.service.file.ExportService;
import com.dsep.util.JsonConvertor;
import com.meta.domain.search.SearchGroup;

@Controller
@RequestMapping("PublicDataManage/viewData/export")
public class PublicExportController {
	
	@Resource(name="exportService")
	private ExportService exportService;
	
	@RequestMapping(value = "/excel/{tableId}/{sortOrder}/{sortName}",method=RequestMethod.POST)
	@ResponseBody
	public String exportExcel(
							  @PathVariable(value="tableId")String entityId,
							  @PathVariable(value="sortOrder")String sortOrder,
							  @PathVariable(value="sortName")String orderPropName,
							  HttpServletRequest request,
							  HttpServletResponse response)
	{
		SearchGroup searchGroup = null;
		String filters = request.getParameter("filters");
		if(StringUtils.isNotBlank(filters)){
			searchGroup = JsonConvertor.string2SearchObject(filters);
		}
		boolean asc = (sortOrder.equals("asc")?true:false);
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		String fileString = exportService.exportExcelByEntityId(null, null, entityId,"G", asc, orderPropName, rootPath,searchGroup);
		return fileString;
	}
}
