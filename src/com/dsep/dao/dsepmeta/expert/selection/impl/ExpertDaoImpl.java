package com.dsep.dao.dsepmeta.expert.selection.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.expert.selection.ExpertDao;
import com.dsep.domain.dsepmeta.expert.ExpertNumberGroupByUnitId;
import com.dsep.entity.expert.Expert;
import com.dsep.util.GUID;
import com.dsep.util.expert.ExpertEvalCurrentStatus;

public class ExpertDaoImpl extends
		DsepMetaDaoImpl<Expert, String> implements ExpertDao {

	private String getExpertTableName() {
		return super.getTableName("E", "EXPERT");
	}

	@Override
	public int Count(String batchId) {
		String hql = "select count(*) from Expert where evalBatch.id='"
				+ batchId + "'";
		Query query = getSession().createQuery(hql);
		return ((Long) query.iterate().next()).intValue();
	}

	@Override
	public void addExpertSelected(Expert expert) {
		expert.setId(GUID.get());

		// 这些东西应该放在service中的！！
		// 初始化已选专家
		expert.setCurrentStatus(ExpertEvalCurrentStatus.NotMailed.toInt());
		expert.setRemark("");
		expert.setSource(0);

		super.save(expert);
	}

	@Override
	public List<Expert> deleteExpertsNotMailed(String batchId) {
		// String sql = "delete from " + getExpertSelectedTableName()
		// + " where CURRENT_STATUS = "
		// + ExpertEvalCurrentStatus.NotMailed.getIndex();
		// super.sqlBulkUpdate(sql);
		// 返回没有被删除的专家,用于log
		// return super.getAll();
		String hql = "delete Expert where currentStatus="
				+ ExpertEvalCurrentStatus.NotMailed.toInt()
				+ " and evalBatch.id='" + batchId + "'";
		super.hqlBulkUpdate(hql);
		return null;
	}

	@Override
	public List<Expert> hqlPage(int pageIndex, int pageSize,
			Boolean desc, String orderProperName) {
		String hql = "select distinct e from Expert e";
		if (orderProperName.equals("college")) {
			hql += " order by e.teacher.college.collegeName";
		} else if (orderProperName.equals("discipline")) {
			hql += " order by e.teacher.discipline.disciplineName";
			// } else if (orderProperName.equals("exp"))
			// Boolean isTeacherField = orderProperName.equals("name") ||
			// orderProperName.equals("college") ||
			// orderProperName.equals("discipline") ||
			// orderProperName.equals("officePhone") ||
			// orderProperName.equals("expertType");
			// if (isTeacherField) {
			//
		} else {
			hql += " order by e." + orderProperName;
		}
		if (desc) {
			hql += " desc";
		} else {
			hql += " asc";
		}
		System.out.println(hql);
		return (List<Expert>) super.hqlPage(hql, pageIndex, pageSize);
	}

	/*
	 * @Override public ExpertSelected getExpertById(String expertId) { String
	 * sql = "select * from " + getTableName() + " where ID = ?"; String sql =
	 * "select * from DSEP_EXPERT_SELECTED"; String[] params = new String[1];
	 * params[0] = expertId; return super.sqlFind(sql,params).get(0); }
	 */

	@Override
	public List<String> getExpertDiscs(String expertId) {
		Expert theExpert = super.get(expertId);
		List<String> disciplineList = new ArrayList<String>();
		disciplineList.add(theExpert.getDiscId());
		if (theExpert.getDiscId2() != null && theExpert.getDiscId2() != "") {
			disciplineList.add(theExpert.getDiscId2());
		}
		return disciplineList;
	}

	@Override
	public List<String> getExpertNumberByName(String name) {
		String sql = "select EXPERT_NUMBER from  "
				+ getExpertTableName() + " where EXPERT_NAME like '%"
				+ name + "%'";
		List<String> list = super.GetShadowResult(sql);
		return list;
	}

	@Override
	public List<String> getExpertNumberByDiscIdAndUnitId(String discId,
			String unitId) {
		String sql = "select EXPERT_NUMBER from  "
				+ getExpertTableName() + " where ";
		if (discId != null && unitId != null) {
			sql += " DISC_ID='" + discId + "' or DISC_ID2='" + discId
					+ "' and UNIT_ID='" + unitId + "'";
		} else if (discId != null && unitId == null) {
			sql += " DISC_ID='" + discId + "' or DISC_ID2='" + discId + "'";
		} else if (discId == null && unitId != null) {
			sql += " UNIT_ID='" + unitId + "'";
		}

		List<String> list = super.GetShadowResult(sql);
		return list;
	}

	@Override
	public List<Expert> query(String name) {
		String hql = "from Expert e where e.expertName like ?";
		return super.hqlFind(hql, new Object[] { name });
	}

	@Override
	public List<Expert> query(String name, String expertNo,
			String expertDisc, Integer expertCurrentStatus, String batchId) {
		List<Object> conditions = new ArrayList<Object>();
		String hql = "select e from Expert e left join e.evalBatch as b where ";
		if (null != name && !"".equals(name)) {
			hql += " e.expertName like '%" + name + "%' and ";
		}
		if (null != expertNo && !"".equals(expertNo)) {
			hql += " e.expertNumber like ? and ";
			conditions.add(expertNo);
		}
		if (null != expertDisc) {
			hql += " (e.discId like ? or e.discId2 like ?) and ";
			conditions.add(expertDisc);
			conditions.add(expertDisc);
		}
		if (null != expertCurrentStatus) {
			hql += " e.currentStatus = ? and ";
			conditions.add(expertCurrentStatus);
		}

		// hql = hql.substring(0, hql.length() - 4);
		hql += " b.id = ?";
		conditions.add(batchId);
		return super.hqlFind(hql, conditions.toArray());
	}

	@Override
	public List<Expert> query(String expertId, String expertName,
			String expertDisc, Integer expertCurrentStatus,
			String expertNumber, String expertUnit, String expertIs985,
			String expertIs211) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Expert> getExpertsNotMailed() {
		String sql = "select * from " + getExpertTableName()
				+ " where current_status="
				+ ExpertEvalCurrentStatus.NotMailed.toInt();
		return super.sqlFind(sql);
	}

	@Override
	public List<Expert> getExpertsNotReplied() {
		// ExpertEvalCurrentStatus.Mailed表示已经发送了邮件,但是没有回复
		String sql = "select * from " + getExpertTableName()
				+ " where current_status="
				+ ExpertEvalCurrentStatus.Mailed.toInt();
		return super.sqlFind(sql);
	}

	@Override
	public List<Expert> getComfirmedExpertsByLoginId(String loginId) {
		String sql = "select * from " + getExpertTableName()
				+ " where CURRENT_STATUS="
				+ ExpertEvalCurrentStatus.Confirmed.toInt()
				+ " AND (EXPERT_EMAIL1='" + loginId + "' or EXPERT_EMAIL2='"
				+ loginId + "' or EXPERT_EMAIL3='" + loginId + "')";
		return super.sqlFind(sql);
	}

	@Override
	public List<Expert> page(String batchId, int pageIndex,
			int pageSize, Boolean desc, String orderProperName) {
		String sql = "select * from " + getExpertTableName();
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (!StringUtils.isBlank(batchId)) {
			params.add(batchId); // 参数
			conditionColumns.add("BATCH_ID");// 查询条件
		}
		sql += super.sqlAndConditon(conditionColumns);
		return super.sqlPage(sql, pageIndex, pageSize, desc, orderProperName,
				params.toArray());
	}

	@Override
	public Map<List<String>, List<String>> getStatisticsInfo(String batchId) {
		/*String viewName = "dsep_e_expert_selected_view";
		String sql = "create view "
				+ viewName
				+ " as select case when DISC_ID='' or DISC_ID is null then DISC_ID2 else DISC_ID end as DISC_ID FROM "
				+ getExpertSelectedTableName() + " WHERE BATCH_ID='" + batchId+ "'";
		super.sqlBulkUpdate(sql);*/

		/*sql = "select count(*) from " + viewName + " where BATCH_ID='"
				+ batchId + "' group by DISC_ID ORDER BY DISC_ID ASC";*/
		String sql = "";
		sql = "select count(*) from " + getExpertTableName()
				+ " group by real_Disc_Id ORDER BY real_Disc_Id ASC";
		List<String> nums = super.GetShadowResult(sql);

		sql = "select real_Disc_Id from " + getExpertTableName()
				+ " group by real_Disc_Id ORDER BY real_Disc_Id ASC";
		List<String> discs = super.GetShadowResult(sql);

		Map<List<String>, List<String>> map = new HashMap<List<String>, List<String>>();
		map.put(discs, nums);
		return map;
	}

	@Override
	public List<String> getExpertByExpertNumber(String expertNumber) {
		String sql = "select EXPERT_NUMBER from  "
				+ getExpertTableName() + " where EXPERT_NUMBER ='"
				+ expertNumber + "'";
		List<String> list = super.GetShadowResult(sql);
		return list;
	}

	@Override
	public void modifyExpertEmail(String id, String newEmail,
			String validateCode) {
		String sql = "update " + getExpertTableName()
				+ " set EXPERT_EMAIL1='" + newEmail + "' where ID='" + id + "'";
		super.sqlBulkUpdate(sql);
		sql = "update " + getExpertTableName()
				+ " set VALIDATE_CODE1='" + validateCode + "' where ID='" + id
				+ "'";
		super.sqlBulkUpdate(sql);
	}

	@Override
	public List<Expert> queryExpertsByDiscIdOrDiscId2(String discId,
			String batchId) {
		String sql = "select * from " + getExpertTableName()
				+ " where DISC_ID='" + discId + "' or DISC_ID2='" + discId
				+ "' and BATCH_ID='" + batchId + "'";
		return super.sqlFind(sql);
	}

	@Override
	public List<String> queryExpertZJBHsByDiscIdOrDiscId2(String discId,
			String batchId) {
		String sql = "select distinct EXPERT_NUMBER from "
				+ getExpertTableName() + " where (DISC_ID='" + discId
				+ "' or DISC_ID2='" + discId + "') and BATCH_ID='" + batchId
				+ "'";
		return super.GetShadowResult(sql);
	}

	@Override
	public List<ExpertNumberGroupByUnitId> countExpertNumbersByConditionsGroupByUnitId(
			String discId, String batchId, List<Integer> statuses) {
		String sqlSnippet = "";
		for (int i = 0; i < statuses.size(); i++) {
			if (i == 0) {
				sqlSnippet += " and (CURRENT_STATUS=" + statuses.get(i);
			} else if (i == statuses.size() - 1) {
				sqlSnippet += " or CURRENT_STATUS=" + statuses.get(i) + ") ";
			} else {
				sqlSnippet += " or CURRENT_STATUS=" + statuses.get(i);
			}
		}
		
		// 因为GetShadowResult只能获取一列的结果，所以需要用两条sql语句进行拼装
		String sql = "select UNIT_ID from " + getExpertTableName()
				+ " where REAL_DISC_ID='" + discId + "' and BATCH_ID='"
				+ batchId + "'" + sqlSnippet
				+ " group by UNIT_ID order by UNIT_ID asc";
		List<String> results = super.GetShadowResult(sql);

		String sql2 = "select count(ID) from " + getExpertTableName()
				+ " where REAL_DISC_ID='" + discId + "' and BATCH_ID='"
						+ batchId + "'" + sqlSnippet
				+ " group by UNIT_ID order by UNIT_ID asc";
		List<Integer> results2 = super.GetShadowresult(sql2);
		
		List<ExpertNumberGroupByUnitId> list = new ArrayList<ExpertNumberGroupByUnitId>();
		// results和results2一定是一样的，所以拿一个做循环即可
		for (int i = 0; i < results.size(); i++) {
			ExpertNumberGroupByUnitId ele = new ExpertNumberGroupByUnitId();
			ele.setUnitId(results.get(i));
			ele.setNum(results2.get(i));
			list.add(ele);
		}
		return list;
	}
}
