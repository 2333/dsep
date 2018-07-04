/**
 * Project Name:DSEP
 * File Name:ExportController.java
 * Package Name:com.dsep.controller.dsepmeta.teacher
 * Date:2014年7月2日下午3:40:44
 *
 */

package com.dsep.controller.dsepmeta.teacher;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.ws.security.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.entity.User;
import com.dsep.service.file.ExportService;
import com.dsep.service.rbac.UserService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.UserSession;
import com.meta.domain.search.SearchGroup;

/**
 * ClassName: ExportController <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年7月2日 下午3:40:44 <br/>
 *
 * @author QianYuxiang
 */
@Controller
@RequestMapping("TCollect/toTCollect/export")
public class TExportController {
	@Resource(name="exportService")
	private ExportService exportService;
	@Resource(name="userService") 
	private UserService userService; 
	
	@RequestMapping(value="/excel/{tableId}/{sortOrder}/{sortName}",method=RequestMethod.POST)
	@ResponseBody
	public String exportTeacherExcelByTeacherId(@PathVariable(value="tableId")String entityId,
			@PathVariable(value="sortOrder")String sortOrder,
			@PathVariable(value="sortName")String orderPropName,
			HttpServletRequest request,
			HttpServletResponse response,HttpSession session){
		String filters = request.getParameter("filters");
		SearchGroup searchGroup = null;
		if(StringUtils.isNotBlank(filters)){
			searchGroup = JsonConvertor.string2SearchObject(filters);
		}
		String fileString = null;
		//params in need 
		boolean asc = (sortOrder.equals("asc")?true:false);
		List<Object> teacherList = null;
		Map<String, String> additionalParamsMap = null;
 
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		UserSession userSession = new UserSession(session);
		User currentUser = userSession.getCurrentUser();	
		if(currentUser.getUserType().equals("4")){
			teacherList = new ArrayList<Object>();
			teacherList.add(currentUser.getId());
		}else if(currentUser.getUserType().equals("3")){
			teacherList = new ArrayList<Object>(userService.getUserIdsByUnitIdAndDiscId(currentUser.getUnitId(), currentUser.getDiscId()));
			additionalParamsMap = new LinkedHashMap<String, String>();
			additionalParamsMap.put("ACHIEVE_OF_PERSON_LOGINID", "成果所属人用户名");
			additionalParamsMap.put("ACHIEVE_OF_PERSON_NAME", "成果所属人姓名");
		}else if(currentUser.getUserType().equals("2")){
			teacherList = new ArrayList<Object>(userService.getUserIdsByUnitIdAndDiscId(currentUser.getUnitId(), null));
			additionalParamsMap = new LinkedHashMap<String, String>();
			additionalParamsMap.put("ACHIEVE_OF_PERSON_LOGINID", "成果所属人用户名");
			additionalParamsMap.put("ACHIEVE_OF_PERSON_NAME", "成果所属人姓名");
		}
		else{
		}
		fileString = exportService.exportTeacherExcelByEntityId(entityId,teacherList,asc,orderPropName, rootPath, 
				additionalParamsMap,searchGroup);
		return fileString;
	}
}

