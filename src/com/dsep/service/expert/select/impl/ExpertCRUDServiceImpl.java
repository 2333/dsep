package com.dsep.service.expert.select.impl;

import java.util.ArrayList;
import java.util.List;

import com.dsep.dao.dsepmeta.expert.selection.ExpertDao;
import com.dsep.dao.dsepmeta.expert.selection.OuterExpertDao;
import com.dsep.domain.dsepmeta.expert.OuterExpert;
import com.dsep.domain.dsepmeta.expert.ExpertQueryConditions;
import com.dsep.entity.expert.EvalBatch;
import com.dsep.entity.expert.Expert;
import com.dsep.service.expert.batch.EvalBatchService;
import com.dsep.service.expert.evaluation.EvalAchvService;
import com.dsep.service.expert.evaluation.EvalIndicIdxService;
import com.dsep.service.expert.evaluation.EvalIndicWtService;
import com.dsep.service.expert.evaluation.EvalRepuService;
import com.dsep.service.expert.select.ExpertCRUDService;
import com.dsep.util.expert.ExpertEvalCurrentStatus;
import com.dsep.util.expert.email.MD5Util;
import com.dsep.vm.PageVM;
import com.dsep.vm.expert.ExpertSelectedVM;

public class ExpertCRUDServiceImpl implements ExpertCRUDService {
	private ExpertDao expertDao;
	private OuterExpertDao outerExpertDao;
	private EvalIndicIdxService evalIndicIdxService;
	private EvalIndicWtService evalIndicWtService;
	private EvalAchvService evalAchvService;
	private EvalRepuService evalRepuService;
	private EvalBatchService evalBatchService;

	@Override
	public void addExpert(Expert expert) {
		this.expertDao.addExpertSelected(expert);
	}

	@Override
	public void addExperts(List<Expert> experts, String batchId) {
		EvalBatch batch = evalBatchService.getEvalBatchById(batchId);
		for (Expert expert : experts) {
			batch.getExperts().add(expert);
			expert.setEvalBatch(batch);
			addExpert(expert);
		}
	}

	@Override
	public void deleteExperts(List<String> expertIds) {
		for (String expertId : expertIds) {
			Expert expert = expertDao.get(expertId);
			expert.getEvalBatch().getExperts().remove(expert);
			expert.setEvalBatch(null);
			expertDao.delete(expert);
		}
	}

	@Override
	public void replaceExpert(String oldExpertId, String newExpertNumber,
			Boolean isSecond) throws InstantiationException,
			IllegalAccessException {
		Expert expert = expertDao.get(oldExpertId);
		EvalBatch batch = expert.getEvalBatch();
		expert.getEvalBatch().getExperts().remove(expert);
		expert.setEvalBatch(null);
		// 删除被替换专家
		expertDao.delete(expert);
		List<String> expertNumbers = new ArrayList<String>();
		expertNumbers.add(newExpertNumber);

		List<OuterExpert> list = outerExpertDao
				.getExpertsByExpertNumbers(expertNumbers);

		Expert expertSelected = new Expert();

		// 这里不涉及数据库操作，设置ID仅仅为了前台jqgrid的check box可以有唯一id
		OuterExpert e = list.get(0);
		System.out.println(expertSelected.getId());
		expertSelected.setExpertName(e.getZJXM());
		// 这里需要斟酌, 本库中应该只存一个参评disc
		if (isSecond)
			expertSelected.setDiscId2(e.getYJXKM2());
		else
			expertSelected.setDiscId(e.getYJXKM());

		expertSelected.setExpertNumber(e.getZJBH());
		expertSelected.setExpertType(e.getZJFL());
		expertSelected.setUnitId(e.getXXDM());
		// 当前状态设置为"未发送邮件"
		expertSelected.setCurrentStatus(ExpertEvalCurrentStatus.NotMailed
				.toInt());
		expertSelected.setEvalBatch(batch);
		batch.getExperts().add(expertSelected);

		this.expertDao.addExpertSelected(expertSelected);
	}

	@Override
	public ExpertSelectedVM getExpert(String expertId) {
		Expert expert = this.expertDao.get(expertId);
		ExpertSelectedVM vm = new ExpertSelectedVM(expert);
		return vm;
	}

