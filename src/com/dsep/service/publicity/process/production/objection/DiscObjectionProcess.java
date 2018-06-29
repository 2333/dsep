package com.dsep.service.publicity.process.production.objection;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;

import com.dsep.entity.dsepmeta.OriginalObjection;
import com.dsep.entity.enumeration.publicity.UnitObjectStatus;
import com.dsep.entity.enumeration.rbac.UserType;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;
import com.dsep.service.publicity.objection.OriginalObjectionService;
import com.dsep.vm.PageVM;
import com.dsep.vm.publicity.OriginalObjectionVM;

public class DiscObjectionProcess extends ObjectionProcess{
	private String unitId;
	private String discId;
	
	public DiscObjectionProcess(String unitId,String discId){
		this.unitId = unitId;
		this.discId = discId;
	}

	@Override
	public PageVM<OriginalObjectionVM> getOriginalObjection(OriginalObjection queryObjection,
			boolean order_flag,String orderName,int pageIndex, int pageSize,
			String status, OriginalObjectionService originalObjectionService) {
		// TODO Auto-generated method stub
		queryObjection.setObjectUnitId(this.unitId);
		queryObjection.setProblemDiscId(this.discId);
		queryObjection.setUnitStatus(status);
		/*queryObjection.setObjectDiscId(this.discId);*/
		try {
			return originalObjectionService.showOriginalObjections(queryObjection, orderName, order_flag, pageIndex, pageSize);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Map<String, String> getObjectStatus() {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		Map<String,String> objectMap = new LinkedMap();
		objectMap.put(UnitObjectStatus.NOTSUBMIT.getStatus(),"未提交");
		objectMap.put(UnitObjectStatus.SUBMIT.getStatus(), "已提交");
		/*objectMap.put(ObjectStatus.UNITNOTPASS.getStatus(), "已删除");*/
		return objectMap;
	}

	@Override
	public Map<String, String> getDisciplineMap(DisciplineService disciplineService) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String downloadSubmitObjection(String currentRoundId,
			String realPath, OriginalObjectionService originalObjectionService) {
		// TODO Auto-generated method stub
		OriginalObjection conditionalObjection = new OriginalObjection();
		conditionalObjection.setObjectUnitId(this.unitId);
		conditionalObjection.setProblemDiscId(this.discId);
		/*conditionalObjection.setStatus(ObjectStatus.SUBMIT.getStatus());*/
		conditionalObjection.setCurrentPublicRoundId(currentRoundId);
		conditionalObjection.setUnitStatus(UnitObjectStatus.SUBMIT.getStatus());
		return originalObjectionService.downloadSubmitObjection(conditionalObjection, realPath, UserType.DISC.getStatus());
	}

	@Override
	public boolean deleteObjection(
			OriginalObjectionService originalObjectionService,
			String objectionId) {
		// TODO Auto-generated method stub
		return originalObjectionService.departmentNotPassObjection(objectionId);
	}

	@Override
	protected void setExistedObjection(OriginalObjection queryObjection) {
		// TODO Auto-generated method stub
		queryObjection.setObjectUnitId(this.unitId);
		queryObjection.setProblemDiscId(this.discId);
	}

	@Override
	public String downloadProcessObjection(String currentRoundId,
			String realPath, OriginalObjectionService originalObjectionService) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		OriginalObjection conditionalObjection = new OriginalObjection();
		conditionalObjection.setObjectUnitId(this.unitId);
		conditionalObjection.setProblemDiscId(this.discId);
		/*conditionalObjection.setStatus(ObjectStatus.PROCESSED.getStatus());*/
		conditionalObjection.setCurrentPublicRoundId(currentRoundId);
		return originalObjectionService.downloadSubmitObjection(conditionalObjection, realPath,UserType.DISC.getStatus());
	}

	@Override
	public Map<String, String> getJoinUnitMap(UnitService unitService) {
		// TODO Auto-generated method stub
		return unitService.getJoinUnitMapByDiscId(this.discId);
	}

	@Override
	public int updateObjectionRowData(OriginalObjectionVM objection,
			OriginalObjectionService originalObjectionService) {
		// TODO Auto-generated method stub
		String objectionId = objection.getObjectionId();
		String unitObjectContent = objection.getUnitObjectContent();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("unitObjectContent", unitObjectContent);
		/*statusMap.put(ObjectStatus.UNITNOTPASS.getStatus(), "已删除");*/
		return originalObjectionService.updateObjectionRowData(objectionId, data);
	}




}
