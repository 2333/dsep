package com.dsep.service.flow;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.dsepmeta.PreEval;
import com.dsep.vm.PageVM;
import com.dsep.vm.flow.PreEvalVM;
@Transactional(propagation = Propagation.SUPPORTS)
public interface PreFlowService {
	/**
	 * 获取与参评学科信息
	 * @param unitId
	 * @param discId
	 * @param orderPropName
	 * @param asc
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	abstract public PageVM<PreEvalVM> getCollectPreByPage(String unitId, String discId,String isReport,String isEval,String isUnitintReport,int pageIndex,int pageSize,boolean asc,String orderPropName);
	
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	abstract public boolean updatePreEval(PreEval preEval,String userId) throws NoSuchFieldException, SecurityException;
	
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	abstract public boolean savePreEval(PreEval preEval,String userId);
	
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	abstract public boolean importDiscsFromBase(String unitId,String userId);
	
	abstract public String getPreState(String unitId);
	
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	abstract public boolean unit2Center(String unitId,String isUnitReport,String userId);
	
	/**
	 * 是否在预参评中存在该学校
	 * @param unitId
	 * @return
	 */
	abstract public String isHaveUnit(String unitId);
	
	/**
	 * 学校是否订阅单位报告
	 * @param unitId
	 * @return
	 */
	abstract public String isReport(String unitId);
	
	abstract public List<PreEval> getPreEvalsByUnitIds(String unitIds);
		
}
