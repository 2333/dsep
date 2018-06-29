package com.dsep.util.briefsheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dsep.service.dsepmeta.dsepmetas.DMDiscIndexService;
import com.dsep.util.export.briefsheet.ResourceLoader;

public class XMLParser {
	private DMDiscIndexService discIndexService;
	//private static Element root;
	
	public List<List<String>> getDiscConfigList(String discId){
		SAXReader reader = new SAXReader();
   	 reader.setEncoding("utf-8");
   	 File in = new File(PathProvider.getTemplateRootPath(), "test.xml");
        Document doc;
		try {
			doc = reader.read(in);
			Element root = ((org.dom4j.Document) doc).getRootElement(); 
	    	String fileType = discIndexService.getCategotyByDiscId(discId);
	    	System.out.println(fileType);
	    	List<Element> elements = root.elements();  
	        List<String> tittlies = new ArrayList<String>();
	        List<String> entityIds = new ArrayList<String>();
	        if (elements != null && elements.size() > 0) {   
	            for (Element e: elements) {  
	            	if(e.getName().toUpperCase().equals(fileType.toUpperCase())){
	            		System.out.println(e.getName());
	            		List<Element> o= e.elements();
	            		for(int j=0;j<o.size();j++){
	            			tittlies.add(o.get(j).getName());
	            			entityIds.add(o.get(j).attributeValue("EntityId"));
	            		}
	                }
	            }
	        } 
	        List<List<String>> result = new ArrayList<List<String>>();
	        result.add(tittlies);
	        result.add(entityIds);
	        return result;
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}  
	}
	public List<List<String>> getTeahcerConfigList(String rootName){
	 SAXReader reader = new SAXReader();
   	 reader.setEncoding("utf-8");
   	 File in = new File(PathProvider.getTemplateRootPath(),"test.xml");
        Document doc;
		 try {
			doc = reader.read(in);
			Element root = ((org.dom4j.Document) doc).getRootElement();
	    	List<Element> elements = root.elements();  
	        List<String> tittlies = new ArrayList<String>();
	        List<String> entityIds = new ArrayList<String>();
	        if (elements != null && elements.size() > 0) {   
	            for (Element e: elements) {  
	            	if(e.getName().toUpperCase().equals(rootName)){
	            		System.out.println(e.getName());
	            		List<Element> o= e.elements();
	            		for(int j=0;j<o.size();j++){
	            			tittlies.add(o.get(j).getName());
	            			entityIds.add(o.get(j).attributeValue("EntityId"));
	            		}
	                }
	            }
	        } 
	        List<List<String>> result = new ArrayList<List<String>>();
	        result.add(tittlies);
	        result.add(entityIds);
	        return result;
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}  
	}
	/**
	 * 
	 * @param filePath 	xml文件路径
	 * @param fileName 	xml文件名
	 * @param rootEle 	要获得的根节点名
	 * @param attrName 	对应节点中要获得属性名
	 * @return
	 */
	public static Map<String, String> getEntries(String filePath, String fileName, String rootEle, String attrName){
		Map<String, String> entries = new LinkedHashMap<String, String>();
		Document doc = load(filePath, fileName);
		Element root = doc.getRootElement();
		Element ele = root.element(rootEle);
        for(Iterator it = ele.elementIterator(); it.hasNext(); ){      
            Element element = (Element) it.next();      
            entries.put(element.getName(), element.attributeValue(attrName));
        }      
		return entries;
	}
	/**
	 * 根据文件路径加载xml文件为document文档
	 * @param filePath xml存储路径
	 * @param fileName xml文件名
	 * @return
	 */
	public static Document load(String filePath, String fileName) {
		Document document = null;
		try {
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(new File(filePath, fileName));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return document;
	}   
}
