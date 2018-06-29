package com.dsep.dao.dsepmeta.base;

import java.util.List;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import com.dsep.dao.common.Dao;
import com.dsep.dao.common.DsepMetaDao;
import com.dsep.dao.common.impl.DaoImpl;
import com.dsep.entity.dsepmeta.TeachDisc;

public interface TeachDiscDao extends DsepMetaDao<TeachDisc, String>{
	
	/**
	 * 分页获取某学校某学科的教师学科信息
	 * @param unitId
	 * @param discId
	 * @param pageIndex
	 * @param pageSize
	 * @param orderProperName
	 * @param desc
	 * @return
	 */
	public abstract List<TeachDisc> getTeachDiscByPage(String teachLoginId,String teachName,String unitId,
			String discId,int pageIndex,int pageSize,String orderProperName,Boolean desc);
	/**
	 * 获取本学校、本学科所有教师学科信息
	 * @param unitId
	 * @param discId
	 * @return
	 */
	public abstract List<TeachDisc> getAllTeachDiscs(String unitId,String discId);
	/**
	 * 获取本学校、本学科的教师学科记录数
	 * @param unitId（null 学校ID不作为筛选条件）
	 * @param discId（null 学科ID不作为筛选条件）
	 * @return
	 */
	public int getTeachDiscCount(String teachLoginId,String teachName,String unitId,String discId);
	
	/**
	 * 从基础数据表导入学科教师表
	 * @param pkValue
	 * @param unitId
	 * @param discId
	 * @return
	 */
	public String import2FromUser(String pkValue,String seqNo,String unitId,String discId);
	
	/**
	 * 删除一条教师学科记录
	 * @param pkValue
	 * @param unitid
	 * @param discId
	 * @param seqNo
	 * @return 返回翻出记录的ID
	 */
	public String delTeachDisc(String pkValue);
	
	/**
	 * 对SEQ_NO之后的序号减一
	 * @param unitId
	 * @param discId
	 * @param seqNo
	 * @return 返回此序号
	 */
	public Integer updateAfterSeqNo(String unitId,String discId,int seqNo);
	
	/**
	 * 通过学校学科Id,获取用户TEACH_ID
	 * @param unitId(NULL时， 不作为筛选条件)
	 * @param discId(null时，不作为筛选条件)
	 * @return 返回teachId 列表
	 */
	public List<String> getTeachIds(String unitId,String discId);
	
	/**
	 * 该教师是否在本学校、本学科存在
	 * @param unitId
	 * @param discId
	 * @param teachId
	 * @return
	 */
	public boolean isTeacherExist(String unitId,String discId,String teachId);
	
}
