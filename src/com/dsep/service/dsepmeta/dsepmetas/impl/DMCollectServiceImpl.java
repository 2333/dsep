package com.dsep.service.dsepmeta.dsepmetas.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.common.exception.BusinessException;
import com.dsep.common.exception.CollectBusinessException;
import com.dsep.dao.dsepmeta.databackup.CenterDataBackupDao;
import com.dsep.domain.dsepmeta.viewconfig.ViewType;
import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.CategoryCollect;
import com.dsep.entity.dsepmeta.DataModifyHistory;
import com.dsep.entity.enumeration.datamodify.ModifySource;
import com.dsep.entity.enumeration.datamodify.ModifyType;
import com.dsep.service.base.TeachDiscService;
import com.dsep.service.datamodify.DataModifyService;
import com.dsep.service.dsepmeta.MetaOper;
import com.dsep.service.dsepmeta.dsepmetas.DMBackupService;
import com.dsep.service.dsepmeta.dsepmetas.DMCollectService;
import com.dsep.service.expert.evaluation.EvalService;
import com.dsep.service.flow.EvalFlowService;
import com.dsep.service.rbac.UserService;
import com.dsep.util.CollectTools;
import com.dsep.util.Configurations;
import com.dsep.util.GUID;
import com.dsep.vm.CollectionTreeVM;
import com.dsep.vm.JqgridVM;
import com.dsep.vm.collect.CollectCategoryTreeVM;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.meta.domain.MetaEntityDomain;
import com.meta.domain.search.SearchGroup;
import com.meta.domain.search.SearchRule;
import com.meta.domain.search.SearchType;
import com.meta.entity.MetaAttribute;
import com.meta.entity.MetaAttributeMap;
import com.meta.entity.MetaEntity;
import com.meta.entity.MetaEntityCategory;
import com.meta.entity.MetaEntityMap;

