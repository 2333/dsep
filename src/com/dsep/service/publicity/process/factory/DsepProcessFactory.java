package com.dsep.service.publicity.process.factory;

import com.dsep.entity.User;
import com.dsep.service.publicity.process.production.feedback.FeedbackProcess;
import com.dsep.service.publicity.process.production.objection.ObjectionProcess;
import com.dsep.service.publicity.process.production.publicity.CenterPublicityProcess;
import com.dsep.service.publicity.process.production.publicity.DiscPublicityProcess;
import com.dsep.service.publicity.process.production.publicity.PublicityProcess;
import com.dsep.service.publicity.process.production.publicity.UnitPublicityProcess;

public abstract class DsepProcessFactory {
	
	public static DsepProcessFactory getDsepFactory(User user){
		switch(user.getUserType()){
		case "1":
			return new CenterProcessFactory();
		case "2":
			return new UnitProcessFactory(user.getUnitId());
		case "3":
			return new DiscProcessFactory(user.getUnitId(),user.getDiscId());
		default:
			return null;
		}
	}
	
	public abstract PublicityProcess getPublicityProcess();
	
	public abstract ObjectionProcess getObjectionProcess();
	
	public abstract FeedbackProcess getFeedbackProcess();
}
