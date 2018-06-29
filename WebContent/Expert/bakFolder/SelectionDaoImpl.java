package com.dsep.dao.dsepmeta.expert.selection.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import com.dsep.dao.common.impl.DaoImpl;
import com.dsep.dao.dsepmeta.expert.selection.SelectionDao;
import com.dsep.domain.dsepmeta.expert.OuterExpert;
import com.dsep.entity.expert.Expert;
import com.dsep.entity.expert.ExpertSelectionRuleDetail;
import com.dsep.service.expert.select.util.ExpertsFromOtherDB2TreeConvertorValve;
import com.dsep.service.expert.select.util.SelectionRuleWeightInjectionValve;

/**
 * 
 * @author p_next 专家、学科、以及学科的数量大概为：20W专家、4000学科、100一级学科 首先专家按照一级学科分组
 * 
 *         遴选的流程如下
 * 
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
public class SelectionDaoImpl extends DaoImpl<OuterExpert, String>
		implements SelectionDao {
	private int is211Weight = 0, 
				isGraduateWeight = 0, 
				is985Weight = 0,
				manageWeight = 0, 
				ageWeight = 0;
	
	private int ageLowerLimit = 0, 
				ageUpperLimit = 0;
	
	private int sameDisciplineSumLowerLimit = 0,
				sameDisciplineSumUpperLimit = 0;
	private int sameUnitSumLowerLimit = 0, 
				sameUnitSumUpperLimit = 0;
	
	private double manageExpertPercent = 0D;
	private void print() {
		System.out.println("is211Weight:" + is211Weight);
		System.out.println("isGraduateWeight:" + isGraduateWeight);
		System.out.println("is985Weight:" + is985Weight);
		System.out.println("manageWeight:" + manageWeight);
		System.out.println("ageWeight:" + ageWeight);
		System.out.println("ageLowerLimit:" + ageLowerLimit);
		System.out.println("ageUpperLimit:" + ageUpperLimit);
		System.out.println("sameDisciplineSumLowerLimit:" + sameDisciplineSumLowerLimit);
		System.out.println("sameDisciplineSumUpperLimit:" + sameDisciplineSumUpperLimit);
		System.out.println("sameUnitSumLowerLimit:" + sameUnitSumLowerLimit);
		System.out.println("sameUnitSumUpperLimit:" + sameUnitSumUpperLimit);
		System.out.println("manageExpertPercent:" + manageExpertPercent);
	}

	List<OuterExpert> selectedExpert = new ArrayList<OuterExpert>();
	// 参评单位代码
	List<String> attendedUnit = new ArrayList<String>();
	// 授权单位代码
	List<String> authorizedUnit = new ArrayList<String>();

	// 传入的是学科学校参评信息、从外部数据库来的专家信息、遴选细则
	@Override
	public List<Expert> selectExperts(List<Object> disciplines,
			List<Object> units, List<OuterExpert> list,
			List<ExpertSelectionRuleDetail> expertSelectionRuleDetails) {
		
		List<List<List<OuterExpert>>> container = beforeSelect(list,
				expertSelectionRuleDetails);

		print();
		select(null, container);

		return afterSelect();
	}
	
	@Override
	public List<OuterExpert> sequenceExperts(List<OuterExpert> experts){
		experts = evaluateWeigth(experts);
		for(int i=0;i<experts.size()-1;i++){
			if(experts.get(i).getWeight()<experts.get(i+1).getWeight()){
				OuterExpert e = new OuterExpert();
				e = experts.get(i);
				experts.set(i, experts.get(i+1));
				experts.set(i+1, e);
			}
		}
		return experts;
	}

	// 在开始选择前做两件事：
	// 1.把规则解析出来，权重赋值把每个规则细则
	// 2.把从外部来的按学科、学校有序的专家列表转换为一棵树
	private List<List<List<OuterExpert>>> beforeSelect(
			List<OuterExpert> list,
			List<ExpertSelectionRuleDetail> expertSelectionRuleDetails) {
		// 利用反射注入权重
		try {
			//SelectionRuleAndWeightInjection.injectWeight(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 解析规则
		parseDetailRestricts(expertSelectionRuleDetails);

		// 处理传入的数据
		return ExpertsFromOtherDB2TreeConvertorValve.convertRawData2ListTree(list);

	}

	private void select(Map<String, List<String>> map,
			List<List<List<OuterExpert>>> container) {
		// 模拟
		Map<String, List<String>> discipline_units = new HashMap<String, List<String>>();
		List<String> imitate_units = new ArrayList<String>();
		imitate_units.add("10001");
		imitate_units.add("10002");
		imitate_units.add("10003");
		imitate_units.add("10004");
		imitate_units.add("10005");
		imitate_units.add("10006");
		imitate_units.add("10007");
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
		imitate_units.add("10030");
		imitate_units.add("10031");
		imitate_units.add("10032");
		imitate_units.add("10033");
		imitate_units.add("10056");
		imitate_units.add("10057");
		imitate_units.add("10129");
		imitate_units.add("10183");
		imitate_units.add("10192");
		imitate_units.add("10193");
		imitate_units.add("10201");
		imitate_units.add("10225");
		imitate_units.add("10298");
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
		discipline_units.put("0829", imitate_units);
		// 模拟结束

		// 现在有两个类树状的数据结构，分别是List<List<List<E>>>和Map<String, List>类型
		// List<List<List<E>>>来源于20W条专家
		// Map<String, List>来源于参评的学科、学校
		// 两者都有一层节点表示所在单位，再上一层节点是单位对应的学科
		// 两个类树状数据结构的单位和专家节点值都是从小到大排列的
		// 现在需要找到两个数据结构中相同学科、相同学校的节点
		Iterator<List<List<OuterExpert>>> conDisciplinesIter = container
				.iterator();
		Iterator<String> mDisciplinesIter = discipline_units.keySet()
				.iterator();

		// 以Map<String, List>来循环
		while (mDisciplinesIter.hasNext()) {
			String mDiscipline = mDisciplinesIter.next();
			List<List<OuterExpert>> conDiscipline = null;
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
				List<String> mUnits = discipline_units.get(mDiscipline);
				// findAuAndAuUnitsInSpecificDiscipline(conDiscipline, mUnits);
				List<List<OuterExpert>> conUnits = conDiscipline;
				Iterator<List<OuterExpert>> conUnitsIter = conUnits
						.iterator();
				Iterator<String> mUnitsIter = mUnits.iterator();

				// 某一学科的所有参评单位下的专家
				List<List<OuterExpert>> expertsInAttendedUnit = new ArrayList<List<OuterExpert>>();
				// 某一学科的所有授权单位下的专家
				List<List<OuterExpert>> expertsInAuthorizedUnit = new ArrayList<List<OuterExpert>>();
				// 这和外层的循环思路一样
				while (mUnitsIter.hasNext()) {
					String mUnit = mUnitsIter.next();
					List<OuterExpert> conUnit = null;
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
				selectSpecificDisciplineExperts(expertsInAttendedUnit,
						expertsInAuthorizedUnit);
			}
		}

	}

	// 选出来的专家会加入到全局变量selectedExpert中
	private void selectSpecificDisciplineExperts(
			List<List<OuterExpert>> expertsInAttendedUnit,
			List<List<OuterExpert>> expertsInAuthorizedUnit) {
		// doStep1设置为true是因为step1必定执行
		// doStep2~4设置为false是因为它们执行与否要看前一个step的结果
		boolean doStep1 = true, doStep2 = false, doStep3 = false, doStep4 = false;
		List<OuterExpert> step1Experts = new ArrayList<OuterExpert>();
		List<OuterExpert> step2Experts = new ArrayList<OuterExpert>();
		List<OuterExpert> step3Experts = new ArrayList<OuterExpert>();
		List<OuterExpert> step4Experts = new ArrayList<OuterExpert>();

		int sum = 0;
		// ================step1=================
		if (doStep1) {

			// 首先进行步骤1的选择
			// 即选择参评单位数 * 每个单位的专家数下限
			for (List<OuterExpert> specificUnitExperts : expertsInAttendedUnit) {
				step1Experts = choose(specificUnitExperts,
						sameUnitSumLowerLimit);
				selectedExpert.addAll(step1Experts);
				sum += step1Experts.size();
			}
		}

		// 如果步骤1就已经把人都选出来了，那么直接结束这个学科的遴选
		// 如果选出来的人少了，那么继续步骤2
		// 如果选出来的人多了，那么随机选人
		if (sum >= sameDisciplineSumLowerLimit
				&& sum <= sameDisciplineSumUpperLimit) {
			return;
		} else if (sum < sameDisciplineSumLowerLimit) {
			// 如果选出来的人少了，那么继续步骤2
			doStep2 = true;
		} else {
			// random();
		}

		// ================step2=================
		if (doStep2) {
			for (List<OuterExpert> specificUnitExperts : expertsInAuthorizedUnit) {
				step2Experts = choose(specificUnitExperts,
						sameUnitSumLowerLimit);
				selectedExpert.addAll(step2Experts);
				sum += step2Experts.size();
			}
		}
		// 逻辑同上
		if (sum >= sameDisciplineSumLowerLimit
				&& sum <= sameDisciplineSumUpperLimit) {
			return;
		} else if (sum < sameDisciplineSumLowerLimit) {
			// 如果选出来的人少了，那么继续步骤3
			doStep3 = true;
		} else {

			// random();
		}

		// ================step3=================
		if (doStep3) {
			for (List<OuterExpert> specificUnitExperts : expertsInAttendedUnit) {
				step3Experts = choose(specificUnitExperts, 1);
				selectedExpert.addAll(step3Experts);
				sum += step3Experts.size();
			}
		}
		// 逻辑同上
		if (sum >= sameDisciplineSumLowerLimit
				&& sum <= sameDisciplineSumUpperLimit) {
			return;
		} else if (sum < sameDisciplineSumLowerLimit) {
			// 如果选出来的人少了，那么继续步骤3
			doStep4 = true;
		} else {
			randomChoose(null, -1);
		}

		// ================step4=================
		if (doStep4) {
			for (List<OuterExpert> specificUnitExperts : expertsInAuthorizedUnit) {
				step4Experts = choose(specificUnitExperts, 1);
				selectedExpert.addAll(step4Experts);
				sum += step4Experts.size();
			}
		}
		// 逻辑同上
		if (sum >= sameDisciplineSumLowerLimit
				&& sum <= sameDisciplineSumUpperLimit) {
			return;
		} else if (sum < sameDisciplineSumLowerLimit) {
			// 如果步骤4选出来的人少了没办法了，结束
		} else {
			// random();
		}
	}

	private List<OuterExpert> choose(
			List<OuterExpert> experts, int number) {
		int manageExpertsNumber = (int) (number * manageExpertPercent);
		int commonExpertsNumber = number - manageExpertsNumber;

		// 获得所有管理专家
		List<OuterExpert> manageExperts = getManageExperts(experts);
		// 从管理专家中随机选出了manageExpertsNumber个管理专家
		List<OuterExpert> choosenExperts = randomChoose(
				manageExperts, manageExpertsNumber);

		// 对所有非管理专家进行权重计算
		List<OuterExpert> evaluatedExperts = evaluateWeigth(experts);

		// 挑选出commonExpertsNumber个权重最大的专家
		for (int i = 0; i < commonExpertsNumber; i++) {
			OuterExpert maxWeigthExpert = getMaxWeightExpert(evaluatedExperts);
			if (null != maxWeigthExpert) {
				// 把权重最高的专家放入选出的专家中
				choosenExperts.add(maxWeigthExpert);
			}
		}
		return choosenExperts;
	}

	private List<OuterExpert> randomChoose(
			List<OuterExpert> experts, int choosenNumber) {
		List<OuterExpert> randomChoosenExperts = new ArrayList<OuterExpert>();
		if (null == experts) {
			// need Log
			return null;
		}
		if (experts.size() <= choosenNumber) {
			// need Log
			return experts;
		} else {
			Set<Integer> set = new HashSet<Integer>();
			while (set.size() < choosenNumber) {
				set.add(Integer.valueOf((int) (Math.random() * choosenNumber)));// 产生0-1000的双精度随机数
			}
			for (Integer s : set) {
				randomChoosenExperts.add(experts.get(s));
			}
			return randomChoosenExperts;
		}
	}

	// 找出权重最大的专家，并将其权重设为-1
	private OuterExpert getMaxWeightExpert(
			List<OuterExpert> list) {
		int weight = -1;
		OuterExpert expert = null;
		for (OuterExpert e : list) {
			if (weight < e.getWeight()) {
				weight = e.getWeight();
				expert = e;
			}
		}
		// expert不为空，把最大权重设为1，下次不会再选出来了
		if (null != expert) {
			expert.setWeight(-1);
		}
		return expert;
	}

	// 算出每一位专家的权重
	private List<OuterExpert> evaluateWeigth(
			List<OuterExpert> experts) {
		for (OuterExpert e : experts) {
			if (-1 != e.getWeight()) {
				int weight = 0;
				if ("1".equals(e.getIS_985())) {
					weight += is985Weight;
				}
				if ("1".equals(e.getIS_GRADUATE())) {
					weight += isGraduateWeight;
				}
				if ("1".equals(e.getIS_211())) {
					weight += is211Weight;
				}
				int expertAge = Calendar.getInstance().get(Calendar.YEAR)
						- Integer.valueOf(e.getCSNY().substring(0, 4));
				if (expertAge >= ageLowerLimit && expertAge <= ageUpperLimit) {
					weight += ageWeight;
				}
				e.setWeight(weight);
			}
		}
		return experts;
	}

	private List<OuterExpert> getManageExperts(
			List<OuterExpert> experts) {
		List<OuterExpert> manageExperts = new ArrayList<OuterExpert>();
		for (OuterExpert e : experts) {
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

	private ExpertSelectionRuleDetail getDetailByName(String detailName, List<ExpertSelectionRuleDetail> expertSelectionRuleDetails) {
		for (ExpertSelectionRuleDetail detail : expertSelectionRuleDetails) {
			if (detailName.equals(detail.getItemName())) {
				return detail;
			}
		}
		return null;
	}

	/**
	 *  ruleDetail的英文名
		detail1  每组专家数：sameDisciplineSumLimit
		detail2  同单位专家数：sameUnitSumLimit
		detail3  专家评价学科数：disciplineNumber
		detail4  管理专家：manageExpertPercent
		detail5  高水平专家：highLevelExpert
		detail6  博士生导师：doctoralSupervisor
		detail7  硕士生导师：masterSupervisor
		detail8  985高校：985Expert
		detail9  研究生院高校：graduateSchoolExpert
		detail10 211高校：211Expert
		detail11 国家重点学科（含培育学科）专家优先：nationalKeyDiscipline
		detail12 博士一级学科专家优先：doctoralFirstLevelDiscipline
	 * @param expertSelectionRuleDetails
	 */
	private void parseDetailRestricts(
			List<ExpertSelectionRuleDetail> expertSelectionRuleDetails) {

		ExpertSelectionRuleDetail detail1 = getDetailByName("211Expert", expertSelectionRuleDetails);
		if (!detail1.getIsNecessary()) {
			is211Weight = 0;
		}
		ExpertSelectionRuleDetail detail2 = getDetailByName("graduateSchoolExpert", expertSelectionRuleDetails);
		if (!detail2.getIsNecessary()) {
			isGraduateWeight = 0;
		}
		ExpertSelectionRuleDetail detail3 = getDetailByName("985Expert", expertSelectionRuleDetails);
		if (!detail3.getIsNecessary()) {
			is985Weight = 0;
		}
		ExpertSelectionRuleDetail detail4 = getDetailByName("age", expertSelectionRuleDetails);
		if (!detail4.getIsNecessary()) {
			ageWeight = 0;
		} else {
			ageLowerLimit = Integer.valueOf(detail4.getRestrict1());
			ageUpperLimit = Integer.valueOf(detail4.getRestrict2());
		}

		ExpertSelectionRuleDetail detail5 = getDetailByName("manageExpertPercent", expertSelectionRuleDetails);
		if (!detail5.getIsNecessary()) {
			manageWeight = 0;
		} else {
			//manageExpertPercent = Double.valueOf(detail5.getRestrict1());
			// 模拟
			manageExpertPercent = Double.valueOf(0.2D);
		}

		ExpertSelectionRuleDetail detail6 = getDetailByName("sameDisciplineSumLimit", expertSelectionRuleDetails);
		if (detail6.getIsNecessary()) {
			sameDisciplineSumLowerLimit = Integer.valueOf(detail6
					.getRestrict1());
			sameDisciplineSumUpperLimit = Integer.valueOf(detail6
					.getRestrict2());
		}

		ExpertSelectionRuleDetail detail7 = getDetailByName("sameUnitSumLimit", expertSelectionRuleDetails);
		if (detail7.getIsNecessary()) {
			sameUnitSumLowerLimit = Integer.valueOf(detail7.getRestrict1());
			sameUnitSumUpperLimit = Integer.valueOf(detail7.getRestrict2());
		}
	}

	private List<Expert> afterSelect() {
		List<Expert> experts = new ArrayList<Expert>();
		for (OuterExpert expert : selectedExpert) {
			Expert expertSelected = new Expert();
			expertSelected.setDiscId(expert.getYJXKM());
			expertSelected.setExpertNumber(expert.getZJBH());
			expertSelected.setExpertName(expert.getZJXM());
			expertSelected.setExpertType(expert.getZJFL());
			experts.add(expertSelected);
		}
		// 集合清空重置
		selectedExpert = new ArrayList<OuterExpert>();
		for (Expert e : experts) {
			System.out.print(e.getExpertNumber()+":");
		}
		return experts;
	}

	@Override
	public List<OuterExpert> rangeExperts(
			List<OuterExpert> experts) {
		// 利用反射注入权重
		try {
			//SelectionRuleAndWeightInjection.injectWeight(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		print();
		experts = evaluateWeigth(experts);
		for (OuterExpert e : experts) {
			System.out.println(e.getWeight());
		}
		
		return null;
	}

}
