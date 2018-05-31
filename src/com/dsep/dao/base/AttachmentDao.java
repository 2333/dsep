package com.dsep.dao.base;

import java.util.List;

import com.dsep.dao.common.Dao;
import com.dsep.entity.Attachment;

public interface AttachmentDao extends Dao<Attachment, String>{
	
	public abstract List<Attachment>  getAttachmentsByOccassion(int pageIndex,int pageSize,Boolean desc,
			String orderProperName, String occassion) ;
	
	public int getCountByOccassion(String occassion);
	
}
