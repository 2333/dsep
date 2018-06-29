package com.dsep.dao.dsepmeta.base;

import com.dsep.entity.dsepmeta.Index;

public interface IndexDao {
	/**
	 * 通过指标id，获取指标
	 * @param id
	 * @return
	 */
	public Index getIndexById(String id);	
}
