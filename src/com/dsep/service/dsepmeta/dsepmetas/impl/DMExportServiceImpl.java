package com.dsep.service.dsepmeta.dsepmetas.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.apache.commons.lang.StringUtils;



import com.dsep.service.dsepmeta.MetaOper;
import com.dsep.service.dsepmeta.dsepmetas.DMDiscIndexService;
import com.dsep.service.dsepmeta.dsepmetas.DMExportService;
import com.dsep.util.FileOperate;
import com.dsep.util.JsonConvertor;
import com.dsep.util.briefsheet.PathProvider;
import com.dsep.util.export.briefsheet.HtmlGenerator;
import com.dsep.util.export.briefsheet.ITextRendererObjectFactory;
import com.dsep.util.export.briefsheet.PdfGenerator;
import com.dsep.util.export.briefsheet.ResourceLoader;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.itextpdf.text.pdf.events.IndexEvents.Entry;
import com.meta.domain.MetaAttrDomain;
import com.meta.domain.MetaEntityDomain;
import com.meta.domain.search.SearchGroup;
import com.meta.domain.search.SearchRule;
import com.meta.domain.search.SearchType;

import freemarker.template.TemplateException;

public class DMExportServiceImpl extends MetaOper implements DMExportService {
	
	private final static Logger logger = Logger.getLogger(DMExportServiceImpl.class);
	private DMDiscIndexService discIndexService;
	
	public DMDiscIndexService getDiscIndexService() {
		return discIndexService;
	}

	public void setDiscIndexService(DMDiscIndexService discIndexService) {
		this.discIndexService = discIndexService;
	}

	@Override
	public String exportExcel(String unitId,String discId,String entityId,String occasion,boolean sortOrder, String orderPropName,String rootPath) {
		// TODO Auto-generated method stub
		return exportExcel(unitId, discId, entityId, occasion, sortOrder, orderPropName, rootPath, null);
		/*MetaEntityDomain metaEntityDomain = metaEntityService.getEntityDomain(entityId);
		//get sheetName
		List<String> sheetName = new ArrayList<String>(); 
		sheetName.add(metaEntityDomain.getChsName());
		//get titleValues && titleNames
		List<MetaAttrDomain> attrDomains = metaEntityDomain.getAttrDomains();
		List<String> titleValues = new ArrayList<String>();
		List<List<String>> titleNames = new ArrayList<List<String>>();
		titleNames.add(new ArrayList<String>());
		for(int i=0; i < attrDomains.size(); ++i){
			if(!attrDomains.get(i).isHidden()){
				titleValues.add(attrDomains.get(i).getName());
				titleNames.get(0).add(attrDomains.get(i).getChsName());
			}
		}
		//get rowData
		Map<String, Object> params = new HashMap<String, Object>();
		if (!params.containsKey("UNIT_ID")) {
			params.put("UNIT_ID", unitId);// 单位代码
		}
		if (!params.containsKey("DISC_ID")) {
			params.put("DISC_ID", discId);// 学科代码
		}
		List<List<String[]>> rowStrings = new ArrayList<List<String[]>>();
		rowStrings.add(super.getData(entityId, titleValues, params, orderPropName, sortOrder, 0, 0));
		Map<String, String> excelPathMap = FileOperate.exportExcel(titleNames, rowStrings, sheetName, rootPath, "excel");
		return JsonConvertor.obj2JSON(excelPathMap);*/
	}
	@Override
	public String exportExcel(String unitId, String discId, String entityId,
			String occasion,boolean sortOrder, String orderPropName, String rootPath,
			SearchGroup searchGroup) {
		// TODO Auto-generated method stub
		MetaEntityDomain metaEntityDomain = metaEntityService.getEntityDomain(entityId,occasion);
		//get sheetName
		List<String> sheetName = new ArrayList<String>(); 
		sheetName.add(metaEntityDomain.getChsName());
		//get titleValues && titleNames
		List<MetaAttrDomain> attrDomains = metaEntityDomain.getAttrDomains();
		List<String> titleValues = new ArrayList<String>();
		List<List<String>> titleNames = new ArrayList<List<String>>();
		titleNames.add(new ArrayList<String>());
		for(int i=0; i < attrDomains.size(); ++i){
			if(!attrDomains.get(i).isHidden()){
				titleValues.add(attrDomains.get(i).getName());
				titleNames.get(0).add(attrDomains.get(i).getChsName());
			}
		}
		//get rowData
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(unitId) && StringUtils.isNotBlank(discId) ){
			if (!params.containsKey("UNIT_ID")) {
				params.put("UNIT_ID", unitId);// 单位代码
			}
			if (!params.containsKey("DISC_ID")) {
				params.put("DISC_ID", discId);// 学科代码
			}
		}
		
