package com.dsep.service.dsepmeta.dsepmetas.publicity;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface DMPrePublicService {
	
	/**
	 * 通过主键删除一行数据
	 * @param entity
	 * @param pkValue
	 * @param unitId
	 * @param discId
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract boolean deleteBackupRow(String entityId,String pkValue,String seqNo,
			String versionId,String unitId,String discId);
	
	/**
	 * 通过主键不公示数据，如果主键为空，则不公示整个采集项
	 * @param entityId 采集项实体ID
	 * @param versionId 版本号ID
	 * @param pkValue 主键值
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract boolean notPublicBackupData(String entityId,String versionId,String pkValue);
	
	/**
	 * 通过主键重新公示数据
	 * @param entityId 采集项实体ID
	 * @param versionId 版本号ID
	 * @param pkValue 主键值
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract boolean publicBackupData(String entityId,String versionId,String pkValue);
}
