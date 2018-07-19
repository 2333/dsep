package com.dsep.service.project.impl;

import com.dsep.dao.dsepmeta.project.JudgeResultDao;
import com.dsep.entity.project.JudgeResult;
import com.dsep.service.project.JudgeResultService;

public class JudgeResultServiceImpl implements JudgeResultService{

	private JudgeResultDao judgeResultDao;
	
	@Override
	public String create(JudgeResult result) {
		// TODO Auto-generated method stub
		return judgeResultDao.save(result);
	}

	@Override
	public void update(JudgeResult result) {
		// TODO Auto-generated method stub
		judgeResultDao.saveOrUpdate(result);
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		judgeResultDao.deleteByKey(id);
	}

	@Override
	public JudgeResult getResultByItemId(String id) {
		// TODO Auto-generated method stub
		return judgeResultDao.getResultByItemId(id);
	}

	public JudgeResultDao getJudgeResultDao() {
		return judgeResultDao;
	}

	public void setJudgeResultDao(JudgeResultDao judgeResultDao) {
		this.judgeResultDao = judgeResultDao;
	}

	@Override
	public JudgeResult getResultById(String parameter) {
		// TODO Auto-generated method stub
		return judgeResultDao.getResultById(parameter);
	}
	
}
