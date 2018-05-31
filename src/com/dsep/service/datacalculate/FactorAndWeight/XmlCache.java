package com.dsep.service.datacalculate.FactorAndWeight;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.springframework.cache.annotation.Cacheable;

import com.dsep.util.datacalculate.ReadXml;

public class XmlCache {
	private Map<String,Map<String,String>> xmlData;
	
	@Cacheable(value="xmlData", key="#filePath")
	public Map<String, Map<String, String>> getXmlData(String filePath,
			List<String> catIds,List<String> attrs,String keyAttr) 
			throws DocumentException {
		System.out.println("缓存中未发现该记录");
		return ReadXml.readTwoLevelXml(filePath, catIds, attrs, "IndexItemId");
	}

}
