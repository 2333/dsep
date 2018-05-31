package com.dsep.controller.project;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.entity.project.ItemFunds;
import com.dsep.entity.project.ItemProvideFunds;
import com.dsep.service.project.ItemProvideFundsService;
import com.dsep.util.JsonConvertor;
import com.dsep.vm.PageVM;

@Controller
@RequestMapping("project")
public class ItemProvideFundsController {
	
	@Resource(name="itemProvideFundsService")
	private ItemProvideFundsService itemProvideFundsService;
	
	@RequestMapping("pproject_addProvideFunds")
	public String addProvideFunds(String item_id,Model m){
		m.addAttribute("item_id", item_id);
		return "ProjectManagement/Add_ProvideFunds";
	}
	@ResponseBody
	@RequestMapping("pproject_itemProvideFundsList")
	public String initFundsProvideGrid(String itemId,
			HttpServletRequest request,HttpServletResponse response){
		String sord = request.getParameter("sord");// 排序顺序
		String sidx = request.getParameter("sidx");
		//String searchString = request.getParameter("searchString");// 查询条件值
		//String filter = request.getParameter("filters");
		int pageIndex = Integer.parseInt(request.getParameter("page")); // 当前页
		int pageSize  = Integer.parseInt(request.getParameter("rows")); // 每页多少数据
		String item_id=itemId;
		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}
		PageVM<ItemProvideFunds> itemProvideFunds = null;
		try {
			itemProvideFunds =itemProvideFundsService.page(item_id,pageIndex, pageSize, order_flag, sidx);
			Map<String, Object> map = itemProvideFunds.getGridData();
			String json = JsonConvertor.obj2JSON(map);
			return json;
		} catch ( Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@ResponseBody
	@RequestMapping("pproject_providedFundsSave")
	public Boolean provideFunds(ItemProvideFunds itemProvideFunds,
			HttpServletRequest request) throws ParseException{
		String provideTime = request.getParameter("provide_Time");
		String provideAmount = request.getParameter("provideAmount");
		String balance = request.getParameter("balance");
		String item_Id=request.getParameter("itemId");
		itemProvideFunds.setPassItem(itemProvideFundsService.getPassItem(item_Id));
		if (!StringUtils.isBlank(provideTime)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(provideTime);
			itemProvideFunds.setProvideTime(date);
		}
		if (!StringUtils.isBlank(provideAmount)){
			Double d = Double.parseDouble(provideAmount);
			itemProvideFunds.setProvideAmount(d);
		}
		if (!StringUtils.isBlank(balance)){
			Double d = Double.parseDouble(balance);
			itemProvideFunds.setProvideAmount(d);
		}
		try{
			itemProvideFundsService.create(itemProvideFunds);
		}catch(Exception e)
		{
			return false;
		}
		return true;
	}
	
	/*
	 * 经费记录查询
	 */
	@RequestMapping("pproject_serachProvideFunds")
	@ResponseBody
	public String fundsSearch(HttpServletRequest request,
			HttpServletResponse response, String startDate, String endDate,String item_id){
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		PageVM<ItemProvideFunds> itemProvideFunds = null;
		itemProvideFunds = itemProvideFundsService.getSearch(startDate,endDate,item_id,page,pageSize);
		Map<String, Object> m = itemProvideFunds.getGridData();
		String json = JsonConvertor.obj2JSON(m);
		return json;
	}

}
