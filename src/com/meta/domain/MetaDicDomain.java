package com.meta.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dsep.domain.dsepmeta.jqcol.dicrule.JqGridColDicRule;
import com.dsep.util.StringDealUtil;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.meta.entity.MetaDicItem;
import com.meta.entity.MetaDic;

/*
 * 字典对象领域模型，包括某个类型的全部字典信息
 */
public class MetaDicDomain implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2582028225424611478L;
	/**
	 * 字典实体对象
	 */
	private MetaDic dic;
	/**
	 * 该字典领域对象中所包含的内容，以Key-Value的形式存储
	 */
	private Map<String, String> dicItems;
	/**
	 * 构造函数，根据一个字典实体对象初始化字典领域对象
	 * @param dic
	 */
	public MetaDicDomain(MetaDic dic){
		this.dic = dic;
		dicItems = new LinkedHashMap<String, String>();
		fillDic(null);
	}
	/**
	 * 根据规则筛选字典条目
	 * @param dic
	 * @param dicRules
	 */
	public MetaDicDomain(MetaDic dic,List<JqGridColDicRule> dicRules){
		this.dic = dic;
		dicItems = new LinkedHashMap<String, String>();
		fillDic(dicRules);
	}
	/**
	 * 根据字典数据类型的Key ID，获得value
	 * 返回""则表示没有该key
	 * @param key
	 * @return
	 */
	public String get(String key){
		if(dicItems.containsKey(key)){
			return dicItems.get(key);
		}else{
			return "";
		}		
	}
	/**
	 * 返回当前字典类型中所有的Key
	 * @return
	 */
	public Set<String> keySet(){
		return dicItems.keySet();
	}
	/**
	 * 根据字典数据类型的value，获得对应的Key，此操作效率较低，建议减少使用，而且如有有value对应多个key，则至返回第一个key
	 * 返回""则表示没有该value
	 * @param value
	 * @return
	 */
	public String getKey(String value){
		Set<String> keys = dicItems.keySet();
		for(String key : keys)
		{
			String foundKey =dicItems.get(key); 
			if(foundKey.equals(value)){
				return foundKey;
			}
		}
		return "";
	}
	/**
	 * 获得当前字典ID
	 * @return
	 */
	public String getId(){
		return dic.getId();
	}
	/**
	 * 获得当前字典名称
	 * @return
	 */
	public String getName(){
		return dic.getName();
	}
	/**
	 * 内部函数，将字典实体对象转化为字典领域对象
	 * @param dic
	 */
	public Map<String, List<String>> getLinkMap(){
		Map<String, List<String>> linkMap = new HashMap<String, List<String>>(0);
		for(MetaDicItem item:dic.getDicItemSet()){
			if(linkMap.containsKey(item.getpId())){
				linkMap.get(item.getpId()).add(item.getId());
			}else{
				List<String> valuse = new ArrayList<String>(0);
				valuse.add(item.getId());
				linkMap.put(item.getpId(), valuse);
			}
		}
		return linkMap;
	}
	private void fillDic(List<JqGridColDicRule> dicRules){
		Set<MetaDicItem> dicItemSet = dic.getDicItemSet();
		boolean insert = false;
		for(MetaDicItem item : dicItemSet){
			if(dicRules==null||dicRules.size()==0){
				insert=true;
			}else{
				List<JqGridColDicRule> itemRules = getColDicRules(item);
				if(itemRules==null||itemRules.size()==0||
						itemRules.containsAll(dicRules)){
					insert = true;
				}
			}
			if(insert){
				dicItems.put(item.getId(), item.getId());
			}
			
		}
	}
	public MetaDic getDic() {
		return dic;
	}
	public void setDic(MetaDic dic) {
		this.dic = dic;
	}
	public Map<String, String> getDicItems() {
		return dicItems;
	}
	public void setDicItems(Map<String, String> dicItems) {
		this.dicItems = dicItems;
	}
	
	private List<JqGridColDicRule> getColDicRules(MetaDicItem item){
		if(item.getRules()==null) return null;
		String[] strRules = item.getRules().split(";");
		List<JqGridColDicRule> dicRules = new ArrayList<JqGridColDicRule>(0);
		for(String strRule:strRules){
			String type = strRule.split(":")[0];
			String[] values = StringDealUtil.getSubStrFromMidBrackets(strRule.split(":")[1]).split(",");
			JqGridColDicRule dicRule = new JqGridColDicRule(type,values);
			dicRules.add(dicRule);
 		}
		return dicRules;
	}
	
}
