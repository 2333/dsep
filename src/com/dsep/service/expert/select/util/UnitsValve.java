package com.dsep.service.expert.select.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dsep.domain.dsepmeta.expert.DiscAndUnits;
import com.dsep.entity.Discipline;
import com.dsep.entity.Unit;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;
import com.dsep.service.expert.select.impl.TestData;

/**
 * 通过判断前台学校参评的配置，设定学校范围
 * 通过前台国家重点学科/211学科等的配置，排序学校的优先级
 */
public class UnitsValve {

	public static String[] call(boolean chooseFromAllUnits,
			boolean chooseFromAttendAndAccredit, boolean chooseFromNotAttend,
			boolean chooseFromOnlyAttend, String currentDisc,
			UnitService unitService, DisciplineService disciplineService) {
		List<DiscAndUnits> discAndUnitsList = null;
		if (chooseFromAllUnits) {
			discAndUnitsList = getDiscAndAllUnits(unitService,
					disciplineService);
		} else if (chooseFromAttendAndAccredit) {
			discAndUnitsList = getImitateDiscAndAttendAndAccreditUnits(
					unitService, disciplineService);
		} else if (chooseFromNotAttend) {
			discAndUnitsList = getImitateDiscAndNotAttendUnits(unitService);
		} else if (chooseFromOnlyAttend) {
			discAndUnitsList = getImitateDiscAndOnlyAttendUnits(unitService,
					disciplineService);
		}

		List<String> units = null;
		// 获得currentDisc的所有学校
		for (DiscAndUnits ele : discAndUnitsList) {
			if (ele.getDisc().equals(currentDisc)) {
				units = ele.getUnits();
				break;
			}
		}

		// 边界处理：如果该学科下没有学校，直接返回null
		if (null == units)
			return null;

		return sortUnits(units.toArray(new String[units.size()]), currentDisc);
	}

	private static String[] sortUnits(String[] units, String currentDisc) {
		Integer[] weights = new Integer[units.length];
		for (int i = 0; i < units.length; i++) {
			weights[i] = 0;
		}

		for (int i = 0; i < units.length; i++) {
			String unit = units[i];
			if (is211(unit))
				weights[i] += 1;
			if (is985(unit))
				weights[i] += 1;
			if (isKeyDis(unit, currentDisc))
				weights[i] += 1;
			if (isDocDis(unit, currentDisc))
				weights[i] += 1;
		}
		String[] sortedUnits = new String[units.length];

		for (int times = 0; times < weights.length; times++) {
			int max = -1;
			int pointer = -1;
			for (int i = 0; i < weights.length; i++) {
				if (weights[i] > max) {
					max = weights[i];
					pointer = i;
				}
			}
			weights[pointer] = -1;
			sortedUnits[times] = units[pointer];
		}

		return sortedUnits;
	}

	private static boolean is211(String unit) {
		// 改二分
		for (String _211Unit : TestData._211Units) {
			if (_211Unit.equals(unit))
				return true;
		}
		return false;
	}

	private static boolean is985(String unit) {
		// 改二分
		for (String _985Unit : TestData._985Units) {
			if (_985Unit.equals(unit))
				return true;
		}
		return false;

	}

	private static boolean isKeyDis(String unit, String currentDisc) {
		String[] units = TestData.keyDis.get(currentDisc);
		if (null == units)
			return false;
		for (String u : units) {
			if (u.equals(unit))
				return true;
		}
		return false;
	}

	private static boolean isDocDis(String unit, String currentDisc) {
		String[] units = TestData.docDis.get(currentDisc);
		if (null == units)
			return false;
		for (String u : units) {
			if (u.equals(unit))
				return true;
		}
		return false;
	}

	private static List<DiscAndUnits> getDiscAndAllUnits(
			UnitService unitService, DisciplineService disciplineService) {
		// 模拟
		List<DiscAndUnits> discAndUnitsList = new ArrayList<DiscAndUnits>();
		List<String> imitate_units = new ArrayList<String>();
		List<Unit> units = unitService.getAllUnits();
		for (Unit unit : units) {
			imitate_units.add(unit.getId());
		}
		// 浙江大学
		//imitate_units.add("10335");
		List<Discipline> discs = disciplineService.getAllDisciplines();
		for (Discipline disc : discs) {
			discAndUnitsList.add(new DiscAndUnits(disc.getId(), imitate_units));
		}

		// 模拟结束
		return discAndUnitsList;
	}

