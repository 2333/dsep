package com.dsep.vm.teacher;

import com.dsep.entity.Teacher;

public class ViewTeachVM {
	private Teacher teacher;
	private boolean isSelected;//是否教师被选中
	
	public ViewTeachVM(){
		
	}
	public ViewTeachVM(Teacher teacher,boolean isSelected){
		this.isSelected = isSelected;
		this.setTeacher(teacher);
	}
	
	public boolean getIsSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	

}