	@Override
	public PageVM<ExpertSelectedVM> getExperts(String batchId, int pageIndex,
			int pageSize, Boolean desc, String orderProperName)
			throws InstantiationException, IllegalAccessException {
		// 获得所有已选专家
		List<Expert> experts = this.expertDao.page(batchId,
				pageIndex, pageSize, desc, orderProperName);
		// 已选专家的总数
		int totalCount = expertDao.Count(batchId);

		List<ExpertSelectedVM> vmList = new ArrayList<ExpertSelectedVM>();
		for (Expert expert : experts) {
			String expertId = expert.getId();
			ExpertSelectedVM vm = new ExpertSelectedVM(expert);
			// 已经确定参评的专家，这时专家的题目都已经配置好了
			if (expert.getCurrentStatus() == ExpertEvalCurrentStatus.Confirmed
					.toInt()) {
				vm.setIndicatorName(evalIndicIdxService.getIndicIdxProg(
						batchId, expertId));
				vm.setIndicatorWeightName(evalIndicWtService.getIndicWtProg(
						batchId, expertId));
				vm.setAchievementName(evalAchvService.getAchvProg(batchId,
						expertId, null));
				vm.setReputationName(evalRepuService.getRepuProg(batchId,
						expertId));
				// String PCT = "TEST";
				/*
				 * evalProgressService.getIndicatorIndexPCT(expertId);
				 * 
				 * vm.setIndicatorName(PCT); PCT =
				 * evalProgressService.getIndicatorWeightPCT(expertId);
				 * vm.setIndicatorWeightName(PCT); PCT =
				 * evalProgressService.getAchievementPCT(expertId);
				 * vm.setAchievementName(PCT); PCT =
				 * evalProgressService.getReputationPCT(expertId);
				 * vm.setReputationName(PCT);
				 */
			} else {
				vm.setIndicatorName(null);
				vm.setIndicatorWeightName(null);
				vm.setAchievementName(null);
				vm.setReputationName(null);
			}

			vmList.add(vm);
		}
		PageVM<ExpertSelectedVM> result = new PageVM<ExpertSelectedVM>(
				pageIndex, totalCount, pageSize, vmList);

		return result;
	}

	@Override
	public PageVM<ExpertSelectedVM> queryExperts(
			ExpertQueryConditions conditions) throws InstantiationException,
			IllegalAccessException {
		List<Expert> experts = null;
		// 目前前台的条件有：
		// 1.专家姓名(本库) 2.专家编号(本库) 3.参评学科(本库)
		// 4.学校(外库) 5.is211(外库) 6.is985(外库)
		// 7.专家是否邮件通知/专家是否回复(本库 这两者都是在本库中用CURRENT_STATUS字段表示)
		// 9.所属学科(是否需要提供?)
		if (innerQuery(conditions)) {
			experts = this.expertDao.query(
					conditions.getInnerExpertName(),
					conditions.getInnerExpertNumber(),
					conditions.getInnerExpertDisc1(),
					conditions.getInnerCurrentCondition(),
					conditions.getCurrentBatchId());
		} else if (outerQuery(conditions)) {

			experts = this.expertDao.query(
					conditions.getOuterExpertName(),
					conditions.getOuterExpertNumber(),
					conditions.getInnerExpertDisc1(),
					conditions.getInnerCurrentCondition(),
					conditions.getCurrentBatchId());
			// getExpertResultFromOtherDBDao.getExpertsQueryByName(experts,);
		}
		// 用外库专家填充信息

		List<ExpertSelectedVM> vmList = new ArrayList<ExpertSelectedVM>();
		for (Expert expert : experts) {
			String expertId = expert.getId();
			ExpertSelectedVM vm = new ExpertSelectedVM(expert);
			// 已经确定参评的专家，这时专家的题目都已经配置好了
			if (expert.getCurrentStatus() == ExpertEvalCurrentStatus.Confirmed
					.toInt()) {
				String batchId = conditions.getCurrentBatchId();
				vm.setIndicatorName(evalIndicIdxService.getIndicIdxProg(
						batchId, expertId));
				vm.setIndicatorWeightName(evalIndicWtService.getIndicWtProg(
						batchId, expertId));
				vm.setAchievementName(evalAchvService.getAchvProg(batchId,
						expertId, null));
				vm.setReputationName(evalRepuService.getRepuProg(batchId,
						expertId));

				// String PCT = "TEST";
				/*
				 * evalProgressService.getIndicatorIndexPCT(expertId);
				 * vm.setIndicatorName(PCT); PCT =
				 * evalProgressService.getIndicatorWeightPCT(expertId);
				 * vm.setIndicatorWeightName(PCT); PCT =
				 * evalProgressService.getAchievementPCT(expertId);
				 * vm.setAchievementName(PCT); PCT =
				 * evalProgressService.getReputationPCT(expertId);
				 * vm.setReputationName(PCT);
				 */
			} else {
				vm.setIndicatorName(null);
				vm.setAchievementName(null);
				vm.setIndicatorWeightName(null);
				vm.setReputationName(null);
			}

			vmList.add(vm);
		}
		// 这里需要完善 这里的10是写死的
		PageVM<ExpertSelectedVM> result = new PageVM<ExpertSelectedVM>(1,
				vmList.size(), 10, vmList);
		return result;
	}

