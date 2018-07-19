package com.dsep.service.expert.select.util;

import java.lang.reflect.Field;
import java.util.List;

import com.dsep.entity.expert.ExpertSelectionRuleDetail;

public class UserConfigWeightInjectionValve {

	public static void call(Object obj,
			List<ExpertSelectionRuleDetail> expertSelectionRuleDetails) {
		Field[] fields = obj.getClass().getDeclaredFields();
		// 将private属性设置为可访问
		setFieldsAccessible(fields);
		boolean notSetDisciplineNumber = false;
		boolean needSetManageExpertPCT  = false; 
		try {
			for (ExpertSelectionRuleDetail detail : expertSelectionRuleDetails) {
				String detailName = detail.getItemName();
				// 前台是否勾选211学校
				if (detailName.equals("211Expert")) {
					if (null == detail.getConditionChecked()
							|| !detail.getConditionChecked()) {
						// 将is211Weight属性设置为0
						getFieldByName(fields, "is211Weight").set(obj, 0);

					}
				}
				// 前台是否勾选博士生导师
				else if (detailName.equals("doctoralSupervisor")) {
					if (null == detail.getConditionChecked()
							|| !detail.getConditionChecked()) {
						getFieldByName(fields, "docSuperWeight").set(obj, 0);
					}
				}
				// 前台是否勾选硕士生导师
				else if (detailName.equals("masterSupervisor")) {
					if (null == detail.getConditionChecked()
							|| !detail.getConditionChecked()) {
						getFieldByName(fields, "masSuperWeight").set(obj, 0);
					}
				}
				// 前台是否勾选研究生院
				else if (detailName.equals("graduateSchoolExpert")) {
					if (null == detail.getConditionChecked()
							|| !detail.getConditionChecked()) {
						getFieldByName(fields, "isGraduateWeight").set(obj, 0);
					}
				}
				// 前台是否勾选985学校
				else if (detailName.equals("985Expert")) {
					if (null == detail.getConditionChecked()
							|| !detail.getConditionChecked()) {
						getFieldByName(fields, "is985Weight").set(obj, 0);
					}
				}
				// 前台是否勾选不设置管理专家
				else if (detailName.equals("notSetManageExpertPCT")) {
					// 表明设置
					if (null == detail.getConditionChecked()
							|| !detail.getConditionChecked()) {
						getFieldByName(fields, "needChooseManageExpert").set(
								obj, true);
						needSetManageExpertPCT = true;
					}
				}
				
				// 前台是否勾选不设置
				else if (detailName.equals("notSetDisciplineNumber")) {
					// 表明设置
					if (null == detail.getConditionChecked()
							|| !detail.getConditionChecked()) {
						getFieldByName(fields, "needSetDisciplineNumber").set(
								obj, true);
					}
				}

				// 设定遴选的学校范围
				else if (detailName.equals("onlyAttend")) {
					if (detail.getConditionChecked() != null
							&& detail.getConditionChecked()) {
						getFieldByName(fields, "chooseFromOnlyAttend").set(obj,
								true);
						getFieldByName(fields, "chooseFromAttendAndAccredit")
								.set(obj, false);
						getFieldByName(fields, "chooseFromNotAttend").set(obj,
								false);
						getFieldByName(fields, "chooseFromAllUnits").set(obj,
								false);
					}
				} else if (detailName.equals("attendAndAccredit")) {
					if (detail.getConditionChecked() != null
							&& detail.getConditionChecked()) {
						getFieldByName(fields, "chooseFromOnlyAttend").set(obj,
								false);
						getFieldByName(fields, "chooseFromAttendAndAccredit")
								.set(obj, true);
						getFieldByName(fields, "chooseFromNotAttend").set(obj,
								false);
						getFieldByName(fields, "chooseFromAllUnits").set(obj,
								false);
					}
				} else if (detailName.equals("notAttend")) {
					if (detail.getConditionChecked() != null
							&& detail.getConditionChecked()) {
						getFieldByName(fields, "chooseFromOnlyAttend").set(obj,
								false);
						getFieldByName(fields, "chooseFromAttendAndAccredit")
								.set(obj, false);
						getFieldByName(fields, "chooseFromNotAttend").set(obj,
								true);
						getFieldByName(fields, "chooseFromAllUnits").set(obj,
								false);
					}
				} else if (detail.getConditionChecked() != null
						&& detailName.equals("allUnits")) {
					if (detail.getConditionChecked()) {
						getFieldByName(fields, "chooseFromOnlyAttend").set(obj,
								false);
						getFieldByName(fields, "chooseFromAttendAndAccredit")
								.set(obj, false);
						getFieldByName(fields, "chooseFromNotAttend").set(obj,
								false);
						getFieldByName(fields, "chooseFromAllUnits").set(obj,
								true);
					}
				}

				// 前台管理专家百分比，要与上一个“是否设置管理专家”一起使用
				else if (detailName.equals("manageExpertPCT")) {
					if (needSetManageExpertPCT) {
						getFieldByName(fields, "manageExpertPercent").set(obj,
								Double.valueOf(detail.getRestrict1()) / 100);
					} 
				} else if (detailName.equals("age")) {
					getFieldByName(fields, "ageLowerLimit").set(obj,
							Integer.valueOf(detail.getRestrict1()));
					getFieldByName(fields, "ageUpperLimit").set(obj,
							Integer.valueOf(detail.getRestrict2()));
				} else if (detailName.equals("sameDisciplineSumLimit")) {
					getFieldByName(fields, "sameDisciplineSumLowerLimit").set(
							obj, Integer.valueOf(detail.getRestrict1()));
					getFieldByName(fields, "sameDisciplineSumUpperLimit").set(
							obj, Integer.valueOf(detail.getRestrict2()));
				} else if (detailName.equals("sameUnitSumLimit")) {
					getFieldByName(fields, "sameUnitSumLowerLimit").set(obj,
							Integer.valueOf(detail.getRestrict1()));
					getFieldByName(fields, "sameUnitSumUpperLimit").set(obj,
							Integer.valueOf(detail.getRestrict2()));
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private static void setFieldsAccessible(Field[] fields) {
		for (Field f : fields) {
			f.setAccessible(true);
		}
	}

	private static Field getFieldByName(Field[] fields, String name) {
		for (Field f : fields) {
			if (f.getName().equals(name)) {
				return f;
			}
		}
		return null;
	}
}
