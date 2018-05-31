package com.dsep.controller.base;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.dsep.service.base.AttachmentService;

@Controller
@RequestMapping("ftp")
public class FTPController {
	
	@Resource(name = "attachmentService")
	private AttachmentService attachmentService;
	
	
	
	@RequestMapping("ftp")
	public String index(){
		return "ftp";
	}
	
	/**
	 * 上传附件
	 */
	@RequestMapping("ftpUpload")
	@ResponseBody
	public String upload(HttpServletRequest request, HttpSession session){
		return null;

	}
	

}
