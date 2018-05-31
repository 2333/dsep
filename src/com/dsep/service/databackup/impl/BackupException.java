package com.dsep.service.databackup.impl;

import com.dsep.common.exception.BusinessException;

public class BackupException extends BusinessException {
	public BackupException(){
		super();
	}
	
	public BackupException(String msg){
		super(msg);
	}
}
