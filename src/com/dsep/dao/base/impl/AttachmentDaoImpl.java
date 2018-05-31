package com.dsep.dao.base.impl;

import java.util.List;

import com.dsep.dao.base.AttachmentDao;
import com.dsep.dao.common.impl.DaoImpl;
import com.dsep.entity.Attachment;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class AttachmentDaoImpl extends DaoImpl<Attachment, String> implements AttachmentDao{

	@Override
	public List<Attachment> getAttachmentsByOccassion(int pageIndex,int pageSize,Boolean desc,
			String orderProperName, String occassion) {
		// TODO Auto-generated method stub
		String hql = "from Attachment a where a.occasion = ?";
		return super.hqlPage(hql, pageIndex, pageSize, desc, orderProperName,  new Object[]{occassion});
	}

	@Override
	public int getCountByOccassion(String occassion) {
		// TODO Auto-generated method stub
		String hql = "select count(*) from Attachment a where a.occasion = ?";
		return super.hqlCount(hql, new Object[]{occassion});
	}

}
