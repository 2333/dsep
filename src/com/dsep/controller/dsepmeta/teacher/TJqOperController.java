package com.dsep.controller.dsepmeta.teacher;

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
import org.springframework.web.multipart.MultipartFile;

import com.dsep.common.exception.BusinessException;
import com.dsep.common.logger.LoggerTool;
import com.dsep.domain.AttachmentHelper;
import com.dsep.entity.User;
import com.dsep.entity.enumeration.AttachmentType;
import com.dsep.service.base.AttachmentService;
import com.dsep.service.dsepmeta.dsepmetas.DMCollectService;
import com.dsep.util.FileOperate;
import com.dsep.util.JsonConvertor;
import com.dsep.util.MySessionContext;
import com.dsep.util.Tools;
import com.dsep.util.UserSession;

@Controller
@RequestMapping("TCollect/toTCollect/JqOper")
public class TJqOperController {
	
	@Resource(name="loggerTool")
	private LoggerTool loggerTool;
	@Resource(name="collectService")
	private DMCollectService collectService;
	
	@Resource(name = "attachmentService")
	private AttachmentService attachmentService;
	@RequestMapping("collectionTEdit/{entityId}/{primaryKey}/{titleValues}/{userId}/{seqNo}")
	@ResponseBody
	public String collectTEdit(@PathVariable(value="entityId")String entityId,
			@PathVariable(value="primaryKey")String primaryKey,
			@PathVariable(value="titleValues")List<String>titleValues,
			@PathVariable(value="userId") String userId,
			@PathVariable(value="seqNo")String seqNo,HttpServletRequest request,
			HttpSession session){
		UserSession userSession= new UserSession(session);
		User user= userSession.getCurrentUser();
		String oper=request.getParameter("oper");
		Map<String, String> rowData = Tools.requestData2Map(request, titleValues);
		if("edit".equals(oper)){
			if("0".equals(primaryKey)){//新建
				if(collectService.newTRow(entityId, user, rowData)!=null){
					return "success";
				}else{
					throw new BusinessException("更新失败！");
				}
			}else{//编辑
				if(collectService.updateRow(entityId, user.getId(), primaryKey,"","",rowData)>0){
					return "success";
				}else{
					throw new BusinessException("更新失败！");
				}
			}
		}else if("del".equals(oper)){
			if(collectService.deleteTRow(entityId, primaryKey, userId,seqNo)){
				return "success";
			}else{
				throw new BusinessException("删除成果！");
			}
		}
		return "success";
	}
	
	@RequestMapping("/batchTSubmit")
	@ResponseBody
	public Map<String,String> batchTSubmit(HttpServletRequest request,HttpSession session){
		String params=request.getParameter("params");
		BatchData sub_byBatch=getBatchEditData(params);
		Map<String, String> map = new HashMap<>();
		UserSession userSession= new UserSession(session);
		User user= userSession.getCurrentUser();
		try{
			for(int i=0;i<sub_byBatch.getRowDatas().size();i++)
			{
				if(collectService.newTRow(sub_byBatch.getEntityId(),user, sub_byBatch.getRowDatas().get(i))!=null)
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
	@RequestMapping("/batchDel/{entityId}/{pkValues}")
	@ResponseBody
	public String batchDelItem(@PathVariable(value="entityId")String entityId,
			@PathVariable(value="pkValues")List<String>pkValues,
			HttpSession session){
		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();
		for(String pkValue:pkValues){
			collectService.deleteTRow(entityId, pkValue, user.getId(), "-1");
		}
		return "success";
	}
	@RequestMapping("downLoadTemplate/{templateId}")
	@ResponseBody
	public String downLoadTemplate(@PathVariable(value="templateId")String templateId){
		return JsonConvertor.obj2JSON(attachmentService.getAttachmentPath(templateId));
	}
	/**
	 * 对于采集成果重新排序
	 * @param request
	 * @return
	 */
	@RequestMapping("/reorderT")
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
	/**
	 * 上传文件
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("uploadFile")
	@ResponseBody
	public String uploadFile(HttpServletRequest request){
		String sessionId = request.getParameter("jsessionid");
		MySessionContext myc= MySessionContext.getInstance();
		HttpSession session = myc.getSession(sessionId); 
		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();
		MultipartFile file = FileOperate.getFile(request);
		AttachmentHelper attachmentHelper = attachmentService.getAttachmentHelper(file, user.getId(), 
				AttachmentType.TEACHER, user.getUnitId());
		if(FileOperate.upload(file, attachmentHelper.getPath(), attachmentHelper.getStorageName()))
			return attachmentService.addAttachment(attachmentHelper.getAttachment());
		else
			return "error";
	}
	/**
	 * 下载文件
	 * @param attachId
	 * @return
	 */
	@RequestMapping("downLoadAttach/{attachId}")
	@ResponseBody
	public String downLoadAttach(@PathVariable(value="attachId")String attachId){
		return JsonConvertor.obj2JSON(attachmentService.getAttachmentPath(attachId));
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
				map.put(colName.toString(),row.getString(colName.toString()));
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
