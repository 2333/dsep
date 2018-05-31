package com.dsep.service.expert.select.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.dsep.dao.dsepmeta.expert.rule.RuleDetailDao;
import com.dsep.dao.dsepmeta.expert.selection.ExpertDao;
import com.dsep.dao.dsepmeta.expert.selection.OuterExpertDao;
import com.dsep.domain.dsepmeta.expert.ExpertAndDiscUnitAndRule;
import com.dsep.domain.dsepmeta.expert.OuterExpert;
import com.dsep.entity.expert.Expert;
import com.dsep.entity.expert.ExpertSelectionRuleDetail;
import com.dsep.service.expert.select.util.OuterExpertsToTreeConvertorValve;

/**
 * DO NOT TRY TO PRESS SHIFT+F！
 * 
 * OR COMMENTS FROMAT WILL BE DESTROIED！ 
 * 
 * 在你改动或者查看遴选专家的代码时，
 * you'd better思考一下专家的List<List<List<Expert>>>"这棵树"
 */
public class TMPSelectExpertServiceImpl {
	
	private ExpertDao expertSelectedDao;
	//private SelectionDao selectionDao;
	private RuleDetailDao ruleDetailDao;
	private OuterExpertDao getExpertResultFromOtherDBDao;
	
	/**
	 * 选择步骤： 
	 * 1.清空原有的已选专家库 
	 * 2.获得输入条件、输入数据 
	 * 3.执行DAO遴选、输出 
	 * 4.保存到已选专家库中
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public void select(String ruleId) throws InstantiationException,
			IllegalAccessException {
		// 步骤1.
		// 在选择专家之前，先把已选专家库中的信息清空
		//expertSelectedDao.deleteAll();
		
		// 步骤2.
		// 获得输入条件、输入数据 
		ExpertAndDiscUnitAndRule expertAndDiscUnitAndRule = getInputData(ruleId);

		// 步骤3.
		// 输出信息：符合条件的专家
		List<Expert> expertsSelected = select(expertAndDiscUnitAndRule);

		// 步骤4.
		// 把遴选出来的专家添加到已选专家库中
		for (Expert expert : expertsSelected) {
			addSelectedExpert(expert);
		}
	}
	
	private ExpertAndDiscUnitAndRule getInputData(String ruleId) throws InstantiationException, IllegalAccessException {
		// 以下都是输入信息
		// 输入1.规则明细
		List<ExpertSelectionRuleDetail> ruleDetails =  ruleDetailDao
				.getAllRuleDetailsByRuleId(ruleId);

		// 输入2.从外部数据库来的20W+专家信息
		List<OuterExpert> experts = this.getExpertResultFromOtherDBDao.getAll();

		OuterExpertsToTreeConvertorValve.convertRawData2ListTree(experts);
		// 输入3.从本系统来的参评学科信息和参评学校信息，这里需要参评单位和授权单位
		// ！！无数据！！
		// 模拟！！！！
		Map<String, List<String>> discipline_units = imitateAndNeedBeDelete();
		
		// 封装成一个bean
		ExpertAndDiscUnitAndRule expertAndDiscUnitAndRule = new ExpertAndDiscUnitAndRule();
		expertAndDiscUnitAndRule.setRuleDetails(ruleDetails);
		expertAndDiscUnitAndRule.setExperts(experts);
		expertAndDiscUnitAndRule.setDiscipline_units(discipline_units);
		return expertAndDiscUnitAndRule;
	}
	
	
	/**
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
	private List<Expert> select(ExpertAndDiscUnitAndRule expertAndDiscUnitAndRule) {
		return null;
	}
	
	
	private void executeControlFlow1() {
		
	}
	
	private void executeControlFlow2() {
		
	}
	
	private void executeControlFlow3() {
		
	}
	
	private void executeControlFlow4() {
		
	}
	
	public void addSelectedExpert(Expert expertSelected) {
		// this.userDao.insertIntoUsersByExpert(expert);
		this.expertSelectedDao.addExpertSelected(expertSelected);
	}
	
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
	// Map<String, List>来源于参评的学科、学校
	 * {
	 *  [0809:(10006, 10007, 10008)],
	 *  [0835:(10001, 10003, 10006)]
	 * }
	 * 两者都有一层节点表示所在单位，再上一层节点是单位对应的学科
	 * 两个类树状数据结构的单位和专家节点值都是从小到大排列的
	 * 现在需要找到两个数据结构中相同学科
	 * 
	 */
	private void compareTreeAndFindSameDiscipline(
			List<List<List<OuterExpert>>> container,
			Map<String, List<String>> discipline_units) {
		
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
				
				// 找到了学科相同的集合，进行学校比对
				compareTreeAndFindSameUnit(discipline_units, mDiscipline, conDiscipline);
			}
		}
	}
	
	private void compareTreeAndFindSameUnit(
			Map<String, List<String>> discipline_units, String mDiscipline,
			List<List<OuterExpert>> conDiscipline) {
		/**
		 * Map<String, List<String>> discipline_units来源于参评的学科、学校
		 * {
		 *  [0809:(10006, 10007, 10008)],
		 *  [0835:(10001, 10003, 10006)]
		 * }
		 * 
		 * mUnit形如(10006, 10007, 10008)
		 */
		List<String> mUnits = discipline_units.get(mDiscipline);
		/**
		 * conUnit形如：
		 * [
		 *	 (老师1_10006_0835, 老师2_10006_0835, 老师3_10006_0835),
		 *   (老师3_10007_0835, 老师4_10007_0835)
		 * ]
		 */
		List<List<OuterExpert>> conUnits = conDiscipline;
		
		Iterator<List<OuterExpert>> conUnitsIter = conUnits
				.iterator();
		Iterator<String> mUnitsIter = mUnits.iterator();

		// 某一学科的所有参评单位下的专家
		List<List<OuterExpert>> expertsInAttendedUnit = new ArrayList<List<OuterExpert>>();
		// 某一学科的所有授权单位下的专家
		List<List<OuterExpert>> expertsInAuthorizedUnit = new ArrayList<List<OuterExpert>>();
		// 这和compareTreeAndFindSameDiscipline循环思路一样
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
			// 如果学校也一样，那么找出这个节点下的所有专家，把专家加入到一个集合中
			if (0 == Integer.valueOf(mUnit).compareTo(
					Integer.valueOf(conUnit.get(0).getXXDM()))) {
				// 现在假设所有学校都是参评学校，但以后肯定要分参评和授权，只不过现在没在数据
				expertsInAttendedUnit.add(conUnit);
				// expertsInAuthorizedUnit.add(xxx)
			}
		}

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private Map<String, List<String>> imitateAndNeedBeDelete() {
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
		return discipline_units;
	}
}