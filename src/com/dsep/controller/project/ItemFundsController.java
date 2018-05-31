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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.entity.project.ItemFunds;
import com.dsep.entity.project.ItemProvideFunds;
import com.dsep.service.project.ItemFundsService;
import com.dsep.util.JsonConvertor;
import com.dsep.vm.NewsVM;
import com.dsep.vm.PageVM;

@Controller
@RequestMapping("project")
public class ItemFundsController {
	@Resource(name="itemFundsService")
	private ItemFundsService itemFundsService;
	
	@RequestMapping("pproject_addUsingFunds")
	public String addUsingFunds(String item_id,Model m){
		m.addAttribute("item_id", item_id);
		return "ProjectManagement/Add_UsingFunds";
	}
	@ResponseBody
	@RequestMapping("pproject_itemFundsList")
	public String initFundsUsingGrid(String itemId,
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
		PageVM<ItemFunds> itemFunds = null;
		try {
			itemFunds =itemFundsService.page(item_id,pageIndex, pageSize, order_flag, sidx);
			Map<String, Object> map = itemFunds.getGridData();
			String json = JsonConvertor.obj2JSON(map);
			return json;
		} catch ( Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	


	/*
	 * 经费记录查询
	 */
	@RequestMapping("pproject_serachFunds")
	@ResponseBody
	public String useFundsSearch(HttpServletRequest request,
			HttpServletResponse response, String startDate, String endDate,
			String invoiceNumber,String item_id){
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		PageVM<ItemFunds> itemFunds = null;
		itemFunds = itemFundsService.getSearch(startDate,endDate,invoiceNumber,item_id,page,pageSize);
		Map<String, Object> m = itemFunds.getGridData();
		String json = JsonConvertor.obj2JSON(m);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("pproject_saveUsingFunds")
	public Boolean usingFundsSave(ItemFunds itemFunds,
			HttpServletRequest request) throws ParseException{
		String use_time = request.getParameter("using_Time");
		String consumption = request.getParameter("amount");
		String item_Id=request.getParameter("itemId");
		itemFunds.setPassItem(itemFundsService.getPassItem(item_Id));
		if (!StringUtils.isBlank(use_time)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date endDate = sdf.parse(use_time);
			itemFunds.setUsingTime(endDate);
		}
		if (!StringUtils.isBlank(consumption)){
			Double d = Double.parseDouble(consumption);
			itemFunds.setConsumption(d);
		}
		try{
			itemFundsService.create(itemFunds);
		}catch(Exception e)
		{
			return false;
		}
		return true;
	}
	
	@RequestMapping("pproject_fundsDetail")
	
	public String fundsDetail(String fundsId,Model model){
		ItemFunds itemFunds =itemFundsService.getItemFunds(fundsId);
		model.addAttribute("itemFunds",itemFunds );
		return "ProjectManagement/detail_funds";
	}
	
	@ResponseBody
	@RequestMapping("pproject_deleteFunds")
	public boolean deleteFunds(String fundsId){
		try{
			itemFundsService.delete(fundsId);
		}catch(Exception e)
		{
			return false;
		}
		return true;
	}
}
