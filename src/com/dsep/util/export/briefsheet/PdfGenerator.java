package com.dsep.util.export.briefsheet;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.resource.XMLResource;

import com.dsep.util.briefsheet.PathProvider;



public class PdfGenerator {
	private final static Logger logger = Logger.getLogger(PdfGenerator.class);
	
	/**
	 * Output a pdf to the specified outputstream
	 * 
	 * @param htmlContent
	 *            the htmlstr
	 * @param filePath
	 *            root path
	 * @throws Exception
	 */
	public Map<String,String> generate(String unitId, String discId, String htmlContent, String filePath)
			throws Exception {
		OutputStream out = null;
		//这是flying saucer的类
		ITextRenderer iTextRenderer = null;

		try {
			String[] filePathArray = filePath.split("\\\\");
			String webRoot = filePathArray[filePathArray.length-1];
			filePath = URLDecoder.decode(filePath+ "breifsheet"+File.separator + "computer" + File.separator, "UTF-8");
			File outputDir = new File(filePath);
			if (!outputDir.exists()) {  
		            outputDir.mkdirs();  
		    } 			
			//SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
			//String fileName = URLDecoder.decode(df.format(new Date()) +".pdf", "UTF-8");
			String fileName = URLDecoder.decode(unitId + discId + ".pdf", "UTF-8");
			File outputFile = new File(filePath + fileName);
			out = new FileOutputStream(outputFile);
			Map<String,String> exportPdfPath = new HashMap<String,String>();
			exportPdfPath.put("filename",fileName);
			exportPdfPath.put("filepath",filePath);

			iTextRenderer = (ITextRenderer) ITextRendererObjectFactory.getObjectPool().borrowObject();//获取对象池中对象,注释中用的是new
			try {
				//iTextRenderer.setDocument(htmlDoc,null);
				//iTextRenderer.setDocument(url);
				htmlContent = htmlContent.replaceAll("/" + webRoot + "/upload/Image/" + unitId + discId + "/", "");
				iTextRenderer.setDocumentFromString(htmlContent,"file:///" + ResourceLoader.getCommonURL() + "/" + webRoot + "/upload/Image/"+ unitId + discId + "/");
				iTextRenderer.layout();
				iTextRenderer.createPDF(out);
			} catch (Exception e) {
				ITextRendererObjectFactory.getObjectPool().invalidateObject(iTextRenderer);
				iTextRenderer = null;
				throw e;
			}
			return exportPdfPath;

		} catch (Exception e) {
			throw e;
		} finally {
			if (out != null)
				out.close();

			if (iTextRenderer != null) {
				try {
					//ITextRendererObjectFactory自定义类
					ITextRendererObjectFactory.getObjectPool().returnObject(iTextRenderer);
				} catch (Exception ex) {
					logger.error("Cannot return object from pool.", ex);
				}
			}
		}
	}

	public Map<String, String> generateTeacherBriefsheet(String htmlContent) throws Exception {
		// TODO Auto-generated method stub
		OutputStream out = null;
		//这是flying saucer的类
		ITextRenderer iTextRenderer = null;

		try {
			File outputDir = new File(PathProvider.getTemplateRootPath(), "brief");
			if (!outputDir.exists()) {  
		            outputDir.mkdirs();  
		    } 			
			//SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
			//String fileName = URLDecoder.decode(df.format(new Date()) +".pdf", "UTF-8");
			String fileName = URLDecoder.decode("teacherbriefsheet.pdf", "UTF-8");
			File outputFile = new File(outputDir.getPath(), fileName);
			out = new FileOutputStream(outputFile);
			Map<String,String> exportPdfPath = new HashMap<String,String>();
			exportPdfPath.put("filename",fileName);
			exportPdfPath.put("filepath",outputDir.getPath());

			iTextRenderer = (ITextRenderer) ITextRendererObjectFactory.getObjectPool().borrowObject();//获取对象池中对象,注释中用的是new
			try {
				//iTextRenderer.setDocument(htmlDoc,null);
				//iTextRenderer.setDocument(url);
				iTextRenderer.setDocumentFromString(htmlContent);
				iTextRenderer.layout();
				iTextRenderer.createPDF(out);
			} catch (Exception e) {
				ITextRendererObjectFactory.getObjectPool().invalidateObject(iTextRenderer);
				iTextRenderer = null;
				throw e;
			}
			return exportPdfPath;

		} catch (Exception e) {
			throw e;
		} finally {
			if (out != null)
				out.close();

			if (iTextRenderer != null) {
				try {
					//ITextRendererObjectFactory自定义类
					ITextRendererObjectFactory.getObjectPool().returnObject(iTextRenderer);
				} catch (Exception ex) {
					logger.error("Cannot return object from pool.", ex);
				}
			}
		}
	}
}

//利用xml解析的方式，如果直接解析html，会是以下代码
/***
 *  String inputFile = "samples/firstdoc.xhtml";
 * 	String url = new File(inputFile).toURI().toURL().toString();
 *	String outputFile = "firstdoc.pdf";
 *	OutputStream os = new FileOutputStream(outputFile);
 *
 *	ITextRenderer renderer = new ITextRenderer();
 *	renderer.setDocument(url);
 *	renderer.layout();
 *	renderer.createPDF(os);
 *
 *	os.close();
 */