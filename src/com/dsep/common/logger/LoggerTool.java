package com.dsep.common.logger;
import org.apache.log4j.Logger;

import com.dsep.common.annotation.EnableLog;

public class LoggerTool {
	@EnableLog
	private Logger logger;
	public void debug(Object object)
	{
		logger.debug(object);
	}
	public void info(Object object)
	{
		logger.info(object);
	}
	public void warn(Object object)
	{
		logger.warn(object);
	}
	public void error(Object object)
	{
		logger.error(object);
	}

}
