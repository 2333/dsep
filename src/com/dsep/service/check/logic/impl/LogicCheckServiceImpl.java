package com.dsep.service.check.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.dsepmeta.base.MessageDao;
import com.dsep.dao.dsepmeta.check.LogicCheckAttrResultDao;
import com.dsep.dao.dsepmeta.check.LogicCheckEntityResultDao;
import com.dsep.dao.dsepmeta.check.LogicCheckIdentityResultDao;
import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.LogicCheckAttrResult;
import com.dsep.entity.dsepmeta.LogicCheckEntityResult;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;
import com.dsep.service.check.logic.LogicCheckService;
import com.dsep.service.dsepmeta.dsepmetas.DMCheckLogicRuleService;
import com.dsep.service.queue.QueueService;
import com.dsep.util.Dictionaries;
import com.dsep.util.GUID;
import com.dsep.util.datacheck.LogicCheckStatusCode;
import com.dsep.vm.LogicCheckVM;
import com.dsep.vm.PageVM;

public class LogicCheckServiceImpl implements LogicCheckService {
	private DMCheckLogicRuleService checkLogicRule;
	private LogicCheckAttrResultDao logicCheckAttrResultDao;
	private LogicCheckEntityResultDao logicCheckEntityResultDao;
	private LogicCheckIdentityResultDao logicCheckIdentityResultDao;
	private MessageDao messageDao;
	private DisciplineService disciplineService;
	private UnitService unitService;
	private QueueService queueService;

	

	@Override
	public PageVM<LogicCheckVM> showResultForCenter(
			String inputUnitId, User user, String orderPropName, boolean asc,
			int pageIndex, int pageSize) {
		List<Map<String, String>> unitDataList = logicCheckEntityResultDao
				.getResultForCenter(inputUnitId, user,
						pageIndex, pageSize, asc, orderPropName);

		List<LogicCheckVM> logicCheckVMList = new ArrayList<LogicCheckVM>();

		int count = -1;
		if (StringUtils.isBlank(inputUnitId)) {
			count = logicCheckEntityResultDao.getCheckCount(user.getUnitId(),
					user.getDiscId(), user.getId());
		} else {
			count = 1;
		}
		for (Map<String, String> passInfo : unitDataList) {
			logicCheckVMList.add(new LogicCheckVM(passInfo));
		}
		return new PageVM<LogicCheckVM>(pageIndex, count, pageSize,
				logicCheckVMList);
	}

	@Override
	public PageVM<LogicCheckVM> showResultForUnit(
			String discId, User user, String orderPropName, boolean asc,
			int pageIndex, int pageSize) {
		List<Map<String, String>> discDataList = logicCheckEntityResultDao
				.getResultForUnit(discId, user,
						pageIndex, pageSize, asc, orderPropName);

		List<LogicCheckVM> logicCheckVMList = new ArrayList<LogicCheckVM>();

		int count = -1;
		if (StringUtils.isNotBlank(discId)) {
			count = 1;
		} else {
			count = logicCheckEntityResultDao.getCheckCount(user.getUnitId(),
					user.getDiscId(), user.getId());
		}
		for (Map<String, String> passInfo : discDataList) {
			logicCheckVMList.add(new LogicCheckVM(passInfo));
		}
		return new PageVM<LogicCheckVM>(pageIndex, count, pageSize,
				logicCheckVMList);
	}

	@Override
	public PageVM<LogicCheckVM> showResultForDisc(
			String unitId, String discId, String userId, String orderPropName,
			boolean asc, int pageIndex, int pageSize) {
		List<LogicCheckEntityResult> entityDataList = logicCheckEntityResultDao
				.getResultForDisc(unitId, discId,
						userId, pageIndex, pageSize, asc, orderPropName);

		List<LogicCheckVM> logicCheckVMList = new ArrayList<LogicCheckVM>();

		for (LogicCheckEntityResult entityData : entityDataList) {
			logicCheckVMList.add(new LogicCheckVM(entityData));
		}

		int count = logicCheckEntityResultDao.getCheckCount(unitId, discId,
				userId);
		return new PageVM<LogicCheckVM>(pageIndex, count, pageSize,
				logicCheckVMList);
	}

