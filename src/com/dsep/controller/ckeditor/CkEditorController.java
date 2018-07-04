/**
 * Project Name:DSEP
 * File Name:CkEditorController.java
 * Package Name:com.dsep.controller.ckeditor
 * Date:2014年5月16日上午8:34:47
 *
 */
package com.dsep.controller.ckeditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dsep.service.dsepmeta.dsepmetas.DMExportService;
import com.dsep.util.export.briefsheet.ResourceLoader;

/**
 * ClassName: CkEditorController <br/>
 * Reason: 有关CKEditor的文件或者图片上传功能controller. <br/>
 * date: 2014年5月16日 上午8:34:47 <br/>
 *
 * @author QianYuxiang
 */
@Controller
@RequestMapping("/Collect/toCollect/CkEditor")
public class CkEditorController {
	
	@Resource(name="dMExportService")
	private DMExportService dMExportService;
	/*
	 * 通用的页面跳转
	 */
	@RequestMapping(value = "/redirect/{path}")
	public String redirect(@PathVariable String path) {
		System.out.println("go to page " + path);
		return path;
	}
	
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public void  submitCkeditor(HttpServletRequest request,
			HttpServletResponse response){
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String ckEditorData = request.getParameter("disc_editor");
		String discId = request.getParameter("discId");
		String unitId = request.getParameter("unitId");
		try {
			dMExportService.generateMEMOTemplate(unitId, discId, ckEditorData);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * ckeditor上传
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void fileUpload(HttpServletRequest request,
			HttpServletResponse response, @RequestParam MultipartFile upload) {
		OutputStream out = null;
		PrintWriter printWriter = null;
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		try {
			String fileName = upload.getOriginalFilename().toLowerCase();
			byte[] bytes = upload.getBytes();
			String uploadPath = getUploadpath(request) + "/" + fileName;
			System.out.println(uploadPath);
			System.out.println(ResourceLoader.getCommonURL()+uploadPath);
			out = new FileOutputStream(new File(uploadPath));
			out.write(bytes);
			String callback = request.getParameter("CKEditorFuncNum");
			System.out.println("callback:" + callback);
			printWriter = response.getWriter();
			String filePath = getRelativePath(request) + "/" + fileName;
			System.out.println(filePath);
			printWriter
					.println("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction("
							+ callback
							+ ",'"
							+ filePath
							+ "',''"
							+ ")</script>");
			printWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (printWriter != null) {
					printWriter.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return;
	}

	/*
	 * ckeditor浏览服务器
	 */
	@RequestMapping(value = "/browerServer")
	public void showImage(HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		List<String> fileList = new ArrayList<String>();
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset = utf-8");
			String filePath = getUploadpath(request);
			out = response.getWriter();
			File file = new File(filePath);
			String callback = request.getParameter("CKEditorFuncNum");
			out.println("<script type='text/javascript' src='${ContextPath}/ckeditor/ckeditor.js'></script>");
			out.println("<script>");
			out.println("function choose(obj){");
			out.println("window.opener.CKEDITOR.tools.callFunction(" + callback + ",obj)");
			out.println("window.close();");
			out.println("}");
			out.println("</script>");
			out.println("<h2>单击图片进行选择</h2>");
			if (file.exists()) {
				File[] files = file.listFiles();
				for (File file2 : files) {
					fileList.add(file2.getName());
					String fileName = file2.getName();
					fileName = getRelativePath(request) + "/" + fileName;
					out.println("<img src='" + fileName + "' onclick=\""
							+ "choose('" + fileName + "')\">");
					out.flush();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}

		return;
	}

	/*
	 * 后台获取ckeditor提交的内容
	 */
	@RequestMapping(value = "ckeditor")
	public ModelAndView ckeditorAction(@RequestParam String ckEditor) {
		ModelAndView modelAndView = new ModelAndView("ckeditorSuccess");
		try {
			ckEditor = new String(ckEditor.getBytes("iso-8859-1"), "utf-8");
			System.out.println("content:" + ckEditor);
			modelAndView.addObject("editor1", ckEditor);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return modelAndView;
	}

	// 获得服务器的上传路径
	public String getUploadpath(HttpServletRequest request) {
		String path = "";
		//path = request.getSession().getServletContext().getRealPath("/") +request.getParameter("type");
		path = request.getSession().getServletContext().getRealPath("/") + "upload/" + request.getParameter("type") + "/" + request.getParameter("unitId") + request.getParameter("discId");
		File uploadFolder = new File(path);
		if((!uploadFolder.exists()) && (!uploadFolder.isDirectory())){
			uploadFolder.mkdirs();
		}
		return path;
	}
	//获得文件相对路径
	public String getRelativePath(HttpServletRequest request){
		String visitPath = request.getContextPath()+"/upload/"+request.getParameter("type")+"/" + request.getParameter("unitId") + request.getParameter("discId");
		/*visitPath = visitPath.replace("/", "\\\\");*/
		return visitPath;
	}
}
