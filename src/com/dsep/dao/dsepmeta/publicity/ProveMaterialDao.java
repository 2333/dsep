package com.dsep.dao.dsepmeta.publicity;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.dsepmeta.ProveMaterial;

public interface ProveMaterialDao extends DsepMetaDao<ProveMaterial,String>{
	
	/**
	 * 根据证明项ID获取证明材料的路径，如果没有则返回空
	 * @param proveItemId
	 * @return 证明材料的路径
	 */
	public String getMaterialPath(String proveItemId);
	
}
