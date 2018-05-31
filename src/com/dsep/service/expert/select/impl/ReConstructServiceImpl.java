package com.dsep.service.expert.select.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.dsep.domain.dsepmeta.expert.OuterExpert;
import com.dsep.entity.expert.EvalBatch;
import com.dsep.entity.expert.Expert;
import com.dsep.entity.expert.ExpertSelectionRuleDetail;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;
import com.dsep.service.expert.rule.RuleMetaService;
import com.dsep.service.expert.select.ReConstructService;
import com.dsep.service.expert.select.SelectionStrategyDelegationService;
import com.dsep.service.expert.select.util.OuterExpertsToTreeConvertorValve;
import com.dsep.service.expert.select.util.SelectionRuleWeightInjectionValve;
import com.dsep.service.expert.select.util.UnitsValve;
import com.dsep.service.expert.select.util.UserConfigWeightInjectionValve;
import com.dsep.util.expert.ExpertEvalCurrentStatus;
import com.dsep.util.expert.email.MD5Util;

public class ReConstructServiceImpl implements ReConstructService{
	public static Logger logger1 = Logger.getLogger("selectLog");

	private RuleMetaService ruleMetaService;
	private DisciplineService disciplineService;
	private UnitService unitService;
	
	private Map<String,Integer> lackMap = new HashMap<String,Integer>();

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
	//存放当前学科可以提供补选的专家
	Map<String,List<OuterExpert>> makeUpMap = new HashMap<String,List<OuterExpert>>();

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
	public List<Object> select(List<OuterExpert> experts,
			List<ExpertSelectionRuleDetail> ruleDetails, EvalBatch evalBatch,
			String currentDisc, Map<String,List<OuterExpert>> makeUpMap) {
		List<Object> result = new ArrayList<Object>();
		// 通过反射给基本参数赋值
		basicArgsConfig(ruleDetails);
		
		this.makeUpMap = makeUpMap;

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
			traversalNodesAndSelect(container, units);
			if(selectedExpert.size()>=sameDisciplineSumLowerLimit&&
					selectedExpert.size()<=sameDisciplineSumUpperLimit)
				logger1.info(selectedExpert.get(0).getYJXKM() 
						+ "学科遴选结束\r\n");
			else if(selectedExpert.size()<sameDisciplineSumLowerLimit){
				logger1.info("注意");
				logger1.info(selectedExpert.get(0).getYJXKM() 
						+ "学科距离该学科人数下限相差" 
						+ (sameDisciplineSumLowerLimit-selectedExpert.size())
						+ "人\r\n");
			}
			result.add(wrapExperts(evalBatch, currentDisc));
			result.add(this.makeUpMap);
			result.add(this.lackMap);
			//该学科遴选结束，重置所有全局变量
			this.selectedExpert = new ArrayList<OuterExpert>(); 
			this.makeUpMap = new HashMap<String,List<OuterExpert>>();
			this.lackMap = new HashMap<String,Integer>();
			
			// 把traversalNodesAndSelect一个学科所有学校遴选出的专家封装好
			return result;
		} else {
			this.makeUpMap = new HashMap<String,List<OuterExpert>>();
			this.lackMap = new HashMap<String,Integer>();
			// 返回空List
			return new ArrayList<Object>();
		}

	};
	
	private void basicArgsConfig(
			List<ExpertSelectionRuleDetail> expertSelectionRuleDetails) {
		SelectionRuleWeightInjectionValve.call(this, ruleMetaService);
		UserConfigWeightInjectionValve.call(this, expertSelectionRuleDetails);
	}

	public void traversalNodesAndSelect(
			List<List<OuterExpert>> container, String[] unitsArr) {
		logger1.info("开始寻找学校节点");
		// 需要以String[]为主导找，因为它的序列是有意义的
		for (String unit : unitsArr) {
			// 每次container都从头开始遍历
			Iterator<List<OuterExpert>> iter = container.iterator();
			while (iter.hasNext()) {
				List<OuterExpert> list = iter.next();
				if (list.get(0).getXXDM().equals(unit)) {
					List<OuterExpert> experts = new ArrayList<OuterExpert>();
					// 对特定学校节点进行遴选并存储到全局变量selectedExpert中
					if(makeUpMap!=null){
						experts = selectSpecificUnitExperts(list, makeUpMap.get(unit));
					}else experts =  selectSpecificUnitExperts(list, null);
					selectedExpert.addAll(experts);
					//比较目前所选专家数量和同学科专家上限数，如果超出上限，则在随机选出上限数量的专家
					if(selectedExpert.size()>sameDisciplineSumUpperLimit){
						logger1.info("学科为" + selectedExpert.get(0).getYJXKM() + "的专家超出上限,"
								+ "在其中随机去除" 
								+ (selectedExpert.size()-sameDisciplineSumUpperLimit)
								+ "名专家\r\n");
						selectedExpert = random(selectedExpert,
								selectedExpert.size()-sameDisciplineSumUpperLimit);
					}
						
					break;
				}
			}
		}

	}
	
	private List<OuterExpert> selectSpecificUnitExperts(
			List<OuterExpert> list ,List<OuterExpert> makeUpList) {
		List<OuterExpert> selectedExpert = new ArrayList<OuterExpert>();
		int sum = 0;
		logger1.info("学科：" + list.get(0).getYJXKM() + "，学校："
				+ list.get(0).getXXDM());
		logger1.info("学校的下限是：" + sameUnitSumLowerLimit + "，上限是："
				+ sameUnitSumUpperLimit + "\r\n");
		// 首先进行初步遴选
		// 即(选择参评单位数)乘以(每个单位的专家数下限)
		logger1.info("开始执行步骤");
		selectedExpert = choose(list, sameUnitSumUpperLimit);
		
		sum += selectedExpert.size();
		// 如果已经把人都选出来了，那么直接结束这个学科的遴选
		// 如果选出来的人少了，那么将缺失信息封装进lackMap
		// 如果选出来的人多了，那么随机选取其中一部分人
		if (sum >= sameUnitSumLowerLimit
				&& sum <= sameUnitSumUpperLimit) {
			logger1.info("遴选结束\r\n\r\n");
			if(selectedExpert.size()>0&&makeUpList!=null&&makeUpList.size()>0){
				List<OuterExpert> temp = new ArrayList<OuterExpert>();
				temp.addAll(makeUpList);
				for(OuterExpert e1:selectedExpert){
					for(OuterExpert e2:makeUpList){
						if(e1.getZJBH().equals(e2.getZJBH())){
							logger1.info("补选专家树中去除专家" + e2.getZJBH() + e2.getZJXM());
							temp.remove(e2);
						}
					}
				}
				makeUpMap.put(selectedExpert.get(0).getXXDM(), temp);
			}
			logger1.info("学科为：" + selectedExpert.get(0).getYJXKM() + "的" + selectedExpert.get(0).getXXDM() 
					+ "学校，总共有专家" + list.size() + "人，遴选出" + selectedExpert.size()
					+ "人\r\n");
		} else if (sum < sameUnitSumLowerLimit) {
			logger1.info("毕遴选结束\r\n");
			// 测试代码
			if(selectedExpert.size()>0&&makeUpList!=null&&makeUpList.size()>0){
				List<OuterExpert> temp = new ArrayList<OuterExpert>();
				temp.addAll(makeUpList);
				for(OuterExpert e1:selectedExpert){
					for(OuterExpert e2:makeUpList){
						if(e1.getZJBH().equals(e2.getZJBH())){
							logger1.info("补选专家树中去除专家" + e2.getZJBH() + e2.getZJXM());
							temp.remove(e2);
						}
					}
				}
				makeUpMap.put(selectedExpert.get(0).getXXDM(), temp);
			}
			int lack = sameUnitSumLowerLimit - selectedExpert.size();
			lackMap.put(selectedExpert.get(0).getXXDM(), lack);
			logger1.info("学科为：" + selectedExpert.get(0).getYJXKM() + "的" + selectedExpert.get(0).getXXDM() 
					+ "学校，总共有专家" + list.size() + "人，遴选出" + selectedExpert.size()
					+ "人,还需要补选" + lack + "人\r\n");
		}
		return selectedExpert;
	}
	
	// 把ExpertInfoFromOtherDB的信息赋给ExpertSelected
	private List<Expert> wrapExperts(EvalBatch evalBatch,
			String currentDisc) {
		List<Expert> experts = new ArrayList<Expert>();
		//logger1.info("开始转换为本地数据库格式");
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
		//logger1.info("本地数据库格式转换完毕\r\n\r\n\r\n");
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
		
		if(manageExperts.size()>0){
			logger1.info("其中管理专家总共有" + manageExperts.size() + "人，他们分别是:");
			for (OuterExpert e : manageExperts) {
				logger1.info(e.getZJBH() + e.getZJXM());
				for(int i=0;i<experts.size();i++){
					if(experts.get(i).getZJBH().equals(e.getZJBH())){
						experts.set(i, e);
						logger1.info("将管理专家" + e.getZJBH() + e.getZJXM() + "的权重置为-1");
					}
				}
			}
			logger1.info("\r\n");
		}else{
			logger1.info(experts.get(0).getXXDM() + "学校" + experts.get(0).getYJXKM() + "学科"
					+ "没有管理专家，由普通专家顶替");
			commonExpertsNumber += manageExperts.size();
		}
		
		// 从管理专家中随机选出了manageExpertsNumber个管理专家
		List<OuterExpert> choosenExperts = randomChoose(
				manageExperts, manageExpertsNumber);
		
		if(manageExperts.size()<manageExpertsNumber){
			logger1.info("该学科管理专家数目达不到要求，由普通专家顶替\r\n");
			commonExpertsNumber += manageExpertsNumber - manageExperts.size(); 
		}

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

		//logger1.info("step1对该学校选择的专家总共有：" + choosenExperts.size() + "\r\n");

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
			logger1.info(expert.getZJXM() + "的权重是：" + expert.getWeight());
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
			//logger1.info("计算专家" + e.getZJBH() + e.getZJXM() + "的权重");
			if (-1 != e.getWeight()) {
				int weight = 0;
				if ("1".equals(e.getIS_985())) {
					weight += is985Weight;
					//logger1.info("所在院校是985，权重变为" + weight);
				}
				if ("1".equals(e.getIS_GRADUATE())) {
					weight += isGraduateWeight;
					//logger1.info("所在院校有研究生院，权重变为" + weight);
				}
				if ("1".equals(e.getIS_211())) {
					weight += is211Weight;
					//logger1.info("所在院校是211，权重变为" + weight);
				}
				if ("1".equals(e.getDSLBM())) {
					weight += masSuperWeight;
					//logger1.info("该专家是硕导，权重变为" + weight);
				}
				if ("2".equals(e.getDSLBM())) {
					weight += docSuperWeight;
					//logger1.info("该专家是博导，权重变为" + weight);
				}
				int expertAge = Calendar.getInstance().get(Calendar.YEAR)
						- Integer.valueOf(e.getCSNY().substring(0, 4));
				//logger1.info("该专家年龄为" + expertAge);
				if (expertAge >= ageLowerLimit && expertAge <= ageUpperLimit) {
					weight += ageWeight;
					//logger1.info("年龄符合条件，权重变为" + weight);
				}
				e.setWeight(weight);
				//logger1.info("该专家最终权重为" + weight + "\r\n");
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
	
	@Override
	public List<Object> makeUpExperts(EvalBatch batch,String discId,
			Map<String,List<OuterExpert>> makeUpMap,Map<String,Integer> lackMap){
		List<Object> result = new ArrayList<Object>();
		this.selectedExpert = new ArrayList<OuterExpert>(); 
		for(String unit:lackMap.keySet()){
			int temp = 0;
			List<OuterExpert> exps = makeUpMap.get(unit);
			int num = lackMap.get(unit);
			logger1.info("开始进行" + unit + "学校的补选");
			logger1.info("该学校需补选" + num + "人");
			if(exps!=null&&num!=0){
				List<Object> objs = makeUp(exps,num);
				selectedExpert.addAll((List<OuterExpert>) objs.get(0));
				for(OuterExpert e:selectedExpert)
					logger1.info("从待补选专家中去除专家" + e.getZJBH() + e.getZJXM());
				temp = (Integer)objs.get(1);
				lackMap.put(unit,temp);
				logger1.info(unit + "学校补选结束，补选出专家" + temp + "人");
				logger1.info("-------------------------");
			}
		}
		result.add(wrapExperts(batch,discId));
		result.add(lackMap);
		return result;
	}
	
	private List<Object> makeUp(List<OuterExpert> experts,int num){
		List<Object> result = new ArrayList<Object>();
		List<OuterExpert> choosenMakeUp = choose(experts,num);
		result.add(choosenMakeUp);
		result.add(choosenMakeUp.size());
		return result;
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
