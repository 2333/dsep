package com.dsep.service.base.impl;

import java.util.ArrayList;
import java.util.List;

import com.dsep.dao.base.NewsDao;
import com.dsep.entity.News;
import com.dsep.service.base.AttachmentService;
import com.dsep.service.base.NewsService;
import com.dsep.vm.NewsVM;
import com.dsep.vm.PageVM;

public class NewsServiceImpl implements NewsService{
	
	private NewsDao newsDao;
	private AttachmentService attachmentService;

	public NewsDao getNewsDao() {
		return newsDao;
	}
	public void setNewsDao(NewsDao newsDao) {
		this.newsDao = newsDao;
	}
	
	public AttachmentService getAttachmentService() {
		return attachmentService;
	}
	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}
	
	@Override
	public void saveNews(News news) {
		newsDao.save(news);
	}
	
	@Override
	public PageVM<NewsVM> getNews(int page, int pageSize) {
		
		List<News> list=newsDao.getNewsPage(page, pageSize);
		
		int totalCount=newsDao.Count();
		
		List<NewsVM> vmList= new ArrayList<NewsVM>();
		
		for(News n : list){
			NewsVM vm= new NewsVM();
			vm.setNews(n);
			vmList.add(vm);
		}
		PageVM<NewsVM> result=new PageVM<NewsVM>(page,totalCount,pageSize,vmList);
		return result;
	}
	
	@Override
	public PageVM<NewsVM> getNews(String importantLevel, String type, String title, String publisher, String fromDate, String toDate,
			int page, int pageSize) {
		List<News> list=newsDao.getSearchData(importantLevel, type, title, publisher, fromDate, toDate,  page, pageSize);
		
		int totalCount=newsDao.getSearchDataCount(importantLevel, type, title, publisher, fromDate, toDate);
		
		List<NewsVM> vmList= new ArrayList<NewsVM>();
		
		for(News n : list){
			NewsVM vm= new NewsVM();
			vm.setNews(n);
			vmList.add(vm);
		}
		PageVM<NewsVM> result=new PageVM<NewsVM>(page ,totalCount, pageSize , vmList);
		return result;
	}
	
	@Override
	public News getNews(String newsId) {
		return newsDao.get(newsId);
	}
	
	
	@Override
	public void updateNews(News news) {
		newsDao.saveOrUpdate(news);
	}
	@Override
	public void deleteNews(String id) {
		newsDao.deleteByKey(id);
	}
	@Override
	public void deleteAttachment(String id) {
		newsDao.deleteAttachment(id);
		
	}
	@Override
	public String getAttachmentId(String newsId) {
		return newsDao.getAttachmentId(newsId);
	}
	@Override
	public List<NewsVM> getLatestNews(int count) {
		
		List<News> list=newsDao.getNewsPage(1, count);
		
		List<NewsVM> vmlist = new ArrayList<NewsVM>();
		
		for(News n : list){
			NewsVM vm= new NewsVM();
			vm.setNews(n);
			vmlist.add(vm);
		}
		return vmlist;
	}
	
	
}
