package com.dsep.service.expert.rule;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation=Propagation.SUPPORTS, isolation=Isolation.READ_COMMITTED, readOnly=false)
public interface RuleDetailService {
	
}
