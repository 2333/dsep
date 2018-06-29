package com.dsep.service.check.logic;

import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.LogicCheckEntityResult;
import com.dsep.vm.LogicCheckVM;
import com.dsep.vm.PageVM;

public interface LogicCheckService {

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract boolean startLogicCheck(String unitId, String discId,
			User user);

	/**
	 * 中心登陆，显示逻辑左边表格的检查信息，是经过处理的学校的逻辑检查汇总信息
	 */
	public abstract PageVM<LogicCheckVM> showResultForCenter(
			String InputUnitId, User user, String orderPropName, boolean asc,
			int pageIndex, int pageSize);

	/**
	 * @return 学校用户登陆，显示逻辑左边表格的检查信息，是经过处理的学科的逻辑检查汇总信息
	 */
	public abstract PageVM<LogicCheckVM> showResultForUnit(
			String inputDiscId, User user, String orderPropName, boolean asc,
			int pageIndex, int pageSize);

	/**
	 * @return 学科用户登陆，显示逻辑左边表格的检查信息，是各个表的具体信息
	 */
	public abstract PageVM<LogicCheckVM> showResultForDisc(
			String unitId, String discId, String userId, String orderPropName,
			boolean asc, int pageIndex, int pageSize);

	
	
	public abstract PageVM<?> showLogicResultData(String unitId, String discId,
			String entityId, String userId, String orderPropName, boolean asc,
			int pageIndex, int pageSize);
	

	public abstract PageVM<LogicCheckEntityResult> showLogicWarnData(
			String unitId, String discId, String entityId, String userId,
			boolean onlyGetWarn, String orderPropName, boolean asc,
			int pageIndex, int pageSize);

	
	
	
	public abstract String exportErrorAndWarnData(String unitId, String discId,
			User user, String rootPath);

	
	
	
	public abstract String haveCheckCompleted(String unitId, String discId,
			String userId);

	public abstract Map<String, String> getInitDiscIdMaps(String unitId);

	public abstract Map<String, String> getInitUnitIdMaps();
	
	
}
