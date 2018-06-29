package com.dsep.controller.news;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dsep.domain.AttachmentHelper;
import com.dsep.entity.News;
import com.dsep.entity.User;
import com.dsep.entity.enumeration.AttachmentType;
import com.dsep.service.base.AttachmentService;
import com.dsep.service.base.NewsService;
import com.dsep.util.Dictionaries;
import com.dsep.util.FileOperate;
import com.dsep.util.JsonConvertor;
import com.dsep.util.MySessionContext;
import com.dsep.util.UserSession;
import com.dsep.util.crawler.spider.SiteSetting;
import com.dsep.util.crawler.spider.Starter;
import com.dsep.util.crawler.spider.TestPhantomJsDriver;
import com.dsep.util.crawler.zFire.SessionParser;
import com.dsep.vm.NewsVM;
import com.dsep.vm.PageVM;

@Controller
@RequestMapping("news")
public class NewsController {
	
	@Resource(name = "newsService")
	private NewsService newsService;
	
	@Resource(name = "attachmentService")
	private AttachmentService attachmentService;
	/**
	 * 跳转主页
	 */
	@RequestMapping("news")
	public String news(HttpServletRequest request){
		// 方弘宇2018-02-06
		String basePath = request.getRealPath("/");
		String myCookie = SessionParser.parseCookieStr(TestPhantomJsDriver.getCookieStr(basePath));
		SiteSetting.cookie = myCookie.trim();
		SiteSetting.token = SiteSetting.cookie.substring(SiteSetting.cookie.indexOf("SCXAT=") + 6, SiteSetting.cookie.indexOf("+"));
		Starter.begin();
		return "news/manage_news";
	}
	
	/*
	 * 跳转新建公告界面
	 */
	@RequestMapping("newsadd")
	public String add(){
		return "news/add_news";
	}
	
	/*
	 * 跳转编辑公告界面
	 */
	@RequestMapping("newsedit")
	public String edit(String newsId, Model model){
		
		News news = newsService.getNews(newsId);
		Map<String,String> levels = Dictionaries.getNewsImportantLevel();
		Map<String,String> newsTypes = Dictionaries.getNewsType();
		
		model.addAttribute("news", news);
		model.addAttribute("levels", levels);
		model.addAttribute("newsTypes", newsTypes);
		
		return "news/edit_news";
	}
	
	/*
	 * 返回新闻列表
	 */
	@RequestMapping("newslist")
	@ResponseBody
	public String newslist(HttpServletRequest request,
			HttpServletResponse response){
		
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		
		PageVM<NewsVM> news = newsService.getNews(page, pageSize);
		Map<String, Object> m = news.getGridData();
		String json = JsonConvertor.obj2JSON(m);
		return json;
	}
	
	/*
	 * 新闻查询
	 */
	@RequestMapping("newssearch")
	@ResponseBody
	public String newssearch(HttpServletRequest request,
			HttpServletResponse response, String importantLevel, String type, String title, String publisher, String fromDate, String toDate){
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		PageVM<NewsVM> news = newsService.getNews(importantLevel, type, title, publisher, fromDate, toDate, page, pageSize);
		Map<String, Object> m = news.getGridData();
		String json = JsonConvertor.obj2JSON(m);
		return json;
	}
	
	/*
	 * 保存新建公告
	 */
	@RequestMapping("newssaveadd")
	@ResponseBody
	public Boolean newsSaveAdd(News news, @RequestParam(value = "attachmentId", required = false) String id,
			HttpSession session){
		
		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();
		news.setPublisher(user.getName());
		
		String unitId = user.getUnitId();
		String discId = user.getDiscId();
		
		if(!StringUtils.isBlank(unitId))
			news.setUnitId(unitId);
		
		if(!StringUtils.isBlank(discId))
			news.setDiscId(discId);
		
		Date now = new Date();
		@SuppressWarnings("deprecation")
		Date nowTime = new Date(now.getYear(), now.getMonth(), now.getDate());
		news.setDate(nowTime);
		
		
		if(!StringUtils.isBlank(id))//如果附件不空
			news.setAttachment(attachmentService.getAttachment(id));
		try{
			newsService.saveNews(news);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	@RequestMapping("newssaveedit")
	@ResponseBody
	public Boolean newsSaveEdit(News news, @RequestParam(value = "attachmentId", required = false) String id,
			HttpSession session){
		
		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();
		news.setPublisher(user.getName());
		
		String unitId = user.getUnitId();
		String discId = user.getDiscId();
		
		if(!StringUtils.isBlank(unitId))
			news.setUnitId(unitId);
		
		if(!StringUtils.isBlank(discId))
			news.setDiscId(discId);
		
		Date now = new Date();
		@SuppressWarnings("deprecation")
		Date nowTime = new Date(now.getYear(), now.getMonth(), now.getDate());
		news.setDate(nowTime);
		
		
		if(!StringUtils.isBlank(id))//如果附件不空
			news.setAttachment(attachmentService.getAttachment(id));
		try{
			newsService.updateNews(news);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	/*
	 * 删除公告
	 */
	@RequestMapping("newsdelete")
	@ResponseBody
	public boolean newsdelete(String newsId){
		try{
			//获取公告附件路径
			String attachmentId =  newsService.getAttachmentId(newsId);
			String path = attachmentService.getAttachmentPath(attachmentId);
			
			newsService.deleteNews(newsId);
			FileOperate.delete(path);//删除本地文件
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	/*
	 * 看公告详细
	 */
	@RequestMapping("newsdetail")
	public String newsdetail(String newsId, Model model){
		
		News news = newsService.getNews(newsId);
		NewsVM vm = new NewsVM();
		vm.setNews(news);
		model.addAttribute("news", vm);
		
		return "news/detail_news";
	}
	
	/**
	 * 上传附件
	 * @param file
	 * @return
	 */
	@RequestMapping("newsupload")
	@ResponseBody
	public String uploadAttachment(HttpServletRequest request, String jsessionid){
		
		MySessionContext myc= MySessionContext.getInstance();
		HttpSession session = myc.getSession(jsessionid); 
		
		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();
		
		MultipartFile file = FileOperate.getFile(request);
		
		AttachmentHelper ah = attachmentService.getAttachmentHelper(file, user.getId(), AttachmentType.NEWS, user.getUnitId());
		if(FileOperate.upload(file, ah.getPath(), ah.getStorageName())){
			return attachmentService.addAttachment(ah.getAttachment());
		}	
		else{
			return null;
		}

	}
	
	/**
	 * 下载附件
	 * @param id
	 * @return
	 */
	@RequestMapping("newsdownload")
	@ResponseBody
	public String downloadAttachment(String id){

		String filePath = attachmentService.getAttachmentPath(id);
		String json = "false";
		try {
			if(FileOperate.ifFileExist(filePath)){
				json = JsonConvertor.obj2JSON(filePath);
				return json;
			}else{
				return JsonConvertor.obj2JSON(json);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return JsonConvertor.obj2JSON(json);
		}
	}
	
	/**
	 * 删除附件
	 * @param id
	 * @return
	 */
	@RequestMapping("newsfiledelete")
	@ResponseBody
	public boolean deleteAttachment(String id, String newsId){
		
		try{
			String path =  attachmentService.getAttachmentPath(id);
			//删除附件实体之前要先删除关联
			if(!StringUtils.isBlank(newsId))
			{
				newsService.deleteAttachment(newsId);
			}
			attachmentService.deleteAttachment(id);
			FileOperate.delete(path);
		}catch(Exception e){
			return false;
		}
		return true;
	}
}