	private static List<DiscAndUnits> getImitateDiscAndUnits() {
		// 模拟
		List<DiscAndUnits> discAndUnitsList = new ArrayList<DiscAndUnits>();

		Map<String, List<String>> discipline_units = new LinkedHashMap<String, List<String>>();
		List<String> imitate_units = new ArrayList<String>();
		// 要确保有序
		imitate_units.add("10001");
		imitate_units.add("10002");
		imitate_units.add("10003");
		imitate_units.add("10004");
		imitate_units.add("10005");
		imitate_units.add("10006");
		imitate_units.add("10007");
		imitate_units.add("10008");
		imitate_units.add("10010");
		imitate_units.add("10012");
		imitate_units.add("10013");
		imitate_units.add("10015");
		imitate_units.add("10016");
		imitate_units.add("10017");
		imitate_units.add("10018");
		imitate_units.add("10019");
		imitate_units.add("10020");
		imitate_units.add("10022");
		imitate_units.add("10023");
		imitate_units.add("10025");
		imitate_units.add("10027");
		imitate_units.add("10028");
		imitate_units.add("10029");
		imitate_units.add("10030");
		imitate_units.add("10031");
		imitate_units.add("10032");
		imitate_units.add("10033");
		imitate_units.add("10052");
		imitate_units.add("10056");
		imitate_units.add("10057");
		imitate_units.add("10108");
		imitate_units.add("10129");
		imitate_units.add("10135");
		imitate_units.add("10140");
		imitate_units.add("10145");
		imitate_units.add("10158");
		imitate_units.add("10167");
		imitate_units.add("10175");
		imitate_units.add("10183");
		imitate_units.add("10190");
		imitate_units.add("10192");
		imitate_units.add("10193");
		imitate_units.add("10200");
		imitate_units.add("10201");
		imitate_units.add("10212");
		imitate_units.add("10213");
		imitate_units.add("10217");
		imitate_units.add("10225");
		imitate_units.add("10246");
		imitate_units.add("10247");
		imitate_units.add("10248");
		imitate_units.add("10269");
		imitate_units.add("10271");
		imitate_units.add("10284");
		imitate_units.add("10285");
		imitate_units.add("10286");
		imitate_units.add("10287");
		imitate_units.add("10288");
		imitate_units.add("10290");
		imitate_units.add("10291");
		imitate_units.add("10295");
		imitate_units.add("10298");
		imitate_units.add("10299");
		imitate_units.add("10341");
		imitate_units.add("10364");
		imitate_units.add("10389");
		imitate_units.add("10431");
		imitate_units.add("10517");
		imitate_units.add("10531");
		imitate_units.add("10538");
		imitate_units.add("10593");
		imitate_units.add("10626");
		imitate_units.add("10677");
		imitate_units.add("10712");
		imitate_units.add("11059");
		imitate_units.add("82201");
		imitate_units.add("90001");
		imitate_units.add("90002");
		imitate_units.add("90003");
		imitate_units.add("90009");
		List<String> imitate_units2 = new ArrayList<String>();
		imitate_units2.add("10002");
		imitate_units2.add("10003");
		imitate_units2.add("10006");
		imitate_units2.add("10008");
		imitate_units2.add("10011");
		imitate_units2.add("10013");
		imitate_units2.add("10019");
		imitate_units2.add("10022");
		imitate_units2.add("10027");
		imitate_units2.add("10028");
		imitate_units2.add("10032");
		imitate_units2.add("10033");
		imitate_units2.add("10052");
		imitate_units2.add("10055");
		imitate_units2.add("10056");
		imitate_units2.add("10057");
		imitate_units2.add("10060");
		imitate_units2.add("10065");
		imitate_units2.add("10066");
		imitate_units2.add("10082");
		imitate_units2.add("10094");
		imitate_units2.add("10118");
		imitate_units2.add("10126");

		discAndUnitsList.add(new DiscAndUnits("0101", imitate_units));
		discAndUnitsList.add(new DiscAndUnits("0501", imitate_units));
		discAndUnitsList.add(new DiscAndUnits("0705", imitate_units));
		discAndUnitsList.add(new DiscAndUnits("0809", imitate_units2));
		discAndUnitsList.add(new DiscAndUnits("0812", imitate_units2));
		discAndUnitsList.add(new DiscAndUnits("0825", imitate_units2));
		discAndUnitsList.add(new DiscAndUnits("0829", imitate_units));
		// 模拟结束
		return discAndUnitsList;
	}

	private static List<DiscAndUnits> getImitateDiscAndOnlyAttendUnits(
			UnitService unitService, DisciplineService disciplineService) {
		// 模拟
		List<DiscAndUnits> discAndUnitsList = new ArrayList<DiscAndUnits>();
		List<String> imitate_units = new ArrayList<String>();
		List<String> units = unitService.getAllEvalUnitIds();
		for (String unit : units) {
			imitate_units.add(unit);
		}
		// 浙江大学
		//imitate_units.add("10335");
		for (String unit : units) {
			List<String> discs = disciplineService
					.getJoinDisciplineByUnitId(unit);
			for (String disc : discs) {
				discAndUnitsList.add(new DiscAndUnits(disc, imitate_units));
			}
		}

		// 模拟结束
		return discAndUnitsList;
	}

	private static List<DiscAndUnits> getImitateDiscAndAttendAndAccreditUnits(
			UnitService unitService, DisciplineService disciplineService) {
		// 模拟
		List<DiscAndUnits> discAndUnitsList = new ArrayList<DiscAndUnits>();
		List<String> imitate_units = new ArrayList<String>();
		List<String> units = unitService.getAllEvalUnitIds();
		for (String unit : units) {
			imitate_units.add(unit);
		}
		// 浙江大学
		//imitate_units.add("10335");
		for (String unit : units) {
			List<String> discs = disciplineService
					.getJoinDisciplineByUnitId(unit);
			for (String disc : discs) {
				discAndUnitsList.add(new DiscAndUnits(disc, imitate_units));
			}
		}
		// 模拟结束
		return discAndUnitsList;
	}

	private static List<DiscAndUnits> getImitateDiscAndNotAttendUnits(
			UnitService unitService) {
		// 模拟
		List<DiscAndUnits> discAndUnitsList = new ArrayList<DiscAndUnits>();
		List<String> imitate_units = new ArrayList<String>();
		String[] units = TestData.notAttendUnits;
		for (String unit : units) {
			imitate_units.add(unit);
		}
		discAndUnitsList.add(new DiscAndUnits("0101", imitate_units));
		discAndUnitsList.add(new DiscAndUnits("0501", imitate_units));
		discAndUnitsList.add(new DiscAndUnits("0705", imitate_units));
		discAndUnitsList.add(new DiscAndUnits("0809", imitate_units));
		discAndUnitsList.add(new DiscAndUnits("0812", imitate_units));
		discAndUnitsList.add(new DiscAndUnits("0825", imitate_units));
		discAndUnitsList.add(new DiscAndUnits("0829", imitate_units));
		// 模拟结束
		return discAndUnitsList;
	}

}
