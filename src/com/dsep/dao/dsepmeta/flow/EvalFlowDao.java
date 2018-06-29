package com.dsep.dao.dsepmeta.flow;

import java.util.List;
import java.util.Map;

import org.apache.catalina.tribes.tipis.Streamable;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.dsepmeta.Eval;
import com.meta.dao.MetaObjectDao;

public interface EvalFlowDao extends DsepMetaDao<Eval,String> {
	/**
	 * 获得评估流程信息
	 * @param unitId
	 * @param disciplineId
	 * @param status
	 * @param orderPropName
	 * @param asc
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	
	abstract public List<Eval> getEvalsData(String unitId,String disciplineId,String status,Boolean isEval,Boolean isReport, String orderPropName, boolean asc, int pageIndex, int pageSize);

	/** 获得评估流程信息数量
	 * @param unitId (null或空值默认为所有学校)
	 * @param disciplineId (null或空值默认为所有学科)
	 * @param status (-1默认为所有状态)
	 * @return
	 */
	public int evalDataCount(String unitId, String disciplineId,String status,Boolean isEval); 
	
	/** 根据条件查看是否有符合该条件的流程状态
	 * @param unitId （null或空值默认为所有学校）
	 * @param disciplineId （null或空值默认为所有学科）
	 * @param status (-1默认为所有状态)
	 * @return
	 */
	abstract public boolean haveStatus(String unitId,String disciplineId,String status); 
	
	/** 某学校某学科的的流程状态
	 * @param unitId （不能为空）
	 * @param disciplineId （不能为空）
	 * @return
	 */
	abstract public Integer getStatus(String unitId,String disciplineId);
	
	/** 根据条件修改某学校某学科的流程状态
	 * @param unitId （null或空值默认为所有学校）
	 * @param disciplineId （null或空值默认为所有学科）
	 * @param newtatus
	 */
	abstract public void updateStatus(String unitId,String disciplineId,Integer newtatus);
	
	
	/**
	 * 获取当前流水号
	 * @param unitId
	 * @param discId
	 * @return
	 */
	abstract public Integer getStreamNo(String unitId,String discId);
	
	/**
	 * 更新学科版本号
	 * @param unitId
	 * @param discId
	 * @param newVersionNo
	 * @return
	 */
	abstract public boolean updateDiscVersionNo(String unitId,String discId,String newVersionNo);
	
	/**
	 * 更新学校版本号
	 * @param unitId
	 * @param newVersionNo
	 * @return
	 */
	abstract public boolean updateUnitVersionNo(String unitId,String newVersionNo);
	/**
	 * 更新学校流水号
	 * @param unitId
	 * @return
	 */
	abstract public boolean updateUnitStreamNo(String unitId,Integer newStreamNo);
	
	/**
	 * 更新学科流水号 
	 * @param unitId
	 * @return
	 */
	abstract public boolean updateDiscStreamNo(String unitId,String discId,Integer newStreamNo);
	/**
	 * 获取版本号
	 * @param unitId
	 * @param discId（null为获取学科版本号）
	 * @return
	 */
	abstract public String getVersionNo(String unitId,String discId);
	
	/**
	 * 根据学科ID获取参评学校
	 * @param disciplineId 学科ID
	 * @return 参评学校集合
	 */
	public List<String> getEvalUnitByDiscId(String disciplineId);
	
	/**
	 * 根据学校ID获取参评学科
	 * @param unitId 学校ID
	 * @return 参评学科集合
	 */
	abstract public List<String> getEvalDiscByUnitId(String unitId);
	
	/**
	 * 获取两学校都有的参评学科
	 * @param formerUnitId 学校1
	 * @param choosenUnitId 学校2
	 * @return 参评学科的集合
	 */
	public List<String> getBothEvalDiscList(String formerUnitId,String choosenUnitId);
	
	/**
	 * 学科信息从与参评导入参评表中
	 * @param unitId
	 * @param userId
	 * @return
	 */
	abstract public boolean importDiscsFromPreDisc(String unitId,String userId);
	
	abstract public boolean updateEval(Eval eval) throws NoSuchFieldException, SecurityException;
	/**
	 * 参评表中是否已经存在该学校的学科信息
	 * @param unitId
	 * @return
	 */
	abstract public boolean haveImportPre(String unitId);
	
	/**
	 * 该学校学科是否参评
	 * @param unitId
	 * @param discId
	 * @return
	 */
	abstract public boolean isInEval(String unitId,String discId);
	/**
	 * 获取所有参评学校
	 * @return
	 */
	abstract public List<String> getAllEvalUnitIds();
	/**
	 * 获取学校、学科、采集项根ID
	 * @param unitId
	 * @param discid
	 * @return
	 */
	abstract public List<String[]> getUnitIdDiscIdAndCollectId(String unitId,String discid);
	
	public List<String> getAllEvalDiscIds();
	
	/**
	 * 在参评管理中是否存在该学校或者学科
	 * @param unitId
	 * @param discId,（为null时表示是否有该学校）
	 * @return
	 */
	abstract public Boolean hasUnitOrDisc(String unitId,String discId);
	/**
	 * 获取所有学科的id,name
	 * @return
	 */
	abstract public List<String[]> getAllDiscIdAndName();
	
	/**
	 * 通过学校id、学科Id 获取参评信息
	 * @param unitId
	 * @param discId
	 * @return
	 */
	abstract public Eval getEvalByUnitIdAndDiscId(String unitId,String discId);
	
	/**
	 * 根据学校ID获取所有学科参评信息
	 * @param unitId
	 * @return
	 */
	abstract public List<Eval> getEvalByUnitId(String unitId);
	/**
	 * 根据学校Id和学科Id更新参评表
	 * @param unitId
	 * @param discId
	 * @param attachId
	 * @return
	 */
	abstract public int updateEvalByUnitIdAndDiscId(String unitId,String discId,String attachId);
}
