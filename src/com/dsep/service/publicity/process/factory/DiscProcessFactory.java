package com.dsep.service.publicity.process.factory;

import com.dsep.service.publicity.process.production.feedback.DiscFeedbackProcess;
import com.dsep.service.publicity.process.production.feedback.FeedbackProcess;
import com.dsep.service.publicity.process.production.objection.DiscObjectionProcess;
import com.dsep.service.publicity.process.production.objection.ObjectionProcess;
import com.dsep.service.publicity.process.production.publicity.DiscPublicityProcess;
import com.dsep.service.publicity.process.production.publicity.PublicityProcess;

public class DiscProcessFactory extends DsepProcessFactory{

	private String unitId;
	private String discId;
	
	public DiscProcessFactory(String unitId,String discId){
		this.unitId = unitId;
		this.discId = discId;
	}
	
	@Override
	public PublicityProcess getPublicityProcess() {
		// TODO Auto-generated method stub
		return new DiscPublicityProcess(this.unitId,this.discId);
	}

	@Override
	public ObjectionProcess getObjectionProcess() {
		// TODO Auto-generated method stub
		return new DiscObjectionProcess(this.unitId,this.discId);
	}

	@Override
	public FeedbackProcess getFeedbackProcess() {
		// TODO Auto-generated method stub
		return new DiscFeedbackProcess(this.unitId, this.discId);
	}

}
