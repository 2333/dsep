package com.dsep.controller.dsepmeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dsep.domain.AttachmentHelper;
import com.dsep.entity.User;
import com.dsep.entity.enumeration.AttachmentType;
import com.dsep.common.exception.*;
import com.dsep.common.logger.LoggerTool;
import com.dsep.service.base.AttachmentService;
import com.dsep.service.dsepmeta.dsepmetas.DMCollectService;
import com.dsep.util.FileOperate;
import com.dsep.util.JsonConvertor;
import com.dsep.util.MySessionContext;
import com.dsep.util.StringDealUtil;
import com.dsep.util.Tools;
import com.dsep.util.UserSession;
import com.dsep.vm.collect.BatchDataVM;
@Controller
@RequestMapping("Collect/toCollect/JqOper")
public class JqOperController {

	@Resource(name="loggerTool")
	private LoggerTool loggerTool;
	@Resource(name="collectService")
	private DMCollectService collectService;
	
	@Resource(name = "attachmentService")
	private AttachmentService attachmentService;
	/**
	 * 批量添加
	 * @param request
	 * @return
	 */
	@RequestMapping("/batchSubmit/{unitId}/{discId}")
	@ResponseBody
	public Map<String, String> collectBatchSumbit(@PathVariable(value="unitId")String unitId,@PathVariable(value="discId")String discId,
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
				if(collectService.newRow(sub_byBatch.getEntityId(), unitId, discId, user.getId(), sub_byBatch.getRowDatas().get(i))!=null)
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
	@RequestMapping("/batchDel/{entityId}/{pkValueAndSeqNos}/{unitId}/{discId}/{userId}")
	@ResponseBody
	public String batchDel(@PathVariable(value="entityId")String entityId,
			@PathVariable(value="pkValueAndSeqNos")List<String> pkValueAndSeqNos,
			@PathVariable(value="unitId")String unitId,
			@PathVariable(value="discId")String discId,
			@PathVariable(value="userId")String userId,
			HttpSession session){
		int count = 0;
		for(String pkAndSeqNo:pkValueAndSeqNos){
			String pk = pkAndSeqNo.split(":")[0];
			String seqNo = StringDealUtil.operWithInteger(pkAndSeqNo.split(":")[1],count,"-");
			count++;
			collectService.deleteRow(entityId, pk, seqNo, unitId, discId, userId);
		}
		return "success";
	}
	/**
	 * jqGrid编辑、添加、删除
	 * @param tableId
	 * @param titleValues
	 * @param collegeId
	 * @param disciplineId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/collectionEdit/{tableId}/{primaryKey}/{titleValues}/{unitId}/{discId}/{seqNo}")
	@ResponseBody
	public String collectionEdit(@PathVariable(value="tableId")String entityId,
								 @PathVariable(value="primaryKey")String primaryKey,
								 @PathVariable(value="titleValues")List<String>titleValues,
								 @PathVariable(value="unitId")String unitId,
								 @PathVariable(value="discId")String discId,
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
				if(collectService.newRow(entityId, unitId, discId, user.getId(), rowData)!=null){
					return "success";
				}
				else{
					throw new BusinessException("更新失败！");
				}
			}else{
				if(collectService.updateRow(entityId, user.getId(), primaryKey,unitId,discId, rowData)==1){
						
						return "success";
					}
					else{
						throw new BusinessException("更新失败！");
					}
			}
			
		}else if("del".equals(oper)){
			if(collectService.deleteRow(entityId, primaryKey,seqNo,unitId,discId, user.getId())){
				return "success";
			}else{
				throw new BusinessException("数据删除失败！");
			}
				
		}
		return "success";
	}
	@RequestMapping(value="collectionClear/{tableId}/{unitId}/{discId}/{pkValue}/{seqNo}")
	@ResponseBody
	public String clearCollectItem(@PathVariable("tableId")String tableId,
			@PathVariable("unitId")String unitId,
			@PathVariable("discId")String discId,
			@PathVariable("pkValue")String pkValue,
			@PathVariable("seqNo")String seqNo,
			HttpSession session){
		 UserSession  userSession = new UserSession(session);
		 User user = userSession.getCurrentUser();
		 if(collectService.clearOneCollectItem(tableId, unitId, discId, pkValue, user.getId(), seqNo))
		 {
			 return "success";
		 }
		 return "error";
	}
	

	/**
	 * 对于采集成果重新排序
	 * @param request
	 * @return
	 */
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
	@RequestMapping("importFromPub/{entityId}/{unitId}/{discId}/{categoryId}")
	@ResponseBody
	public String importFromPub(@PathVariable(value="entityId")String entityId,
			@PathVariable(value="unitId")String unitId,
			@PathVariable(value="discId")String discId,
			@PathVariable(value="categoryId")String categoryId,
			HttpSession session){
		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();
		collectService.importPubData(entityId, unitId, discId,user.getId());
		return "success";
	}
	/**
	 * 从教师采集表导入本轮采集表
	 * @param oriEntityId 源采集表EntityId
	 * @param tarEntityId 本轮采集表EntityId
	 * @param unitId
	 * @param discId
	 * @param pkValues 源数据ID
	 * @param importType 导入类型（0 从教师表导入，1从上轮采集表导入）
	 * @param session
	 * @return
	 */
	@RequestMapping("/import2Disc/{oriEntityId}/{tarEntityId}/{unitId}/{discId}/{pkValues}/{importType}")
	@ResponseBody
	public String import2Disc(@PathVariable(value="oriEntityId")String oriEntityId,
			@PathVariable(value="tarEntityId")String tarEntityId,
			@PathVariable(value="unitId")String unitId,
			@PathVariable(value="discId")String discId,
			@PathVariable(value="pkValues")List<String> pkValues,
			@PathVariable(value="importType")String importType,
			HttpSession session){
		//Map<String,String> result = new HashMap<String,String>(0);
		UserSession userSession =  new UserSession(session);
		String userId = userSession.getCurrentUser().getId();
		for(String pkValue: pkValues){
			if(collectService.isExistItem(tarEntityId, pkValue)){
				throw new CollectBusinessException("选中的教师成果部分已导入，请重新确认！");
			}
		}
		for(String pkValue: pkValues){
			String oriPkValue = collectService.importNewRow(oriEntityId, tarEntityId, pkValue, importType,unitId, discId, userId);
			if(StringUtils.isBlank(oriPkValue)){
				throw new CollectBusinessException("部分数据导入失败，请重新操作！");
			}
		}
		return "success";
	}
	@RequestMapping("/uploadFileForm/{unitId}/{discId}/{entityId}/{pkValue}/{titleValue}")
	@ResponseBody
	public String uploadFileForm(@PathVariable(value="unitId")String unitId,
			@PathVariable(value="discId")String discId,
			@PathVariable(value="entityId")String entityId,
			@PathVariable(value="pkValue")String pkValue,
			@PathVariable(value="titleValue")String titleValue,
			HttpServletRequest request){
		String sessionId = request.getParameter("jsessionid");
		MySessionContext myc= MySessionContext.getInstance();
		HttpSession session = myc.getSession(sessionId); 
		
		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();
		MultipartFile file = FileOperate.getFile(request);
		String attachId = null;
		String state = "error";
		AttachmentHelper attachmentHelper = attachmentService.getAttachmentHelper(file, user.getId(), 
				AttachmentType.COLLECTION, user.getUnitId());
		if(FileOperate.upload(file, attachmentHelper.getPath(), attachmentHelper.getStorageName())){
			attachId = attachmentService.addAttachment(attachmentHelper.getAttachment());
			if(attachId!=null){
				Map<String, String> rowData = new HashMap<String,String>(0);
				rowData.put(titleValue, attachId);
				if("0".equals(pkValue)){
					if(collectService.uploadDiscIntroduceFile(entityId, unitId, discId, user.getId(), rowData,attachId)!=null){
						state = "success";
					}
				}else{
					if(collectService.updateDiscIntroduceFile(entityId, unitId,discId,user.getId(),rowData,pkValue,attachId)!=null){
						state = "success";
					}
				}
			}
		}
		return state;
	}
	/**
	 * 
	 * @param unitId
	 * @param file
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/uploadFile/{unitId}")
	@ResponseBody
	public String uploadFile(@PathVariable(value="unitId")String unitId,
			HttpServletRequest request){
		String sessionId = request.getParameter("jsessionid");
		MySessionContext myc= MySessionContext.getInstance();
		HttpSession session = myc.getSession(sessionId); 
		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();
		MultipartFile file = FileOperate.getFile(request);
		AttachmentHelper attachmentHelper = attachmentService.getAttachmentHelper(file, user.getId(), 
				AttachmentType.COLLECTION, user.getUnitId());
		if(FileOperate.upload(file, attachmentHelper.getPath(), attachmentHelper.getStorageName()))
			return attachmentService.addAttachment(attachmentHelper.getAttachment());
		else
			return "error";
	}
	@RequestMapping("downLoadAttach/{attachId}")
	@ResponseBody
	public String downLoadAttach(@PathVariable(value="attachId")String attachId){
		return JsonConvertor.obj2JSON(attachmentService.getAttachmentPath(attachId));
	}
	@RequestMapping("downLoadTemplate/{templateId}")
	@ResponseBody
	public String downLoadTemplate(@PathVariable(value="templateId")String templateId){
		return JsonConvertor.obj2JSON(attachmentService.getAttachmentPath(templateId));
	}
	@RequestMapping("/delFileFormAttach/{unitId}/{discId}/{entityId}/{pkValue}/{titleValue}/{attachId}")
	@ResponseBody
	public String delFileFormAttach(@PathVariable(value="unitId")String unitId,
			@PathVariable(value="discId")String discId,
			@PathVariable(value="entityId")String entityId,
			@PathVariable(value="pkValue")String pkValue,
			@PathVariable(value="titleValue")String titleValue,
			@PathVariable(value="attachId")String attachId,
			HttpServletRequest request,
			HttpSession session){
		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();
		Map<String, String> rowData = new HashMap<String,String>(0);
		rowData.put(titleValue, " ");
		if(collectService.updateRow(entityId,user.getId() , pkValue, unitId,discId,rowData)==1){
			return "success";
		}else{
			return "error";
		}
	}
	@RequestMapping("/deleteAttach/{attachId}")
	@ResponseBody
	public String deleteAttach(@PathVariable(value="attachId")String attachId){
		attachmentService.deleteAttachment(attachId);
		return "success";
	}
	/**
	 * 从request中获取批量添加的数据
	 * @param params
	 * @return
	 */
	private BatchData getBatchEditData(String params)
	{
		List batchEditData=new ArrayList();
		JSONObject jsonObject = JSONObject.fromObject(params);
		String entityId=jsonObject.getJSONArray("tableId").get(0).toString();
		JSONArray titleValues= jsonObject.getJSONArray("titleValues");
		JSONArray rowsArray=jsonObject.getJSONArray("rows");
		for(Object o:rowsArray )
		{
			Map map= new HashMap();
			JSONObject row=(JSONObject)o;
			for(Object colName:titleValues)
			{	
				if("ATTACH_ID".equals(colName.toString())){
					if(!row.getString(colName.toString()).equals("请上传附件")){
						map.put(colName.toString(),row.getString(colName.toString()));
					}
				}else{
					map.put(colName.toString(),row.getString(colName.toString()));
				}
			}
			batchEditData.add(map);
		}
		BatchData batchData=new BatchData();
		batchData.setEntityId(entityId);
		batchData.setRowDatas(batchEditData);
		return batchData;
	}
	/**
	 * 封装的批量添加的类
	 * @author OPCUser
	 *
	 */
	private class BatchData
	{
		private String entityId;
		private List<Map<String, String>>rowDatas;
		public String getEntityId() {
			return entityId;
		}
		public void setEntityId(String entityId) {
			this.entityId = entityId;
		}
		public List<Map<String, String>> getRowDatas() {
			return rowDatas;
		}
		public void setRowDatas(List<Map<String, String>> rowDatas) {
			this.rowDatas = rowDatas;
		}
	}
}
