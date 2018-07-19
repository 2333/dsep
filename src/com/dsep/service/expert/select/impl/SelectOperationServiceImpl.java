package com.dsep.service.expert.select.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.dsep.domain.dsepmeta.expert.OuterExpert;
import com.dsep.entity.expert.EvalBatch;
import com.dsep.entity.expert.Expert;
import com.dsep.entity.expert.ExpertSelectionRuleDetail;
import com.dsep.service.expert.select.SelectionStrategyDelegationService;
import com.dsep.service.expert.select.util.OuterExpertsToTreeConvertorValve;

public class SelectOperationServiceImpl /*implements
		DiscAndUnitDivisionSelectionStrategyService*/ {

	//	private SpecificDisExpertsService specificDisExpertsService;
	//	
	//	public SpecificDisExpertsService getSpecificDisExpertsService() {
	//		return specificDisExpertsService;
	//	}
	//
	//	public void setSpecificDisExpertsService(
	//			SpecificDisExpertsService specificDisExpertsService) {
	//		this.specificDisExpertsService = specificDisExpertsService;
	//	}

	List<OuterExpert> selectedExpert = new ArrayList<OuterExpert>();
	// 参评单位代码
	List<String> attendedUnit = new ArrayList<String>();
	// 授权单位代码
	List<String> authorizedUnit = new ArrayList<String>();

	public List<Expert> discAndUnitDivisionSelect(
			List<OuterExpert> list, Map<String, List<String>> m,
			List<ExpertSelectionRuleDetail> expertSelectionRuleDetails) {
		List<List<List<OuterExpert>>> container = beforeSelect(list);
		select(null, container, expertSelectionRuleDetails);
		return afterSelect();
	}

	private List<List<List<OuterExpert>>> beforeSelect(
			List<OuterExpert> list) {
		return OuterExpertsToTreeConvertorValve.convertRawData2ListTree(list);
	}

	private void select(Map<String, List<String>> m,
			List<List<List<OuterExpert>>> container,
			List<ExpertSelectionRuleDetail> expertSelectionRuleDetails) {
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
				/*selectedExpert.addAll(specificDisExpertsService.selectSpecificDiscExperts(expertsInAttendedUnit,
						expertsInAuthorizedUnit,expertSelectionRuleDetails));*/
			}
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
			// 插入专家的emial地址
			String email = expert.getDZXX();
			// 因为有些邮箱是如下所示，有两个
			// zhanglp418@163.com,xfdzyn@sohu.com
			// 所以需要拆分
			String[] addrs = email.split("\\,");
			// 这里取第一个邮箱
			/*expertSelected.setExpertEmail(addrs[0]);
			expertSelected.setValidateCode(MD5Util.encode2hex(addrs[0]));
			experts.add(expertSelected);*/
		}
		// 集合清空重置
		selectedExpert = new ArrayList<OuterExpert>();
		for (Expert e : experts) {
			System.out.print(e.getExpertNumber() + ":");
		}
		return experts;
	}

	//@Override
	public List<Expert> select(List<OuterExpert> allExperts,
			Map<String, List<String>> DiscsAndUnitsInfo,
			List<ExpertSelectionRuleDetail> expertSelectionRuleDetails,
			EvalBatch evalBatch) {
		// TODO Auto-generated method stub
		return null;
	}

}
