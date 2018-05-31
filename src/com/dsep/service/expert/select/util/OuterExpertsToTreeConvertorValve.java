package com.dsep.service.expert.select.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
//import org.jboss.logging.Logger;
//import org.mortbay.log.Log;


import com.dsep.domain.dsepmeta.expert.OuterExpert;

public class OuterExpertsToTreeConvertorValve {
	public static Logger logger1 = Logger.getLogger("selectLog");

	// RawData指的是：
	// 从数据库传来的某个特定学科下所有按序排列的学校的专家数据
	// 数据库中取的是：select * from xx where discId = xx order by unidId asc
	//  学校 | 专家
	// 10001 | l
	// 10001 | k
	// 10035 | t
	// 10038 | y
	// 10038 | u
	// 10839 | i
	// 10839 | b
	// 12041 | a
	// 30024 | z
	// 现在先把这个list处理一下，变成List<List<Expert>>类型
	// 里层的List<Expert> sameUnitExpertsContainer把相同学校的专家放在一起
	// 最外层container是一个根节点
	public static final List<List<OuterExpert>> call(
			List<OuterExpert> data) {
		// 计算时间
		long startTime = System.currentTimeMillis(); // 获取开始时间

		List<OuterExpert> sameUnitExpertsContainer = new ArrayList<OuterExpert>();

		List<List<OuterExpert>> container = new ArrayList<List<OuterExpert>>();

		Iterator<OuterExpert> iter = data.iterator();
		OuterExpert expert = iter.next();

		String XXDM = expert.getXXDM();

		if (!checkExpertInfo(expert)) {
			//若该专家有信息缺失则移动到下一个专家
			iter.next();
		}

		sameUnitExpertsContainer.add(expert);

		while (iter.hasNext()) {
			expert = iter.next();
			if (!checkExpertInfo(expert)) {
				//若该专家有信息缺失则移动到下一个专家
				iter.next();
			}
			String currentXXDM = expert.getXXDM();

			if (XXDM.equals(currentXXDM)) {
				sameUnitExpertsContainer.add(expert);
			} else {
				container.add(sameUnitExpertsContainer);
				XXDM = currentXXDM;
				sameUnitExpertsContainer = new ArrayList<OuterExpert>();
				sameUnitExpertsContainer.add(expert);
			}
		}
		// 装入最后一组相同学校的专家
		container.add(sameUnitExpertsContainer);

		long endTime = System.currentTimeMillis(); // 获取结束时间
		logger1.info("转化专家为树形结构的程序的运行时间： " + (endTime - startTime) + "ms");
		return container;
	}

	// 从数据库传来的20W条左右的数据是按照一级学科、学校排序的
	// 一级学科| 学校 | 专家
	// 0085 | 10035 | t
	// 0085 | 10038 | y
	// 0085 | 10038 | u
	// 1092 | 12041 | a
	// 1092 | 30024 | z
	// 1035 | 10839 | i
	// 1035 | 10839 | b
	// 1035 | 10001 | l
	// 1035 | 10001 | k
	// 现在先把这个list处理一下，变成List<List<List<Expert>>>类型
	// 最里层的List<Expert> sameUnitExpertsContainer把相同学校的专家放在一起
	// 中间层的List<List<Expert>> sameDisciplineExpertsContainer收集相同的学校List，组成相同的学科
	// 最外层container是一个根节点
	public static final List<List<List<OuterExpert>>> convertRawData2ListTree(
			List<OuterExpert> data) {

		// 计算时间
		long startTime = System.currentTimeMillis(); // 获取开始时间

		List<OuterExpert> sameUnitExpertsContainer = new ArrayList<OuterExpert>();

		List<List<OuterExpert>> sameDisciplineExpertsContainer = new ArrayList<List<OuterExpert>>();

		List<List<List<OuterExpert>>> container = new ArrayList<List<List<OuterExpert>>>();

		Iterator<OuterExpert> iter = data.iterator();
		OuterExpert expert = iter.next();

		// 获得第一个专家的一级学科码
		String YJXKM = expert.getYJXKM();
		String XXDM = expert.getXXDM();

		sameUnitExpertsContainer.add(expert);

		while (iter.hasNext()) {
			expert = iter.next();
			if (!checkExpertInfo(expert))
				continue;
			String currentYJXKM = expert.getYJXKM();
			String currentXXDM = expert.getXXDM();

			if (YJXKM.equals(currentYJXKM)) {
				if (XXDM.equals(currentXXDM)) {
					sameUnitExpertsContainer.add(expert);
				} else {
					XXDM = currentXXDM;
					sameDisciplineExpertsContainer
							.add(sameUnitExpertsContainer);
					sameUnitExpertsContainer = new ArrayList<OuterExpert>();
					sameUnitExpertsContainer.add(expert);
				}
			} else {
				YJXKM = currentYJXKM;
				XXDM = currentXXDM;
				// 这里顺序要写好，一定是先把sameUnit归入sameDiscipline
				// 再把sameDiscipline归入container
				sameDisciplineExpertsContainer.add(sameUnitExpertsContainer);
				container.add(sameDisciplineExpertsContainer);

				// 重新初始化sameDiscipline和sameUnit
				sameUnitExpertsContainer = new ArrayList<OuterExpert>();
				sameDisciplineExpertsContainer = new ArrayList<List<OuterExpert>>();
				// 同时要记得接收expert，这个写在sameUnit重新初始化之后即可
				sameUnitExpertsContainer.add(expert);
			}
		}
		// 装入最后一组相同学校的专家
		sameDisciplineExpertsContainer.add(sameUnitExpertsContainer);
		// 装入最后一组相同一级学科码的专家
		container.add(sameDisciplineExpertsContainer);

		long endTime = System.currentTimeMillis(); // 获取结束时间
		System.out.println("转化20W专家为树形结构的程序的运行时间： " + (endTime - startTime)
				+ "ms");
		return container;
	}

