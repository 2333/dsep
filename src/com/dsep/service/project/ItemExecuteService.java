package com.dsep.service.project;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.project.ItemExecute;
import com.dsep.vm.PageVM;

@Transactional(propagation = Propagation.SUPPORTS)
public interface ItemExecuteService {
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public abstract String create(ItemExecute itemExecute);
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public abstract void update(ItemExecute itemExecute);
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public abstract void delete(String id);
	public abstract PageVM<ItemExecute> page(String id, int pageIndex,
			int pageSize, Boolean desc, String orderProperName)
			throws InstantiationException, IllegalAccessException;
	public abstract ItemExecute getResultByItemId(String itemId);
	public abstract ItemExecute getResultById(String resultId);
}
