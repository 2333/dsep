package com.dsep.entity.expert;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonAutoDetect
@JsonIgnoreProperties(value = { "evalAchievements" })
public class EvalQuestion implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4712072037815597892L;
	private String id;
	private String questionName;
	/*private String evalItemName;
	private String collectId1;
	private String collectId2;
	private String collectId3;*/
	private Integer storageMode;
	private Integer QType;
	// 具体子问题的主键作为Question表的外键
	private String subQuestionId;
	// 占位字段，发送邀请邮件的时候初始化所有参评学科学校
	private Boolean isMeta;
	private String unitId;
	private String discId;
	private Set<EvalPaper> evalPapers = new HashSet<EvalPaper>();
	private Set<EvalResult> evalResults = new HashSet<EvalResult>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	public Integer getStorageMode() {
		return storageMode;
	}

	public void setStorageMode(Integer storageMode) {
		this.storageMode = storageMode;
	}

	public Integer getQType() {
		return QType;
	}

	public void setQType(Integer qType) {
		QType = qType;
	}

	public String getSubQuestionId() {
		return subQuestionId;
	}

	public void setSubQuestionId(String subQuestionId) {
		this.subQuestionId = subQuestionId;
	}

	public Boolean getIsMeta() {
		return isMeta;
	}

	public void setIsMeta(Boolean isMeta) {
		this.isMeta = isMeta;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getDiscId() {
		return discId;
	}

	public void setDiscId(String discId) {
		this.discId = discId;
	}

	public Set<EvalPaper> getEvalPapers() {
		return evalPapers;
	}

	public void setEvalPapers(Set<EvalPaper> evalPapers) {
		this.evalPapers = evalPapers;
	}

	public Set<EvalResult> getEvalResults() {
		return evalResults;
	}

	public void setEvalResults(Set<EvalResult> evalResults) {
		this.evalResults = evalResults;
	}

}
