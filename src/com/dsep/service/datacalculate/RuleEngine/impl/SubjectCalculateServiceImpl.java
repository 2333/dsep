package com.dsep.service.datacalculate.RuleEngine.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dsep.dao.dsepmeta.base.DiscCategoryDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalResultDao;
import com.dsep.dao.dsepmeta.expert.selection.ExpertDao;
import com.dsep.entity.expert.EvalResult;
import com.dsep.entity.expert.Expert;
import com.dsep.service.datacalculate.FactorAndWeight.XmlCache;
import com.dsep.service.datacalculate.RuleEngine.SubjectCalculateService;
import com.dsep.util.datacalculate.ReadXml;
import com.dsep.util.export.briefsheet.ResourceLoader;

public class SubjectCalculateServiceImpl implements SubjectCalculateService {
	private EvalResultDao evalResultDao;
	private DiscCategoryDao discCategoryDao;
	private XmlCache xmlCache;

	public XmlCache getXmlCache() {
		return xmlCache;
	}

	public void setXmlCache(XmlCache xmlCache) {
		this.xmlCache = xmlCache;
	}

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
	
	@Override
	public double getSubjectValue(String indexId,String discId,String unitId) throws Exception{
		double sum = 0;
		String subQuestionId;
		try {
			subQuestionId = getSubQuestionId(indexId,discId);
		} catch (DocumentException e1) {
			throw new Exception("找不到相关的指标体系信息");
		}
		List<Double> values=new ArrayList<Double>();
		if(unitId.contains(",")){
			String[] unitArr=unitId.split(",");
			for(int i=0;i<unitArr.length;i++){
				List<Double> list=getAllSubjectValues(subQuestionId,discId,unitArr[i]);
				values.addAll(list);
			}
		}else{
			values = getAllSubjectValues(subQuestionId,discId,unitId);
		}
		if(values.size()==0)
			return 0.0;
		else{
			//将打分结果去除奇异值以及进行线性处理
			values = handleSubjectValues(values);
			for(Double e:values){
				sum += e;
			}
			double temp = sum/values.size();
			BigDecimal b = new BigDecimal(temp);
			//保留2位小数
			double value = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			return value;
		}
		
	}
	
	private String getSubQuestionId(String indexId,String discId) throws DocumentException{
		String subQId = "";
		String filePath = "template/subIndex.xml";
		List<String> catIds = new ArrayList<String>();
		//注意！此处保证所有门类的信息都要获得
		catIds.add("JSJ");
		List<String> attrs = new ArrayList<String>();
		attrs.add("IndexItemId");
		attrs.add("Sub");
		//从名为xmlData的缓存中获取相应的数据，如果没有则调用getXmlData方法获得
		Map<String,Map<String,String>> m = xmlCache.getXmlData(filePath, catIds, attrs, "IndexItemId");
		for(String key:m.keySet()){
			if(key.equals(indexId)&&key.substring(0, 3).equals(""))
				subQId = m.get(key).get("Sub");
		}
		return subQId;
	}
	
	private List<Double> getAllSubjectValues(String subQuestionId,String discId,
			String unitId){
		List<Double> values = new ArrayList<Double>();
		List<EvalResult> list = evalResultDao.getSameUnitValueBySubQuestionId(subQuestionId, discId, unitId);
		for(EvalResult e:list){
			values.add(Double.valueOf(e.getEvalValue()));
		}
		return values;
	}
	
	private static List<Double> handleSubjectValues(List<Double> values){
		double temp;
		//排序
		for(int i=0;i<values.size()-1;i++){
			for(int j=i+1;j<values.size();j++){
				if(values.get(i)>values.get(j)){
					temp = values.get(j);
					values.set(j, values.get(i));
					values.set(i, temp);
				}
			}
		}
		double max = values.get(values.size()-1);
		double min = values.get(0);
		double needDel = values.size()*0.05;
		if(!(needDel%1==0))
			needDel = needDel - needDel%1;
		//去除奇异值
		if(needDel>0){
			int n = (int)needDel;
			values = values.subList(n, values.size()-n);
		}
		if(max!=min){
			for(int i=0;i<values.size();i++){
				values.set(i, 60+40*(values.get(i)-min)/(max-min));
			}
		}
		return values;
	}

}
