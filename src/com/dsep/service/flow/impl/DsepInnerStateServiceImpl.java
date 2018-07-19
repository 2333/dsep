package com.dsep.service.flow.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dsep.service.flow.DsepInnerStateService;
import com.dsep.util.Configurations;
import com.dsep.vm.PageVM;
import com.dsep.vm.flow.InnerStateVm;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.meta.dao.MetaInnerStateDetailDao;
import com.meta.entity.MetaInnerStateDetail;

public class DsepInnerStateServiceImpl implements DsepInnerStateService {
	
	private MetaInnerStateDetailDao metaInnerStateDetailDao;
	@Override
	public PageVM<InnerStateVm> getCurrentInnerStates(int pageIndex,int pageSize,Boolean desc, String orderpropName) {
		// TODO Auto-generated method stub
		String domainId = Configurations.getCurrentDomainId();
		return getInnerStatesByDomainId(domainId, pageIndex, pageSize, desc, orderpropName);
	}
	@Override
	public PageVM<InnerStateVm> getInnerStatesByDomainId(String domainId,
			int pageIndex, int pageSize, Boolean desc, String orderpropName) {
		// TODO Auto-generated method stub
		List<MetaInnerStateDetail> innerStateDetails = metaInnerStateDetailDao.getInnerStateDetailByDomainId(domainId,
				pageIndex, pageSize, desc, orderpropName);
		List<InnerStateVm> innerStateVms = new ArrayList<InnerStateVm>(0);
		for(MetaInnerStateDetail innerStateDetail: innerStateDetails){
			InnerStateVm innerStateVm = new InnerStateVm(innerStateDetail);
			innerStateVms.add(innerStateVm);
		}
		int totalCount = metaInnerStateDetailDao.getInnerDetailCount(domainId); 
		PageVM<InnerStateVm> innerStatePageVM = new PageVM<InnerStateVm>(pageIndex,totalCount, pageSize,innerStateVms);
		return innerStatePageVM;
	}
	public MetaInnerStateDetailDao getMetaInnerStateDetailDao() {
		return metaInnerStateDetailDao;
	}
	public void setMetaInnerStateDetailDao(
			MetaInnerStateDetailDao metaInnerStateDetailDao) {
		this.metaInnerStateDetailDao = metaInnerStateDetailDao;
	}
	@Override
	public String updateInnerStateDetail(
			MetaInnerStateDetail metaInnerStateDetail) {
		// TODO Auto-generated method stub
		MetaInnerStateDetail newStateDetail=metaInnerStateDetailDao.get(metaInnerStateDetail.getId());
		if(newStateDetail!=null){
			newStateDetail.setStartTime(metaInnerStateDetail.getStartTime());
			newStateDetail.setEndTime(metaInnerStateDetail.getEndTime());
			newStateDetail.setMemo(metaInnerStateDetail.getMemo());
			metaInnerStateDetailDao.saveOrUpdate(newStateDetail);
			return newStateDetail.getId();
		}
		return null;
	}

	
}
