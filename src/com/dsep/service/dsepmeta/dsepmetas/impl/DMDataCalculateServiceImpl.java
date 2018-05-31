package com.dsep.service.dsepmeta.dsepmetas.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.mortbay.log.Log;

import com.dsep.dao.dsepmeta.base.DiscCategoryDao;
import com.dsep.dao.dsepmeta.calculate.CalResultDao;
import com.dsep.dao.dsepmeta.calculate.DiscIndexValueDao;
import com.dsep.dao.dsepmeta.calculate.IndexAvgWtDao;
import com.dsep.dao.dsepmeta.calculate.IndexScoreDao;
import com.dsep.entity.dsepmeta.CalResult;
import com.dsep.entity.dsepmeta.DiscLastIndexValue;
import com.dsep.entity.dsepmeta.IndexAvgWt;
import com.dsep.entity.dsepmeta.IndexMap;
import com.dsep.entity.dsepmeta.IndexScore;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;
import com.dsep.service.datacalculate.FactorAndWeight.XmlCache;
import com.dsep.service.datacalculate.RuleEngine.ObjectCalculateService;
import com.dsep.service.datacalculate.RuleEngine.SubjectCalculateService;
import com.dsep.service.dsepmeta.dsepmetas.DMDataCalculateService;
import com.dsep.service.dsepmeta.dsepmetas.DMDiscIndexService;
import com.dsep.util.GUID;
import com.dsep.util.datacalculate.Cluster;
import com.dsep.util.datacalculate.ConvertNumber;
import com.dsep.util.datacalculate.ReadXml;

public class DMDataCalculateServiceImpl implements DMDataCalculateService{

	private DiscIndexValueDao discIndexValueDao;
	private DiscCategoryDao discCategoryDao;
	private CalResultDao calResultDao;
	private IndexAvgWtDao indexAvgWtDao;
	private IndexScoreDao indexScoreDao;
	
	private DMDiscIndexService discIndexService;
	private UnitService unitService;
	private ObjectCalculateService objectCalculateService;
	private SubjectCalculateService subjectCalculateService;
	private DisciplineService disciplineService;
	
	private XmlCache xmlCache;
	
	public XmlCache getXmlCache() {
		return xmlCache;
	}

	public void setXmlCache(XmlCache xmlCache) {
		this.xmlCache = xmlCache;
	}

	public DiscIndexValueDao getDiscIndexValueDao() {
		return discIndexValueDao;
	}

	public void setDiscIndexValueDao(DiscIndexValueDao discIndexValueDao) {
		this.discIndexValueDao = discIndexValueDao;
	}

	public DiscCategoryDao getDiscCategoryDao() {
		return discCategoryDao;
	}

	public void setDiscCategoryDao(DiscCategoryDao discCategoryDao) {
		this.discCategoryDao = discCategoryDao;
	}
	
	public CalResultDao getCalResultDao() {
		return calResultDao;
	}

	public void setCalResultDao(CalResultDao calResultDao) {
		this.calResultDao = calResultDao;
	}

	public IndexAvgWtDao getIndexAvgWtDao() {
		return indexAvgWtDao;
	}

	public void setIndexAvgWtDao(IndexAvgWtDao indexAvgWtDao) {
		this.indexAvgWtDao = indexAvgWtDao;
	}

	public IndexScoreDao getIndexScoreDao() {
		return indexScoreDao;
	}

	public void setIndexScoreDao(IndexScoreDao indexScoreDao) {
		this.indexScoreDao = indexScoreDao;
	}

	public DMDiscIndexService getDiscIndexService() {
		return discIndexService;
	}

	public void setDiscIndexService(DMDiscIndexService discIndexService) {
		this.discIndexService = discIndexService;
	}

