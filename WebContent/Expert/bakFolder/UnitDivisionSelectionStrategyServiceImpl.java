package com.dsep.service.expert.select.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

import com.dsep.domain.dsepmeta.expert.DiscAndUnits;
import com.dsep.domain.dsepmeta.expert.ExpertInfoFromOtherDB;
import com.dsep.entity.dsepmeta.EvalBatch;
import com.dsep.entity.dsepmeta.ExpertSelected;
import com.dsep.entity.dsepmeta.ExpertSelectionRuleDetail;
import com.dsep.service.expert.rule.RuleMetaService;
import com.dsep.service.expert.select.UnitDivisionSelectionStrategyService;
import com.dsep.service.expert.select.util.ExpertsFromOtherDB2TreeConvertorValve;
import com.dsep.service.expert.select.util.SelectionRuleWeightInjectionValve;
import com.dsep.util.expert.ExpertEvalCurrentStatus;
import com.dsep.util.expert.email.MD5Util;

public class UnitDivisionSelectionStrategyServiceImpl implements
		UnitDivisionSelectionStrategyService {
	private RuleMetaService ruleMetaService;

	private int is211Weight = 0, 
				isGraduateWeight = 0, 
				is985Weight = 0,
				ageWeight = 0, 
				docSuperWeight = 0, 
				masSuperWeight = 0,
				ageLowerLimit = 0, 
				ageUpperLimit = 0,
				sameDisciplineSumLowerLimit = 0,
				sameDisciplineSumUpperLimit = 0,
				sameUnitSumLowerLimit = 0, 
				sameUnitSumUpperLimit = 0;

	private double manageExpertPercent = 0D;
	// 默认不区分管理专家与非管理专家
	private boolean needChooseManageExpert = false;

	// 选择范围,默认都是false
	private boolean chooseFromOnlyAttend = false,
					chooseFromAttendAndAccredit = false, 
					chooseFromNotAttend = false,
					chooseFromAllUnits = false;

	BufferedWriter buffWriter = null;
	Writer writer = null;

	// 全局变量,用来存放本次select方法调用遴选出的专家
	List<ExpertInfoFromOtherDB> selectedExpert = new ArrayList<ExpertInfoFromOtherDB>();

	/**
	 * A.对"在参评单位及未参评授权点中选（参评单位优先）"：
	 * 
	 * 遴选的流程如下
	 *   规定每组的专家上下限：disc_lower/disc_upper
	 *   规定同一学校选择的专家上下限：unit_lower/unit_upper
	 * 
	 * 步骤1. 在参评单位中，每个单位各选出unit_lower个专家
	 *   如果总数量∈[disc_lower..disc_upper]，结束;
	 *   如果总量小于下限disc_lower，步骤2
	 * 
	 * 步骤2. 继续在未参评授权单位中，每个单位各选出unit_lower个专家，加入总量
	 *   如果总数量∈[disc_lower..disc_upper]，结束;
	 *   如果总量仍小于下限disc_lower，步骤3
	 * 
	 * 步骤3. 继续在参评单位中，每个单位再各选出1个专家，
	 *   加入总量 如果总数量∈[disc_lower..disc_upper]，结束;
	 *   如果总量仍小于下限disc_lower，步骤4
	 * 
	 * 步骤4. 继续在未参评但被授权的单位中，每个单位再各选出1个专家，加入总量
	 *   无论总数量是否∈[disc_lower..disc_upper]，都结束;
	 *   不在[disc_lower..disc_upper]则给出警告
	 * 
	 * 
	 * 所以遴选代码实现思路：
	 * 
	 * 1.把专家按照学校分类
	 * 
	 * 2.对参评/参评、授权/所有学校(即专家学校范围)进行排序，排序条件包括
	 *   985高校、211高校、博士一级学科对应高校等
	 * 
	 * 3.遍历专家的List<List<E>>和经过排序的学校List，获得每个学校的专家list节点，
	 *   按照优先系数加权排列，选择出排名前几的专家
	 * 
	 * 3.如果流程未完，重复1~3的过程
	 * 
	 * B.对"仅在参评单位中选"：
	 * C.对"不在参评单位中选（授权点优先）"：
	 * D.对"在全部学位授予单位中选（参评单位、授权点优先）"：
	 */
	@Override
	public List<ExpertSelected> select(List<ExpertInfoFromOtherDB> experts,
			Map<String, java.util.List<String>> discsAndUnitsInfo,
			List<ExpertSelectionRuleDetail> ruleDetails, EvalBatch evalBatch,
			String currentDisc) {

		try {
			writer = new FileWriter(new File("E:/Test.txt"));
			buffWriter = new BufferedWriter(writer);//这个写完可以关闭  
		} catch (IOException e) {
			e.printStackTrace();
		}//writer不能关闭  

		// 通过反射给各个权重赋值
		parseDetailRestricts(ruleDetails);

		List<List<ExpertInfoFromOtherDB>> container = ExpertsFromOtherDB2TreeConvertorValve
				.call(experts);

		
		// 找到通过对比学校,找到树形结构相同的节点们(即相同学科下的相同学校们)
		// 对相同学校们进行遴选,其内部会调用选择方法
		findSpecificUnitNodes(container, currentDisc);

		return wrapExperts(evalBatch);
	};

	private List<DiscAndUnits> discAndUnitsList = null;
	private void getSelectionUnitRange() {
		
		if (chooseFromAllUnits) {
			discAndUnitsList = getImitateDiscAndAllUnitsUnits();
		} else if (chooseFromAttendAndAccredit) {
			discAndUnitsList = getImitateDiscAndAttendAndAccreditUnits();
		} else if (chooseFromNotAttend) {
			discAndUnitsList = getImitateDiscAndNotAttendUnits();
		} else if (chooseFromOnlyAttend) {
			discAndUnitsList = getImitateDiscAndOnlyAttendUnits();
		}
		// 异常处理，如果前台没有选择学校范围
		else {
			new Exception("没有指定遴选单位的范围！");
		}
	}
	
	private String[] sortUnits(String[] units, String currentDisc) {
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
			if (isGraduate(unit))
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

	private boolean is211(String unit) {
		// 改二分
		for (String _211Unit : TestData._211Units) {
			if (_211Unit.equals(unit))
				return true;
		}
		return false;
	}

	private boolean is985(String unit) {
		// 改二分
		for (String _985Unit : TestData._985Units) {
			if (_985Unit.equals(unit))
				return true;
		}
		return false;

	}
	
	private boolean isGraduate(String unit) {
		for (String graduateUnit : TestData.graduateUnits) {
			if (graduateUnit.equals(unit)) 
				return true;
		}
		return false;
	}

	private boolean isKeyDis(String unit, String currentDisc) {
		String[] units = TestData.keyDis.get(currentDisc);
		if (null == units)
			return false;
		for (String u : units) {
			if (u.equals(unit))
				return true;
		}
		return false;
	}

	private boolean isDocDis(String unit, String currentDisc) {
		String[] units = TestData.docDis.get(currentDisc);
		if (null == units)
			return false;
		for (String u : units) {
			if (u.equals(unit))
				return true;
		}
		return false;
	}
	

	public void findSpecificUnitNodes(
			List<List<ExpertInfoFromOtherDB>> container, String currentDisc) {
		// 现在有两个类树状的数据结构，分别是List<List<E>>和Map<String, List>类型
		// List<List<<E>>来源于某个学科的专家
		// Map<String, List>来源于参评的学科、学校
		// 两者都有一层节点表示所在单位
		// 现在需要找到两个数据结构中相同学科、相同学校的节点

		List<String> units = null;
		for (DiscAndUnits ele : discAndUnitsList) {
			if (ele.getDisc().equals(currentDisc)) {
				units = ele.getUnits();
				break;
			}
		}

		String[] unitsArr = sortUnits(units.toArray(new String[units.size()]),
				currentDisc);

		for (String unit : unitsArr) {
			Iterator<List<ExpertInfoFromOtherDB>> iter = container.iterator();
			while (iter.hasNext()) {
				List<ExpertInfoFromOtherDB> list = iter.next();
				if (list.get(0).getXXDM().equals(unit)) {
					selectedExpert.addAll(selectSpecificUnitExperts(list));
					break;
				}
			}
		}

	}

	private List<ExpertInfoFromOtherDB> selectSpecificUnitExperts(
			List<ExpertInfoFromOtherDB> list) {
		// doStep1设置为true是因为step1必定执行
		// doStep2~4设置为false是因为它们执行与否要看前一个step的结果
		List<ExpertInfoFromOtherDB> selectedExpert = new ArrayList<ExpertInfoFromOtherDB>();
		boolean doStep1 = true, doStep2 = false, doStep3 = false, doStep4 = false;
		// step1Experts保存每个学科的每个学校的一批专家
		List<ExpertInfoFromOtherDB> step1EveryUnitExperts = new ArrayList<ExpertInfoFromOtherDB>();
		// step1AllExperts保存每个学科的所有学校的专家
		List<ExpertInfoFromOtherDB> step1AllUnitsExperts = new ArrayList<ExpertInfoFromOtherDB>();
		// step1Sum保存所有学校的专家
		int step1Sum = 0;

		int sum = 0;
		// ================step1=================
		if (doStep1) {
			try {
				buffWriter.write("每个学校的下限是：" + sameUnitSumLowerLimit + "，上限是："
						+ sameUnitSumUpperLimit + "\r\n");
				buffWriter.write("学科为：" + list.get(0).getYJXKM() + "，学校："
						+ list.get(0).getXXDM() + "\r\n");
				buffWriter.write("进入：" + sameUnitSumLowerLimit + "\r\n");
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			// 首先进行步骤1的选择
			// 即(选择参评单位数)乘以(每个单位的专家数下限)

			step1EveryUnitExperts = choose(list, sameUnitSumLowerLimit);
			ExpertInfoFromOtherDB e = list.get(0);
			try {
				buffWriter.write("学科为：" + e.getYJXKM() + "的" + e.getXXDM()
						+ "学校，总共有专家" + list.size() + "人，step1遴选出"
						+ step1EveryUnitExperts.size() + "人\r\n\r\n\r\n");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			step1AllUnitsExperts.addAll(step1EveryUnitExperts);

			sum += step1EveryUnitExperts.size();
		}

		// 如果步骤1就已经把人都选出来了，那么直接结束这个学科的遴选
		// 如果选出来的人少了，那么继续步骤2
		// 如果选出来的人多了，那么随机选人
		if (sum >= sameDisciplineSumLowerLimit
				&& sum <= sameDisciplineSumUpperLimit) {
			selectedExpert.addAll(step1AllUnitsExperts);
			try {
				buffWriter.write("if1本学科循环结束\r\n\r\n\r\n");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return selectedExpert;
		} else if (sum < sameDisciplineSumLowerLimit) {
			selectedExpert.addAll(step1AllUnitsExperts);
			// 如果选出来的人少了，那么继续步骤2
			doStep2 = true;
			try {
				buffWriter.write("if2本学科循环结束\r\n\r\n\r\n");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			// 测试代码
			return selectedExpert;
		} else {
			step1AllUnitsExperts = random(step1AllUnitsExperts,
					step1AllUnitsExperts.size() - sameDisciplineSumUpperLimit);
			selectedExpert.addAll(step1AllUnitsExperts);

			try {
				buffWriter.write("if3本学科循环结束\r\n\r\n\r\n");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return selectedExpert;
		}
	}

	// 前提是有序！！
	/**
	 * 现在有两个类树状的数据结构，分别是List<List<List<E>>>和Map<String, List>类型
	 * List<List<List<E>>> 来源于20W条专家
	 * 
	 * container:
	 * {
	 *  [
	 *   (老师a_10006_0809, 老师b_10006_0809),-> 最内层List<E>一组相同学科,相同学校的不同专家
	 *   (老师d_10007_0809, 老师e_10007_0809, 老师f_10007_0809)
	 *  ],-> 中间层List<List<E>>一组相同学科,不同学校的不同专家
	 *  
	 *  [
	 *   (老师5_10006_0812, 老师6_10006_0812, 老师7_10006_0812),
	 *   (老师8_10007_0812, 老师9_10007_0812)
	 *  ]
	 *  
	 *	[
	 *	 (老师1_10006_0835, 老师2_10006_0835, 老师3_10006_0835),
	 *   (老师3_10007_0835, 老师4_10007_0835)
	 *  ]
	 *  
	 * }->最外层 一组不同学科，不同学校的不同专家
	 * 
	 * Map<String, List>来源于参评的学科、学校
	 * {
	 *  [0809:(10006, 10007, 10008)],
	 *  [0835:(10001, 10003, 10006)]
	 * }
	 * 两者都有一层节点表示所在单位，再上一层节点是单位对应的学科
	 * 两个类树状数据结构的单位和专家节点值都是从小到大排列的
	 * 现在需要找到两个数据结构中相同学科
	 * 
	 */
	private void findSpecificDiscNodes(Map<String, List<String>> m,
			List<List<List<ExpertInfoFromOtherDB>>> container) {
		// 模拟的参评学科和学校
		//Map<String, List<String>> discipline_units = getImitateDiscAndUnits();
		List<DiscAndUnits> discAndUnitsList = null;
		if (chooseFromAllUnits) {
			discAndUnitsList = getImitateDiscAndAllUnitsUnits();
		} else if (chooseFromAttendAndAccredit) {
			discAndUnitsList = getImitateDiscAndAttendAndAccreditUnits();
		} else if (chooseFromNotAttend) {
			discAndUnitsList = getImitateDiscAndNotAttendUnits();
		} else if (chooseFromOnlyAttend) {
			discAndUnitsList = getImitateDiscAndOnlyAttendUnits();
		}
		// 异常处理，如果前台没有选择学校范围
		else {
			try {
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//List<DiscAndUnits> discAndUnitsList = getImitateDiscAndUnits();

		// 现在有两个类树状的数据结构，分别是List<List<List<E>>>和Map<String, List>类型
		// List<List<List<E>>>来源于20W条专家
		// Map<String, List>来源于参评的学科、学校
		// 两者都有一层节点表示所在单位，再上一层节点是单位对应的学科
		// 两个类树状数据结构的单位和专家节点值都是从小到大排列的
		// 现在需要找到两个数据结构中相同学科、相同学校的节点
		Iterator<List<List<ExpertInfoFromOtherDB>>> conDisciplinesIter = container
				.iterator();
		/*Iterator<String> mDisciplinesIter = discipline_units.keySet()
				.iterator();*/

		List<String> discAndUnitsListKey = new ArrayList<String>();
		for (DiscAndUnits ele : discAndUnitsList) {
			discAndUnitsListKey.add(ele.getDisc());
		}
		Iterator<String> mDisciplinesIter = discAndUnitsListKey.iterator();
		// 以Map<String, List>来循环
		while (mDisciplinesIter.hasNext()) {
			String mDiscipline = mDisciplinesIter.next();
			List<List<ExpertInfoFromOtherDB>> conDiscipline = null;
			// List<List<List<E>>>也同步循环
			if (conDisciplinesIter.hasNext()) {
				conDiscipline = conDisciplinesIter.next();
			} else {
				break;
			}

			// 如果Map的学科小于List<List<List>>的学科，那么指向Map学科的节点往后走一个
			while (-1 == Integer.valueOf(mDiscipline).compareTo(
					Integer.valueOf(conDiscipline.get(0).get(0).getYJXKM()))
					&& mDisciplinesIter.hasNext()) {
				mDiscipline = mDisciplinesIter.next();
			}
			// 如果List<List<List>>的学科小于Map的学科，那么指向List<List<List>>学科的节点往后走一个
			while (1 == Integer.valueOf(mDiscipline).compareTo(
					Integer.valueOf(conDiscipline.get(0).get(0).getYJXKM()))
					&& conDisciplinesIter.hasNext()) {
				conDiscipline = conDisciplinesIter.next();
			}

			// 如果List<List<List>>的学科等于Map的学科，那么开始遍历学科对应的学校
			// == only works for numbers between -128 and 127 in JVM
			if (0 == Integer.valueOf(mDiscipline).compareTo(
					Integer.valueOf(conDiscipline.get(0).get(0).getYJXKM()))) {

				List<String> mUnits = null;
				DiscAndUnits targetDiscAndUnits = null;
				for (DiscAndUnits ele : discAndUnitsList) {
					targetDiscAndUnits = ele.getObjByDisc(mDiscipline);
					if (null != targetDiscAndUnits) {
						mUnits = targetDiscAndUnits.getUnits();
						break;
					}
				}

				// findAuAndAuUnitsInSpecificDiscipline(conDiscipline, mUnits);
				List<List<ExpertInfoFromOtherDB>> conUnits = conDiscipline;
				Iterator<List<ExpertInfoFromOtherDB>> conUnitsIter = conUnits
						.iterator();
				Iterator<String> mUnitsIter = mUnits.iterator();

				// 某一学科的所有参评单位下的专家
				List<List<ExpertInfoFromOtherDB>> expertsInAttendedUnit = new ArrayList<List<ExpertInfoFromOtherDB>>();
				// 某一学科的所有授权单位下的专家
				List<List<ExpertInfoFromOtherDB>> expertsInAuthorizedUnit = new ArrayList<List<ExpertInfoFromOtherDB>>();
				// 这和外层的循环思路一样
				while (mUnitsIter.hasNext()) {
					String mUnit = mUnitsIter.next();
					List<ExpertInfoFromOtherDB> conUnit = null;
					if (conUnitsIter.hasNext()) {
						conUnit = conUnitsIter.next();
					} else {
						break;
					}

					while (-1 == Integer.valueOf(mUnit).compareTo(
							Integer.valueOf(conUnit.get(0).getXXDM()))
							&& mUnitsIter.hasNext()) {
						mUnit = mUnitsIter.next();
					}
					while (1 == Integer.valueOf(mUnit).compareTo(
							Integer.valueOf(conUnit.get(0).getXXDM()))
							&& conUnitsIter.hasNext()) {
						conUnit = conUnitsIter.next();
					}
					// 如果学校也一样，那么找出这个节点下的所有专家，开始遴选
					if (0 == Integer.valueOf(mUnit).compareTo(
							Integer.valueOf(conUnit.get(0).getXXDM()))) {
						// 现在假设所有学校都是参评学校，但以后肯定要分参评和授权，只不过现在没在数据
						expertsInAttendedUnit.add(conUnit);
						// expertsInAuthorizedUnit.add(xxx)
					}
				}

				// 找出一个学科下的所有参评单位和授权单位，进行遴选
				selectedExpert.addAll(selectSpecificDiscExperts(
						expertsInAttendedUnit, expertsInAuthorizedUnit));
			}
		}
		try {
			buffWriter.flush();
			buffWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void parseDetailRestricts(
			List<ExpertSelectionRuleDetail> expertSelectionRuleDetails) {
		// 利用反射注入权重
		try {
			SelectionRuleWeightInjectionValve.call(this, ruleMetaService);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (ExpertSelectionRuleDetail detail : expertSelectionRuleDetails) {
			String detailName = getDetailName(detail);
			// 前台是否勾选211学校
			if (detailName.equals("211Expert")) {
				if (null == detail.getConditionChecked()
						|| !detail.getConditionChecked()) {
					is211Weight = 0;
				}
			}
			// 前台是否勾选博士生导师
			else if (detailName.equals("doctoralSupervisor")) {
				if (null == detail.getConditionChecked()
						|| !detail.getConditionChecked()) {
					docSuperWeight = 0;
				}
			}
			// 前台是否勾选硕士生导师
			else if (detailName.equals("masterSupervisor")) {
				if (null == detail.getConditionChecked()
						|| !detail.getConditionChecked()) {
					masSuperWeight = 0;
				}
			}
			// 前台是否勾选研究生院
			else if (detailName.equals("graduateSchoolExpert")) {
				if (null == detail.getConditionChecked()
						|| !detail.getConditionChecked()) {
					isGraduateWeight = 0;
				}
			}
			// 前台是否勾选985学校
			else if (detailName.equals("985Expert")) {
				if (null == detail.getConditionChecked()
						|| !detail.getConditionChecked()) {
					is985Weight = 0;
				}
			}
			// 前台是否勾选不设置管理专家
			else if (detailName.equals("notSetManageExpertPCT")) {
				// 表明设置
				if (null == detail.getConditionChecked()
						|| !detail.getConditionChecked()) {
					needChooseManageExpert = true;
				}
			}

			// 设定遴选的学校范围
			else if (detailName.equals("onlyAttend")) {
				if (detail.getConditionChecked() != null
						&& detail.getConditionChecked()) {
					chooseFromOnlyAttend = true;
					chooseFromAttendAndAccredit = false;
					chooseFromNotAttend = false;
					chooseFromAllUnits = false;
				}
			} else if (detailName.equals("attendAndAccredit")) {
				if (detail.getConditionChecked() != null
						&& detail.getConditionChecked()) {
					chooseFromOnlyAttend = false;
					chooseFromAttendAndAccredit = true;
					chooseFromNotAttend = false;
					chooseFromAllUnits = false;
				}
			} else if (detailName.equals("notAttend")) {
				if (detail.getConditionChecked() != null
						&& detail.getConditionChecked()) {
					chooseFromOnlyAttend = false;
					chooseFromAttendAndAccredit = false;
					chooseFromNotAttend = true;
					chooseFromAllUnits = false;
				}
			} else if (detail.getConditionChecked() != null
					&& detailName.equals("allUnits")) {
				if (detail.getConditionChecked()) {
					chooseFromOnlyAttend = false;
					chooseFromAttendAndAccredit = false;
					chooseFromNotAttend = false;
					chooseFromAllUnits = true;
				}
			}

			// 前台管理专家百分比，要与上一个“是否设置管理专家”一起使用
			else if (detailName.equals("manageExpertPCT")) {
				manageExpertPercent = Double.valueOf(detail.getRestrict1()) / 100;
			} else if (detailName.equals("age")) {
				ageLowerLimit = Integer.valueOf(detail.getRestrict1());
				ageUpperLimit = Integer.valueOf(detail.getRestrict2());
			} else if (detailName.equals("sameDisciplineSumLimit")) {
				sameDisciplineSumLowerLimit = Integer.valueOf(detail
						.getRestrict1());
				sameDisciplineSumUpperLimit = Integer.valueOf(detail
						.getRestrict2());
			} else if (detailName.equals("sameUnitSumLimit")) {
				sameUnitSumLowerLimit = Integer.valueOf(detail.getRestrict1());
				sameUnitSumUpperLimit = Integer.valueOf(detail.getRestrict2());
			}
		}
	}

	private List<ExpertInfoFromOtherDB> selectSpecificDiscExperts(
			List<List<ExpertInfoFromOtherDB>> expertsInAttendedUnit,
			List<List<ExpertInfoFromOtherDB>> expertsInAuthorizedUnit) {
		// doStep1设置为true是因为step1必定执行
		// doStep2~4设置为false是因为它们执行与否要看前一个step的结果
		List<ExpertInfoFromOtherDB> selectedExpert = new ArrayList<ExpertInfoFromOtherDB>();
		boolean doStep1 = true, doStep2 = false, doStep3 = false, doStep4 = false;
		// step1Experts保存每个学科的每个学校的一批专家
		List<ExpertInfoFromOtherDB> step1EveryUnitExperts = new ArrayList<ExpertInfoFromOtherDB>();
		// step1AllExperts保存每个学科的所有学校的专家
		List<ExpertInfoFromOtherDB> step1AllUnitsExperts = new ArrayList<ExpertInfoFromOtherDB>();
		// step1Sum保存所有学校的专家
		int step1Sum = 0;

		List<ExpertInfoFromOtherDB> step2Experts = new ArrayList<ExpertInfoFromOtherDB>();
		List<ExpertInfoFromOtherDB> step3Experts = new ArrayList<ExpertInfoFromOtherDB>();
		List<ExpertInfoFromOtherDB> step4Experts = new ArrayList<ExpertInfoFromOtherDB>();

		int sum = 0;
		// ================step1=================
		if (doStep1) {
			try {
				buffWriter.write("每个学校的下限是：" + sameUnitSumLowerLimit + "，上限是："
						+ sameUnitSumUpperLimit + "\r\n");
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			// 首先进行步骤1的选择
			// 即(选择参评单位数)乘以(每个单位的专家数下限)
			for (List<ExpertInfoFromOtherDB> specificUnitExperts : expertsInAttendedUnit) {
				try {
					buffWriter.write("学科为："
							+ specificUnitExperts.get(0).getYJXKM() + "，学校："
							+ specificUnitExperts.get(0).getXXDM() + "\r\n");
					buffWriter.write("进入：" + sameUnitSumLowerLimit + "\r\n");
				} catch (IOException e) {
				}
				step1EveryUnitExperts = choose(specificUnitExperts,
						sameUnitSumLowerLimit);
				ExpertInfoFromOtherDB e = specificUnitExperts.get(0);
				try {
					buffWriter.write("学科为：" + e.getYJXKM() + "的" + e.getXXDM()
							+ "学校，总共有专家" + specificUnitExperts.size()
							+ "人，step1遴选出" + step1EveryUnitExperts.size()
							+ "人\r\n\r\n\r\n");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				step1AllUnitsExperts.addAll(step1EveryUnitExperts);

				sum += step1EveryUnitExperts.size();
			}
		}

		// 如果步骤1就已经把人都选出来了，那么直接结束这个学科的遴选
		// 如果选出来的人少了，那么继续步骤2
		// 如果选出来的人多了，那么随机选人
		if (sum >= sameDisciplineSumLowerLimit
				&& sum <= sameDisciplineSumUpperLimit) {
			selectedExpert.addAll(step1AllUnitsExperts);
			try {
				buffWriter.write("if1本学科循环结束\r\n\r\n\r\n");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return selectedExpert;
		} else if (sum < sameDisciplineSumLowerLimit) {
			selectedExpert.addAll(step1AllUnitsExperts);
			// 如果选出来的人少了，那么继续步骤2
			doStep2 = true;
			try {
				buffWriter.write("if2本学科循环结束\r\n\r\n\r\n");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			// 测试代码
			return selectedExpert;
		} else {
			step1AllUnitsExperts = random(step1AllUnitsExperts,
					step1AllUnitsExperts.size() - sameDisciplineSumUpperLimit);
			selectedExpert.addAll(step1AllUnitsExperts);

			try {
				buffWriter.write("if3本学科循环结束\r\n\r\n\r\n");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return selectedExpert;
		}
		// 测试代码
		//doStep2 = doStep3 = doStep4 = false;

		/*// ================step2=================
		if (doStep2) {
			for (List<ExpertInfoFromOtherDB> specificUnitExperts : expertsInAuthorizedUnit) {
				step2Experts = choose(specificUnitExperts,
						sameUnitSumLowerLimit);
				sum += step2Experts.size();
			}
		}
		// 逻辑同上
		if (sum >= sameDisciplineSumLowerLimit
				&& sum <= sameDisciplineSumUpperLimit) {
			selectedExpert.addAll(step2Experts);
			return selectedExpert;
		} else if (sum < sameDisciplineSumLowerLimit) {
			selectedExpert.addAll(step2Experts);
			// 如果选出来的人少了，那么继续步骤3
			doStep3 = true;
		} else {
			step2Experts = random(step2Experts, sum
					- sameDisciplineSumUpperLimit);
			selectedExpert.addAll(step2Experts);
			return selectedExpert;
		}

		// ================step3=================
		if (doStep3) {
			for (List<ExpertInfoFromOtherDB> specificUnitExperts : expertsInAttendedUnit) {
				step3Experts = choose(specificUnitExperts, 1);
				sum += step3Experts.size();
			}
		}
		// 逻辑同上
		if (sum >= sameDisciplineSumLowerLimit
				&& sum <= sameDisciplineSumUpperLimit) {
			selectedExpert.addAll(step3Experts);
			return selectedExpert;
		} else if (sum < sameDisciplineSumLowerLimit) {
			selectedExpert.addAll(step3Experts);
			// 如果选出来的人少了，那么继续步骤4
			doStep4 = true;
		} else {
			selectedExpert.addAll(step3Experts);
			randomChoose(null, -1);
			return selectedExpert;
		}

		// ================step4=================
		if (doStep4) {
			for (List<ExpertInfoFromOtherDB> specificUnitExperts : expertsInAuthorizedUnit) {
				step4Experts = choose(specificUnitExperts, 1);
				sum += step4Experts.size();
			}
		}
		// 逻辑同上
		if (sum >= sameDisciplineSumLowerLimit
				&& sum <= sameDisciplineSumUpperLimit) {
			selectedExpert.addAll(step4Experts);
			return selectedExpert;
		} else if (sum < sameDisciplineSumLowerLimit) {
			selectedExpert.addAll(step4Experts);
			// 如果步骤4选出来的人少了没办法了，结束
			return selectedExpert;
		} else {
			step4Experts = random(step4Experts, sum
					- sameDisciplineSumUpperLimit);
			selectedExpert.addAll(step4Experts);
			// random();
			return selectedExpert;
		}*/

	}

	// 把ExpertInfoFromOtherDB的信息赋给ExpertSelected
	private List<ExpertSelected> wrapExperts(EvalBatch evalBatch) {
		List<ExpertSelected> experts = new ArrayList<ExpertSelected>();
		for (ExpertInfoFromOtherDB expert : selectedExpert) {
			ExpertSelected expertSelected = new ExpertSelected();
			// 这里不能setDiscId2,DiscId2只有是专家以一级学科码2的身份被遴选出来是才有用
			//expertSelected.setId(GUID.get());
			expertSelected.setDiscId(expert.getYJXKM());
			expertSelected.setUnitId(expert.getXXDM());
			expertSelected.setExpertNumber(expert.getZJBH());
			expertSelected.setExpertName(expert.getZJXM());
			expertSelected.setExpertType(expert.getZJFL());
			expertSelected.setOfficePhone(expert.getBGDH());
			expertSelected.setPersonalPhone(expert.getYDDH());

			String email = expert.getDZXX();
			// 因为有些邮箱是如下所示，有两个
			// zhanglp418@163.com,xfdzyn@sohu.com
			// 需要拆分
			String[] addrs = email.split("\\,");

			// 已选专家中最多存储专家的三个邮箱
			if (addrs.length > 3) {
				expertSelected.setExpertEmail1(addrs[0]);
				expertSelected.setValidateCode1(MD5Util.encode2hex(addrs[0]));
				expertSelected.setExpertEmail2(addrs[1]);
				expertSelected.setValidateCode2(MD5Util.encode2hex(addrs[1]));
				expertSelected.setExpertEmail3(addrs[2]);
				expertSelected.setValidateCode3(MD5Util.encode2hex(addrs[2]));
			} else {
				for (String addr : addrs) {
					expertSelected.setExpertEmail1(addr);
					expertSelected.setValidateCode1(MD5Util.encode2hex(addr));
				}
			}
			expertSelected.setCurrentStatus(ExpertEvalCurrentStatus.NotMailed
					.getIndex());
			expertSelected.setRemark(null);
			expertSelected.setEvalBatch(evalBatch);
			evalBatch.getExperts().add(expertSelected);
			experts.add(expertSelected);

		}
		// 集合清空重置
		selectedExpert = new ArrayList<ExpertInfoFromOtherDB>();
		return experts;
	}

	private String getDetailName(ExpertSelectionRuleDetail detail) {
		return detail.getItemName();
	}

	private ExpertSelectionRuleDetail getDetailByName(String detailName,
			List<ExpertSelectionRuleDetail> expertSelectionRuleDetails) {
		for (ExpertSelectionRuleDetail detail : expertSelectionRuleDetails) {
			if (detailName.equals(detail.getItemName())) {
				return detail;
			}
		}
		return null;
	}

	// 一个学科的一个学校中，管理专家不够通过非管理专家补齐，非管理专家不够通过管理专家贾补齐，并报告
	private List<ExpertInfoFromOtherDB> choose(
			List<ExpertInfoFromOtherDB> experts, int number) {
		int manageExpertsNumber = (int) (number * manageExpertPercent);
		int commonExpertsNumber = number - manageExpertsNumber;

		// 获得所有管理专家
		List<ExpertInfoFromOtherDB> manageExperts = getManageExperts(experts);
		try {
			buffWriter.write("其中管理专家总共有：" + manageExperts.size() + "\r\n");
			for (ExpertInfoFromOtherDB m : manageExperts) {
				buffWriter.write(m.getZJXM() + "是" + m.getXZZW() + "\r\n");
			}
		} catch (IOException e) {
		}

		// 从管理专家中随机选出了manageExpertsNumber个管理专家
		List<ExpertInfoFromOtherDB> choosenExperts = randomChoose(
				manageExperts, manageExpertsNumber);
		try {
			buffWriter.write("每组下限" + number + "人 x 管理专家比例"
					+ manageExpertPercent * 100 + "%=" + manageExpertsNumber
					+ "人\r\n");
			buffWriter.write("按比例选出的管理专家总共有：" + choosenExperts.size() + "\r\n");
			for (ExpertInfoFromOtherDB m : choosenExperts) {
				buffWriter.write(m.getZJXM() + "\r\n");
			}
		} catch (IOException e) {
		}

		// 对所有非管理专家进行权重计算
		List<ExpertInfoFromOtherDB> evaluatedExperts = evaluateWeigth(experts);

		// 挑选出commonExpertsNumber个权重最大的专家
		for (int i = 0; i < commonExpertsNumber; i++) {
			ExpertInfoFromOtherDB maxWeigthExpert = getMaxWeightExpert(evaluatedExperts);
			if (null != maxWeigthExpert) {
				// 把权重最高的专家放入选出的专家中
				choosenExperts.add(maxWeigthExpert);
			}
		}

		try {
			buffWriter.write("step1对一个学校选择的专家总共有：" + choosenExperts.size()
					+ "\r\n");
		} catch (IOException e) {
		}
		return choosenExperts;
	}

	private List<ExpertInfoFromOtherDB> randomChoose(
			List<ExpertInfoFromOtherDB> experts, int choosenNumber) {
		List<ExpertInfoFromOtherDB> randomChoosenExperts = new ArrayList<ExpertInfoFromOtherDB>();
		if (null == experts) {
			// need Log
			return null;
		}
		if (experts.size() <= choosenNumber) {
			// need Log
			return experts;
		} else {
			Set<Integer> set = new HashSet<Integer>();
			try {
				buffWriter.write("在随机抽选" + choosenNumber + "名管理专家\r\n");
			} catch (IOException e) {
			}
			while (set.size() < choosenNumber) {
				set.add(Integer.valueOf((int) (Math.random() * choosenNumber)));// 产生0-1000的双精度随机数
			}
			for (Integer s : set) {
				try {
					buffWriter.write("抽选了第" + s + "号管理专家"
							+ experts.get(s).getZJXM() + " \r\n");
				} catch (IOException e) {
				}
				randomChoosenExperts.add(experts.get(s));
			}
			return randomChoosenExperts;
		}
	}

	// 找出权重最大的专家，并将其权重设为-1
	private ExpertInfoFromOtherDB getMaxWeightExpert(
			List<ExpertInfoFromOtherDB> list) {
		int weight = -1;
		ExpertInfoFromOtherDB expert = null;
		for (ExpertInfoFromOtherDB e : list) {
			if (weight < e.getWeight()) {
				weight = e.getWeight();
				expert = e;
			}
		}

		// expert不为空，把最大权重设为1，下次不会再选出来了
		if (null != expert) {
			try {
				buffWriter.write("非管理专家按权重排序：" + expert.getZJXM() + "的权重是："
						+ expert.getWeight() + "\r\n");
			} catch (IOException e) {
			}

			expert.setWeight(-1);
		}
		return expert;
	}

	private List<ExpertInfoFromOtherDB> getManageExperts(
			List<ExpertInfoFromOtherDB> experts) {
		List<ExpertInfoFromOtherDB> manageExperts = new ArrayList<ExpertInfoFromOtherDB>();
		for (ExpertInfoFromOtherDB e : experts) {
			// XXZW：行政职务
			if (null == e.getXZZW()) {
				continue;
			} else if (Pattern.matches("^评议组成员$|校长$|院长$|处长$|系主任$", e.getXZZW())) {
				e.setWeight(-1);
				manageExperts.add(e);
			}
		}
		return manageExperts;
	}

	// 算出每一位专家的权重
	private List<ExpertInfoFromOtherDB> evaluateWeigth(
			List<ExpertInfoFromOtherDB> experts) {
		for (ExpertInfoFromOtherDB e : experts) {
			if (-1 != e.getWeight()) {
				int weight = 0;
				if ("1".equals(e.getIS_985())) {
					weight += is985Weight;
					try {
						buffWriter.write(e.getZJXM() + "是985，权重变成：" + weight
								+ "\r\n");
					} catch (IOException t) {
					}
				}
				if ("1".equals(e.getIS_GRADUATE())) {
					weight += isGraduateWeight;
					try {
						buffWriter.write(e.getZJXM() + "是研究生院校，权重变成：" + weight
								+ "\r\n");
					} catch (IOException t) {
					}
				}
				if ("1".equals(e.getIS_211())) {
					weight += is211Weight;
					try {
						buffWriter.write(e.getZJXM() + "是211院校，权重变成：" + weight
								+ "\r\n");
					} catch (IOException t) {
					}
				}
				if ("1".equals(e.getDSLBM())) {
					weight += masSuperWeight;
					try {
						buffWriter.write(e.getZJXM() + "是硕导，权重变成：" + weight
								+ "\r\n");
					} catch (IOException t) {
					}
				}
				if ("2".equals(e.getDSLBM())) {
					weight += docSuperWeight;
					try {
						buffWriter.write(e.getZJXM() + "是博导，权重变成：" + weight
								+ "\r\n");
					} catch (IOException t) {
					}
				}
				int expertAge = Calendar.getInstance().get(Calendar.YEAR)
						- Integer.valueOf(e.getCSNY().substring(0, 4));
				if (expertAge >= ageLowerLimit && expertAge <= ageUpperLimit) {
					weight += ageWeight;
					try {
						buffWriter.write(e.getZJXM() + "年龄符合条件，年龄是："
								+ expertAge + "，权重变成：" + weight + "\r\n");
					} catch (IOException t) {
					}
				}
				e.setWeight(weight);
			}
		}
		return experts;
	}

	private List<ExpertInfoFromOtherDB> random(
			List<ExpertInfoFromOtherDB> list, int removeTimes) {
		Random random = new Random();
		for (int i = 0; i < removeTimes; i++) {
			int argv1 = random.nextInt(list.size());
			int argv2 = (list.size() + 1);
			int s = argv1 % argv2;
			if (s <= 0)
				s++;
			list.remove(s);
			System.out.println("argv1%argv2=" + s);
			System.out.println("size=" + list.size() + " times=" + (i + 1));
		}
		return list;
	}

	private List<DiscAndUnits> getImitateDiscAndOnlyAttendUnits() {
		// 模拟
		List<DiscAndUnits> discAndUnitsList = new ArrayList<DiscAndUnits>();
		List<String> imitate_units = new ArrayList<String>();
		String[] units = TestData.attendUnits;
		for (String unit : units) {
			imitate_units.add(unit);
		}
		String[] discs = TestData.allDisc;
		for (String disc : discs) {
			discAndUnitsList.add(new DiscAndUnits(disc, imitate_units));
		}
		// 模拟结束
		return discAndUnitsList;
	}

	private List<DiscAndUnits> getImitateDiscAndAttendAndAccreditUnits() {
		// 模拟
		List<DiscAndUnits> discAndUnitsList = new ArrayList<DiscAndUnits>();
		List<String> imitate_units = new ArrayList<String>();
		String[] units = TestData.accridtUnits;
		for (String unit : units) {
			imitate_units.add(unit);
		}
		
		String[] discs = TestData.allDisc;
		for (String disc : discs) {
			discAndUnitsList.add(new DiscAndUnits(disc, imitate_units));
		}
		// 模拟结束
		return discAndUnitsList;
	}

	private List<DiscAndUnits> getImitateDiscAndNotAttendUnits() {
		// 模拟
		List<DiscAndUnits> discAndUnitsList = new ArrayList<DiscAndUnits>();
		List<String> imitate_units = new ArrayList<String>();
		String[] units = TestData.notAttendUnits;
		for (String unit : units) {
			imitate_units.add(unit);
		}
		String[] discs = TestData.allDisc;
		for (String disc : discs) {
			discAndUnitsList.add(new DiscAndUnits(disc, imitate_units));
		}
		// 模拟结束
		return discAndUnitsList;
	}

	private List<DiscAndUnits> getImitateDiscAndAllUnitsUnits() {
		// 模拟
		List<DiscAndUnits> discAndUnitsList = new ArrayList<DiscAndUnits>();
		List<String> imitate_units = new ArrayList<String>();
		String[] units = TestData.allUnits;
		for (String unit : units) {
			imitate_units.add(unit);
		}
		String[] discs = TestData.allDisc;
		for (String disc : discs) {
			discAndUnitsList.add(new DiscAndUnits(disc, imitate_units));
		}
		// 模拟结束
		return discAndUnitsList;
	}

	private List<DiscAndUnits> getImitateDiscAndUnits() {
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
		discAndUnitsList.add(new DiscAndUnits("0812", imitate_units2));
		discAndUnitsList.add(new DiscAndUnits("0825", imitate_units2));
		discAndUnitsList.add(new DiscAndUnits("0829", imitate_units));
		// 模拟结束
		return discAndUnitsList;
	}

	public RuleMetaService getRuleMetaService() {
		return ruleMetaService;
	}

	public void setRuleMetaService(RuleMetaService ruleMetaService) {
		this.ruleMetaService = ruleMetaService;
	}
}
