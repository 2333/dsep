package com.dsep.controller.flow;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.service.flow.EvalFlowService;

@Controller
@RequestMapping("FlowActions")
public class CollectFlowController {

	@Resource(name="evalFlowService")
	private EvalFlowService evalFlowService;
	
	@RequestMapping("disc2Unit/{unitId}/{discId}/{isConfirm}")
	@ResponseBody
	public String disc2Unit(@PathVariable("unitId")String unitId,
			@PathVariable("discId")String discId,@PathVariable("isConfirm")String isConfirm)
	{
		String message=null;
		if(evalFlowService.disc2Unit(unitId, discId,isConfirm)){
			 message = "{\"message\":\""+"success"+"\"}";
		}else{
			 message = "{\"message\":\""+"error"+"\"}";
		}
		return message;
	}
	
	@RequestMapping("unit2Center/{unitId}")
	@ResponseBody
	public String unit2Center(@PathVariable(value="unitId")String unitId)
	{
		if(evalFlowService.unit2Center(unitId))
		{
			return "success";
		}	
		else {
			return "error";
		}		
	}
	
	@RequestMapping("unitback2Disc/{unitId}/{discId}")
	@ResponseBody
	public String unitback2Disc(@PathVariable("unitId")String unitId,@PathVariable("discId")String discId)
	{
		if(evalFlowService.unitBack2Disc(unitId, discId))
		{
			return "success";
		}
		else {
			return "error";
		}
	}
	@RequestMapping("unitRepealFromCenter/{unitId}")
	@ResponseBody
	public String unitRepealFromCenter(@PathVariable(value="unitId")String unitId)
	{
		if(evalFlowService.unitRepealFromCenter(unitId))
		{
			return "success";
		}else {
			return "error";
		}
	}
	@RequestMapping("terminateSubmit")
	@ResponseBody
    public String terminateSubmits()
    {   
		if(evalFlowService.terminateUnitSubmit())
		{
			return "success";
		}else{
			return "error";
		}
    }
	
	@RequestMapping("rebootSubmit")
	@ResponseBody
    public String rebootSubmits()
    {   
		if(evalFlowService.rebootSubmit())
		{
			return "success";
		}else {
			return "error";
		}
		
    }
	
}
