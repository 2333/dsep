package com.dsep.service.datacalculate.FactorAndWeight.impl;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.cache.annotation.Cacheable;

import com.dsep.dao.dsepmeta.base.DiscCategoryDao;
import com.dsep.dao.dsepmeta.calculate.IndexFactorDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalIndicWtDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalResultDao;
import com.dsep.domain.dsepmeta.calculaterule.IndicWtDomain;
import com.dsep.entity.dsepmeta.ConvFactorValue;
import com.dsep.entity.dsepmeta.IndexMap;
import com.dsep.entity.expert.EvalIndicWt;
import com.dsep.entity.expert.EvalResult;
import com.dsep.service.datacalculate.FactorAndWeight.XmlCache;
import com.dsep.service.datacalculate.FactorAndWeight.IndexFactorService;
import com.dsep.service.dsepmeta.dsepmetas.DMDiscIndexService;
import com.dsep.util.Dictionaries;
import com.dsep.util.FileOperate;
import com.dsep.util.GUID;
import com.dsep.util.JsonConvertor;
import com.dsep.util.datacalculate.ReadXml;
import com.dsep.util.expert.eval.QType;
import com.dsep.util.export.briefsheet.ResourceLoader;
import com.meta.entity.MetaEntity;
import com.meta.service.MetaEntityService;
import com.meta.service.sql.SqlBuilder;
import com.meta.service.sql.SqlExecutor;

public class IndexFactorServiceImpl implements IndexFactorService {
	
	DiscCategoryDao discCategoryDao;
	IndexFactorDao indexFactorDao;
	DMDiscIndexService discIndexService;
	private EvalResultDao evalResultDao;
	private EvalIndicWtDao evalIndicWtDao;
	private SqlBuilder sqlBuilder;
	private SqlExecutor sqlExecutor;
	private MetaEntityService metaEntityService;
	private XmlCache xmlCache;
	
	public XmlCache getXmlCache() {
		return xmlCache;
	}

	public void setXmlCache(XmlCache xmlCache) {
		this.xmlCache = xmlCache;
	}

	public EvalIndicWtDao getEvalIndicWtDao() {
		return evalIndicWtDao;
	}

	public void setEvalIndicWtDao(EvalIndicWtDao evalIndicWtDao) {
		this.evalIndicWtDao = evalIndicWtDao;
	}

	public EvalResultDao getEvalResultDao() {
		return evalResultDao;
	}

	public void setEvalResultDao(EvalResultDao evalResultDao) {
		this.evalResultDao = evalResultDao;
	}

	public IndexFactorDao getIndexFactorDao() {
		return indexFactorDao;
	}

	public void setIndexFactorDao(IndexFactorDao indexFactorDao) {
		this.indexFactorDao = indexFactorDao;
	}

	public DMDiscIndexService getDiscIndexService() {
		return discIndexService;
	}

	public void setDiscIndexService(DMDiscIndexService discIndexService) {
		this.discIndexService = discIndexService;
	}
	
	public DiscCategoryDao getDiscCategoryDao() {
		return discCategoryDao;
	}

	public void setDiscCategoryDao(DiscCategoryDao discCategoryDao) {
		this.discCategoryDao = discCategoryDao;
	}

	public SqlBuilder getSqlBuilder() {
		return sqlBuilder;
	}

	public void setSqlBuilder(SqlBuilder sqlBuilder) {
		this.sqlBuilder = sqlBuilder;
	}

	public SqlExecutor getSqlExecutor() {
		return sqlExecutor;
	}

	public void setSqlExecutor(SqlExecutor sqlExecutor) {
		this.sqlExecutor = sqlExecutor;
	}

	public MetaEntityService getMetaEntityService() {
		return metaEntityService;
	}

	public void setMetaEntityService(MetaEntityService metaEntityService) {
		this.metaEntityService = metaEntityService;
	}

	@Override
	public Map<String, Double> getIndexFactor(String indexId, String discId) {
		String catId = discCategoryDao.getCatByDiscId(discId);
		String indexName = "";
		//此处以后换调用接口来实现
		List<IndexMap> indexMap = discIndexService.getIndexMapsByDiscId(discId);
		for(IndexMap index : indexMap){
			if(index.getId().equals(indexId)){
				indexName = index.getName();
			}
			break;
		}
		
		Map<String,Double> factor = indexFactorDao.getIndexFactor(indexName, catId);	
		return factor;
	}
	
