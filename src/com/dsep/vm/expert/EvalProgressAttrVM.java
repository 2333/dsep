package com.dsep.vm.expert;

/**
 * ★★注意！
 * AttrVM封装的实体的属性名aNameIndex、aPCTIndex等
 * 应该与前端colModelndex的字段名一致
 * 
 * 而且EvalProgressRowVM中的属性名display
 * 应该与前端arrtSetting()方法中的attr.display的dispaly叫一样的名称
 *
 * ★★
 * 之所以要把colModelndex中的index这个属性也加入进来，是为了方便构造JSON对象
 * colModelndex中的index属性是为了合并单元格而设立的，
 * EvalProgressRowVM内置的display属性为数字时表示合并单元格的个数，为"none"时表明此时这项不显示
 * 原来是colModelndex中的name属性承担这项显示工作，但是这样给后台构建JSON带来困难
 * 现在加入index属性，name属性只要设置为String即可，
 * 而index属性设置为一个类，内部维护String类型display的显示信息
 */
public class EvalProgressAttrVM {
	EvalProgressRowVM aNameIndex = new EvalProgressRowVM();
	EvalProgressRowVM aPCTIndex = new EvalProgressRowVM();
	EvalProgressRowVM bNameIndex = new EvalProgressRowVM();
	EvalProgressRowVM bPCTIndex = new EvalProgressRowVM();

	public EvalProgressRowVM getaNameIndex() {
		return aNameIndex;
	}

	public void setaNameIndex(EvalProgressRowVM aNameIndex) {
		this.aNameIndex = aNameIndex;
	}

	public EvalProgressRowVM getaPCTIndex() {
		return aPCTIndex;
	}

	public void setaPCTIndex(EvalProgressRowVM aPCTIndex) {
		this.aPCTIndex = aPCTIndex;
	}

	public EvalProgressRowVM getbNameIndex() {
		return bNameIndex;
	}

	public void setbNameIndex(EvalProgressRowVM bNameIndex) {
		this.bNameIndex = bNameIndex;
	}

	public EvalProgressRowVM getbPCTIndex() {
		return bPCTIndex;
	}

	public void setbPCTIndex(EvalProgressRowVM bPCTIndex) {
		this.bPCTIndex = bPCTIndex;
	}

}
