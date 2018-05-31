package com.dsep.service.publicity.process.production.objection;


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

public class UnitObjectionProcess extends ObjectionProcess{
	private String unitId;
	
	public UnitObjectionProcess(String unitId){
		this.unitId = unitId;
	}

	@Override
	public PageVM<OriginalObjectionVM> getOriginalObjection(OriginalObjection queryObjection,
			boolean order_flag,String orderName,int pageIndex, int pageSize,
			String status, OriginalObjectionService originalObjectionService) {
		// TODO Auto-generated method stub
		queryObjection.setObjectUnitId(this.unitId);
		queryObjection.setUnitStatus(status);
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
		Map<String,String> statusMap = new LinkedMap();
		statusMap.put(UnitObjectStatus.NOTSUBMIT.getStatus(),"未提交");
		statusMap.put(UnitObjectStatus.SUBMIT.getStatus(), "已提交");
		/*statusMap.put(ObjectStatus.UNITNOTPASS.getStatus(), "已删除");*/
		return statusMap;
	}

	@Override
	public Map<String, String> getDisciplineMap(DisciplineService disciplineService) {
		// TODO Auto-generated method stub
		return disciplineService.getJoinDisciplineMapByUnitId(this.unitId);
	}

	@Override
	public String downloadSubmitObjection(String currentRoundId,
			String realPath, OriginalObjectionService originalObjectionService) {
		// TODO Auto-generated method stub
		OriginalObjection conditionalObjection = new OriginalObjection();
		conditionalObjection.setObjectUnitId(this.unitId);
		/*conditionalObjection.setStatus(ObjectStatus.SUBMIT.getStatus());*/
		conditionalObjection.setCurrentPublicRoundId(currentRoundId);
		conditionalObjection.setUnitStatus(UnitObjectStatus.SUBMIT.getStatus());
		return originalObjectionService.downloadSubmitObjection(conditionalObjection, realPath, UserType.UNIT.getStatus());
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
	}

	@Override
	public String downloadProcessObjection(String currentRoundId,
			String realPath, OriginalObjectionService originalObjectionService) {
		// TODO Auto-generated method stub
		OriginalObjection conditionalObjection = new OriginalObjection();
		conditionalObjection.setObjectUnitId(this.unitId);
		/*conditionalObjection.setStatus(ObjectStatus.PROCESSED.getStatus());*/
		conditionalObjection.setCurrentPublicRoundId(currentRoundId);
		conditionalObjection.setUnitStatus(UnitObjectStatus.SUBMIT.getStatus());
		return originalObjectionService.downloadSubmitObjection(conditionalObjection, realPath,UserType.UNIT.getStatus());
	}

	@Override
	public Map<String, String> getJoinUnitMap(UnitService unitService) {
		// TODO Auto-generated method stub
		return unitService.getAllEvalUnitMap();
	}

	@Override
	public int updateObjectionRowData(OriginalObjectionVM objection,
			OriginalObjectionService originalObjectionService) {
		// TODO Auto-generated method stub
		String objectionId = objection.getObjectionId();
		String unitObjectContent = objection.getUnitObjectContent();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("unitObjectContent", unitObjectContent);
		return originalObjectionService.updateObjectionRowData(objectionId, data);
	}



	
}
