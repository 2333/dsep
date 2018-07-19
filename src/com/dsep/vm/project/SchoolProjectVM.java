package com.dsep.vm.project;

import com.dsep.entity.project.SchoolProject;
import com.dsep.util.DateProcess;
import com.dsep.util.Dictionaries;

public class SchoolProjectVM {
	
	private SchoolProject project;
	private String startDate;
	private String endDate;
	private String currentState;
	
	public SchoolProjectVM( SchoolProject project )
	{
		this.project=project;
		this.setCurrentState(Dictionaries.getSchoolProjectState(project.getCurrentState()));
		this.setStartDate(DateProcess.getShowingDate(project.getStartDate()));
		this.setEndDate(DateProcess.getShowingDate(project.getEndDate()));
	}
	
	public SchoolProject getProject() {
		return project;
	}
	public void setProject(SchoolProject project) {
		this.project = project;
	}
	public String getStartDate() {
		return DateProcess.getShowingDate(this.project.getStartDate());
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return DateProcess.getShowingDate(this.project.getEndDate());
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getCurrentState() {
		return Dictionaries.getSchoolProjectState(this.project.getCurrentState());
	}
	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}
	
	
}
