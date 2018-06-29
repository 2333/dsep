package com.dsep.service.base;

import com.dsep.entity.Storage;

public interface StorageService {

	/**
	 * 获取当前激活的存储设备
	 * @return
	 */
	abstract public Storage getActiveStorage();
	
	/**
	 * 获取指定的存储设备
	 * @param id
	 * @return
	 */
	abstract public Storage getStorage(String id);
}
