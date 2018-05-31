package com.dsep.service.publicity.process.production.publicity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dsep.entity.dsepmeta.OriginalObjection;
import com.dsep.entity.dsepmeta.PublicityManagement;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;
import com.dsep.service.publicity.objection.OriginalObjectionService;
import com.dsep.util.DateProcess;
import com.dsep.util.FileOperate;
import com.dsep.vm.JqgridVM;

public class DiscPublicityProcess extends PublicityProcess{

	private String userDisciplineId;
	private String userUnitId;
	

	public String getUserDisciplineId() {
		return userDisciplineId;
	}

	public void setUserDisciplineId(String userDisciplineId) {
		this.userDisciplineId = userDisciplineId;
	}

	public String getUserUnitId() {
		return userUnitId;
	}

	public void setUserUnitId(String userUnitId) {
		this.userUnitId = userUnitId;
	}
	
	public DiscPublicityProcess(String unitId,String discId){
		setUserDisciplineId(discId);
		setUserUnitId(unitId);
	}

	@Override
	public Map<String,String> changeDiscipline(String discId,UnitService unitService) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String,String> changeUnit(String unitId,DisciplineService discService) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getDiscMap(DisciplineService discService) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getUnitMap(UnitService unitService) {
		// TODO Auto-generated method stub
		return unitService.getJoinUnitMapByDiscId(this.userDisciplineId);
	}

	@Override
	public String uploadFile(MultipartFile file) {
		// TODO Auto-generated method stub
		String userString = this.userUnitId + "_" + this.userDisciplineId + "_" + DateProcess.getShowingTime(new Date());
		return FileOperate.uploadFile(file, userString);
	}

	@Override
	protected void setOriginalObjection(OriginalObjection originalObjection) {
		// TODO Auto-generated method stub
		originalObjection.setObjectUnitId(this.userUnitId);
		originalObjection.setProblemDiscId(this.userDisciplineId);
	}


}
