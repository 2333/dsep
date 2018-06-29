package com.dsep.dao.base;

import java.util.List;

import com.dsep.dao.common.Dao;
import com.dsep.entity.News;

public interface NewsDao extends Dao<News , String>{

	abstract public void deleteAttachment(String id);

	abstract public String getAttachmentId(String newsId);

	abstract public List<News> getSearchData(String importantLevel,
			String type, String title, String publisher, String fromDate, String toDate, int page, int pageSize);
	abstract public int getSearchDataCount(String importantLevel,
			String type, String title, String publisher, String fromDate, String toDate);
	abstract public List<News> getNewsByImportantLevel(String importantLevel);
	
	abstract public List<News> getNewsPage(int page, int pageSize);
}
