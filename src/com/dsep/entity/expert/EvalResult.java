package com.dsep.entity.expert;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonAutoDetect
@JsonIgnoreProperties(value = { "evalItemMessage" })
public class EvalResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1799121587658917081L;
	private String id;
	//private String unitId;
	//private String disciplineId;
	private String expertId;
	private EvalQuestion evalQuestion;
	private String evalValue;
	private String evalValueState;
	private Date firstEvalTime;
	private Date lastEvalTime;

	public EvalQuestion getEvalQuestion() {
		return evalQuestion;
	}

	public void setEvalQuestion(EvalQuestion evalQuestion) {
		this.evalQuestion = evalQuestion;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/*public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getDisciplineId() {
		return disciplineId;
	}

	public void setDisciplineId(String disciplineId) {
		this.disciplineId = disciplineId;
	}*/

	public String getExpertId() {
		return expertId;
	}

	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}

	public String getEvalValue() {
		return evalValue;
	}

	public void setEvalValue(String evalValue) {
		this.evalValue = evalValue;
	}

	public String getEvalValueState() {
		return evalValueState;
	}

	public void setEvalValueState(String evalValueState) {
		this.evalValueState = evalValueState;
	}

	public Date getFirstEvalTime() {
		return firstEvalTime;
	}

	public void setFirstEvalTime(Date firstEvalTime) {
		this.firstEvalTime = firstEvalTime;
	}

	public Date getLastEvalTime() {
		return lastEvalTime;
	}

	public void setLastEvalTime(Date lastEvalTime) {
		this.lastEvalTime = lastEvalTime;
	}

}
