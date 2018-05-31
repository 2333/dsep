package com.dsep.service.datacalculate.RuleEngine.commander;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.dsep.service.datacalculate.RuleEngine.getdata.DataCode;

public class SumFactor implements ComputeCommander{

	@Override
	public double compute(List<Map<String,String>> data) {
		
		double result=0.0;
		boolean isSort=false;    //本末级指标是否有本单位，本学科排名百分比字段
		String sortField1="";
		String sortField2="";
		
		Map<String,String> commParams=data.get(0);
		Map<String,String> factorMap=data.get(1);
		
		Set<String> keySet=commParams.keySet();
		String field=commParams.get(DataCode.FIELD);      //要折算的字段
		
		//如果有获奖等级字段，则要把获奖等级字段和获奖名称字段合并为一个字段，
		//如--国家科技进步奖&特等奖--，把这个新字段的内容存在获奖名称中
		if(keySet.contains(DataCode.AWARDLEVEL)){    
			String awardField=commParams.get(DataCode.AWARDLEVEL);
			for(int i=2;i<data.size();i++){
				
				Map<String,String> mapItem=data.get(i);
				String newCont=mapItem.get(field)+"&"+mapItem.get(awardField);
				mapItem.put(field, newCont);
				data.set(i, mapItem);
			}
		}
		
		if(keySet.contains(DataCode.SORT)){
			isSort=true;
			String sortStr=commParams.get(DataCode.SORT);
			if(sortStr.contains("&")){//判断是有一个排名的字段还是两个排名的字段
				sortField1=sortStr.substring(0,sortStr.indexOf('&'));
				sortField2=sortStr.substring(sortStr.indexOf('&')+1);
			}else{
				sortField1=sortStr;
			}
		}
		
		if(isSort==false){    //没有排名
			for(int i=2;i<data.size();i++){//没有参与单位数，参与学科数
				Map<String,String> dataItem=data.get(i);
				
				String cont=dataItem.get(field);
				
				if(factorMap.keySet().contains(cont)){
					result+=Double.parseDouble(factorMap.get(cont));
				}
			}
		}else if(isSort==true){
			for(int i=2;i<data.size();i++){//有参与单位数，参与学科数
				Map<String,String> dataItem=data.get(i);
				String cont=dataItem.get(field);
				
				if(factorMap.keySet().contains(cont)==false)
					continue;                                  //如果本项在折算系数表中没有，则不计算本项
				
				double proportion;
				String sortValue=dataItem.get(sortField1);
				if(sortValue.contains("%"))
					proportion=percentFactor(sortValue);
				else
					proportion=sortFactor(sortValue);
				
				if(StringUtils.isNotBlank(sortField2)){   //如果还包含第二个排序字段
					sortValue=dataItem.get(sortField2);
					if(sortValue.contains("%"))
						proportion=proportion*percentFactor(sortValue);
					else
						proportion=proportion*sortFactor(sortValue);
				}
				
				result=result+Double.parseDouble(factorMap.get(cont))*proportion;
			}
		}
		
		return result;
	}
	
	/**
	 * 算出本学科或者本单位所占的比重
	 * @param strSort 传字符串n(m),n为总数，m为排名
	 * @return
	 */
	private double sortFactor(String strSort){           
		if(StringUtils.isBlank(strSort))
			return 1.0;
		
		strSort=strSort.replaceAll(" ", "");
		
		int n=0;
		int m=0;
		
		int index1=strSort.indexOf('(');
		int index2=strSort.indexOf(')');
		n=Integer.parseInt(strSort.substring(0,index1));
		m=Integer.parseInt(strSort.substring(index1+1,index2));
		
		double fenZi=Math.pow(2.0, n-m);
		double fenMu=0.0;
		
		for(int i=1;i<=n;i++){
			fenMu=fenMu+Math.pow(2.0, n-i);
		}
		
		double result=fenZi/fenMu;
		
		return result;
	}
	
	/**
	 * 算出百分比的比重
	 * @param strPercent  传字符串n[m%]
	 * @return
	 */
	private double percentFactor(String strPercent){
		if(StringUtils.isBlank(strPercent))
			return 1.0;
		
		strPercent=strPercent.replaceAll(" ", "");
		
		int index1=strPercent.indexOf('[');
		int index2=strPercent.indexOf('%');
		
		double result=Double.parseDouble(strPercent.substring(index1+1, index2))/100.0;
		
		return result;
	}

}