	@Override
	public String exportFactorExcel(String discId,String rootPath) throws Exception{
		List<IndexMap> indexs = discIndexService.getIndexMapsByDiscId(discId);
		if(indexs.size()==0)
			throw new Exception("未获得该学科的指标体系");
		List<IndexMap> lastIndexs = new ArrayList<IndexMap>();
		for(IndexMap e:indexs){
			if(e.getIndexLevel()==3)
				lastIndexs.add(e);
		}
		//获得折算系数表的元数据信息
		MetaEntity entity = metaEntityService.getById("E2013O003");
		List<String> attrs = new ArrayList<String>();
		attrs.add("INDEX_ID");
		attrs.add("CONTENT");
		attrs.add("AVG_VALUE");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("DISC_ID", discId);
		sqlBuilder.buildSingleSelectSql(entity, attrs, params, "CONTENT", true);
		List<Map<String,String>> list = sqlExecutor.execQuery(sqlBuilder, 0, 999);
		if(!StringUtils.isNotBlank(list.get(0).get("AVG_VALUE")))
			throw new Exception("该学科未未计算观测点系数");
		List<String[]> link = new LinkedList<String[]>();
		for(Map<String,String> e:list){
			String[] result = new String[4];
			int i = 0;
			for(String attr:attrs){
				String temp;
				if(e.get(attr).substring(0,1).equals("."))
					temp = "0" + e.get(attr); 
				else temp = e.get(attr);
				result[i++] = temp;
				System.out.println(temp);
			}
			for(IndexMap e2:lastIndexs){
				if(e2.getId().equals(result[0])){
					for(int j=result.length-1;j>1;j--)
						result[j] = result[j-1];
					result[1] = e2.getName();
				}
			}
			link.add(result);
		}
		List<List<String[]>> rowStrings = new ArrayList<List<String[]>>();
		rowStrings.add(link);
		List<String> sheetName = new ArrayList<String>();
		sheetName.add(Dictionaries.getDisciplineName(discId) + "观测点系数");
		List<List<String>> attrDomain = new ArrayList<List<String>>();
		List<String> attrNames = new ArrayList<String>();
		attrNames.add("末级指标项ID");
		attrNames.add("末级指标项名称");
		attrNames.add("观测点系数名称");
		attrNames.add("观测点系数值");
		attrDomain.add(attrNames);
		Map<String, String> excelPathMap = FileOperate.exportExcel(attrDomain, rowStrings, sheetName, rootPath, "excel");
		return JsonConvertor.obj2JSON(excelPathMap);
	}
	
	/*@Override
	public void calculateIndicWtByCat(String catId){
		List<String> discIds = discCategoryDao.getDiscByCatId(catId);
		//获得“末级指标项——折算系数”的映射
		Map<String,List<String>> allIndicWtIds = getIndicWtIds(catId,"normal");
		Set<String> keySet = allIndicWtIds.keySet();
		List<IndicWtDomain> allDomains = new ArrayList<IndicWtDomain>();
		for(String discId:discIds){
			List<IndicWtDomain> domains = getAndConvertToIndicWtDomain(discId,catId);
			allDomains.addAll(domains);
		}
		for(String key:keySet){
			for(String discId:discIds)
				calculateAvgValue(discId,key,allIndicWtIds.get(key),allDomains);
		}
		
	}*/
	
	@Override
	public void calculateIndicWtByDisc(String discId) throws Exception{
		String catId = discCategoryDao.getCatByDiscId(discId);
		Map<String,List<String>> allIndicWtIds = new HashMap<String,List<String>>();
		try{
			allIndicWtIds = getIndicWtIds(catId,"normal");
		}catch(DocumentException e){
			throw new Exception("xml文件读取错误");
		}
		if(allIndicWtIds.size()==0)
			throw new Exception("未获得该学科的观测点信息");
		Set<String> keySet = allIndicWtIds.keySet();
		//获得同一学科的所有折算系数的打分并封装成domain
		List<IndicWtDomain> domains = getAndConvertToIndicWtDomain(discId,catId);
		Map<String,Double> m = new HashMap<String,Double>();
		for(String key:keySet){
			for(String indicWtId:allIndicWtIds.get(key)){
				m = calculateAvgValue(discId,indicWtId,domains);
				if(m.size()>0){
					saveOrUpdateFactor(m,discId,key);
				}
			}
		}
	}
	
