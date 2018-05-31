package com.dsep.dao.base.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.base.NewsDao;
import com.dsep.dao.common.impl.DaoImpl;
import com.dsep.entity.News;

public class NewsDaoImpl extends DaoImpl<News, String> implements NewsDao{

	@Override
	public void deleteAttachment(String id) {
		String attachmentId = "";
		String sql = "update dsep_base_news set attachment_id = ? where id = ?";
		super.sqlBulkUpdate(sql, new Object[]{attachmentId , id});
		
	}

	@Override
	public String getAttachmentId(String newsId) {
		String hql = "from News n where n.id = ?";
		List<News> list = super.hqlFind(hql, new Object[]{newsId});
		if(list.size()>0)
			return list.get(0).getAttachment().getId();
		else {
			return null;
		}
	}

	@Override
	public List<News> getSearchData(String importantLevel, String type,
			String title, String publisher, String fromDate, String toDate,
			int page, int pageSize) {
		String hql = "from News n where n.id is not null";
		
		List<Object> params = new ArrayList<Object>(0);
		
		if(!importantLevel.equals("-")){
			hql += " and n.importantLevel = ?";
			params.add(importantLevel);
		}
		
		if(!type.equals("-")){
			hql += " and n.type = ?";
			params.add(type);
		}
		
		if(StringUtils.isNotBlank(title)){
			hql += " and n.title like ?";
			title = "%" + title + "%";
			params.add(title);
		}
		
		if(StringUtils.isNotBlank(publisher)){
			hql += " and n.publisher like ?";
			publisher = "%" + publisher + "%";
			params.add(publisher);
		}
		
		if(StringUtils.isNotBlank(fromDate)){
			Date d = null;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				d = df.parse(fromDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			hql += " and n.date >= ?";
			params.add(d);
		}
		
		if(StringUtils.isNotBlank(toDate)){
			Date d = null;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				d = df.parse(toDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			hql += " and n.date <= ?";
			params.add(d);
		}
		
		hql+= " order by n.importantLevel desc,n.date desc";
		
		List<News> list = super.hqlPage(hql, page, pageSize, params.toArray());
		return list;
	}
	
	
	@Override
	public int getSearchDataCount(String importantLevel, String type,
			String title, String publisher, String fromDate, String toDate) {
		String hql = "from News n where n.id is not null";
		
		List<Object> params = new ArrayList<Object>(0);
		
		if(!importantLevel.equals("-")){
			hql += " and n.importantLevel = ?";
			params.add(importantLevel);
		}
		
		if(!type.equals("-")){
			hql += " and n.type = ?";
			params.add(type);
		}
		
		if(StringUtils.isNotBlank(title)){
			hql += " and n.title like ?";
			title = "%" + title + "%";
			params.add(title);
		}
		
		if(StringUtils.isNotBlank(publisher)){
			hql += " and n.publisher like ?";
			publisher = "%" + publisher + "%";
			params.add(publisher);
		}
		
		if(StringUtils.isNotBlank(fromDate)){
			Date d = null;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				d = df.parse(fromDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			hql += " and n.date >= ?";
			params.add(d);
		}
		
		if(StringUtils.isNotBlank(toDate)){
			Date d = null;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				d = df.parse(toDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			hql += " and n.date <= ?";
			params.add(d);
		}
		
		hql+= " order by n.importantLevel desc,n.date desc";
		
		return super.hqlFind(hql, params.toArray()).size();
		
	}
	

	@Override
	public List<News> getNewsByImportantLevel(String importantLevel) {
		
		String sql = "select * from dsep_base_news where important_level = ? order by news_date desc";
		
		List<News> list = super.sqlFind(sql, new Object[]{importantLevel});
		
		return list;
	}

	@Override
	public List<News> getNewsPage(int page, int pageSize) {
		String sql = "select * from dsep_base_news order by important_level desc,news_date desc";
		
		List<News> list = super.sqlPage(sql, page, pageSize);
		
		return list;
	}




}
