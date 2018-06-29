package com.dsep.entity.expert;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class EvalPaper implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3657348297970801745L;
	private String id;
	private String expertTypeId;
	//private String disciplineTypeId;
	//private String discId;
	private String discCatId;
	// 占位字段，发送邀请邮件的时候初始化所有参评学科学校
	private Boolean isMeta;
	private Set<EvalQuestion> evalQuestions = new HashSet<EvalQuestion>();
	private EvalBatch evalBatch = new EvalBatch();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExpertTypeId() {
		return expertTypeId;
	}

	public void setExpertTypeId(String expertTypeId) {
		this.expertTypeId = expertTypeId;
	}

	/*public String getDisciplineTypeId() {
		return disciplineTypeId;
	}

	public void setDisciplineTypeId(String disciplineTypeId) {
		this.disciplineTypeId = disciplineTypeId;
	}*/

	public Boolean getIsMeta() {
		return isMeta;
	}

	public String getDiscCatId() {
		return discCatId;
	}

	public void setDiscCatId(String discCatId) {
		this.discCatId = discCatId;
	}

	public void setIsMeta(Boolean isMeta) {
		this.isMeta = isMeta;
	}

	public Set<EvalQuestion> getEvalQuestions() {
		return evalQuestions;
	}

	public void setEvalQuestions(Set<EvalQuestion> evalQuestions) {
		this.evalQuestions = evalQuestions;
	}

	public EvalBatch getEvalBatch() {
		return evalBatch;
	}

	public void setEvalBatch(EvalBatch evalBatch) {
		this.evalBatch = evalBatch;
	}

}
