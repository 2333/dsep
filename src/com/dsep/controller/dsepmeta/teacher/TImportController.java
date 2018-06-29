package com.dsep.controller.dsepmeta.teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.JXLException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dsep.entity.User;
import com.dsep.service.file.ImportService;
import com.dsep.util.UserSession;
@Controller
@RequestMapping("TCollect/toTCollect/import")
public class TImportController {
	
	@Resource(name="importService")
	private ImportService importService;
	
	@RequestMapping(value = "/excel",method=RequestMethod.POST)
	@ResponseBody
	public String importExcel(@RequestParam("tableId")String entityId,
							@RequestParam("file") MultipartFile importFile,
							HttpServletRequest request,
							HttpServletResponse response,
							HttpSession session)
	{
		try{
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			Map<String, Map<Object, Object>> viewConfigSetting = new LinkedHashMap<String, Map<Object, Object>>();
			Map<String, String> additionalParamsMap = null;
			UserSession userSession = new UserSession(session);
			User currentUser = userSession.getCurrentUser();	
			if(currentUser.getUserType().equals("4")){
				Map<Object, Object> colConfig = new HashMap<Object, Object>();
				colConfig.put("editable", false);
				colConfig.put("hidden", true);
				viewConfigSetting.put("ACHIEVE_OF_PERSON_ID", colConfig);
				viewConfigSetting.put("ACHIEVE_OF_PERSON_LOGINID", colConfig);
				viewConfigSetting.put("ACHIEVE_OF_PERSON_NAME", colConfig);
			}else if(currentUser.getUserType().equals("3") || currentUser.getUserType().equals("2")){
				Map<Object, Object> colConfig = new HashMap<Object, Object>();
				colConfig.put("editable", true);
				colConfig.put("hidden", false);
				viewConfigSetting.put("ACHIEVE_OF_PERSON_LOGINID", colConfig);
				viewConfigSetting.put("ACHIEVE_OF_PERSON_NAME", colConfig);	
				additionalParamsMap = new LinkedHashMap<String, String>();
				additionalParamsMap.put("ACHIEVE_OF_PERSON_LOGINID", "成果所属人用户名");
				additionalParamsMap.put("ACHIEVE_OF_PERSON_NAME", "成果所属人姓名");
			}else{
			}		
			return importService.importExcelByEntityId(entityId,"C", importFile, rootPath, viewConfigSetting, additionalParamsMap);}
		catch (Exception e) {
			if(e.getMessage()!= null && (!e.getMessage().equals(""))){
				return e.getMessage();
			}
			else{
				return "对不起，由于未知原因您的导入未能成功。";
			}
		}
	}
	
	@RequestMapping(value = "/excelTemplate",method=RequestMethod.POST)
	@ResponseBody
	public String createExcelTemplate(@RequestParam("tableId")String entityId,
							HttpServletRequest request,
							HttpServletResponse response,
							HttpSession session) throws JXLException, Exception
	{
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		
		Map<String, String> additionalParamsMap = null;
		UserSession userSession = new UserSession(session);
		User currentUser = userSession.getCurrentUser();	
		if(currentUser.getUserType().equals("4")){

		}else if(currentUser.getUserType().equals("3")){
			additionalParamsMap = new LinkedHashMap<String, String>();
			additionalParamsMap.put("ACHIEVE_OF_PERSON_LOGINID", "成果所属人用户名");
			additionalParamsMap.put("ACHIEVE_OF_PERSON_NAME", "成果所属人姓名");
		}else if(currentUser.getUserType().equals("2")){
			additionalParamsMap = new LinkedHashMap<String, String>();
			additionalParamsMap.put("ACHIEVE_OF_PERSON_LOGINID", "成果所属人用户名");
			additionalParamsMap.put("ACHIEVE_OF_PERSON_NAME", "成果所属人姓名");
		}
		else{
		}		
 		String excelName = importService.createExcelTmpByEntityId(entityId,"C", rootPath, additionalParamsMap);
		return excelName;
	}

}

