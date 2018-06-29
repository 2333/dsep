package com.dsep.service.flow.impl;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.common.exception.BusinessException;
import com.dsep.common.exception.CollectBusinessException;
import com.dsep.dao.dsepmeta.flow.EvalFlowDao;
import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.Eval;
import com.dsep.service.check.similarity.SimilarityCheckService;
import com.dsep.service.dsepmeta.dsepmetas.DMCheckLogicRuleService;
import com.dsep.service.flow.EvalFlowService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.VersionNoCreate;
import com.dsep.vm.EntityLogicCheckVM;
import com.dsep.vm.PageVM;
import com.dsep.vm.flow.CollectFlowVM;
import com.dsep.vm.flow.EvalVM;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class EvalFlowServiceImpl implements EvalFlowService {
	private EvalFlowDao evalFlowDao;
	private SimilarityCheckService similarityCheckService;
	private DMCheckLogicRuleService checkLogicRule;
	

	@Override
	public boolean isEditable(String unitId, String discId,String userType) {
		// TODO Auto-generated method stub
		int state=evalFlowDao.getStatus(unitId,discId);
		/**
		 * userType
		 * 1：中心用户
		 * 2：学校用户
		 * 3：学科用户
		 */
		switch(userType)
		{
			case "1":
				if(state==5)
					return true;
				break;
			case "2":
				if(state==6)
					return true;
				break;
			case "3":
				if(state==0||state==3)
					return true;
				break;
			default:
				break;
		}
		return false;
	}

	@Override
	public Integer getEvalStatus(String unitId, String discId) {
		// TODO Auto-generated method stub
		return evalFlowDao.getStatus(unitId,discId);
	}

	@Override
	public boolean disc2Unit(String unitId, String discId,String isConfirm) {
		// TODO Auto-generated method stub
		if(!evalFlowDao.isInEval(unitId, discId)){
			throw new CollectBusinessException("该学科没有参与本轮参评！");
		}	
		/*if(!similarityCheckService.isSimCheckComplete(unitId, discId)){
			throw new CollectBusinessException("该学科没有进行重复检查或学科成果存在重复性问题，请解决后再提交！");
		}*/
		if(evalFlowDao.haveStatus(unitId, discId, "0")||evalFlowDao.haveStatus(unitId, discId, "3"))
		{
			if(!"1".equals(isConfirm)){
				EntityLogicCheckVM logicCheckVM = checkLogicRule.entityLogicCheck(unitId, discId);
				if(!logicCheckVM.isPassed()){
					String message =JsonConvertor.obj2JSON(logicCheckVM); 
					throw new CollectBusinessException(message,"json");
				}
			}
			//执行更新
			updateDiscVersionNo(unitId, discId);
			//更改提交状态
			evalFlowDao.updateStatus(unitId, discId,1);
			return true;
		}else{
			throw new CollectBusinessException("学科成果已提交！");
		}
			
	}

	@Override
	public boolean unitBack2Disc(String unitId, String discId) {
		// TODO Auto-generated method stub
		if(evalFlowDao.haveStatus(unitId, discId, "1")||evalFlowDao.haveStatus(unitId, discId, "4"))
	    {
			evalFlowDao.updateStatus(unitId, discId,3);
			return true;
		}else {
			throw new CollectBusinessException("学科成果退回失败！");
		}
			
	}

	@Override
	public boolean unit2Center(String unitId) {
		// TODO Auto-generated method stub
		String errorMessage= null;
		boolean isPassed = true;
		if(!evalFlowDao.isInEval(unitId,null)){
			isPassed = false;
			errorMessage = "该学校未参加本次参评！";
		}else if(evalFlowDao.haveStatus(unitId, null, "0")||
			evalFlowDao.haveStatus(unitId, null, "3")){
			isPassed = false;
			errorMessage = "存在参评学科未提交至学校，请确认";
		}else if(evalFlowDao.haveStatus(unitId, null, "2")){
			isPassed = false;
			errorMessage = "已经提交至中心!";
		}else if(evalFlowDao.haveStatus(unitId, null, "5")){
			isPassed = false;
			errorMessage = "中心已终止提交，请联系管理员！";
		}else if(evalFlowDao.haveStatus(unitId, null, "6")){
			isPassed = false;
			errorMessage = "学校正在编辑部分学科数据，请确认后在提交！";
		}
		if(isPassed){
			updateUnitVersionNo(unitId);
			//更改提交状态
			evalFlowDao.updateStatus(unitId, null, 2);
			return true;
		}else{
			throw new CollectBusinessException(errorMessage);
		}	
	}

	@Override
	public boolean centerBack2Unit(String unitId) {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public boolean unitRepealFromCenter(String unitId) {
		// TODO Auto-generated method stub
		if(evalFlowDao.haveStatus(unitId, null, "2"))
		{
			evalFlowDao.updateStatus(unitId, null, 4);
			return true;
		}else {
			throw new CollectBusinessException("撤销失败！");
		}
		
	}

	@Override
	public boolean terminateUnitSubmit() {
		// TODO Auto-generated method stub
		evalFlowDao.updateStatus(null,null,5);
		return true;
	}
	
	private boolean updateDiscVersionNo(String unitId, String discId) {
		// TODO Auto-generated method stub
		//获取当前流水号
		int int_streamNo = evalFlowDao.getStreamNo(unitId, discId).intValue();
		//修改流水号格式
		NumberFormat formatter = NumberFormat.getNumberInstance();   
	    formatter.setMinimumIntegerDigits(4);   
	    formatter.setGroupingUsed(false);   
	    String streamNo = formatter.format(int_streamNo);
	    //创建新的版本号
	    String newVersionNo = VersionNoCreate.CreateVersionNo(unitId, discId, streamNo);
	    //版本号写入数据库
	    if(evalFlowDao.updateDiscVersionNo(unitId, discId, newVersionNo)){
			//更新学科流水号
			Integer newStreamNo = new Integer(int_streamNo+1);
			evalFlowDao.updateDiscStreamNo(unitId, discId, newStreamNo);
	    	return true;
	    }
	    else{
	    	throw new CollectBusinessException("学科版本号生成失败！");
	    }
	}
	
	private boolean updateUnitVersionNo(String unitId) {
		// TODO Auto-generated method stub
		//获取当前流水号
		int int_streamNo = evalFlowDao.getStreamNo(unitId, null).intValue();
		//修改流水号格式
		NumberFormat formatter = NumberFormat.getNumberInstance();   
		formatter.setMinimumIntegerDigits(4);   
		formatter.setGroupingUsed(false);   
		String streamNo = formatter.format(int_streamNo);
		//创建新的版本号
		String newVersionNo = VersionNoCreate.CreateVersionNo(unitId, null, streamNo);
		//将版本号写入数据库
		if(evalFlowDao.updateUnitVersionNo(unitId,newVersionNo))
		{
			//更新学习流水号
			Integer newStreamNo = new Integer(int_streamNo+1);
			evalFlowDao.updateUnitStreamNo(unitId, newStreamNo);
	    	return true;
	    }
	    else{
	    	throw new CollectBusinessException("学校版本号生成失败！");
	    }
	}

	@Override
	public boolean rebootSubmit() {
		// TODO Auto-generated method stub
		if(evalFlowDao.haveStatus(null, null, "5")){
			evalFlowDao.updateStatus(null, null, 2);
			return true;
		}else {
			throw new CollectBusinessException("参评正在进行中！");
		}
			
		
	}
	

	@Override
	public String getVersionNo(String unitId, String discId) {
		// TODO Auto-generated method stub
		return evalFlowDao.getVersionNo(unitId, discId);
		
	}

	@Override
	public PageVM<EvalVM> getCollectEvalByPage(String unitId,
			String discId, String status, Boolean isEval,Boolean isReport,int pageIndex, int pageSize,
			boolean asc, String orderProperName) {
		// TODO Auto-generated method stub
		List<Eval> evals=evalFlowDao.getEvalsData(unitId, discId, status, isEval,isReport,orderProperName, asc, pageIndex, pageSize);
		List<EvalVM> evalVMs= new ArrayList<EvalVM>(0);
		for(Eval e: evals)
		{
			EvalVM evalVM= new EvalVM(e);
			evalVMs.add(evalVM);
			
		}
		int totalCount=evalFlowDao.evalDataCount(unitId, discId,status,isEval);
		PageVM<EvalVM> pageVM= new PageVM<EvalVM>(pageIndex, totalCount, pageSize, evalVMs);
		return pageVM;
	}

	public EvalFlowDao getEvalFlowDao() {
		return evalFlowDao;
	}

	public void setEvalFlowDao(EvalFlowDao evalFlowDao) {
		this.evalFlowDao = evalFlowDao;
	}

	@Override
	public boolean importDiscsFromPre(String unitId, String userId) {
		// TODO Auto-generated method stub
		if(!evalFlowDao.haveImportPre(unitId)){
			if(evalFlowDao.importDiscsFromPreDisc(unitId, userId))
			return true;
			else{
				throw new CollectBusinessException("导入失败！");
			}
		}else {
			throw new CollectBusinessException("学科信息已经导入！");
		}
		
	}

	@Override
	public boolean updateEval(Eval eval, String userId) throws NoSuchFieldException, SecurityException {
		// TODO Auto-generated method stub
		String unitVersion = getVersionNo(eval.getUnitId(),null);
		Integer streamNo = evalFlowDao.getStreamNo(eval.getUnitId(), null);
		eval.setModifyUserId(userId);
		eval.setModifyTime(new Date());
		eval.setUnitVersionNo(unitVersion);
		eval.setUnitStreamNo(String.valueOf(streamNo));
		if(evalFlowDao.updateEval(eval))
		{
			return true;	
		}
		else{
			throw new CollectBusinessException("编辑失败 ！");
		}
		
	}
	public SimilarityCheckService getSimilarityCheckService() {
		return similarityCheckService;
	}

	public void setSimilarityCheckService(SimilarityCheckService similarityCheckService) {
		this.similarityCheckService = similarityCheckService;
	}

	@Override
	public boolean isEditableEval(String unitId) {
		// TODO Auto-generated method stub
		if(evalFlowDao.haveStatus(unitId, null, "2")
				||evalFlowDao.haveStatus(unitId, null, "5")){
			return false;
		}
		return true;
	}

	public DMCheckLogicRuleService getCheckLogicRule() {
		return checkLogicRule;
	}

	public void setCheckLogicRule(DMCheckLogicRuleService checkLogicRule) {
		this.checkLogicRule = checkLogicRule;
	}

	@Override
	public boolean eidtDiscData(String unitId, String discId) {
		// TODO Auto-generated method stub
		if(1==evalFlowDao.getStatus(unitId, discId)||
				4==evalFlowDao.getStatus(unitId, discId)){
				evalFlowDao.updateStatus(unitId, discId, 6);
				return true;
		}
		return false;
	}

	@Override
	public boolean confirmDiscData(String unitId, String discId) {
		// TODO Auto-generated method stub
		if(6==evalFlowDao.getStatus(unitId, discId)){
			updateDiscVersionNo(unitId, discId);
			evalFlowDao.updateStatus(unitId, discId, 1);
			return true;
		}
		return false;
	}

	@Override
	public String getUnitState(String unitId) {
		// TODO Auto-generated method stub
		if(evalFlowDao.haveStatus(unitId,null,"2")){
			return "2";
		}else if(evalFlowDao.haveStatus(unitId, null, "5")){
			return "5";
		}
		return "1";
	}

	@Override
	public List<Eval> getEvalByUnitId(String unitId) {
		// TODO Auto-generated method stub
		return evalFlowDao.getEvalByUnitId(unitId);
	}

	@Override
	public int updateEvalByUnitIdAndDiscId(String unitId, String discId,
			String attachId) {
		// TODO Auto-generated method stub
		return evalFlowDao.updateEvalByUnitIdAndDiscId(unitId, discId, attachId);
	}
	
	
}
