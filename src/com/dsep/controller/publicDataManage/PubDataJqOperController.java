package com.dsep.controller.publicDataManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.common.exception.BusinessException;
import com.dsep.entity.User;
import com.dsep.service.base.AttachmentService;
import com.dsep.service.dsepmeta.dsepmetas.DMCollectService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.StringDealUtil;
import com.dsep.util.Tools;
import com.dsep.util.UserSession;
import com.dsep.vm.collect.BatchDataVM;

@Controller
@RequestMapping("PublicDataManage/viewData/JqOper")
public class PubDataJqOperController {
	
	@Resource(name="collectService")
	private DMCollectService collectService;
	
	@Resource(name = "attachmentService")
	private AttachmentService attachmentService;

	@RequestMapping("/batchSubmit")
	@ResponseBody
	public Map<String, String> collectBatchSumbit(
			HttpServletRequest request,HttpSession session)
	{
		String params=request.getParameter("params");
		//BatchData sub_byBatch=getBatchEditData(params);
		BatchDataVM sub_byBatch = new BatchDataVM(params);
		Map<String, String> map = new HashMap<>();
		UserSession userSession= new UserSession(session);
		User user= userSession.getCurrentUser();
		try{
			for(int i=0;i<sub_byBatch.getRowDatas().size();i++)
			{
				if(collectService.newRow(sub_byBatch.getEntityId(), null, null, user.getId(), sub_byBatch.getRowDatas().get(i))!=null)
				{
					map.put(i+"",(sub_byBatch.getRowDatas().get(i).get("SEQ_NO")));
				}
			}
		}catch(RuntimeException exception)
		{
			map.put("status", "error");
			return map;
		}
		map.put("status", "success");
		return map;
	}
	
	@RequestMapping("/batchDel/{entityId}/{pkValueAndSeqNos}/{userId}")
	@ResponseBody
	public String batchDel(@PathVariable(value="entityId")String entityId,
			@PathVariable(value="pkValueAndSeqNos")List<String> pkValueAndSeqNos,
			@PathVariable(value="userId")String userId,
			HttpSession session){
		int count = 0;
		for(String pkAndSeqNo:pkValueAndSeqNos){
			String pk = pkAndSeqNo.split(":")[0];
			String seqNo = StringDealUtil.operWithInteger(pkAndSeqNo.split(":")[1],count,"-");
			count++;
			collectService.deleteRow(entityId, pk, seqNo, null, null, userId);
		}
		return "success";
	}
	
	@RequestMapping(value="/publicEdit/{tableId}/{primaryKey}/{titleValues}/{seqNo}")
	@ResponseBody
	public String collectionEdit(@PathVariable(value="tableId")String entityId,
								 @PathVariable(value="primaryKey")String primaryKey,
								 @PathVariable(value="titleValues")List<String>titleValues,
								 @PathVariable(value="seqNo")String seqNo,
								 HttpServletRequest request,
								 HttpSession session)
	{
		UserSession userSession= new UserSession(session);
		User user= userSession.getCurrentUser();
		String oper=request.getParameter("oper");
		Map<String, String> rowData = Tools.requestData2Map(request, titleValues);
		if("edit".equals(oper))
		{
			//主键为0意味着该条记录没有存在数据库中
			if("0".equals(primaryKey)){
				if(collectService.newRow(entityId, null, null, user.getId(), rowData)!=null){
					return "success";
				}
				else{
					throw new BusinessException("更新失败！");
				}
			}else{
				if(collectService.updateRow(entityId, user.getId(), primaryKey,null,null, rowData)==1){
						
						return "success";
					}
					else{
						throw new BusinessException("更新失败！");
					}
			}
			
		}else if("del".equals(oper)){
			if(collectService.deleteRow(entityId, primaryKey,seqNo,null,null, user.getId())){
				return "success";
			}else{
				throw new BusinessException("数据删除失败！");
			}
				
		}
		return "success";
	}
	
	@RequestMapping(value="publicClear/{tableId}/{pkValue}/{seqNo}")
	@ResponseBody
	public String clearCollectItem(@PathVariable("tableId")String tableId,
			@PathVariable("pkValue")String pkValue,
			@PathVariable("seqNo")String seqNo,
			HttpSession session){
		 UserSession  userSession = new UserSession(session);
		 User user = userSession.getCurrentUser();
		 if(collectService.clearOneCollectItem(tableId, null, null, pkValue, user.getId(), seqNo))
		 {
			 return "success";
		 }
		 return "error";
	}
	
	@RequestMapping("/reorder")
	@ResponseBody
	public String recorder(HttpServletRequest request)
	{
		String params=request.getParameter("params");
		JSONObject jsonObject= JSONObject.fromObject(params);
		String entityId=jsonObject.getJSONArray("tableId").getString(0);
		JSONArray rows= jsonObject.getJSONArray("rows");
		List<String> pkValues= new ArrayList<>();
		List<Map<String, String>> rowDatas=new ArrayList<>();
		for(Object o:rows)
		{
			JSONObject row=(JSONObject)o;
			Map<String,String> rowData= new HashMap<String,String>();
			rowData.put("SEQ_NO", row.getString("SEQ_NO"));
			rowDatas.add(rowData);
			pkValues.add(row.getString("ID"));
		}
		if(collectService.reOrder(entityId, "userId", pkValues, rowDatas)==pkValues.size())
		{
			return "success";
		}else {
			return "error";
		}
		
	}
	
	@RequestMapping("downLoadTemplate/{templateId}")
	@ResponseBody
	public String downLoadTemplate(@PathVariable(value="templateId")String templateId){
		return JsonConvertor.obj2JSON(attachmentService.getAttachmentPath(templateId));
	}
}
