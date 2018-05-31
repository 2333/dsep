package com.dsep.controller.rbac;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.dsep.entity.Right;
import com.dsep.service.rbac.RightService;
import com.dsep.util.Dictionaries;
import com.dsep.util.JsonConvertor;

@Controller
@RequestMapping("rbac")
public class RightController {
	
	static Logger logger = Logger.getLogger(RightController.class.getName());
	@Resource(name="rightService")
	private RightService rightService;
	
	@RequestMapping("right")
	public String right()
	{
		return "rbac/right";
	}
	
	/**
	 * @param response返回的输出流
	 */
	@RequestMapping("rightlist")
	public void rightList(HttpServletRequest request,HttpServletResponse response)
	{
		String sord=request.getParameter("sord");//排序顺序
		String sidx=request.getParameter("sidx");//排序字段
		int page= Integer.parseInt(request.getParameter("page")); //当前页
		int pageSize=Integer.parseInt(request.getParameter("rows")); //每页多少数据
		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}
		Map<String,Object>m =  rightService.getRights(page, pageSize, order_flag, sidx).getGridData();
		String listRight=JsonConvertor.obj2JSON(m);
		response.setCharacterEncoding("UTF-8");
        try {
			response.getWriter().print(listRight);
		} 
        catch (IOException e) {
			e.printStackTrace();
		}   

	}
	
	@RequestMapping("rightadd")
	public String rightadd(Model model) {
		Map<String,String> categories = Dictionaries.getCategoryType();
	    model.addAttribute("categories", categories);
		return "rbac/addRightInfo";
	}
	
	@RequestMapping("rightsaveadd")
	@ResponseBody
	public boolean rightsaveadd(Right right){
		try{
			rightService.newRight(right);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	@RequestMapping("rightedit")
	public String rightedit(String rightId, Model model) {
		Right right=rightService.getRight(rightId);
		model.addAttribute("right", right);
        String rightTypeName = Dictionaries.getRightTypeName(right.getCategory());
        model.addAttribute("rightTypeName",rightTypeName );
		Map<String,String> categories = Dictionaries.getCategoryType();
	    model.addAttribute("categories", categories);
		return "rbac/editRightInfo";
	}
	
	@RequestMapping("rightsaveedit")
	@ResponseBody
	public boolean rightsaveedit(Right right) {
		try{
			rightService.updateRight(right);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	@RequestMapping("rightdelete")
	@ResponseBody 
	public boolean rightdelete(String rightId)
	{
		try{
			rightService.deleteRight(rightId);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	//检查right_id唯一性
	@RequestMapping("rightidcheck")
	@ResponseBody 
	public boolean rightidcheck(String id){
		return !rightService.checkRightId(id);
	}
	
	
}