	public UnitService getUnitService() {
		return unitService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public ObjectCalculateService getObjectCalculateService() {
		return objectCalculateService;
	}

	public void setObjectCalculateService(
			ObjectCalculateService objectCalculateService) {
		this.objectCalculateService = objectCalculateService;
	}

	public SubjectCalculateService getSubjectCalculateService() {
		return subjectCalculateService;
	}

	public void setSubjectCalculateService(
			SubjectCalculateService subjectCalculateService) {
		this.subjectCalculateService = subjectCalculateService;
	}

	public DisciplineService getDisciplineService() {
		return disciplineService;
	}

	public void setDisciplineService(DisciplineService disciplineService) {
		this.disciplineService = disciplineService;
	}
	
	// 用折算系数算末级指标项的大分
	@Override
	public String calLastIndex(List<String> discIds) throws Exception{
		String calculateLog = "";
		
		List<Map<String,String>> xmlData=new ArrayList<Map<String,String>>();//读取合并学校的xml配置文档
		List<String> attrs=new LinkedList<String>();//每个合并学校<merge></merge>下要读取的属性
		attrs.add("unitID");
		attrs.add("unitName");
		attrs.add("units");
		attrs.add("disciplines");
		
		try{
			xmlData=ReadXml.readOneLevelXml("template/mergeUnit.xml",attrs);//读取配置文档
		}catch(Exception e){
			//TODO 对于异常的处理
			e.printStackTrace();
		}
		for (String discId : discIds) {
			// 根据学科ID获取指标体系
			List<IndexMap> indexs = discIndexService
					.getIndexMapsByDiscId(discId);
			if(indexs.size()==0){
				//TODO 没有对应的指标体系，没有配置该门类的指标体系，或者是该学科没有所属的门类，抛出异常，本学科不再进行计算
				calculateLog+="没有对应的指标体系";
				return calculateLog;
			}
			// 获得该学科所有参评学校
			List<String> evalUnitIds = unitService.getEvalUnitByDiscId(discId);

			List<IndexMap> objectLastIndexs = new ArrayList<IndexMap>(); // 客观末级指标
			List<IndexMap> subjectLastIndexs = new ArrayList<IndexMap>(); // 主观末级指标
			List<IndexMap> lastIndexs = new ArrayList<IndexMap>(); // 末级指标

			for (IndexMap index : indexs) {
				if (index.getIndexLevel() == 3) {
					lastIndexs.add(index);
					if (index.getIsSubject().equals("0"))      //把末级指标分为主观末级指标和客观末级指标，0为主观，1为客观
						objectLastIndexs.add(index);
					else if (index.getIsSubject().equals("1"))
						subjectLastIndexs.add(index);
				}
			}
			
			Map<String,Map<String,String>> mergeUnitOfDisc=new HashMap<String,Map<String,String>>();
			Set<String> mergeUnitSet;
			// 在XML中找到所有的关于本学科的配置6
			for(Map<String,String> unitConfig:xmlData){
				if(unitConfig.get("disciplines").equals("all")||unitConfig.get("disciplines").equals(discId)){//本学科下要合并的学校
					String units=unitConfig.get("units");
					String [] mergeUnitArr=units.split(",");
					boolean flag=false;
					for(int i=0;i<mergeUnitArr.length;i++){
						if(!evalUnitIds.contains(mergeUnitArr[i]))//如果units属性的所有学校都在参评学科列表中，则在evalUnitList中添加mergeUnit，并移除所有的units
							flag=true;
					}
					if(flag)	continue;
					
					String mergeUnitId=unitConfig.get("unitID");
					mergeUnitOfDisc.put(mergeUnitId, unitConfig);
					evalUnitIds.add(mergeUnitId);
					for(int i=0;i<mergeUnitArr.length;i++){
						evalUnitIds.remove(mergeUnitArr[i]);
					}
				}
			}
			mergeUnitSet=mergeUnitOfDisc.keySet();
			
			String catId=discCategoryDao.getCatByDiscId(discId);
			List<String> catList=new ArrayList<String>();
			catList.add(catId);
			List<String> formularAttr=new ArrayList<String>();
			formularAttr.add("IndexItemId");
			formularAttr.add("Formula");
			Map<String,Map<String,String>> formularXml=new HashMap<String,Map<String,String>>();
			try {
				//从缓存中获取数据，若缓存中没有对应数据，则调用getXmlData方法获得
				formularXml=xmlCache.getXmlData("template/indicFormula.xml", catList, formularAttr, "IndexItemId");
			} catch (DocumentException e1) {
				e1.printStackTrace();
				Log.info("无法获得xml信息");
			}
			// 计算客观末级指标
			for (IndexMap index : objectLastIndexs) {
				String indexId=index.getId();
				String formular=null;
				if(formularXml.keySet().contains(indexId))
					formular=formularXml.get(indexId).get("Formula");
				for (String unitId : evalUnitIds) {
					String unitCode;
					if(mergeUnitSet.contains(unitId))
						unitCode=mergeUnitOfDisc.get(unitId).get("units");
					else 
						unitCode=unitId;
					try {
						double objectValue;
						if(indexId.equals("JSJ000I030101")&&unitCode.equals("10335"))
							unitCode=unitCode+"";
						objectValue=objectCalculateService.calculate(formular, indexId, unitCode, discId);
						objectValue=ConvertNumber.convertDouble(objectValue,4);
						
						List<DiscLastIndexValue> list = discIndexValueDao
								.isExist(index.getId(), discId, unitId);
						if (list.size() == 0) {
							DiscLastIndexValue d = new DiscLastIndexValue();
							d.setId(GUID.get());
							d.setDiscId(discId);
							d.setUnitId(unitId);
							d.setIndexId(index.getId());
							d.setIndexContent(index.getName());
							d.setValue(objectValue);
							discIndexValueDao.save(d);
						} else {
							DiscLastIndexValue d = list.get(0);
							d.setValue(objectValue);
							discIndexValueDao.saveOrUpdate(d);
						}
					} catch (Exception e) {
						if(calculateLog.indexOf(e.getMessage())<0)
							calculateLog += e.getMessage() + "\\";
						e.printStackTrace();
					}

				}
			}

			// 计算主观末级指标
			for (IndexMap index : subjectLastIndexs) {
				
				for (String unitId : evalUnitIds) {

					String unitCode="";
					if(mergeUnitSet.contains(unitId))
						unitCode=mergeUnitOfDisc.get(unitId).get("units");//使用unitCode是因为对于某些学校的数据需要合并在一起计算，例如：传入“10491,11415”表示要将这两个学校的数据合并 在一起计算
					else 
						unitCode=unitId;
					String indexId = index.getId();
					double valueSubject = subjectCalculateService
							.getSubjectValue(indexId, discId, unitCode);
					valueSubject=ConvertNumber.convertDouble(valueSubject,4);
					try {
						List<DiscLastIndexValue> list = discIndexValueDao
								.isExist(index.getId(), discId, unitId);
						if (list.size() == 0) {
							DiscLastIndexValue d = new DiscLastIndexValue();
							d.setId(GUID.get());
							d.setDiscId(discId);
							d.setUnitId(unitId);
							d.setIndexId(index.getId());
							d.setIndexContent(index.getName());
							d.setValue(valueSubject);
							discIndexValueDao.save(d);
						} else {
							DiscLastIndexValue d = list.get(0);
							d.setValue(valueSubject);
							discIndexValueDao.saveOrUpdate(d);
						}
					} catch (Exception e) {
						calculateLog += e.getMessage() + " ";
					}
					
				}
			}

		}
		return calculateLog;
	}

	/**
	 * 转换百分制
	 * 
	 * @param discId
	 * @param lastIndexs
	 */
	@Override
	public void convertHundredMark(List<String> discIds) {
		// 读xml
		List<Map<String,String>> xmlData=new LinkedList<Map<String,String>>();
		List<String> specialIndexList=new LinkedList<String>();
		List<String> attrs=new LinkedList<String>();
		attrs.add("indexId");
		attrs.add("type");
		attrs.add("set");
		attrs.add("c1");
		attrs.add("c2");
		
		try{
			xmlData=ReadXml.readOneLevelXml("template/specialindex.xml",attrs);
		}catch(Exception e){
			//TODO 对于异常的处理
			e.printStackTrace();
		}
		
		for(int i=0;i<xmlData.size();i++){	//获取所有的特殊末级指标项indexId
			Map<String,String> item=xmlData.get(i);
			specialIndexList.add(item.get("indexId"));
		}
		
		for (String discId : discIds) {
			// 根据学科ID获取指标体系
			List<IndexMap> indexs = discIndexService
					.getIndexMapsByDiscId(discId);
			List<IndexMap> lastIndexs = new ArrayList<IndexMap>(); // 末级指标

			for (IndexMap index : indexs) {
				if (index.getIndexLevel() == 3)
					lastIndexs.add(index);
			}
			for (IndexMap index : lastIndexs) {
				
				String indexId = index.getId();
				
				if(specialIndexList.contains(indexId)){
					Map<String,String> indexConfig=new HashMap<String,String>();  //xml中关于这个末级指标的配置
					boolean xmlFlag=true;
					for(int i=0;i<xmlData.size()&&xmlFlag;i++){ //获得这个index的xml配置
						Map<String,String> item=xmlData.get(i);
						if(item.get("indexId").equals(indexId)){
							indexConfig=item;
							xmlFlag=false;
						}
					}
						String type=indexConfig.get("type");
						if(type.equals("1")){               //获取阈值
							double limit=0.0;
							String setStr=indexConfig.get("set");
							if(StringUtils.isNotBlank(setStr)&&setStr.equals("number")){
								String c1Str=indexConfig.get("c1");
								limit=Double.parseDouble(c1Str);
							}else if(StringUtils.isNotBlank(setStr)&&setStr.equals("percent")){
								String percentStr=indexConfig.get("c1");
								double percent=Double.parseDouble(percentStr.substring(0,percentStr.length()-1));
								percent=percent*0.01;
								limit=getFullLimit(discId,indexId,percent);
					
							}else if(StringUtils.isNotBlank(setStr)&&setStr.equals("expert")){
								//TODO 获得本地专家打分所给的上限值
							}
							
							calHundredmarkWithLimit(discId, indexId, limit);
						}
						else if(type.equals("2")){
							double c1=0.0;
							double c2=0.0;
							String setStr=indexConfig.get("set");
							if(StringUtils.isNotBlank(setStr)&&setStr.equals("number")){
								String c1Str=indexConfig.get("c1");
								String c2Str=indexConfig.get("c2");
								c1=Double.parseDouble(c1Str);
								c2=Double.parseDouble(c2Str);
							}else if(StringUtils.isNotBlank(setStr)&&setStr.equals("percent")){
								String percent1Str=indexConfig.get("c1");
								String percent2Str=indexConfig.get("c2");
								double percent1=Double.parseDouble(percent1Str.substring(0,percent1Str.length()-1));
								double percent2=Double.parseDouble(percent2Str.substring(0,percent2Str.length()-1));
								percent1=percent1*0.01;
								percent2=percent2*0.01;
								double[] cArray=getFullC1C2(discId,indexId,percent1,percent2);
								c1=cArray[0];
								c2=cArray[1];
							}else if(StringUtils.isNotBlank(setStr)&&setStr.equals("expert")){
								//TODO 调用专家打分的数值
							}
							
							calHundredMarkBetwenc1c2(discId, indexId, c1, c2);
						}
						
					}
				else
					calHundredMark(discId, indexId);

			}
		}
	}

	/**
	 * 换算每个学科的总分
	 * @param discId
	 * @param evalUnitIds
	 * @param lastIndexs
	 */
	@Override
	public void calTotalScore(List<String> discIds) {
		
		List<Map<String,String>> xmlData=new ArrayList<Map<String,String>>();
		List<String> attrs=new LinkedList<String>();
		attrs.add("unitID");
		attrs.add("unitName");
		attrs.add("disciplines");
		Map<String,Map<String,String>> mergeConfig=new HashMap<String,Map<String,String>>();
		for(Map<String,String> mergeItem:xmlData){
			String unitID=mergeItem.get("unitID");
			mergeConfig.put(unitID, mergeItem);
		}
		Set<String> mergeUnit=mergeConfig.keySet();
		try{
			xmlData=ReadXml.readOneLevelXml("template/mergeUnit.xml",attrs);
		}catch(Exception e){
			//TODO 对于异常的处理
			e.printStackTrace();
		}
		
		for (String discId : discIds) {
			// 根据学科ID获取指标体系
			List<IndexMap> indexs = discIndexService
					.getIndexMapsByDiscId(discId);
			// 获得该学科所有参评学校
			List<String> evalUnitIds = unitService.getEvalUnitByDiscId(discId);

			List<IndexMap> lastIndexs = new ArrayList<IndexMap>(); // 末级指标

			for (IndexMap index : indexs) {
				if (index.getIndexLevel() == 3)
					lastIndexs.add(index);
			}
			
			for (String unitId : evalUnitIds) {
				double result = 0.0;
				for (IndexMap index : lastIndexs) {
					String indexID = index.getId();
					double Bi; // 末级指标的百分制得分
					double Wi; // 末级指标的权重
					List<DiscLastIndexValue> list = discIndexValueDao.isExist(
							index.getId(), discId, unitId);

					if (list.size() == 0)   //TODO 本末级指标没有运算到
						continue;

					Bi = list.get(0).getTransValue();

					List<IndexAvgWt> str=indexAvgWtDao
							.getIndexAvgWtByIndexAndDisc(indexID, "0835");
					if (str.size()==0){
						Wi=0;
					}
					Wi = str.get(0).getAvgValue();
					result = result + Bi * Wi;
				}
				List<CalResult> list = calResultDao.getResult(unitId, discId);
				if (list.size() == 0) {
					CalResult calResult = new CalResult();
					calResult.setId(GUID.get());
					calResult.setDiscId(discId);
					String discName=disciplineService.getDisciplineNameById(discId);
					calResult.setDiscName(discName);
					calResult.setUnitId(unitId);
					String unitName="";
					if(mergeUnit.contains(unitId)){        //这个学校ID是合并后的学校，则读取xml文档中设置的学校名称
						String xmlDiscplines=mergeConfig.get(unitId).get("disciplines");
						if(xmlDiscplines.equals("all")||xmlDiscplines.equals(discId))//在计算本学科时，学校合并
							unitName=mergeConfig.get(unitId).get("unitName");
					}
					else
						unitName=unitService.getUnitNameById(unitId);
					calResult.setUnitName(unitName);
					calResult.setRank(0);
					calResult.setCluClass("-");
					calResult.setScore(result);
					calResultDao.save(calResult);
				} else {
					CalResult calResult = list.get(0);
					calResult.setScore(result);
					calResult.setCluClass("-");
					calResultDao.saveOrUpdate(calResult);
				}
			}
		}
	}

	@Override
	public void calIndexScore(List<String> discIds) {
		
		for (String discId : discIds) {
			// 根据学科ID获取指标体系
			List<IndexMap> indexTree = discIndexService
					.getIndexMapsByDiscId(discId);
			// 获得该学科所有参评学校
			List<String> evalUnitIds =discIndexValueDao.getCaledUnitIDByDisc(discId); /*unitService.getEvalUnitByDiscId(discId)*/;

			List<IndexMap> indexs12 = new ArrayList<IndexMap>(); // 末级指标
			// 获取一二级指标
			for (IndexMap index : indexTree) {
				if (index.getIndexLevel() != 3)
					indexs12.add(index);
			}

			for (String unitId : evalUnitIds) {
				
				for (IndexMap index : indexs12) { // 计算每个一二级指标的得分
					double result = 0.0;
					List<IndexMap> child3Indexs = this.getlastIndexOfOneIndex(
							index, indexTree);       //获得该指标下的所有末级指标
					double w_total = 0.0;         //该指标下所有末级指标的总和
					for (IndexMap index3 : child3Indexs) {
						double w3 = indexAvgWtDao
								.getIndexAvgWtByIndexAndDisc(index3.getId(),
										"0835").get(0).getAvgValue();  //TODO  discID!!
						w_total += w3;
					}
					for (IndexMap index3 : child3Indexs) {
						double value = 0.0;
						double B3 = discIndexValueDao
								.isExist(index3.getId(), discId, unitId).get(0)
								.getTransValue();
						double w3 = indexAvgWtDao
								.getIndexAvgWtByIndexAndDisc(index3.getId(),
										"0835").get(0).getAvgValue();
						value = B3 *w3/ w_total;
						result += value;
					}

					//存储结果
					List<IndexScore> list = indexScoreDao.getIndexScore(discId, unitId, index.getId(), null, null, true, 0, 0);
							
					if (list.size() == 0) {
						IndexScore indexScore = new IndexScore();
						indexScore.setDiscId(discId);
						indexScore.setUnitId(unitId);
						indexScore.setIndexId(index.getId());
						indexScore.setIndexName(index.getName());
						indexScore.setIndexLevel(index.getIndexLevel());
						indexScore.setScore(result);
						indexScoreDao.save(indexScore);
					} else if (list.size() == 1) {
						IndexScore indexScore = list.get(0);
						indexScore.setScore(result);
						indexScoreDao.saveOrUpdate(indexScore);
					}
				}
			}
		}
		
	}
	
	@Override
	public void deductScore(List<String> discIds) {

		List<Map<String,String>> xmlMergeData=new ArrayList<Map<String,String>>();
		List<String> attrs=new LinkedList<String>();
		attrs.add("unitID");
		attrs.add("unitName");
		attrs.add("units");
		attrs.add("disciplines");
		
		try{
			xmlMergeData=ReadXml.readOneLevelXml("template/mergeUnit.xml",attrs);
		}catch(Exception e){
			
			e.printStackTrace();
		}
		for(String discId:discIds){
			Map<String,Map<String,String>> mergeUnits=new HashMap<String,Map<String,String>>();//读出本学科下合并的学校
			for(Map<String,String> unitConfig:xmlMergeData){
				if(unitConfig.get("disciplines").equals("all")||unitConfig.get("disciplines").equals(discId)){
				String unitStr=unitConfig.get("unitID");
				mergeUnits.put(unitStr, unitConfig);
				}
			}
			Set<String> keys=new HashSet<String>();
			keys=mergeUnits.keySet();
			List<String> evalUnitIds =discIndexValueDao.getCaledUnitIDByDisc(discId);
			Map<String,String> indexMap=new HashMap<String,String>();
			
			// 要扣分的末级指标项indexList
			try {
				String catId=discCategoryDao.getCatByDiscId(discId);
				indexMap=ReadXml.readDeductIndex(catId);
			} catch (DocumentException e1) {
				e1.printStackTrace();
			}
			//求出所有要扣分的指标
			for(String unitId:evalUnitIds){
				String unitCode="";
				if(keys.contains(unitId))   //该学校是一个合并的学校
					unitCode=mergeUnits.get(unitId).get("units");
				else 
					unitCode=unitId;
				//算得应该扣除的总分
				double deductValue=0.0;
				Set<String> indexkey=indexMap.keySet();
				for(String key:indexkey){
					try {
						String formular=indexMap.get(key);
						double value=objectCalculateService.calculate(formular, key, unitCode, discId);
						deductValue+=value;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				// 减去应该扣除的总分
				List<CalResult> list = calResultDao.getResult(unitId, discId);
				CalResult calResult = list.get(0);
				double score=calResult.getScore();
				double deductScore=score-deductValue;
				if(deductScore<60.0)
					deductScore=60.0;
				calResult.setScore(deductScore);
				calResultDao.saveOrUpdate(calResult);
			}
		}
	}
	
	/**
	 * 对学科进行排序
	 */
	@Override
	public void sortUnits(List<String> discIds){
		for(String discId:discIds){		
			int index=1;
			List<CalResult> list=calResultDao.getResult(discId, null,0, 0, true, "SCORE");
			List<CalResult> maxResult=new ArrayList<CalResult>();
			while(!list.isEmpty()){
				maxResult.clear();
				maxResult.add(list.get(0));
				for(int i=1;i<list.size();i++){
					CalResult item=list.get(i);
					if(item.getScore()>maxResult.get(0).getScore()){
						maxResult.clear();
						maxResult.add(item);
					}else if(item.getScore()==maxResult.get(0).getScore()){
						maxResult.add(item);
					}
				}
				for(int i=0;i<maxResult.size();i++){
					CalResult item=maxResult.get(i);
					item.setRank(index);
					list.remove(item);
					calResultDao.saveOrUpdate(item);
				}
				index=index+maxResult.size();
			}
		}
	}
	@Override
	public void cluster(List<String> discIds,double limit) {
		////聚类
		for(String discId:discIds){
			List<CalResult> calList=calResultDao.getResult(discId, null, 0, 0, true, "SCORE");
			Cluster cluster=new Cluster(calList);
			cluster.toCluster(limit);   //聚类
			List<CalResult> list=cluster.getAllEntity();    //取得聚类结果
			for(CalResult cal:list){                //更新数据库
				calResultDao.saveOrUpdate(cal);
			}
		}
	}
	/**
	 * 计算百分制
	 * 
	 * @param discID
	 * @param indexID
	 */
	private void calHundredMark(String discID, String indexID) {
		//  根据discID、indexID，获得list末级指标得分，找出最大值和最小值，线性变换到60-100分
		List<DiscLastIndexValue> list = discIndexValueDao
				.getDiscLastIndexValues(discID, indexID);

		if (list.size() == 0)
			return;
		double max, min;

		max = list.get(0).getValue();
		min = list.get(0).getValue();

		for (int i = 1; i < list.size(); i++) {
			double value = list.get(i).getValue();
			max = value > max ? value : max;
			min = value < min ? value : min;
		}

		double fenMu = max - min; // Amax-Amin
		fenMu = fenMu == 0 ? fenMu + 1 : fenMu;
		for (int i = 0; i < list.size(); i++) {
			DiscLastIndexValue cis = list.get(i);
			double Ai = cis.getValue(); // Ai
			double score;

			score = 60.0 + 40.0 * (Ai - min) / fenMu; // 线性变换
			score=ConvertNumber.convertDouble(score,4);
			cis.setTransValue(score);
			// 把百分制的分存进数据库
			discIndexValueDao.saveOrUpdate(cis);
		}
	}

	/**
	 * 获得阈值
	 * @param discID
	 * @param indexID
	 * @param percent 百分比
	 * @return
	 */
	private double getFullLimit(String discID,String indexID, double percent){
		List<DiscLastIndexValue> list = discIndexValueDao
				.getDiscLastIndexValues(discID, indexID);
		if (list.size() == 0)
			return 0;
		
		int length=list.size();
		int index=(int) (length*percent);   //满分的阈值下标
		double[] arryNum=new double[length];
		for(int i=0;i<length;i++){
			arryNum[i]=list.get(i).getValue();
		}
		// 对取到的数组排序并获得阈值
		Arrays.sort(arryNum);
		for(int i=0;i<length/2;i++){
			double temp=arryNum[length-1-i];
			arryNum[length-1-i]=arryNum[i];
			arryNum[i]=temp;
		}
		double limit=arryNum[index];
		
		return limit;
	}
	
	private double[] getFullC1C2(String discID,String indexID, double percent1,double percent2){
		double[] result=new double[2];
		result[0]=0.0;
		result[1]=0.0;
		List<DiscLastIndexValue> list = discIndexValueDao
				.getDiscLastIndexValues(discID, indexID);
		if (list.size() == 0)
			return result;
		
		int length=list.size();
		int index1=(int) (length*percent1);   //满分的阈值下标1
		int index2=(int) (length*percent2);   //满分的阈值下标2
		double[] arryNum=new double[length];
		for(int i=0;i<length;i++){
			arryNum[i]=list.get(i).getValue();
		}
		// 对取到的数组排序并获得阈值
		Arrays.sort(arryNum);
		for(int i=0;i<length/2;i++){
			double temp=arryNum[length-1-i];
			arryNum[length-1-i]=arryNum[i];
			arryNum[i]=temp;
		}
		
		result[0]=arryNum[index1];
		result[1]=arryNum[index2];
		
		return result;
	}
	/**
	 * 计算百分制，有阈值上限，例如专职教师总数、授予博士/硕士学位数
	 * 
	 * @param discID
	 * @param indexID
	 * @param limitValue
	 *            末级指标得分转换为百分制的阈值
	 */
	private void calHundredmarkWithLimit(String discID, String indexID,
			double limitValue) {
		List<DiscLastIndexValue> list = discIndexValueDao
				.getDiscLastIndexValues(discID, indexID);

		if (list.size() == 0)
			return;
		double min;

		min = list.get(0).getValue();

		for (int i = 1; i < list.size(); i++) {
			double value = list.get(i).getValue();
			min = value < min ? value : min;
		}

		double fenMu = limitValue - min; // Amax-Amin
		fenMu = fenMu == 0 ? fenMu + 1 : fenMu;
		for (int i = 0; i < list.size(); i++) {
			DiscLastIndexValue cis = list.get(i);
			double Ai = cis.getValue(); // Ai
			double score;

			if (Ai >= limitValue)
				score = 100;
			else
				score = 60.0 + 40.0 * (Ai - min) / fenMu; // 线性变换

			score=ConvertNumber.convertDouble(score,4);
			cis.setTransValue(score);
			// 把百分制的分存进数据库
			discIndexValueDao.saveOrUpdate(cis);
		}
	}

	/**
	 * 数值在c1,c2区间内为满分的百分制转换，例如生师比
	 * 
	 * @param discID
	 * @param indexID
	 * @param c1
	 * @param c2
	 */
	private void calHundredMarkBetwenc1c2(String discID, String indexID,
			double c1, double c2) {
		List<DiscLastIndexValue> list = discIndexValueDao
				.getDiscLastIndexValues(discID, indexID);

		if (list.size() == 0)
			return;
		List<Double> list_D = new ArrayList<Double>();
		double Amax = 0.0;
		double Dmax = 0.0;

		for (int i = 0; i < list.size(); i++) { // 找到末级指标项数值的最大值
			DiscLastIndexValue cal = list.get(i);
			double Ai = cal.getValue();
			Amax = Ai > Amax ? Ai : Amax;
		}

		for (int i = 0; i < list.size(); i++) {
			DiscLastIndexValue calScore = list.get(i);
			double value = calScore.getValue(); // 获得末级指标项的数值
			if (value < c1) { // 如果该得分位于Amin, c1区间内
				value = c1 - value;
				list_D.add(value);
			} else if (value > c2) {
				value = Amax - value;
				list_D.add(value);
			} else
				continue;
		}

		for (int i = 0; i < list_D.size(); i++) {
			double value = list_D.get(i);
			Dmax = value > Dmax ? value : Dmax;
		}

		Dmax = Dmax == 0 ? Dmax + 1 : Dmax;

		for (int i = 0; i < list.size(); i++) {
			DiscLastIndexValue calScore = list.get(i);
			double Di = calScore.getValue();
			double score = 0.0;
			if (Di < c1) {
				Di = c1 - Di;
				score = 60.0 + 40.0 * (Dmax - Di) / Dmax;
			} else if (Di > c2) {
				Di = Amax - Di;
				score = 60.0 + 40.0 * (Dmax - Di) / Dmax;
			} else {
				score = 100.0;
			}
			score=ConvertNumber.convertDouble(score,4);
			calScore.setTransValue(score);
			discIndexValueDao.saveOrUpdate(calScore);
		}
	}

	/**
	 * 获得某个一二级指标下的所有末级指标
	 * 
	 * @param theIndex
	 * @param tree
	 * @return
	 */
	private List<IndexMap> getlastIndexOfOneIndex(IndexMap theIndex,
			List<IndexMap> tree) {
		List<IndexMap> result = new LinkedList<IndexMap>();
		if (theIndex.getIndexLevel() == 2) {  //该指标为二级指标
			for (IndexMap index : tree) {
				if (index.getIndexLevel()==3             //取出该指标下的所有末级指标
						&& index.getParentId().equals(theIndex.getId())) {
					result.add(index);
				}
			}
		} else if (theIndex.getIndexLevel() == 1) {  //该指标为一级指标
			List<IndexMap> twoIndex = new ArrayList<IndexMap>();
			//取到该节点下的所有二级指标
			for (IndexMap index : tree) {     
				if (index.getIndexLevel()==2             
						&& index.getParentId().equals(theIndex.getId())) {
					twoIndex.add(index);
				}
			}
			//再取到这些二级指标下的所有末级指标
			for (IndexMap index2 : twoIndex) {  
				for (IndexMap index : tree) {
					if (index.getIndexLevel()==3             
							&& index.getParentId().equals(index2.getId()))
						result.add(index);
				}
			}
		}
		return result;
	}

}
