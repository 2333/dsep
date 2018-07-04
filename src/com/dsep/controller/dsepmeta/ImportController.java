package com.dsep.controller.dsepmeta;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.JXLException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dsep.service.file.ImportService;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;


@Controller
@RequestMapping("Collect/toCollect/import")
public class ImportController {
	
	@Resource(name="importService")
	private ImportService importService;
	
	@RequestMapping(value = "/excel",method=RequestMethod.POST)
	@ResponseBody
	public String importExcel(@RequestParam("tableId")String entityId,
							@RequestParam("file") MultipartFile importFile,
							HttpServletRequest request,
							HttpServletResponse response)
	{
		try{
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			return importService.importExcelByEntityId(entityId,"C", importFile, rootPath, null, null);}
		catch (Exception e) {
			if(e.getMessage().equals("Unable to recognize OLE stream"))
				return "文件格式不正确，请检查后重新上传";
			else
				return e.getMessage();
		}
	}
	
	@RequestMapping(value = "/excelTemplate",method=RequestMethod.POST)
	@ResponseBody
	public String createExcelTemplate(@RequestParam("tableId")String entityId,
							HttpServletRequest request,
							HttpServletResponse response) throws JXLException, Exception
	{
		String rootPath = request.getSession().getServletContext().getRealPath("/");
 		String excelName = importService.createExcelTmpByEntityId(entityId,"C", rootPath, null);
		return excelName;
	}

}
