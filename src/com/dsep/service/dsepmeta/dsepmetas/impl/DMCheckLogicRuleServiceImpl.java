package com.dsep.service.dsepmeta.dsepmetas.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.dsepmeta.check.LogicCheckAttrResultDao;
import com.dsep.dao.dsepmeta.check.LogicCheckEntityResultDao;
import com.dsep.dao.dsepmeta.check.LogicCheckIdentityResultDao;
import com.dsep.domain.UnitDiscCollect;
import com.dsep.domain.dsepmeta.logicrule.LogicCheckFactory;
import com.dsep.domain.dsepmeta.logicrule.MetaAttrCheck;
import com.dsep.domain.dsepmeta.logicrule.MetaEntityCheck;
import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.LogicCheckAttrResult;
import com.dsep.entity.dsepmeta.LogicCheckEntityResult;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;
import com.dsep.service.dsepmeta.MetaOper;
import com.dsep.service.dsepmeta.dsepmetas.DMCheckLogicRuleService;
import com.dsep.service.dsepmeta.dsepmetas.DMCollectService;
import com.dsep.service.dsepmeta.dsepmetas.DMDiscIndexService;
import com.dsep.service.file.ExportService;
import com.dsep.service.queue.QueueMessage;
import com.dsep.service.queue.QueueMessageHandler;
import com.dsep.util.Dictionaries;
import com.dsep.util.datacheck.LogicCheckStatusCode;
import com.dsep.vm.EntityLogicCheckVM;
import com.dsep.vm.JqgridVM;
import com.meta.domain.MetaAttrDomain;
import com.meta.domain.MetaEntityDomain;
import com.meta.entity.MetaControlType;
import com.meta.entity.MetaEntity;

