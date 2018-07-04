package com.dsep.dao.dsepmeta.base;

import java.util.List;

import com.dsep.entity.dsepmeta.IndexMap;

public interface IndexMapDao {
	
	List<IndexMap> getIndexMapsByIndexId(String indexId);

}
