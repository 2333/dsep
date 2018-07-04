package com.dsep.service.dataprocess.normalization;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.NormConfig;
import com.dsep.entity.dsepmeta.NormResult;
import com.dsep.vm.PageVM;

@Transactional(propagation=Propagation.SUPPORTS)
public interface NormalizationService {

	/**
	 * 初始化需要规范化检测的采集表,并显示
	 * @return 
	 */
	public abstract PageVM<NormConfig> initNormaTable(User user);
	/**
	 * 更新配置表
	 * @param user
	 * @return 
	 */
	public abstract int updateNormTable(User user,String entityId);
	/**
	 * 展示每张采集表需要规范的数据
	 * @return 
	 */
	public abstract PageVM<NormResult> showNormaFieldData(String entityId);
	/**
	 * 查询某实体某字段的规范化数据的数据集
	 * @param entityId
	 * @param fieldName
	 * @return
	 */
	public abstract String showNormDataSet(String entityId,String fieldName);
	/**
	 * 存储一条规范化映射结果
	 * @return 
	 */
	public abstract int saveOneNormaResult(String normResult);
	/**
	 * 存储多条规范化映射结果
	 */
	public abstract void saveManyNormalResult(List<NormResult> normResultList);
	/**
	 * 导出规范化映射结果
	 * @return
	 */
	public abstract String exportNormaResult(String rootPath);
	
}