public class DMCheckLogicRuleServiceImpl extends MetaOper implements
		DMCheckLogicRuleService, QueueMessageHandler {

	private LogicCheckAttrResultDao logicCheckAttrResultDao;
	private LogicCheckEntityResultDao logicCheckEntityResultDao;
	private DMDiscIndexService discIndexService;
	private DMCollectService collectService;
	private ExportService exportService;
	private UnitService unitService;
	private DisciplineService disciplineService;
	private LogicCheckIdentityResultDao logicCheckIdentityResultDao;
	

	// 全局变量用来存放检查结果，把检查结果一次性存入数据库
	List<LogicCheckAttrResult> attrResultList;
	List<LogicCheckEntityResult> entityResultList;

	private void initCollection() {
		// 初始化属性检查结果集合
		attrResultList = new ArrayList<LogicCheckAttrResult>();
		entityResultList = new ArrayList<LogicCheckEntityResult>();
	}

	private void destroyCollection() {
		attrResultList = null;
		entityResultList = null;
	}

	@Override
	public String checkSingleField(String entityId, String attrName,
			String attrVal) {
		MetaEntityDomain entityDomain = metaEntityService.getEntityDomain(
				entityId, "C");

		return atomicAttrLogicCheck(entityId, entityDomain, attrName, attrVal);
	}

	@Override
	public boolean logicCheck(String unitId, String discId, String userId) {
		System.out.println("*******************************************");
		System.out.println("*******************************************");
		System.out.println(unitId + "的" + discId + "学科开始逻辑检查");
		System.out.println("*******************************************");
		System.out.println("*******************************************");
		System.out.println();

		initCollection();
		// 改为在检查之前全部清空
		//logicCheckAttrResultDao.deleteLogicCheckAttrResultsByUserId(unitId,
		//		discId, userId);

		//logicCheckEntityResultDao
		//		.deleteAllOfTheLastData(unitId, discId, userId);

		List<UnitDiscCollect> entityList = discIndexService
				.getUnitDiscCollects(unitId, discId);

		for (UnitDiscCollect entity : entityList) {
			String metaUnitId = entity.getUnitId();
			String metaDiscId = entity.getDiscId();
			for (String[] data : entity.getEntityIdandName()) {
				String entityId = data[0];
				String entityChsName = data[1];
				System.out
						.println("*******************************************");
				System.out.println("开始对\"" + entityChsName + "\"表" + "进行检查");
				System.out
						.println("*******************************************");

				MetaEntityDomain entityDomain = metaEntityService
						.getEntityDomain(entityId, "C");

				// "原子属性检查":检查一个学校的一个学科的一个实体的所有记录字段
				boolean hasError = atomicAttrLogicCheck(metaUnitId, metaDiscId,
						entityId, userId, entityDomain);

				String entityResult = this.entitycheck(entityId, metaUnitId,
						metaDiscId, entityDomain, false);

				if (!"PASSED".equals(entityResult)) {
					// 对实体检查也会有错误信息(不作为警告信息！)，它们都是ERRORxxx的形式
					if (entityResult.startsWith("ERROR")) {
						entityResult = entityResult.replace("ERROR", "");
						boolean hasErrorComeFromEntityCheck = true;
						boolean hasWarn = false;
						attrResultList.add(packageAttrResultForXYJG(entityId,
								unitId, discId, userId, entityResult, 1));
						attrResultList.add(packageAttrResultForXYJG(entityId,
								unitId, discId, userId, entityResult, 2));
						attrResultList.add(packageAttrResultForXYJG(entityId,
								unitId, discId, userId, entityResult, 3));
						attrResultList.add(packageAttrResultForXYJG(entityId,
								unitId, discId, userId, entityResult, 4));
						entityResultList.add(packageEntityResult(metaUnitId,
								metaDiscId, entityId, userId, entityResult,
								entityChsName, hasErrorComeFromEntityCheck,
								hasWarn));
					} else {
						boolean hasWarn = true;
						entityResultList.add(packageEntityResult(metaUnitId,
								metaDiscId, entityId, userId, entityResult,
								entityChsName, hasError, hasWarn));
					}

				} else {
					// 如果entityResult的结果是PASSED 那么数据库中hasWarn是false，表明没有警告
					boolean hasWarn = false;
					entityResultList.add(packageEntityResult(metaUnitId,
							metaDiscId, entityId, userId, "", entityChsName,
							hasError, hasWarn));
				}
			}
		}

		// 调用DAO，写入数据库
		logicCheckAttrResultDao.saveLogicCheckAttrResults(attrResultList);
		logicCheckEntityResultDao.saveLogicCheckEntityResults(entityResultList);
		// flag implements contains error
		Boolean flag = (attrResultList.size() > 0) ? true : false;

		destroyCollection();

		return flag;
	}

	// public boolean attrLogicCheck(String unitId, String discId, String
	// userId) {
	//
	// // [10001, 0835, E2013A01] [10001, 0835, E2013A02]
	// // [10001, 0836, E2013A01]
	// List<UnitDiscCollect> entityList = discIndexService
	// .getUnitDiscCollects(unitId, discId);
	//
	// for (UnitDiscCollect record : entityList) {
	// String uId = record.getUnitId();
	// String dId = record.getDiscId();
	// List<String[]> entityIdAndNames = record.getEntityIdandName();
	// for (String[] entityIdAndName : entityIdAndNames) {
	// String eId = entityIdAndName[0];
	// atomicAttrLogicCheck(uId, dId, eId, userId);
	// }
	// }
	//
	// // 调用DAO，写入数据库
	// logicCheckAttrResultDao.deleteLogicCheckAttrResultsByUserId(userId);
	// logicCheckAttrResultDao.saveLogicCheckAttrResults(attrResultList);
	//
	// // 销毁属性检查结果集合
	// attrResultList = null;
	//
	// return true;
	// }

	private String atomicAttrLogicCheck(String entityId,
			MetaEntityDomain metaEntityDomain, String attrName, String attrVal) {
		MetaEntity metaEntity = metaEntityDomain.getEntity();

		List<MetaAttrDomain> metaAttrDomains = metaEntityDomain
				.getAttrDomains();
		Map<String, String> data = new HashMap<String, String>();
		data.put(attrName, attrVal);
		return check(attrName, metaEntity, metaAttrDomains, data);
	}

	private boolean atomicAttrLogicCheck(String unitId, String discId,
			String entityId, String userId, MetaEntityDomain metaEntityDomain) {
		// MetaEntityDomain metaEntityDomain = metaEntityService
		// .getEntityDomain(entityId);
		MetaEntity metaEntity = metaEntityDomain.getEntity();

		List<MetaAttrDomain> metaAttrDomains = metaEntityDomain
				.getAttrDomains();

		List<Map<String, String>> dataList = getCheckData(unitId, discId,
				entityId);

		boolean hasError = false;
		// 开始检查
		for (Map<String, String> rowData : dataList) {
			/**
			 * 一行rowData如下所示 
			 * {ID=.., 
			 * UNIT_ID=10006, 
			 * DISC_ID=0835,
			 * INSERT_USER_ID=10006_0835, 
			 * INSERT_TIME=.., MODIFY_USER_ID=..,
			 * MODIFY_TIME=.., 
			 * ZX_BSS=9, ZX_SSS=8, BSDS=9, SSDS=7, XWSD=0,
			 * ZZJS=6, DSZS=6, ZYXWS=78, XSXWSSS=8, XSXWBSS=7}
			 * 而metaAttrDomains对应一个实体（也就是数据库中一张表）的各个属性 比如：
			 * metaAttrDomain1对应：目前在校博士生数 metaAttrDomain2对应：目前在校硕士生数
			 * 
			 * 它们内置对应于rowData的字段，如ZX_BSS(可以用metaAttr.getColumnName()得到)
			 * 根据ZX_BSS获得rowData中对应的信息9(可以用data = (String)
			 * rowData.get(colName)得到)
			 */
			List<LogicCheckAttrResult> everyRowDataAttrsCheckResults = check(
					metaEntity, metaAttrDomains, rowData, userId);

			// 一旦出现错误，size>0为true
			// hasError就被赋值为true，这样就可以判断这个实体里面有错误
			hasError = everyRowDataAttrsCheckResults.size() > 0 || hasError;

			if (attrResultList != null) {
				attrResultList.addAll(everyRowDataAttrsCheckResults);
			}
		}
		return hasError;
	}

	private List<Map<String, String>> getCheckData(String unitId,
			String discId, String checkEntityId) {
		Map<String, Object> param = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(unitId)) {
			param.put("UNIT_ID", unitId);
		}
		if (StringUtils.isNotBlank(discId)) {
			param.put("DISC_ID", discId);
		}
		// 根据entityID选出了要查的数据
		return super.getData(checkEntityId, param, null, true, 0, 0);
	}

	private String check(String attrName, MetaEntity metaEntity,
			List<MetaAttrDomain> metaAttrDomains, Map<String, String> rowData) {
		Iterator<MetaAttrDomain> itor = metaAttrDomains.iterator();

		// 遍历某个entity，也就是数据库中某张表的所有属性
		while (itor.hasNext()) {

			MetaAttrDomain attr = itor.next();

			if (null != attr.getCheckRule()) {
				/*System.out
						.println("*******************************************");
				System.out
						.println("*******************************************");
				System.out
						.println("*******************************************");
				System.out
						.println("*******************************************");
				System.out
						.println("*******************************************");
				System.out
						.println("*******************************************");
				System.out
						.println("*******************************************");
				System.out
						.println("*******************************************");
				System.out
						.println("*******************************************");
				System.out
						.println("*******************************************");
				System.out
						.println("*******************************************");
				System.out.println(attr.getAttribute().getName() + " "
						+ attr.getCheckRule().getName());*/
			}

			// 某个属性(数据库表中的某个属性)不需要逻辑检查
			if (null == attr.getCheckRule()) {
				continue;
			} else if (!attrName.equals(attr.getAttribute().getName())) {
				continue;
			}

			// 根据attrDomain找规则
			String ruleClass = attr.getCheckRuleClass();
			// 开始检查
			MetaAttrCheck checkInstance = LogicCheckFactory
					.getMetaAttrCheckInstance(ruleClass);
			String checkResult = checkInstance.check(attr, rowData);
			if (!checkResult.equals("CHECKPASS")) {
				return checkResult;
			}
		}
		return "CHECKPASS";
	}

	// 该函数返回一行数据的所有需要检查的属性的检查结果
	private List<LogicCheckAttrResult> check(MetaEntity metaEntity,
			List<MetaAttrDomain> metaAttrDomains, Map<String, String> rowData,
			String userId) {
		// result才是真正要返回的结果
		// 下文中checkResult和map都只是对应了一行结果中的有问题的“属性”的检查结果
		List<LogicCheckAttrResult> result = new LinkedList<LogicCheckAttrResult>();
		Iterator<MetaAttrDomain> itor = metaAttrDomains.iterator();

		// 遍历某个entity，也就是数据库中某张表的所有属性
		while (itor.hasNext()) {
			MetaAttrDomain attr = itor.next();
			// 首先进行字典检查，如果检查通过再进行具体的检查
			String dicId = attr.getAttribute().getDicId();
			if (dicId != null) {
				MetaControlType type=attr.getControlTypeObject();
				if(type.equals(MetaControlType.MULSELECT)){
					
				}else{
					
				}
				String colName = attr.getColumnName();
				String data = (String) rowData.get(colName);
				
				Iterator<String> iter = attr.getDic().getDicItems().keySet().iterator();
				boolean flag = true;
				while(iter.hasNext()) {
					if (iter.next().equals(data)) {
						// 命中字典，通过
						flag = false;
						break;
					}
				}
				// 命中字典则不会记录
				if (flag) {
					result.add(packageDicCheckResult(metaEntity, metaAttrDomains,
							rowData, userId, attr, "字段值不在限定范围之内，不能正确显示"));
					continue;
				}
			}
			// 某个属性(数据库表中的某个属性)不需要逻辑检查
			if (null == attr.getCheckRule()) {
				continue;
			}
			// 根据attrDomain找规则
			String ruleClass = attr.getCheckRuleClass();

			System.out.println("*******************************************");
			System.out.println(attr.getChsName() + "字段有" + ruleClass + "规则");
			System.out.println("com.dsep.domain.dsepmeta.logicrule.rules."
					+ ruleClass);
			System.out.println("*******************************************");

			if (ruleClass.equals("checkIfExistAttach"))
				continue;
			// 开始检查
			MetaAttrCheck checkInstance = LogicCheckFactory
					.getMetaAttrCheckInstance(ruleClass);
			
			String checkResult = checkInstance.check(attr, rowData);
			if (!checkResult.equals("CHECKPASS")) {
				// 每查出一个属性值问题，拼装成一个对象，最后放到result中
				result.add(packageAttrResult(metaEntity, metaAttrDomains,
						rowData, userId, attr, checkResult));
			}
		}
		return result;
	}

	// public boolean entityLogicCheck(String unitId, String discId, String
	// userId) {
	// logicCheckEntityResultDao
	// .deleteAllOfTheLastData(unitId, discId, userId);
	// List<UnitDiscCollect> entityList = new LinkedList<UnitDiscCollect>();
	// entityList = discIndexService.getUnitDiscCollects(unitId, discId);
	// for (UnitDiscCollect entity : entityList) {
	// String metaUnitId = entity.getUnitId();
	// String metaDiscId = entity.getDiscId();
	// for (String[] data : entity.getEntityIdandName()) {
	// String metaEntityId = data[0];
	// String metaEntityChsName = data[1];
	//
	// LogicCheckEntityResult ent = new LogicCheckEntityResult();
	//
	// ent.setUnitId(metaUnitId);
	// ent.setDiscId(metaDiscId);
	// ent.setCheckDate(new Date());
	// ent.setUserId(userId);
	// ent.setEntityChsName(metaEntityChsName);
	// ent.setEntityId(metaEntityId);
	// int count = logicCheckAttrResultDao.getCount(metaUnitId,
	// metaDiscId, metaEntityId, userId);
	// if (count > 0) {
	// ent.setHasError(true);
	// } else {
	// ent.setHasError(false);
	// }
	// MetaEntityDomain entityDomain = metaEntityService
	// .getEntityDomain(metaEntityId, "C");// 获得数据采集类别中的科研获奖领域对象
	// if (null == entityDomain.getCheckRule()) {
	// // 没有检查规则，自然不会有警告
	// ent.setHasWarn(false);
	// ent.setConclusion("");
	// logicCheckEntityResultDao.saveOrUpdate(ent);
	// continue;
	// } else {
	// String result = this.entitycheck(metaEntityId, metaUnitId,
	// metaDiscId);
	// if ("PASSED".equals(result)) {
	// ent.setHasWarn(false);
	// ent.setConclusion("");
	// logicCheckEntityResultDao.saveOrUpdate(ent);
	// } else {
	// ent.setHasWarn(true);
	// ent.setConclusion(result);
	// logicCheckEntityResultDao.saveOrUpdate(ent);
	// }
	// }
	// }
	// }
	// return true;
	// }

	@Override
	public EntityLogicCheckVM entityLogicCheck(String unitId, String displineId) {

		List<UnitDiscCollect> entityList = discIndexService
				.getUnitDiscCollects(unitId, displineId);
		EntityLogicCheckVM result = new EntityLogicCheckVM();

		for (UnitDiscCollect entity : entityList) {
			String metaUnitId = entity.getUnitId();
			String metaDiscId = entity.getDiscId();
			for (String[] data : entity.getEntityIdandName()) {
				String entityId = data[0];
				String entityChsName = data[1];

				MetaEntityDomain entityDomain = metaEntityService
						.getEntityDomain(entityId, "C");

				String entityResult = this.entitycheck(entityId, metaUnitId,
						metaDiscId, entityDomain, true);
				if (!"PASSED".equals(entityResult)) {
					result.setPassed(false);
					if (entityResult.startsWith("ERROR")) {
						entityResult = entityResult.replace("ERROR", "");
						// 0:错误
						result.addResult(entityChsName, "0", metaUnitId,
								metaDiscId, entityResult);
					} else {
						// 1:警告
						result.addResult(entityChsName, "1", metaUnitId,
								metaDiscId, entityResult);
					}

				}
			}
		}
		return result;
	}

	private String entitycheck(String entityId, String unitId, String discId,
			MetaEntityDomain entityDomain, Boolean checkFromSubmitData) {
		
		if (entityDomain.getCheckRule() == null)
			return "PASSED";
		if (entityDomain.getCheckRuleClass().equals("CheckImport")) {
			if (checkFromSubmitData) {
				// 对导入的数据进行检查
				Boolean result = atomicAttrLogicCheck(unitId, discId, entityId,
						entityDomain.getId(), entityDomain);
				// result represents has error,if result is false, means no error
				if (!result)
					return "PASSED";
				else
					return "ERROR" + entityDomain.getChsName() + "有错误";
			} else {
				// 导入的表只需要对它的所有字段作检查，不需要做实体检查，所以直接返回"PASSED"
				atomicAttrLogicCheck(unitId, discId, entityId,
						entityDomain.getId(), entityDomain);
				return "PASSED";
			}
		}

		String ruleClass = entityDomain.getCheckRuleClass();
		System.out.println("=================" + ruleClass + "================");

		MetaEntityCheck checkOperater = LogicCheckFactory
				.getMetaEntityCheckInstance(ruleClass);
		int countOfEntityData = collectService.getCount(entityId, unitId,
				discId);

		JqgridVM list = collectService.getJqGridData(entityId, unitId, discId,
				0, 100, "SEQ_NO", true);
		List<Map<String, String>> datas = list.getRows();
		String entityResult = checkOperater.entityCheck(entityDomain,
				discIndexService.getCategotyByDiscId(discId),
				countOfEntityData, datas);
		return entityResult;
	}

	@Override
	public String getLogicCheckResultBeforeSubmit(String unitId, String discId,
			User user) {
		List<LogicCheckEntityResult> list = logicCheckEntityResultDao
				.getResultForDisc(unitId, discId, user.getId(), 1, 100000,
						true, "ID");
		boolean flag = false;
		for (LogicCheckEntityResult result : list) {
			if (result.getHasError()) {
				flag = true;
				break;
			}
		}
		//String info2 = logicCheckIdentityResultDao.getDate(user.getId());
		if (flag) {
			return "error";
		} else {
			String info = logicCheckIdentityResultDao.getDate(user.getId());
			if (info.equals(LogicCheckStatusCode.HAVE_NOT_CHECKED)) {
				return "unchecked";
			} else {
				
				return info;
			}
			
		}
		
	}

	// ==================导出excel文件=================================

	@Override
	public String exportErrorAndWarnData(String unitId, String discId,
			User user, String rootPath) {

		List<String> errorExcelTitle = new LinkedList<String>();
		errorExcelTitle.add("学校ID");
		errorExcelTitle.add("学科ID");
		errorExcelTitle.add("采集项名");
		errorExcelTitle.add("原始数据序号");
		errorExcelTitle.add("字段名");
		errorExcelTitle.add("错误类型");
		errorExcelTitle.add("错误信息");

		List<String> warnExcelTitle = new LinkedList<String>();
		warnExcelTitle.add("学校ID");
		warnExcelTitle.add("学科ID");
		warnExcelTitle.add("采集项名");
		warnExcelTitle.add("警告信息");

		List<List<String>> excelTitle = new LinkedList<List<String>>();
		excelTitle.add(errorExcelTitle);
		excelTitle.add(warnExcelTitle);

		String warnSheetName = "逻辑检查警告信息表";
		String errorSheetName = "逻辑检查错误信息表";
		List<String> sheetName = new LinkedList<String>();
		sheetName.add(errorSheetName);
		sheetName.add(warnSheetName);

		String storeFolder = "logiccheck";

		// 用来存放所有的错误和警告信息的容器

		List<List<String[]>> exportDataList = new LinkedList<List<String[]>>();
		List<String> units = new ArrayList<String>();
		List<String> discs = new ArrayList<String>();
		// 中心用户
		if (user.getUserType().equals("1")) {
			if (StringUtils.isNotBlank(unitId)) {
				units.add(unitId);
			} else {
				units = unitService.getAllEvalUnitIds();
			}

			List<String[]> allError = new LinkedList<String[]>();
			List<String[]> allWarn = new LinkedList<String[]>();
			for (String unit : units) {
				if ((StringUtils.isNotBlank(discId))
						&& (!discId.equals("undefined"))) {
					discs.add(discId);
				} else {
					discs = disciplineService.getJoinDisciplineByUnitId(unit);
				}
			
				
				
				for (String disc : discs) {
					List<String[]> errorExcelDataList = new LinkedList<String[]>();
					List<String[]> warnExcelDataList  = new LinkedList<String[]>();
					List<LogicCheckAttrResult> errorDataList = logicCheckAttrResultDao
							.getExportResultsOfError(unit, disc, null,
									user.getId());
					

					for (LogicCheckAttrResult attrEntity : errorDataList) {
						String[] data = new String[10];
						data[0] = attrEntity.getUnitId();
						data[1] = attrEntity.getDiscId();
						data[2] = attrEntity.getEntityChsName();
						data[3] = String.valueOf(attrEntity.getSeqNo());
						data[4] = attrEntity.getAttrChsName();
						data[5] = attrEntity.getErrorType();
						data[6] = attrEntity.getError();
						errorExcelDataList.add(data);
					}

					List<LogicCheckEntityResult> warnDataList = logicCheckEntityResultDao
							.getExportResultsOfWarn(unit, disc, null,
									user.getId());
					
					for (LogicCheckEntityResult warnEntity : warnDataList) {
						String[] data = new String[10];
						data[0] = warnEntity.getUnitId();
						data[1] = warnEntity.getDiscId();
						data[2] = warnEntity.getEntityChsName();
						data[3] = warnEntity.getConclusion();
						warnExcelDataList.add(data);
					}
					if (errorExcelDataList.size() > 0) {
						allError.addAll(errorExcelDataList);
					}
					if (warnExcelDataList.size() > 0) {
						allWarn.addAll(warnExcelDataList);
					}
				}
			}
			exportDataList.add(allError);
			exportDataList.add(allWarn);
			System.out.println("========b4 saving ==========");
			//System.out.println(exportDataList.get(0).size());
			//System.out.println(exportDataList.get(1).size());
			/*for (List<String[]> list : exportDataList) {
				for (String[] arr : list) {
					for (String str : arr) {
						System.out.print(str + " ");
					}
					System.out
							.println("============================================");
				}
			}*/
			return exportService.exportExcelByData(excelTitle, exportDataList,
					sheetName, rootPath, storeFolder);
		}
		// 学校用户
		else if (user.getUserType().equals("2")) {
			units.add(user.getUnitId());

			for (String unit : units) {
				if ((StringUtils.isNotBlank(discId))
						&& (!discId.equals("undefined"))) {
					discs.add(discId);
				} else {
					discs = disciplineService.getJoinDisciplineByUnitId(unit);
				}
				List<String[]> errorExcelDataList = new LinkedList<String[]>();
				List<String[]> warnExcelDataList = new LinkedList<String[]>();

				for (String disc : discs) {
					List<LogicCheckAttrResult> errorDataList = logicCheckAttrResultDao
							.getExportResultsOfError(unit, disc, null,
									user.getId());

					for (LogicCheckAttrResult attrEntity : errorDataList) {
						String[] data = new String[10];
						data[0] = attrEntity.getUnitId();
						data[1] = attrEntity.getDiscId();
						data[2] = attrEntity.getEntityChsName();
						data[3] = String.valueOf(attrEntity.getSeqNo());
						data[4] = attrEntity.getAttrChsName();
						data[5] = attrEntity.getErrorType();
						data[6] = attrEntity.getError();
						errorExcelDataList.add(data);
					}

					List<LogicCheckEntityResult> warnDataList = logicCheckEntityResultDao
							.getExportResultsOfWarn(unit, disc, null,
									user.getId());
					for (LogicCheckEntityResult warnEntity : warnDataList) {
						String[] data = new String[10];
						data[0] = warnEntity.getUnitId();
						data[1] = warnEntity.getDiscId();
						data[2] = warnEntity.getEntityChsName();
						data[3] = warnEntity.getConclusion();
						warnExcelDataList.add(data);
					}

				}
				if (errorExcelDataList.size() > 0) {
					exportDataList.add(errorExcelDataList);
				}
				if (warnExcelDataList.size() > 0) {
					exportDataList.add(warnExcelDataList);
				}
			}
			/*System.out.println("========b4 saving ==========");
			for (List<String[]> list : exportDataList) {
				for (String[] arr : list) {
					for (String str : arr) {
						System.out.print(str + " ");
					}
					System.out
							.println("============================================");
				}
			}*/
			return exportService.exportExcelByData(excelTitle, exportDataList,
					sheetName, rootPath, storeFolder);
		} 
		// 学科用户
		else if (user.getUserType().equals("3")) {
			units.add(user.getUnitId());

			for (String unit : units) {
				discs.add(user.getDiscId());
				List<String[]> errorExcelDataList = new LinkedList<String[]>();
				List<String[]> warnExcelDataList = new LinkedList<String[]>();

				for (String disc : discs) {
					List<LogicCheckAttrResult> errorDataList = logicCheckAttrResultDao
							.getExportResultsOfError(unit, disc, null,
									user.getId());

					for (LogicCheckAttrResult attrEntity : errorDataList) {
						String[] data = new String[10];
						data[0] = attrEntity.getUnitId();
						data[1] = attrEntity.getDiscId();
						data[2] = attrEntity.getEntityChsName();
						data[3] = String.valueOf(attrEntity.getSeqNo());
						data[4] = attrEntity.getAttrChsName();
						data[5] = attrEntity.getErrorType();
						data[6] = attrEntity.getError();
						errorExcelDataList.add(data);
					}

					List<LogicCheckEntityResult> warnDataList = logicCheckEntityResultDao
							.getExportResultsOfWarn(unit, disc, null,
									user.getId());
					for (LogicCheckEntityResult warnEntity : warnDataList) {
						String[] data = new String[10];
						data[0] = warnEntity.getUnitId();
						data[1] = warnEntity.getDiscId();
						data[2] = warnEntity.getEntityChsName();
						data[3] = warnEntity.getConclusion();
						warnExcelDataList.add(data);
					}

				}
				if (errorExcelDataList.size() > 0) {
					exportDataList.add(errorExcelDataList);
				} else {
					exportDataList.add(errorExcelDataList);
				}
				if (warnExcelDataList.size() > 0) {
					exportDataList.add(warnExcelDataList);
				}
			}
			System.out.println("========b4 saving ==========");
			/*for (List<String[]> list : exportDataList) {
				for (String[] arr : list) {
					for (String str : arr) {
						System.out.print(str + " ");
					}
					System.out
							.println("============================================");
				}
			}*/
			return exportService.exportExcelByData(excelTitle, exportDataList,
					sheetName, rootPath, storeFolder);
		} else {
			return null;
		}

	}

	// 封装一个entityResult实体
	private LogicCheckEntityResult packageEntityResult(String unitId,
			String discId, String entityId, String userId, String conclusion,
			String chsName, boolean hasError, boolean hasWarn) {
		LogicCheckEntityResult ent = new LogicCheckEntityResult();

		ent.setUnitId(unitId);
		ent.setDiscId(discId);
		ent.setCheckDate(new Date());
		ent.setUserId(userId);
		ent.setEntityChsName(chsName);
		ent.setEntityId(entityId);
		ent.setHasWarn(hasWarn);
		ent.setHasError(hasError);
		ent.setConclusion(conclusion);
		return ent;
	}

	// 封装一个attrResult实体
	private LogicCheckAttrResult packageAttrResult(MetaEntity metaEntity,
			List<MetaAttrDomain> metaAttrDomains, Map<String, String> rowData,
			String userId, MetaAttrDomain attr, String checkResult) {
		LogicCheckAttrResult attrResult = new LogicCheckAttrResult();
		attrResult.setUnitId(rowData.get("UNIT_ID"));
		attrResult.setDiscId(rowData.get("DISC_ID"));
		attrResult.setEntityId(metaEntity.getId());
		attrResult.setUserId(userId);
		attrResult.setDataId(rowData.get("ID"));
		attrResult.setSeqNo(Integer.valueOf(rowData.get("SEQ_NO")));
		attrResult.setAttrId(attr.getId());
		attrResult.setEntityChsName(metaEntity.getChsName());
		attrResult.setAttrChsName(attr.getChsName());
		attrResult.setError(checkResult);
		attrResult.setErrorType(Dictionaries.getErrorType(attr
				.getCheckRuleCategory()));
		return attrResult;
	}
	
	// 检测字典数据，封装一个attrResult实体
	private LogicCheckAttrResult packageDicCheckResult(MetaEntity metaEntity,
				List<MetaAttrDomain> metaAttrDomains, Map<String, String> rowData,
				String userId, MetaAttrDomain attr, String checkResult) {
			LogicCheckAttrResult attrResult = new LogicCheckAttrResult();
			attrResult.setUnitId(rowData.get("UNIT_ID"));
			attrResult.setDiscId(rowData.get("DISC_ID"));
			attrResult.setEntityId(metaEntity.getId());
			attrResult.setUserId(userId);
			attrResult.setDataId(rowData.get("ID"));
			attrResult.setSeqNo(Integer.valueOf(rowData.get("SEQ_NO")));
			attrResult.setAttrId(attr.getId());
			attrResult.setEntityChsName(metaEntity.getChsName());
			attrResult.setAttrChsName(attr.getChsName());
			attrResult.setError(checkResult);
			// 字符串类型错误
			attrResult.setErrorType(Dictionaries.getErrorType("S"));
			return attrResult;
		}
	
	

	private LogicCheckAttrResult packageAttrResultForXYJG(String entityId,
			String unitId, String discId, String userId, String checkResult, Integer seqNo) {
		LogicCheckAttrResult attrResult = new LogicCheckAttrResult();
		attrResult.setUnitId(unitId);
		attrResult.setDiscId(discId);
		attrResult.setEntityId(entityId);
		attrResult.setUserId(userId);
		// 学缘结构不是作为一条字段查的
		attrResult.setDataId(seqNo.toString());
		attrResult.setSeqNo(seqNo);
		attrResult.setAttrId("");
		attrResult.setEntityChsName("学缘结构");
		attrResult.setAttrChsName("在总人数中的比例");
		attrResult.setError(checkResult);
		attrResult.setErrorType("其他");
		return attrResult;
	}

	@Override
	public Boolean handleMessage(QueueMessage message) {
		try {
			this.logicCheck(message.getParameters().get("unitId"), message
					.getParameters().get("discId"), message.getParameters()
					.get("userId"));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// 与业务逻辑无关的setter和getter放在最下面==========================================================
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

	public DMDiscIndexService getDiscIndexService() {
		return discIndexService;
	}

	public void setDiscIndexService(DMDiscIndexService discIndexService) {
		this.discIndexService = discIndexService;
	}

	public ExportService getExportService() {
		return exportService;
	}

	public void setExportService(ExportService exportService) {
		this.exportService = exportService;
	}

	public DMCollectService getCollectService() {
		return collectService;
	}

	public void setCollectService(DMCollectService collectService) {
		this.collectService = collectService;
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

	public LogicCheckIdentityResultDao getLogicCheckIdentityResultDao() {
		return logicCheckIdentityResultDao;
	}

	public void setLogicCheckIdentityResultDao(
			LogicCheckIdentityResultDao logicCheckIdentityResultDao) {
		this.logicCheckIdentityResultDao = logicCheckIdentityResultDao;
	}

}
