package com.dsep.dao.dsepmeta.expert.evaluation;

import java.util.List;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.expert.EvalAchv;


public interface EvalAchvDao extends DsepMetaDao<EvalAchv,String>
{
	public List<EvalAchv> getByPaperId(String paperId);
	
	public List<EvalAchv> getByDiscCatId(String discCatId);
}
