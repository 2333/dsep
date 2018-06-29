package com.dsep.service.publicity.process.factory;

import com.dsep.entity.User;
import com.dsep.service.publicity.process.production.feedback.CenterFeedbackProcess;
import com.dsep.service.publicity.process.production.feedback.FeedbackProcess;
import com.dsep.service.publicity.process.production.objection.CenterObjectionProcess;
import com.dsep.service.publicity.process.production.objection.ObjectionProcess;
import com.dsep.service.publicity.process.production.publicity.CenterPublicityProcess;
import com.dsep.service.publicity.process.production.publicity.PublicityProcess;

public class CenterProcessFactory extends DsepProcessFactory{

	@Override
	public PublicityProcess getPublicityProcess() {
		// TODO Auto-generated method stub
		return new CenterPublicityProcess();
	}

	@Override
	public ObjectionProcess getObjectionProcess() {
		// TODO Auto-generated method stub
		return new CenterObjectionProcess();
	}

	@Override
	public FeedbackProcess getFeedbackProcess() {
		// TODO Auto-generated method stub
		return new CenterFeedbackProcess();
	}


}