public class DMCollectServiceImpl extends MetaOper implements DMCollectService {
	
	
	private UserService userService;
	private TeachDiscService teachDiscService;
	private EvalFlowService evalFlowService;
	private DataModifyService dataModifyService;
	private CenterDataBackupDao centerDataBackupDao;
	
	
	public void setCenterDataBackupDao(CenterDataBackupDao centerDataBackupDao) {
		this.centerDataBackupDao = centerDataBackupDao;
	}
	public void setDataModifyService(DataModifyService dataModifyService) {
		this.dataModifyService = dataModifyService;
	}
	public EvalFlowService getEvalFlowService() {
		return evalFlowService;
	}
	public void setEvalFlowService(EvalFlowService evalFlowService) {
		this.evalFlowService = evalFlowService;
	}
	@Override
	public List<CollectionTreeVM> getDisciplineCollectTrees(String discId) {
		/**
		 * discCategory字段目前还没有使用？此处考虑的应该是学科分类，每个分类一个指标体系
		 */
		String categoryId = discCategoryDao.getCatByDiscId(discId);
		String collectId = categoryDao.getCollectIdById(categoryId);
		List<CategoryCollect> categoryCollects= categoryCollectDao.getCategoryTreeById(collectId);
		List<CollectionTreeVM> collTreeList = new LinkedList<CollectionTreeVM>();
		for(CategoryCollect c: categoryCollects)
		{
			if("R".equals(c.getMetaCat()))
				continue;
			CollectionTreeVM cTreeVM = new CollectionTreeVM(c);
			collTreeList.add(cTreeVM);
		}
		return collTreeList;
	}
	@Override
	public CollectCategoryTreeVM getDiscCollectCategoryTreeVMs(String discId) {
		// TODO Auto-generated method stub
		String categoryId = discCategoryDao.getCatByDiscId(discId);
		String collectId = categoryDao.getCollectIdById(categoryId);
		List<CategoryCollect> categoryCollects= categoryCollectDao.getCategoryTreeById(collectId);
		List<CollectionTreeVM> collTreeList = new LinkedList<CollectionTreeVM>();
		for(CategoryCollect c: categoryCollects)
		{
			if("R".equals(c.getMetaCat()))
				continue;
			CollectionTreeVM cTreeVM = new CollectionTreeVM(c);
			collTreeList.add(cTreeVM);
		}
		return new CollectCategoryTreeVM(categoryId,collTreeList);
	}
	@Override
	public List<CollectionTreeVM> getTreesByOccasion(String occasion) {
		// TODO Auto-generated method stub
		List<MetaEntityCategory> categories = metaEntityCategoryService.getEntityCategories(occasion);
		List<CollectionTreeVM> cTreeVMs= new ArrayList<CollectionTreeVM>(0);
		if(categories.size()>0){
			for(MetaEntityCategory category : categories){
				CollectionTreeVM cTreeVM= new CollectionTreeVM(category.getId(),category.getName(),
						category.getParentId(),category.getName(),true,true,category.getId());
				cTreeVMs.add(cTreeVM);
				for(MetaEntity entity:category.getEntities()){
					CollectionTreeVM eTreeVM = new CollectionTreeVM(entity.getId(),entity.getChsName(),
							entity.getCategoryId(),entity.getChsName(),true,true,entity.getId());
					cTreeVMs.add(eTreeVM);
				}	
			}
		}
		return cTreeVMs;
	}
	@Override
	public List<CollectionTreeVM> getDisciplineCollectTreesByCatId(String catId) {
		// TODO Auto-generated method stub
		String collectId = categoryDao.getCollectIdById(catId);
		List<CategoryCollect> categoryCollects= categoryCollectDao.getCategoryTreeById(collectId);
		List<CollectionTreeVM> collTreeList = new LinkedList<CollectionTreeVM>();
		for(CategoryCollect c: categoryCollects)
		{
			if("R".equals(c.getMetaCat()))
				continue;
			CollectionTreeVM cTreeVM = new CollectionTreeVM(c);
			collTreeList.add(cTreeVM);
		}
		return collTreeList;
	}
	@Override
	public String newRow(String entityId, String unitId, String discId, String userId, Map<String,String> rowData){
		if(!rowData.containsKey("UNIT_ID")){
			rowData.put("UNIT_ID", unitId);// 单位代码
		}
		if(!rowData.containsKey("DISC_ID")){
			rowData.put("DISC_ID", discId);// 学科代码*/
		}
		if(!rowData.containsKey("INSERT_USER_ID")){
			rowData.put("INSERT_USER_ID", userId);// 学科代码*/
		}
		if(!rowData.containsKey("MODIFY_USER_ID")){
			rowData.put("MODIFY_USER_ID", userId);// 学科代码*/
		}
		return super.newRow(entityId, rowData);
	}
	@Override
	public int updateRow(String entityId, String userId,String pkValue,String unitId,String discId,
		Map<String, String> rowData) {

		if(!rowData.containsKey("MODIFY_USER_ID")){
			rowData.put("MODIFY_USER_ID", userId);// 学科代码*/
		}
		if(!rowData.containsKey("MODIFY_TIME")){
			
			rowData.put("MODIFY_TIME",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).toString());// 学科代码*/
		}
		User theUser = userService.getUser(userId);
		if( theUser.getUserType().equals("1")){
			/*
			 * 这里的逻辑为双重循环，第一层是要更新的列值的集合，第二层是实体所有的列值的集合
			 * 如果列名相等则进行对比，如果要更新的列值与原来的列值不同则记录历史，否则进行continue操作
			 * 此外，对于"MODIFY_USER_ID"和"MODIFY_TIME"这两列不进行记录历史的操作
			 */
			for(Map.Entry<String,String> theEntry:rowData.entrySet()){
				if( theEntry.getKey().equals("MODIFY_USER_ID")||theEntry.getKey().equals("MODIFY_TIME")){
					continue;
				}
				
				MetaEntity entity = metaEntityService.getById(entityId);
				
				//获取数据项所有列值的集合
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("id", pkValue);//查询条件，根据数据项Id获取要更新的那条数据项
				Map<String,Object> attributes = super.getDataObject(entityId, params, "", true, 0, 1).get(0);
				
				//attributes是采集项所有列名和列值的集合
				//rowData是从前台传过来的列名和列值集合
				//比较两者列名相同而列值不同的字段记入修改历史中
				for( Map.Entry<String, Object> attribute:attributes.entrySet()){
					String attributeKey = attribute.getKey();
					String entryKey = theEntry.getKey();
					String attributeValue = "";
					String entryValue = "";
					if( attribute.getValue() != null ){
						attributeValue = attribute.getValue().toString();
					}
					if( theEntry.getValue() != null){
						entryValue = theEntry.getValue();
					}
					
					boolean isKeyEqual = attributeKey.equals(entryKey);
					boolean isValueEqual = attributeValue.equals(entryValue); 
					if( isKeyEqual && !isValueEqual){
						MetaAttribute attributeEntity = metaEntityService.getEntityAttrbute(entityId, entryKey);
						DataModifyHistory modifyHistory = new DataModifyHistory();
						modifyHistory.setId(GUID.get());
						modifyHistory.setEntityId(entityId);
						modifyHistory.setEntityName(entity.getChsName());
						modifyHistory.setUnitId(unitId);
						modifyHistory.setDiscId(discId);
						modifyHistory.setAttrName(attributeEntity.getChsName());
						modifyHistory.setModifyTime(new Date());
						modifyHistory.setEntityItemId(pkValue);
						modifyHistory.setAttrEnsName(theEntry.getKey());
						modifyHistory.setAttrModifyValue(theEntry.getValue());
						modifyHistory.setAttrOriginalValue(attribute.getValue().toString());
						modifyHistory.setOperateUserId(theUser.getLoginId());
						modifyHistory.setModifyType(ModifyType.CHANGE.getStatus());
						modifyHistory.setModifySource(ModifySource.DATACHANGE.getStatus());
						dataModifyService.saveModifyHistory(modifyHistory);
						break;
					}
				}
			}
		}
		return super.updateRow(entityId, pkValue, rowData);
	}

	@Override
	public int reOrder(String entityId, String userId, List<String> pkValues,
			List<Map<String, String>> rowDatas) {
		int counter=0;
		for(int i=0;i<pkValues.size();i++)
		{
			counter+=updateRow(entityId,pkValues.get(i),rowDatas.get(i));
		}
		return counter;
	}

	@Override
	public boolean deleteRow(String entityId, String pkValue,String seqNo, String unitId,
			String discId, String userId) {
		
		Map<String, Object> rowData = new HashMap<String, Object>(0);
		if(StringUtils.isNotBlank(unitId) && StringUtils.isNotBlank(discId))
		{
			rowData.put("UNIT_ID", unitId);
			rowData.put("DISC_ID", discId);
		}
		if(!"-1".equals(seqNo)){
			super.updateSeqNo(entityId, seqNo, rowData);
		}
		User theUser = userService.getUser(userId);
		/*if( theUser.getUserType().equals("1")){//如果是中心用户则需要保存删除的记录
			DataModifyHistory modifyHistory = new DataModifyHistory();
			modifyHistory.setId(GUID.get());
			modifyHistory.setEntityId(entityId);
			
			MetaEntity entity = metaEntityService.getById(entityId);
			modifyHistory.setEntityName(entity.getChsName());
			modifyHistory.setModifyTime(new Date());
			modifyHistory.setEntityItemId(pkValue);
			modifyHistory.setUnitId(unitId);
			modifyHistory.setDiscId(discId);
			modifyHistory.setSeqNo(Integer.parseInt(seqNo));
			modifyHistory.setOperateUserId(theUser.getLoginId());
			modifyHistory.setModifyType(ModifyType.DELETE.getStatus());
			modifyHistory.setModifySource(ModifySource.DATACHANGE.getStatus());
			dataModifyService.deleteEntityData(modifyHistory);
			
			Map<String,String> nullRowData = new HashMap<String,String>();
			int result = this.updateRow(entityId, userId, pkValue, unitId, discId, nullRowData);
			if( result <= 0 ){
				throw new BusinessException("出现错误");
			}
			
			centerDataBackupDao.backupDataWhenDelete(entity.getName(),
					Configurations.getBackupTable(entity.getName(),"bak"),pkValue);
			
		}*/
		return super.deleteRow(entityId, pkValue);
	}
	

	@Override
	public JqgridVM getJqGridData(String entityId, String unitId,
			String discId, int pageIndex,
			int pageSize, String orderPropName, boolean asc) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (!params.containsKey("UNIT_ID")) {
			params.put("UNIT_ID", unitId);// 单位代码
		}
		if (!params.containsKey("DISC_ID")) {
			params.put("DISC_ID", discId);// 学科代码*/
		}
		return super.getJqGridData(entityId, params, pageIndex, pageSize,orderPropName, asc);
	}

	@Override
	public int getCount(String entityId, String unitId, String discId) {
		// TODO Auto-generated method stub
		Map<String, Object> params= new HashMap<String, Object>(0);
		if(StringUtils.isNotBlank(unitId)){
			params.put("UNIT_ID", unitId);
		}
		if(StringUtils.isNotBlank(discId)){
			params.put("DISC_ID", discId);
		}
		return super.getCount(entityId, params);
	}
	@Override
	public String newTRow(String entityId, User user,
			Map<String, String> rowData) {
		// TODO Auto-generated method stub
		if(!rowData.containsKey("UNIT_ID")){
			rowData.put("UNIT_ID",user.getUnitId());
		}
		if(!rowData.containsKey("DISC_ID")){
			rowData.put("DISC_ID", user.getDiscId());
		}
		if(!rowData.containsKey("INSERT_USER_ID")){
			rowData.put("INSERT_USER_ID", user.getId());// 插入userID*/
		}
		if(!rowData.containsKey("MODIFY_USER_ID")){
			rowData.put("MODIFY_USER_ID", user.getId());// 修改userid*/
		}
		String userType = user.getUserType();
		switch(userType){
			case "4"://教师用户
				if(!rowData.containsKey("CGSSR_ID")){
					rowData.put("CGSSR_ID", user.getId());
				}
				if(!rowData.containsKey("CGSSR_LOGINID")){
					rowData.put("CGSSR_LOGINID", user.getLoginId());
				}
				if(!rowData.containsKey("CGSSR_NAME")){
					rowData.put("CGSSR_NAME",user.getName());
				}
				break;
			case "3"://学科用户
				if(rowData.containsKey("CGSSR_LOGINID")){
					String loginId = rowData.get("CGSSR_LOGINID");
					User user2 = userService.getUserByLoginId(loginId);//通过loginId从数据库获取User
					rowData.put("CGSSR_ID", user2.getId());
					rowData.put("CGSSR_NAME",user2.getName());
				}
				break;
		}
		return super.newRow(entityId, rowData);
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public Boolean deleteTRow(String entityId,String pkValue,String userId,String seqNo) {
		// TODO Auto-generated method stub
		Map<String, Object> params= new HashMap<String,Object>(0);
		params.put("CGSSR_ID", userId);
		if(!"-1".equals(seqNo)){
			super.updateSeqNo(entityId, seqNo, params);
		}
		super.deleteRow(entityId, pkValue);
		return true;
	}

	@Override
	public JqgridVM getJqGridTData(String entityId, String unitId,
			String discId, List<String> userIds, int pageIndex, int pageSize,
			String orderPropName, boolean asc) {
		// TODO Auto-generated method stub
		if(userIds==null||userIds.size()==0){
			userIds = teachDiscService.getTeachIds(unitId, discId);
		}
		List<Object> inParamsObjects= new ArrayList<Object>(userIds);
		SearchGroup newSearchGroup = new SearchGroup("and",new SearchRule("CGSSR_ID",inParamsObjects.toArray(),SearchType.STRING));
		return super.getJqGridData(entityId,null,newSearchGroup.toString(), pageIndex, pageSize, orderPropName, asc);
	}
	
	@Override
	public JqgridVM getJqGridSearchData(String entityId, String unitId,
			String discId, SearchGroup searchGroup, int pageIndex, int pageSize,
			String orderPropName, boolean asc) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String,Object>(0);
		if(!params.containsKey("UNIT_ID")&&StringUtils.isNotBlank(unitId)){
			params.put("UNIT_ID", unitId);
		}
		if(!params.containsKey("DISC_ID")&&StringUtils.isNotBlank(discId)){
			params.put("DISC_ID", discId);
		}
		return super.getJqGridData(entityId, params, searchGroup.toString(), pageIndex, pageSize, orderPropName, asc);
	}

	@Override
	public JqgridVM getJqGridTSearchData(String entityId, String unitId,
			String discId, List<String> userIds, SearchGroup searchGroup,
			int pageIndex, int pageSize, String orderPropName, boolean asc) {
		// TODO Auto-generated method stub
		if(userIds==null||userIds.size()==0){
			userIds = teachDiscService.getTeachIds(unitId, discId);
		}
		List<Object> inParamsObjects= new ArrayList<Object>(userIds);
		SearchGroup newSearchGroup = new SearchGroup("and",new SearchRule("CGSSR_ID",inParamsObjects.toArray(),SearchType.STRING));
		newSearchGroup.addSubGroup(searchGroup);
		Map params = new HashMap<String,Object>(0);
		return super.getJqGridData(entityId,params,newSearchGroup.toString(), pageIndex, pageSize, orderPropName, asc);
	
	}

	@Override
	public Map<String, String> getOriginEntity(String tarEntityId,String discId) {
		// TODO Auto-generated method stub
		String catId = discCategoryDao.getCatByDiscId(discId);
		List<MetaEntityMap> metaEntityMaps = metaEntityMapService.getEntityMaps(null, tarEntityId, catId);
		if(metaEntityMaps!=null&&metaEntityMaps.size()>0){
			Map<String, String> origEntitys = new HashMap<String,String>(0);
			for(MetaEntityMap mEntityMap: metaEntityMaps){
				String entityId = mEntityMap.getOriginEntity().getId();
				String entityName = mEntityMap.getOriginEntity().getChsName();
				origEntitys.put(entityId,entityName);
			}
			return origEntitys;
		}
		return null;
	}

	@Override
	public String importNewRow(String oriEntityId, String tarEntityId,
			String pkValue,String importType,String unitId, String discId, String userId) {
		// TODO Auto-generated method stub
		String catId = discCategoryDao.getCatByDiscId(discId);
		Map<String, Object> params = new HashMap<String,Object>(0);
		params.put("ID", pkValue);
		List<Map<String, String>> resDatas = super.getData(oriEntityId, params, "",false, 0, 0);
		Map<String, String> resData = new HashMap<String,String>(0);
		if(resDatas!=null&&resDatas.size()>0){
			resData= resDatas.get(0);
		}
		MetaEntityMap metaEntityMap = metaEntityMapService.getEntityMap(oriEntityId, tarEntityId,catId);
		/*Set<MetaAttributeMap> metaAttrMaps = metaEntityMap.getAttributeMaps();
		Map<String , String> tarData = new HashMap<String, String>(0);
		for(MetaAttributeMap attrMap:metaAttrMaps){
			MetaAttribute oriAttr = attrMap.getOriginAttr();
			MetaAttribute tarAttr = attrMap.getTargetAttr();
			if(resData.containsKey(oriAttr.getName())){
				tarData.put(tarAttr.getName(),resData.get(tarAttr.getName()));
			}
		}*/
		Map<String , String> tarData = metaEntityMapService.convertData(metaEntityMap, resData);
		
		if(StringUtils.isNotBlank(unitId)){
			tarData.put("UNIT_ID", unitId);	
		}
		if(StringUtils.isNotBlank(discId)){
			tarData.put("DISC_ID", discId);
		}
		if(StringUtils.isNotBlank(unitId)){
			tarData.put("INSERT_USER_ID", userId);
			tarData.put("MODIFY_USER_ID", userId);
		}
		if(StringUtils.isNotBlank(importType)){
			tarData.put("ORI_TYPE", importType);
		}
		if(StringUtils.isNotBlank(pkValue)){
			tarData.put("ORI_ID", pkValue);
		}
		int seqNo = getCount(tarEntityId,unitId,discId)+1;
		tarData.put("SEQ_NO", String.valueOf(seqNo));
		tarData.put("ID", GUID.get());
		MetaEntity entity = metaEntityService.getById(tarEntityId);
		Set<MetaAttribute> attributes = entity.getAttributes();
		for(MetaAttribute attr:attributes){
			if(!tarData.containsKey(attr.getName())){
				attr.addDefaultValue(tarData);
			}
		}
		String newId =newRow(tarEntityId, unitId, discId, userId, tarData);
		if(StringUtils.isNotBlank(newId)){
			return pkValue;
		}else{
			return null;
		}
		//return super.importNewRow(oriEntityId, tarEntityId, catId, pkValue, rowData);
	}

	@Override
	public JqgridVM getCollectDataDetail(String entityId, String itemId,
			String sidx, boolean order_flag, int page, int pageSize) {
		// TODO Auto-generated method stub
		MetaEntity entity = metaEntityService.getById(entityId);

		List<Map<String, String>> list = new LinkedList<Map<String, String>>();

		Map<String, Object> params = new HashMap<String, Object>();

		params.put(entity.getPkName(), itemId);

		List<Map<String, String>> preList = super.getData(entityId,
				params, sidx, order_flag, page, pageSize);
		list.addAll(preList);
		int count = list.size();
		return new JqgridVM(page, count, pageSize, list);
	}

	public TeachDiscService getTeachDiscService() {
		return teachDiscService;
	}

	public void setTeachDiscService(TeachDiscService teachDiscService) {
		this.teachDiscService = teachDiscService;
	}

	@Override
	public JqgridVM viewTSearchData(String entityId,String tarEntityId,String unitId,
			String discId, List<String> userIds, SearchGroup searchGroup,
			int pageIndex, int pageSize, String orderPropName, boolean asc) {
		// TODO Auto-generated method stub
		JqgridVM jqgridVM = getJqGridTSearchData(entityId, unitId, discId, userIds, searchGroup, pageIndex, pageSize, orderPropName, asc);
		List<String> pkvalues = getOriPkValues(tarEntityId,unitId,discId,"0");
		for(Map rowData:jqgridVM.getRows()){
			if(pkvalues.contains(rowData.get("ID"))){
				rowData.put("IS_SELECTED", "true");
			}else{
				rowData.put("IS_SELECTED", "false");
			}
		}
		return jqgridVM;
	}
	private List<String> getOriPkValues(String entityId,String unitId,String discId,String oriType){
		List<String> columns = new ArrayList<String>(0);
		columns.add("ORI_ID");
		Map<String, Object> params = new HashMap<String,Object>(0);
		if(StringUtils.isNotBlank(unitId)){
			params.put("UNIT_ID", unitId);
		}
		if(StringUtils.isNotBlank(discId)){
			params.put("DISC_ID", discId);
		}
		if(StringUtils.isNotBlank(oriType)){
			params.put("ORI_TYPE", oriType);
		}
		List<String[]> valueList = super.getData(entityId,columns,params,null,true, 0, 0);
		List<String> pkValues = new ArrayList<String>(0);
		for(String[] values:valueList){
			pkValues.add(values[0]);
		}
		return pkValues;
	}
	
	@Override
	public boolean isExistItem(String entityId,String pkValue){
		Map<String, Object> params= new HashMap<String,Object>(0);
		params.put("ORI_ID", pkValue);
		if(super.getCount(entityId, params)>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public JqgridVM getCollectDataDetail(String entityId, List<String> itemIds,
			String sidx, boolean order_flag, int page, int pageSize) {
		// TODO Auto-generated method stub
		MetaEntity entity = metaEntityService.getById(entityId);

		List<Map<String, String>> list = new LinkedList<Map<String, String>>();

		for(String itemId:itemIds){
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put(entity.getPkName(), itemId);

			List<Map<String, String>> preList = super.getData(entityId,
					params, sidx, order_flag, page, pageSize);
			list.addAll(preList);
		}
		int count = list.size();
		return new JqgridVM(page, count, pageSize, list);
	}
	@Override
	public String uploadDiscIntroduceFile(String entityId, String unitId,
			String discId, String userId, Map<String, String> rowData,
			String attachId) {
		// TODO Auto-generated method stub
		String pk=newRow(entityId, unitId, discId, userId, rowData);
		int count=evalFlowService.updateEvalByUnitIdAndDiscId(unitId, discId, attachId);
		if( pk!=null && count==1 ) return "success";
		return null;
	}
	@Override
	public String updateDiscIntroduceFile(String entityId, String unitId,
			String discId, String userId, Map<String, String> rowData,String pkValue,
			String attachId) {
		// TODO Auto-generated method stub
		int count1=updateRow(entityId, userId, pkValue, unitId,discId,rowData);
		int count2=evalFlowService.updateEvalByUnitIdAndDiscId(unitId, discId, attachId);
		if( count1==1 && count2==1 ) return "success";
		return null;
	}
	@Override
	public boolean initOneCollectData(String entityId, String unitId,
			String discId,String userId) {
		// TODO Auto-generated method stub
		MetaEntityDomain entityDomain =  metaEntityService.getEntityDomain(entityId, "C");
		ViewType viewType = ViewType.getViewType(entityDomain.getFormType());
		if(viewType.equals(ViewType.INITJQGRID)){
			if(getCount(entityId, unitId, discId)==0){
				String rules = entityDomain.getInitRule();
				List<Map<String, String>> rowDatas = CollectTools.initRulesUtil(rules);
				for(Map<String, String> rowData : rowDatas){
					newRow(entityId, unitId, discId, userId, rowData);
				}
			}
		}
		return true;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public boolean clearOneCollectItem(String entityId, String unitId,
			String discId, String pkValue, String userId, String seqNo) {
		// TODO Auto-generated method stub
		MetaEntityDomain entityDomain =  metaEntityService.getEntityDomain(entityId, "C");
		List<Map<String, String>> rowDatas = CollectTools.initRulesUtil(entityDomain.getInitRule());
		Map<String, String> initRow = new HashMap<String,String>(0);
		for(Map<String, String> rowData: rowDatas){
			if(seqNo.equals(rowData.get("SEQ_NO"))){
				initRow = rowData;
			}
		}
		deleteRow(entityId, pkValue);
		newRow(entityId, unitId, discId, userId, initRow);
		return true;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public boolean initOnePublicData(String entity, String userId) {
		// TODO Auto-generated method stub
		MetaEntityDomain entityDomain =  metaEntityService.getEntityDomain(entity, "G");
		ViewType viewType = ViewType.getViewType(entityDomain.getFormType());
		if(viewType.equals(ViewType.INITJQGRID)){
			if(getCount(entity, null, null)==0){
				String rules = entityDomain.getInitRule();
				List<Map<String, String>> rowDatas = CollectTools.initRulesUtil(rules);
				for(Map<String, String> rowData : rowDatas){
					newRow(entity, null, null, userId, rowData);
				}
			}
		}
		return true;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public int importPubData(String entityId, String unitId, String discId,
			String userId) {
		//先删除原来的数据
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("UNIT_ID", unitId);
		params.put("DISC_ID", discId);
		super.deleteRow(entityId, params);
		//首先获得实体映射信息
		String catId = discId;//学科门类？？		
		int count = 0;
		MetaEntityMap map = metaEntityMapService.getEntityMap(entityId, catId);
		//首先获取公共库数据
		MetaAttribute attr1 = metaEntityMapService.getOrigMapAttr(map, "UNIT_ID");
		MetaAttribute attr2 = metaEntityMapService.getOrigMapAttr(map, "DISC_ID");
		params.clear();
		params.put(attr1.getName(), unitId);
		params.put(attr2.getName(), discId);
		List<Map<String, String>> srcDataList = 
				super.getData(map.getOriginEntity().getId(), params, "", true, 0, 0);
		for(Map<String, String> srcData: srcDataList)
		{
			Map<String, String> destData = metaEntityMapService.convertData(map, srcData);
			if(srcData.containsKey("ID"))
			{
				destData.put("SJLY", "G");
				destData.put("LYZJ", srcData.get("ID"));
			}
			if(!destData.containsKey("SEQ_NO"))
			{
				destData.put("SEQ_NO", String.format("%d", count+1));
			}
			String ID = newRow(entityId, unitId, discId, userId, destData);
			if(StringUtils.isNotBlank(ID)) count ++;
		}
		return count;
	}
}
