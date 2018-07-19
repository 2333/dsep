package com.dsep.controller.databackup;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.BackupManagement;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;
import com.dsep.service.databackup.DataBackupService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.TextConfiguration;
import com.dsep.util.UserSession;
import com.dsep.vm.BackupManageVM;
import com.dsep.vm.PageVM;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;


@Controller
@RequestMapping("databackup")
public class DisciplineBackupController {
	
	@Resource(name="unitService")
	private UnitService unitService;
	
	@Resource(name="disciplineService")
	private DisciplineService disciplineService;
	
	@Resource(name="dataBackupService")
	private DataBackupService dataBackupService;
	
	private User getUser(HttpSession session){
		UserSession newSession = new UserSession(session);
		return newSession.getCurrentUser();
	}
	
	@RequestMapping("disciplinebackup")
	public String getIntoDisciplineBackup(Model model,HttpSession session){
		UserSession newSession = new UserSession(session);
		User theUser = newSession.getCurrentUser();
		String unitId = theUser.getUnitId();
		String unitName = unitService.getUnitNameById(unitId);
		String discId = theUser.getDiscId();
		String discName = disciplineService.getDisciplineNameById(discId);
		model.addAttribute("unitId", unitId);
		model.addAttribute("discId", discId);
		model.addAttribute("unitName", unitName);
		model.addAttribute("discName",discName);
		return "CommonTools/backup";
	}
	
	@RequestMapping("disciplinebackup_getallversion")
	@ResponseBody
	public String getAllVersion(HttpServletRequest request,
			HttpServletResponse response,HttpSession session) throws IllegalArgumentException, IllegalAccessException{
		User user = getUser(session);
		String unitId = user.getUnitId();
		String discId = user.getDiscId();
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		
		String sord = request.getParameter("sord");
		String orderName = request.getParameter("sidx");
		
		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}
		
		PageVM<BackupManageVM> theVm = dataBackupService.getAllBackupVersion(page, pageSize,order_flag,orderName, unitId, discId);
		System.out.println("*************************************");
		System.out.println("*************************************");
		System.out.println("***************success***************");
		System.out.println("*************************************");
		System.out.println("*************************************");
		return JsonConvertor.obj2JSON(theVm.getGridData());
	}
	
	@RequestMapping("disciplinebackup_saveremark")
	@ResponseBody
	public boolean saveRemark(HttpServletRequest request,
			HttpServletResponse response,HttpSession session,BackupManageVM backup) throws NoSuchFieldException, SecurityException{
		String id = backup.getBackup().getId();
		String remark = backup.getRemark();
	    if( dataBackupService.updateBackupVersion(id,remark) == 1)
	    	return true;
	    else
	    	return false;
	}
	
	@RequestMapping("disciplinebackup_restore")
	@ResponseBody
	public boolean restoreBackup(HttpServletRequest request,
			HttpServletResponse response,HttpSession session) throws NoSuchFieldException, SecurityException{
		String id = request.getParameter("id");
		String unitId = request.getParameter("unitId");
		String discId = request.getParameter("discId");
		String versionId = request.getParameter("versionId");
		if( dataBackupService.restoreDiscipline(id,unitId,discId,versionId) )
			return true;
		else 
			return false;
	}
	
	@RequestMapping("disciplinebackup_add")
	@ResponseBody
	public boolean addBackup(HttpServletRequest request,
			HttpServletResponse response,HttpSession session) throws Exception{
		User user = getUser(session);
		String unitId = user.getUnitId();
		String discId = user.getDiscId();
		return dataBackupService.backupDiscipline(unitId, discId);
	}
					 
	@RequestMapping("disciplinebackup_deletebackup")
	@ResponseBody
	public boolean deletebackup(HttpServletRequest request,
			HttpServletResponse response,HttpSession session) throws Exception{
		String versionId = request.getParameter("versionId");
		String discId = request.getParameter("discId");
		return dataBackupService.deleteDiscipline(versionId, discId);
	}
}
