package com.dsep.service.dsepmeta.dsepmetas.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.dsepmeta.check.SimilarityEntryDao;
import com.dsep.dao.dsepmeta.check.SimilarityResultDao;
import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.SimilarityResult;
import com.dsep.service.dsepmeta.MetaOper;
import com.dsep.service.dsepmeta.dsepmetas.DMSimilarityCheckService;
import com.dsep.util.datacheck.CheckSimByCompositive;
import com.meta.domain.MetaAttrDomain;
import com.meta.domain.MetaEntityDomain;
import com.meta.entity.MetaEntity;


public class DMSimilarityCheckServiceImpl extends MetaOper implements
		DMSimilarityCheckService {

	private SimilarityEntryDao similarityEntryDao;
	private SimilarityResultDao similarityResultDao;

	public SimilarityEntryDao getSimilarityEntryDao() {
		return similarityEntryDao;
	}

	public void setSimilarityEntryDao(SimilarityEntryDao similarityEntryDao) {
		this.similarityEntryDao = similarityEntryDao;
	}

	public SimilarityResultDao getSimilarityResultDao() {
		return similarityResultDao;
	}

	public void setSimilarityResultDao(SimilarityResultDao similarityResultDao) {
		this.similarityResultDao = similarityResultDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void startCheck(String userId, String unitId, String discId,
			MetaEntity me) {

		String entityId = me.getId();
		
		// 查重前先置查重的状态为为通过
		similarityEntryDao.initSimilarityFlag(userId, entityId);
				
		// 删除用户上次的该采集项查重结果
		similarityResultDao.deleteResultByUser(userId, entityId);


		
		String entityChsName = me.getChsName();
		String pk = me.getPkName();
		// 获取查重字段
		Map<String, String> checkFields = getSimilarityFields(entityId);
		if (checkFields.isEmpty())	return;
		// 获取关键字段
		String keyFiled = me.getIdAttr();
		// 合成要拿的数据字段
		List<String> fieldsList = new LinkedList<String>();
		fieldsList.add(pk);
		for (String key : checkFields.keySet()) {
			fieldsList.add(key);
		}
		if (!fieldsList.contains(keyFiled)) {
			fieldsList.add(keyFiled);
		}
		fieldsList.add("UNIT_ID");
		fieldsList.add("DISC_ID");
		fieldsList.add("SEQ_NO");

		// 设置条件查询，学校学科与中心取数据范围由此区别
		Map<String, Object> params = new HashMap<String, Object>();

		if (StringUtils.isNotBlank(discId) && StringUtils.isNotBlank(unitId)) {
			// 学科用户
			params.put("DISC_ID", discId);
			params.put("UNIT_ID", unitId);
		} else if (!StringUtils.isNotBlank(discId)
				&& StringUtils.isNotBlank(unitId)) {
			// 学校用户
			params.put("UNIT_ID", unitId);
		}// 中心用户取所有数据，所以不作处理

		// 取查重数据源
		List<Map<String, Object>> originData = super.getDataObject(entityId,
				fieldsList, params, "", true, 0, 0);
		
		
		// 取要比对的数据
		List<String> objectFieldsList = new LinkedList<String>();
		objectFieldsList.add(pk);
		for (String key : checkFields.keySet()) {
			objectFieldsList.add(key);
		}
		// 此处可以设置目标数据的取值范围
		Map<String, Object> objectParams = new HashMap<String, Object>();

		//学科与学校都只查校内，中心查全部
		if (StringUtils.isNotBlank(unitId)) {
			objectParams.put("UNIT_ID", unitId);
		} 
		
		// 取比对数据源
		List<Map<String, Object>> objectData = super.getDataObject(entityId,
				objectFieldsList, objectParams, "", true, 0, 0);
		String simIdsStr = "";

		System.out.println("Start similarity check time:" + new Date());
		int count = 0;
		// 循环比对
		for (Map<String, Object> m : originData) {// 针对数据源循环比对

			count++;

			String originValue = "", objectValue = "";
			List<String> simIds = new LinkedList<String>();

			for (String key : checkFields.keySet()) {// 针对每一个查重字段逐个比较

				for (Map<String, Object> n : objectData) {// 循环要比对的数据

					if (m.get(pk).equals(n.get(pk)))
						continue;
					//先进行分词
					String word_key = key+"_words";
					List<String> originWords;
					List<String> destWords;
					originValue = m.get(key).toString();
					if(!m.containsKey(word_key))
					{
						originWords =CheckSimByCompositive.getWords(originValue);
						m.put(word_key, originWords);
					}else
					{
						originWords = (List<String>) m.get(word_key);
					}
					objectValue = n.get(key).toString();
					if(!n.containsKey(word_key))
					{
						destWords =CheckSimByCompositive.getWords(objectValue);
						n.put(word_key, destWords);
					}else{
						destWords = (List<String>) n.get(word_key);
					}
					
					double simValue = (double) 0.8 * CheckSimByCompositive.getWordsSim(originWords, destWords) + 
							0.1	* CheckSimByCompositive.getLengthSim(originValue, objectValue) 
							+ 0.1 * CheckSimByCompositive.getNiXuSim(originWords, destWords);
					if(simValue > 0.9)
					{
						simIds.add(n.get(pk).toString());
					}
				}

				if (!simIds.isEmpty()) {
					simIdsStr = "";
					simIdsStr = changeListToString(simIds);
					// 存结果表
					SimilarityResult sr = new SimilarityResult();
					sr.setEntityId(entityId);
					sr.setEntityChsName(entityChsName);

					sr.setDiscId(m.get("DISC_ID").toString());
					sr.setUnitId(m.get("UNIT_ID").toString());

					sr.setDataId(m.get(pk).toString());
					sr.setDataValue(originValue);

					sr.setField(key);
					sr.setFieldName(checkFields.get(key));

					sr.setKeyValue(m.get(keyFiled).toString());
					sr.setUserId(userId);
					sr.setSeqNo(Integer.parseInt(m.get("SEQ_NO").toString()));

					sr.setSimilarityIds(simIdsStr);

					similarityResultDao.save(sr);
				}
			}
			//
			if (count % 100 == 0) {
				System.out.println(count + " items has checked, now time is:"
						+ new Date());
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void startCheck(String userId, String unitId, String discId,
			String entityId) {
		
		//获取查重采集项实体
		MetaEntity me = metaEntityService.getById(entityId);
		
		// 查重前先置查重的状态为为通过
		similarityEntryDao.initSimilarityFlag(userId, entityId);
		
		// 删除用户上次的该采集项查重结果
		similarityResultDao.deleteResultByUser(userId, entityId);
		

		String entityChsName = me.getChsName();
		String pk = me.getPkName();
		// 获取查重字段
		Map<String, String> checkFields = getSimilarityFields(entityId);
		if (checkFields.isEmpty())	return;
		// 获取关键字段
		String keyFiled = me.getIdAttr();
		// 合成要拿的数据字段
		List<String> fieldsList = new LinkedList<String>();
		fieldsList.add(pk);
		for (String key : checkFields.keySet()) {
			fieldsList.add(key);
		}
		if (!fieldsList.contains(keyFiled)) {
			fieldsList.add(keyFiled);
		}
		fieldsList.add("UNIT_ID");
		fieldsList.add("DISC_ID");
		fieldsList.add("SEQ_NO");

		// 设置条件查询，学校学科与中心取数据范围由此区别
		Map<String, Object> params = new HashMap<String, Object>();

		if (StringUtils.isNotBlank(discId) && StringUtils.isNotBlank(unitId)) {
			// 学科用户
			params.put("DISC_ID", discId);
			params.put("UNIT_ID", unitId);
		} else if (!StringUtils.isNotBlank(discId)
				&& StringUtils.isNotBlank(unitId)) {
			// 学校用户
			params.put("UNIT_ID", unitId);
		}// 中心用户取所有数据，所以不作处理

		// 取查重数据源
		List<Map<String, Object>> originData = super.getDataObject(entityId,
				fieldsList, params, "", true, 0, 0);
		
		
		// 取要比对的数据
		List<String> objectFieldsList = new LinkedList<String>();
		objectFieldsList.add(pk);
		for (String key : checkFields.keySet()) {
			objectFieldsList.add(key);
		}
		// 此处可以设置目标数据的取值范围
		Map<String, Object> objectParams = new HashMap<String, Object>();

		//学科查校内，学校中心查全部
		if (StringUtils.isNotBlank(discId)) {
			objectParams.put("UNIT_ID", unitId);
		} 
		
		// 取比对数据源
		List<Map<String, Object>> objectData = super.getDataObject(entityId,
				objectFieldsList, objectParams, "", true, 0, 0);
		String simIdsStr = "";

		System.out.println("Start similarity check time:" + new Date());
		int count = 0;
		// 循环比对
		for (Map<String, Object> m : originData) {// 针对数据源循环比对

			count++;

			String originValue = "", objectValue = "";
			List<String> simIds = new LinkedList<String>();

			for (String key : checkFields.keySet()) {// 针对每一个查重字段逐个比较

				for (Map<String, Object> n : objectData) {// 循环要比对的数据

					if (m.get(pk).equals(n.get(pk)))
						continue;
					//先进行分词
					String word_key = key+"_words";
					List<String> originWords;
					List<String> destWords;
					originValue = m.get(key).toString();
					if(!m.containsKey(word_key))
					{
						originWords =CheckSimByCompositive.getWords(originValue);
						m.put(word_key, originWords);
					}else
					{
						originWords = (List<String>) m.get(word_key);
					}
					objectValue = n.get(key).toString();
					if(!n.containsKey(word_key))
					{
						destWords =CheckSimByCompositive.getWords(objectValue);
						n.put(word_key, destWords);
					}else{
						destWords = (List<String>) n.get(word_key);
					}
					
					double simValue = (double) 0.8 * CheckSimByCompositive.getWordsSim(originWords, destWords) + 
							0.1	* CheckSimByCompositive.getLengthSim(originValue, objectValue) 
							+ 0.1 * CheckSimByCompositive.getNiXuSim(originWords, destWords);
					if(simValue > 0.9)
					{
						simIds.add(n.get(pk).toString());
					}
				}

				if (!simIds.isEmpty()) {
					simIdsStr = "";
					simIdsStr = changeListToString(simIds);
					// 存结果表
					SimilarityResult sr = new SimilarityResult();
					sr.setEntityId(entityId);
					sr.setEntityChsName(entityChsName);

					sr.setDiscId(m.get("DISC_ID").toString());
					sr.setUnitId(m.get("UNIT_ID").toString());

					sr.setDataId(m.get(pk).toString());
					sr.setDataValue(originValue);

					sr.setField(key);
					sr.setFieldName(checkFields.get(key));

					sr.setKeyValue(m.get(keyFiled).toString());
					sr.setUserId(userId);
					sr.setSeqNo(Integer.parseInt(m.get("SEQ_NO").toString()));

					sr.setSimilarityIds(simIdsStr);

					similarityResultDao.save(sr);
				}
			}
			//
			if (count % 100 == 0) {
				System.out.println(count + " items has checked, now time is:"
						+ new Date());
			}
		}

	}

	@Override
	public List<Map<String, String>> getSimilarityGroupDetail(String entityId,
			String dataId, String simIds, String sidx, boolean order_flag,
			int page, int pageSize, String unitId) {

		MetaEntity entity = metaEntityService.getById(entityId);

		List<Map<String, String>> list = new LinkedList<Map<String, String>>();

		String[] simsWith = simIds.split(",");

		List<String> sims = new LinkedList<String>();

		sims.add(dataId);

		for (String sw : simsWith) {
			sims.add(sw);
		}

		if (sims.size() > 1) {

			for (String id : sims) {

				Map<String, Object> params = new HashMap<String, Object>();

				params.put(entity.getPkName(), id);
				
				List<Map<String, String>> preList = super.getData(entityId,
						params, sidx, order_flag, page, pageSize);

				Map<String, String> m = preList.get(0);
				
				String dataUnitId = m.get("UNIT_ID");
				
				//数据取出来后进行处理
				if(StringUtils.isNotBlank(unitId) && !unitId.equals(dataUnitId)){
					
					//设置非本校的要屏蔽的字段
					List<String> keySet = new LinkedList<String>();
					
					for(String key : m.keySet()){
						//只保留学校代码
						if(key.equals("UNIT_ID")) continue;
						else keySet.add(key);
					}
					
					for(String key : keySet){
						m.put(key, "");
					}
					
					m.put("DISC_ID", "他校数据不显示");
				}
				
				list.addAll(preList);
			}
		}

		return list;
	}

	/*********************************** 工具函数 ***************************************/
	/*
	 * 根据EntityId获取该Entity需要查重的字段与中文名
	 */
	private Map<String, String> getSimilarityFields(String entityId) {

		Map<String, String> checkFields = new LinkedHashMap<String, String>();
		// 获取EntityDomain
		MetaEntityDomain entityDomain = metaEntityService
				.getEntityDomain(entityId);
		for (MetaAttrDomain mad : entityDomain.getAttrDomains()) {
			if (mad.isSimCheck()) {
				checkFields.put(mad.getName(), mad.getChsName());
			}
		}
		return checkFields;
	}

	/*
	 * 将["1","2","3"]的数组转换为"1,2,3"
	 */
	private String changeListToString(List<String> simIds) {
		String str = "";
		for (int i = 0; i < simIds.size(); i++) {
			if (i == 0) {
				str += simIds.get(i);
			} else {
				str += "," + simIds.get(i);
			}
		}
		return str;
	}

	/**
	 * 返回要导出查重数据文件的表头
	 * 
	 * @param user
	 * @param entityIDs
	 * @return
	 */
	public List<List<String>> getExcelTitles(User user, List<String> entityIDs) {

		List<List<String>> result = new LinkedList<List<String>>();

		if (entityIDs.size() == 0)
			return result;

		for (int i = 0; i < entityIDs.size(); i++) {
			List<String> title = new LinkedList<String>();
			String entityId = entityIDs.get(i);
			MetaEntityDomain entityDomain = metaEntityService
					.getEntityDomain(entityId);
			List<String> showAttrList = getShowAttrList(entityId);
			List<MetaAttrDomain> attrDomainList = entityDomain.getAttrDomains();
			title.add("组别");
			for (int j = 0; j < attrDomainList.size(); j++) {
				MetaAttrDomain mad = attrDomainList.get(j);
				if (showAttrList.contains(mad.getName()))
					title.add(mad.getChsName());

			}
			result.add(title);
		}

		return result;
	}

	public List<String> getShowAttrList(String entityId) {
		List<String> result = new LinkedList<String>();
		MetaEntityDomain entityDomain = metaEntityService
				.getEntityDomain(entityId);
		List<MetaAttrDomain> attrDomainList = entityDomain.getAttrDomains();
		for (int i = 0; i < attrDomainList.size(); i++) {
			MetaAttrDomain mad = attrDomainList.get(i);
			if (mad.isHidden() == false) // 如果这项不需要隐藏
				result.add(mad.getName());
		}
		result.add("UNIT_ID");
		result.add("DISC_ID");
		return result;
	}

}