	@Override
	public void modifyExpertEmail(String id, String newEmail) {
		String validateCode = MD5Util.encode2hex(newEmail);
		this.expertDao.modifyExpertEmail(id, newEmail, validateCode);
	}

	// ===========private方法 util==================
	private boolean innerQuery(ExpertQueryConditions conditions) {
		if (null != conditions.getInnerCurrentCondition()
				|| null != conditions.getInnerExpertDisc1()
				|| null != conditions.getInnerExpertName()
				|| null != conditions.getInnerExpertId()
				|| null != conditions.getInnerExpertNumber()) {
			return true;
		} else {
			return false;
		}
	}

	private boolean outerQuery(ExpertQueryConditions conditions) {
		if (null != conditions.getOuterCurrentCondition()
				|| null != conditions.getOuterExpertDisc1()
				|| null != conditions.getOuterExpertName()
				|| null != conditions.getOuterExpertId()
				|| null != conditions.getOuterExpertNumber()) {
			return true;
		} else {
			return false;
		}
	}

	private List<ExpertSelectedVM> fillExpertSelectedVMUsingExpertInfoFromOtherDB(
			List<Expert> experts,
			List<OuterExpert> expertInfoFromOtherDB) {
		List<ExpertSelectedVM> vmList = new ArrayList<ExpertSelectedVM>();
		for (Expert expert : experts) {
			for (OuterExpert expertFromOtherDB : expertInfoFromOtherDB) {
				// ExpDaoImpl中有一个bug
				// ——expertInfoFromOtherDB的最后一个是null
				// 容易引起空指针异常，需要排除！！
				if (null == expertFromOtherDB)
					break;
				if (null == expert)
					break;

				if (expert.getExpertNumber()
						.equals(expertFromOtherDB.getZJBH())) {
					vmList.add(new ExpertSelectedVM(expert, expertFromOtherDB));
					break;
				}
			}
		}
		return vmList;
	}

	@Override
	public PageVM<ExpertSelectedVM> queryOuterExperts(
			ExpertQueryConditions conditions) throws InstantiationException,
			IllegalAccessException {
		return null;
	}

	@Override
	public PageVM<ExpertSelectedVM> queryOuterExpertsByName(String name)
			throws InstantiationException, IllegalAccessException {
		List<String> selectedExpertNumbers = expertDao
				.getExpertNumberByName(name);
		List<OuterExpert> expertsQueryByNameAndNotSelected = outerExpertDao
				.getExpertsByName(name, selectedExpertNumbers);
		// 按权重排序
		// this.selectionDao.rangeExperts(expertsQueryByNameAndNotSelected);
		List<ExpertSelectedVM> vmList = new ArrayList<ExpertSelectedVM>();
		int i = 0;
		for (OuterExpert e : expertsQueryByNameAndNotSelected) {
			OuterExpert expert = e;
			Expert expertSelected = new Expert();
			// 这里不涉及数据库操作，设置ID仅仅为了前台jqgrid的check box可以有唯一id
			expertSelected.setId(String.valueOf(i++));
			System.out.println(expertSelected.getId());
			expertSelected.setDiscId(expert.getYJXKM());
			expertSelected.setDiscId2(expert.getYJXKM2());
			expertSelected.setExpertNumber(expert.getZJBH());

			// 当前状态设置为"未发送邮件"
			expertSelected.setCurrentStatus(ExpertEvalCurrentStatus.NotMailed
					.toInt());
			ExpertSelectedVM vm = new ExpertSelectedVM(expertSelected, expert);
			vm.showYJXKMAndYJXKM2();
			vmList.add(vm);
		}
		PageVM<ExpertSelectedVM> result = new PageVM<ExpertSelectedVM>(1,
				vmList.size(), 10, vmList);
		return result;
	}

