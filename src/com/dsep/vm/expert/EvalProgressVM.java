package com.dsep.vm.expert;

public class EvalProgressVM {
	private String aName;
	private String aPCT;
	private String bName;
	private String bPCT;
	private EvalProgressAttrVM attr = new EvalProgressAttrVM();
	private String cLink;


	public String getaName() {
		return aName;
	}

	public void setaName(String aName) {
		this.aName = aName;
	}

	public String getaPCT() {
		return aPCT;
	}

	public void setaPCT(String aPCT) {
		this.aPCT = aPCT;
	}

	public String getbName() {
		return bName;
	}

	public void setbName(String bName) {
		this.bName = bName;
	}

	public String getbPCT() {
		return bPCT;
	}

	public void setbPCT(String bPCT) {
		this.bPCT = bPCT;
	}

	public String getcLink() {
		return cLink;
	}

	public void setcLink(String cLink) {
		this.cLink = cLink;
	}

	public EvalProgressAttrVM getAttr() {
		return attr;
	}

	public void setAttr(EvalProgressAttrVM attr) {
		this.attr = attr;
	}

}