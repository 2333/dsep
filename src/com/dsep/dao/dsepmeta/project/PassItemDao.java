package com.dsep.dao.dsepmeta.project;

import java.util.List;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.project.PassItem;

public interface PassItemDao extends DsepMetaDao<PassItem, String> {

	public abstract List<PassItem> getAll();

	public abstract List<PassItem> getPassItemByProjectId(String projectId);

	public abstract List<PassItem> retrive(String projectId, Integer status,
			String unitId, String discId, String principalId, int pageIndex,
			int pageSize, Boolean desc, String orderProperName);

	public abstract Integer count(String projectId, Integer status,
			String unitId, String discId, String principalId);

	public abstract PassItem getPassItemById(String passItemId);
}
