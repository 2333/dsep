package com.dsep.util.quartzJob;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.dsep.common.logger.LoggerTool;
import com.dsep.service.publicity.objection.PublicityService;

public class PublicityQuartz extends QuartzJobBean{

	private PublicityService publicityService;
	private LoggerTool loggerTool;
	
	public void setLoggerTool(LoggerTool loggerTool) {
		this.loggerTool = loggerTool;
	}

	public void setPublicityService(PublicityService publicityService) {
		this.publicityService = publicityService;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		String result;
		try {
			result = publicityService.autoBeginPublicityRound();
			loggerTool.info(result);
			for(int i=0;i < 10 ;i++){
				System.out.println("***********************************************");
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			loggerTool.error(e.getMessage());
		}
	}

}
