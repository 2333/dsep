package com.dsep.dao.base;

import com.dsep.dao.common.Dao;
import com.dsep.entity.Storage;

public interface StorageDao extends Dao<Storage, String>{
	
	abstract public Storage getActiveStorage();
	
	abstract public Storage getStorageById(String id);
}