	@Override
	public PageVM<ExpertSelectedVM> queryOuterExpertsByNumber(String number)
			throws InstantiationException, IllegalAccessException {
		List<String> selectedExpertNumbers = expertDao
				.getExpertByExpertNumber(number);
		// selectedExpertNumbers有值，说明专家已经在已选库中了
		if (selectedExpertNumbers.size() > 0) {
			return null;
		}

		OuterExpert expert = outerExpertDao
				.getExpertsByExpertNumber(number);
		if (null == expert) {
			return null;
		}
		// 按权重排序
		// this.selectionDao.rangeExperts(expertsQueryByNameAndNotSelected);
		List<ExpertSelectedVM> vmList = new ArrayList<ExpertSelectedVM>();
		Expert expertSelected = new Expert();
		// 这里不涉及数据库操作，设置ID仅仅为了前台jqgrid的check box可以有唯一id
		expertSelected.setId(String.valueOf(0));
		System.out.println(expertSelected.getId());
		expertSelected.setDiscId(expert.getYJXKM());
		expertSelected.setDiscId2(expert.getYJXKM2());
		expertSelected.setExpertNumber(expert.getZJBH());

		// 当前状态设置为"未发送邮件"
		expertSelected.setCurrentStatus(ExpertEvalCurrentStatus.NotMailed
				.toInt());
		ExpertSelectedVM vm = new ExpertSelectedVM(expertSelected, expert);
		vm.showYJXKMAndYJXKM2();
		vmList.add(vm);
		PageVM<ExpertSelectedVM> result = new PageVM<ExpertSelectedVM>(1,
				vmList.size(), 10, vmList);
		return result;
	}

	@Override
	public PageVM<ExpertSelectedVM> queryOuterExpertsByDiscIdAndUnitId(
			String discId, String unitId) throws InstantiationException,
			IllegalAccessException {
		if (discId.equals("")) {
			discId = null;
		}
		if (unitId.equals("")) {
			unitId = null;
		}
		List<String> selectedExpertNumbers = expertDao
				.getExpertNumberByDiscIdAndUnitId(discId, unitId);
		List<OuterExpert> expertsQueryByNameAndNotSelected = outerExpertDao
				.getExpertsQueryByDisAndUnit(discId, unitId,
						selectedExpertNumbers);
		// 按权重排序
		// this.selectionDao.rangeExperts(expertsQueryByNameAndNotSelected);
		List<ExpertSelectedVM> vmList = new ArrayList<ExpertSelectedVM>();
		int i = 0;
		for (OuterExpert e : expertsQueryByNameAndNotSelected) {
			OuterExpert expert = e;
			Expert expertSelected = new Expert();
			// 这里不涉及数据库操作，设置ID仅仅为了前台jqgrid的check box可以有唯一id
			expertSelected.setId(String.valueOf(i++));
			System.out.println(expertSelected.getId());
			expertSelected.setDiscId(expert.getYJXKM());
			expertSelected.setDiscId2(expert.getYJXKM2());
			expertSelected.setExpertNumber(expert.getZJBH());

			// 当前状态设置为"未发送邮件"
			expertSelected.setCurrentStatus(ExpertEvalCurrentStatus.NotMailed
					.toInt());
			ExpertSelectedVM vm = new ExpertSelectedVM(expertSelected, expert);
			vm.showYJXKMAndYJXKM2();
			vmList.add(vm);
		}
		PageVM<ExpertSelectedVM> result = new PageVM<ExpertSelectedVM>(1,
				vmList.size(), 10, vmList);
		return result;
	}

	@Override
	public List<Expert> queryInnerExpertsByDiscIdOrDiscId2(
			String discIdOrDiscId2, String batchId) {
		return expertDao.queryExpertsByDiscIdOrDiscId2(discIdOrDiscId2,
				batchId);
	}
	
	@Override
	public List<String> queryInnerExpertZJBHsByDiscIdOrDiscId2(
			String discIdOrDiscId2, String batchId) {
		return expertDao.queryExpertZJBHsByDiscIdOrDiscId2(
				discIdOrDiscId2, batchId);
	}

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

	public EvalIndicIdxService getEvalIndicIdxService() {
		return evalIndicIdxService;
	}
	
	public void setEvalIndicIdxService(EvalIndicIdxService evalIndicIdxService) {
		this.evalIndicIdxService = evalIndicIdxService;
	}

	public EvalIndicWtService getEvalIndicWtService() {
		return evalIndicWtService;
	}

	public void setEvalIndicWtService(EvalIndicWtService evalIndicWtService) {
		this.evalIndicWtService = evalIndicWtService;
	}

	public EvalAchvService getEvalAchvService() {
		return evalAchvService;
	}

	public void setEvalAchvService(EvalAchvService evalAchvService) {
		this.evalAchvService = evalAchvService;
	}

	public EvalBatchService getEvalBatchService() {
		return evalBatchService;
	}

	public void setEvalBatchService(EvalBatchService evalBatchService) {
		this.evalBatchService = evalBatchService;
	}

	public EvalRepuService getEvalRepuService() {
		return evalRepuService;
	}

	public void setEvalRepuService(EvalRepuService evalRepuService) {
		this.evalRepuService = evalRepuService;
	}

	
}
