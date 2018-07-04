package com.dsep.service.file;

import com.dsep.util.briefsheet.AbstractPDF;

public interface BriefsheetService {
	/**
	 * 接收简况表基类，利用多态生成各自的简况表
	 * @param briefSheet 一个简况表实例，由基类BasePDF接收
	 * @return 返回controller需要的字符串对象
	 * @throws Exception 
	 */
	String generateBriefSheet(AbstractPDF briefSheet) throws Exception;

}
