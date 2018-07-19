package com.meta.entity;

import java.io.Serializable;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;


public enum MetaDataType  implements Serializable {
	CHAR("CHAR"),VARCHAR2("VARCHAR2"), NVARCHAR2("NVARCHAR2"), 
	INT("INT"),
	DOUBLE("DOUBLE"),
	DATE("DATE"),
	DATETIME("DATETIME"),
	YEAR("YEAR"),
	YEARMONTH("YEARMONTH"),
	DIC("DIC"),
	PERCENT("PERCENT"),
	FILE("FILE");
	
	private final String dataType;
	private MetaDataType(String dataType){
		this.dataType = dataType;
	}
	public String getDataType(){
		return dataType;
	}
	
	@Override
	public String toString() {
		return dataType;
	}
	/**
	 * 
	 * @param state
	 * @return
	 */
	public static MetaDataType getDataType(String dataType)
	{
		String upperDataType=dataType.toUpperCase();
		if(upperDataType.equals("CHAR")) return MetaDataType.CHAR;
		if(upperDataType.equals("VARCHAR2")) return MetaDataType.VARCHAR2;
		if(upperDataType.equals("NVARCHAR2")) return MetaDataType.NVARCHAR2;
		if(upperDataType.equals("INT")) return MetaDataType.INT;
		if(upperDataType.equals("DOUBLE")) return MetaDataType.DOUBLE;
		if(upperDataType.equals("DATE")) return MetaDataType.DATE;
		if(upperDataType.equals("DATETIME")) return MetaDataType.DATETIME;
		if(upperDataType.equals("YEAR")) return MetaDataType.YEAR;
		if(upperDataType.equals("YEARMONTH")) return MetaDataType.YEARMONTH;
		if(upperDataType.equals("DIC")) return MetaDataType.DIC;
		if(upperDataType.equals("PERCENT")) return MetaDataType.PERCENT;
		if(upperDataType.equals("FILE")) return MetaDataType.FILE;
		
		return MetaDataType.VARCHAR2;
	}

}
