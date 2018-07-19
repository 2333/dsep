package com.dsep.vm.brief;

import java.util.Date;

import com.dsep.entity.Teacher;
import com.dsep.entity.dsepmeta.Eval;
import com.dsep.util.DateProcess;
import com.dsep.util.Dictionaries;

public class DiscBriefVM {
	
	private Eval eval;//参评信息
	private String unitId;//学校代码
	private String unitName;//学校名称
	private String discId;//学科代码
	private String discName;//学科名称
	private String briefState;//简况表状态
	private String createDate;// 创建日期
	private String briefId;//简况表Id
	public DiscBriefVM() {
		// TODO Auto-generated constructor stub
	}
	public DiscBriefVM(Eval eval){
		this.eval = eval;
		this.unitId = eval.getUnitId();
		this.discId = eval.getDiscId();
		this.unitName = Dictionaries.getPureUnitName(unitId);
		this.discName = Dictionaries.getPureDisciplineName(discId);
		this.briefId = eval.getBriefId();
		setBriefState(eval);
		setCreateDate(eval);
	}
	public DiscBriefVM(Teacher teacher){
		this.briefId = teacher.getBriefId();
		setBriefState(teacher);
		setCreateDate(teacher);
		
	}
	public String getBriefId() {
		return briefId;
	}
	public void setBriefId(String briefId) {
		this.briefId = briefId;
	}
	public Eval getEval() {
		return eval;
	}
	public void setEval(Eval eval) {
		this.eval = eval;
	}
	public String getBriefState() {
		return briefState;
	}
	public void setBriefState(Eval eval) {
		if(eval.getBriefId()==null){
			this.briefState = "未生成";
		}else{
			this.briefState = "已生成";
		}
	}
	public void setBriefState(Teacher teacher){
		if(teacher.getBriefId()==null){
			this.briefState="未生成";
		}else{
			this.briefState = "已生成";
		}
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Eval eval) {
		if(eval.getCreateBriefTime()!=null)
			this.createDate = DateProcess.getShowingDate(eval.getCreateBriefTime());
	}
	public void setCreateDate(Teacher teacher) {
		if(teacher.getCreateBriefTime()!=null)
			this.createDate = DateProcess.getShowingDate(teacher.getCreateBriefTime());
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getDiscId() {
		return discId;
	}
	public void setDiscId(String discId) {
		this.discId = discId;
	}
	public String getDiscName() {
		return discName;
	}
	public void setDiscName(String discName) {
		this.discName = discName;
	}
	public void setBriefState(String briefState) {
		this.briefState = briefState;
	}
	
}
