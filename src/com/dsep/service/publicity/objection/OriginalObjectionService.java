package com.dsep.service.publicity.objection;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.OriginalObjection;
import com.dsep.entity.enumeration.DsepEnum;
import com.dsep.vm.PageVM;
import com.dsep.vm.publicity.OriginalObjectionVM;

@Transactional(propagation = Propagation.SUPPORTS)
public interface OriginalObjectionService {

	/**
	 * 根据查询条件获取原始异议信息集合
	 * 
	 * @param conditionalObjection
	 *            属性值不为空的为查询条件
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public PageVM<OriginalObjection> getOriginalObjection(int pageIndex,
			int pageSize, boolean desc, String orderPropName,
			OriginalObjection conditionalObjection)
			throws IllegalArgumentException, IllegalAccessException;

	/**
	 * 根据ID获取对应的异议
	 * @param objectionId
	 * @return
	 */
	public OriginalObjection getObjectionById(String objectionId);
	
	
	/**
	 * 根据实体ID获取该实体对应的所有异议类型
	 * @param entityId 实体ID
	 * @return key为字段Id,value为异议类型名称
	 */
	public Map<String,String> getObjectTypeByEntityId(String entityId);

	/**
	 * 增加新的异议
	 * @param newObjection 异议实体
	 * @param filePath 证明文件的路径
	 * @return
	 * @throws Exception 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public boolean addNewObjection(OriginalObjection newObjection,String filePath) throws Exception;
	
	
	
	/**
	 * 修改异议内容
	 * 
	 * @param newObjection
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public boolean updateObjection(OriginalObjection newObjection)
			throws NoSuchFieldException, SecurityException;

	/**
	 * 删除某一条异议
	 * @param objectionId
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public boolean deleteObjection(String objectionId);

	/**
	 * 学校和学科用户对于不通过的异议直接点击删除，后台操作则是改变其状态位
	 * @param objectionId 未通过的异议Id
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public boolean departmentNotPassObjection(String objectionId);
	
	/**
	 * 学校用户对于不通过的异议直接点击删除，后台操作则是改变其状态位
	 * @param objectionId 未通过的异议Id
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public boolean centerNotPassObjection(String objectionId);
	

	/**
	 * 获取异议的集合
	 * @param unitId
	 * @param discId
	 * @param conditions
	 * @param orderName
	 * @param order_flag
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public PageVM<OriginalObjectionVM> showOriginalObjections(OriginalObjection objection, String orderName,
			boolean order_flag, int pageIndex, int pageSize)
			throws IllegalArgumentException, IllegalAccessException;

	/**
	 * 修改异议内容
	 * @param key
	 * @param dataMap
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public int updateObjectionRowData(String key, Map<String, Object> dataMap);

	/**
	 * 学校提交某轮次的所有异议
	 * @param publicityRoundId 公示轮次Id
	 * @param unitId 学校Id
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public void updateSchoolStatusToSubmit(String publicityRoundId,String unitId);

	/**
	 * 导出已提交的异议
	 * @param rootPath 路径
	 * @param userType TODO
	 * @param unitId 学校ID
	 * @param currentRoundId 要导出异议的公示轮次ID
	 * @return
	 */
	public String downloadSubmitObjection(OriginalObjection conditionalObjection,
			String rootPath, String userType);
	
	/**
	 * 根据查询条件查询原始异议，返回查询到的数据条数
	 * @param conditionalObjection
	 * @return 数据条数
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public int getObjectionCount(OriginalObjection conditionalObjection) throws IllegalArgumentException, IllegalAccessException;

	/**
	 * 获取某条异议对应的证明材料路径，如果没有则返回空
	 * @param objectionId 异议ID
	 * @return 证明材料的路径
	 */
	public String getProveMaterial(String objectionId);

	/**
	 * 学位中心将所有异议设置为已处理
	 * @param publicityRoundId
	 * @return
	 */
	public boolean processAllObjection(String publicityRoundId);

	/**
	 * 上传证明材料
	 * @param objectionItemId
	 * @param proveMaterialId
	 * @return
	 */
	public boolean uploadFile(String objectionItemId, String proveMaterialId);

	/**
	 * 删除证明材料
	 * @param objectionItemId
	 * @param proveMaterialId
	 * @return
	 */
	public boolean deleteProveMaterial(String objectionItemId,
			String proveMaterialId);

}
