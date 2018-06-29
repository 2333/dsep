package com.dsep.service.databackup;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.dsepmeta.BackupManagement;
import com.dsep.service.databackup.impl.BackupException;
import com.dsep.vm.BackupManageVM;
import com.dsep.vm.PageVM;

@Transactional(propagation=Propagation.SUPPORTS)
/**
 * 
 * @author PanLinjie
 *
 */
public interface DataBackupService{
	
	/**
	 * 创建备份表
	 * @return 创建的备份表数
	 */
	public int createTable();
	
	
	
	/**
	 * 备份学科数据
	 * @param unitId 学校Id
	 * @param discId 学科Id
	 * @return
	 * @throws Exception 
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public boolean backupDiscipline(String unitId,String discId) throws Exception;
	
	
	
	/**
	 * 学科数据恢复
	 * @param id 要恢复的版本号的id
	 * @param unitId 学校ID
	 * @param discId 学科ID
	 * @param versionId 版本号
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED)
	public boolean restoreDiscipline(String id,String unitId,String discId,String versionId) throws NoSuchFieldException, SecurityException;
	
	/**
	 * 获取某学科某原表的所有备份版本号
	 * @param schoolId 学校代码
	 * @param disciplineId 学科代码
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public PageVM<BackupManageVM> getAllBackupVersion(int pageIndex,int pageSize,boolean order_flag,String orderName,
		String schoolId,String disciplineId) throws IllegalArgumentException, IllegalAccessException;
	
	/**
	 * 修改备份的备注信息
	 * @param backup 备份版本信息
	 * @id 列的Id值
	 * @remark 备份的备注信息
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED)
	public int updateBackupVersion(String id,String remark) throws NoSuchFieldException, SecurityException;
	
	/**
	 * 获取某学科某原表的数据总数
	 * @param tableName 原表表名
	 * @param schoolId
	 * @param disciplineId
	 * @return
	 * @throws Exception 
	 */
	public int GetDataCount(String tableName,String schoolId,String disciplineId) ;
	
	/**
	 * 获取某学科某备份表某版本的数据条数
	 * @param tableName 原表表名
	 * @param schoolId
	 * @param disciplineId
	 * @param versionId
	 * @return
	 * @throws Exception 
	 */
	public int GetBackupDataCount(String tableName,String backupTableName,String schoolId,String disciplineId,String versionId) ;
	
	/**
	 * 删除某版本的备份数据
	 * @param versionId
	 * @param discId
	 * @return
	 * @throws Exception 
	 */
	public boolean deleteDiscipline(String versionId,String discId) throws Exception;



	
}
