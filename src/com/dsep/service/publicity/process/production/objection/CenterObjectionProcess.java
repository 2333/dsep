package com.dsep.service.publicity.process.production.objection;


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;

import com.dsep.entity.dsepmeta.OriginalObjection;
import com.dsep.entity.enumeration.publicity.CenterObjectStatus;
import com.dsep.entity.enumeration.rbac.UserType;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;
import com.dsep.service.publicity.objection.OriginalObjectionService;
import com.dsep.vm.PageVM;
import com.dsep.vm.publicity.OriginalObjectionVM;

public class CenterObjectionProcess extends ObjectionProcess{

	@Override
	public PageVM<OriginalObjectionVM> getOriginalObjection(OriginalObjection queryObjection,
			boolean order_flag,String orderName,int pageIndex, int pageSize,
			String status, OriginalObjectionService originalObjectionService) {
		// TODO Auto-generated method stub
		try {
			queryObjection.setCenterStatus(status);
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
		statusMap.put(CenterObjectStatus.UNITSUBMIT.getStatus(), "未处理");
		statusMap.put(CenterObjectStatus.PROCESSED.getStatus(), "已处理");
		return statusMap;
	}

	@Override
	public Map<String, String> getDisciplineMap(DisciplineService disciplineService) {
		// TODO Auto-generated method stub
		return disciplineService.getAllEvalDiscMap();
	}

	@Override
	public String downloadSubmitObjection(String currentRoundId,
			String realPath, OriginalObjectionService originalObjectionService) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteObjection(
			OriginalObjectionService originalObjectionService,
			String objectionId) {
		// TODO Auto-generated method stub
		return originalObjectionService.centerNotPassObjection(objectionId);
	}

	@Override
	protected void setExistedObjection(OriginalObjection queryObjection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String downloadProcessObjection(String currentRoundId,
			String realPath, OriginalObjectionService originalObjectionService) {
		// TODO Auto-generated method stub
		OriginalObjection conditionalObjection = new OriginalObjection();
		/*conditionalObjection.setStatus(ObjectStatus.PROCESSED.getStatus());*/
		conditionalObjection.setCurrentPublicRoundId(currentRoundId);
		conditionalObjection.setCenterStatus(CenterObjectStatus.PROCESSED.getStatus());
		return originalObjectionService.downloadSubmitObjection(conditionalObjection, realPath, UserType.CENTER.getStatus());
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
		String centerObjectContent = objection.getCenterObjectContent();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("centerObjectContent", centerObjectContent);
		/*statusMap.put(ObjectStatus.UNITNOTPASS.getStatus(), "已删除");*/
		return originalObjectionService.updateObjectionRowData(objectionId, data);
	}

}
