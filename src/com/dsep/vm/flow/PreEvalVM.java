package com.dsep.vm.flow;

import com.dsep.entity.dsepmeta.PreEval;
import com.dsep.util.Dictionaries;

public class PreEvalVM {
	private PreEval preEval;
	private String state;
	private String discName;
	private String unitName;
	public PreEvalVM()
	{
		
	}
	public PreEvalVM(PreEval preEval)
	{
		this.preEval=preEval;
		setState(preEval.getState());
		setDiscName(preEval.getDiscId());
		setUnitName(preEval.getUnitId());
	}
	public PreEval getPreEval() {
		return preEval;
	}
	public void setPreEval(PreEval preEval) {
		this.preEval = preEval;
	}
	public String getState() {
		return state;
	}
	public void setState(int state) {
		if(state==1){
			this.state="已提交至中心";
		}
		if (state==0) {
			this.state="学校未提交";
		} 
			
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
