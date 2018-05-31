package com.dsep.service.flow.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.common.exception.CollectBusinessException;
import com.dsep.dao.dsepmeta.flow.EvalFlowDao;
import com.dsep.dao.dsepmeta.flow.PreFlowDao;
import com.dsep.entity.dsepmeta.PreEval;
import com.dsep.service.flow.PreFlowService;
import com.dsep.vm.PageVM;
import com.dsep.vm.flow.PreEvalVM;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class PreFlowServiceImpl implements PreFlowService{
	
	private PreFlowDao preFlowDao;
	private EvalFlowDao evalFlowDao;
	public PreFlowDao getPreFlowDao() {
		return preFlowDao;
	}

	public void setPreFlowDao(PreFlowDao preFlowDao) {
		this.preFlowDao = preFlowDao;
	}

	@Override
	public PageVM<PreEvalVM> getCollectPreByPage(String unitId, String discId,String isReport,String isEval,String isUnitReport,
			int pageIndex, int pageSize, boolean asc, String orderPropName) {
		// TODO Auto-generated method stub
		Boolean is_Eval=covertToBoolean(isEval);
		Boolean is_Report= covertToBoolean(isReport);
		Boolean is_Unit_Report= covertToBoolean(isUnitReport);
		List<PreEvalVM> preEvalVMs= new ArrayList<PreEvalVM>(0);
		
		List<PreEval> preEvals = preFlowDao.getPreDataByPage(unitId, discId,is_Report,is_Eval,is_Unit_Report,orderPropName, asc, pageIndex, pageSize);
		for(PreEval preEval: preEvals)
		{
			PreEvalVM preEvalVM= new PreEvalVM(preEval);
			preEvalVMs.add(preEvalVM);
		}
		int totalCount=preFlowDao.preEvalCount(unitId, discId);
		PageVM <PreEvalVM> pageVM= new PageVM<PreEvalVM>(pageIndex, totalCount, pageSize,preEvalVMs);
		return pageVM;
	}

	@Override
	public boolean updatePreEval(PreEval preEval,String userId) throws NoSuchFieldException, SecurityException {
		// TODO Auto-generated method stub
		preEval.setModifyUserId(userId);
		preEval.setModifyTime(new Date());
		if(preFlowDao.updatePreEval(preEval))
		{
			return true;
		}else {
			throw new CollectBusinessException("编辑失败！");
		}
		
	}

	@Override
	public boolean savePreEval(PreEval preEval, String userId) {
		// TODO Auto-generated method stub
		preEval.setInsertTime(new Date());
		preEval.setInsertUserId(userId);
		preEval.setModifyTime(new Date());
		preEval.setModifyUserId(userId);
		preFlowDao.savePreEval(preEval);
		return true;
	}
	private Boolean covertToBoolean(String s)
	{
		if("0".equals(s))
			return false;
		if("1".equals(s))
			return true;
		return null;
	}

	@Override
	public boolean importDiscsFromBase(String unitId, String userId) {
		// TODO Auto-generated method stub
		if(!preFlowDao.isHaveUnit(unitId))
		{
			if(preFlowDao.importDiscsFromBaseDisc(unitId, userId))
				return true;
			else {
				throw new CollectBusinessException("导入失败！");
			}
		}else {
			throw new CollectBusinessException("基础学科信息已导入！");
		}
		
	}
	@Override
	public String getPreState(String unitId) {
		// TODO Auto-generated method stub
		return preFlowDao.getUnitPreState(unitId)+"";
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public boolean unit2Center(String unitId,String isUnitReport,String userId) {
		// TODO Auto-generated method stub
		if(!preFlowDao.isHaveUnit(unitId))
			throw new CollectBusinessException("本学校未参加预参评！");
		if(preFlowDao.getUnitPreState(unitId)==1)
			throw new CollectBusinessException("已提交至中心！");
		else{
			if(preFlowDao.updateState(unitId, "1",isUnitReport)){
				if(!evalFlowDao.hasUnitOrDisc(unitId, null))
					evalFlowDao.importDiscsFromPreDisc(unitId, userId);
				return true;
			}		
			else {
				throw new CollectBusinessException("提交失败！");
			}
		}
	}
	@Override
	public String isHaveUnit(String unitId) {
		// TODO Auto-generated method stub
		if(preFlowDao.isHaveUnit(unitId)){
			return "1";
		}else{
			return "0";
		}
		
	}
	@Override
	public String isReport(String unitId) {
		// TODO Auto-generated method stub
		return preFlowDao.isUnitReport(unitId)+"";
	}
	public EvalFlowDao getEvalFlowDao() {
		return evalFlowDao;
	}

	public void setEvalFlowDao(EvalFlowDao evalFlowDao) {
		this.evalFlowDao = evalFlowDao;
	}

	@Override
	public List<PreEval> getPreEvalsByUnitIds(String unitId) {
		// TODO Auto-generated method stub
		return preFlowDao.getPreEvalsByUnitId(unitId);
	}

	

	


}
