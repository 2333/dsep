package com.dsep.vm.expert;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.dsep.entity.expert.ExpertSelectionRule;

@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class ExpertSelectionRuleVM {
	public ExpertSelectionRuleVM(ExpertSelectionRule rule) {
		setExpertSelectionRule(rule);
		setCreateTime(null);
		setModifyTime(null);
	}
		
	private ExpertSelectionRule expertSelectionRule;
	
	private String createTime;
	
	private String modifyTime;

	public ExpertSelectionRule getExpertSelectionRule() {
		return expertSelectionRule;
	}

	public void setExpertSelectionRule(ExpertSelectionRule expertSelectionRule) {
		this.expertSelectionRule = expertSelectionRule;
	}
	
	
	
	public void setCreateTime(String createTime) {
		this.createTime = timestamp2String(this.expertSelectionRule.getCreateDate());
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = timestamp2String(this.expertSelectionRule.getModifyDate());
	}

	public String getCreateTime() {
		return createTime;
	}

	public String getModifyTime() {
		return modifyTime;
	}
	
	private String timestamp2String(Timestamp t) {
		if (null != t) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
			return df.format(t);
		} else {
			return null;
		}
		
	}
	
}
