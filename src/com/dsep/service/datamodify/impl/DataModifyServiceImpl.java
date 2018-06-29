package com.dsep.service.datamodify.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dsep.common.exception.BusinessException;
import com.dsep.dao.dsepmeta.databackup.CenterDataBackupDao;
import com.dsep.dao.dsepmeta.datamodify.DataModifyHistoryDao;
import com.dsep.entity.dsepmeta.DataModifyHistory;
import com.dsep.entity.dsepmeta.FeedbackResponse;
import com.dsep.service.datamodify.DataModifyService;
import com.dsep.service.dsepmeta.dsepmetas.DMCollectService;
import com.dsep.util.Configurations;
import com.dsep.vm.PageVM;
import com.dsep.vm.feedback.DataModifyHistoryVM;
import com.meta.entity.MetaEntity;
import com.meta.service.MetaEntityService;

public class DataModifyServiceImpl implements DataModifyService{
	

	private DataModifyHistoryDao dataModifyHistoryDao;
	private DMCollectService collectService;

	public void setDataModifyHistoryDao(DataModifyHistoryDao dataModifyHistoryDao) {
		this.dataModifyHistoryDao = dataModifyHistoryDao;
	}

	public void setCollectService(DMCollectService collectService) {
		this.collectService = collectService;
	}



	@Override
	public boolean changeEntityData(DataModifyHistory theHistory) {
		// TODO Auto-generated method stub
		dataModifyHistoryDao.save(theHistory);
		Map<String,String> theMap = new HashMap<String,String>();
		theMap.put(theHistory.getAttrEnsName(), theHistory.getAttrModifyValue());
		
		int result = collectService.updateRow(theHistory.getEntityId(), theHistory.getOperateUserId(), 
			theHistory.getEntityItemId(),theHistory.getUnitId(),theHistory.getDiscId(),theMap);
		if( result > 0)
			return true;
		else 
			throw new BusinessException("出现错误，更新失败，数据可能已被删除");
	}

	@Override
	public boolean deleteEntityData(DataModifyHistory theHistory) {
		// TODO Auto-generated method stub
		Map<String,String> theMap = new HashMap<String,String>();
		
		//传入空的Map,只更新修改时间和修改用户
		
		
		//保存"删除的历史记录"
		dataModifyHistoryDao.save(theHistory);
		
		return true;
	}

	@Override
	public PageVM<DataModifyHistoryVM> getDataModifyHistoryVM(int pageIndex,
			int pageSize, boolean desc, String orderPropName,
			DataModifyHistory conditionalModifyHistory)
			throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		List<DataModifyHistory> historyList = dataModifyHistoryDao.queryByCondition(conditionalModifyHistory, pageIndex, pageSize, desc, orderPropName);
		List<DataModifyHistoryVM> vmList = new ArrayList<DataModifyHistoryVM>();
		for(DataModifyHistory modifyHistory:historyList){
			DataModifyHistoryVM newVm = new DataModifyHistoryVM(modifyHistory);
			vmList.add(newVm);
		}
		int count = dataModifyHistoryDao.getCountByCondition(conditionalModifyHistory);
		return new PageVM<DataModifyHistoryVM>(pageIndex,count,pageSize,vmList);
	}

	@Override
	public boolean saveModifyHistory(DataModifyHistory modifyHistory) {
		// TODO Auto-generated method stub
		dataModifyHistoryDao.save(modifyHistory);
		return true;
	}

	
}
