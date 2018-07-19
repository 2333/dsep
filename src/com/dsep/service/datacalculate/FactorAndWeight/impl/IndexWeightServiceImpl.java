package com.dsep.service.datacalculate.FactorAndWeight.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dsep.dao.dsepmeta.base.DiscCategoryDao;
import com.dsep.dao.dsepmeta.calculate.IndexAvgWtDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalResultDao;
import com.dsep.domain.dsepmeta.calculaterule.IndexDomain;
import com.dsep.entity.dsepmeta.IndexAvgWt;
import com.dsep.entity.dsepmeta.IndexMap;
import com.dsep.entity.expert.EvalResult;
import com.dsep.service.datacalculate.FactorAndWeight.IndexWeightService;
import com.dsep.service.datacalculate.FactorAndWeight.XmlCache;
import com.dsep.service.dsepmeta.dsepmetas.DMDiscIndexService;
import com.dsep.util.Dictionaries;
import com.dsep.util.FileOperate;
import com.dsep.util.GUID;
import com.dsep.util.JsonConvertor;
import com.dsep.util.expert.eval.QType;
import com.dsep.util.export.briefsheet.ResourceLoader;
import com.meta.entity.MetaEntity;
import com.meta.service.MetaEntityService;
import com.meta.service.sql.SqlBuilder;
import com.meta.service.sql.SqlExecutor;

public class IndexWeightServiceImpl implements IndexWeightService{
	private EvalResultDao evalResultDao;
	private DiscCategoryDao discCategoryDao;
	private IndexAvgWtDao indexAvgWtDao;
	private SqlBuilder sqlBuilder;
	private SqlExecutor sqlExecutor;
	private MetaEntityService metaEntityService;
	private DMDiscIndexService discIndexService;
	private XmlCache xmlCache;
	
	public EvalResultDao getEvalResultDao() {
		return evalResultDao;
	}

	public void setEvalResultDao(EvalResultDao evalResultDao) {
		this.evalResultDao = evalResultDao;
	}
	
	public DiscCategoryDao getDiscCategoryDao() {
		return discCategoryDao;
	}

	public void setDiscCategoryDao(DiscCategoryDao discCategoryDao) {
		this.discCategoryDao = discCategoryDao;
	}

	public IndexAvgWtDao getIndexAvgWtDao() {
		return indexAvgWtDao;
	}

