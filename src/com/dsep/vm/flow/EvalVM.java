package com.dsep.vm.flow;

import com.dsep.entity.dsepmeta.Eval;
import com.dsep.util.Dictionaries;

public class EvalVM {
	private String stateName;
	private String isEval;
	private String isReport;
	private Eval eval;
	private String unitName;
	private String discName;
	public EvalVM(){
		
	}
	public EvalVM(Eval eval){
		setIsEval(eval.getIsEval());
		setIsReport(eval.getIsReport());
		setStateName(eval.getState());
		setEval(eval);
		setDiscName(eval.getDiscId());
		setUnitName(eval.getUnitId());
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(int stateName) {
		this.stateName = Dictionaries.getCollectFlowTypes(stateName);
	}
	public String getIsEval() {
		return isEval;
	}
	public void setIsEval(boolean isEval) {
		if(isEval)
		{
			this.isEval = "是";
		}else{
			this.isEval="否";
		}
			
	}
	public String getIsReport() {
		return isReport;
	}
	public void setIsReport(boolean isReport) {
		if(isReport)
		{
			this.isReport = "是";
		}	
		else {
			this.isReport="否";
		}
	}
	public Eval getEval() {
		return eval;
	}
	public void setEval(Eval eval) {
		this.eval = eval;
	}
	public String getDiscName() {
		return discName;
	}
	public void setDiscName(String discId) {
		this.discName = Dictionaries.getPureDisciplineName(discId);
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitId) {
		this.unitName = Dictionaries.getPureUnitName(unitId);
	}
	
}
