package com.dsep.common.exception;

public class SessionNullException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SessionNullException() {
		// TODO Auto-generated constructor stub
		super("Session为空！");
	}
	
	public SessionNullException(String message){
		super(message);
	}
	
	public SessionNullException(Throwable throwable)
	{
		super(throwable);
	}
	
	public SessionNullException(String message,Throwable throwable){
		super(message,throwable);
	}
}
