package com.dsep.service.publicity.feedback;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.dsepmeta.FeedbackResponse;
import com.dsep.vm.JqgridVM;
import com.dsep.vm.PageVM;
import com.dsep.vm.feedback.FeedbackResponseVM;

@Transactional(propagation = Propagation.SUPPORTS)
public interface FeedbackResponseService {
	
	/**
	 * 根据查询条件获取反馈数据的列表
	 * @param pageIndex
	 * @param pageSize
	 * @param desc
	 * @param orderPropName
	 * @param conditionalObjection
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public PageVM<FeedbackResponseVM> getFeedbackResponseVM(int pageIndex,
			int pageSize, boolean desc, String orderPropName,
			FeedbackResponse conditionalResponse) throws IllegalArgumentException, IllegalAccessException;
	
	/**
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param desc
	 * @param orderPropName
	 * @param conditionalResponse
	 * @return
	 */
	public JqgridVM getSameItemJqgridVM(int pageIndex,
		int pageSize,boolean desc, String orderPropName,
		FeedbackResponse conditionalResponse);
	
	/**
	 * 对于某一采集项，获取针对其提出的所有问题项的集合
	 * @param pageIndex
	 * @param pageSize
	 * @param desc
	 * @param orderPropName
	 * @param conditionalResponse
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public PageVM<FeedbackResponseVM> getSameResponseVM(int pageIndex,
			int pageSize, boolean desc, String orderPropName,
			FeedbackResponse conditionalResponse) throws IllegalArgumentException, IllegalAccessException;
	
	
	
	/**
	 * 根据Id删除相应的反馈答复项
	 * @param feedbackId
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public boolean deleteFeedbackResponse(String feedbackResponseId);
	

	/**
	 * 针对某条数据项，该反馈项是否是唯一的问题项
	 * 例如：某条数据项公共库比对有问题、异议也有问题，那么返回1
	 * 如果只是异议有问题，那么返回0
	 * @param feedbackResponse 反馈答复项
	 * @return 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public int getSameProblemNumber(FeedbackResponse feedbackResponse) throws IllegalArgumentException, IllegalAccessException;
	
	/**
	 * 根据ID获取反馈答复
	 * @param responseId
	 * @return
	 */
	public FeedbackResponse getResponseById(String responseId);

	/**
	 * 保存反馈答复意见
	 * @param responseItemId
	 * @param responseType
	 * @param adviceValue
	 * @param responseAdvice
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public boolean saveResponse(String responseItemId, String responseType,
			String adviceValue, String responseAdvice);

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public int testSave(FeedbackResponse saveResponse,String responseItemId);
	
	/**
	 * 上传证明材料
	 * @param responseItemId
	 * @param proveMaterialId
	 * @return
	 */
	public boolean uploadFile(String responseItemId,String proveMaterialId);
	
	/**
	 * 删除附件
	 * @param responseItemId 反馈答复ID
	 * @param attachmentId 附件ID
	 */
	public boolean deleteProveMaterial(String responseItemId, String attachmentId);

	/**
	 * 判断学校是否对所有的反馈数据都给出了答复意见
	 * @param unitId 学校ID
	 * @param feedbackType TODO
	 * @param feedbackRoundId 反馈批次ID
	 * @return
	 */
	public boolean isAllAdvice(String unitId,String feedbackType, String feedbackRoundId);
	
	/**
	 * 提交反馈答复
	 * @param unitId 学校ID
	 * @param feedbackType TODO
	 * @param feedbackRoundId 反馈轮次ID
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public boolean submitResponse(String unitId, String feedbackType, String feedbackRoundId);

	/**
	 * 如果某条数据项有多条反馈项时，其中一条的答复意见为删除
	 * 那么，该数据项多有的反馈项的答复意见均为删除
	 * @param problemEntityId
	 * @param responseItemId
	 * @param responseType
	 * @param adviceValue
	 * @param responseAdvice
	 * @param feedbackRoundId TODO
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public boolean saveDeleteResponse(String problemEntityId,
		String responseItemId, String responseType, String adviceValue,
		String responseAdvice, String feedbackRoundId) throws IllegalArgumentException, IllegalAccessException;

	/**
	 * 学位中心同意学校的处理意见
	 * @param responseItemIdList
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public boolean agreeUnitAdvice(List<String> responseItemIdList,String userId);
	
	/**
	 * 学位中心不同意学校的处理意见
	 * @param responseItemIdList
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public boolean disagreeUnitAdvice(List<String> responseItemIdList,String userId);
}
