package com.dsep.controller.dsepmeta.backup;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.common.logger.LoggerTool;
import com.dsep.controller.base.JqGridBaseController;
import com.dsep.service.dsepmeta.dsepmetas.DMBackupService;
import com.dsep.service.dsepmeta.dsepmetas.DMCollectService;
import com.dsep.service.dsepmeta.dsepmetas.DMViewConfigService;
import com.dsep.util.JsonConvertor;
import com.dsep.vm.JqgridVM;
@Controller
@RequestMapping("databackup/disciplinebackup/BackupData")
public class BuckupDataController extends JqGridBaseController{
	
	@Resource(name="loggerTool")
	private LoggerTool loggerTool;
	@Resource(name="backupService")
	private DMBackupService backupService;	
	@Resource(name="dmViewConfigService")
	private DMViewConfigService viewConfigService;
	
	
	@RequestMapping(value="backupData/{entityId}/" +
			"{unitId}/{discId}/{versionId}")
	@ResponseBody
	public String backupData(@PathVariable(value="entityId")String entityId,
			@PathVariable(value="unitId")String unitId,
			@PathVariable(value="discId")String discId,@PathVariable(value="versionId")String versionId,HttpServletRequest request){
		//传过来列名们
			setRequestParams(request);
			JqgridVM jqgridVM=backupService.getJqGridData(entityId, unitId, discId, versionId, getSearchGroup(), getPageIndex(), getPageSize(),getSidx(), isAsc());
			String result=JsonConvertor.obj2JSON(jqgridVM);
			return result;
		
	}
}
