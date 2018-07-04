package com.dsep.controller.datacheck;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.controller.base.JqGridBaseController;
import com.dsep.common.logger.LoggerTool;
import com.dsep.domain.dsepmeta.viewconfig.ViewConfig;
import com.dsep.service.base.AttachmentService;
import com.dsep.service.dsepmeta.dsepmetas.DMCollectService;
import com.dsep.service.dsepmeta.dsepmetas.DMViewConfigService;
import com.dsep.service.file.ExportService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.TextConfiguration;
import com.dsep.vm.JqgridVM;
import com.meta.domain.search.SearchGroup;
import com.meta.service.MetaEntityService;


@Controller
@RequestMapping("check")
public class CertificationMaterialsController extends JqGridBaseController {
	@Resource(name="loggerTool")
	private LoggerTool loggerTool;	
	@Resource(name="dmViewConfigService")
	private DMViewConfigService viewConfigService;	
	@Resource(name="collectService")
	private DMCollectService collectService;
	@Resource(name="exportService")
	private ExportService exportService;
	@Resource(name = "attachmentService")
	private AttachmentService attachmentService;
	@Resource(name="metaEntityService")
	private MetaEntityService metaEntityService;
	
	@RequestMapping("certi_materials")
	public String center_certiMaterials(Model model){
		Map<String,String> entityMap = metaEntityService.getEntitiesHaveFile();
		model.addAttribute("entityMap", entityMap);
		return "/DataCheck/certification_materials";
	}
	
	/**
	 * 初始化jqgrid的配置信息
	 * @param tableId
	 * @return 配置信息
	 */
	@RequestMapping("/certi_materials/initCertiJqgrid/{entityId}")
	@ResponseBody
	public String initMaterialsJqgrid(@PathVariable(value="entityId")String entityId)
	{
		loggerTool.warn("初始化Jqgrid!");
		//证明材料的view类型为Z
		ViewConfig viewConfig = viewConfigService.getViewConfig(entityId,"Z");
		String configData=JsonConvertor.obj2JSON(viewConfig);
		return configData;	
	}
	
	@RequestMapping("/certi_materials/certiMaterials_dataJqGrid")
	@ResponseBody
	public String certiMaterialsData(String entityId,String unitId,String discId,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session){

		setRequestParams(request);
		JqgridVM jqgridVM=collectService.getJqGridSearchData(entityId,unitId,discId,getSearchGroup(),getPageIndex(), getPageSize(),getSidx(), isAsc());
		String result=JsonConvertor.obj2JSON(jqgridVM);
		return result;
		
	}
	
	/**
	 * 导出excel表
	 * @param entityId
	 * @param unitId
	 * @param discId
	 * @param sortOrder
	 * @param sortName
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping("/certi_materials/export_excel")
	@ResponseBody
	public String certiMaterials_export(String entityId,String unitId,String discId,
			String sortOrder,String sortName,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session){
		SearchGroup searchGroup = null;
		String filters = request.getParameter("filters");
		if(StringUtils.isNotBlank(filters)){
			searchGroup = JsonConvertor.string2SearchObject(filters);
		}
		boolean order_flag = (sortOrder.equals("asc")?true:false);
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		String fileString = exportService.exportExcelByEntityId(unitId, discId, entityId,"Z", order_flag, sortName, rootPath,searchGroup);
		return fileString;
		
	}
	/**
	 * 证明材料的下载
	 * @param attachId
	 * @return
	 */
	@RequestMapping("/certi_materials/downLoadAttachCertiMate")
	@ResponseBody
	public String dowloadCertiMate(String attachId){
		return JsonConvertor.obj2JSON(attachmentService.getAttachmentPath(attachId));
	}
	/**
	 * 证明材料打包下载
	 * @param attachIds
	 * @return
	 */
	@RequestMapping("/certi_materials/downLoadMultiAttachCertiMate")
	@ResponseBody
	public String downloadMultiCertiMate(@RequestParam(value="attachIds")List<String> attachIds){
		
		return JsonConvertor.obj2JSON(attachmentService.getZipAttachmentPath(attachIds));
	}

	
}
