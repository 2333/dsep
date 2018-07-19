package com.dsep.service.publicity.objection.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dsep.dao.base.AttachmentDao;
import com.dsep.dao.dsepmeta.publicity.ProveMaterialDao;
import com.dsep.dao.dsepmeta.publicity.objection.OriginalObjectionDao;
import com.dsep.dao.dsepmeta.publicity.objection.PublicityManagementDao;
import com.dsep.dao.dsepmeta.publicity.objection.PublicityObjectTypeDao;
import com.dsep.entity.Attachment;
import com.dsep.entity.dsepmeta.OriginalObjection;
import com.dsep.entity.dsepmeta.PublicityManagement;
import com.dsep.entity.dsepmeta.PublicityObjectType;
import com.dsep.entity.enumeration.publicity.CenterObjectStatus;
import com.dsep.entity.enumeration.publicity.UnitObjectStatus;
import com.dsep.entity.enumeration.rbac.UserType;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.dsepmeta.MetaOper;
import com.dsep.service.dsepmeta.dsepmetas.DMCollectService;
import com.dsep.service.dsepmeta.dsepmetas.DMDiscIndexService;
import com.dsep.service.file.ExportService;
import com.dsep.service.publicity.objection.OriginalObjectionService;
import com.dsep.util.DateProcess;
import com.dsep.util.GUID;
import com.dsep.vm.PageVM;
import com.dsep.vm.publicity.OriginalObjectionVM;
import com.meta.service.MetaEntityService;

