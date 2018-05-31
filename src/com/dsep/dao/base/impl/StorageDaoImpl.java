package com.dsep.dao.base.impl;

import java.util.List;

import com.dsep.dao.base.StorageDao;
import com.dsep.dao.common.impl.DaoImpl;
import com.dsep.entity.Storage;

public class StorageDaoImpl extends DaoImpl<Storage, String> implements StorageDao{

	@Override
	public Storage getActiveStorage() {
		String hql="from Storage s where s.active=1";
		List<Storage>list =super.hqlFind(hql);
		if(list.size()>0)
			return list.get(0);
		else {
			return null;
		}
	}

	@Override
	public Storage getStorageById(String id) {
		String hql="from Storage s where s.id = " + id;
		List<Storage>list =super.hqlFind(hql);
		if(list.size()>0)
			return list.get(0);
		else {
			return null;
		}
	}

}
