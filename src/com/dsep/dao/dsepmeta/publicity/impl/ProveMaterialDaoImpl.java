package com.dsep.dao.dsepmeta.publicity.impl;

import java.util.List;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.publicity.ProveMaterialDao;
import com.dsep.entity.dsepmeta.ProveMaterial;

public class ProveMaterialDaoImpl extends DsepMetaDaoImpl<ProveMaterial,String>
	implements ProveMaterialDao{

	private String getTableName(){
		return super.getTableName("F", "PROF_MATERIAL");
	}
	

	@Override
	public String getMaterialPath(String proveItemId) {
		// TODO Auto-generated method stub
		String str = "select prove_material_path from "+ getTableName() + " where prove_item_id = ?";
		Object[] param = new Object[1];
		param[0] = proveItemId;
		List<String> result = super.GetShadowResult(str, param);
		if( result != null && result.size() > 0)
			return result.get(0);
		else
			return null;
	}
	
}
