package com.dsep.vm.expert;

import java.text.DecimalFormat;

import com.dsep.domain.dsepmeta.expert.EvalIndicIdxAndScore;
import com.dsep.entity.expert.EvalResult;

public class EvalIndicIdxVM {
	private String aId;
	private String aName;
	private String aVal;
	private String aActualVal;
	private String bId;
	private String bName;
	private String bVal;
	private String bActualVal;
	// 由二级指标找到一级指标的标志，记录了二级指标父一级指标第一行ID
	private String bToAAnchor;
	private String cId;
	private String cName;
	private String cVal;
	private String cActualVal;
	// 由三级指标找到二级指标的标志，记录了三级指标父二级指标第一行ID
	private String cToBAnchor;

	private String oldScore;
	private String score;
	private String state;
	private String questionId;
	private String resultId;

	private EvalIndiIdxAttrVM attr = new EvalIndiIdxAttrVM();

	public EvalIndicIdxVM(EvalIndicIdxAndScore indiIdxAndScore,
			EvalResult result) {
		this.questionId = indiIdxAndScore.getQuestionId();
		this.aId = indiIdxAndScore.getL3rdIdxMapVM().getGrandPId();
		this.aName = indiIdxAndScore.getL3rdIdxMapVM().getGrandPName();
		this.aVal = indiIdxAndScore.getL3rdIdxMapVM().getGrandPWeight();
		
		this.bId = indiIdxAndScore.getL3rdIdxMapVM().getpId();
		this.bName = indiIdxAndScore.getL3rdIdxMapVM().getpName();
		this.bVal = indiIdxAndScore.getL3rdIdxMapVM().getpWeight();
		

		this.cId = indiIdxAndScore.getL3rdIdxMapVM().getId();
		this.cName = indiIdxAndScore.getL3rdIdxMapVM().getName();
		this.cVal = indiIdxAndScore.getL3rdIdxMapVM().getWeight();

		if (result != null) {
			this.resultId = result.getId();
			this.oldScore = this.score = result.getEvalValue();
			this.state = result.getEvalValueState();
		} else {
			this.oldScore = this.score = "";
		}
	}

	public EvalIndicIdxVM(EvalIndicIdxVM vm, String bToAAnchor,
			String cToBAnchor, String aCols, String bCols, String cCols) {
		this.aId = vm.getaId();
		this.aName = vm.getaName(); 
		this.aVal = vm.getaVal();
		this.bId = vm.getbId();
		this.bName = vm.getbName();
		this.bVal = vm.getbVal();
		this.cId = vm.getcId();
		this.cVal = vm.getcVal();
		this.cName = vm.getcName();
		this.cActualVal = vm.getScore();
		this.oldScore = this.score = vm.getOldScore();
		this.state = vm.getState();
		this.questionId = vm.getQuestionId();
		this.resultId = vm.getResultId();
		this.getAttr().setaNameIndex(new EvalIndicIdxRowVM(aCols));
		this.getAttr().setaValIndex(new EvalIndicIdxRowVM(aCols));
		this.getAttr().setbNameIndex(new EvalIndicIdxRowVM(bCols));
		this.getAttr().setbValIndex(new EvalIndicIdxRowVM(bCols));
		this.getAttr().setcNameIndex(new EvalIndicIdxRowVM(cCols));
		this.getAttr().setcValIndex(new EvalIndicIdxRowVM(cCols));
		this.bToAAnchor = bToAAnchor;
		this.cToBAnchor = cToBAnchor;
		
	}

	public String getaId() {
		return aId;
	}

	public void setaId(String aId) {
		this.aId = aId;
	}

	public String getbToAAnchor() {
		return bToAAnchor;
	}

	public void setbToAAnchor(String bToAAnchor) {
		this.bToAAnchor = bToAAnchor;
	}

	public String getcToBAnchor() {
		return cToBAnchor;
	}

	public void setcToBAnchor(String cToBAnchor) {
		this.cToBAnchor = cToBAnchor;
	}

	public String getaName() {
		return aName;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public void setaName(String aName) {
		this.aName = aName;
	}

	public String getaVal() {
		return aVal;
	}

	public void setaVal(String aVal) {
		this.aVal = aVal;
	}

	public String getbId() {
		return bId;
	}

	public void setbId(String bId) {
		this.bId = bId;
	}

	public String getbName() {
		return bName;
	}

	public void setbName(String bName) {
		this.bName = bName;
	}

	public String getbVal() {
		return bVal;
	}

	public void setbVal(String bVal) {
		this.bVal = bVal;
	}

	public String getcId() {
		return cId;
	}

	public void setcId(String cId) {
		this.cId = cId;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getcVal() {
		return cVal;
	}

	public void setcVal(String cVal) {
		this.cVal = cVal;
	}

	public EvalIndiIdxAttrVM getAttr() {
		return attr;
	}

	public void setAttr(EvalIndiIdxAttrVM attr) {
		this.attr = attr;
	}

	public String getOldScore() {
		return oldScore;
	}

	public void setOldScore(String oldScore) {
		this.oldScore = oldScore;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getQuestionId() {
		return questionId;
	}

	public String getaActualVal() {
		return aActualVal;
	}

	public void setaActualVal(String aActualVal) {
		this.aActualVal = convertToFloatFormat(aActualVal);
	}

	public String getbActualVal() {
		return bActualVal;
	}

	public void setbActualVal(String bActualVal) {
		this.bActualVal = convertToFloatFormat(bActualVal);
	}

	public String getcActualVal() {
		return cActualVal;
	}

	public void setcActualVal(String cActualVal) {
		this.cActualVal = convertToFloatFormat(cActualVal);
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getResultId() {
		return resultId;
	}

	public void setResultId(String resultId) {
		this.resultId = resultId;
	}
	
	private String convertToFloatFormat(String num) {
		if (null == num || "".equals(num)) return "0.00";
		if (num.contains(".")) {
			 float scale = Float.valueOf(num);   
			 DecimalFormat fnum = new DecimalFormat("##0.00");    
			 return fnum.format(scale);  
		} else {
			return num + ".00";
		}
	}

}