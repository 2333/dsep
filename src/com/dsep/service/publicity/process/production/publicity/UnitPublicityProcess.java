package com.dsep.service.publicity.process.production.publicity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import com.dsep.entity.dsepmeta.OriginalObjection;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;
import com.dsep.service.publicity.objection.OriginalObjectionService;
import com.dsep.util.DateProcess;
import com.dsep.util.FileOperate;
import com.dsep.util.JsonConvertor;
import com.dsep.vm.JqgridVM;

@Controller
public class UnitPublicityProcess extends PublicityProcess{

	private String userUnitId;
	
	public String getUserUnitId() {
		return userUnitId;
	}

	public void setUserUnitId(String userUnitId) {
		this.userUnitId = userUnitId;
	}
	
	
	public UnitPublicityProcess(String userUnitId){
		this.userUnitId = userUnitId;
	}
	
	public Map<String,String> getDiscMap(DisciplineService discService){
		return discService.getJoinDisciplineMapByUnitId(userUnitId);
	}
	
	public Map<String,String> getUnitMap(UnitService unitService){
		return unitService.getAllEvalUnitMap();
	}
	
	@Override
	public Map<String,String> changeDiscipline(String discId,UnitService unitService) {
		// TODO Auto-generated method stub
		Map<String,String> unitMap = unitService.getJoinUnitMapByDiscId(discId);
		return unitMap;
		/*return JsonConvertor.mapJSON(unitMap);*/
	}


	@Override
	public Map<String,String> changeUnit(String unitId,DisciplineService discService) {
		// TODO Auto-generated method stub
		Map<String,String> discMap = discService.getBothDisciplineMap(this.userUnitId, unitId);
		return discMap;
		/*return JsonConvertor.mapJSON(discMap);*/	
	}

	@Override
	public String uploadFile(MultipartFile file) {
		// TODO Auto-generated method stub
		String userString = this.userUnitId + "_" + DateProcess.getShowingTime(new Date());
		return FileOperate.uploadFile(file, userString);
	}

	@Override
	protected void setOriginalObjection(OriginalObjection originalObjection) {
		// TODO Auto-generated method stub
		originalObjection.setObjectUnitId(this.userUnitId);
	}

	

}