public class OriginalObjectionServiceImpl extends MetaOper implements
		OriginalObjectionService {

	private OriginalObjectionDao originalObjectionDao;
	private DMDiscIndexService discIndexService;
	private DMCollectService collectService;
	private ExportService exportService;
	private MetaEntityService metaEntityService;
	private DisciplineService disciplineService;
	private PublicityManagementDao publicityManagementDao;
	private PublicityObjectTypeDao publicityObjectTypeDao;
	private ProveMaterialDao proveMaterialDao;
	private AttachmentDao attachmentDao;

	public void setAttachmentDao(AttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}
	
	public ProveMaterialDao getProveMaterialDao() {
		return proveMaterialDao;
	}

	public void setProveMaterialDao(ProveMaterialDao proveMaterialDao) {
		this.proveMaterialDao = proveMaterialDao;
	}

	public PublicityObjectTypeDao getPublicityObjectTypeDao() {
		return publicityObjectTypeDao;
	}

	public void setPublicityObjectTypeDao(
			PublicityObjectTypeDao publicityObjectTypeDao) {
		this.publicityObjectTypeDao = publicityObjectTypeDao;
	}

	public PublicityManagementDao getPublicityManagementDao() {
		return publicityManagementDao;
	}

	public void setPublicityManagementDao(
			PublicityManagementDao publicityManagementDao) {
		this.publicityManagementDao = publicityManagementDao;
	}

	public OriginalObjectionDao getOriginalObjectionDao() {
		return originalObjectionDao;
	}

	public void setOriginalObjectionDao(
			OriginalObjectionDao originalObjectionDao) {
		this.originalObjectionDao = originalObjectionDao;
	}

	public DMDiscIndexService getDiscIndexService() {
		return discIndexService;
	}

	public void setDiscIndexService(DMDiscIndexService discIndexService) {
		this.discIndexService = discIndexService;
	}

	public DMCollectService getCollectService() {
		return collectService;
	}

	public void setCollectService(DMCollectService collectService) {
		this.collectService = collectService;
	}

	public ExportService getExportService() {
		return exportService;
	}

	public void setExportService(ExportService exportService) {
		this.exportService = exportService;
	}

	public MetaEntityService getMetaEntityService() {
		return metaEntityService;
	}

	public void setMetaEntityService(MetaEntityService metaEntityService) {
		this.metaEntityService = metaEntityService;
	}

	public DisciplineService getDisciplineService() {
		return disciplineService;
	}

	public void setDisciplineService(DisciplineService disciplineService) {
		this.disciplineService = disciplineService;
	}

	@Override
	public void updateSchoolStatusToSubmit(String publicityRoundId,String unitId) {
		originalObjectionDao.submitObjectionByUnit(publicityRoundId, unitId);
	}

	public String addNewObjection(OriginalObjection newObjection) {
		newObjection.setBeginTime(new Date());
		newObjection.setId(GUID.get());
		/*newObjection.setStatus(ObjectStatus.NOCHECK.getStatus());*/
		newObjection.setUnitStatus(UnitObjectStatus.NOTSUBMIT.getStatus());
		String theId = originalObjectionDao.save(newObjection);
		return theId;
	}

	private boolean isSaveSuccess(String id) {
		if (id == null || id == "")
			return false;
		else
			return true;
	}

	@Override
	public boolean updateObjection(OriginalObjection newObjection)
			throws NoSuchFieldException, SecurityException {
		/* originalObjectionDao.saveOrUpdate(newObjection); */
		Map<String, Object> editRow = new HashMap<String, Object>();
		editRow.put("objectType", newObjection.getUnitObjectType());
		editRow.put("objectContent", newObjection.getUnitObjectContent());
		originalObjectionDao.updateColumn(newObjection.getId(), editRow);
		return true;
	}

	@Override
	public boolean deleteObjection(String objectionId) {
		originalObjectionDao.deleteByKey(objectionId);
		return true;
	}

	@Override
	public PageVM<OriginalObjection> getOriginalObjection(int pageIndex,
			int pageSize, boolean desc, String orderPropName,
			OriginalObjection conditionalObjection)
			throws IllegalArgumentException, IllegalAccessException {
		@SuppressWarnings("unused")
		List<OriginalObjection> objectionList = originalObjectionDao
				.queryByCondition(conditionalObjection, pageIndex, pageSize,
						desc, orderPropName);
		for (OriginalObjection objection : objectionList) {
			System.out.println(objection.getObjectCollectEntityId());
		}
		return null;
	}

	@Override
	public PageVM<OriginalObjectionVM> showOriginalObjections(OriginalObjection objection,
			String orderName,boolean order_flag, int pageIndex, int pageSize)
			throws IllegalArgumentException, IllegalAccessException {
		List<OriginalObjection> dataList = new LinkedList<OriginalObjection>();
		dataList = originalObjectionDao.queryByCondition(objection, pageIndex,
				pageSize, order_flag, orderName);
		List<OriginalObjectionVM> list = new LinkedList<OriginalObjectionVM>();
		int i=0;
		for (OriginalObjection data : dataList) {
			try{
				i++;
				OriginalObjectionVM vmData = new OriginalObjectionVM(data,this);
				list.add(vmData);
			}
			catch(Exception e){
				continue;
			}
		}
		int count = originalObjectionDao.getCountByCondition(objection);
		return new PageVM<OriginalObjectionVM>(pageIndex, count, pageSize, list);
	}

	@Override
	public int updateObjectionRowData(String key, Map<String, Object> dataMap) {
		dataMap.put("checkTime", new Date());
		try {
		    return originalObjectionDao.updateColumn(key, dataMap);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			return 0;
		} catch (SecurityException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public String downloadSubmitObjection(OriginalObjection conditionalObjection,
			String rootPath, String userType) {
		//设置查询条件，从数据库中查询需要导出的异议数据
		/*OriginalObjection conditionalObjection = new OriginalObjection();
		conditionalObjection.setStatus(ObjectStatus.SUBMIT.getStatus());
		conditionalObjection.setCurrentPublicRoundId(currentRoundId);
		conditionalObjection.setObjectUnitId(unitId);*/

		//设置excel的列名
		List<String> objectTitle = new LinkedList<String>();
		objectTitle.add("被异议学校ID");
		objectTitle.add("被异议学科ID");
		objectTitle.add("异议类型");
		objectTitle.add("被异议的采集项");
		objectTitle.add("被异议的数据项");
		objectTitle.add("被异议的数据序号");
		objectTitle.add("异议内容");
		objectTitle.add("异议提出时间");
		objectTitle.add("最近修改时间");

		List<List<String>> excelTitle = new LinkedList<List<String>>();
		excelTitle.add(objectTitle);
		
		//生成excel的文件名
		String objectSheetName = "";
		/*objectSheetName += unitId + "-";*/
		objectSheetName += "异议汇总表";
		List<String> sheetName = new LinkedList<String>();
		sheetName.add(objectSheetName);
		PublicityManagement publicity = publicityManagementDao.get(conditionalObjection.getCurrentPublicRoundId());
		String roundName = publicity.getPublicRoundName();
		objectSheetName += "-" + roundName;

		String storeFolder = "objection";

		List<List<String[]>> exportDataList = new LinkedList<List<String[]>>();
		
		List<OriginalObjection> objectDataList = null;
		try {
			objectDataList = originalObjectionDao.queryByCondition(
					conditionalObjection, 0, 0, true, null);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		List<String[]> objectExcelDataList = new LinkedList<String[]>();
		
		for (OriginalObjection attrEntity : objectDataList) {
			String[] data = new String[9];
			data[0] = attrEntity.getProblemUnitId();
			data[1] = attrEntity.getProblemDiscId();
			data[2] = attrEntity.getUnitObjectType();
			if (this.isNotBlank(attrEntity.getObjectCollectEntityName())) {
				data[3] = attrEntity.getObjectCollectEntityName();
			} else {
				data[3] = "\\";
			}
			if (this.isNotBlank(attrEntity.getUnitObjectCollectAttrName())) {
				data[4] = attrEntity.getUnitObjectCollectAttrName();
			} else {
				data[4] = "\\";
			}
			if( attrEntity.getSeqNo() != null && attrEntity.getSeqNo() != 0){
				data[5] = attrEntity.getSeqNo()+"";
			}
			else{
				data[5] = "\\";
			}
			if( userType.equals(UserType.CENTER.getStatus())){
				data[6] = attrEntity.getCenterObjectContent();
			}
			else{
				data[6] = attrEntity.getUnitObjectContent();
			}
			data[7] = DateProcess.getShowingDate(attrEntity
					.getBeginTime());
			data[8] = DateProcess.getShowingDate(attrEntity
					.getCheckTime());
			objectExcelDataList.add(data);
		}

		exportDataList.add(objectExcelDataList);

		return exportService.exportExcelByData(excelTitle, exportDataList,
				sheetName, rootPath, storeFolder);
	}
	
	
	private boolean isNotBlank(String str){
		if( str != null && !str.equals(""))
			return true;
		else
			return false;
	}




	@Override
	public Map<String, String> getObjectTypeByEntityId(String entityId) {
		// TODO Auto-generated method stub
		List<PublicityObjectType> objectTypeList = publicityObjectTypeDao.getObjectTypeListByEntityId(entityId);
		Map<String,String> objTypeMap = new HashMap<String,String>();
		for(PublicityObjectType objType:objectTypeList){
			objTypeMap.put(objType.getObjectAttrId()+","+objType.getObjectAttrName(), objType.getObjectTypeName());
		}
		return objTypeMap;
	}

	@Override
	public boolean addNewObjection(OriginalObjection newObjection,
			String proveMaterialId) throws Exception {
		// TODO Auto-generated method stub
		if( proveMaterialId != null && !proveMaterialId.equals("")){
			newObjection.setProveMaterial(attachmentDao.get(proveMaterialId));
		}
		String objectionId = this.addNewObjection(newObjection);
		if( objectionId != null)
			return true;
		else
			return false;
	}

	@Override
	public boolean departmentNotPassObjection(String objectionId) {
		// TODO Auto-generated method stub
		OriginalObjection notPassObjection = originalObjectionDao.get(objectionId);
		notPassObjection.setUnitStatus(UnitObjectStatus.UNITNOTPASS.getStatus());
		return true;
	}

	@Override
	public String getProveMaterial(String objectionId) {
		// TODO Auto-generated method stub
		return proveMaterialDao.getMaterialPath(objectionId);
	}

	@Override
	public int getObjectionCount(OriginalObjection conditionalObjection) throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		List<OriginalObjection> objectionList = originalObjectionDao.queryByCondition(conditionalObjection);
		if( objectionList != null){
			return objectionList.size();
		}
		else{
			return -1;
		}
	}

	@Override
	public OriginalObjection getObjectionById(String objectionId) {
		// TODO Auto-generated method stub
		return originalObjectionDao.get(objectionId);
	}

	@Override
	public boolean centerNotPassObjection(String objectionId) {
		// TODO Auto-generated method stub
		OriginalObjection notPassObjection = originalObjectionDao.get(objectionId);
		notPassObjection.setCenterStatus(CenterObjectStatus.NOTPASS.getStatus());
		return true;
	}

	@Override
	public boolean processAllObjection(String publicityRoundId) {
		// TODO Auto-generated method stub
		if( originalObjectionDao.processAllObjection(publicityRoundId) > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean uploadFile(String objectionItemId, String proveMaterialId) {
		// TODO Auto-generated method stub
		Attachment attachment = attachmentDao.get(proveMaterialId);
		OriginalObjection theObjection = originalObjectionDao.get(objectionItemId);
		theObjection.setProveMaterial(attachment);
		/*this.setResponseTime(theResponse);*/
		return true;
	}

	@Override
	public boolean deleteProveMaterial(String objectionItemId,
			String proveMaterialId) {
		// TODO Auto-generated method stub
		OriginalObjection response = originalObjectionDao.get(objectionItemId);
		response.setProveMaterial(null);
		/*response.setModifyTime(new Date());*/
		attachmentDao.deleteByKey(proveMaterialId);
		return true;
	}
	
	
}
