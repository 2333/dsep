package com.dsep.util;

import java.util.Date;

import com.meta.domain.MetaAttrDomain;

public class CollectSampleData {
	
	public static String createExcelSample(MetaAttrDomain attrDomains){
		switch(attrDomains.getDataTypeObject()){
			case DATE:
			case DATETIME:
				return DateProcess.getShowingDate(new Date());
			case DIC:
				 return attrDomains.getDicItems().keySet().iterator().next();
			case INT:
			case DOUBLE:
				return "0";
			case YEAR:
				return DateProcess.getShowYear(new Date());
			case YEARMONTH:
				return DateProcess.getShowYearMonth(new Date());
			case PERCENT:
				return "1(1)";
			case FILE:
				return "请上传附件";
			default:
				return "**"+attrDomains.getChsName()+"**";
		}
	}
}
