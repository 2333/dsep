package com.dsep.controller.datacheck;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("dataModify")
public class DataModifyController {
	
	//@Resource(name="collectionFlowService")
	//public CollectionFlowService collectionFlowService; 看到这个联系柏林
	@RequestMapping("toDataModify")
	public String dataModify(Model model,String tableId)
	{
		return "/DataCheck/data_modify";
	}
	
	@RequestMapping("vsDataModify")
	public String vsDataModify(Model model)
	{   
		/*Map<String,String>disciplines = collectionFlowService.getAllDisciplines(); 
		Map<String,String>collectStatus=Dictionaries.getCollectFlowTypesMap();

		model.addAttribute("collectStatus", collectStatus);
		model.addAttribute("colleges", colleges);
		model.addAttribute("disciplines", disciplines); 看到这个联系柏林*/
		return "/DataCheck/view_data_modify";
	}
	
	@RequestMapping("loadDisciplines")
	@ResponseBody
    public String loadDisciplines(String collegeid)
    {   
		/*Map<String,String>map=collectionFlowService.getDiscipline(collegeid);
		String disciplines=JsonConvertor.obj2JSON(map);
    	return disciplines; 看到这个联系柏林*/
		return null;
    }
	
	@RequestMapping("loadCollages")
	@ResponseBody
    public String loadCollages(String disciplineId)
    {   
		/*Map<String,String>map=collectionFlowService.getcolleges();	
		String colleges=JsonConvertor.obj2JSON(map);
    	return colleges; 看到这个联系柏林*/
		return null;
    }
	
}
