package com.meta.dao;
import com.dsep.dao.common.Dao;
import com.meta.entity.MetaAttribute;
import com.meta.entity.MetaEntity;
import java.util.List;
import java.util.Map;

public interface MetaEntityDao extends Dao<MetaEntity, String>{
	/**
	 * 根据实体表名称获得当前实体
	 * @param name 实体名称
	 * @return 实体
	 */
	public abstract MetaEntity getByName(String name);
	/**
	 * 根据领域ID和分类ID获得所有的实体类
	 * @param domainId 领域ID
	 * @param categoryId 分类ID
	 * @return 该领域该分类下的所有实体类
	 */
	public abstract List<MetaEntity> getEntities(String domainId, String categoryId);	
	/**
	 * 删除实体通过entityId
	 * @param entityId
	 * @return 
	 */
	public abstract boolean deleteEntityById(String entityId);
	
	/**
	 * 返回带有附件实体的id和名称
	 * @return
	 */
	public Map<String, String> getEntitiesHaveFile();
	
}
