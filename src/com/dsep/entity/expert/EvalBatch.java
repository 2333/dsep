package com.dsep.entity.expert;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@JsonAutoDetect
@JsonIgnoreProperties(value = {"rules","experts","papers"})
public class EvalBatch implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private Integer batchNum;
	private String batchChName;
	private Date beginDate;
	private Date endDate;
	// 用于显示
	private String beginDateStr;
	private String endDateStr;
	
	private Integer currentStatus;
	
	private Set<Expert> experts = new HashSet<Expert>();

	private Set<EvalPaper> papers = new HashSet<EvalPaper>();

	private Set<ExpertSelectionRule> rules = new HashSet<ExpertSelectionRule>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(Integer batchNum) {
		this.batchNum = batchNum;
	}

	public String getBatchChName() {
		return batchChName;
	}

	public void setBatchChName(String batchChName) {
		this.batchChName = batchChName;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	

	public String getBeginDateStr() {
		return beginDateStr;
	}

	public void setBeginDateStr(String beginDateStr) {
		this.beginDateStr = beginDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public Integer getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(Integer currentStatus) {
		this.currentStatus = currentStatus;
	}

	public Set<EvalPaper> getPapers() {
		return papers;
	}

	public void setPapers(Set<EvalPaper> papers) {
		this.papers = papers;
	}

	public Set<Expert> getExperts() {
		return experts;
	}

	public void setExperts(Set<Expert> experts) {
		this.experts = experts;
	}

	public Set<ExpertSelectionRule> getRules() {
		return rules;
	}

	public void setRules(Set<ExpertSelectionRule> rules) {
		this.rules = rules;
	}

}
