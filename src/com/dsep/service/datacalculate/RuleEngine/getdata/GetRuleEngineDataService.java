package com.dsep.service.datacalculate.RuleEngine.getdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;

import com.dsep.service.dsepmeta.BaseDBOper;
import com.dsep.service.dsepmeta.MetaOper;
import com.dsep.util.datacalculate.ConvertNumber;
import com.dsep.util.datacalculate.ReadXml;
import com.meta.entity.MetaAttribute;
import com.meta.entity.MetaEntity;

public class GetRuleEngineDataService extends MetaOper{
	private BaseDBOper baseDBOper;
	private static List<String> awardFiledList=new ArrayList<>();
	private static List<String> sortFieldList=new ArrayList<>();

	public BaseDBOper getBaseDBOper() {
		return baseDBOper;
	}

	public void setBaseDBOper(BaseDBOper baseDBOper) {
		this.baseDBOper = baseDBOper;
	}
	
	///////////////////////////////////////////////////////////////
	/**
	 * 用tableName去取公共库的数据
	 * @param commParams
	 * @return
	 */
	public List<Map<String,String>> getCalculateDataByTableName(Map<String,String> commParams){
		
		Set<String> keySet=commParams.keySet();
		String commName=commParams.get(DataCode.COMMANDER_NAME);
		String tableName=commParams.get(DataCode.TABLE_NAME);
		String field=commParams.get(DataCode.FIELD);
		if(commName.equals(DataCode.COMM_COUNT))
			field="XXDM";
		String condition=" ";
		 
		if(keySet.contains(DataCode.UNIT_ID)){
			condition=condition+"XXDM like2 '%"+commParams.get(DataCode.UNIT_ID)+"%'";
		}
		
		if(commParams.keySet().contains(DataCode.CONDITION))
			condition= condition+" and "+"("+commParams.get(DataCode.CONDITION)+")";
		 
		//对于生师比的硕士授权学科查询，sql语句单独考虑
		List<String> fieldClomns=new LinkedList<String>();
		fieldClomns.add(field);
		
		if(commParams.keySet().contains(DataCode.LIMIT)){    //获取限制字段的字段名称
			String strLimit=commParams.get(DataCode.LIMIT);
			if(strLimit.contains(DataCode.FIELD)){
				String limitField=strLimit.substring(strLimit.indexOf("&")+1);
				fieldClomns.add(limitField);
			}
		}
		
		Object[] fieldValue=fieldClomns.toArray();
		String[] fieldArrs=new String[fieldValue.length];
		for(int i=0;i<fieldValue.length;i++){
			fieldArrs[i]=fieldValue[i].toString();
		}
		
		List<Map<String,String>> result=new LinkedList<Map<String,String>>();
		List<Map<String,String>> list=baseDBOper.getRowData(tableName, fieldArrs, condition);
		
		result.add(commParams);
		result.addAll(list);
		
		return result;
		
	}
	