	@Override
	public PageVM<LogicCheckAttrResult> showLogicResultData(String unitId,
			String discId, String entityId, String userId,
			String orderPropName, boolean asc, int pageIndex, int pageSize) {
		List<LogicCheckAttrResult> list = logicCheckAttrResultDao
				.getLogicCheckAttrResults(unitId, discId, entityId, userId,
						pageIndex, pageSize, asc, orderPropName);

		int count = logicCheckAttrResultDao.getCount(unitId, discId, entityId,
				userId);

		return new PageVM<LogicCheckAttrResult>(pageIndex, count, pageSize,
				list);
	}

	@Override
	public boolean startLogicCheck(String inputUnitId, String inputDiscId,
			User user) {
		/*//注释中为异步检查代码
		// * 
		 HashMap<String, String> map = new HashMap<String, String>();
		if (StringUtils.isNotBlank(inputUnitId)) {
			map.put("unitId", inputUnitId);
		} else {
			map.put("unitId", user.getUnitId());
		}
		if ((StringUtils.isNotBlank(inputDiscId))
				&& (!inputDiscId.equals("undefined"))) {
			map.put("discId", inputDiscId);
		} else {
			map.put("discId", user.getDiscId());
		}
		map.put("userId", user.getId());
		String id = null;
		QueueMessage message = null;
		if (logicCheckIdentityResultDao.whetherExistGUID(user.getId())) {
			message = new QueueMessage(logicCheckIdentityResultDao.getGUID(user
					.getId()), "checkLogicRule", map);
		} else {
			id = GUID.get();
			logicCheckIdentityResultDao.setNewIdentityId(id, user.getId());
			message = new QueueMessage(id, "checkLogicRule", map);
		}
		try {
			queueService.sendMessage(message);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;*/
		if ((null == inputUnitId) || inputUnitId.equals("undefined")) inputUnitId = "";
		if ((null == inputDiscId) || inputDiscId.equals("undefined")) inputDiscId = "";
		// 以下是同步检查代码
		// 先清空
		logicCheckAttrResultDao.deleteLogicCheckAttrResultsByUserId(inputUnitId, inputDiscId, user.getId());
		logicCheckEntityResultDao.deleteAllOfTheLastData(inputUnitId, inputDiscId, user.getId());
		List<String> units = new ArrayList<String>();
		List<String> discs = new ArrayList<String>();
		
		// 中心用户
		if (user.getUserType().equals("1")) {
			if (StringUtils.isNotBlank(inputUnitId)) {
				units.add(inputUnitId);
			} else {
				units = unitService.getAllEvalUnitIds();
			}
			
			for (String unit : units) {
				if ((StringUtils.isNotBlank(inputDiscId))
						&& (!inputDiscId.equals("undefined"))) {
					discs.add(inputDiscId);
				} else {
					discs = disciplineService.getJoinDisciplineByUnitId(unit);
				}
				for (String disc : discs) {
					checkLogicRule.logicCheck(unit, disc, user.getId());
				}
			}
		} // 学校用户
		else if (user.getUserType().equals("2")) {
			units.add(user.getUnitId());
			
			for (String unit : units) {
				if ((StringUtils.isNotBlank(inputDiscId))
						&& (!inputDiscId.equals("undefined"))) {
					discs.add(inputDiscId);
				} else {
					discs = disciplineService.getJoinDisciplineByUnitId(unit);
				}
				for (String disc : discs) {
					checkLogicRule.logicCheck(unit, disc, user.getId());
				}
			}
		}
		else if (user.getUserType().equals("3")) {
			checkLogicRule.logicCheck(inputUnitId, inputDiscId, user.getId());
		}
		
		logicCheckIdentityResultDao.deleteByUserId(user.getId());
		// 写入数据库，该用户表明已经进行过逻辑检查
		logicCheckIdentityResultDao.setNewIdentityId(GUID.get(), user.getId(), new Date());
		
		
		if (StringUtils.isNotBlank(inputUnitId)) {
			units.add(inputUnitId);
		} else {
			units.add(user.getUnitId());
		}
		if ((StringUtils.isNotBlank(inputDiscId))
				&& (!inputDiscId.equals("undefined"))) {
			discs.add(inputDiscId);
		} else {
			discs.add(user.getDiscId());
		}
		
		return true;
	}

