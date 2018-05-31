package com.dsep.service.expert.select.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.dsep.domain.dsepmeta.expert.ExpertNumberGroupByUnitId;
import com.dsep.domain.dsepmeta.expert.OuterExpert;
import com.dsep.entity.expert.EvalBatch;
import com.dsep.entity.expert.Expert;
import com.dsep.entity.expert.ExpertSelectionRuleDetail;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;
import com.dsep.service.expert.rule.RuleMetaService;
import com.dsep.service.expert.select.SelectionStrategyDelegationService;
import com.dsep.service.expert.select.util.OuterExpertsToTreeConvertorValve;
import com.dsep.service.expert.select.util.SelectionRuleWeightInjectionValve;
import com.dsep.service.expert.select.util.UnitsValve;
import com.dsep.service.expert.select.util.UserConfigWeightInjectionValve;
import com.dsep.util.expert.ExpertEvalCurrentStatus;
import com.dsep.util.expert.email.MD5Util;

public class UnitDivisionSelectionStrategyServiceImpl implements
		SelectionStrategyDelegationService {
	public static Logger logger1 = Logger.getLogger("selectLog");

	private RuleMetaService ruleMetaService;
	private DisciplineService disciplineService;
	private UnitService unitService;

	//=======================基本参数 basic args=========================
	private int is211Weight 					= 0, 
				isGraduateWeight 				= 0, 
				is985Weight 					= 0,
				ageWeight 						= 0, 
				docSuperWeight 					= 0, 
				masSuperWeight 					= 0;

	private int ageLowerLimit 					= 0, 
				ageUpperLimit 					= 0;

	private int sameDisciplineSumLowerLimit 	= 0,
				sameDisciplineSumUpperLimit 	= 0;

	private int sameUnitSumLowerLimit 			= 0, 
				sameUnitSumUpperLimit 			= 0;

	private double manageExpertPercent 			= 0D;

	// 默认不区分管理专家与非管理专家
	private boolean needChooseManageExpert 		= false;
	
	// 默认不设置专家评价学科数
	private boolean needSetDisciplineNumber		= false;

	// 选择范围,默认都是false
	private boolean chooseFromOnlyAttend 		= false,
					chooseFromAttendAndAccredit = false, 
					chooseFromNotAttend 		= false,
					chooseFromAllUnits 			= false;
	//=======================基本参数 basic args=========================

	// 全局变量,用来存放本次select方法调用遴选出的专家
	List<OuterExpert> selectedExpert = new ArrayList<OuterExpert>();

	/**
	 * 这个select的实现
	 * 是对SelectServiceImpl传递来的某个学科的所有学校的专家学校进行分类
	 * 把2专家变成一个树形结构
	 * 对每个树的子节点进行遴选
	 * 
	 * 算法的第一步：
	 * basicArgsConfig 首先初始化优先条件的默认权重，
	 * 然后根据用户前台的勾选值修正基本参数
	 * 
	 * 算法的第二步：
	 * 利用ExpertsFromOtherDB2TreeConvertorValve把专家按照学校变成一棵树
	 * 
	 * 算法的第三步：
	 * 利用UnitsValve把所有的学校按照211，985，博士以及学科等进行优先排序
	 * 
	 * 算法的第四步：
	 * traversalNodesAndSelect，将第二步的树的学校节点和第三步的学校数据进行比对，
	 * 相同node下的教师子树进行优先级遴选
	 */
	@Override
	public List<Expert> select(List<OuterExpert> experts,
			List<ExpertSelectionRuleDetail> ruleDetails, EvalBatch evalBatch,
			List<ExpertNumberGroupByUnitId> selectedNumberGroupByUnitId,
			String currentDisc) {
		// 通过反射给基本参数赋值
		basicArgsConfig(ruleDetails);

		// 把专家转变成树形结构
		List<List<OuterExpert>> container = OuterExpertsToTreeConvertorValve
				.call(experts);

		// 根据用户配置和权重排序的学校信息
		String[] units = UnitsValve.call(chooseFromAllUnits,
				chooseFromAttendAndAccredit, chooseFromNotAttend,
				chooseFromOnlyAttend, currentDisc, unitService,
				disciplineService);
		if (units != null) {
			// 通过对比参评学校，找到树形结构相同学校的节点，其内部会调用选择方法
			traversalNodesAndSelect(container, units, selectedNumberGroupByUnitId);

			// 把traversalNodesAndSelect一个学科所有学校遴选出的专家封装好
			return wrapExperts(evalBatch, currentDisc);
		} else {
			// 返回空List
			return new ArrayList<Expert>();
		}

	};
	
	private void basicArgsConfig(
			List<ExpertSelectionRuleDetail> expertSelectionRuleDetails) {
		SelectionRuleWeightInjectionValve.call(this, ruleMetaService);
		UserConfigWeightInjectionValve.call(this, expertSelectionRuleDetails);
	}

	public void traversalNodesAndSelect(List<List<OuterExpert>> container,
			String[] unitsArr, List<ExpertNumberGroupByUnitId> selectedNums) {

		logger1.info("开始寻找学校节点");

		// 有两个数据结构，分别是List<List<E>>和String[]类型
		// List<List<<E>>来源于某个学科的专家
		// String[]来源于参评配置选择的学校
		// 现在需要找到两个数据结构中相同学校的节点

		// 需要以String[]为主导找，因为它的序列是有意义的
		for (String unit : unitsArr) {
			// 每次container都从头开始遍历
			Iterator<List<OuterExpert>> iter = container.iterator();
			while (iter.hasNext()) {
				List<OuterExpert> list = iter.next();
				if (list.get(0).getXXDM().equals(unit)) {
					// 获得本学校已经选出的专家人数，用于补选
					int selectedNum = 0;
					for (ExpertNumberGroupByUnitId ele : selectedNums) {
						if (ele.getUnitId().equals(unit)) {
							selectedNum = ele.getNum();
							break;
						}
					}
					// 对特定学校节点进行遴选并存储到全局变量selectedExpert中
					List<OuterExpert> experts = selectSpecificUnitExperts(list,
							sameUnitSumLowerLimit - selectedNum,
							sameUnitSumUpperLimit - selectedNum);
					selectedExpert.addAll(experts);
					break;
				}
			}
		}

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
	private List<OuterExpert> selectSpecificUnitExperts(List<OuterExpert> list,
			Integer unitSumLowerLimit, Integer unitSunUpperLimit) {
		List<OuterExpert> selectedExpert = new ArrayList<OuterExpert>();
		
		// 补选时，人员已经超过了下限，则直接返回
		if (unitSumLowerLimit <= 0) {
			return selectedExpert;
		}
		
		// doStep1设置为true是因为step1必定执行
		// doStep2~4设置为false是因为它们执行与否要看前一个step的结果
		boolean doStep1 = true, 
				doStep2 = false, 
				doStep3 = false, 
				doStep4 = false;
		// step1Experts保存每个学科的每个学校的一批专家
		List<OuterExpert> step1EveryUnitExperts = new ArrayList<OuterExpert>();
		// step1AllExperts保存每个学科的所有学校的专家
		List<OuterExpert> step1AllUnitsExperts = new ArrayList<OuterExpert>();
		// step1Sum保存所有学校的专家
		int step1Sum = 0;

		int sum = 0;
		// ================step1=================
		if (doStep1) {

			logger1.info("学科为：" + list.get(0).getYJXKM() + "，学校："
					+ list.get(0).getXXDM());
			logger1.info("学校的下限是：" + unitSumLowerLimit + "，上限是："
					+ unitSunUpperLimit + "\r\n");
			// 首先进行步骤1的选择
			// 即(选择参评单位数)乘以(每个单位的专家数下限)
			logger1.info("开始执行步骤");

			step1EveryUnitExperts = choose(list, unitSumLowerLimit);
			OuterExpert e = list.get(0);

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

	// 把ExpertInfoFromOtherDB的信息赋给ExpertSelected
	private List<Expert> wrapExperts(EvalBatch evalBatch,
			String currentDisc) {
		List<Expert> experts = new ArrayList<Expert>();
		logger1.info("开始转换为本地数据库格式");
		for (OuterExpert expert : selectedExpert) {
			System.out.println(expert.getZJXM());
			Expert expertSelected = new Expert();
			expertSelected.setDiscId(expert.getYJXKM());
			expertSelected.setDiscId2(expert.getYJXKM2());
			// 这里不能setDiscId2,DiscId2只有是专家以一级学科码2的身份被遴选出来是才有用
			//expertSelected.setId(GUID.get());
			if (currentDisc.equals(expert.getYJXKM())) {
				expertSelected.setUseDiscId(true);
				expertSelected.setRealDiscId(expert.getYJXKM());
			} else if (currentDisc.equals(expert.getYJXKM2())) {
				expertSelected.setUseDiscId(false);
				expertSelected.setRealDiscId(expert.getYJXKM2());
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
					.toInt());
			expertSelected.setRemark(null);
			expertSelected.setEvalBatch(evalBatch);
			evalBatch.getExperts().add(expertSelected);
			experts.add(expertSelected);

		}
		// 集合清空重置
		selectedExpert = new ArrayList<OuterExpert>();
		logger1.info("本地数据库格式转换完毕\r\n\r\n\r\n");
		return experts;
	}

	// 一个学科的一个学校中，管理专家不够通过非管理专家补齐，非管理专家不够通过管理专家贾补齐，并报告
	private List<OuterExpert> choose(
			List<OuterExpert> experts, int number) {
		logger1.info("本组专家数量为" + experts.size());
		
		int manageExpertsNumber = 0;
		if (needChooseManageExpert) {
			manageExpertsNumber = (int) (number * manageExpertPercent);
		}
		
		int commonExpertsNumber = number - manageExpertsNumber;
		logger1.info("所需管理专家的数量为" + manageExpertsNumber);
		logger1.info("所需普通专家的数量为" + commonExpertsNumber);
		// 获得所有管理专家
		List<OuterExpert> manageExperts = getManageExperts(experts);

		logger1.info("其中管理专家总共有" + manageExperts.size() + "人，他们分别是:");
		for (OuterExpert e : manageExperts) {
			logger1.info(e.getZJBH() + e.getZJXM());
		}
		logger1.info("\r\n");

		// 从管理专家中随机选出了manageExpertsNumber个管理专家
		List<OuterExpert> choosenExperts = randomChoose(
				manageExperts, manageExpertsNumber);

		logger1.info("每组下限" + number + "人 x 管理专家比例" + manageExpertPercent * 100
				+ "%=" + manageExpertsNumber + "人");
		logger1.info("按比例实际选出的管理专家有" + choosenExperts.size() + "人\r\n");

		// 对所有非管理专家进行权重计算
		List<OuterExpert> evaluatedExperts = evaluateWeigth(experts);

		logger1.info("该组专家权重计算完毕");

		// 挑选出commonExpertsNumber个权重最大的专家
		for (int i = 0; i < commonExpertsNumber; i++) {
			OuterExpert maxWeigthExpert = getMaxWeightExpert(evaluatedExperts);
			if (null != maxWeigthExpert) {
				// 把权重最高的专家放入选出的专家中
				choosenExperts.add(maxWeigthExpert);
			}
		}

		logger1.info("step1对该学校选择的专家总共有：" + choosenExperts.size() + "\r\n");

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
			logger1.info("非管理专家按权重排序：" + expert.getZJXM() + "的权重是："
					+ expert.getWeight());

			expert.setWeight(-1);
		}
		return expert;
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

	// 算出每一位专家的权重
	private List<OuterExpert> evaluateWeigth(
			List<OuterExpert> experts) {
		for (OuterExpert e : experts) {
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

	private List<OuterExpert> random(
			List<OuterExpert> list, int removeTimes) {
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
