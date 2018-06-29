package com.dsep.dao.dsepmeta.project.impl;


import java.util.List;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.project.JudgeResultDao;
import com.dsep.entity.project.JudgeResult;

public class JudgeResultDaoImpl extends DsepMetaDaoImpl<JudgeResult,String>
	implements JudgeResultDao{

	private String getTableName(){
		return super.getTableName("P", "JUDGERESULT");
	}
	@Override
	public JudgeResult getResultByItemId(String itemId) {
		// TODO Auto-generated method stub
		String sql = "select * from "+getTableName()+" where ITEM_Id = '"+itemId+"'";
		List<JudgeResult> judgeResults = super.sqlFind(sql);
		if( judgeResults.isEmpty() ) return null;
		JudgeResult judgeResult = judgeResults.get(0);
		return judgeResult;
	}
	@Override
	public JudgeResult getResultById(String parameter) {
		// TODO Auto-generated method stub
		String sql = "select * from "+getTableName()+" where id  = '"+parameter+"'";
		return super.sqlFind(sql).get(0);
	}
}
