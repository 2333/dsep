package com.dsep.dao.dsepmeta.project;

import java.util.List;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.project.SchoolProject;

public interface SchoolProjectDao extends DsepMetaDao<SchoolProject, String> {

	public abstract List<SchoolProject> getAll();

	public abstract SchoolProject retrive(Object conditions);

	public abstract void deleteAttachment(String projectId);

	public abstract String getAttachmentId(String projectId);

	public abstract List<String> getUniqueProjectTypes(String unitId);

	public abstract List<SchoolProject> pageFind(String unitId, int pageIndex,
			int pageSize, Boolean desc, String orderProperName);

	public abstract Boolean closeProject(String projectId);

	public abstract Boolean restartProject(String projectId);

	public abstract List<SchoolProject> pageFind(String unitId,
			String projectType, int pageIndex, int pageSize, Boolean desc,
			String orderProperName);

	public abstract int count(String unitId, String projectType);

	public abstract List<SchoolProject> pageFind(String unitId,int pageIndex, int pageSize, boolean desc,
			String orderProperName, String projectName, String projectType,int currentState);

	public abstract int setProjectState(String projectId, int i);
}
