package com.dsep.domain.dsepmeta.viewconfig.views;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dsep.util.StringDealUtil;
import com.meta.domain.MetaEntityDomain;

public class InitedJqGridConfig extends JqgridViewConfig{
	private Map<String, List<String>> initColNameAndValues;
	private int initRows;
	private Map<String, List<String>> controlTypeRules;
	public InitedJqGridConfig(MetaEntityDomain e){
		super(e);
		setInitColNameAndValues(e);
		setInitRows(e);
		setControlTypeRules(e);
	}

	public Map<String, List<String>> getInitColNameAndValues() {
		return initColNameAndValues;
	}
	
	public void setInitColNameAndValues(MetaEntityDomain e) {
		if(e.getInitRule()!=null){
			String []rules = e.getInitRule().split(";");
			for(String rule:rules){
				String colName = rule.split(":")[0];
				String[] values = StringDealUtil.getSubStrFromMidBrackets(
						rule.split(":")[1]).split(",");
				//List<String> valueList = new ArrayList<String>(0);
				//valueList.addAll(Arrays.asList(values));
				if(this.initColNameAndValues==null){
					this.initColNameAndValues = new LinkedHashMap<String, List<String>>(0);
					this.initColNameAndValues.put(colName, Arrays.asList(values));
				}else if(this.initColNameAndValues.containsKey(colName)){
					this.initColNameAndValues.get(colName).addAll(Arrays.asList(values));
				}else{
					this.initColNameAndValues.put(colName,Arrays.asList(values));
				}
			}
		}else{
			this.initColNameAndValues = new LinkedHashMap<String, List<String>>(0);
		}
		
	}

	public int getInitRows() {
		return initRows;
	}

	public void setInitRows(MetaEntityDomain e) {
		this.initRows = e.getInitRows();
	}

	public Map<String, List<String>> getControlTypeRules() {
		return controlTypeRules;
	}

	public void setControlTypeRules(MetaEntityDomain e) {
		if(e.getControlTypeRles()!=null){
			String []rules = e.getControlTypeRles().split(";");
			for(String rule:rules){
				String type = rule.split(":")[0];
				String[] values = StringDealUtil.getSubStrFromMidBrackets(
						rule.split(":")[1]).split(",");
				if(this.controlTypeRules==null){
					this.controlTypeRules = new HashMap<String,List<String>>(0);
					this.controlTypeRules.put(type, Arrays.asList(values));
				}else if(this.controlTypeRules.containsKey(type)){
					controlTypeRules.get(type).addAll(Arrays.asList(values));
				}else{
					this.controlTypeRules.put(type, Arrays.asList(values));
				}
			}
		}
	}
}