	@Override
	public void calculateAwardWtByDisc(String discId) throws Exception{
		String catId = discCategoryDao.getCatByDiscId(discId);
		//确定特、一、二、三等奖的权重比例
		Map<String,Double> levelWt = confirmLevelWt(discId);
		if(levelWt==null)
			throw new Exception("无法获取获奖等级评估信息");
		//确定国家级和省部级奖项的权重比例
		Map<String,Double> NPWt = confirmNationalAndProvincialWt(discId);
		if(NPWt==null)
			throw new Exception("无法获得国家、省部级获奖等级评估信息");
		Map<String,List<String>> allIndicWtIds = new HashMap<String,List<String>>();
		try{
			allIndicWtIds = getIndicWtIds(catId,"award");
		}catch(DocumentException e){
			throw new Exception("xml文件读取错误");
		}
		if(allIndicWtIds.size()==0)
			throw new Exception("未获得该学科的观测点信息");
		Set<String> keySet = allIndicWtIds.keySet();
		//获得同一学科的所有折算系数的打分并封装成domain
		List<IndicWtDomain> domains = getAndConvertToIndicWtDomain(discId,catId);
		Map<String,Double> m = new HashMap<String,Double>();
		Map<String,Double> m2 = new HashMap<String,Double>();
		for(String k:keySet){
			for(String indicWtId:allIndicWtIds.get(k)){
				//计算出具体奖项类型的权重
				m = calculateAvgValue(discId,indicWtId,domains);
				if(m.size()>0)
					m2 = getAwardMap(levelWt,NPWt,m);
				saveOrUpdateFactor(m2,discId,k);
			}
		}
	}
	
	//将奖项类型权重和其相应的获奖等级（例如国家级、特等奖）的权重相乘获得最终具体奖项的权重
	private Map<String,Double> getAwardMap(Map<String,Double> levelWt,
			Map<String,Double> NPWt,Map<String,Double> award){
		Map<String,Double> m = new HashMap<String,Double>();
		for(String NPName:NPWt.keySet()){
			for(String awardName:award.keySet()){
				for(String levelName:levelWt.keySet()){
					String finalAwardName = NPName + awardName + "&" + levelName;
					if(checkAwardName(finalAwardName))
						m.put(finalAwardName, NPWt.get(NPName)*award.get(awardName)
								*levelWt.get(levelName));
				}
			}
		}
		return m;
	}
	
	private boolean checkAwardName(String str){
		boolean flag = true;
		if(str.substring(0, 2).equals("国家")&&str.substring(2, 6).equals("其他奖项"))
				flag = false;
		return flag;	
	}
	
	private Map<String,Double> confirmNationalAndProvincialWt(String discId){
		Map<String,Double> m = new HashMap<String,Double>();
		List<EvalResult> list = evalResultDao.
				getSameDiscOrCatValue(discId, null, null, "ZSALL0202");
		double sumN = 0;
		double sumP = 0;
		if(list.size()>0){
			for(EvalResult e:list){
				String[] values = e.getEvalValue().split(",");
				sumN += Double.valueOf(values[0]);
				sumP += Double.valueOf(values[1]);
			}
			m.put("国家", sumN/list.size());
			m.put("省级", sumP/list.size());
		}
		return m;
	}
	
	private Map<String,Double> confirmLevelWt(String discId){
		Map<String,Double> m = new HashMap<String,Double>();
		//注意！此处indicWtId写死，待修改
		List<EvalResult> list = evalResultDao.
				getSameDiscOrCatValue(discId, null, null, "ZSALL0201");
		double sum0 = 0;
		double sum1 = 0;
		double sum2 = 0;
		double sum3 = 0;
		if(list.size()>0){
			for(EvalResult e:list){
				String[] values = e.getEvalValue().split(",");
				sum0 += Double.valueOf(values[0]);
				sum1 += Double.valueOf(values[1]);
				sum2 += Double.valueOf(values[2]);
				sum3 += Double.valueOf(values[3]);
			}
			m.put("特等奖", sum0/list.size());
			m.put("一等奖", sum1/list.size());
			m.put("二等奖", sum2/list.size());
			m.put("三等奖", sum3/list.size());
		}
		return m;
	}
	
