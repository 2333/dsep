package com.dsep.service.dsepmeta.dsepmetas.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dsep.dao.dsepmeta.check.PubEntryDao;
import com.dsep.dao.dsepmeta.check.PubFieldConfigDao;
import com.dsep.dao.dsepmeta.check.PubResultDao;
import com.dsep.dao.dsepmeta.check.PubTableConfigDao;
import com.dsep.entity.dsepmeta.PubEntry;
import com.dsep.entity.dsepmeta.PubFieldConfig;
import com.dsep.entity.dsepmeta.PubTabConfig;
import com.dsep.service.dsepmeta.BaseDBOper;
import com.dsep.service.dsepmeta.MetaOper;
import com.dsep.service.dsepmeta.dsepmetas.DMPublicLibService;
import com.dsep.util.publiccheck.PublicLibCompare;
import com.meta.entity.MetaEntity;

public class DMPublicLibServiceImpl extends MetaOper 
	implements DMPublicLibService{
	
	private PubEntryDao pubEntryDao;
	private PubTableConfigDao pubTableConfigDao;
	private PubFieldConfigDao pubFieldConfigDao;
	private PubResultDao pubResultDao;
	private BaseDBOper baseDBOper;
	
	public BaseDBOper getBaseDBOper() {
		return baseDBOper;
	}
	public void setBaseDBOper(BaseDBOper baseDBOper) {
		this.baseDBOper = baseDBOper;
	}
	public PubEntryDao getPubEntryDao() {
		return pubEntryDao;
	}
	public void setPubEntryDao(PubEntryDao pubEntryDao) {
		this.pubEntryDao = pubEntryDao;
	}
	public PubTableConfigDao getPubTableConfigDao() {
		return pubTableConfigDao;
	}
	public void setPubTableConfigDao(PubTableConfigDao pubTableConfigDao) {
		this.pubTableConfigDao = pubTableConfigDao;
	}
	public PubFieldConfigDao getPubFieldConfigDao() {
		return pubFieldConfigDao;
	}
	public void setPubFieldConfigDao(PubFieldConfigDao pubFieldConfigDao) {
		this.pubFieldConfigDao = pubFieldConfigDao;
	}
	public PubResultDao getPubResultDao() {
		return pubResultDao;
	}
	public void setPubResultDao(PubResultDao pubResultDao) {
		this.pubResultDao = pubResultDao;
	}
	
	
	/**
	 * 开始比对
	 */
	@Override
	public void startPubCompare(String userId){
		List<PubEntry> pubs = pubEntryDao.getAll();
		for(PubEntry pe : pubs){
			pubLibCompare(pe.getPublibId(), userId);
		}
	}
	
	/**
	 * 针对每一个公共库进行比对
	 */
	private void pubLibCompare(String pubLibId, String userId){
		
		//获取公共库pubLibId的所有表映射和字段映射信息
		String tabConfigId = pubTableConfigDao.getId(pubLibId);//获取数据库中主键ID
		PubTabConfig pubConfig = pubTableConfigDao.get(tabConfigId);//根据主键ID利用Hibernate获取表配置信息
		
		//根据pubLibId获取本地实体entityId及过滤条件
		String entityId = pubConfig.getEntityId();
		String querySql = pubConfig.getQuerySql();
		
		//获取本地实体中文名称与标志字段
		MetaEntity me = metaEntityService.getById(entityId);
		String entityChsName = me.getChsName();
		String flagField = me.getIdAttr();
		
		//获取比对相应的字段映射列表
		Set<PubFieldConfig> fieldsConfig = pubConfig.getPubFieldConfigs();
		List<Map<String, String>> fieldsMap = getFieldsMap(fieldsConfig);
		
		//获取公共库pubLibId所对应的所有比对字段
		List<String> pubFields = getPubFields(fieldsConfig);
		//获取本地实体entityId所对应的所有比对字段
		List<String> entityFields = getEntityFields(fieldsConfig);
		
		//获取相应的本地库数据
		List<Map<String,String>> entityDatas = getEntityDatas(entityId, querySql, entityFields, flagField);
		//获取相应的公共库比对数据
		List<Map<String,String>> pubDatas = getPubDatas(pubLibId, pubFields, fieldsMap);
		//应用算法进行比对
		List<Map<String, String>> results = PublicLibCompare.check(pubDatas, entityDatas, fieldsMap, entityChsName, flagField);

		
		//比对过程中将结果数据存入结果表
		//先清空原来的比对结果
		pubResultDao.deletePubResultByPubId(pubLibId);
		pubResultDao.saveCompareResult(pubLibId, entityId, results);
		//更新入口表
		pubEntryDao.updatePubEntryState(pubLibId, userId);
	}
	
	
	
	/***************************工具函数************************************/
	
	private List<Map<String, String>> getFieldsMap(Set<PubFieldConfig> fieldsConfig){
		List<Map<String, String>> map = new LinkedList<Map<String, String>>();
		for(PubFieldConfig pfc:fieldsConfig){
			Map<String, String> m = new LinkedHashMap<String, String>();
			m.put("publibField", pfc.getPublibField());
			m.put("entityField", pfc.getEntityField());
			m.put("entityChsField", pfc.getEntityChsField());
			m.put("fieldType", pfc.getFieldType() + "");
			m.put("normRule", pfc.getNormRule());
			m.put("compareRule", pfc.getCompareRule());
			map.add(m);
		}
		return map;
	}
	
	
	/**
	 * 获取公共库比对字段列表
	 * @param pubFields
	 * @return
	 */
	private List<String> getPubFields(Set<PubFieldConfig> fieldsConfig){
		List<String> fields = new LinkedList<String>();
		for(PubFieldConfig pfc:fieldsConfig){
			if(fields.contains(pfc.getPublibField())) continue;
			fields.add(pfc.getPublibField());
		}
		return fields;
	}
	
	/**
	 * 获取本地库比对字段列表
	 * @param pubFields
	 * @return
	 */
	private List<String> getEntityFields(Set<PubFieldConfig> fieldsConfig){
		List<String> fields = new LinkedList<String>();
		for(PubFieldConfig pfc:fieldsConfig){
			if(fields.contains(pfc.getEntityField())) continue;
			fields.add(pfc.getEntityField());
		}
		return fields;
	}
	
	/**
	 * 获取本地数据库数据
	 * @param entityId 本地数据库实体Id
	 * @param entityFields 要比对的字段
	 * @return
	 */
	private List<Map<String,String>> getEntityDatas(String entityId, String querySql, List<String> entityFields, String flagField){
		
		int index = 0;
		entityFields.add(index, "ID");//添加主键，方便之后标识数据
		index++;
		entityFields.add(index, "SEQ_NO");//添加序号，方便之后标识数据
		
		if(!entityFields.contains("UNIT_ID")){
			index++;
			entityFields.add(index, "UNIT_ID");//添加学校ID，方便之后识别记录
		}
		
		if(!entityFields.contains("DISC_ID")){
			index++;
			entityFields.add(index, "DISC_ID");//添加学科ID，方便之后识别记录
		}
		
		if(!entityFields.contains(flagField)){
			index++;
			entityFields.add(index, flagField);//添加标志字段
		}
		
		List<Map<String,String>> result =  super.getRowData(entityId, entityFields , querySql);
		return result;
	}
	
	/**
	 * 获取公共库数据
	 * @param pubLibId 公共库数据表名字
	 * @param pubFields 要比对的字段
	 * @param fieldsMap
	 * @return
	 * @throws ClassNotFoundException 
	 */
	private List<Map<String,String>> getPubDatas(String pubLibId , List<String> pubFields, List<Map<String, String>> fieldsMap){
		List<Map<String,String>> result = new LinkedList<Map<String, String>>();
		//添加主键(暂时不用)
		//取数据
		result = baseDBOper.getRowData(pubLibId, (String[]) pubFields.toArray(new String[0]), null);
		return result;
	}
	
	@Override
	public List<Map<String, String>> getLocalDataDetail(String entityId, String itemId,
			String sidx, boolean order_flag, int page, int pageSize) {
		MetaEntity entity = metaEntityService.getById(entityId);

		List<Map<String, String>> list = new LinkedList<Map<String, String>>();

		Map<String, Object> params = new HashMap<String, Object>();

		params.put(entity.getPkName(), itemId);

		List<Map<String, String>> preList = super.getData(entityId,
				params, sidx, order_flag, page, pageSize);
		list.addAll(preList);
		return list;
	}

}
