package com.dsep.common.exception;

public class BusinessException extends RuntimeException{

	public BusinessException() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public BusinessException(String message){
		super(message);
	}
	
	public BusinessException(Throwable throwable)
	{
		super(throwable);
	}
	public BusinessException(String message,Throwable throwable){
		super(message,throwable);
	}
}
