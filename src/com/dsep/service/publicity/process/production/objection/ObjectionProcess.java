package com.dsep.service.publicity.process.production.objection;

import java.util.Map;

import com.dsep.entity.dsepmeta.OriginalObjection;
import com.dsep.entity.enumeration.publicity.UnitObjectStatus;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;
import com.dsep.service.publicity.objection.OriginalObjectionService;
import com.dsep.vm.PageVM;
import com.dsep.vm.publicity.OriginalObjectionVM;

public abstract class ObjectionProcess{
	
	/**
	 * 获取异议数据，学科、学校和中心有所不同
	 * @param queryObjection
	 * @param order_flag
	 * @param orderName
	 * @param pageIndex
	 * @param pageSize
	 * @param status TODO
	 * @param originalObjectionService
	 * @return
	 */
	public abstract PageVM<OriginalObjectionVM> getOriginalObjection(OriginalObjection queryObjection,
			boolean order_flag,String orderName,int pageIndex,int pageSize, 
			String status, OriginalObjectionService originalObjectionService);

	/**
	 * 获取界面上能够显示的异议状态
	 * 学校用户能看到未提交和已提交
	 * 学科用户是未提交、已提交和未通过学校审核
	 * 中心用户看到的是未处理和已处理
	 * @return
	 */
	public abstract Map<String,String> getObjectStatus(); 
	
	/**
	 * 获取异议汇总页面用来作为查询条件的学科集合
	 * @param disciplineService
	 * @return
	 */
	public abstract Map<String,String> getDisciplineMap(DisciplineService disciplineService);

	public abstract Map<String,String> getJoinUnitMap(UnitService unitService);
	
	/**
	 * 导出已提交的异议
	 * @param currentRoundId
	 * @param realPath TODO
	 * @param originalObjectionService
	 * @return
	 */
	public abstract String downloadSubmitObjection(String currentRoundId,String realPath, OriginalObjectionService originalObjectionService);

	/**
	 * 导出已处理的异议
	 * @param currentRoundId
	 * @param realPath
	 * @param originalObjectionService
	 * @return
	 */
	public abstract String downloadProcessObjection(String currentRoundId,String realPath,OriginalObjectionService originalObjectionService);
	
	public abstract boolean deleteObjection(OriginalObjectionService originalObjectionService,String objectionId);

	/**
	 * 针对某条采集项，获取其已提出的异议，学校和学科显示相同
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public PageVM<OriginalObjectionVM> getExistedObjectionList(OriginalObjection queryObjection,
		OriginalObjectionService originalObjectionService) throws IllegalArgumentException, IllegalAccessException{
		queryObjection.setUnitStatus(UnitObjectStatus.NOTSUBMIT.getStatus());
		this.setExistedObjection(queryObjection);//根据学校、学科、中心的不同设置查询条件
		return originalObjectionService.showOriginalObjections(queryObjection, null, true, 1, 100);
	}
	
	/**
	 * 学校、学科或中心更改异议内容和异议字段
	 * @param objection
	 * @return
	 */
	public abstract int updateObjectionRowData(OriginalObjectionVM objection,
		OriginalObjectionService originalObjectionService);
	
	/**
	 * 设置“获取已提出的异议”的查询条件，不同单位的条件不同
	 * @param queryObjection
	 */
	protected abstract void setExistedObjection(OriginalObjection queryObjection);
}
	
	
