package com.dsep.domain.dsepmeta.viewconfig;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;



public enum ViewType {
	JQGRID(0),
	FORM(1),
	JQFORM(2),
	ReadOnlyJQGRID(3),
	CKFORM(4),
	INITJQGRID(5),
	FILEFORM(6);
	private final int viewType;
	
	private ViewType(int i){
		this.viewType = i;
	}

	public int getViewType() {
		return viewType;
	}
	
	public static ViewType getViewType(String type){
		if(type.equals("T"))return ViewType.JQGRID;
		if(type.equals("ROT"))return ViewType.ReadOnlyJQGRID;
		if(type.equals("F"))return ViewType.FORM;
		if(type.equals("TF"))return ViewType.JQFORM; 
		if(type.equals("CF")) return ViewType.CKFORM;
		if(type.equals("IQ")) return ViewType.INITJQGRID;//在生成jqgrid的时候需要初始化
		if(type.equals("FF")) return ViewType.FILEFORM;//上传附件的FORM
		return ViewType.JQGRID;
	}
}