	public void setIndexAvgWtDao(IndexAvgWtDao indexAvgWtDao) {
		this.indexAvgWtDao = indexAvgWtDao;
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
	
	public DMDiscIndexService getDiscIndexService() {
		return discIndexService;
	}

	public void setDiscIndexService(DMDiscIndexService discIndexService) {
		this.discIndexService = discIndexService;
	}

	public MetaEntityService getMetaEntityService() {
		return metaEntityService;
	}

	public void setMetaEntityService(MetaEntityService metaEntityService) {
		this.metaEntityService = metaEntityService;
	}

	public XmlCache getXmlCache() {
		return xmlCache;
	}

	public void setXmlCache(XmlCache xmlCache) {
		this.xmlCache = xmlCache;
	}

	@Override
	public String exportWeightExcel(String discId,String rootPath) throws Exception{
		List<IndexMap> indexs = discIndexService.getIndexMapsByDiscId(discId);
		if(indexs.size()==0)
			throw new Exception("未获得该学科的指标体系");
		List<IndexMap> lastIndexs = new ArrayList<IndexMap>();
		for(IndexMap e:indexs){
			if(e.getIndexLevel()==3)
				lastIndexs.add(e);
		}
		MetaEntity entity = metaEntityService.getById("E2013O007");
		List<String> attrs = new ArrayList<String>();
		attrs.add("INDEX_ID");
		attrs.add("AVG_VALUE");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("DISC_ID", discId);
		sqlBuilder.buildSingleSelectSql(entity, attrs, params, "INDEX_ID", true);
		List<Map<String,String>> list = sqlExecutor.execQuery(sqlBuilder, 0, 999);
		if(!StringUtils.isNotBlank(list.get(0).get("AVG_VALUE")))
			throw new Exception("该学科未计算指标权重");
		List<String[]> link = new LinkedList<String[]>();
		for(Map<String,String> e:list){
			int i = 0;
			String[] result = new String[3];
			for(String attr:attrs){
				result[i++] = e.get(attr);
				System.out.println(e.get(attr));
			}
			for(IndexMap e2:lastIndexs){
				if(e2.getId().equals(result[0])){
					result[2] = result[1];
					result[1] = e2.getName();
				}
			}
			link.add(result);
		}
		List<List<String[]>> rowStrings = new ArrayList<List<String[]>>();
		rowStrings.add(link);
		List<String> sheetName = new ArrayList<String>();
		sheetName.add(Dictionaries.getDisciplineName(discId) + "末级指标项权重");
		List<List<String>> attrDomain = new ArrayList<List<String>>();
		List<String> attrNames = new ArrayList<String>();
		attrNames.add("末级指标项ID");
		attrNames.add("末级指标项名称");
		attrNames.add("末级指标项权重");
		attrDomain.add(attrNames);
		Map<String, String> excelPathMap = FileOperate.exportExcel(attrDomain, rowStrings, sheetName, rootPath, "excel");
		return JsonConvertor.obj2JSON(excelPathMap);
	}

	/*@Override
	public void calculateAllIndexByCat(String catId){
		List<String> indexIds = getIndexIdsByCat(catId);
		List<String> discIds = discCategoryDao.getDiscByCatId(catId);
		for(String discId:discIds){
			List<IndexDomain> indexDomains = getAndConvertToIndexDomain(discId);
			calculateByDisc(discId,catId,indexDomains,indexIds);
		}
	}*/
	
	@Override
	public void calculateAllIndexByDisc(String discId) throws Exception{
		String catId = discCategoryDao.getCatByDiscId(discId);
		List<String> indexIds = new ArrayList<String>();
		try {
			indexIds = getIndexIdsByCat(catId);
		} catch (DocumentException e1) {
			throw new Exception("xml读取错误");
		}
		List<IndexDomain> indexDomains = getAndConvertToIndexDomain(discId);
		Map<String,Double> m = calculateByDisc(discId,catId,indexDomains,indexIds);
		if(sum(m)!=1)
			m = fixSum(m,sum(m));
		saveOrUpdateIndexWeight(discId,m);
	}

	private List<String> getIndexIdsByCat(String catId) throws DocumentException{
		List<String> indexIds = new ArrayList<String>();
		SAXReader reader = new SAXReader(); 
   	    reader.setEncoding("utf-8");
   	    String filePath = "template/factor.xml";
   	    List<String> attrs = new ArrayList<String>();
   	    List<String> catIds = new ArrayList<String>();
   	    attrs.add("IndexItemId");
   	    attrs.add("CatId");
   	    catIds.add("JSJ");
   	    //注意！！！此处以后应补全所有的门类！因为下次直接从缓存中加载
   	    //catIds.add(xxx)......
   	    Map<String,Map<String,String>> m = xmlCache.getXmlData(filePath,catIds,attrs,"IndexItemId");
   	    for(String key:m.keySet()){
   	    	String temp = m.get(key).get("CatId");
   	    	if(temp.equals(catId))
   	    		indexIds.add(m.get(key).get("IndexItemId"));
   	    }
   	    return indexIds; 
	}
	
	private List<IndexDomain> getAndConvertToIndexDomain(String discId){
		List<EvalResult> e = evalResultDao.
				getSameDiscIndicWtOrIndexValue(discId, null, QType.INDIC_IDX.toInt());
		List<IndexDomain> indexDomains = new ArrayList<IndexDomain>();
		for(EvalResult e1:e){
			IndexDomain indexDomain = new IndexDomain(e1,e1.getEvalQuestion().getSubQuestionId(),
					e1.getEvalQuestion().getDiscId());
			indexDomains.add(indexDomain);
		}
		return indexDomains;
	}
	
	private Map<String,Double> calculateByDisc(String discId,String catId,List<IndexDomain> indexDomains,
			List<String> indexIds){
		Map<String,Double> m = new HashMap<String,Double>();
		for(String id:indexIds){
			double value = 0;
			int count = 0;
			for(IndexDomain e:indexDomains){
				if(e.getIndexItemId().equals(id)){
					value += Double.parseDouble(e.getEvalResult().getEvalValue());
					count ++;
				}
				m.put(id, value/count);
			}
		}
		return m;
	}
	
	private static Double sum(Map<String,Double> m){
		Set<String> keySet = m.keySet();
		double sum = 0;
		for(String key:keySet)
			sum += m.get(key);
		return sum;
	}
	
	private static Map<String,Double> fixSum(Map<String,Double> m, Double sum){
		Set<String> keySet = m.keySet();
		for(String key:keySet){
			double temp = m.get(key)/sum;
			//BigDecimal b = new BigDecimal(temp);
			//double value = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			m.put(key, temp);
		}
		return m;
	}
	
	private void saveOrUpdateIndexWeight(String discId,Map<String,Double> m){
		Set<String> keySet = m.keySet();
		for(String key:keySet){
			List<IndexAvgWt> avgWts = indexAvgWtDao.
					getIndexAvgWtByIndexAndDisc(key, discId);
			if(avgWts.size()>0){
				IndexAvgWt wt = avgWts.get(0);
				wt.setAvgValue(m.get(key));
				indexAvgWtDao.saveOrUpdate(wt);
			}else{
				IndexAvgWt wt = new IndexAvgWt();
				wt.setId(GUID.get());
				wt.setDiscId(discId);
				wt.setIndexItemId(key);
				wt.setAvgValue(m.get(key));
				indexAvgWtDao.save(wt);
			}
		}
	}
	
	public static void main(String [] args){
		Map<String,Double> m = new HashMap<String,Double>();
		m.put("1", 33.3324234);
		m.put("2", 24.712432344);
		m.put("3", 76.521312312);
		double sum = 0;
		Set<String> key = m.keySet();
		for(String k:key){
			sum += m.get(k);
			System.out.println(m.get(k));
		}
		m = fixSum(m,sum);
		System.out.println(sum);
		System.out.println("he");
	}
}
