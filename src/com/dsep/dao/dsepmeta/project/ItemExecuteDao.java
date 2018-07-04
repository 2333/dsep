package com.dsep.dao.dsepmeta.project;

import java.util.List;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.project.ItemExecute;

public interface ItemExecuteDao extends DsepMetaDao<ItemExecute, String>{

	public abstract List<ItemExecute> getAll();

	public abstract ItemExecute getResultByItemId(String itemId);

	public abstract ItemExecute getResultById(String resultId);
}
