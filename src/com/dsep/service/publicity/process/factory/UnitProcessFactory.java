package com.dsep.service.publicity.process.factory;

import com.dsep.service.publicity.process.production.feedback.FeedbackProcess;
import com.dsep.service.publicity.process.production.feedback.UnitFeedbackProcess;
import com.dsep.service.publicity.process.production.objection.ObjectionProcess;
import com.dsep.service.publicity.process.production.objection.UnitObjectionProcess;
import com.dsep.service.publicity.process.production.publicity.PublicityProcess;
import com.dsep.service.publicity.process.production.publicity.UnitPublicityProcess;

public class UnitProcessFactory extends DsepProcessFactory{

	private String unitId;
	
	public UnitProcessFactory(String unitId){
		this.unitId = unitId;
	}
	
	@Override
	public PublicityProcess getPublicityProcess() {
		// TODO Auto-generated method stub
		return new UnitPublicityProcess(this.unitId);
	}

	@Override
	public ObjectionProcess getObjectionProcess() {
		// TODO Auto-generated method stub
		return new UnitObjectionProcess(this.unitId);
	}

	@Override
	public FeedbackProcess getFeedbackProcess() {
		// TODO Auto-generated method stub
		return new UnitFeedbackProcess(this.unitId);
	}
	 
}
