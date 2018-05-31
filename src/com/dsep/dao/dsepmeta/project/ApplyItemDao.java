package com.dsep.dao.dsepmeta.project;

import java.util.List;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.project.ApplyItem;
import com.dsep.util.project.ApplyItemStatus;

public interface ApplyItemDao extends DsepMetaDao<ApplyItem, String> {

	public abstract List<ApplyItem> getAll();

	public abstract List<ApplyItem> retrive(String projectId,
			ApplyItemStatus status, String unitId, String discId,
			String userId, int pageIndex, int pageSize, Boolean desc,
			String orderProperName);

	public abstract List<ApplyItem> retrive2Status(String projectId,
			ApplyItemStatus status1, ApplyItemStatus status2, String unitId,
			String discId, String userId, int pageIndex, int pageSize,
			Boolean desc, String orderProperName);

	public abstract List<ApplyItem> getApplyItemsByProjectId(String projectId);

	public abstract int count(String projectId, ApplyItemStatus status,
			String unitId, String discId, String userId);

	public abstract int updateApplyItemStatus(String id, ApplyItemStatus status);

	public abstract void deleteAttachment(String projectId);

	public abstract void deleteById(String itemId);

}
