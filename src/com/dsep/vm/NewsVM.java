package com.dsep.vm;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.dsep.entity.News;
import com.dsep.util.Dictionaries;

@JsonIgnoreProperties(value={"hibernateLazyInitializer"})  
public class NewsVM {

	private News news;
	private String id;
	private String importantLevelName;
	private String newsTypeName;
	
	public News getNews() {
		return news;
	}
	public void setNews(News news) {
		this.news = news;
	}
	public String getImportantLevelName() {
		String importantLevel = this.news.getImportantLevel();
		this.importantLevelName = Dictionaries.getNewsImportantLevel(importantLevel);
		return importantLevelName;
	}
	public void setImportantLevelName(String importantLevelName) {
		this.importantLevelName = importantLevelName;
	}
	
	public String getNewsTypeName() {
		String newsType = this.news.getType();
		this.newsTypeName = Dictionaries.getNewsType(newsType);
		return newsTypeName;
	}
	public void setNewsTypeName(String newsTypeName) {
		this.newsTypeName = newsTypeName;
	}
	public String getId() {
		this.id = this.news.getId();
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
