package com.dsep.service.expert.select.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dsep.dao.dsepmeta.expert.selection.ExpertDao;
import com.dsep.dao.dsepmeta.expert.selection.OuterExpertDao;
import com.dsep.domain.dsepmeta.expert.ExpertNumberGroupByUnitId;
import com.dsep.domain.dsepmeta.expert.OuterExpert;
import com.dsep.entity.expert.Expert;
import com.dsep.entity.expert.ExpertSelectionRule;
import com.dsep.entity.expert.ExpertSelectionRuleDetail;
import com.dsep.service.expert.select.ExpertUtilService;
import com.dsep.util.expert.ExpertEvalCurrentStatus;

public class ExpertUtilServiceImpl implements ExpertUtilService {
	private ExpertDao expertDao;
	private OuterExpertDao outerExpertDao;

	@Override
	public List<String> getEmailAddrs(String expertId)
			throws InstantiationException, IllegalAccessException {
		List<Expert> experts = new ArrayList<Expert>();
		experts.add(expertDao.get(expertId));

		/*
		 * List<ExpertInfoFromOtherDB> expertsFromOtherDB =
		 * getExpertResultFromOtherDBDao .getEmailAddr(experts);
		 */
		List<OuterExpert> expertsFromOtherDB = null;
		List<String> emailAddrs = new ArrayList<String>();

		for (OuterExpert e : expertsFromOtherDB) {
			// 因为有些邮箱是如下所示，有两个
			// zhanglp418@163.com,xfdzyn@sohu.com
			// 所以需要拆分
			String[] addrs = e.getDZXX().split("\\,");
			for (String addr : addrs) {
				emailAddrs.add(addr);
			}
		}
		return emailAddrs;
	}

	@Override
	public Map<Expert, List<String>> getNotMailEmailAddrs() {
		List<Expert> experts = expertDao.getExpertsNotMailed();
		Map<Expert, List<String>> expertsAndEmails = new HashMap<Expert, List<String>>();
		for (Expert expert : experts) {
			List<String> emailAddrs = new ArrayList<String>();
			emailAddrs.add(expert.getExpertEmail1());
			if (null != expert.getExpertEmail2()) {
				emailAddrs.add(expert.getExpertEmail2());
			}
			if (null != expert.getExpertEmail3()) {
				emailAddrs.add(expert.getExpertEmail3());
			}
			expertsAndEmails.put(expert, emailAddrs);
		}
		return expertsAndEmails;
	}

	@Override
	public Map<Expert, List<String>> getNotReplyEmailAddrs() {
		List<Expert> experts = expertDao.getExpertsNotReplied();
		Map<Expert, List<String>> expertsAndEmails = new HashMap<Expert, List<String>>();
		for (Expert expert : experts) {
			List<String> emailAddrs = new ArrayList<String>();
			emailAddrs.add(expert.getExpertEmail1());
			if (null != expert.getExpertEmail2()) {
				emailAddrs.add(expert.getExpertEmail2());
			}
			if (null != expert.getExpertEmail3()) {
				emailAddrs.add(expert.getExpertEmail3());
			}
			expertsAndEmails.put(expert, emailAddrs);
		}
		return expertsAndEmails;
	}

	@Override
	public String showExpertStatistics(ExpertSelectionRule rule, String batchId) {
		List<ExpertSelectionRuleDetail> ruleDetails = rule.getExpertSelectionRuleDetails();
		ExpertSelectionRuleDetail sameDisciplineSumLimit = null;
		for (ExpertSelectionRuleDetail d : ruleDetails) {
			// 目前sameDisciplineSumLimit的序号是1
			if (d.getSequ().equals("1")) {
				sameDisciplineSumLimit = d;
				break;
			}
		}
		int lowerNum = Integer.valueOf(sameDisciplineSumLimit.getRestrict1());
		int upperNum = Integer.valueOf(sameDisciplineSumLimit.getRestrict2());
		Map<List<String>, List<String>> map = expertDao
				.getStatisticsInfo(batchId);
		Set set = map.keySet();
		Iterator iter = set.iterator();
		List<String> discs = null, nums = null;
		if (iter.hasNext()) {
			discs = (List<String>) iter.next();
			nums = map.get(discs);

		}
		List<Integer> nums2 = new ArrayList<Integer>();
		for (int i = 0; i < nums.size(); i++) {
			Object ob = nums.get(i);
			int t = Integer.parseInt(ob.toString());

			nums2.add(t);
		}
		String discStr = "[";
		for (String disc : discs) {
			discStr += ("'" + disc + "',");
		}
		discStr = discStr.substring(0, discStr.length() - 1) + "]";
		String lowerStr = ",{name:'标准人数下限',data:[";
		String upperStr = ",{name:'标准人数上限',data:[";
		String actualStr = ",{name:'实际人数',data:[";
		for (Integer i : nums2) {
			lowerStr += (lowerNum + ",");
			upperStr += (upperNum + ",");
			actualStr += (i + ",");
		}
		lowerStr = lowerStr.substring(0, lowerStr.length() - 1) + "]}";
		upperStr = upperStr.substring(0, upperStr.length() - 1) + "]}";
		actualStr = actualStr.substring(0, actualStr.length() - 1) + "]}";

		return "[" + discStr + lowerStr + upperStr + actualStr + "]";
	}

	@Override
	public List<ExpertNumberGroupByUnitId> countExpertNumbersByConditionsGroupByUnitId(
			String discId, String batchId, List<ExpertEvalCurrentStatus> statuses) {
		List<Integer> statuses2 = new ArrayList<Integer>();
		for (ExpertEvalCurrentStatus status : statuses) {
			statuses2.add(status.toInt());
		}
		return expertDao.countExpertNumbersByConditionsGroupByUnitId(discId,
				batchId, statuses2);
	}

	/*
	 * @Override public List<String> getAttendExpertsUnits() throws
	 * InstantiationException, IllegalAccessException { List<ExpertSelected>
	 * experts = expertSelectedDao.getAll(); List<String> expertNumbers = new
	 * ArrayList<String>(); for (ExpertSelected expert : experts) {
	 * expertNumbers.add(expert.getExpertNumber()); } return
	 * getExpertResultFromOtherDBDao.getAttendExpertsUnits(expertNumbers); }
	 */
	// =================以下是与业务逻辑无关的setter和getter=========================
	public ExpertDao getExpertDao() {
		return expertDao;
	}

	public void setExpertDao(ExpertDao expertDao) {
		this.expertDao = expertDao;
	}

	public OuterExpertDao getOuterExpertDao() {
		return outerExpertDao;
	}

	public void setOuterExpertDao(OuterExpertDao outerExpertDao) {
		this.outerExpertDao = outerExpertDao;
	}
}
