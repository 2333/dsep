package com.dsep.domain.dsepmeta.publicity.process;

import org.springframework.beans.factory.annotation.Autowired;

import com.dsep.service.base.UnitService;

public class ProcessTest {
	
	@Autowired
	private UnitService unitService;
	
	public void testUnit(){
		unitService.getAllUnits();
	}
	
}
