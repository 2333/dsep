package com.dsep.util.datacalculate;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dsep.util.export.briefsheet.ResourceLoader;

public class ReadXml {

	/**
	 * 读取末级指标进行百分制转换时，需要进行特殊计算的指标项 
	 * @param fielPath  要读取的文件名称
	 * @return  读取的xml文档的内容
	 * @throws DocumentException
	 */
	public static List<Map<String,String>> readOneLevelXml(String fielPath,List<String> attrs) throws DocumentException{
		
		List<Map<String,String>> xmlData=new LinkedList<Map<String,String>>();
		SAXReader reader = new SAXReader(); 
   	    reader.setEncoding("utf-8");
   	    File in = new File(ResourceLoader.getPath(fielPath));
        Document doc;
        int length=attrs.size();
        	doc = reader.read(in);
			Element root = ((org.dom4j.Document) doc).getRootElement();
			List<Element> elementList = root.elements();
			for(Element element:elementList){
				Map<String,String> item=new HashMap<String,String>();
				for(int i=0;i<length;i++){
					String attr=attrs.get(i);
					String value=element.attributeValue(attr);
					item.put(attr, value);
				}
				xmlData.add(item);
			}
		return xmlData;
	}
	
	
	public static Map<String,Map<String,String>>readTwoLevelXml(String filePath,List<String> catIdlist,List<String> attrs,String keyAttr)throws DocumentException{
		
		Map<String,Map<String,String>> xmlData=new HashMap<String,Map<String,String>>();
		SAXReader reader = new SAXReader(); 
   	    reader.setEncoding("utf-8");
   	    File in = new File(ResourceLoader.getPath(filePath));
        Document doc;
        doc = reader.read(in);
		Element root = ((org.dom4j.Document) doc).getRootElement();
		List<Element> elements1 = root.elements();
		
		for(Element e1:elements1){
			for(String catId:catIdlist){
				if(e1.getName().equals(catId)){
	    			List<Element> elements2 = e1.elements();
	    			for(Element e2:elements2){
	    				Map<String,String> item=new HashMap<String,String>();
	    				String keyStr="";
	    				for(String attr:attrs){
	    					String value= e2.attributeValue(attr);
	    					if(attr.equals("Formula"))
	    					value = value.replaceAll("\"", "\'");
	    					item.put(attr,value);
	    					if(attr.equals(keyAttr)) keyStr=value;
	    				}
	    				xmlData.put(keyStr, item);
	    			}
			}
    		
    		}
		}
		return xmlData;
	}
	/**
	 * 读取需要扣分的末级指标
	 * @param catId
	 * @return
	 * @throws DocumentException
	 */
	public static Map<String,String> readDeductIndex(String catId) throws DocumentException{
		Map<String,String> result=new HashMap<String,String>();
//		String catId=discCategoryDao.getCatByDiscId(discId);
		List<Map<String,String>> xmlData=new LinkedList<Map<String,String>>();
		SAXReader reader = new SAXReader(); 
   	    reader.setEncoding("utf-8");
   	    File in = new File(ResourceLoader.getPath("template/indicFormula.xml"));
        Document doc;
        doc = reader.read(in);
		Element root = ((org.dom4j.Document) doc).getRootElement();
		List<Element> elements1 = root.elements();
		for(Element e1:elements1){
    		if(e1.getName().equals(catId)){
    			List<Element> elements2 = e1.elements();
    			for(Element e2:elements2){
    				String deductFlag=e2.attributeValue("deduct");
    				if(StringUtils.isNotBlank(deductFlag)&&deductFlag.equals("true")){
    					String formula=e2.attributeValue("Formula");
    					formula=formula.replaceAll("\"", "\'");
    					result.put(e2.attributeValue("IndexItemId"),formula);
    				}
    			}
    		}
    	}
		return result;
	}
}