	/**
	 * 用entityID去取采集的数据
	 * @param commParams
	 * @return
	 * @throws Exception 
	 */
	public List<Map<String,String>> getCalculateDataByID(Map<String,String> commParams) throws Exception{
		
		Set<String> keySet=commParams.keySet();
		
		String commName=commParams.get(DataCode.COMMANDER_NAME);
		
		String entityID=commParams.get(DataCode.ENTITY_ID);//获取entityID
		String field=commParams.get(DataCode.FIELD);
		if(commName.equals(DataCode.COMM_COUNT))
			field="ID";
		/******************添加取数据的条件***********************/
		String condition=" ";
		if(commParams.get(DataCode.COMMANDER_NAME).equals(DataCode.COMM_FACTOR)){   //取折算系数
			String DISC_ID=commParams.get(DataCode.DISC_ID);
			condition=condition+" DISC_ID='"+DISC_ID+"'";
		}
		else if(keySet.contains(DataCode.UNIT_ID)&&keySet.contains(DataCode.DISC_ID)){
			String UNIT_ID=commParams.get(DataCode.UNIT_ID);
			String DISC_ID=commParams.get(DataCode.DISC_ID);
			/*****************需要合并的学校去数据，拼相应的查询条件 start*******************/
			String unitStr;
			if(UNIT_ID.contains(",")){     //本学校是需要合并的学校
				String [] unitArr=UNIT_ID.split(",");
				unitStr=" (";
				for(int i=0;i<unitArr.length;i++){
					unitStr=unitStr+" UNIT_ID='"+unitArr[i];
					if(i!=(unitArr.length-1))
						unitStr=unitStr+"' or ";
					else
						unitStr=unitStr+"' )";
				}
			/***************需要合并的学校去数据，拼相应的查询条件 end*****************/
			}else
				unitStr=" UNIT_ID='"+UNIT_ID+"'";
			condition=condition+unitStr+" and DISC_ID='"+DISC_ID+"' ";
		}else if(keySet.contains(DataCode.DISC_ID)){	//
			String DISC_ID=commParams.get(DataCode.DISC_ID);
			condition=condition+" DISC_ID='"+DISC_ID+"'";
		}
		
		
		if(commParams.keySet().contains(DataCode.CONDITION))
			condition= condition+" and "+"("+commParams.get(DataCode.CONDITION)+")";
		
		if(commName.equals(DataCode.COMM_FACTOR))
			condition=condition+" and "+"CONTENT ='"+commParams.get(DataCode.INDEX_CONT)+"'";
		
		/*****************************************************/
		
		List<String> fieldClomns=new LinkedList<String>();              //要取出的字段
		fieldClomns.add(field);
		 
		if(commParams.keySet().contains(DataCode.LIMIT)){    //获取限制字段的字段名称
			String strLimit=commParams.get(DataCode.LIMIT);
			if(!ConvertNumber.isNum(strLimit)){
				fieldClomns.add(strLimit);
				commParams.put(DataCode.LIMIT, DataCode.FIELD+"&"+strLimit);
			}else{
				commParams.put(DataCode.LIMIT, DataCode.NUMBER+"&"+strLimit);
			}
		}
		/******************本单位本学科排序的字段，获奖等级字段******************/
		
		if(awardFiledList.isEmpty()){
			this.awardGrade();
		}
		if(sortFieldList.isEmpty()){
			this.sortFields();
		}
		
		String sortStr="";
		MetaEntity entity=super.getMetaEntityService().getById(entityID);   //取到这个entityID对应的实体
		for(MetaAttribute attr : entity.getAttributes()){
			if(sortFieldList.contains(attr.getName())){                //查看这个实体中是否有学科单位排序的字段
				fieldClomns.add(attr.getName());
				if(StringUtils.isBlank(sortStr))
					sortStr=sortStr+attr.getName();
				else
					sortStr=sortStr+"&"+attr.getName();
			}
			if(awardFiledList.contains(attr.getName())){//如果该字段为获奖等级字段，则添加进要取出的字段的list中
				fieldClomns.add(attr.getName());
				commParams.put(DataCode.AWARDLEVEL, attr.getName());
			}	
		}
		if(StringUtils.isNotBlank(sortStr))
			commParams.put(DataCode.SORT, sortStr);             //将排序字段添加进参数包中
		/*******************本单位本学科排序的字段end*******************/
		
		/*********************从数据库中取出计算需要用的数据**********************/
		List<Map<String,String>> result=new LinkedList<Map<String,String>>();
		
		result.add(commParams);//把命令参数封装到dataList的首条；
		
		/******************以下为sumFacto取折算系数所写*******************/
		if(commParams.get(DataCode.COMMANDER_NAME).equals(DataCode.COMM_SUMFACTOR)){
			
			Map<String,Object> factorParams=new HashMap<String,Object>();
			factorParams.put("INDEX_ID", commParams.get(DataCode.INDEX_ID));
			factorParams.put("DISC_ID",commParams.get(DataCode.DISC_ID));
			
			List<Map<String,String>> factorList=new LinkedList<Map<String,String>>();
			factorList=super.getData("E2013O003", factorParams, null, true, 0, 0);
			
			Map<String,String> factorResult=new HashMap<String,String>();
			for(int i=0;i<factorList.size();i++){
				Map<String,String> factorItem=factorList.get(i);
				String factorValue=factorItem.get("AVG_VALUE");         //		取折算系数
				String factorCont=factorItem.get("CONTENT");         //取折算系数对应的数据类型
				
				factorResult.put(factorCont,factorValue);
			}
			
			result.add(factorResult);                       //将取到的折算系数附加为list的第二个元素
			
		}
		/******************为sumFacto取折算系数所写 end************************/
		
		List<Map<String,String>> dataList=super.getRowData(entityID, fieldClomns, condition);
		if(dataList.size()==0 &&DataCode.COMMANDER_NAME.equals(DataCode.COMM_FACTOR)){
			throw new Exception("学科"+commParams.get(DataCode.DISC_ID)+"的折算系数"+"<"+commParams.get(DataCode.INDEX_CONT)+">没有配置");
		}
		result.addAll(dataList);                 //将取到的数据添加到list的后面
		
		return result;
	}
		
	/**
	 * 单位、学科排序字段的所有字段名
	 * @return
	 */
	private void sortFields(){
		List<String> result=new LinkedList<String>();
		String attr="name";
		List<String> attrList=new LinkedList<String>();
		attrList.add(attr);
		String cat="sort";
		List<String> catList=new LinkedList<String>();
		catList.add(cat);
		Map<String, Map<String, String>> xmlData=new HashMap<String,Map<String,String>>();
		try {
			xmlData = ReadXml.readTwoLevelXml("template/calField.xml",catList, attrList,attr);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Set<String> keys=xmlData.keySet();
		for(String key:keys){
			this.sortFieldList.add(key);
		}
	}
	
	/**
	 * 获奖等级的所有字段名
	 * @return
	 */
	private void awardGrade(){
		String attr="name";
		List<String> attrList=new LinkedList<String>();
		attrList.add(attr);
		String cat="award";
		List<String> catList=new LinkedList<String>();
		catList.add(cat);
		Map<String, Map<String, String>> xmlData=new HashMap<String,Map<String,String>>();
		try {
			xmlData = ReadXml.readTwoLevelXml("template/calField.xml",catList, attrList,attr);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Set<String> keys=xmlData.keySet();
		for(String key:keys){
			this.awardFiledList.add(key);
		}
		
	}
}
