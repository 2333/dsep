package com.dsep.controller.templateManage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dsep.controller.base.JqGridBaseController;
import com.dsep.domain.AttachmentHelper;
import com.dsep.entity.Attachment;
import com.dsep.entity.User;
import com.dsep.entity.enumeration.AttachmentType;
import com.dsep.service.base.AttachmentService;
import com.dsep.util.FileOperate;
import com.dsep.util.JsonConvertor;
import com.dsep.util.MySessionContext;
import com.dsep.util.UserSession;
import com.dsep.vm.PageVM;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

@Controller
@RequestMapping("templateManage")
public class CollectTemManage extends JqGridBaseController{
	
	@Resource(name = "attachmentService")
	private AttachmentService attachmentService;
	
	@RequestMapping("collect")
	public String viewTemplates(){
		return "TemplateManage/collectTempManage";
	}
	@ResponseBody
	@RequestMapping("collect/templates")
	public String initTemplates(HttpServletRequest request){
		setRequestParams(request);
		PageVM<Attachment> pageVM = attachmentService.getAttachPageVMByOccassion("6",
				getSidx(),getPageIndex(),
				getPageSize(),isAsc());
		String result = JsonConvertor.obj2JSON(pageVM.getGridData());
		return result;
	}
	@ResponseBody
	@RequestMapping("collect/upLoadTemp")
	public String upLoadTemp(HttpServletRequest request){
		String sessionId = request.getParameter("jsessionid");
		MySessionContext myc= MySessionContext.getInstance();
		HttpSession session = myc.getSession(sessionId); 
		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();
		MultipartFile file = FileOperate.getFile(request);
		AttachmentHelper attachmentHelper = attachmentService.getAttachmentHelper(file, user.getId(), 
				AttachmentType.TEMPLATE, user.getUnitId());
		if(FileOperate.upload(file, attachmentHelper.getPath(), attachmentHelper.getStorageName()))
			return attachmentService.addAttachment(attachmentHelper.getAttachment());
		else
			return "error";
	}
	

}
