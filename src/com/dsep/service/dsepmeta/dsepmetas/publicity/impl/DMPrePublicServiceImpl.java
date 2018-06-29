package com.dsep.service.dsepmeta.dsepmetas.publicity.impl;

import java.util.HashMap;
import java.util.Map;

import com.dsep.service.dsepmeta.MetaOper;
import com.dsep.service.dsepmeta.dsepmetas.publicity.DMPrePublicService;
import com.dsep.util.UnitTest;

public class DMPrePublicServiceImpl extends MetaOper
	implements DMPrePublicService{

	@Override
	public boolean deleteBackupRow(String entityId, String pkValue, String seqNo,
			String versionId,String unitId,String discId) {
		// TODO Auto-generated method stub
		Map<String, Object> rowData = new HashMap<String, Object>(0);
		rowData.put("UNIT_ID", unitId);
		rowData.put("DISC_ID", discId);
		/*if(!"-1".equals(seqNo)){
			super.updateBackSeqNo(entityId, seqNo,versionId,rowData);
		}*/
		/*UnitTest.testPrint();*/
		super.deleteBackupRow(entityId, pkValue, versionId);
		return true;
	}

	@Override
	public boolean notPublicBackupData(String entityId, String versionId,
			String pkValue) {
		// TODO Auto-generated method stub
		return super.notPubBackData(entityId, versionId, pkValue);
	}
	
	public boolean publicBackupData(String entityid,String versionId,String pkValue){
		return super.pubBackData(entityid, versionId, pkValue);
	}
	
	
}
