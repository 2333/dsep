package com.dsep.service.flow;


import java.util.List;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.Eval;
import com.dsep.vm.PageVM;
import com.dsep.vm.flow.EvalVM;

@Transactional(propagation = Propagation.SUPPORTS)
public interface EvalFlowService {
	/**
	 * 获取学科评估信息
	 * @param unitId
	 * @param discId
	 * @param status
	 * @param pageIndex
	 * @param pageSize
	 * @param asc
	 * @param orderProperName
	 * @return
	 */
	abstract public PageVM<EvalVM> getCollectEvalByPage(String unitId,
			String discId,String status,Boolean isEval,Boolean isReport,int pageIndex, int pageSize, boolean asc,
			String orderProperName);
	
	/**
	 * 学科是否可以编辑学科成果
	 * 通过用户类型和数据状态，判断是否可以操作数据
	 * 数据在学科为 0
	 * 数据在学校为 1
	 * 数据在中心为 2
	 * 数据由学校退回学科为 3
	 * 数据从中心退回学校 为 4
	 * 数据中心截止为提交为 5
	 * 学校正在编辑为6
	 * @param unitId
	 * @param displineId
	 * @param userType
	 * @return 是否可以编辑
	 */
	abstract public boolean isEditable(String unitId, String discId,String userType);
	/**
	 * 数据由学科提交至学校
	 * @param unitId
	 * @param discId
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	abstract public boolean disc2Unit(String unitId, String discId,String isConfig);
	/**
	 * 数据由学校退回学科
	 * @param unitId
	 * @param discId
	 * @return
	 */
	abstract public boolean unitBack2Disc(String unitId, String discId);
	/**
	 * 数据由学校提交至中心
	 * @param unitId
	 * @param discIds
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	abstract public boolean unit2Center(String unitId);
	/**
	 * 数据由中心退回学校
	 * @param unitId
	 * @param discIds
	 * @return
	 */
	abstract public boolean centerBack2Unit(String unitId);
	/**
	 * 学校撤销提交至中心的数据
	 * @param unitId
	 * @param discId
	 * @return
	 */
	abstract public boolean unitRepealFromCenter(String unitId);
	/**
	 * 终止学校提交数据至中心
	 * @return
	 */
	abstract public boolean terminateUnitSubmit();
	/**
	 * 
	 * @param unitId
	 * @param discId
	 * @return 获取数据状态
	 */
	abstract public Integer getEvalStatus(String unitId,String discId);
	/**
	 * 重新开启 提交
	 * @return
	 */
	abstract public boolean rebootSubmit();
	/**
	 * 获取版本号
	 * @param unitId
	 * @param discId（null为获取学校版本号）
	 * @return
	 */
	abstract public String getVersionNo(String unitId,String discId);
	/**
	 * 从预参评中导入参评中
	 * @param unitId
	 * @param userId
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	abstract public boolean importDiscsFromPre(String unitId,String userId);
	/**
	 * 更新参评管理
	 * @param eval
	 * @param userId
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	abstract public boolean updateEval(Eval eval,String userId) throws NoSuchFieldException, SecurityException;
	/**
	 * 学校是否可以编辑参评信息
	 * @param unitId
	 * @return
	 */
	abstract public boolean isEditableEval(String unitId);
	/**
	 * 学校用户进入学科成果编辑状态
	 * @param unitId
	 * @param discId
	 * @param user
	 * @return
	 */
	abstract public boolean eidtDiscData(String unitId,String discId);
	/**
	 * 学校确认对学科数据的修改 
	 * @param unitId
	 * @param discId
	 * @return
	 */
	abstract public boolean confirmDiscData(String unitId,String discId);
	
	/**
	 * 获取学校的状态（数据在学科或者学校：1；数据在中心：2；中心终止：5）
	 * @param unitId
	 * @return
	 */
	abstract public String getUnitState(String unitId);
	
	/**
	 * 根据学校ID获取所有参评学科
	 * @param unitId
	 * @return
	 */
	abstract public List<Eval> getEvalByUnitId(String unitId);
	/**
	 * 根据学校和学科Id更新学科简介附件Id
	 * @param unitId
	 * @param discId
	 * @param attachId
	 * @return
	 */
	abstract public int updateEvalByUnitIdAndDiscId(String unitId, String discId,String attachId);
	
}
