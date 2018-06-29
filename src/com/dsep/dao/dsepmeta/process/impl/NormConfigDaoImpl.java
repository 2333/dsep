package com.dsep.dao.dsepmeta.process.impl;

import java.util.LinkedList;
import java.util.List;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.process.NormConfigDao;
import com.dsep.entity.dsepmeta.NormConfig;

public class NormConfigDaoImpl extends DsepMetaDaoImpl<NormConfig, String>
		implements NormConfigDao {

	String tableName = super.getTableName("O", "NORM_CONFIG");

	@Override
	public void updateStatus(NormConfig normConfig) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<NormConfig> getConfig() {

		StringBuilder strb = new StringBuilder("Select * from " + tableName);

		List<NormConfig> result = super.sqlFind(strb.toString());

		return result;
	}

	@Override
	public void saveNormConfigList(List<NormConfig> list) {
		// TODO Auto-generated method stub
		for (int i = 0; i < list.size(); i++) {
			NormConfig normConfig = list.get(i);
			super.save(normConfig);
		}
	}

	@Override
	public int selectStatus(String entityId) {

		String sql = "select * from " + tableName + " where ENTITY_ID='"
				+ entityId + "'";
		List<NormConfig> list = super.sqlFind(sql);
		int status = list.get(0).getNormStatus();
		return status;
	}

	@Override
	public NormConfig getConfigByEntityId(String entityId) {

		String sql = "select * from " + tableName + " where ENTITY_ID='"
				+ entityId + "'";
		List<NormConfig> list = super.sqlFind(sql);
		return list.get(0);
	}

}