	//获得指定门类的所有折算系数和末级指标的映射关系
	private Map<String,List<String>> getIndicWtIds(String catId,String type) throws DocumentException{
		Map<String,List<String>> m = new HashMap<String,List<String>>();
		String filePath = "template/factor.xml";
		List<String> attrs = new ArrayList<String>();
		List<String> catIds = new ArrayList<String>();
		attrs.add("Type");
		attrs.add("IndicWtId");
		attrs.add("IndexItemId");
		attrs.add("CatId");
		//注意！此处以后要补全所有门类信息！之后加载都会从缓存中加载
		catIds.add("JSJ");
		Map<String,Map<String,String>> m2 = xmlCache.getXmlData(filePath,catIds,attrs,"IndexItemId");
		for(String key:m2.keySet()){
			if(StringUtils.isNotBlank(m2.get(key).get("IndicWtId"))
					&&m2.get(key).get("Type").equals(type)
					&&m2.get(key).get("CatId").equals(catId)){
				String indicWtIdStr = m2.get(key).get("IndicWtId").replace(" ","");
				String[] temp = indicWtIdStr.split(",");
				List<String> indicIds = new ArrayList<String>();
				for(int i=0;i<temp.length;i++)
					indicIds.add(temp[i]);
				m.put(key, indicIds);
			}
		}
		return m;
	}
	
	//获得折算系数的打分结果
	private List<IndicWtDomain> getAndConvertToIndicWtDomain(String discId,String catId){
		List<EvalResult> indicWtResults = evalResultDao.
				getSameDiscIndicWtOrIndexValue(discId,null,QType.INDIC_WT.toInt());
		List<IndicWtDomain> indicWtDomains = new ArrayList<IndicWtDomain>();
		for(EvalResult e:indicWtResults){
			IndicWtDomain indic = new IndicWtDomain(e,e.getEvalQuestion().getDiscId(),
					catId,e.getEvalQuestion().getSubQuestionId());
			indicWtDomains.add(indic);
		}
		return indicWtDomains;
	}
	
	//获得各个折算系数的平均值
	private Map<String,Double> calculateAvgValue(String discId,String indicWtId,
			List<IndicWtDomain> indicWtDomains) throws Exception{
		Map<String,Double> result = new HashMap<String,Double>();
		List<String> items = new ArrayList<String>();
		List<Double> values = new ArrayList<Double>();
		EvalIndicWt indic = evalIndicWtDao.get(indicWtId);
		if(indic==null)
			throw new Exception("找不到相关的折算系数");
		//多个折算系数确定比例的计算（例如一、二、三等奖）
		if(!(indic.getEffectItemNum().equals("1"))){
			items.add(indic.getItem1());
			items.add(indic.getItem2());
			items.add(indic.getItem3());
			items.add(indic.getItem4());
			items.add(indic.getItem5());
			items.add(indic.getItem6());
			items.add(indic.getItem7());
			for(int i=0;i<items.size();i++){
				if(items.get(i)!=null)
					values.add(0.0);
			}
			int count = 0;
			for(IndicWtDomain e:indicWtDomains){
				if(e.getIndicWtId().equals(indicWtId)){
					String[] valueStr = e.getEvalResult().getEvalValue().split(",");
					for(int i=0;i<valueStr.length;i++){
						values.set(i, values.get(i)+Double.valueOf(valueStr[i]));
					}
					count ++;
				}
			}
			if(count!=0){
				for(int i=0;i<values.size();i++)
					result.put(items.get(i), values.get(i)/count);
			}
		}
		//一个折算系数或者多个规定相同权重的折算系数的计算
		if(indic.getEffectItemNum().equals("1")){
			String[] itemStr = indic.getItem1().split("、");
			for(int i=0;i<itemStr.length;i++)
				items.add(itemStr[i]);
			for(int i=0;i<items.size();i++){
				if(items.get(i)!=null)
					values.add(0.0);
			}
			int count = 0;
			for(IndicWtDomain e:indicWtDomains){
				if(e.getIndicWtId().equals(indicWtId)){
					String value = e.getEvalResult().getEvalValue();
					for(int i=0;i<items.size();i++){
						values.set(i, values.get(i)+Double.valueOf(value));
					}
					count ++;
				}
			}
			if(count!=0){
				for(int i=0;i<values.size();i++)
					result.put(items.get(i), values.get(i)/count);
			}
		}	
		return result;
	}
	
	private void saveOrUpdateFactor(Map<String,Double> m,String discId,String indexId){
		for(String indicWtName:m.keySet()){
			List<ConvFactorValue> factors = indexFactorDao.
					getCFValueByContentAndDisc(discId, indicWtName);
			if(factors.size()!=0){
				ConvFactorValue factor = factors.get(0);
				factor.setAvgValue(m.get(indicWtName));
				indexFactorDao.saveOrUpdate(factor);
			}else{
				ConvFactorValue factor = new ConvFactorValue();
				factor.setId(GUID.get());
				factor.setIndexId(indexId);
				factor.setDiscId(discId);
				factor.setContent(indicWtName);
				factor.setAvgValue(m.get(indicWtName));
			    indexFactorDao.save(factor);
			}
		}
	}

}
