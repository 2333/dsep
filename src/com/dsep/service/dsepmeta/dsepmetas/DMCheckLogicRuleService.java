package com.dsep.service.dsepmeta.dsepmetas;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.User;
import com.dsep.vm.EntityLogicCheckVM;

/**
 * 实体校验接口
 * 
 * @author lubin
 * 
 */
@Transactional(propagation = Propagation.SUPPORTS)
public interface DMCheckLogicRuleService {
	//	/**
	//	 * 对某一学校某一学科的各个采集表具体数据进行“数据的逻辑检查”
	//	 * 
	//	 * @param unitId
	//	 *            学校Id
	//	 * @param displineId
	//	 *            学科Id
	//	 * @param userId
	//	 *            学科Id
	//	 * @return 逻辑检查结果
	//	 */
	//	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	//	public abstract boolean attrLogicCheck(String unitId, String displineId,String userId);
	//
	//	/**
	//	 * 对某一学校某一学科的采集表进行“表的逻辑检查”
	//	 * 
	//	 * @param unitId
	//	 *            学校Id
	//	 * @param displineId
	//	 *            学科Id
	//	 * @param userId
	//	 *            学科Id
	//	 * @return 逻辑检查结果
	//	 */
	//	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	//	public abstract boolean entityLogicCheck(String unitId, String displineId,String userId);
	//	
	/**
	 * 
	 * @param unitId
	 * @param discId
	 * @param userId
	 * @return
	 * 
	 * 对某一学校某一学科的各个采集表具体数据进行“数据的逻辑检查”
	 * 对某一学校某一学科的采集表进行“表的逻辑检查”
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract boolean logicCheck(String unitId, String discId,
			String userId);

	/** 对实体进行逻辑检查,不将结果写入数据库，直接返回结果
	 * @param unitId
	 * @param displineId
	 * @return 实体检查结果VM
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract EntityLogicCheckVM entityLogicCheck(String unitId,
			String displineId);

	public abstract String getLogicCheckResultBeforeSubmit(String unitId,
			String discId, User user);

	public abstract String exportErrorAndWarnData(String unitId, String discId,
			User user, String rootPath);

	// 通过逻辑检查，返回"CHECKPASS"
	public abstract String checkSingleField(String entityId, String attrName,
			String attrVal);

}
