package com.dsep.service.publicity.process.production.publicity;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dsep.entity.dsepmeta.OriginalObjection;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;
import com.dsep.service.publicity.objection.OriginalObjectionService;
import com.dsep.util.JsonConvertor;
import com.dsep.vm.JqgridVM;

public class CenterPublicityProcess extends PublicityProcess{
	


	@Override
	public Map<String,String> changeDiscipline(String discId,UnitService unitService) {
		// TODO Auto-generated method stub
		Map<String,String> unitMap = unitService.getJoinUnitMapByDiscId(discId);
		return unitMap;
		/*return JsonConvertor.obj2JSON(unitMap);*/
	}

	@Override
	public Map<String,String> changeUnit(String unitId,DisciplineService discService) {
		// TODO Auto-generated method stub
		Map<String,String> discMap = discService.getJoinDisciplineMapByUnitId(unitId);
		return discMap;
		/*return JsonConvertor.obj2JSON(discMap);*/
	}

	@Override
	public Map<String, String> getDiscMap(DisciplineService discService) {
		// TODO Auto-generated method stub
		return discService.getAllEvalDiscMap();
	}

	@Override
	public Map<String, String> getUnitMap(UnitService unitService) {
		// TODO Auto-generated method stub
		return unitService.getAllEvalUnitMap();
	}

	@Override
	public String uploadFile(MultipartFile file) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	protected void setOriginalObjection(OriginalObjection originalObjection) {
		// TODO Auto-generated method stub
	}

}
