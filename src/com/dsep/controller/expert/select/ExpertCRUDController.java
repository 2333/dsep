package com.dsep.controller.expert.select;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.domain.dsepmeta.expert.ExpertQueryConditions;
import com.dsep.entity.expert.Expert;
import com.dsep.service.expert.select.ExpertCRUDService;
import com.dsep.util.JsonConvertor;
import com.dsep.vm.PageVM;
import com.dsep.vm.expert.ExpertSelectedVM;

/**
 * 
 * 方法上的路由selectExpert是前缀 该Controller是有关专家的增删改查,里面包含 批量添加selectExpertBatchAdd
 * 批量删除selectExpertBatchDelete 批量替换selectExpertBatchReplace
 */

@Controller
@RequestMapping("expert")
public class ExpertCRUDController {
	@Resource(name = "expertCRUDService")
	private ExpertCRUDService expertCRUDService;

	@RequestMapping("selectExpertBatchAdd/{currentBatchId}")
	@ResponseBody
	public boolean batchAdd(
			@PathVariable(value = "currentBatchId") String currentBatchId,
			@RequestBody List<Expert> experts) {
		System.out.println(experts.size());
		expertCRUDService.addExperts(experts, currentBatchId);
		return true;
	}

	// 删除专家
	@RequestMapping("selectExpertBatchDelete")
	public String batchDelete(
			@RequestParam(value = "selectedIds", required = false) List<String> selectedIds) {
		this.expertCRUDService.deleteExperts(selectedIds);
		return "success";
	}

	// 替换专家，在Service层面setBatch
	@RequestMapping("selectExpertBatchReplace")
	public String replaceExpert(String oldExpertId, String newExpertNumber,
			String isSecond, String batchId) {
		try {
			Boolean second = false;
			if ("true".equals(isSecond))
				second = true;
			this.expertCRUDService.replaceExpert(oldExpertId, newExpertNumber,
					second);
			return "success";
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return "false";
		}

	}

	@RequestMapping("selectExpertQueryExperts")
	public void queryExperts(String name, HttpServletRequest request,
			HttpServletResponse response) {
		// 前台转码两遍
		try {
			name = URLDecoder.decode(name, "UTF-8");
			name = URLDecoder.decode(name, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		System.out.println(name);
		System.out.println("============================");

		String queryExpertNumber = request.getParameter("queryExpertNumber");
		String queryIs985 = request.getParameter("queryIs985");
		String queryIs211 = request.getParameter("queryIs211");
		String currentStatusVar = request.getParameter("currentStatus");
		String currentBatchId = request.getParameter("currentBatchId");
		Integer currentStatus = -1;
		if (null != currentStatusVar) {
			currentStatus = Integer.valueOf(currentStatusVar);
		}

		System.out.println("queryExpertNumber" + queryExpertNumber
				+ " queryIs985:" + queryIs985 + " queryIs211" + queryIs211
				+ "currentStatus:" + currentStatus);
		// 把前台传递的值封装成查询类

		ExpertQueryConditions conditions = new ExpertQueryConditions();

		// 前台选择"全部"也传递-1
		if (-1 != currentStatus) {
			conditions.setInnerCurrentCondition(currentStatus);
		}
		conditions.setInnerExpertName(name);
		conditions.setInnerExpertNumber(queryExpertNumber);

		if ((null != queryIs985) && (!"undefined".equals(queryIs985))) {
			conditions.setOuterExpertIs985(queryIs985);
		}
		if ((null != queryIs211) && (!"undefined".equals(queryIs211))) {
			conditions.setOuterExpertIs211(queryIs211);
		}

		conditions.setCurrentBatchId(currentBatchId);
		PageVM<ExpertSelectedVM> data = null;
		try {
			data = expertCRUDService.queryExperts(conditions);
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		String listExperts = JsonConvertor.obj2JSON(data.getGridData());

		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().print(listExperts);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("selectExpertGetExpertByName")
	public void getTeachersByName(String name, HttpServletRequest request,
			HttpServletResponse response) {
		// 前台转码两遍
		try {
			name = URLDecoder.decode(name, "UTF-8");
			name = URLDecoder.decode(name, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		// 把前台传递的值封装成查询类
		// ExpertQueryConditions conditions = new ExpertQueryConditions();
		// conditions.setOuterExpertName(name);
		PageVM<ExpertSelectedVM> data = null;
		try {
			data = expertCRUDService.queryOuterExpertsByName(name);
		} catch (InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
		String listExperts = JsonConvertor.obj2JSON(data.getGridData());
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().print(listExperts);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("selectExpertGetExpertByNumber")
	public void getTeachersByNumber(String number, HttpServletRequest request,
			HttpServletResponse response) {
		// 前台转码两遍
		try {
			number = URLDecoder.decode(number, "UTF-8");
			number = URLDecoder.decode(number, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		// 把前台传递的值封装成查询类
		// ExpertQueryConditions conditions = new ExpertQueryConditions();
		// conditions.setOuterExpertName(name);
		PageVM<ExpertSelectedVM> data = null;
		try {
			data = expertCRUDService.queryOuterExpertsByNumber(number);
		} catch (InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
		String listExperts = JsonConvertor.obj2JSON(data.getGridData());
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().print(listExperts);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("selectExpertGetExpertByDiscIDAndUnitId")
	public void getTeachersId(String discId, String unitId,
			HttpServletRequest request, HttpServletResponse response) {
		// 把前台传递的值封装成查询类
		/*
		 * ExpertQueryConditions conditions = new ExpertQueryConditions();
		 * conditions.setInnerExpertDisc1(disciplineID);
		 * conditions.setOuterExpertUnit(unitID);
		 */
		PageVM<ExpertSelectedVM> data = null;
		try {
			data = expertCRUDService.queryOuterExpertsByDiscIdAndUnitId(discId,
					unitId);
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}

		String listExperts = JsonConvertor.obj2JSON(data.getGridData());
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().print(listExperts);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除已选专家
	 * 
	 * @param selectedIds
	 * @return
	 */
	@RequestMapping("selectExpertDelExpert")
	public String delExpert(
			@RequestParam(value = "selectedIds", required = false) List<String> selectedIds) {
		this.expertCRUDService.deleteExperts(selectedIds);
		return "success";
	}
	
	
	/**
	 * 删除已选专家
	 * 
	 * @param selectedIds
	 * @return
	 */
	@RequestMapping("selectExpertModifyExpertEmail/{selectedId}/{newEmail}")
	@ResponseBody
	public Boolean alertExpertEmail(
			@PathVariable(value = "selectedId") String selectedId,
			@PathVariable(value = "newEmail") String newEmail) {
		//"S___d_B__l_"是前台替换"."的特殊字符串
		newEmail = newEmail.replaceAll("S___d_B__l_", ".");
		System.out.println(selectedId);
		System.out.println(newEmail);
		this.expertCRUDService.modifyExpertEmail(selectedId, newEmail);
		return true;
	}
}
