package com.dsep.service.datamodify;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.dsepmeta.DataModifyHistory;
import com.dsep.entity.dsepmeta.FeedbackResponse;
import com.dsep.vm.PageVM;
import com.dsep.vm.feedback.DataModifyHistoryVM;
import com.dsep.vm.feedback.FeedbackResponseVM;

/**
 * 数据修改服务
 * @author Pangeneral
 * @version 创建时间：2014年9月25日  下午5:02:25
 */
@Transactional(propagation = Propagation.SUPPORTS)
public interface DataModifyService {
	
	/**
	 * 修改数据
	 * @param theHistory
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public boolean changeEntityData(DataModifyHistory theHistory);
	
	
	/**
	 * 保存数据修改的历史
	 * @param modifyHistory
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public boolean saveModifyHistory(DataModifyHistory modifyHistory);
	
	/**
	 * 删除数据
	 * @param entityId
	 * @param entityItemId
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public boolean deleteEntityData(DataModifyHistory theHistory);
	
	/**
	 * 根据查询条件获取数据修改记录的列表
	 * @param pageIndex
	 * @param pageSize
	 * @param desc
	 * @param orderPropName
	 * @param conditionalObjection
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public PageVM<DataModifyHistoryVM> getDataModifyHistoryVM(int pageIndex,
			int pageSize, boolean desc, String orderPropName,
			DataModifyHistory conditionalModifyHistory) throws IllegalArgumentException, IllegalAccessException;
}
