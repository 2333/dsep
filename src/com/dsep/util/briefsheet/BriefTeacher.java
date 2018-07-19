package com.dsep.util.briefsheet;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.dsep.util.export.briefsheet.ITextRendererObjectFactory;
import com.dsep.util.export.briefsheet.PdfGenerator;

public class BriefTeacher extends AbstractPDF {
	private static final String FILE_TYPE = "pdf";
	private final static Logger logger = Logger.getLogger(PdfGenerator.class);
	public BriefTeacher(String teacherId, String name, String path){
		super(path);
		this.params = new HashMap<String, Object>();
		this.params.put("CGSSR_ID", teacherId);
		this.templateName = "TEACHER";
		this.name = name + "." + FILE_TYPE;
	}
	@Override
	public Map<String, String> generate(String htmlString) throws Exception {
		OutputStream out = null;
		//这是flying saucer的类
		ITextRenderer iTextRenderer = null;
		try {
			out = new FileOutputStream(this.loadFile());
			iTextRenderer = (ITextRenderer) ITextRendererObjectFactory.getObjectPool().borrowObject();//获取对象池中对象,注释中用的是new
			try {
				iTextRenderer.setDocumentFromString(htmlString);
				iTextRenderer.layout();
				iTextRenderer.createPDF(out);
			} catch (Exception e) {
				ITextRendererObjectFactory.getObjectPool().invalidateObject(iTextRenderer);
				iTextRenderer = null;
				throw e;
			}
			Map<String,String> exportPdfPath = new HashMap<String,String>();
			exportPdfPath.put("filepath",this.path);
			exportPdfPath.put("filename",this.name);
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
	@Override
	public void setPath(String path) {
		this.path = path;
	}

}
