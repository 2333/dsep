/*package com.dsep.unitTest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.dsep.service.dsepmeta.dsepmetas.impl.DMExportServiceImpl;
import com.dsep.service.file.impl.BriefsheetServiceImpl;
import com.dsep.util.briefsheet.*;
import com.dsep.util.export.briefsheet.ITextRendererObjectFactory;
import com.dsep.util.export.briefsheet.ResourceLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:WebContent/Web-INF/config/spring-util.xml",
		"file:WebContent/Web-INF/config/spring-quartz.xml",
		"file:WebContent/WEB-INF/config/2013/util/utils.xml",
		"file:WebContent/WEB-INF/config/spring-logger.xml"})
public class BriefsheetTest {
	public static PDFBoxTest pdfFiller = new PDFBoxTest();
	//public static DMExportServiceImpl exportService = new DMExportServiceImpl();
	public static XMLParser xmlParser = new XMLParser();
	@Autowired
	public BriefsheetServiceImpl briefsheetService;
	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testReadPDFText() throws IOException {
		//pdfFiller.readPDFText();
		fail("Not yet implemented");
	}
	@Test
	public void testgenerateMEMOTemplate() throws Exception{
		//
		File file = new File("T:/temp.pdf");
		FileOutputStream out = new FileOutputStream(file,true);
		ITextRenderer iTextRenderer = null;
	    iTextRenderer = (ITextRenderer) ITextRendererObjectFactory.getObjectPool().borrowObject();
	    //iTextRenderer.setDocument("C:/Users/qyx/Desktop/ueditor.html");
        String inputFile = "C:/Users/qyx/Desktop/ueditor.html";
        String url = new File(inputFile).toURI().toURL().toString();
	    iTextRenderer.setDocument(url);
	    iTextRenderer.layout();
	    iTextRenderer.createPDF(out);
	    out.close();		
	}
	@Test
	public void testgetTeahcerConfigList(){
		List<List<String>> entityList = xmlParser.getTeahcerConfigList("TEACHER");
		System.out.print(entityList);
		Map<String, Object> templateData = briefsheetService.getTeacherData("B4DDE95F014C44E48B225A096CB41A96", entityList);
		System.out.print(templateData);
	}
	@Test
	public void testgenerateTeacherBriefSheet(){
		String teacherId = new String("B4DDE95F014C44E48B225A096CB41A96");
		briefsheetService.generateTeacherBriefSheet(teacherId);
	}
	@Test
	public void testgeneratePDFwithData(){
		String dataPath = ResourceLoader.getPath("template/data.txt");
		Map<String, Object> templateData = null;
		String htmlContent = briefsheetService.generateTeacherHtml(templateData);
		String downLoadUrl = briefsheetService.exportPDF(htmlContent);
	}
}
*/