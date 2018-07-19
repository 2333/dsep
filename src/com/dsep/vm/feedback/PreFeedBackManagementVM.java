package com.dsep.vm.feedback;

import java.util.Date;

import com.dsep.entity.dsepmeta.FeedbackManagement;
import com.dsep.util.DateProcess;

public class PreFeedBackManagementVM {
	private String feedBackRoundName;
	private String beginTime;
	private String endTime;
	private String remark;
	
	public PreFeedBackManagementVM(FeedbackManagement management){
		setFeedBackRoundName(management.getFeedbackRoundName());
		setBeginTime(management.getBeginTime());
		setEndTime(management.getEndTime());
		setRemark(management.getRemark());
	}

	public String getFeedBackRoundName() {
		return feedBackRoundName;
	}

	public void setFeedBackRoundName(String feedBackRoundName) {
		this.feedBackRoundName = feedBackRoundName;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = DateProcess.getShowingFormatDate(beginTime);
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = DateProcess.getShowingFormatDate(endTime);
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
