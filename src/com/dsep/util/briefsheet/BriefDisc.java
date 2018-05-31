package com.dsep.util.briefsheet;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.dsep.util.export.briefsheet.ITextRendererObjectFactory;
import com.dsep.util.export.briefsheet.PdfGenerator;

public class BriefDisc extends AbstractPDF {
	private static final String FILE_TYPE = "pdf";
	private final static Logger logger = Logger.getLogger(PdfGenerator.class);
	private String imagePath;

	public BriefDisc(String unitId, String discId, String templateName, String path, String imagePath) {
		super(path, templateName);
		this.params = new HashMap<String, Object>();
		this.params.put("UNIT_ID", unitId);
		this.params.put("DISC_ID", discId);
		this.name = unitId + "_" + discId + "." + FILE_TYPE;
		this.imagePath = imagePath;
	}


	@Override
	public Map<String, String> generate(String htmlString) throws Exception {
		OutputStream out = null;
		ITextRenderer iTextRenderer = null;
		try {
			out = new FileOutputStream(this.loadFile());
			iTextRenderer = (ITextRenderer) ITextRendererObjectFactory.getObjectPool().borrowObject();//获取对象池中对象,注释中用的是new
			try {
				htmlString = htmlString.replaceAll(this.imagePath, "");
				iTextRenderer.setDocumentFromString(htmlString,"file:///" + this.imagePath);
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

}
