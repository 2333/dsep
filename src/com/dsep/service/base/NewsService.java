package com.dsep.service.base;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.News;
import com.dsep.vm.NewsVM;
import com.dsep.vm.PageVM;

@Transactional(propagation=Propagation.SUPPORTS)
public interface NewsService {

	/*
	 * 新建公告
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	abstract public void saveNews(News news);

	/*
	 * 获取新闻列表
	 */
	abstract public PageVM<NewsVM> getNews(int page, int pageSize);
	
	/*
	 * 查询新闻
	 */
	abstract public PageVM<NewsVM> getNews(String importantLevel, String type, String title, String publisher, String fromDate, String toDate,
			int page, int pageSize);
	
	/*
	 * 根据Id获取公告实体
	 */
	abstract public News getNews(String newsId);
	
	/*
	 * 修改公告
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	abstract public void updateNews(News news);
	
	/*
	 * 根据id删除公告
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	abstract public void deleteNews(String id);
	
	/*
	 * 删除公告附件
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	abstract public void deleteAttachment(String id);
	
	/*
	 * 根据公告Id返回附件Id
	 */
	abstract public String getAttachmentId(String newsId);
	
	/**
	 * 获取最新的公告
	 * @param count
	 * @return
	 */
	abstract public List<NewsVM> getLatestNews(int count);

}
