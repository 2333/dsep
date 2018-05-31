package com.dsep.vm;

import java.util.Map;

import com.dsep.entity.dsepmeta.LogicCheckEntityResult;
import com.dsep.util.Dictionaries;


public class LogicCheckVM {
	public String passInfo;
	public String entityChsName;
	public String disciplineChsName;
	public String unitChsName;
	public String entityId;
	public String discId;
	public String unitId;


	public LogicCheckVM(Map<String, String> data) {
		
		// 此处应该用缓存拿到中文名，现在先存代号 
		setDisciplineChsName(Dictionaries.getDisciplineName(data.get("DISC_ID")));
		setUnitChsName(Dictionaries.getUnitName(data.get("UNIT_ID")));
		
		setDiscId(data.get("DISC_ID"));
		setUnitId(data.get("UNIT_ID"));
		
		// 关于hasError和hasWarn字段
		// 详见com.dsep.dao.dsepmeta.check.impl.LogicCheckEntityResultDaoImpl
		// 中的getDisciplineLogicCheckResultForUnitUser方法
		setPassInfo(data.get("hasError"), data.get("hasWarn"));
	}
	
	
	public LogicCheckVM(LogicCheckEntityResult entityResult) {
		setEntityChsName(entityResult.getEntityChsName());
		setPassInfo(entityResult.getHasError(), entityResult.getHasWarn());
		setEntityId(entityResult.getEntityId());
		setUnitId(entityResult.getUnitId());
		setDiscId(entityResult.getDiscId());
	}


	public String getPassInfo() {
		return passInfo;
	}

	// hasError.equals("1") means has error
	public void setPassInfo(String hasError, String hasWarn) {
		if (hasError.equals("1") && hasWarn.equals("1")) {
			this.passInfo = "错误、警告";
		} else if (!hasError.equals("1") && hasWarn.equals("1")) {
			this.passInfo = "警告";
		} else if (hasError.equals("1") && !hasWarn.equals("1")) {
			this.passInfo = "错误";
		} else if (!hasError.equals("1") && !hasWarn.equals("1")) {
			this.passInfo = "通过";
		}
	}


	public void setPassInfo(Boolean hasError, Boolean hasWarn) {
		
		if (hasError && hasWarn) {
			this.passInfo = "错误、警告";
		} else if (!hasError && hasWarn) {
			this.passInfo = "警告";
		} else if (hasError && !hasWarn) {
			this.passInfo = "错误";
		} else if (!hasError && !hasWarn) {
			this.passInfo = "通过";
		}
	}



	public String getEntityChsName() {
		return entityChsName;
	}

	public void setEntityChsName(String entityChsName) {
		this.entityChsName = entityChsName;
	}

	public String getDisciplineChsName() {
		return disciplineChsName;
	}

	public void setDisciplineChsName(String disciplineChsName) {
		this.disciplineChsName = disciplineChsName;
	}

	public String getUnitChsName() {
		return unitChsName;
	}

	public void setUnitChsName(String unitChsName) {
		this.unitChsName = unitChsName;
	}


	public String getEntityId() {
		return entityId;
	}


	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}


	public String getDiscId() {
		return discId;
	}


	public void setDiscId(String discId) {
		this.discId = discId;
	}


	public String getUnitId() {
		return unitId;
	}


	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	
//	
//	public static List<Map<String, String>> changePassInfo(
//			List<Map<String, String>> dataList) {
//		for (Map<String, String> data : dataList) {
//			if (data.get("PASSED").equals("0")) {
//				data.put("PASSED", "通过");
//			} else if (data.get("PASSED").equals("1")) {
//				data.put("PASSED", "错误");
//			} else if (data.get("PASSED").equals("2")) {
//				data.put("PASSED", "警告");
//			} else if (data.get("PASSED").equals("3")) {
//				data.put("PASSED", "未检查");
//			} else {
//				data.put("PASSED", "未检查");
//			}
//		}
//		return dataList;
//	}
}