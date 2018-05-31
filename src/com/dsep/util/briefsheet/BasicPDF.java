package com.dsep.util.briefsheet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.dsep.util.export.briefsheet.ITextRendererObjectFactory;
import com.dsep.util.export.briefsheet.PdfGenerator;

public class BasicPDF {
	protected String path;
	protected String name;
	protected String imagePath;
	protected final static Logger logger = Logger.getLogger(PdfGenerator.class);
	
	public Map<String,String> generatePDF(String htmlContent) throws Exception{
		OutputStream out = null;
		//这是flying saucer的类
		ITextRenderer iTextRenderer = null;
		try {
			File outputDir = new File(this.path);
			if (!outputDir.exists()) {  
		            outputDir.mkdirs();  
		    } 			
			File outputFile = new File(this.path + this.name);
			out = new FileOutputStream(outputFile);
			Map<String,String> exportPdfPath = new HashMap<String,String>();
			exportPdfPath.put("filename",this.name);
			exportPdfPath.put("filepath",this.path);

			iTextRenderer = (ITextRenderer) ITextRendererObjectFactory.getObjectPool().borrowObject();//获取对象池中对象,注释中用的是new
			try {
				htmlContent = htmlContent.replaceAll(this.imagePath, "");
				iTextRenderer.setDocumentFromString(htmlContent,"file:///" + this.imagePath);
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
