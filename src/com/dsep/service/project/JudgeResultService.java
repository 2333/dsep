package com.dsep.service.project;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.project.JudgeResult;


@Transactional(propagation = Propagation.SUPPORTS)
public interface JudgeResultService {
	
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract String create(JudgeResult result);
	
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract void update(JudgeResult result);
	
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract void delete(String id);
	
	public abstract JudgeResult getResultByItemId(String id);

	public abstract JudgeResult getResultById(String parameter);
}
