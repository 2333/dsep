package com.dsep.service.expert.select.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.dsep.domain.dsepmeta.expert.DiscAndUnits;
import com.dsep.domain.dsepmeta.expert.ExpertInfoFromOtherDB;
import com.dsep.entity.dsepmeta.EvalBatch;
import com.dsep.entity.dsepmeta.ExpertSelected;
import com.dsep.entity.dsepmeta.ExpertSelectionRuleDetail;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;
import com.dsep.service.expert.rule.RuleMetaService;
import com.dsep.service.expert.select.DiscAndUnitDivisionSelectionStrategyService;
import com.dsep.service.expert.select.util.ExpertsFromOtherDB2TreeConvertorValve;
import com.dsep.service.expert.select.util.SelectionRuleWeightInjectionValve;
import com.dsep.service.expert.select.util.UnitsValve;
import com.dsep.service.expert.select.util.UserConfigWeightInjectionValve;
import com.dsep.util.expert.ExpertEvalCurrentStatus;
import com.dsep.util.expert.email.MD5Util;

public class DiscAndUnitDivisionSelectionStrategyServiceImpl implements
		DiscAndUnitDivisionSelectionStrategyService {
	public static Logger logger1 = Logger.getLogger("selectLog");

	private RuleMetaService ruleMetaService;
	private DisciplineService disciplineService;
	private UnitService unitService;

	//=======================基本参数 basic args=========================
	private int is211Weight = 0, isGraduateWeight = 0, is985Weight = 0,
			ageWeight = 0, docSuperWeight = 0, masSuperWeight = 0;

	private int ageLowerLimit = 0, ageUpperLimit = 0;

	private int sameDisciplineSumLowerLimit = 0,
			sameDisciplineSumUpperLimit = 0;

	private int sameUnitSumLowerLimit = 0, sameUnitSumUpperLimit = 0;

	private double manageExpertPercent = 0D;

	// 默认不区分管理专家与非管理专家
	private boolean needChooseManageExpert = false;

	// 选择范围,默认都是false
	private boolean chooseFromOnlyAttend = false,
			chooseFromAttendAndAccredit = false, chooseFromNotAttend = false,
			chooseFromAllUnits = false;
	//=======================基本参数 basic args=========================

	// 全局变量,用来存放本次select方法调用遴选出的专家
	List<ExpertInfoFromOtherDB> selectedExpert = new ArrayList<ExpertInfoFromOtherDB>();

	/**
	 * 这个select的实现
	 * 是对所有的专家按照学科、学校进行分类
	 * 把20W专家变成一个树形结构
	 * 对每个树的子节点进行遴选
	 * 
	 * 这个算法的第二步convertRawData2ListTree和第三步findSpecificDiscNodes是关键
	 *        
	 *         专家、学科、以及学科的数量大概为：
	 * 		   20W专家、4000学科、100一级学科 
	 * 		   
	 *         首先专家按照一级学科分组
	 *         遴选的流程如下
	 *         规定每组的专家上下限：group_lower/group_upper
	 *         规定同一学校选择的专家上下限：college_lower/college_upper
	 * 
	 *         步骤1. 在参评单位中，每个单位各选出college_lower个专家
	 *         如果总数量∈[group_lower..group_upper]，结束;如果总量小于下限group_lower，步骤2
	 * 
	 *         步骤2. 继续在未参评但被授权的单位中，每个单位各选出college_lower个专家，加入总量
	 *         如果总数量∈[group_lower..group_upper]，结束;如果总量仍小于下限group_lower，步骤3
	 * 
	 *         步骤3. 继续在参评单位中，每个单位再各选出1个专家，加入总量 如果总数量∈[group_lower..group_upper]，结束
	 *         如果总量仍小于下限group_lower，步骤4
	 * 
	 *         步骤4. 继续在未参评但被授权的单位中，每个单位再各选出1个专家，加入总量
	 *         无论总数量是否∈[group_lower..group_upper]，都结束
	 *         不在[group_lower..group_upper]则给出警告
	 * 
	 * 
	 *         所以遴选代码实现思路：
	 * 
	 *         1.把专家按照专家的一级学科码分类，分成100个左右的组
	 * 
	 *         2.把每个组中的专家按照学校分类
	 * 
	 *         3.获得每个一级学科、每个学校的专家list，按照优先系数加权排列，选择出排名前几的专家
	 * 
	 *         4.如果流程未完，重复1~3的过程
	 * 
	 */
	@Override
	public List<ExpertSelected> select(List<ExpertInfoFromOtherDB> experts,
			Map<String, java.util.List<String>> discsAndUnitsInfo,
			List<ExpertSelectionRuleDetail> ruleDetails, EvalBatch evalBatch,
			String currentDisc) {
		// 通过反射给基本参数赋值
		basicArgsConfig(ruleDetails);

		// 把专家转变成树形结构
		List<List<ExpertInfoFromOtherDB>> container = ExpertsFromOtherDB2TreeConvertorValve
				.call(experts);

		String[] units = UnitsValve.call(chooseFromAllUnits,
				chooseFromAttendAndAccredit, chooseFromNotAttend,
				chooseFromOnlyAttend, currentDisc, unitService,
				disciplineService);
		if (units != null) {
			// 通过对比参评学校，找到树形结构相同学校的节点，其内部会调用选择方法
			traversalNodesAndSelect(container, units);

			// 把traversalNodesAndSelect一个学科所有学校遴选出的专家封装好
			return wrapExperts(evalBatch, currentDisc);
		} else {
			return null;
		}

	};

	private void basicArgsConfig(
			List<ExpertSelectionRuleDetail> expertSelectionRuleDetails) {
		SelectionRuleWeightInjectionValve.call(this, ruleMetaService);
		UserConfigWeightInjectionValve.call(this, expertSelectionRuleDetails);
	}

	public void traversalNodesAndSelect(
			List<List<ExpertInfoFromOtherDB>> container, String[] unitsArr) {

		logger1.info("开始寻找学校节点");

		// 现在有两个类树状的数据结构，分别是List<List<E>>和String[]类型
		// List<List<<E>>来源于某个学科的专家
		// String[]来源于参评配置选择的学校
		// 现在需要找到两个数据结构中相同学校的节点
		
		// 需要以String[]为主导找，因为它的序列是有意义的
		for (String unit : unitsArr) {
			Iterator<List<ExpertInfoFromOtherDB>> iter = container.iterator();
			while (iter.hasNext()) {
				List<ExpertInfoFromOtherDB> list = iter.next();
				if (list.get(0).getXXDM().equals(unit)) {
					// 对特定学校节点进行遴选并存储到全局变量selectedExpert中
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

			logger1.info("学科为：" + list.get(0).getYJXKM() + "，学校："
					+ list.get(0).getXXDM());
			logger1.info("学校的下限是：" + sameUnitSumLowerLimit + "，上限是："
					+ sameUnitSumUpperLimit + "\r\n");
			// 首先进行步骤1的选择
			// 即(选择参评单位数)乘以(每个单位的专家数下限)
			logger1.info("开始执行步骤");

			step1EveryUnitExperts = choose(list, sameUnitSumLowerLimit);
			ExpertInfoFromOtherDB e = list.get(0);

			logger1.info("学科为：" + e.getYJXKM() + "的" + e.getXXDM() + "学校，总共有专家"
					+ list.size() + "人，step1遴选出" + step1EveryUnitExperts.size()
					+ "人");

			step1AllUnitsExperts.addAll(step1EveryUnitExperts);

			sum += step1EveryUnitExperts.size();
		}

		// 如果步骤1就已经把人都选出来了，那么直接结束这个学科的遴选
		// 如果选出来的人少了，那么继续步骤2
		// 如果选出来的人多了，那么随机选人
		if (sum >= sameDisciplineSumLowerLimit
				&& sum <= sameDisciplineSumUpperLimit) {
			selectedExpert.addAll(step1AllUnitsExperts);

			logger1.info("步骤1执行完毕遴选结束\r\n\r\n");
			return selectedExpert;
		} else if (sum < sameDisciplineSumLowerLimit) {
			selectedExpert.addAll(step1AllUnitsExperts);
			// 如果选出来的人少了，那么继续步骤2
			doStep2 = true;

			logger1.info("步骤2执行完毕遴选结束\r\n\r\n");
			// 测试代码
			return selectedExpert;
		} else {
			step1AllUnitsExperts = random(step1AllUnitsExperts,
					step1AllUnitsExperts.size() - sameDisciplineSumUpperLimit);
			selectedExpert.addAll(step1AllUnitsExperts);

			logger1.info("步骤3执行完毕遴选结束\r\n\r\n");
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
			//discAndUnitsList = getDiscAndAllUnits();
		} else if (chooseFromAttendAndAccredit) {
			//discAndUnitsList = getImitateDiscAndAttendAndAccreditUnits();
		} else if (chooseFromNotAttend) {
			//discAndUnitsList = getImitateDiscAndNotAttendUnits();
		} else if (chooseFromOnlyAttend) {
			//discAndUnitsList = getImitateDiscAndOnlyAttendUnits();
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
			// 首先进行步骤1的选择
			// 即(选择参评单位数)乘以(每个单位的专家数下限)
			for (List<ExpertInfoFromOtherDB> specificUnitExperts : expertsInAttendedUnit) {

				logger1.info("学科为：" + specificUnitExperts.get(0).getYJXKM()
						+ "，学校：" + specificUnitExperts.get(0).getXXDM()
						+ "\r\n");
				logger1.info("进入：" + sameUnitSumLowerLimit + "\r\n");
				step1EveryUnitExperts = choose(specificUnitExperts,
						sameUnitSumLowerLimit);
				ExpertInfoFromOtherDB e = specificUnitExperts.get(0);

				logger1.info("学科为：" + e.getYJXKM() + "的" + e.getXXDM()
						+ "学校，总共有专家" + specificUnitExperts.size()
						+ "人，step1遴选出" + step1EveryUnitExperts.size()
						+ "人\r\n\r\n");

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

			logger1.info("步骤1执行完毕遴选结束");
			return selectedExpert;
		} else if (sum < sameDisciplineSumLowerLimit) {
			selectedExpert.addAll(step1AllUnitsExperts);
			// 如果选出来的人少了，那么继续步骤2
			doStep2 = true;

			logger1.info("步骤2执行完毕遴选结束");
			// 测试代码
			return selectedExpert;
		} else {
			logger1.info("选出的人数超出上限");
			step1AllUnitsExperts = random(step1AllUnitsExperts,
					step1AllUnitsExperts.size() - sameDisciplineSumUpperLimit);
			selectedExpert.addAll(step1AllUnitsExperts);

			logger1.info("步骤3执行完毕遴选结束");
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
	private List<ExpertSelected> wrapExperts(EvalBatch evalBatch,
			String currentDisc) {
		List<ExpertSelected> experts = new ArrayList<ExpertSelected>();
		logger1.info("开始转换为本地数据库格式");
		for (ExpertInfoFromOtherDB expert : selectedExpert) {
			System.out.println(expert.getZJXM());
			ExpertSelected expertSelected = new ExpertSelected();
			expertSelected.setDiscId(expert.getYJXKM());
			expertSelected.setDiscId2(expert.getYJXKM2());
			// 这里不能setDiscId2,DiscId2只有是专家以一级学科码2的身份被遴选出来是才有用
			//expertSelected.setId(GUID.get());
			if (currentDisc.equals(expert.getYJXKM())) {
				expertSelected.setUseDiscId(true);
			} else if (currentDisc.equals(expert.getYJXKM2())) {
				expertSelected.setUseDiscId(false);
			} else {
				new Exception("学科码有问题");
			}

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
		logger1.info("本地数据库格式转换完毕\r\n\r\n\r\n");
		return experts;
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
		logger1.info("本组专家数量为" + experts.size());
		int manageExpertsNumber = (int) (number * manageExpertPercent);
		int commonExpertsNumber = number - manageExpertsNumber;
		logger1.info("所需管理专家的数量为" + manageExpertsNumber);
		logger1.info("所需普通专家的数量为" + commonExpertsNumber);
		// 获得所有管理专家
		List<ExpertInfoFromOtherDB> manageExperts = getManageExperts(experts);

		logger1.info("其中管理专家总共有" + manageExperts.size() + "人，他们分别是:");
		for (ExpertInfoFromOtherDB e : manageExperts) {
			logger1.info(e.getZJBH() + e.getZJXM());
		}
		logger1.info("\r\n");

		// 从管理专家中随机选出了manageExpertsNumber个管理专家
		List<ExpertInfoFromOtherDB> choosenExperts = randomChoose(
				manageExperts, manageExpertsNumber);

		logger1.info("每组下限" + number + "人 x 管理专家比例" + manageExpertPercent * 100
				+ "%=" + manageExpertsNumber + "人");
		logger1.info("按比例实际选出的管理专家有" + choosenExperts.size() + "人\r\n");

		// 对所有非管理专家进行权重计算
		List<ExpertInfoFromOtherDB> evaluatedExperts = evaluateWeigth(experts);

		logger1.info("该组专家权重计算完毕");

		// 挑选出commonExpertsNumber个权重最大的专家
		for (int i = 0; i < commonExpertsNumber; i++) {
			ExpertInfoFromOtherDB maxWeigthExpert = getMaxWeightExpert(evaluatedExperts);
			if (null != maxWeigthExpert) {
				// 把权重最高的专家放入选出的专家中
				choosenExperts.add(maxWeigthExpert);
			}
		}

		logger1.info("step1对该学校选择的专家总共有：" + choosenExperts.size() + "\r\n");

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
			logger1.info("在随机抽选" + choosenNumber + "名管理专家");
			while (set.size() < choosenNumber) {
				set.add(Integer.valueOf((int) (Math.random() * choosenNumber)));// 产生0-1000的双精度随机数
			}
			for (Integer s : set) {
				logger1.info("抽选了第" + s + "号管理专家" + experts.get(s).getZJXM());
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
			/*try {
				buffWriter.write("非管理专家按权重排序：" + expert.getZJXM() + "的权重是："
						+ expert.getWeight() + "\r\n");
			} catch (IOException e) {
			}*/
			logger1.info("非管理专家按权重排序：" + expert.getZJXM() + "的权重是："
					+ expert.getWeight());

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
			logger1.info("计算专家" + e.getZJBH() + e.getZJXM() + "的权重");
			if (-1 != e.getWeight()) {
				int weight = 0;
				if ("1".equals(e.getIS_985())) {
					weight += is985Weight;
					logger1.info("所在院校是985，权重变为" + weight);
				}
				if ("1".equals(e.getIS_GRADUATE())) {
					weight += isGraduateWeight;
					logger1.info("所在院校有研究生院，权重变为" + weight);
				}
				if ("1".equals(e.getIS_211())) {
					weight += is211Weight;
					logger1.info("所在院校是211，权重变为" + weight);
				}
				if ("1".equals(e.getDSLBM())) {
					weight += masSuperWeight;
					logger1.info("该专家是硕导，权重变为" + weight);
				}
				if ("2".equals(e.getDSLBM())) {
					weight += docSuperWeight;
					logger1.info("该专家是博导，权重变为" + weight);
				}
				int expertAge = Calendar.getInstance().get(Calendar.YEAR)
						- Integer.valueOf(e.getCSNY().substring(0, 4));
				logger1.info("该专家年龄为" + expertAge);
				if (expertAge >= ageLowerLimit && expertAge <= ageUpperLimit) {
					weight += ageWeight;
					logger1.info("年龄符合条件，权重变为" + weight);
				}
				e.setWeight(weight);
				logger1.info("该专家最终权重为" + weight + "\r\n");
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

	public RuleMetaService getRuleMetaService() {
		return ruleMetaService;
	}

	public void setRuleMetaService(RuleMetaService ruleMetaService) {
		this.ruleMetaService = ruleMetaService;
	}

	public DisciplineService getDisciplineService() {
		return disciplineService;
	}

	public void setDisciplineService(DisciplineService disciplineService) {
		this.disciplineService = disciplineService;
	}

	public UnitService getUnitService() {
		return unitService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

}
