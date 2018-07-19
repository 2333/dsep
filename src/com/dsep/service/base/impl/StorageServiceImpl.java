package com.dsep.service.base.impl;

import com.dsep.dao.base.StorageDao;
import com.dsep.entity.Storage;
import com.dsep.service.base.StorageService;

public class StorageServiceImpl implements StorageService{
	
	private StorageDao storageDao;

	public StorageDao getStorageDao() {
		return storageDao;
	}

	public void setStorageDao(StorageDao storageDao) {
		this.storageDao = storageDao;
	}

	@Override
	public Storage getActiveStorage() {
		return storageDao.getActiveStorage();
	}

	@Override
	public Storage getStorage(String id) {
		return storageDao.get(id);
	}

}
