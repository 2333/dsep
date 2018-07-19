package com.dsep.vm;

import com.dsep.entity.Right;
import com.dsep.util.Dictionaries;

public class RightVM {

	private Right right;
	private String rightTypeName;
	
	public void setRight(Right right) {
		this.right = right;
	}
	public Right getRight() {
		return right;
	}

	public String getRightTypeName() {
		this.rightTypeName=Dictionaries.getRightTypeName(this.right.getCategory());
		return rightTypeName;
	}
	public void setRightTypeName(String rightTypeName) {
		this.rightTypeName = rightTypeName;
	}


}
