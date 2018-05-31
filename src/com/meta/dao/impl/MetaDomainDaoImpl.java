package com.meta.dao.impl;
import java.util.List;
import com.dsep.dao.common.impl.DaoImpl;
import com.meta.dao.MetaDomainDao;
import com.meta.entity.MetaDomain;
import com.meta.entity.MetaDomainState;

public class MetaDomainDaoImpl extends DaoImpl<MetaDomain, String> 
       implements MetaDomainDao{
	
	public List<MetaDomain> getAllAvailDomain(String occasion, MetaDomainState state)
	{		
		String hql = "from MetaDomain d where d.occasion=? and d.state=?";
		List<MetaDomain> list=super.hqlFind(hql, new Object[]{occasion,state.getState()});
		return list;
	}

	@Override
	public boolean updateInnerState(String metaDomainId, String innerState) {
		// TODO Auto-generated method stub
		String hql = "update MetaDomain domain set domain.innerState =? where domain.id=?";
		if(super.hqlBulkUpdate(hql, new Object[]{innerState,metaDomainId})>0){
			return true;
		}else{
			return false;
		}	
	}

	@Override
	public boolean updateState(String metaDomainId, String state) {
		// TODO Auto-generated method stub
		String hql = "update MetaDomain domain set domain.state =? where domain.id=?";
		if(super.hqlBulkUpdate(hql, new Object[]{state,metaDomainId})>0){
			return true;
		}else{
			return false;
		}	
	}
	
}