		List<List<String[]>> rowStrings = new ArrayList<List<String[]>>();
		StringBuilder sqlCondition = new StringBuilder("");
		if(searchGroup!=null){
			sqlCondition.append(searchGroup.toString());
		}
		rowStrings.add(super.getData(entityId, titleValues, params, sqlCondition.toString(), orderPropName, sortOrder, 0, 0));
		//rowStrings.add(super.getData(entityId, titleValues, params, orderPropName, sortOrder, 0, 0));
		Map<String, String> excelPathMap = FileOperate.exportExcel(metaEntityDomain.getChsName(),titleNames, rowStrings, sheetName, rootPath, "excel");
		return JsonConvertor.obj2JSON(excelPathMap);
	}
	@Override
	public String exportTeacherExcelByEntityId(String entityId,
			List<Object> teacherList, boolean sortOrder, String orderPropName,
			String rootPath, Map<String, String> additionalParams,
			SearchGroup searchGroup) {
		// TODO Auto-generated method stub
		MetaEntityDomain metaEntityDomain = metaEntityService.getEntityDomain(entityId);
		//get sheetName
		List<String> sheetName = new ArrayList<String>(); 
		sheetName.add(metaEntityDomain.getChsName());
		//get titleValues && titleNames
		List<MetaAttrDomain> attrDomains = metaEntityDomain.getAttrDomains();
		List<String> titleValues = new ArrayList<String>();
		List<List<String>> titleNames = new ArrayList<List<String>>();
		titleNames.add(new ArrayList<String>());
		for(int i=0; i < attrDomains.size(); ++i){
			if(!attrDomains.get(i).isHidden()){
				titleValues.add(attrDomains.get(i).getName());
				titleNames.get(0).add(attrDomains.get(i).getChsName());
			}
		}
		//add additioanl titleValues&&titleNames
		if(additionalParams != null){
			for(java.util.Map.Entry<String, String> titleEntry : additionalParams.entrySet()){
				titleValues.add(titleEntry.getKey());
				titleNames.get(0).add(titleEntry.getValue());
			}
		}
		//get rowData
		Map<String, List<Object>> teacherIdMap = new HashMap<String, List<Object>>();
		teacherIdMap.put("CGSSR_ID", teacherList);
		List<List<String[]>> rowStrings = new ArrayList<List<String[]>>();
		SearchGroup newSearchGroup = new SearchGroup("and",new SearchRule("CGSSR_ID",teacherList.toArray(),SearchType.STRING));
		if(searchGroup!=null){
			newSearchGroup.addSubGroup(searchGroup);
		}
		rowStrings.add(super.getData(entityId,titleValues , null,newSearchGroup.toString(), orderPropName,sortOrder, 0, 0));
		Map<String, String> excelPathMap = FileOperate.exportExcel(metaEntityDomain.getChsName(),titleNames, rowStrings, sheetName, rootPath, "teacherExcel");
		return JsonConvertor.obj2JSON(excelPathMap);
	}
	@Override
	public String exportTeacherExcelByEntityId(String entityId,
			List<Object> teacherList, boolean sortOrder, String orderPropName, String rootPath, Map<String,String> additionalParams) {
		return exportTeacherExcelByEntityId(entityId, teacherList,
				sortOrder, orderPropName, rootPath, additionalParams, null);
		/*MetaEntityDomain metaEntityDomain = metaEntityService.getEntityDomain(entityId);
		//get sheetName
		List<String> sheetName = new ArrayList<String>(); 
		sheetName.add(metaEntityDomain.getChsName());
		//get titleValues && titleNames
		List<MetaAttrDomain> attrDomains = metaEntityDomain.getAttrDomains();
		List<String> titleValues = new ArrayList<String>();
		List<List<String>> titleNames = new ArrayList<List<String>>();
		titleNames.add(new ArrayList<String>());
		for(int i=0; i < attrDomains.size(); ++i){
			if(!attrDomains.get(i).isHidden()){
				titleValues.add(attrDomains.get(i).getName());
				titleNames.get(0).add(attrDomains.get(i).getChsName());
			}
		}
		//add additioanl titleValues&&titleNames
		if(additionalParams != null){
			for(java.util.Map.Entry<String, String> titleEntry : additionalParams.entrySet()){
				titleValues.add(titleEntry.getKey());
				titleNames.get(0).add(titleEntry.getValue());
			}
		}
		//get rowData
		Map<String, List<Object>> teacherIdMap = new HashMap<String, List<Object>>();
		teacherIdMap.put("CGSSR_ID", teacherList);
		List<List<String[]>> rowStrings = new ArrayList<List<String[]>>();
		SearchGroup newSearchGroup = new SearchGroup("and",new SearchRule("CGSSR_ID",teacherList.toArray(),SearchType.STRING));
		rowStrings.add(super.getData(entityId,titleValues , null,newSearchGroup.toString(), orderPropName,sortOrder, 0, 0));
		Map<String, String> excelPathMap = FileOperate.exportExcel(titleNames, rowStrings, sheetName, rootPath, "teacherExcel");
		return JsonConvertor.obj2JSON(excelPathMap);*/
	}
    
	@Override
	public  String exportBriefSheetPDF(String unitId,String discId,String location){
		//读取xml文档获得模板中所有表名以及其对应的数据库中的实体ID
        List<List<String>> tittleAndEntityId = gettittleAndEntityList(discId);
        //通过实体ID获得所有表的数据
        Map<String,Object> variables = getVariables(unitId,discId,tittleAndEntityId);
        //获得所要生成的html模板并灌入数据
        String htmlContent = generateHtmlContent(unitId,discId,variables);
        //通过html模板生成导出pdf并生成下载地址
        String downLoadUrl = exportPDF(unitId,discId,htmlContent,location);
        return downLoadUrl;
    }
	  
    private List<List<String>> gettittleAndEntityList(String discId) {  
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

	private  Map<String,Object> getVariables(String unitId,String discId,List<List<String>> tittleAndEntityId){
		List<String> tittlies = tittleAndEntityId.get(0);
        List<String> entityIds = tittleAndEntityId.get(1);
        Map<String,Object> variables = new HashMap<String,Object>();
        for(int i=0;i<tittlies.size();i++){
			List<Map<String,String>> gridData = getGridData(unitId,discId,entityIds.get(i));
			variables.put(tittlies.get(i), gridData); 
			System.out.println(tittlies.get(i));
			}
        return variables;
	}
	
	private List<Map<String,String>> getGridData(String unitId,String discId,String entityId){
    	Map<String, Object> params = new HashMap<String, Object>();
		if (!params.containsKey("UNIT_ID")) {
			params.put("UNIT_ID", unitId);// 单位代码
		}
		if (!params.containsKey("DISC_ID")) {
			params.put("DISC_ID", discId);// 学科代码
		}
		List<Map<String,String>> result = super.getData(entityId, params, null, true, 0, 15);
		return result;
    }
    
	private String generateHtmlContent(String unitId,String discId,Map<String,Object> variables){
        //step2：generate ftl-template
		//暂时没有这一步，方案测试成功后，需要将模板进行分割设计，然后在这里进行拼接	    	
		String template = discIndexService.getCategotyByDiscId(discId) + ".ftl";
		HtmlGenerator htmlGenerator = new HtmlGenerator();
		String generatedHtml;
		try {
			generatedHtml = htmlGenerator.generate(template, variables);
			return generatedHtml;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		} catch (TemplateException e2) {
			e2.printStackTrace();
			return null;
		}
		
    }
	
    private String exportPDF(String unitId, String discId, String htmlContent,String location){
    	PdfGenerator pdfGenerator = new PdfGenerator();
    	try {
			Map<String,String> exportPath = pdfGenerator.generate(unitId,discId,htmlContent, location);
			return JsonConvertor.obj2JSON(exportPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    	
    }
    
    @Override
    public void generateMEMOTemplate(String unitId,String discId,String ckEditorData) throws FileNotFoundException{
    	ckEditorData = ckEditorData.replaceAll("/DSEP/upload/Image/", "");
    	try {
		    String fileName = URLDecoder.decode(unitId + discId +".pdf", "UTF-8");
		    File file = new File(ResourceLoader.getPath("MEMO/")+fileName);
            System.out.println(ResourceLoader.getPath("MEMO/")+fileName);
            //true代表如果原文件存在则覆写
		    FileOutputStream out = new FileOutputStream(file,true);
		    ITextRenderer iTextRenderer = null;
    	    try {
			    iTextRenderer = (ITextRenderer) ITextRendererObjectFactory.getObjectPool().borrowObject();
			    iTextRenderer.setDocumentFromString(ckEditorData,"file:///" + ResourceLoader.getCommonURL() + "/DSEP/upload/Image/");
			    iTextRenderer.layout();
			    iTextRenderer.createPDF(out);
			    out.close();
		    } catch (UnsupportedEncodingException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
		    } catch (IOException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
		    } catch (Exception e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
		    } finally {
				if (iTextRenderer != null) {
					try {
						//ITextRendererObjectFactory自定义类
						ITextRendererObjectFactory.getObjectPool().returnObject(iTextRenderer);
					} catch (Exception ex) {
						logger.error("Cannot return object from pool.", ex);
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
}