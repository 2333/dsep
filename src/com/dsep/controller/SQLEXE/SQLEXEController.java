package com.dsep.controller.SQLEXE;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.service.admin.SqlHelperService;
import com.dsep.util.JsonConvertor;
import com.dsep.vm.resultSet.ResultSetVM;

@Controller
@RequestMapping("/SQLEXE/")
public class SQLEXEController {
	
	@Resource(name="sqlHelperService")
	private SqlHelperService sqlHelperService;
	@RequestMapping("/view")
	public String viewSqlPage(){
		return "/SQLHelper/SQLHelper";
	}
	@RequestMapping("/view/querySet")
	@ResponseBody
	public String querySet(HttpServletRequest request){
		String sql = request.getParameter("sqlStr");
		ResultSetVM resultSetVM = sqlHelperService.getResultSetVM(sql);
		String result = JsonConvertor.obj2JSON(resultSetVM);
		return result;
	}
	@RequestMapping("/view/updateOrSave")
	@ResponseBody
	public String updateOrSave(HttpServletRequest request){
		String sql = request.getParameter("sqlStr");
		int count = sqlHelperService.executeUpdateOrSave(sql);
		return count+"";
	}
	
}
