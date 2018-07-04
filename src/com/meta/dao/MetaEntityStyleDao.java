package com.meta.dao;
import com.dsep.dao.common.Dao;
import com.meta.entity.MetaEntityStyle;

public interface MetaEntityStyleDao extends Dao<MetaEntityStyle, String>{
	/**
	 * 根据实体ID和使用场合，获得所需的显示风格
	 * @param entityID 实体ID
	 * @param occassion 使用场合
	 * @return 当前需要的实体风格
	 */
	public MetaEntityStyle getByOccassion(String entityId, String occassion);
}