	@Override
	public PageVM<LogicCheckEntityResult> showLogicWarnData(String unitId,
			String discId, String entityId, String userId, boolean onlyGetWarn,
			String orderPropName, boolean asc, int pageIndex, int pageSize) {
		List<LogicCheckEntityResult> list = null;
		int count = 0;
		if (onlyGetWarn) {
			list = logicCheckEntityResultDao.getWarnData(unitId, discId,
					entityId, userId, pageIndex, pageSize, asc, orderPropName);
			count = logicCheckEntityResultDao.getCheckWarnCount(unitId, discId,
					entityId, userId);

		} else {
			list = logicCheckEntityResultDao.getWarnAndErrorData(unitId,
					discId, entityId, userId, pageIndex, pageSize, asc,
					orderPropName);
			count = logicCheckEntityResultDao.getCheckWarnCount(unitId, discId,
					entityId, userId);
		}

		return new PageVM<LogicCheckEntityResult>(pageIndex, count, pageSize,
				list);
	}

	@Override
	public String exportErrorAndWarnData(String unitId, String discId,
			User user, String rootPath) {
		return checkLogicRule.exportErrorAndWarnData(unitId, discId, user,
				rootPath);
	}

	@Override
	public String haveCheckCompleted(String unitId, String discId, String userId) {
		String checkId = logicCheckIdentityResultDao.getGUID(userId);
		
		String checkStatus = messageDao.getStatusByIdentifier(checkId);
		
		
		if (!checkId.equals(LogicCheckStatusCode.HAVE_NOT_CHECKED)) {
			
			// 方弘宇：同步的话，如果有checkId就表示有检查结果
			return LogicCheckStatusCode.HAVE_CHECKED;
			//if (checkStatus.equals("2")) {
			//	return LogicCheckStatusCode.HAVE_CHECKED;
			//} else {
			//	return LogicCheckStatusCode.IS_CHECK_ING;
			//}
		} else {
			return LogicCheckStatusCode.HAVE_NOT_CHECKED;
		}
	}

	@Override
	public Map<String, String> getInitDiscIdMaps(String unitId) {
		Map<String, String> data = new HashMap<String, String>();
		data = disciplineService.getJoinDisciplineMapByUnitId(unitId);
		return data;
	}

	@Override
	public Map<String, String> getInitUnitIdMaps() {
		Map<String, String> data = new HashMap<String, String>();
		List<String> unitIds = unitService.getAllEvalUnitIds();
		for (String unitId : unitIds) {
			String unitName = Dictionaries.getUnitName(unitId);
			data.put(unitId, unitName);
		}
		return data;
	}
	

	
	
	
	
	
	// 与业务逻辑无关的getter和setter放在最下面
	public DMCheckLogicRuleService getCheckLogicRule() {
		return checkLogicRule;
	}

	public void setCheckLogicRule(DMCheckLogicRuleService checkLogicRule) {
		this.checkLogicRule = checkLogicRule;
	}

	public LogicCheckAttrResultDao getLogicCheckAttrResultDao() {
		return logicCheckAttrResultDao;
	}

	public void setLogicCheckAttrResultDao(
			LogicCheckAttrResultDao logicCheckAttrResultDao) {
		this.logicCheckAttrResultDao = logicCheckAttrResultDao;
	}

	public LogicCheckEntityResultDao getLogicCheckEntityResultDao() {
		return logicCheckEntityResultDao;
	}

	public void setLogicCheckEntityResultDao(
			LogicCheckEntityResultDao logicCheckEntityResultDao) {
		this.logicCheckEntityResultDao = logicCheckEntityResultDao;
	}

	public QueueService getQueueService() {
		return queueService;
	}

	public void setQueueService(QueueService queueService) {
		this.queueService = queueService;
	}

	public LogicCheckIdentityResultDao getLogicCheckIdentityResultDao() {
		return logicCheckIdentityResultDao;
	}

	public void setLogicCheckIdentityResultDao(
			LogicCheckIdentityResultDao logicCheckIdentityResultDao) {
		this.logicCheckIdentityResultDao = logicCheckIdentityResultDao;
	}
	
	public UnitService getUnitService() {
		return unitService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public DisciplineService getDisciplineService() {
		return disciplineService;
	}

	public void setDisciplineService(DisciplineService disciplineService) {
		this.disciplineService = disciplineService;
	}

	public MessageDao getMessageDao() {
		return messageDao;
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	
}
