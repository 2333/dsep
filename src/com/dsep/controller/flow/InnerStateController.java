package com.dsep.controller.flow;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.controller.base.JqGridBaseController;
import com.dsep.service.flow.DsepInnerStateService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.String2Date;
import com.dsep.vm.flow.InnerStateVm;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.meta.entity.MetaInnerStateDetail;

@Controller
@RequestMapping("/InnerState")
public class InnerStateController extends JqGridBaseController{
	
	@Resource(name="dsepInnerStateService")
	private DsepInnerStateService dsepInnerStateService;
	
	@RequestMapping("innerStateManage")
	public String innerStateManage(){
		return "centerFlowManage/innerStateDetail";
	}
	@RequestMapping("innerStateManage/innerStateDetail")
	@ResponseBody
	public String innerStateDetial(HttpServletRequest request){
		setRequestParams(request);
		String resultString=JsonConvertor.obj2JSON(dsepInnerStateService.getCurrentInnerStates(getPageIndex(),
				getPageSize(), !isAsc(), getSidx()).getGridData());
		return resultString;
		
	}
	@SuppressWarnings("deprecation")
	@RequestMapping("innerStateManage/editInnerStateDetail")
	@ResponseBody
	public String eidtInnerStateDetail(HttpServletRequest request){
		String pkValue = request.getParameter("detail.id");
		String startTime = request.getParameter("detail.startTime");
		String endTime = request.getParameter("detail.endTime");
		String memo = request.getParameter("detail.memo");
		MetaInnerStateDetail innerStateDetail = new MetaInnerStateDetail();
		innerStateDetail.setId(pkValue);
		innerStateDetail.setStartTime(String2Date.string2Date(startTime,"yyyy-MM-dd"));
		innerStateDetail.setEndTime(String2Date.string2Date(endTime,"yyyy-MM-dd"));
		innerStateDetail.setMemo(memo);
		if(dsepInnerStateService.updateInnerStateDetail(innerStateDetail)!=null)
		{
			return "success";
		}else{
			return "error";
		}
		
	}

}
