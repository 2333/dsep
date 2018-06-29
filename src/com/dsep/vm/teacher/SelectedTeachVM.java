package com.dsep.vm.teacher;

import com.dsep.entity.dsepmeta.TeachDisc;

public class SelectedTeachVM {
	private TeachDisc teachDisc;
	
	public SelectedTeachVM(){
		
	}
	public SelectedTeachVM(TeachDisc teachDisc){
		this.teachDisc = teachDisc;
	}
	public TeachDisc getTeachDisc() {
		return teachDisc;
	}

	public void setTeachDisc(TeachDisc teachDisc) {
		this.teachDisc = teachDisc;
	}
	

}
