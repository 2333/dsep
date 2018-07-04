package com.dsep.service.publicity.feedback;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.SUPPORTS)
public interface FeedbackImportService {
	
	/**
	 * 导入反馈的数据源
	 * @param feedbackRoundId 反馈批次ID
	 * @param feedbackType 反馈类型
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public int importFeedbackDataSource(String feedbackRoundId,String feedbackType) throws IllegalArgumentException, IllegalAccessException;
}