	private static boolean checkExpertInfo(OuterExpert e) {
		boolean flag = true;
		if (!StringUtils.isNotBlank(e.getZJBH())) {
			flag = false;
			logger1.info("专家" + e.getZJXM() + "编号丢失");
		}
		if (!StringUtils.isNotBlank(e.getXXDM())) {
			flag = false;
			logger1.info("专家" + e.getZJBH() + e.getZJXM() + "学校信息丢失");
		}
		if (!StringUtils.isNotBlank(e.getYJXKM())) {
			flag = false;
			logger1.info("专家" + e.getZJBH() + e.getZJXM() + "学科信息丢失");
		}
		/*if(!StringUtils.isNotBlank(e.getYJXKM2())){
			flag = false;
			logger1.info("专家" + e.getZJBH() + e.getZJXM() + "学科信息丢失");
		}*/
		if (!StringUtils.isNotBlank(e.getDZXX())) {
			flag = false;
			logger1.info("专家" + e.getZJBH() + e.getZJXM() + "邮箱信息丢失");
		}
		return flag;
	}
	
	/*新增方法，仅供重构测试*/
	public static final Map<String,Map<String,List<OuterExpert>>>
	    convertToMakeUpTree(List<OuterExpert> list){
		Map<String,Map<String,List<OuterExpert>>> finalTree = new HashMap<String,Map<String,List<OuterExpert>>>();
		Map<String,List<OuterExpert>> discTree = new HashMap<String,List<OuterExpert>>();
		List<OuterExpert> expList = new ArrayList<OuterExpert>();
		Iterator<OuterExpert> iter = list.iterator();
		OuterExpert expert = iter.next();
		
		while(!checkExpertInfo(expert)||"0".equals(expert.getYJXKM2())||"\t".equals(expert.getYJXKM2())){
			//若该专家有信息缺失则移动到下一个专家
			expert = iter.next();
		}
		String XXDM = expert.getXXDM();
		String YJXKM2 = expert.getYJXKM2();
		System.out.println(XXDM + "学校" + YJXKM2 + "学科");
		while(iter.hasNext()){
			OuterExpert e = iter.next();
			while(!checkExpertInfo(e)){
				//若该专家有信息缺失则移动到下一个专家
				e = iter.next();
			}
			System.out.println(e.getXXDM() + "学校" + e.getYJXKM2() + "学科");
			if(e.getXXDM().equals(XXDM)&&e.getYJXKM2().equals(YJXKM2)){
				expList.add(e);
				continue;
			}else if(!e.getXXDM().equals(XXDM)&&e.getYJXKM2().equals(YJXKM2)){
				discTree.put(XXDM, expList);
				expList = new ArrayList<OuterExpert>();
				XXDM = e.getXXDM();
				expList.add(e);
				continue;
			}else if(!e.getYJXKM2().equals(YJXKM2)){
				discTree.put(XXDM, expList);
				finalTree.put(YJXKM2, discTree);
				discTree = new HashMap<String,List<OuterExpert>>();
				expList = new ArrayList<OuterExpert>();
				expList.add(e);
				YJXKM2 = e.getYJXKM2();
				XXDM = e.getXXDM();
				continue;
			}
				
		}
		return finalTree;
	}
	/*新增方法，仅供重构测试*/
}
