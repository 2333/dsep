package com.dsep.service.expert.select;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.expert.Expert;

@Transactional(propagation = Propagation.SUPPORTS)
public interface SelectReConstructService {
	//@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract void select(String ruleId) throws InstantiationException,
			IllegalAccessException;
}
