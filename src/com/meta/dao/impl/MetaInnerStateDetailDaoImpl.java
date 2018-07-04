package com.meta.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang.StringUtils;

import com.dsep.dao.common.impl.DaoImpl;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.meta.dao.MetaInnerStateDetailDao;
import com.meta.entity.MetaInnerStateDetail;

public class MetaInnerStateDetailDaoImpl extends DaoImpl<MetaInnerStateDetail, String> implements MetaInnerStateDetailDao{

	@Override
	public List<MetaInnerStateDetail> getInnerStateDetailByDomainId(
			String domainId,int pageIndex,int pageSize,Boolean desc,String orderProperName) {
		// TODO Auto-generated method stub
		String hql = " from MetaInnerStateDetail ";
		List<Object> values = new ArrayList<Object>(0);
		if(StringUtils.isNotBlank(domainId)){
			hql+=" where metaDomain.id = ?";
			values.add(domainId);
		}
		return super.hqlPage(hql, pageIndex, pageSize, desc, orderProperName,values.toArray());
	}

	@Override
	public int getInnerDetailCount(String domainId) {
		// TODO Auto-generated method stub
		String hql =" select count(*) from MetaInnerStateDetail ";
		List<Object> values = new ArrayList<Object>(0);
		if(StringUtils.isNotBlank(domainId)){
			hql+=" where metaDomain.id = ?";
			values.add(domainId);
		}
		return super.hqlCount(hql, values.toArray());
	}

}
