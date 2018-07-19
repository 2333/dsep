package com.dsep.service.publicity.prepublic;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.domain.dsepmeta.publicity.CollectionDelete;
import com.dsep.entity.dsepmeta.PublicityManagement;
import com.dsep.vm.publicity.PublicityManagementVM;



@Transactional(propagation=Propagation.SUPPORTS)
public interface PrePublicityService {
	
	/**
	 * 获取当前公示轮次的状态
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public String getPublicityRoundStatus() throws IllegalArgumentException, IllegalAccessException;
	
	/**
	 * 获取当期开启的公示轮次
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public PublicityManagement getCurrentPublicityRound() throws IllegalArgumentException, IllegalAccessException;
	
	/**
	 * 获取当期开启的公示轮次
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public PublicityManagementVM getCurrentPublicityRoundVM() throws IllegalArgumentException, IllegalAccessException;
	
	/**
	 * 是否可以开启新的公示轮次
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public boolean isOpenNewPrePublicityRound() throws IllegalArgumentException, IllegalAccessException;
	
	/**
	 * 开启新的公示轮次，预公示和公示是公示轮次的不同状态
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public boolean openNewPublicityRound() throws IllegalArgumentException, IllegalAccessException;
	
	/**
	 * 开始进行公示
	 * @param publicityName 公示名称
	 * @param endTime 结束时间
	 * @param remark TODO
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws ParseException 
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public boolean setPublicity(String publicityName,String beginTime,String endTime, String remark) throws IllegalArgumentException, IllegalAccessException, ParseException;
	
	
	/**
	 * 预公示时删除备份数据
	 * @param entityId 实体ID
	 * @param pkValue 主键ID
	 * @param seqNo 实体序号
	 * @param versionId 版本号
	 * @param unitId 学校ID
	 * @param discId 学科ID
	 * @return 执行是否成功
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public boolean deleteBackData(String entityId,String versionId,String pkValue,
			String seqNo,String unitId,String discId);
	
	/**
	 * 预公示时批量删除备份数据
	 * @param entityId 实体ID
	 * @param pkSeqMap 主键和序号的Map,key为主键，序号为value
	 * @param versionId 版本号
	 * @param unitId 学校ID
	 * @param discId 学科ID
	 * @return 
	 * @throws Exception 
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public boolean deleteBatchBackData(String entityId,String versionId,
			List<CollectionDelete> deleteList) throws Exception;
	
	/**
	 * 删除某一公示轮次并批量删除备份数据，暂时作为测试时删除数据使用
	 * @param publicityRoundId
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public boolean deletePublicityRound(String publicityRoundId);

	
	/**
	 * 预公示时不公示某条数据
	 * @param entityId
	 * @param versionId
	 * @param pkValue
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public boolean notPubBackData(String entityId, String versionId, String pkValue);
	
	/**
	 * 预公示时批量不公示数据
	 * @param entityId
	 * @param versionId
	 * @param pkValueList
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public boolean notPubBackDataList(String entityId,String versionId,List<String> pkValueList);
	
	/**
	 * 预公示时公示某条数据
	 * @param entityId
	 * @param versionId
	 * @param pkValue
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public boolean pubBackData(String entityId,String versionId,String pkValue);

	/**
	 * 预公示时批量公示数据
	 * @param entityId
	 * @param versionId
	 * @param pkValueList
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public boolean pubBackDataList(String entityId,String versionId,List<String> pkValueList);

	/**
	 * 立即开启公示批次
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public String beginPublicity() throws IllegalArgumentException, IllegalAccessException;
	
}
